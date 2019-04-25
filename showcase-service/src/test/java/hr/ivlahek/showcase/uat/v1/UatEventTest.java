package hr.ivlahek.showcase.uat.v1;

import hr.ivlahek.showcase.UatAbstractTest;
import hr.ivlahek.showcase.dto.mobileapp.CreateMobileApplicationDTO;
import hr.ivlahek.showcase.dto.mobileapp.CreateMobileApplicationDTOBuilder;
import hr.ivlahek.showcase.dto.mobileapp.MobileApplicationDTO;
import hr.ivlahek.showcase.dto.mobileapp.MobileApplicationEndPoints;
import hr.ivlahek.showcase.dto.notification.NotificationEndPoints;
import hr.ivlahek.showcase.dto.notification.SendNotificationRequest;
import hr.ivlahek.showcase.dto.organization.CreateOrganizationDTO;
import hr.ivlahek.showcase.dto.organization.OrganizationDTO;
import hr.ivlahek.showcase.dto.organization.OrganizationEndPoints;
import hr.ivlahek.showcase.dto.user.CreateUserAccountDTO;
import hr.ivlahek.showcase.dto.user.CreateUserAccountDTOBuilder;
import hr.ivlahek.showcase.dto.user.UserAccountDTO;
import hr.ivlahek.showcase.dto.user.UserAccountEndpoints;
import hr.ivlahek.showcase.event.dto.CreateMobileApplicationCommand;
import hr.ivlahek.showcase.event.dto.CreateMobileApplicationCommandBuilder;
import hr.ivlahek.showcase.event.dto.CreateUserCommand;
import hr.ivlahek.showcase.event.dto.CreateUserCommandBuilder;
import hr.ivlahek.showcase.mapping.JsonConverter;
import hr.ivlahek.showcase.persistence.entity.MobileApplication;
import hr.ivlahek.showcase.persistence.entity.UserAccount;
import hr.ivlahek.showcase.persistence.repository.UserAccountRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import static hr.ivlahek.showcase.uat.v1.UatSuiteTest.embeddedKafka;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class UatEventTest extends UatAbstractTest {

    @Autowired
    private JsonConverter jsonConverter;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private UserAccountRepository userAccountRepository;

    @Test
    public void send() throws InterruptedException {
        CreateOrganizationDTO createOrganizationResource = new CreateOrganizationDTO();
        createOrganizationResource.setName("organization-name");

        ResponseEntity<OrganizationDTO> organizationDTOResponse = testRestTemplate.postForEntity(OrganizationEndPoints.ORGANIZATION_RESOURCE, createOrganizationResource, OrganizationDTO.class);

        assertThat(organizationDTOResponse.getStatusCodeValue()).isEqualTo(200);

        int organizationId = organizationDTOResponse.getBody().getId();
        Map<String, Object> senderProps = KafkaTestUtils.producerProps(embeddedKafka.getEmbeddedKafka());

        KafkaProducer<Integer, String> producer = new KafkaProducer<>(senderProps);
        CreateUserAccountDTO createUserAccountDTO = CreateUserAccountDTOBuilder.aCreateUserAccountDTO().build();
        CreateUserCommand createUserCommand = CreateUserCommandBuilder.aCreateUserCommand()
                .withOrganizationId(organizationId)
                .withCreateUserAccountDTO(createUserAccountDTO)
                .build();
        producer.send(new ProducerRecord<>("showcase-topic", 0, 0, jsonConverter.write(createUserCommand)));

        ConsumerRecord<String, String> received = UatSuiteTest.records.poll(10, TimeUnit.SECONDS);
        System.out.println(received.value());
        Thread.sleep(500);

        List<UserAccount> userAccountList = userAccountRepository.findByFirstName(createUserAccountDTO.getFirstName());

        CreateMobileApplicationDTO createMobileApplicationDTO = new CreateMobileApplicationDTO();
        Integer userAccountId = userAccountList.get(0).getId();
        createMobileApplicationDTO.setUserAccountId(userAccountId);
        createMobileApplicationDTO.setName("name");

        ResponseEntity<MobileApplicationDTO> mobileApplicationDTOResponseEntity = testRestTemplate.postForEntity(MobileApplicationEndPoints.MOBILE_APPLICATION_RESOURCE, createMobileApplicationDTO, MobileApplicationDTO.class, organizationId);

        assertThat(mobileApplicationDTOResponseEntity.getStatusCodeValue()).isEqualTo(200);


        SendNotificationRequest request = new SendNotificationRequest();
        request.setMessage("message");
        int mobileApplicationId = mobileApplicationDTOResponseEntity.getBody().getId();
        request.setMobileApplicationId(mobileApplicationId);
        request.setTitle("hello!");

        ResponseEntity<Void> responseEntity = testRestTemplate.postForEntity(NotificationEndPoints.SEND_NOTIFICATION, request, Void.class, organizationId);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void should_create_organization_rest__user_with_rest__mobile_app_with_event__and_send_notification() throws InterruptedException {
        CreateOrganizationDTO createOrganizationResource = new CreateOrganizationDTO();
        createOrganizationResource.setName("organization-name");

        ResponseEntity<OrganizationDTO> organizationDTOResponse = testRestTemplate.postForEntity(OrganizationEndPoints.ORGANIZATION_RESOURCE, createOrganizationResource, OrganizationDTO.class);

        assertThat(organizationDTOResponse.getStatusCodeValue()).isEqualTo(200);


        CreateUserAccountDTO createUserAccountDTO = new CreateUserAccountDTO();
        createUserAccountDTO.setLastName("last-name");
        createUserAccountDTO.setFirstName("first-name");

        Integer organizationId = organizationDTOResponse.getBody().getId();
        ResponseEntity<UserAccountDTO> accountDTOResponseEntity = testRestTemplate.postForEntity(UserAccountEndpoints.USER_RESOURCE, createUserAccountDTO, UserAccountDTO.class, organizationId);

        assertThat(accountDTOResponseEntity.getStatusCodeValue()).isEqualTo(200);


        Map<String, Object> senderProps = KafkaTestUtils.producerProps(embeddedKafka.getEmbeddedKafka());

        KafkaProducer<Integer, String> producer = new KafkaProducer<>(senderProps);
        CreateMobileApplicationDTO createMobileApplicationDTO = CreateMobileApplicationDTOBuilder.aCreateMobileApplicationDTO().withUserAccountId(accountDTOResponseEntity.getBody().getId()).build();
        CreateMobileApplicationCommand createMobileApplicationCommand = CreateMobileApplicationCommandBuilder.aCreateMobileApplicationCommand()
                .withCreateMobileApplicationDTO(createMobileApplicationDTO)
                .withOrganizationId(organizationId).build();

        producer.send(new ProducerRecord<>("showcase-topic", 0, 0, jsonConverter.write(createMobileApplicationCommand)));

        ConsumerRecord<String, String> received = UatSuiteTest.records.poll(10, TimeUnit.SECONDS);
        System.out.println(received);
        Thread.sleep(500);
        List<MobileApplication> mobileApplications = mobileApplicationRepository.findByName(createMobileApplicationDTO.getName());


        SendNotificationRequest request = new SendNotificationRequest();
        request.setMessage("message");
        int mobileApplicationId = mobileApplications.get(0).getId();
        request.setMobileApplicationId(mobileApplicationId);
        request.setTitle("hello!");

        ResponseEntity<Void> responseEntity = testRestTemplate.postForEntity(NotificationEndPoints.SEND_NOTIFICATION, request, Void.class, organizationId);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);

    }
}
