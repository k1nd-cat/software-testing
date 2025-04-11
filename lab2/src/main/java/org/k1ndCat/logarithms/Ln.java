package org.k1ndCat.logarithms;

public class Ln {
    public static double ln(double x) {
        if (x <= 0)
            throw new IllegalArgumentException("Значение X должно быть положительным");

        if (x == 1.0)
            return 0.0;

        var result = 0.0;
        var term = (x - 1) / (x + 1);
        var termSquared = term * term;
        var power = term;

        for (int i = 1; i < 100; i += 2) {
            result += power / i;
            power *= termSquared;
        }

        return 2 * result;
    }
}
