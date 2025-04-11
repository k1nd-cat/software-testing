package org.k1ndCat.trigonometry;

import static org.k1ndCat.trigonometry.Sin.sin;

public class Csc {
    public static double csc(double x) {
        if (sin(x) == 0)
            throw new IllegalArgumentException("Функция не существует в точке x = " + x);

        return 1D / sin(x);
    }
}
