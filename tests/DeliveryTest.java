import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryTest {

    public Delivery delivery;

    @BeforeEach
    void setUp() {
        delivery = new Delivery();
    }

    private static Stream<Arguments> testCaseProvider() {
        return Stream.of(
                /* More detailed corner cases for distance */
                Arguments.of(0, Dimensions.Large, Workload.VeryHigh, true, 880, "Cost must be 880"),
                Arguments.of(1, Dimensions.Large, Workload.VeryHigh, true, 880, "Cost must be 880"),
                Arguments.of(2, Dimensions.Large, Workload.VeryHigh, true, 960, "Cost must be 960"),
                Arguments.of(9, Dimensions.Large, Workload.VeryHigh, true, 960, "Cost must be 960"),
                Arguments.of(10, Dimensions.Large, Workload.VeryHigh, true, 1120, "Cost must be 1120"),
                Arguments.of(29, Dimensions.Large, Workload.VeryHigh, true, 1120, "Cost must be 1120"),
                Arguments.of(30, Dimensions.Large, Workload.VeryHigh, true, 1280, "Cost must be 1280"),

                /* Pair wise for common parameters */
                Arguments.of(1, Dimensions.Small, Workload.VeryHigh, false, 400, "Cost must be 400"),
                Arguments.of(1, Dimensions.Large, Workload.High, true, 770, "Cost must be 770"),
                Arguments.of(1, Dimensions.Small, Workload.Increased, false, 400, "Cost must be 400"),
                Arguments.of(1, Dimensions.Large, Workload.Normal, true, 550, "Cost must be 550"),

                Arguments.of(5, Dimensions.Large, Workload.Normal, false, 400, "Cost must be 400"),
                Arguments.of(5, Dimensions.Small, Workload.VeryHigh, true, 800, "Cost must be 800"),
                Arguments.of(5, Dimensions.Large, Workload.High, false, 420, "Cost must be 420"),
                Arguments.of(5, Dimensions.Small, Workload.Increased, true, 600, "Cost must be 600"),

                Arguments.of(15, Dimensions.Small, Workload.Increased, false, 400, "Cost must be 400"),
                Arguments.of(15, Dimensions.Large, Workload.Normal, true, 700, "Cost must be 700"),
                Arguments.of(15, Dimensions.Small, Workload.VeryHigh, false, 480, "Cost must be 480"),
                Arguments.of(15, Dimensions.Large, Workload.High, true, 980f, "Cost must be 980"),

                Arguments.of(35, Dimensions.Large, Workload.High, false, 700f, "Cost must be 700"),
                Arguments.of(35, Dimensions.Large, Workload.Normal, false, 500f, "Cost must be 500")
        );
    }

    @ParameterizedTest
    @MethodSource("testCaseProvider")
    void costCalculating(int distance, Dimensions dim, Workload wl, boolean isFragile, double expectedCost, String msg) {
        assertEquals(expectedCost, delivery.Calculate(distance, dim, wl, isFragile), msg);
    }

    @Test
    void negativeDistance() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                delivery.Calculate(-1, Dimensions.Small, Workload.High, true));
        assertEquals("Negative distance", ex.getMessage(), "Negative distance is unacceptable");
    }

    @Test
    void negativeFragility() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                delivery.Calculate(31, Dimensions.Small, Workload.High, true));
        assertEquals("Invalid distance for fragility good", ex.getMessage(),
                "It is impossible to deliver fragile goods over a distance of more than 30 km");
    }

}