package org.k1ndCat.trigonometry;

import static org.k1ndCat.trigonometry.Cos.cos;
import static org.k1ndCat.trigonometry.Sin.sin;

public class Tan {
    public static double tan(double x) {
        if (cos(x) == 0)
            throw new IllegalArgumentException("Функция не существует в точке x = " + x);

        return sin(x) / cos(x);
    }
}
