package hr.ivlahek.showcase.persistence.entity;

public final class MobileApplicationBuilder {
    private Integer id;
    private String name = "mobile-app-name";
    private Organization organization;
    private UserAccount userAccount;

    private MobileApplicationBuilder() {
    }

    public static MobileApplicationBuilder aMobileApplication() {
        return new MobileApplicationBuilder();
    }

    public MobileApplicationBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public MobileApplicationBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public MobileApplicationBuilder withOrganization(Organization organization) {
        this.organization = organization;
        return this;
    }

    public MobileApplicationBuilder withUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
        return this;
    }

    public MobileApplication build() {
        MobileApplication mobileApplication = new MobileApplication();
        mobileApplication.setId(id);
        mobileApplication.setName(name);
        mobileApplication.setOrganization(organization);
        mobileApplication.setUserAccount(userAccount);
        return mobileApplication;
    }
}
