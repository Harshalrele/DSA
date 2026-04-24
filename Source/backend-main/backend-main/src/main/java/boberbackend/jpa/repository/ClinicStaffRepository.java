package boberbackend.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import boberbackend.jpa.model.ClinicStaff;

@Repository
public interface ClinicStaffRepository extends JpaRepository<ClinicStaff, Long> {

}

