package backend.dto;

public class SubscriberTagDto {
    private String tag;

    public SubscriberTagDto(String tag) {
        setTag(tag);
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
