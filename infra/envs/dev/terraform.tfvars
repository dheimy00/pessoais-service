project_name = "gestao-veiculos"
service_name = "persons"
cluster_name = "gestao-veiculos-cluster"
vpc_id       = "vpc-0d80ebcc4b26e7568"
subnet_ids   = ["subnet-0688c035a86d695c0", "subnet-07bab2db6ae6c899e"]



task_secrets = [
  {
    name      = "DB_URL"
    valueFrom = "arn:aws:secretsmanager:us-east-2:788796860262:secret:dev/database/personsDB/credentialsv4-Cy3prs:DB_URL::"
  },
  {
    name      = "DB_USERNAME"
    valueFrom = "arn:aws:secretsmanager:us-east-2:788796860262:secret:dev/database/personsDB/credentialsv4-Cy3prs:DB_USERNAME::"
  },
  {
    name      = "DB_PASSWORD"
    valueFrom = "arn:aws:secretsmanager:us-east-2:788796860262:secret:dev/database/personsDB/credentialsv4-Cy3prs:DB_PASSWORD::"
  }
]

