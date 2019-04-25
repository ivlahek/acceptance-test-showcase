package hr.ivlahek.showcase;

import hr.ivlahek.showcase.persistence.IntegrationTest;
import hr.ivlahek.showcase.persistence.repository.MobileApplicationRepository;
import hr.ivlahek.showcase.persistence.repository.OrganizationRepository;
import hr.ivlahek.showcase.persistence.repository.UserAccountRepository;
import org.junit.After;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Category(IntegrationTest.class)
@ActiveProfiles(profiles = "kafka-test")
public class UatAbstractTest {
    @Autowired
    protected
    OrganizationRepository organizationRepository;
    @Autowired
    protected UserAccountRepository userAccountRepository;
    @Autowired
    protected MobileApplicationRepository mobileApplicationRepository;


    @After
    public void tearDown() {
        mobileApplicationRepository.deleteAll();
        userAccountRepository.deleteAll();
        organizationRepository.deleteAll();
    }

}
