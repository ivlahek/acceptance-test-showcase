package hr.ivlahek.showcase.event.dto;

import hr.ivlahek.showcase.dto.user.CreateUserAccountDTO;
import hr.ivlahek.showcase.dto.user.CreateUserAccountDTOBuilder;

public final class CreateUserCommandBuilder {
    private CreateUserAccountDTO createUserAccountDTO = CreateUserAccountDTOBuilder.aCreateUserAccountDTO().build();
    private int organizationId = 1;

    private CreateUserCommandBuilder() {
    }

    public static CreateUserCommandBuilder aCreateUserCommand() {
        return new CreateUserCommandBuilder();
    }

    public CreateUserCommandBuilder withCreateUserAccountDTO(CreateUserAccountDTO createUserAccountDTO) {
        this.createUserAccountDTO = createUserAccountDTO;
        return this;
    }

    public CreateUserCommandBuilder withOrganizationId(int organizationId) {
        this.organizationId = organizationId;
        return this;
    }

    public CreateUserCommand build() {
        return new CreateUserCommand(organizationId, createUserAccountDTO);
    }
}
