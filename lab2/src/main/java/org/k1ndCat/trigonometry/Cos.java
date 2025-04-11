package org.k1ndCat.trigonometry;

import static org.k1ndCat.trigonometry.Sin.sin;

public class Cos {
    public static double cos(double x) {
        return sin(Math.PI / 2 - x);
    }
}
