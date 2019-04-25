package hr.ivlahek.showcase.persistence.repository;

import hr.ivlahek.showcase.persistence.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
}
