package unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.k1ndCat.logarithms.Ln;

import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

public class LnTest {

    @ParameterizedTest
    @MethodSource("validValues")
    void testLn(double x) {
        double expected = Math.log(x);
        double actual = Ln.ln(x);
        assertEquals(expected, actual, 1e-10, "Testing ln(" + x + ")");
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -1.0, -100.0, Double.NEGATIVE_INFINITY})
    void testInvalidValues(double x) {
        assertThrows(IllegalArgumentException.class, () -> Ln.ln(x));
    }

    @Test
    void testSpecialCase1() {
        assertEquals(0.0, Ln.ln(1.0), 1e-10);
    }

    static Stream<Arguments> validValues() {
        return Stream.of(
                Arguments.of(0.1),
                Arguments.of(0.5),
                Arguments.of(1.0),
                Arguments.of(1.5),
                Arguments.of(2.0),
                Arguments.of(Math.E),
                Arguments.of(3.0),
                Arguments.of(10.0),
                Arguments.of(1 + 1e-10)
        );
    }
}
