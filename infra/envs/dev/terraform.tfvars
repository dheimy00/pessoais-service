service_name = "persons"
cluster_name = "gestao-veiculos-cluster"
vpc_id       = "vpc-099fefea06e3c4413"
subnets_id   = ["subnet-0585a9f9a6a21cb96", "subnet-03fb17cf32b4ae63e"]
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
