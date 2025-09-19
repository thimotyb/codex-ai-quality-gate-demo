# Makefile per AI Quality Gate Demo con Jenkins in Docker

.PHONY: build up down logs clean

build:
	docker compose build

up:
	docker compose up -d

down:
	docker compose down

logs:
	docker compose logs -f

clean:
	docker compose down -v --rmi all --remove-orphans
