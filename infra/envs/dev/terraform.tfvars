service_name = "persons"
cluster_name = "gestao-veiculos-cluster"
vpc_id       = "vpc-06a723d80505a0dc3"
subnets_id   = ["subnet-012e4b031a352e929", "subnet-018e8d96aa9e98cf2"]
region       = "us-east-2"

# task_environment_vars = [
#   {
#     name  = "URL_ORDERS_SQS"
#     value = "production"
#   }
# ]

secrets = [
  {
    name      = "DB_URL"
    valueFrom = "arn:aws:secretsmanager:us-east-2:788796860262:secret:dev/database/productsDB/credentialsv3-5P4TGE:DB_URL::"
  },
  {
    name      = "DB_USERNAME"
    valueFrom = "arn:aws:secretsmanager:us-east-2:788796860262:secret:dev/database/productsDB/credentialsv3-5P4TGE:DB_USERNAME::"
  },
  {
    name      = "DB_PASSWORD"
    valueFrom = "arn:aws:secretsmanager:us-east-2:788796860262:secret:dev/database/productsDB/credentialsv3-5P4TGE:DB_PASSWORD::"
  }
]
