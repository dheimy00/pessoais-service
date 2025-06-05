project_name = "gestao-veiculos"
service_name = "persons"
cluster_name = "gestao-veiculos-cluster"
vpc_id       = "vpc-07ed8eed7bf7c1a20"
subnet_ids   = ["subnet-02c7c3ab1730493b5", "subnet-0187eeb0c0d494171"]

# task_environment_vars = [
#   {
#     name  = "URL_ORDERS_SQS"
#     value = "production"
#   }
# ]

task_secrets = [
  {
    name      = "DB_URL"
    valueFrom = "arn:aws:secretsmanager:us-east-2:788796860262:secret:dev/database/productsDB/credentialsv1-Q0IYhU:DB_URL::"
  },
  {
    name      = "DB_USERNAME"
    valueFrom = "arn:aws:secretsmanager:us-east-2:788796860262:secret:dev/database/productsDB/credentialsv1-Q0IYhU:DB_USERNAME::"
  },
  {
    name      = "DB_PASSWORD"
    valueFrom = "arn:aws:secretsmanager:us-east-2:788796860262:secret:dev/database/productsDB/credentialsv1-Q0IYhU:DB_PASSWORD::"
  }
]

