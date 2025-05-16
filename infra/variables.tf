variable "aws_region" {
  description = "AWS region"
  type        = string
  default     = "us-west-2"
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

variable "container_name" {
  default = "app"
}

variable "create_nlb" {
  description = "Indica se o NLB deve ser criado ou não"
  type        = bool
  default     = true
}

variable "region" {
  description = "The AWS Region"
}

variable "vpc_id" {
  type        = string
  description = "The CIDR block of the vpc"
}

variable "subnets_id" {
  type        = list(string)
  description = "The CIDR block for the private subnet"
  default     = []
}

variable "task_environment_vars" {
  description = "Variáveis de ambiente para a task ECS"
  type = list(object({
    name  = string
    value = string
  }))
  default     = []
}

variable "secrets" {
  description = "Variáveis de ambiente para a task ECS"
  type = list(object({
    name      = string
    valueFrom = string
  }))
  default     = []
}

