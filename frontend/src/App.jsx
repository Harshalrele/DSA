// Icons used in the buttons and page headings.
import { CalendarPlus, LogOut, Stethoscope, UserPlus } from "lucide-react";

// React tools for page state, loading data, and filtering lists.
import { useEffect, useMemo, useState } from "react";

// Backend API address.
const API_URL = "http://localhost:8080/api";

// Options shown when a doctor account is created.
const specializations = [
  "Cardiology",
  "Dermatology",
  "Family medicine",
  "Neurology",
  "Orthopedics",
  "Pediatrics"
];

// Standard lab tests for a consultation.
const baseLabTests = [
  "Blood test",
  "Urine test",
  "X-ray",
  "MRI",
  "ECG",
  "Ultrasound",
  "CT scan",
  "Glucose test",
  "Cholesterol test"
];

// Extra choices for specialist doctors.
const specialistTests = ["USG", "Specialist examination", "Doppler scan", "Echo test"];

// Physical tests shown as checkboxes.
const physicalTests = [
  "Blood pressure",
  "Temperature",
  "Pulse",
  "Weight",
  "Height",
  "Lung check",
  "Abdomen check",
  "Reflex check"
];

// Empty values for the register form.
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

// Default dashboard filters.
const defaultFilters = {
  search: "",
  date: "",
  status: "",
  scope: "My visits"
};

