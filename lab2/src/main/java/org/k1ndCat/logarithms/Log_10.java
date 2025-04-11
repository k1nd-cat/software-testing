package org.k1ndCat.logarithms;

import static org.k1ndCat.logarithms.Ln.ln;

public class Log_10 {
    public static double log_10(double x) {
        return ln(x) / ln(10.0);
    }
}
