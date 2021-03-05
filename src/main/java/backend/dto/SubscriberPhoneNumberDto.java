package backend.dto;

public class SubscriberPhoneNumberDto {
    private String phoneNumber;

    public SubscriberPhoneNumberDto(String phoneNumber) {
        setPhoneNumber(phoneNumber);
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
