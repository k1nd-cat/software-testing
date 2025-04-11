package org.k1ndCat.trigonometry;

import static org.k1ndCat.trigonometry.Cos.cos;
import static org.k1ndCat.trigonometry.Sin.sin;

public class Cot {
    public static double cot(double x) {
        if (sin(x) == 0)
            throw new IllegalArgumentException("Функция не существует в точке x = " + x);

        return cos(x) / sin(x);
    }
}
