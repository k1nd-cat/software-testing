package integration.logarithms;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.k1ndCat.logarithms.Ln;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.k1ndCat.Function.fun;
import static org.k1ndCat.logarithms.Log_10.log_10;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.atLeastOnce;

public class Log_10Test {
    private static final double DELTA = 1e-5;
    private static final MockedStatic<Ln> mocked = Mockito.mockStatic(Ln.class);

    @AfterAll
    static void closeMock() {
        mocked.close();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "log_10.csv", numLinesToSkip = 1)
    public void testMockedLog_10(double x, double selfExpected, double funcExpected) {
        mocked.when(() -> Ln.ln(anyDouble())).thenAnswer(invocation -> {
            double arg = invocation.getArgument(0);
            return Math.log(arg);
        });

        assertEquals(selfExpected, log_10(x), DELTA);
        assertEquals(funcExpected, fun(x), DELTA);
        mocked.verify(() -> log_10(anyDouble()), atLeastOnce());
    }
}
