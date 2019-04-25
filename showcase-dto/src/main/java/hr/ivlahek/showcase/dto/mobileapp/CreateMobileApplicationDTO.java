package hr.ivlahek.showcase.dto.mobileapp;

public class CreateMobileApplicationDTO {

    private String name;

    private Integer userAccountId;

    public Integer getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(Integer userAccountId) {
        this.userAccountId = userAccountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
