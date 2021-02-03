package backend.model;

import java.util.List;

public class Subscriber {
    private Long phoneNumber;
    private List<String> tags;

    public Subscriber(Long phoneNumber, List<String> tags) {
        this.phoneNumber = phoneNumber;
        this.tags = tags;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Subscriber{" +
                "phoneNumber=" + phoneNumber +
                ", tags=" + tags.toString() +
                '}';
    }
}
