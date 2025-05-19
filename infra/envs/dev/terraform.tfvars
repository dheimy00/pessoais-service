service_name = "persons"
cluster_name = "gestao-veiculos-cluster"
vpc_id       = "vpc-07ed8eed7bf7c1a20"
subnets_id   = ["subnet-02137b69d1f0a5f0c", "subnet-00d824e3d44e27ccc"]
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
