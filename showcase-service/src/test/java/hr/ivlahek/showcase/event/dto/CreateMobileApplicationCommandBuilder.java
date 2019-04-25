package hr.ivlahek.showcase.event.dto;

import hr.ivlahek.showcase.dto.mobileapp.CreateMobileApplicationDTO;

public final class CreateMobileApplicationCommandBuilder {
    private CreateMobileApplicationDTO createMobileApplicationDTO;
    private int organizationId;

    private CreateMobileApplicationCommandBuilder() {
    }

    public static CreateMobileApplicationCommandBuilder aCreateMobileApplicationCommand() {
        CreateMobileApplicationCommandBuilder createMobileApplicationCommandBuilder = new CreateMobileApplicationCommandBuilder();
        createMobileApplicationCommandBuilder.createMobileApplicationDTO = new CreateMobileApplicationDTO();
        createMobileApplicationCommandBuilder.createMobileApplicationDTO.setName("name");
        createMobileApplicationCommandBuilder.createMobileApplicationDTO.setUserAccountId(1);
        createMobileApplicationCommandBuilder.organizationId = 1;
        return createMobileApplicationCommandBuilder;
    }

    public CreateMobileApplicationCommandBuilder withCreateMobileApplicationDTO(CreateMobileApplicationDTO createMobileApplicationDTO) {
        this.createMobileApplicationDTO = createMobileApplicationDTO;
        return this;
    }

    public CreateMobileApplicationCommandBuilder withOrganizationId(int organizationId) {
        this.organizationId = organizationId;
        return this;
    }

    public CreateMobileApplicationCommand build() {
        CreateMobileApplicationCommand createMobileApplicationCommand = new CreateMobileApplicationCommand();
        createMobileApplicationCommand.setCreateMobileApplicationDTO(createMobileApplicationDTO);
        createMobileApplicationCommand.setOrganizationId(organizationId);
        return createMobileApplicationCommand;
    }
}
