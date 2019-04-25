package hr.ivlahek.showcase.event.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "commandType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateUserCommand.class),
        @JsonSubTypes.Type(value = CreateMobileApplicationCommand.class)
})
public abstract class Command {

    private int organizationId;

    public Command() {
    }

    public Command(int organizationId) {
        this.organizationId = organizationId;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    @Override
    public String toString() {
        return "Command{" +
                "organizationId=" + organizationId +
                '}';
    }
}
