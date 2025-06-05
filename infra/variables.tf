variable "aws_region" {
  description = "AWS region"
  type        = string
  default     = "us-east-2"
}

variable "project_name" {
  description = "The name of project to be used for tagging"
  type        = string
}

variable "service_name" {
  description = "Service name to be used for tagging"
  type        = string
}

variable "cluster_name" {
  description = "Cluster name to be used for tagging"
  type        = string
}

variable "environment" {
  description = "Environment (dev/staging/prod)"
  type        = string
  default     = "dev"
}

variable "region" {
  description = "The AWS Region"
  type        = string
  default     = "us-east-2"
}

variable "vpc_id" {
  type        = string
  description = "The CIDR block of the vpc"
}

variable "subnet_ids" {
  type        = list(string)
  description = "The CIDR block for the private subnet"
  default     = []
}

variable "task_environment_vars" {
  type = list(object({
    name  = string
    value = string
  }))
  default = []  
}

variable "task_secrets" {
  description = "Variáveis de ambiente para a task ECS"
  type = list(object({
    name      = string
    valueFrom = string
  }))
  default = []
}
