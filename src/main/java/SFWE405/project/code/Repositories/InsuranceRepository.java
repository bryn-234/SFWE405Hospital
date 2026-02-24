package SFWE405.project.code.Repositories;

import SFWE405.project.code.Entities.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
    List<Insurance> findByProviderName(String providerName);
    Insurance findByPolicyNumber(String policyNumber);
    
    //Insurance findByPatient_Ssn(Long ssn);
}
