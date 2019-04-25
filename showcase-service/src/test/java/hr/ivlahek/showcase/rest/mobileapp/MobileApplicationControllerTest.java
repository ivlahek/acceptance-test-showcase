package hr.ivlahek.showcase.rest.mobileapp;

import hr.ivlahek.showcase.RestIntegrationTest;
import hr.ivlahek.showcase.dto.mobileapp.CreateMobileApplicationDTO;
import hr.ivlahek.showcase.dto.mobileapp.MobileApplicationDTO;
import hr.ivlahek.showcase.dto.mobileapp.MobileApplicationEndPoints;
import hr.ivlahek.showcase.dto.organization.OrganizationDTO;
import hr.ivlahek.showcase.persistence.entity.MobileApplication;
import hr.ivlahek.showcase.persistence.entity.MobileApplicationBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class MobileApplicationControllerTest extends RestIntegrationTest {
    @Autowired
    protected TestRestTemplate restTemplate;

    @Test
    public void should_create_mobile_app() {
        CreateMobileApplicationDTO createMobileApplicationDTO = new CreateMobileApplicationDTO();
        createMobileApplicationDTO.setName("name");
        createMobileApplicationDTO.setUserAccountId(userAccount.getId());

        //OPERATE
        ResponseEntity<MobileApplicationDTO> responseEntity = restTemplate.postForEntity(MobileApplicationEndPoints.MOBILE_APPLICATION_RESOURCE, createMobileApplicationDTO, MobileApplicationDTO.class, organization.getId());

        //CHECK
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(mobileApplicationRepository.findById(responseEntity.getBody().getId())).isPresent();
    }

    @Test
    public void should_get_by_id_organization() {
        MobileApplication mobileApplication = MobileApplicationBuilder.aMobileApplication().withOrganization(organization).withUserAccount(userAccount).build();
        mobileApplicationRepository.save(mobileApplication);

        //OPERATE
        ResponseEntity<OrganizationDTO> responseEntity = restTemplate.getForEntity(MobileApplicationEndPoints.MOBILE_APPLICATION_RESOURCE_BY_ID, OrganizationDTO.class, organization.getId(), mobileApplication.getId());

        //CHECK
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().getId()).isEqualTo(mobileApplication.getId());
    }
}