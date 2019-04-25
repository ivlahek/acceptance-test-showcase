package hr.ivlahek.showcase.rest.mobileapp;

import hr.ivlahek.showcase.dto.mobileapp.CreateMobileApplicationDTO;
import hr.ivlahek.showcase.dto.mobileapp.MobileApplicationDTO;
import hr.ivlahek.showcase.dto.mobileapp.MobileApplicationEndPoints;
import hr.ivlahek.showcase.persistence.entity.MobileApplication;
import hr.ivlahek.showcase.persistence.repository.MobileApplicationRepository;
import hr.ivlahek.showcase.rest.organization.OrganizationController;
import hr.ivlahek.showcase.rest.organization.OrganizationService;
import hr.ivlahek.showcase.rest.useraccount.UserAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping
@RestController
public class MobileApplicationController {

    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private MobileApplicationRepository mobileApplicationRepository;
    @Autowired
    private MobileApplicationService mobileApplicationService;

    private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

    @PostMapping(path = MobileApplicationEndPoints.MOBILE_APPLICATION_RESOURCE)
    public MobileApplicationDTO createMobileApplication(
            @PathVariable("organizationId") int organizationId, @RequestBody CreateMobileApplicationDTO createMobileApplicationDTO) {
        return map(mobileApplicationService.create(organizationId, createMobileApplicationDTO));
    }


    @GetMapping(path = MobileApplicationEndPoints.MOBILE_APPLICATION_RESOURCE_BY_ID)
    public MobileApplicationDTO getMobileApplicationById(@PathVariable("organizationId") int organizationId, @PathVariable("id") int mobileAppId) {
        return map(mobileApplicationService.findById(mobileAppId, organizationId));
    }

    private MobileApplicationDTO map(MobileApplication mobileApplication) {
        MobileApplicationDTO mobileApplicationDTO = new MobileApplicationDTO();
        mobileApplicationDTO.setName(mobileApplication.getName());
        mobileApplicationDTO.setId(mobileApplication.getId());
        mobileApplicationDTO.setOrganizationId(mobileApplication.getOrganization().getId());
        mobileApplicationDTO.setUserId(mobileApplication.getUserAccount().getId());
        return mobileApplicationDTO;
    }
}
