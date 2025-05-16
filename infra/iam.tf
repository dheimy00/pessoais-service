# ----------------------
# ECS & IAM & SQS
# ----------------------
resource "aws_iam_role" "ecs_task_execution_role" {
  name = "${var.service_name}-ecs-execution"
  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [{
      Effect    = "Allow",
      Principal = { Service = "ecs-tasks.amazonaws.com" },
      Action    = "sts:AssumeRole"
    }]
  })
}

resource "aws_iam_role" "ecs_task_role" {
  name = "${var.service_name}-ecs-task-role"
  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [{
      Action    = "sts:AssumeRole",
      Effect    = "Allow",
      Principal = { Service = "ecs-tasks.amazonaws.com" }
    }]
  })
}

resource "aws_iam_role_policy_attachment" "ecs_task_exec_attach" {
  role       = aws_iam_role.ecs_task_execution_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

resource "aws_iam_role_policy_attachment" "ecs_task_secrets" {
  role       = aws_iam_role.ecs_task_role.name
  policy_arn = "arn:aws:iam::aws:policy/SecretsManagerReadWrite"
}

resource "aws_iam_role_policy" "policy_permission_sqs" {
  name = "${var.service_name}-sqs-access"
  role = aws_iam_role.ecs_task_role.id

  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [{
      Effect = "Allow",
      Action = [
        "sqs:SendMessage",
        "sqs:ReceiveMessage",
        "sqs:DeleteMessage",
        "sqs:GetQueueAttributes",
        "sqs:GetQueueUrl"        
      ],
      Resource = "*"
      }
    ]
  })
}


resource "aws_iam_role_policy" "policy_permission_secrets" {
  name = "${var.service_name}-exec-access"
  role = aws_iam_role.ecs_task_execution_role.id

  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [{
      Effect = "Allow",
      Action = [
        "secretsmanager:GetSecretValue",
        "secretsmanager:DescribeSecret"
      ],
      Resource = "*"
      },
      {
        Effect   = "Allow"
        Action   = "ec2:DescribeNetworkInterfaces"
        Resource = "*"
      },
      {
        Effect   = "Allow"
        Action   = "ec2:DetachNetworkInterface"
        Resource = "*"
    }]
  })
}

