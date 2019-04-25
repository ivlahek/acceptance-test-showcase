package hr.ivlahek.showcase.dto.notification;

public class SendNotificationRequest {

    private String title;

    private String message;

    private int mobileApplicationId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMobileApplicationId() {
        return mobileApplicationId;
    }

    public void setMobileApplicationId(int mobileApplicationId) {
        this.mobileApplicationId = mobileApplicationId;
    }

    @Override
    public String toString() {
        return "SendNotificationRequest{" +
                "title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", mobileApplicationId=" + mobileApplicationId +
                '}';
    }
}
