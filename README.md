
# Helpdesk – Spring Boot

Laboration 2 i Backendutveckling Java.

Ett ärendehanteringssystem där användare kan skapa och följa supportärenden.

## Tekniker

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Thymeleaf
- MySQL 8.4
- Docker Compose
- Maven

## Starta projektet

### 1. Konfigurera databasen

Kopiera `.env.example` till `.env` och ange egna lösenord.

### 2. Starta MySQL

```bash
docker compose up -d
```

### 3. Starta Spring Boot

Öppna projektet i IntelliJ IDEA.

Lägg till miljövariabeln `MYSQL_PASSWORD` i
Run Configuration med samma lösenord som i `.env`.

Starta sedan `M777HelpdeskApplication`.

Applikationen nås på:

http://localhost:8080

## Stoppa databasen

```bash
docker compose down
```

Databasen använder en Docker-volym så att data
sparas mellan omstarter.




