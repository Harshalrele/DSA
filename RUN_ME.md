# Bober Clinic

Project folders:

- `backend` has the Spring Boot Java backend.
- `frontend` has the React frontend.

## Run the backend

Open one terminal:

```powershell
cd "C:\Users\harsh\OneDrive\Desktop\Programming\DSA"
.\start-backend.bat
```

The backend runs at:

```text
http://localhost:8080
```

## Run the frontend

Open a second terminal:

```powershell
cd "C:\Users\harsh\OneDrive\Desktop\Programming\DSA"
.\start-frontend.bat
```

The website runs at:

```text
http://localhost:3000
```

## What the app does

- Create a patient account.
- Create a doctor account with an expertise field.
- Doctor expertise is selected from a dropdown.
- Create a receptionist/admin account.
- Sign in with the saved email and password.
- Login returns a token used to protect backend requests.
- Patients can create appointments only from doctor availability slots.
- Patients add only the visit reason.
- Patients can see only their own appointments.
- Doctors can add availability slots.
- Only doctors can use doctor actions like adding availability and saving consultations.
- Patients can only choose available doctor time slots.
- The app warns when a time slot is occupied.
- Doctors can see all appointments assigned to them.
- Doctors can search patient names inside their assigned visits.
- Doctors can run a consultation, choose lab tests, physical tests, diagnosis, result of interview, and extra notes.
- Receptionist/Admin can check appointments, delete appointments, and view patient/doctor data.
- Patients cannot search or view other patients.

## Backend Structure

The backend is separated into:

- `controller` receives frontend/API requests.
- `service` contains the business logic.
- `repository` talks to the database.
- `model` contains database entity classes.
- `dto` contains request data classes.

## Where the data is saved

The H2 database file is stored here:

```text
backend\data\bober-clinic.mv.db
```

Do not delete the `backend\data` folder if you want accounts and passwords to stay saved.

Generated folders like `target`, `dist`, and `node_modules` are not source code. They may come back when you run the project.

