package org.k1ndCat.trigonometry;

import static org.k1ndCat.trigonometry.Cos.cos;

public class Sec {
    public static double sec(double x) {
        if (cos(x) == 0)
            throw new IllegalArgumentException("Функция не существует в точке x = " + x);

        return 1D / cos(x);
    }
}
