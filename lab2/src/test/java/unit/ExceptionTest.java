package unit;

import org.junit.jupiter.api.Test;
import org.k1ndCat.trigonometry.*;

import static org.junit.jupiter.api.Assertions.*;

public class ExceptionTest {
    @Test
    void testCotThrowsExceptionAtZero() {
        assertThrows(IllegalArgumentException.class, () -> Cot.cot(0));
    }

    @Test
    void testCscThrowsExceptionAtZero() {
        assertThrows(IllegalArgumentException.class, () -> Csc.csc(0));
    }

    @Test
    void testSecThrowsExceptionAtZero() {
        assertThrows(IllegalArgumentException.class, () -> Sec.sec(Math.PI / 2));
    }

    @Test
    void testTanThrowsExceptionAtZero() {
        assertThrows(IllegalArgumentException.class, () -> Tan.tan(Math.PI / 2));
    }

}