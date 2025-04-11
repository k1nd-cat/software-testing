package org.k1ndCat;

import static org.k1ndCat.logarithms.Ln.ln;
import static org.k1ndCat.logarithms.Log_10.log_10;
import static org.k1ndCat.logarithms.Log_2.log_2;
import static org.k1ndCat.logarithms.Log_3.log_3;
import static org.k1ndCat.trigonometry.Cos.cos;
import static org.k1ndCat.trigonometry.Cot.cot;
import static org.k1ndCat.trigonometry.Csc.csc;
import static org.k1ndCat.trigonometry.Sec.sec;
import static org.k1ndCat.trigonometry.Sin.sin;
import static org.k1ndCat.trigonometry.Tan.tan;

public class Function {
    public static double fun(double x) {
        return x <= 0
                ? ((Math.pow(((((((((((((((((Math.pow(Math.pow((sin(x) + cot(x)) * tan(x), 2), 3) - sin(x)) * csc(x)) / Math.pow(sec(x), 2)) + sec(x)) + tan(x)) / cos(x)) - (csc(x) / csc(x))) / (sec(x) - sec(x))) / cot(x)) + Math.pow(csc(x), 3)) + sin(x)) - tan(x)) + (Math.pow(csc(x), 2) + sin(x))) / (sec(x) / cot(x))) + Math.pow(((((cot(x) + sin(x)) + cot(x)) + Math.pow(((sin(x) - (cot(x) * cot(x))) + (csc(x) - (csc(x) - csc(x)))), 3)) / (Math.pow(Math.pow(tan(x), 3), 3) + Math.pow((cos(x) * ((((Math.pow(sin(x), 2) / csc(x)) / tan(x)) + (sin(x) + ((cos(x) - sec(x)) + (cos(x) + sin(x))))) / (cos(x) + cos(x)))), 2))), 2)) / ((cos(x) - (sec(x) * cos(x))) - (cot(x) / Math.pow(cot(x), 3)))), 3) + Math.pow((csc(x) * (csc(x) / ((csc(x) + Math.pow((sin(x) * cos(x)), 3)) - (((tan(x) / (sin(x) + tan(x))) - ((cos(x) + ((cot(x) * tan(x)) - csc(x))) - tan(x))) + ((((sin(x) * ((cot(x) - (Math.pow(sec(x), 2) / (tan(x) + csc(x)))) * sec(x))) * (Math.pow(Math.pow((cot(x) / sec(x)), 2), 2) / ((sin(x) / cot(x)) * tan(x)))) - (Math.pow((sec(x) + csc(x)), 2) + (Math.pow(tan(x), 3) * sec(x)))) - cos(x)))))), 3)) * (sin(x) + (((cot(x) + ((csc(x) + sin(x)) + ((((((cos(x) / (csc(x) / sec(x))) - sin(x)) + csc(x)) / sec(x)) / ((csc(x) / sin(x)) * sin(x))) + Math.pow(((((tan(x) / sin(x)) - csc(x)) / Math.pow(cos(x), 2)) - cos(x)), 2)))) * (Math.pow(cot(x), 2) / Math.pow((Math.pow((csc(x) + Math.pow(sec(x), 3)), 3) / ((cot(x) / ((csc(x) / sin(x)) + (((csc(x) + sin(x)) * Math.pow((csc(x) / sin(x)), 3)) * Math.pow((cos(x) + (cot(x) / Math.pow(Math.pow(((csc(x) / ((tan(x) - csc(x)) * sec(x))) / csc(x)), 2), 2))), 3)))) / ((cot(x) + (sin(x) + sin(x))) * ((csc(x) + tan(x)) / (cos(x) - (cos(x) - tan(x))))))), 2))) - Math.pow((sec(x) - Math.pow(Math.pow((sin(x) / ((sin(x) + sin(x)) / Math.pow(tan(x), 3))), 2), 2)), 2))))
                : (((((ln(x) / log_3(x)) * log_3(x)) / (log_3(x) - (ln(x) * log_10(x)))) * (log_2(x) - log_2(x))) + ln(x));
    }
}
