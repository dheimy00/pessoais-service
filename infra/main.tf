# ----------------------
# Data Sources
# ----------------------
data "aws_availability_zones" "available" {
  state = "available"
}

data "aws_lb" "existing_nlb" {
  count = var.create_nlb ? 0 : 1
  name  = "${var.service_name}-nlb"
}

data "aws_caller_identity" "current" {}
data "aws_region" "current" {}

data "aws_subnet" "subnet_private" {
  for_each = toset(var.subnets_id)
  id       = each.value
}

data "aws_security_group" "security_group" {
  id = "sg-09ce1a832c78b30f6"
}

locals {
  nlb_arn = var.create_nlb ? aws_lb.app[0].arn : data.aws_lb.existing_nlb[0].arn
}

# ----------------------
# Load Balancer
# ----------------------
resource "aws_lb" "app" {
  count              = var.create_nlb ? 1 : 0
  name               = "${var.service_name}-nlb"
  internal           = true
  load_balancer_type = "network"
  security_groups    = [aws_security_group.nlb_sg.id]
  subnets            = var.subnets_id

  tags = {
    Name        = "${var.service_name}-nlb"
    Environment = var.environment
  }

  lifecycle {
    prevent_destroy = false
    ignore_changes  = [subnets, security_groups]
  }
}

resource "aws_lb_target_group" "blue" {
  name        = "${var.service_name}-tg-blue"
  port        = 8080
  protocol    = "TCP"
  target_type = "ip"
  vpc_id      = var.vpc_id

  health_check {
    enabled             = true
    interval            = 30
    timeout             = 5
    healthy_threshold   = 2
    unhealthy_threshold = 2
    protocol            = "TCP"
    port                = "traffic-port"
  }
}

resource "aws_lb_listener" "tcp" {
  load_balancer_arn = local.nlb_arn
  port              = 8080
  protocol          = "TCP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.blue.arn
  }

  depends_on = [aws_lb_target_group.blue]
}

# ----------------------
# Security Group
# ----------------------
# Security Group for NLB
resource "aws_security_group" "nlb_sg" {
  name        = "${var.service_name}-nlb-sg"
  description = "Allow inbound traffic to NLB"
  vpc_id      = var.vpc_id

  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name        = "${var.service_name}-nlb-sg"
    Environment = "${var.environment}"
  }
}

resource "aws_security_group" "rds" {
  name   = "${var.service_name}-rds-sg"
  vpc_id = var.vpc_id


  ingress {
    from_port       = 3306
    to_port         = 3306
    protocol        = "tcp"
    security_groups = [data.aws_security_group.security_group.id]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name        = "${var.service_name}-rds-sg"
    Environment = "${var.environment}"
  }
}

# ----------------------
# CloudWatch Logs
# ----------------------
resource "aws_cloudwatch_log_group" "ecs_logs" {
  name              = "/ecs/${var.service_name}"
  retention_in_days = 30
}

# ----------------------
# ECS Task Definition
# ----------------------
resource "aws_ecs_task_definition" "app" {
  family                   = "${var.service_name}-task"
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  cpu                      = 256
  memory                   = 512
  execution_role_arn       = aws_iam_role.ecs_task_execution_role.arn
  task_role_arn            = aws_iam_role.ecs_task_role.arn
  container_definitions = jsonencode([{
    name        = "${var.service_name}-container",
    image       = "${data.aws_caller_identity.current.account_id}.dkr.ecr.${data.aws_region.current.name}.amazonaws.com/${var.service_name}-app:latest",
    environment = var.task_environment_vars,
    secrets     = var.secrets,
    portMappings = [{
      containerPort = 8080,
      hostPort      = 8080,
      protocol      = "tcp"
    }],
    logConfiguration = {
      logDriver = "awslogs",
      options = {
        awslogs-group         = aws_cloudwatch_log_group.ecs_logs.name,
        awslogs-region        = data.aws_region.current.name,
        awslogs-stream-prefix = "ecs"
      }
    },
    healthCheck = {
      command     = ["CMD", "curl", "-f", "http://localhost:8080/actuator/health"]
      interval    = 30,
      timeout     = 5,
      retries     = 3,
      startPeriod = 30
    }
  }])
}

# ----------------------
# ECS Service
# ----------------------
resource "aws_ecs_service" "app" {
  name            = "${var.service_name}-service"
  cluster         = "${var.cluster_name}"
  task_definition = aws_ecs_task_definition.app.arn
  launch_type     = "FARGATE"
  desired_count   = 1

  deployment_controller {
    type = "ECS"
  }

  load_balancer {
    target_group_arn = aws_lb_target_group.blue.arn
    container_name   = "${var.service_name}-container"
    container_port   = 8080
  }

  network_configuration {
    subnets          = var.subnets_id
    security_groups  = [data.aws_security_group.security_group.id]
    assign_public_ip = false
  }
}
