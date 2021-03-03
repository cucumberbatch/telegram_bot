package backend.model;

import java.util.List;
import java.util.Objects;

public class Subscriber {
    private String phoneNumber;
    private List<String> tags;

    public Subscriber(String phoneNumber, List<String> tags) {
        setPhoneNumber(phoneNumber);
        setTags(tags);
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<String> getTags() {
        return this.tags;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscriber that = (Subscriber) o;
        return Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber, tags);
    }
}
