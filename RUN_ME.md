# Bober Clinic

This project is now split into two simple source folders :

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
- Sign in with the saved email and password.
- Patients can create appointments with doctors.
- Patients select the appointment date and time.
- Patients add only the visit reason.
- Doctors can see appointed patients and patient lookup.
- Doctors can add test notes for appointments.

## Where the data is saved

The H2 database file is stored here:

```text
backend\data\bober-clinic.mv.db
```

Do not delete the `backend\data` folder if you want accounts and passwords to stay saved.

Generated folders like `target`, `dist`, and `node_modules` are not source code. They may come back when you run the project.

