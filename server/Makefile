# Запустить Spring Boot приложение
run:
	./mvnw spring-boot:run

# Собрать jar-файл
run-test:
	./mvnw test

# Собрать jar-файл
build:
	./mvnw clean package

# Поднять PostgreSQL и pgAdmin
docker-up:
	docker compose up -d

docker-ps:
	docker ps

# Остановить контейнеры
docker-down:
	docker compose down

# Перезапустить только БД
restart-db:
	docker compose restart db
