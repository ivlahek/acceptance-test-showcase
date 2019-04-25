package hr.ivlahek.showcase.persistence.entity;

public final class OrganizationBuilder {
    private Integer id;
    private String name = "default-organization";

    private OrganizationBuilder() {
    }

    public static OrganizationBuilder anOrganization() {
        return new OrganizationBuilder();
    }

    public OrganizationBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public OrganizationBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public Organization build() {
        Organization organization = new Organization();
        organization.setId(id);
        organization.setName(name);
        return organization;
    }
}
