# DSA_REPORT_RA1_50_WORKING

This is a 50% working local version of the clinic project.

## What Works

- Backend starts with Java 17.
- No PostgreSQL is required.
- Credentials are stored in a local H2 file database at `Source/parrot-backend-main/parrot-backend-main/data`.
- Login works with preloaded users.
- New patient and doctor accounts can be created from the Register page.
- Frontend auth UI has been redesigned.

## Start Backend

Open a terminal in:

```powershell
Source\parrot-backend-main\parrot-backend-main
```

Then run:

```powershell
.\mvnw.cmd spring-boot:run
```

Backend URL:

```text
http://localhost:8080
```

H2 database console:

```text
http://localhost:8080/h2-console
```

H2 login:

```text
JDBC URL: jdbc:h2:file:./data/beaver-local
User: sa
Password: leave empty
```

## Start Frontend

Open a second terminal in:

```powershell
Source\parrot-frontend-main\parrot-frontend-main
```

Then run:

```powershell
npm install
npm run dev
```

Frontend URL:

```text
http://localhost:3000
```

## Test Login

Preloaded doctor:

```text
doctor1@email.com
doctor1
```

Preloaded patient:

```text
patient1@email.com
patient1
```

You can also use the Register page to create a new patient or doctor account.