function App() {
  // Current page: login, register, or dashboard.
  const [page, setPage] = useState("login");

  // Signed-in user from the backend.
  const [user, setUser] = useState(null);

  // Login token for protected backend requests.
  const [authToken, setAuthToken] = useState("");

  // Success or error message shown on the page.
  const [message, setMessage] = useState("");

  // Sign-in form fields.
  const [loginForm, setLoginForm] = useState({ email: "", password: "" });

  // Create-account form fields.
  const [registerForm, setRegisterForm] = useState(emptyRegisterForm);

  // Data loaded from the backend.
  const [doctors, setDoctors] = useState([]);
  const [patients, setPatients] = useState([]);
  const [appointments, setAppointments] = useState([]);
  const [availability, setAvailability] = useState([]);

  // Doctor consultation forms, grouped by appointment id.
  const [doctorForms, setDoctorForms] = useState({});

  // Dashboard filter fields.
  const [filters, setFilters] = useState(defaultFilters);

  // Doctor availability form fields.
  const [availabilityForm, setAvailabilityForm] = useState({
    date: "",
    time: ""
  });

  // Patient booking form fields.
  const [appointmentForm, setAppointmentForm] = useState({
    slot: "",
    visitReason: ""
  });

  // After login, load the dashboard data for that role.
  useEffect(() => {
    if (user && authToken) {
      loadDoctors();
      loadAvailability();
      loadAppointments();
      if (user.role !== "PATIENT") {
        loadPatients();
      }
    }
  }, [user, authToken]);

  // Shared fetch helper. It adds the login token after sign-in.
  async function api(path, options = {}) {
    const headers = { ...(options.headers || {}) };
    if (authToken) {
      headers.Authorization = `Bearer ${authToken}`;
    }

    const response = await fetch(`${API_URL}${path}`, {
      ...options,
      headers
    });
    if (!response.ok) {
      const text = await response.text();
      throw new Error(path === "/appointments" && response.status === 400
        ? "This time slot is already occupied."
        : text.includes("This time slot is already occupied")
        ? "This time slot is already occupied."
        : "Request failed");
    }
    if (response.status === 204) {
      return null;
    }
    return response.json();
  }

  // Sends JSON to the backend.
  async function sendJson(path, body) {
    return api(path, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(body)
    });
  }

  // Loads doctors for booking and admin views.
  async function loadDoctors() {
    setDoctors(await api("/doctors"));
  }

  // Loads patients only for staff users.
  async function loadPatients() {
    setPatients(await api("/patients"));
  }

  // Loads open doctor appointment times.
  async function loadAvailability() {
    setAvailability(await api("/availability?onlyOpen=true"));
  }

  // Loads only the appointments allowed for the logged-in role.
  async function loadAppointments() {
    const loadedAppointments = await api("/appointments");
    setAppointments(loadedAppointments);
    setDoctorForms(loadedAppointments.reduce((forms, appointment) => {
      forms[appointment.id] = {
        status: appointment.status || "Scheduled",
        labTests: appointment.labTests || "",
        physicalTests: appointment.physicalTests || "",
        diagnosis: appointment.diagnosis || "",
        resultOfInterview: appointment.resultOfInterview || "",
        notes: appointment.notes || ""
      };
      return forms;
    }, {}));
  }

  // Signs in and opens the dashboard.
  async function handleLogin(event) {
    event.preventDefault();
    try {
      const loginResult = await sendJson("/login", loginForm);
      setAuthToken(loginResult.token);
      setUser(loginResult.user);
      setPage("dashboard");
      setMessage("");
      setFilters(defaultFilters);
    } catch {
      setMessage("Bad credentials. Check email and password.");
    }
  }

  // Creates a patient, doctor, or receptionist/admin account.
  async function handleRegister(event) {
    event.preventDefault();
    try {
      await sendJson("/register", registerForm);
      setRegisterForm(emptyRegisterForm);
      setPage("login");
      setMessage("Account created. You can sign in now.");
    } catch {
      setMessage("Could not create account. Use a new email.");
    }
  }

  // Adds one open appointment time for a doctor.
  async function addAvailability(event) {
    event.preventDefault();
    try {
      await sendJson("/availability", {
        doctorEmail: user.email,
        dateTime: `${availabilityForm.date} ${availabilityForm.time}`
      });
      setAvailabilityForm({ date: "", time: "" });
      await loadAvailability();
      setMessage("Availability added.");
    } catch {
      setMessage("Could not add availability.");
    }
  }

  // Books one open doctor time slot for the patient.
  async function createAppointment(event) {
    event.preventDefault();
    const selectedSlot = availability.find((slot) => String(slot.id) === appointmentForm.slot);
    if (!selectedSlot) {
      setMessage("Choose an available time slot.");
      return;
    }
    try {
      await sendJson("/appointments", {
        patientEmail: user.email,
        doctorEmail: selectedSlot.doctorEmail,
        dateTime: selectedSlot.dateTime,
        visitReason: appointmentForm.visitReason
      });
      setAppointmentForm({ slot: "", visitReason: "" });
      await loadAvailability();
      await loadAppointments();
      setMessage("Appointment created.");
    } catch (error) {
      setMessage(error.message);
    }
  }

  // Saves doctor consultation changes.
  async function saveDoctorUpdate(appointmentId) {
    try {
      await sendJson(`/appointments/${appointmentId}/doctor-update`, doctorForms[appointmentId]);
      await loadAppointments();
      setMessage("Appointment updated.");
    } catch {
      setMessage("Could not update appointment.");
    }
  }

  // Deletes an appointment for the receptionist/admin.
  async function deleteAppointment(appointmentId) {
    try {
      await api(`/appointments/${appointmentId}`, { method: "DELETE" });
      await loadAppointments();
      await loadAvailability();
      setMessage("Appointment deleted.");
    } catch {
      setMessage("Could not delete appointment.");
    }
  }

  // Logs out and returns to the sign-in page.
  function logout() {
    setUser(null);
    setAuthToken("");
    setPage("login");
    setMessage("");
    setFilters(defaultFilters);
  }

  // Updates one doctor form field.
  function updateDoctorForm(appointmentId, field, value) {
    setDoctorForms({
      ...doctorForms,
      [appointmentId]: {
        ...doctorForms[appointmentId],
        [field]: value
      }
    });
  }

  // Adds or removes one checkbox value from a text list.
  function toggleListValue(appointmentId, field, value) {
    const currentValue = doctorForms[appointmentId]?.[field] || "";
    const selectedValues = currentValue ? currentValue.split(", ") : [];
    const nextValues = selectedValues.includes(value)
      ? selectedValues.filter((item) => item !== value)
      : [...selectedValues, value];
    updateDoctorForm(appointmentId, field, nextValues.join(", "));
  }

  // Saves selected lab tests from the multi-select dropdown.
  function updateMultiSelect(appointmentId, field, selectedOptions) {
    const values = Array.from(selectedOptions).map((option) => option.value);
    updateDoctorForm(appointmentId, field, values.join(", "));
  }

  // Dashboard appointment filtering.
  const shownAppointments = useMemo(() => {
    const today = new Date().toISOString().slice(0, 10);
    return appointments.filter((appointment) => {
      const text = `${appointment.patientName} ${appointment.doctorName}`.toLowerCase();
      const matchesSearch = user?.role === "PATIENT" || text.includes(filters.search.toLowerCase());
      const matchesDate = !filters.date || appointment.dateTime.startsWith(filters.date);
      const matchesStatus = !filters.status || appointment.status === filters.status;
      const matchesScope =
        filters.scope === "All appointments" ||
        filters.scope === "Today schedule" && appointment.dateTime.startsWith(today) ||
        filters.scope === "My visits";
      return matchesSearch && matchesDate && matchesStatus && matchesScope;
    });
  }, [appointments, filters, user?.role]);

  // Staff patient search.
  const visiblePatients = useMemo(() => {
    const search = filters.search.toLowerCase();
    return patients.filter((patient) =>
      `${patient.firstName} ${patient.lastName}`.toLowerCase().includes(search)
    );
  }, [patients, filters.search]);

  // Specialists get extra lab test options.
  const currentLabTests = user?.role === "DOCTOR" && user.specialization !== "Family medicine"
    ? [...baseLabTests, ...specialistTests]
    : baseLabTests;

  // Sign-in page.
  if (page === "login") {
    return (
      <main className="screen">
        <section className="brand-card">
          <Stethoscope size={48} />
          <h1>Bober Clinic</h1>
          <p>Clinic management system</p>
          <div className="team">
            <strong>Done by</strong>
            <span>Harshal Rele</span>
            <span>Ruth Zhou</span>
            <span>Kabarega Jabo Prince</span>
            <span>Solomon Orviri</span>
            <span>Navneethraj Vadakkuvedu Kabirdas</span>
            <span>Hugo Lapica</span>
            <span>Mohammed Alshanteer</span>
          </div>
        </section>
        <section className="form-card">
          <h2>Sign in</h2>
          {message && <p className="message">{message}</p>}
          <form onSubmit={handleLogin}>
            <label>Email</label>
            <input value={loginForm.email} onChange={(e) => setLoginForm({ ...loginForm, email: e.target.value })} />
            <label>Password</label>
            <input type="password" value={loginForm.password} onChange={(e) => setLoginForm({ ...loginForm, password: e.target.value })} />
            <button type="submit">Sign in</button>
          </form>
          <button className="secondary" onClick={() => setPage("register")}>
            <UserPlus size={18} />
            Create account
          </button>
        </section>
      </main>
    );
  }

  // Create-account page.
  if (page === "register") {
    return (
      <main className="screen">
        <section className="brand-card">
          <UserPlus size={48} />
          <h1>Create account</h1>
          <p>Create a patient, doctor, or receptionist/admin account.</p>
        </section>
        <section className="form-card wide">
          <h2>New account</h2>
          <form onSubmit={handleRegister}>
            <div className="grid">
              <input placeholder="First name" value={registerForm.firstName} onChange={(e) => setRegisterForm({ ...registerForm, firstName: e.target.value })} />
              <input placeholder="Last name" value={registerForm.lastName} onChange={(e) => setRegisterForm({ ...registerForm, lastName: e.target.value })} />
              <input placeholder="Email" value={registerForm.email} onChange={(e) => setRegisterForm({ ...registerForm, email: e.target.value })} />
              <input placeholder="Password" type="password" value={registerForm.password} onChange={(e) => setRegisterForm({ ...registerForm, password: e.target.value })} />
              <input placeholder="National ID" value={registerForm.nationalId} onChange={(e) => setRegisterForm({ ...registerForm, nationalId: e.target.value })} />
              <select value={registerForm.sex} onChange={(e) => setRegisterForm({ ...registerForm, sex: e.target.value })}>
                <option>Female</option>
                <option>Male</option>
                <option>Other</option>
              </select>
              <select value={registerForm.role} onChange={(e) => setRegisterForm({ ...registerForm, role: e.target.value })}>
                <option value="PATIENT">Patient</option>
                <option value="DOCTOR">Doctor</option>
                <option value="RECEPTIONIST_ADMIN">Receptionist/Admin</option>
              </select>
              {registerForm.role === "PATIENT" && (
                <input placeholder="Insurance ID" value={registerForm.insuranceId} onChange={(e) => setRegisterForm({ ...registerForm, insuranceId: e.target.value })} />
              )}
              {registerForm.role === "DOCTOR" && (
                <input placeholder="NPWZ ID" value={registerForm.npwzId} onChange={(e) => setRegisterForm({ ...registerForm, npwzId: e.target.value })} />
              )}
            </div>
            {registerForm.role === "DOCTOR" && (
              <select value={registerForm.specialization} onChange={(e) => setRegisterForm({ ...registerForm, specialization: e.target.value })}>
                <option value="">Choose specialization</option>
                {specializations.map((item) => <option key={item} value={item}>{item}</option>)}
              </select>
            )}
            <button type="submit">Create account</button>
          </form>
          <button className="secondary" onClick={() => setPage("login")}>Back to sign in</button>
        </section>
      </main>
    );
  }

  // Dashboard page after login.
  return (
    <main className="dashboard">
      <header>
        <div>
          <h1>Bober Clinic</h1>
          <p>{user.firstName} {user.lastName} - {user.role}</p>
        </div>
        <button className="logout" onClick={logout}><LogOut size={18} />Logout</button>
      </header>

      {message && <p className="message">{message}</p>}

      <section className="filters">
        {user.role !== "PATIENT" && (
          <input placeholder="Search patient by first or last name" value={filters.search} onChange={(e) => setFilters({ ...filters, search: e.target.value })} />
        )}
        <input type="date" value={filters.date} onChange={(e) => setFilters({ ...filters, date: e.target.value })} />
        <select value={filters.status} onChange={(e) => setFilters({ ...filters, status: e.target.value })}>
          <option value="">All statuses</option>
          <option>Scheduled</option>
          <option>Consultation</option>
          <option>Completed</option>
        </select>
        {user.role !== "PATIENT" && (
          <select value={filters.scope} onChange={(e) => setFilters({ ...filters, scope: e.target.value })}>
            <option>My visits</option>
            <option>Today schedule</option>
            <option>All appointments</option>
          </select>
        )}
      </section>

      {/* Patient dashboard: booking and own appointments only. */}
      {user.role === "PATIENT" && (
        <section className="dashboard-grid">
          <div className="panel">
            <h2><CalendarPlus size={22} /> Book from doctor availability</h2>
            <form onSubmit={createAppointment}>
              <label>Available time</label>
              <select value={appointmentForm.slot} onChange={(e) => setAppointmentForm({ ...appointmentForm, slot: e.target.value })}>
                <option value="">Choose available doctor time</option>
                {availability.map((slot) => (
                  <option key={slot.id} value={slot.id}>
                    Dr. {slot.doctorName} - {slot.doctorSpecialization} - {slot.dateTime}
                  </option>
                ))}
              </select>
              <label>Result of Interview / Patient Description</label>
              <textarea placeholder="Describe symptoms and illness" value={appointmentForm.visitReason} onChange={(e) => setAppointmentForm({ ...appointmentForm, visitReason: e.target.value })} />
              <button type="submit">Create appointment</button>
            </form>
          </div>
          <AppointmentList appointments={shownAppointments} patientView />
        </section>
      )}

      {/* Doctor dashboard: availability and assigned consultations. */}
      {user.role === "DOCTOR" && (
        <section className="dashboard-grid">
          <div className="panel">
            <h2>Add Availability Table Row</h2>
            <form onSubmit={addAvailability}>
              <label>Date</label>
              <input type="date" value={availabilityForm.date} onChange={(e) => setAvailabilityForm({ ...availabilityForm, date: e.target.value })} />
              <label>Start time</label>
              <input type="time" value={availabilityForm.time} onChange={(e) => setAvailabilityForm({ ...availabilityForm, time: e.target.value })} />
              <button type="submit">Add available time</button>
            </form>
          </div>
          <div className="panel">
            <h2>My Availability</h2>
            {availability.filter((slot) => slot.doctorEmail === user.email).map((slot) => (
              <div className="appointment" key={slot.id}>
                <strong>{slot.dateTime}</strong>
                <span>{slot.booked ? "Booked" : "Open"}</span>
              </div>
            ))}
          </div>
          <div className="panel full-width">
            <h2>My Visits / Consultation</h2>
            {shownAppointments.map((appointment) => (
              <div className="appointment" key={appointment.id}>
                <strong>{appointment.patientName}</strong>
                <span>Insurance ID: {appointment.patientInsuranceId}</span>
                <span>Date: {appointment.dateTime}</span>
                <span>Patient description: {appointment.visitReason}</span>
                <label>Status</label>
                <select value={doctorForms[appointment.id]?.status || "Scheduled"} onChange={(e) => updateDoctorForm(appointment.id, "status", e.target.value)}>
                  <option>Scheduled</option>
                  <option>Consultation</option>
                  <option>Completed</option>
                </select>
                <label>Lab tests</label>
                <select multiple value={(doctorForms[appointment.id]?.labTests || "").split(", ").filter(Boolean)} onChange={(e) => updateMultiSelect(appointment.id, "labTests", e.target.selectedOptions)}>
                  {currentLabTests.map((test) => <option key={test} value={test}>{test}</option>)}
                </select>
                <label>Physical Tests Done</label>
                <div className="checkbox-grid">
                  {physicalTests.map((test) => (
                    <label className="checkbox-row" key={test}>
                      <input type="checkbox" checked={(doctorForms[appointment.id]?.physicalTests || "").split(", ").includes(test)} onChange={() => toggleListValue(appointment.id, "physicalTests", test)} />
                      {test}
                    </label>
                  ))}
                </div>
                <label>Result of Interview / Patient Description</label>
                <textarea value={doctorForms[appointment.id]?.resultOfInterview || ""} onChange={(e) => updateDoctorForm(appointment.id, "resultOfInterview", e.target.value)} />
                <label>Diagnosis</label>
                <textarea value={doctorForms[appointment.id]?.diagnosis || ""} onChange={(e) => updateDoctorForm(appointment.id, "diagnosis", e.target.value)} />
                <label>Notes</label>
                <textarea placeholder="Extra remarks only" value={doctorForms[appointment.id]?.notes || ""} onChange={(e) => updateDoctorForm(appointment.id, "notes", e.target.value)} />
                <button type="button" onClick={() => saveDoctorUpdate(appointment.id)}>Perform / Complete Consultation</button>
              </div>
            ))}
          </div>
        </section>
      )}

      {/* Receptionist/Admin dashboard: clinic data and appointment history. */}
      {user.role === "RECEPTIONIST_ADMIN" && (
        <section className="dashboard-grid">
          <div className="panel">
            <h2>Patient Data</h2>
            {visiblePatients.map((patient) => (
              <div className="appointment" key={patient.id}>
                <strong>{patient.firstName} {patient.lastName}</strong>
                <span>Email: {patient.email}</span>
                <span>Insurance ID: {patient.insuranceId}</span>
                <span>National ID: {patient.nationalId}</span>
              </div>
            ))}
          </div>
          <div className="panel">
            <h2>Doctor Data</h2>
            {doctors.map((doctor) => (
              <div className="appointment" key={doctor.id}>
                <strong>Dr. {doctor.firstName} {doctor.lastName}</strong>
                <span>Email: {doctor.email}</span>
                <span>Specialization: {doctor.specialization}</span>
                <span>NPWZ ID: {doctor.npwzId}</span>
              </div>
            ))}
          </div>
          <div className="panel full-width">
            <h2>Appointments / History</h2>
            {shownAppointments.map((appointment) => (
              <div className="appointment" key={appointment.id}>
                <strong>{appointment.patientName} with Dr. {appointment.doctorName}</strong>
                <span>Date: {appointment.dateTime}</span>
                <span>Status: {appointment.status}</span>
                <span>Lab tests: {appointment.labTests || "None"}</span>
                <span>Physical tests: {appointment.physicalTests || "None"}</span>
                <button type="button" className="danger" onClick={() => deleteAppointment(appointment.id)}>Delete appointment</button>
              </div>
            ))}
          </div>
        </section>
      )}
    </main>
  );
}

// Reusable appointment list panel.
function AppointmentList({ appointments, patientView }) {
  return (
    <div className="panel">
      <h2>{patientView ? "My appointments" : "Appointments"}</h2>
      {appointments.length === 0 && <p>No appointments found.</p>}
      {appointments.map((appointment) => (
        <div className="appointment" key={appointment.id}>
          <strong>{appointment.patientName} with Dr. {appointment.doctorName}</strong>
          <span>Date: {appointment.dateTime}</span>
          <span>Status: {appointment.status}</span>
          <span>Lab tests: {appointment.labTests || "None"}</span>
          <span>Physical tests: {appointment.physicalTests || "None"}</span>
          <span>Diagnosis: {appointment.diagnosis || "Not added"}</span>
          <span>Result of interview: {appointment.resultOfInterview || appointment.visitReason}</span>
          <span>Notes: {appointment.notes || "None"}</span>
        </div>
      ))}
    </div>
  );
}

export default App;
