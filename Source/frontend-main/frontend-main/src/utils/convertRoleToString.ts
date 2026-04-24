import { Role } from "@/types";

const convertRoleToString = (role: Role): string => {
  switch (role) {
    case Role.User:
      return "User";
    case Role.Admin:
      return "Admin";
    case Role.Doctor:
      return "Doctor";
    case Role.Receptionist:
      return "Receptionist";
    case Role.LabAssistant:
      return "LabAssistant";
    case Role.LabSupervisor:
      return "LabSupervisor";
    case Role.Patient:
      return "Patient";
    default:
      return "User";
  }
};

export { convertRoleToString };
