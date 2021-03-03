package backend.dao;

import backend.service.AnotherSubscriberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class SubscriberDaoTest {

    private String testNumber1 = "+7(800)555-35-35";
    private String testNumber2 = "+7(844)525-05-30";
    private List<String> testTags1 = Arrays.asList("money");
    private List<String> testTags2 = Arrays.asList("test");



    @Test
    void testGetAllTags() {
        List<String> tags = AnotherSubscriberService.getAllTags();

        System.out.println(tags);

        Assertions.assertNotNull(tags);
        Assertions.assertFalse(tags.isEmpty());
    }

    @Test
    void testGetSubscriberByPhoneNumber() {
        List<String> tags = AnotherSubscriberService.getPhoneNumberTags(this.testNumber1);

        System.out.println(tags);

        Assertions.assertNotNull(tags);
        Assertions.assertFalse(tags.isEmpty());
    }

}