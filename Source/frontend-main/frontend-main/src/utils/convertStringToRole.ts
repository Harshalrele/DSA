import { Role } from "@/types";

const convertStringToRole = (roleString: string): Role => {
  switch (roleString.toUpperCase()) {
    case "USER":
      return Role.User;
    case "ADMIN":
      return Role.Admin;
    case "DOCTOR":
      return Role.Doctor;
    case "RECEPTIONIST":
      return Role.Receptionist;
    case "LAB_ASSISTANT":
      return Role.LabAssistant;
    case "LAB_SUPER":
      return Role.LabSupervisor;
    case "PATIENT":
      return Role.Patient;
    default:
      return Role.User;
  }
};

export { convertStringToRole };
