package hr.ivlahek.showcase.rest.useraccount;

import hr.ivlahek.showcase.dto.user.CreateUserAccountDTO;
import hr.ivlahek.showcase.dto.user.UserAccountDTO;
import hr.ivlahek.showcase.dto.user.UserAccountEndpoints;
import hr.ivlahek.showcase.persistence.entity.UserAccount;
import hr.ivlahek.showcase.persistence.repository.UserAccountRepository;
import hr.ivlahek.showcase.rest.organization.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class UserAccountController {

    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private UserAccountService userAccountService;

    @PostMapping(path = UserAccountEndpoints.USER_RESOURCE)
    public UserAccountDTO create(@PathVariable("organizationId") int organizationId,
                                 @RequestBody CreateUserAccountDTO createUserAccountDTO) {
        return map(userAccountService.create(createUserAccountDTO, organizationId));
    }

    @GetMapping(path = UserAccountEndpoints.USER_RESOURCE_BY_ID)
    public UserAccountDTO getById(@PathVariable("organizationId") int organizationId,
                                  @PathVariable("userId") int userAccountId) {
        return map(userAccountService.findById(userAccountId, organizationId));
    }

    private UserAccountDTO map(UserAccount userAccount) {
        UserAccountDTO userAccountDTO = new UserAccountDTO();
        userAccountDTO.setOrganizationId(userAccount.getOrganization().getId());
        userAccountDTO.setId(userAccount.getId());
        userAccountDTO.setLastName(userAccount.getLastName());
        userAccountDTO.setFirstName(userAccount.getFirstName());
        return userAccountDTO;
    }
}
