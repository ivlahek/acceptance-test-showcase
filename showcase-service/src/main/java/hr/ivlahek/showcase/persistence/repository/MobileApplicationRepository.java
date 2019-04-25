package hr.ivlahek.showcase.persistence.repository;

import hr.ivlahek.showcase.persistence.entity.MobileApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MobileApplicationRepository extends JpaRepository<MobileApplication, Integer> {
    Optional<MobileApplication> findByIdAndOrganizationId(int mobileAppId, int organizationId);

    List<MobileApplication> findByName(String name);
}
