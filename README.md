
# Helpdesk – Spring Boot

Laboration 2 i Backendutveckling Java.

Ett enkelt ärendehanteringssystem där användare kan skapa och följa supportärenden.

## Tekniker

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Thymeleaf
- MySQL 8.4
- Docker Compose
- Maven
- JUnit och Mockito

## Funktioner

### USER
- Logga in och skapa nya ärenden.
- Se och öppna sina egna ärenden.
- Skriva kommentarer på sina egna ärenden.

### AGENT
- Se och öppna alla ärenden.
- Tilldela sig själv ärenden.
- Ändra ärendets status.
- Skriva kommentarer på ärenden.

### Ärendestatus
Ärenden kan ha följande status:

- NEW
- IN_PROGRESS
- WAITING_FOR_USER
- RESOLVED
- CLOSED

## Starta projektet

Projektet kräver Java 21, Maven och Docker.

### 1. Konfigurera databasen

Klona projektet och öppna det i IntelliJ IDEA.

Kopiera `.env.example` till `.env` i projektets rotmapp.

Ange egna värden för:
- `MYSQL_PASSWORD`
- `MYSQL_ROOT_PASSWORD`

### 2. Starta MySQL

Kör i projektets rotmapp:

```bash
docker compose up -d
```

MySQL körs i Docker på port 3307.

### 3. Starta Spring Boot

Öppna Run Configuration för `M777HelpdeskApplication` i IntelliJ.

Lägg till följande miljövariabler:

- `MYSQL_PASSWORD` – samma lösenord som i `.env`.
- `TEST_PASSWORD` – valfritt lösenord för testkontona.

Starta sedan `M777HelpdeskApplication`.

Applikationen nås på:

http://localhost:8080

## Testkonton

Vid första starten skapas tre testkonton automatiskt:

| Användarnamn | Roll |
|---|---|
| user | USER |
| user2 | USER |
| agent | AGENT |

Alla konton använder lösenordet som anges i `TEST_PASSWORD`.

## Tester

Projektet innehåller ett JUnit-test med Mockito som
kontrollerar att en användare inte kan kommentera
ett ärende som tillhör en annan användare.

För att köra alla tester via Git Bash måste MySQL vara igång
och följande miljövariabler vara angivna:

- `MYSQL_PASSWORD`
- `TEST_PASSWORD`

Kör sedan:

```bash
./mvnw clean test
```

## Stoppa databasen

```bash
docker compose down
```

Databasen använder en Docker-volym så att sparade ärenden,
kommentarer och användare finns kvar mellan omstarter.
