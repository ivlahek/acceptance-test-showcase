package hr.ivlahek.showcase.uat.v2.steps.user;

import hr.ivlahek.showcase.dto.organization.OrganizationDTO;
import hr.ivlahek.showcase.dto.user.CreateUserAccountDTO;
import hr.ivlahek.showcase.dto.user.CreateUserAccountDTOBuilder;
import hr.ivlahek.showcase.dto.user.UserAccountDTO;
import hr.ivlahek.showcase.event.dto.CreateUserCommand;
import hr.ivlahek.showcase.event.dto.CreateUserCommandBuilder;
import hr.ivlahek.showcase.mapping.JsonConverter;
import hr.ivlahek.showcase.persistence.entity.UserAccount;
import hr.ivlahek.showcase.persistence.repository.UserAccountRepository;
import hr.ivlahek.showcase.uat.v2.UatSuiteTest;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static hr.ivlahek.showcase.uat.v2.UatSuiteTest.embeddedKafka;

@Service
public class CreateUserEventStep implements CreateUserStep {

    @Autowired
    private JsonConverter jsonConverter;
    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public UserAccountDTO createUserAccount(OrganizationDTO organizationDTO) throws InterruptedException {
        Map<String, Object> senderProps = KafkaTestUtils.producerProps(embeddedKafka.getEmbeddedKafka());

        KafkaProducer<Integer, String> producer = new KafkaProducer<>(senderProps);
        CreateUserAccountDTO createUserAccountDTO = CreateUserAccountDTOBuilder.aCreateUserAccountDTO().build();
        CreateUserCommand createUserCommand = CreateUserCommandBuilder.aCreateUserCommand()
                .withOrganizationId(organizationDTO.getId())
                .withCreateUserAccountDTO(createUserAccountDTO)
                .build();
        producer.send(new ProducerRecord<>("showcase-topic", 0, 0, jsonConverter.write(createUserCommand)));

        ConsumerRecord<String, String> received = UatSuiteTest.records.poll(10, TimeUnit.SECONDS);
        System.out.println(received.value());
        Thread.sleep(500);

        List<UserAccount> userAccountList = userAccountRepository.findByFirstName(createUserAccountDTO.getFirstName());

        UserAccountDTO userAccountDTO = new UserAccountDTO();
        UserAccount userAccount = userAccountList.get(0);
        userAccountDTO.setOrganizationId(userAccount.getOrganization().getId());
        userAccountDTO.setId(userAccount.getId());
        return userAccountDTO;

    }

}
