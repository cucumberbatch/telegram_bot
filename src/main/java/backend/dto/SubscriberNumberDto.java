package backend.dto;

public class SubscriberNumberDto {
    private String phoneNumber;

    public SubscriberNumberDto(String phoneNumber) {
        setPhoneNumber(phoneNumber);
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
