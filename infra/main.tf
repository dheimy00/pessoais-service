
data "aws_caller_identity" "current" {}
data "aws_region" "current" {}


module "ecs_fargate" {
  source = "git::https://github.com/dheimy00/modules-infra-ecs-fargate-aws.git?ref=v1.0.14"

  # Basic Configuration
  service_name = var.service_name
  cluster_name = var.cluster_name
  vpc_id       = var.vpc_id
  subnet_ids   = var.subnet_ids

  # Container Configuration
  container_name  = "${var.service_name}-container"
  container_image = "${data.aws_caller_identity.current.account_id}.dkr.ecr.${data.aws_region.current.name}.amazonaws.com/${var.service_name}-app:latest"
  container_port  = 8080
  host_port       = 8080
  listener_port   = 8080

  # Roles
  task_role_arn           = module.iamsr_module.role_arns["task-persons-role"]
  task_execution_role_arn = module.iamsr_module.role_arns["execution-persons-role"]

  # Task Configuration
  task_cpu                  = 256
  task_memory               = 512
  task_ephemeral_storage    = 21
  task_environment_vars     = var.task_environment_vars
  task_secrets              = var.task_secrets
  health_check_command      = ["CMD-SHELL", "curl -f http://localhost:8080/actuator/health || exit 1"]
  health_check_interval     = 30
  health_check_timeout      = 5
  health_check_retries      = 3
  health_check_start_period = 30

  # Network Configuration
  is_private_subnet = true
  vpc_cidr          = "10.0.0.0/16"
  alb_internal      = true
  assign_public_ip  = false

  # ALB Configuration
  health_check_protocol = "HTTP"
  health_check_path     = "/actuator/health"
  health_check_port     = "traffic-port"
  health_check_matcher  = "200"


  # Auto Scaling Configuration
  desired_count             = 2
  min_capacity              = 1
  max_capacity              = 3
  enable_cpu_autoscaling    = true
  enable_memory_autoscaling = true
  cpu_target_value          = 70
  memory_target_value       = 70
  scale_in_cooldown         = 300
  scale_out_cooldown        = 300

  tags = {
    Environment = "${var.environment}"
    Project     = "${var.project_name}"
  }
}

resource "aws_security_group" "rds" {
  name   = "${var.service_name}-rds-sg"
  vpc_id = var.vpc_id


  ingress {
    from_port       = 3306
    to_port         = 3306
    protocol        = "tcp"
    security_groups = [module.ecs_fargate.ecs_tasks_security_group_id]
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