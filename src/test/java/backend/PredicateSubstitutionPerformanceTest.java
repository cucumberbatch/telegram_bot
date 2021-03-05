package backend;

import backend.command.AddingContactInformationMenuStrategy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PredicateSubstitutionPerformanceTest {

    private final List<Integer> data       = new ArrayList<>();
    private final int DATA_ELEMENTS_NUMBER = 1000000;

    @BeforeAll
    void prepareDataForTesting() {
        Random random = new Random();
        for (int i = 0; i < DATA_ELEMENTS_NUMBER; i++) {
            data.add(random.nextInt());
        }
    }

    @Test
    void testIfStatementsPerformance() {
        for (Integer element : data) {
            if (element < 400000) {
                if (element > 10000) {
                    System.out.println("flag 1");
                }
                System.out.println("flag 2");
            }
            if (element > 10000) {
                System.out.println("flag 3");
            }
        }
    }

    @Test
    void testPredicateSubstitutionPerformance() {
        for (Integer element : data) {

        }
    }
}
