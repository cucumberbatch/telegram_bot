package backend.dto;

public class SubscriberNumberToTagLinkDto {
    private Integer subscriberId;
    private Integer tagId;

    public SubscriberNumberToTagLinkDto(Integer subscriberId, Integer tagId) {
        setSubscriberId(subscriberId);
        setTagId(tagId);
    }


    public Integer getPhoneNumberId() {
        return this.subscriberId;
    }

    public void setSubscriberId(Integer subscriberId) {
        this.subscriberId = subscriberId;
    }

    public Integer getTagId() {
        return this.tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }
}
