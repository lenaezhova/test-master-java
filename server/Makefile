include .env
export

run:
	./mvnw spring-boot:run

run-test:
	source .env && ./mvnw test

build:
	./mvnw clean package

docker-up:
	docker compose up -d

docker-ps:
	docker ps

docker-down:
	docker compose down

restart-db:
	docker compose restart db

liquibase-update:
	source .env && ./mvnw liquibase:update

liquibase-clear-checksums:
	source .env && ./mvnw liquibase:clearCheckSums

liquibase-drop-all:
	source .env && ./mvnw liquibase:dropAll

