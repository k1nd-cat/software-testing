package unit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.k1ndCat.trigonometry.Sin;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SinTest {
    private static final double DELTA = 1e-5;

    @ParameterizedTest
    @MethodSource("testValues")
    void testSin(double x) {
        double expected = Math.sin(x);
        double actual = Sin.sin(x);
        assertEquals(expected, actual, DELTA);
    }

    static Stream<Double> testValues() {
        return Stream.of(
                0.0,
                Math.PI / 6,
                Math.PI / 4,
                Math.PI / 3,
                Math.PI / 2,
                Math.PI,
                3 * Math.PI / 2,
                2 * Math.PI,
                -Math.PI / 4,
                -Math.PI / 2,
                1.0,
                2.5,
                100.0,
                -100.0
        );
    }
}