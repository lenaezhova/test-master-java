include .env
export

run:
	./mvnw spring-boot:run

run-test:
	source .env && ./mvnw test

build:
	./mvnw clean package

docker-restart:
	docker compose down
	docker compose build
	docker compose up -d

docker-build:
	docker compose build

docker-up:
	docker compose up -d

docker-ps:
	docker ps

docker-down:
	docker compose down

liquibase-update:
	source .env && ./mvnw liquibase:update

liquibase-clear-checksums:
	source .env && ./mvnw liquibase:clearCheckSums

# UPDATE DATABASECHANGELOGLOCK SET LOCKED = false, LOCKGRANTED = NULL, LOCKEDBY = NULL WHERE ID = 1;
liquibase-drop-all:
	source .env && ./mvnw liquibase:dropAll

liquibase-clear-check-sums:
	source .env && ./mvnw liquibase:clearCheckSums

