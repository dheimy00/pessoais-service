module "iamsr_module" {
  source = "git::https://github.com/dheimy00/modules-infra-iamsr-aws.git?ref=v1.0.7"
  iam_policies = [
    {
      name     = "policy-task-persons.json"
      document = "iamsr/policy/policy-task-fargate-persons.json"
      path     = "/"
    },
    {
      name     = "policy-execution-persons.json"
      document = "iamsr/policy/policy-execution-fargate-persons.json"
      path     = "/"
    }
  ]
  iam_roles = [
    {
      name                  = "execution-persons-role"
      trust_policy_document = "iamsr/trust/ecs-fargate-persons.json"
      attached_policies = [
        "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy",
        "policy-execution-persons.json"
      ]
    },
    {
      name                  = "task-persons-role"
      trust_policy_document = "iamsr/trust/ecs-fargate-persons.json"
      attached_policies = [
        "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy",
        "policy-task-persons.json"
      ]
    }
  ]
}