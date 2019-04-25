package hr.ivlahek.showcase.uat.v2.steps.mobileapp;

import hr.ivlahek.showcase.dto.mobileapp.CreateMobileApplicationDTO;
import hr.ivlahek.showcase.dto.mobileapp.CreateMobileApplicationDTOBuilder;
import hr.ivlahek.showcase.dto.mobileapp.MobileApplicationDTO;
import hr.ivlahek.showcase.dto.user.UserAccountDTO;
import hr.ivlahek.showcase.event.dto.CreateMobileApplicationCommand;
import hr.ivlahek.showcase.event.dto.CreateMobileApplicationCommandBuilder;
import hr.ivlahek.showcase.mapping.JsonConverter;
import hr.ivlahek.showcase.persistence.entity.MobileApplication;
import hr.ivlahek.showcase.persistence.repository.MobileApplicationRepository;
import hr.ivlahek.showcase.persistence.repository.UserAccountRepository;
import hr.ivlahek.showcase.uat.v1.UatSuiteTest;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static hr.ivlahek.showcase.uat.v1.UatSuiteTest.embeddedKafka;

@Service
public class CreateMobileAppEventStep implements CreateMobileAppStep {

    @Autowired
    protected UserAccountRepository userAccountRepository;
    @Autowired
    protected MobileApplicationRepository mobileApplicationRepository;
    @Autowired
    private JsonConverter jsonConverter;

    protected TestRestTemplate restTemplate;


    @Override
    public MobileApplicationDTO createMobileApp(UserAccountDTO userAccountDTO) throws InterruptedException {
        Map<String, Object> senderProps = KafkaTestUtils.producerProps(embeddedKafka.getEmbeddedKafka());

        KafkaProducer<Integer, String> producer = new KafkaProducer<>(senderProps);
        CreateMobileApplicationDTO createMobileApplicationDTO = CreateMobileApplicationDTOBuilder.aCreateMobileApplicationDTO().withUserAccountId(userAccountDTO.getId()).build();
        CreateMobileApplicationCommand createMobileApplicationCommand = CreateMobileApplicationCommandBuilder.aCreateMobileApplicationCommand()
                .withCreateMobileApplicationDTO(createMobileApplicationDTO)
                .withOrganizationId(userAccountDTO.getOrganizationId()).build();

        producer.send(new ProducerRecord<>("showcase-topic", 0, 0, jsonConverter.write(createMobileApplicationCommand)));

        ConsumerRecord<String, String> received = UatSuiteTest.records.poll(10, TimeUnit.SECONDS);
        System.out.println(received);
        Thread.sleep(500);
        List<MobileApplication> mobileApplications = mobileApplicationRepository.findByName(createMobileApplicationDTO.getName());

        MobileApplication mobileApplication = mobileApplications.get(0);
        MobileApplicationDTO mobileApplicationDTO = new MobileApplicationDTO();
        mobileApplicationDTO.setId(mobileApplication.getId());
        mobileApplicationDTO.setOrganizationId(mobileApplication.getOrganization().getId());
        return mobileApplicationDTO;
    }

    public void setRestTemplate(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
