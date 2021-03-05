package backend.dao;

import backend.service.SubscriberService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class SubscriberServiceTest {

    String testNumber1 = "+7(800)555-35-35";
    String testNumber2 = "+7(844)525-05-30";
    List<String> testTags1 = Arrays.asList("money");
    List<String> testTags2 = Arrays.asList("test");

    String riseUpDatabaseQuery = "";
    String dropDatabaseQuery   = "";


    @BeforeAll
    void riseUpDatabase() {

    }

    @AfterAll
    void dropDatabase() {

    }

    @Test
    void testGetAllTags() {
        List<String> tags = SubscriberService.getAllTags();

        System.out.println(tags);

        Assertions.assertNotNull(tags);
        Assertions.assertFalse(tags.isEmpty());
    }

    @Test
    void testGetSubscriberByPhoneNumber() {
        List<String> tags = SubscriberService.getPhoneNumberTags(this.testNumber1);

        System.out.println(tags);

        Assertions.assertNotNull(tags);
        Assertions.assertFalse(tags.isEmpty());
    }



}