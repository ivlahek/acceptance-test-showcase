package hr.ivlahek.showcase.dto.user;

import java.util.ArrayList;
import java.util.List;

public class UserAccountDTO {

    private Integer id;

    private String firstName;

    private String lastName;

    private int organizationId;

    private List<Integer> mobileApplicationIds = new ArrayList<>();

    public List<Integer> getMobileApplicationIds() {
        return mobileApplicationIds;
    }

    public void setMobileApplicationIds(List<Integer> mobileApplicationIds) {
        this.mobileApplicationIds = mobileApplicationIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }
}
