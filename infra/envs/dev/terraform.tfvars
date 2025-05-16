service_name = "persons"
cluster_name = "gestao-veiculos"
vpc_id       = "vpc-08b65188240431fd6"
subnets_id   = ["subnet-0c8ff33e4f5e757a5", "subnet-0c25f44621aa6a65b", "subnet-00554fc8636d71301"]
region       = "us-east-2"


# task_environment_vars = [
#   {
#     name  = "URL_ORDERS_SQS"
#     value = "production"
#   }
# ]

# secrets = [
#   {
#     name      = "DB_URL"
#     valueFrom = "arn:aws:secretsmanager:us-east-2:788796860262:secret:dev/database/productsDB/credentialsv3-5P4TGE:DB_URL::"
#   },
#   {
#     name      = "DB_USERNAME"
#     valueFrom = "arn:aws:secretsmanager:us-east-2:788796860262:secret:dev/database/productsDB/credentialsv3-5P4TGE:DB_USERNAME::"
#   },
#   {
#     name      = "DB_PASSWORD"
#     valueFrom = "arn:aws:secretsmanager:us-east-2:788796860262:secret:dev/database/productsDB/credentialsv3-5P4TGE:DB_PASSWORD::"
#   }
# ]
