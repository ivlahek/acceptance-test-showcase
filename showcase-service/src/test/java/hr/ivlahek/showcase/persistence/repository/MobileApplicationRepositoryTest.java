package hr.ivlahek.showcase.persistence.repository;

import hr.ivlahek.showcase.persistence.entity.MobileApplication;
import hr.ivlahek.showcase.persistence.entity.MobileApplicationBuilder;
import hr.ivlahek.showcase.persistence.entity.UserAccount;
import hr.ivlahek.showcase.persistence.entity.UserAccountBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class MobileApplicationRepositoryTest extends RepositoryTest {

    @Autowired
    private MobileApplicationRepository mobileApplicationRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;

    @Test
    public void should_save_mobile_application() {
        UserAccount userAccount = UserAccountBuilder.anUser().withOrganization(organization).build();
        userAccountRepository.save(userAccount);

        //OPERATE
        MobileApplication mobileApplication = MobileApplicationBuilder.aMobileApplication().withOrganization(organization).withUserAccount(userAccount).build();
        mobileApplicationRepository.save(mobileApplication);

        //CHECK
        assertThat(mobileApplication.getId()).isNotNull();

    }


}