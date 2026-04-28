// Icons used in the buttons and headings.
import { CalendarPlus, LogOut, Stethoscope, UserPlus } from "lucide-react";

// React hooks used to store page state and load data.
import { useEffect, useState } from "react";

// This is the backend URL.
const API_URL = "http://localhost:8080/api";

// This is the empty register form.
const emptyRegisterForm = {
  email: "",
  password: "",
  firstName: "",
  lastName: "",
  nationalId: "",
  sex: "Female",
  role: "PATIENT",
  insuranceId: "",
  npwzId: "",
  specialization: ""
};

// Doctor expertise options shown when creating a doctor account.
const specializations = [
  "Cardiology",
  "Dermatology",
  "Family medicine",
  "Neurology",
  "Orthopedics",
  "Pediatrics"
];

// Lab test options the doctor can choose for an appointment.
const labTestOptions = [
  "Blood test",
  "Urine test",
  "X-ray",
  "MRI",
  "ECG",
  "Ultrasound"
];

// This is the main frontend component.
function App() {
  // page decides if we show login, register, or dashboard.
  const [page, setPage] = useState("login");

  // user stores the logged-in account.
  const [user, setUser] = useState(null);

  // message stores success or error text.
  const [message, setMessage] = useState("");

  // loginForm stores the login input values.
  const [loginForm, setLoginForm] = useState({ email: "", password: "" });

  // registerForm stores the create account input values.
  const [registerForm, setRegisterForm] = useState(emptyRegisterForm);

  // doctors stores all doctor accounts from the backend.
  const [doctors, setDoctors] = useState([]);

  // appointments stores appointments for the logged-in patient.
  const [appointments, setAppointments] = useState([]);

  // patients stores patient accounts for the doctor dashboard.
  const [patients, setPatients] = useState([]);

  // doctorForms stores appointment changes before the doctor saves them.
  const [doctorForms, setDoctorForms] = useState({});

  // appointmentForm stores the selected doctor and date.
  const [appointmentForm, setAppointmentForm] = useState({
    doctorEmail: "",
    appointmentDate: "",
    appointmentTime: "",
    visitReason: ""
  });

  // This runs when the logged-in user changes.
  useEffect(() => {
    // Only load dashboard data after login.
    if (user) {
      loadDoctors();
      loadAppointments(user.email);
      if (user.role === "DOCTOR") {
        loadPatients();
      }
    }
  }, [user]);

  // This helper sends JSON to the backend and reads JSON back.
  async function sendJson(path, body) {
    // fetch makes the HTTP request to Spring Boot.
    const response = await fetch(`${API_URL}${path}`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(body)
    });

    // If the backend returns an error, show it on the page.
    if (!response.ok) {
      const text = await response.text();
      throw new Error(text || "Request failed");
    }

    // Return the backend JSON response.
    return response.json();
  }

  // This loads all doctors from the backend.
  async function loadDoctors() {
    // GET /api/doctors returns doctor accounts.
    const response = await fetch(`${API_URL}/doctors`);
    // Save the doctor list in React state.
    setDoctors(await response.json());
  }

  // This loads all patients for the doctor dashboard.
  async function loadPatients() {
    // GET /api/patients returns patient accounts.
    const response = await fetch(`${API_URL}/patients`);
    // Save the patient list in React state.
    setPatients(await response.json());
  }

  // This loads appointments for one patient.
  async function loadAppointments(patientEmail) {
    // Doctors load appointments by doctor email, patients load by patient email.
    const query =
      user?.role === "DOCTOR"
        ? `doctorEmail=${encodeURIComponent(patientEmail)}`
        : `patientEmail=${encodeURIComponent(patientEmail)}`;

    // GET /api/appointments returns appointments for the current user.
    const response = await fetch(`${API_URL}/appointments?${query}`);
    // Read appointment data from the backend.
    const loadedAppointments = await response.json();
    // Save the appointment list in React state.
    setAppointments(loadedAppointments);
    // Fill doctor edit boxes with saved backend values.
    setDoctorForms(
      loadedAppointments.reduce((forms, appointment) => {
        forms[appointment.id] = {
          status: appointment.status || "Scheduled",
          diagnosis: appointment.diagnosis || "",
          labTests: appointment.labTests || "",
          testNotes: appointment.testNotes || ""
        };
        return forms;
      }, {})
    );
  }

  // This handles the login form.
  async function handleLogin(event) {
    // Stop the browser from refreshing the page.
    event.preventDefault();
    try {
      // Send email and password to the backend.
      const loggedInUser = await sendJson("/login", loginForm);
      // Store the logged-in user.
      setUser(loggedInUser);
      // Move to the dashboard.
      setPage("dashboard");
      // Clear old messages.
      setMessage("");
    } catch {
      // Show a simple login error.
      setMessage("Bad credentials. Check email and password.");
    }
  }

  // This handles the create account form.
  async function handleRegister(event) {
    // Stop the browser from refreshing the page.
    event.preventDefault();
    try {
      // Send the new account to the backend.
      await sendJson("/register", registerForm);
      // Return to login after creating the account.
      setPage("login");
      // Reset the register form.
      setRegisterForm(emptyRegisterForm);
      // Tell the user what happened.
      setMessage("Account created. You can sign in now.");
    } catch {
      // Show a simple registration error.
      setMessage("Could not create account. Use a new email.");
    }
  }

  // This handles the appointment form.
  async function handleAppointment(event) {
    // Stop the browser from refreshing the page.
    event.preventDefault();
    try {
      // Send appointment details to the backend.
      await sendJson("/appointments", {
        patientEmail: user.email,
        doctorEmail: appointmentForm.doctorEmail,
        dateTime: `${appointmentForm.appointmentDate} ${appointmentForm.appointmentTime}`,
        visitReason: appointmentForm.visitReason
      });
      // Clear the appointment form.
      setAppointmentForm({
        doctorEmail: "",
        appointmentDate: "",
        appointmentTime: "",
        visitReason: ""
      });
      // Reload appointments from the backend.
      await loadAppointments(user.email);
      // Show success text.
      setMessage("Appointment created.");
    } catch {
      // Show a simple appointment error.
      setMessage("Could not create appointment.");
    }
  }

  // This lets a doctor save appointment changes.
  async function saveDoctorUpdate(appointmentId) {
    try {
      // Send the doctor's selected status, lab tests, diagnosis, and notes to the backend.
      await sendJson(`/appointments/${appointmentId}/doctor-update`, doctorForms[appointmentId]);
      // Reload the doctor's appointments after saving.
      await loadAppointments(user.email);
      // Show success text.
      setMessage("Appointment updated.");
    } catch {
      // Show a simple save error.
      setMessage("Could not update appointment.");
    }
  }

  // This logs the user out.
  function logout() {
    // Remove the logged-in user.
    setUser(null);
    // Go back to login.
    setPage("login");
    // Clear messages.
    setMessage("");
  }

  // This updates the login form when the user types.
  function updateLogin(field, value) {
    setLoginForm({ ...loginForm, [field]: value });
  }

  // This updates the register form when the user types.
  function updateRegister(field, value) {
    setRegisterForm({ ...registerForm, [field]: value });
  }

  // This updates the appointment form when the user types.
  function updateAppointment(field, value) {
    setAppointmentForm({ ...appointmentForm, [field]: value });
  }

  // This updates one doctor's appointment-edit form.
  function updateDoctorForm(appointmentId, field, value) {
    setDoctorForms({
      ...doctorForms,
      [appointmentId]: {
        ...doctorForms[appointmentId],
        [field]: value
      }
    });
  }

  // This turns a lab test checkbox on or off.
  function toggleLabTest(appointmentId, labTest) {
    const currentValue = doctorForms[appointmentId]?.labTests || "";
    const selectedTests = currentValue ? currentValue.split(", ") : [];
    const nextTests = selectedTests.includes(labTest)
      ? selectedTests.filter((test) => test !== labTest)
      : [...selectedTests, labTest];
    updateDoctorForm(appointmentId, "labTests", nextTests.join(", "));
  }

  // This shows the login page.
  if (page === "login") {
    return (
      <main className="screen">
        <section className="brand-card">
          <Stethoscope size={48} />
          <h1>Bober Clinic</h1>
          <p>Simple clinic management system</p>
          <div className="team">
            <strong>Done by</strong>
            <span>Harshal Rele</span>
            <span>Ruth Zhou</span>
            <span> Kabarega Jabo Prince</span>
            <span>Solomon Orviri</span>
            <span>Navneethraj Vadakkuvedu Kabirdas</span>
            <span>Hugo Lapica</span>
          </div>
        </section>

        <section className="form-card">
          <h2>Sign in</h2>
          {message && <p className="message">{message}</p>}
          <form onSubmit={handleLogin}>
            <label>Email</label>
            <input value={loginForm.email} onChange={(e) => updateLogin("email", e.target.value)} />
            <label>Password</label>
            <input type="password" value={loginForm.password} onChange={(e) => updateLogin("password", e.target.value)} />
            <button type="submit">Sign in</button>
          </form>
          <button className="secondary" onClick={() => setPage("register")}>
            <UserPlus size={18} />
            Create patient or doctor account
          </button>
        </section>
      </main>
    );
  }

  // This shows the register page.
  if (page === "register") {
    return (
      <main className="screen">
        <section className="brand-card">
          <UserPlus size={48} />
          <h1>Create account</h1>
          <p>Create a patient or doctor account for Bober Clinic.</p>
        </section>

        <section className="form-card wide">
          <h2>New account</h2>
          {message && <p className="message">{message}</p>}
          <form onSubmit={handleRegister}>
            <div className="grid">
              <input placeholder="First name" value={registerForm.firstName} onChange={(e) => updateRegister("firstName", e.target.value)} />
              <input placeholder="Last name" value={registerForm.lastName} onChange={(e) => updateRegister("lastName", e.target.value)} />
              <input placeholder="Email" value={registerForm.email} onChange={(e) => updateRegister("email", e.target.value)} />
              <input placeholder="Password" type="password" value={registerForm.password} onChange={(e) => updateRegister("password", e.target.value)} />
              <input placeholder="National ID" value={registerForm.nationalId} onChange={(e) => updateRegister("nationalId", e.target.value)} />
              <select value={registerForm.sex} onChange={(e) => updateRegister("sex", e.target.value)}>
                <option>Female</option>
                <option>Male</option>
                <option>Other</option>
              </select>
              <select value={registerForm.role} onChange={(e) => updateRegister("role", e.target.value)}>
                <option value="PATIENT">Patient</option>
                <option value="DOCTOR">Doctor</option>
              </select>
              {registerForm.role === "PATIENT" ? (
                <input placeholder="Insurance ID" value={registerForm.insuranceId} onChange={(e) => updateRegister("insuranceId", e.target.value)} />
              ) : (
                <input placeholder="NPWZ ID" value={registerForm.npwzId} onChange={(e) => updateRegister("npwzId", e.target.value)} />
              )}
            </div>
            {registerForm.role === "DOCTOR" && (
              <select value={registerForm.specialization} onChange={(e) => updateRegister("specialization", e.target.value)}>
                <option value="">Choose specialization</option>
                {specializations.map((specialization) => (
                  <option key={specialization} value={specialization}>
                    {specialization}
                  </option>
                ))}
              </select>
            )}
            <button type="submit">Create account</button>
          </form>
          <button className="secondary" onClick={() => setPage("login")}>Back to sign in</button>
        </section>
      </main>
    );
  }

  // This shows the dashboard after login.
  return (
    <main className="dashboard">
      <header>
        <div>
          <h1>Bober Clinic</h1>
          <p>{user.firstName} {user.lastName} - {user.role}</p>
        </div>
        <button className="logout" onClick={logout}>
          <LogOut size={18} />
          Logout
        </button>
      </header>

      {message && <p className="message">{message}</p>}

      {user.role === "PATIENT" ? (
        <section className="dashboard-grid">
          <div className="panel">
            <h2><CalendarPlus size={22} /> Create appointment</h2>
            <form onSubmit={handleAppointment}>
              <label>Doctor</label>
              <select value={appointmentForm.doctorEmail} onChange={(e) => updateAppointment("doctorEmail", e.target.value)}>
                <option value="">Choose a doctor</option>
                {doctors.map((doctor) => (
                  <option key={doctor.id} value={doctor.email}>
                    Dr. {doctor.firstName} {doctor.lastName} - {doctor.specialization}
                  </option>
                ))}
              </select>
              <label>Date</label>
              <input type="date" value={appointmentForm.appointmentDate} onChange={(e) => updateAppointment("appointmentDate", e.target.value)} />
              <label>Time</label>
              <input type="time" value={appointmentForm.appointmentTime} onChange={(e) => updateAppointment("appointmentTime", e.target.value)} />
              <label>Visit reason</label>
              <input placeholder="Example: fever and headache" value={appointmentForm.visitReason} onChange={(e) => updateAppointment("visitReason", e.target.value)} />
              <button type="submit">Create appointment</button>
            </form>
          </div>

          <div className="panel">
            <h2>Your appointments</h2>
            {appointments.length === 0 && <p>No appointments yet.</p>}
            {appointments.map((appointment) => (
              <div className="appointment" key={appointment.id}>
                <strong>Dr. {appointment.doctorName}</strong>
                <span>Specialization: {appointment.doctorSpecialization}</span>
                <span>Date and time: {appointment.dateTime}</span>
                <span>Reason: {appointment.visitReason || "Not added"}</span>
                <span>Status: {appointment.status || "Scheduled"}</span>
                <span>Lab tests: {appointment.labTests || "Not added yet"}</span>
                <span>Diagnosis: {appointment.diagnosis || "Not added yet"}</span>
                <span>Doctor notes: {appointment.testNotes || "Not added yet"}</span>
              </div>
            ))}
          </div>
        </section>
      ) : (
        <section className="dashboard-grid">
          <div className="panel">
            <h2>Doctor profile</h2>
            <p><strong>Expertise:</strong> {user.specialization}</p>
            <p><strong>NPWZ ID:</strong> {user.npwzId}</p>
            <p>Patients can choose this doctor when creating an appointment.</p>
          </div>

          <div className="panel">
            <h2>Appointed patients</h2>
            {appointments.length === 0 && <p>No patients booked yet.</p>}
            {appointments.map((appointment) => (
              <div className="appointment" key={appointment.id}>
                <strong>{appointment.patientName}</strong>
                <span>Insurance ID: {appointment.patientInsuranceId}</span>
                <span>Date: {appointment.dateTime}</span>
                <span>Reason: {appointment.visitReason || "Not added"}</span>
                <label>Status</label>
                <select
                  value={doctorForms[appointment.id]?.status || "Scheduled"}
                  onChange={(e) => updateDoctorForm(appointment.id, "status", e.target.value)}
                >
                  <option>Scheduled</option>
                  <option>In progress</option>
                  <option>Completed</option>
                </select>
                <label>Lab tests</label>
                <div className="checkbox-grid">
                  {labTestOptions.map((labTest) => (
                    <label className="checkbox-row" key={labTest}>
                      <input
                        type="checkbox"
                        checked={(doctorForms[appointment.id]?.labTests || "").split(", ").includes(labTest)}
                        onChange={() => toggleLabTest(appointment.id, labTest)}
                      />
                      {labTest}
                    </label>
                  ))}
                </div>
                <label>Diagnosis</label>
                <textarea
                  placeholder="Example: migraine symptoms"
                  value={doctorForms[appointment.id]?.diagnosis || ""}
                  onChange={(e) => updateDoctorForm(appointment.id, "diagnosis", e.target.value)}
                />
                <label>Doctor notes / test results</label>
                <textarea
                  placeholder="Example: blood test requested or result notes"
                  value={doctorForms[appointment.id]?.testNotes || ""}
                  onChange={(e) => updateDoctorForm(appointment.id, "testNotes", e.target.value)}
                />
                <button type="button" onClick={() => saveDoctorUpdate(appointment.id)}>
                  Save appointment changes
                </button>
              </div>
            ))}
          </div>

          <div className="panel full-width">
            <h2>Patient lookup</h2>
            {patients.length === 0 && <p>No patient accounts created yet.</p>}
            {patients.map((patient) => (
              <div className="appointment" key={patient.id}>
                <strong>{patient.firstName} {patient.lastName}</strong>
                <span>Email: {patient.email}</span>
                <span>Insurance ID: {patient.insuranceId}</span>
                <span>National ID: {patient.nationalId}</span>
              </div>
            ))}
          </div>
        </section>
      )}
    </main>
  );
}

// This exports App so main.jsx can display it.
export default App;
