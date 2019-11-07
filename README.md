# aws-demo
Prof of concept to work with DynamoDB with AWS local stack 


copy https://github.com/localstack/localstack/blob/master/docker-compose.yml  to you project root 

navigate to the docker compose and run the following

TMPDIR=/private$TMPDIR SERVICES=dynamodb docker-compose up
