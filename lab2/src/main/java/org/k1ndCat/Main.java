package org.k1ndCat;

import org.k1ndCat.logarithms.Ln;
import org.k1ndCat.logarithms.Log_10;
import org.k1ndCat.logarithms.Log_2;
import org.k1ndCat.logarithms.Log_3;
import org.k1ndCat.trigonometry.*;

import java.io.*;
import java.util.function.Function;

import static org.k1ndCat.Function.fun;

public class Main {
    private static final String PATH = "C:/test-data/";
    private static final String CSV_FILE_PATH = "testing_lab2_output2.csv";

    public static void outputCsv() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE_PATH))) {
            writer.println("x;y");

            for (double x = -9.0; x <= 10; x += 0.01001) {
                double funcExpected = fun(x);
                writer.printf("%f;%f%n", x, funcExpected);
                if (x % 10 == 0)
                    System.out.println("Ура, осталось недолго!!!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void csvTestGenerator(
            String filename,
            double start,
            Function<Double, Double> curr
    )  throws IOException {
        final var step = 0.25;
        final var count = 10;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename)))) {
            writer.write("X, selfExpected, funcExpected\n");
            double x = start;
            for (int i = 0; i < count; i++) {
                writer.write(x + ", " + curr.apply(x) + ", " + fun(x) + "\n");
                x += step;
            }
        }
    }

    private static void totalGenerator() throws IOException {
//        Тригонометрические функции
        csvTestGenerator(PATH + "sin.csv", -2.5, Sin::sin);
        csvTestGenerator(PATH + "cos.csv", -2.5, Cos::cos);
        csvTestGenerator(PATH + "tan.csv", -2.5, Tan::tan);
        csvTestGenerator(PATH + "cot.csv", -2.5, Cot::cot);
        csvTestGenerator(PATH + "sec.csv", -2.5, Sec::sec);
        csvTestGenerator(PATH + "csc.csv", -2.5, Csc::csc);

//        Логарифмические функции
        csvTestGenerator(PATH + "ln.csv", 0.00001, Ln::ln);
        csvTestGenerator(PATH + "log_2.csv", 0.00001, Log_2::log_2);
        csvTestGenerator(PATH + "log_3.csv", 0.00001, Log_3::log_3);
        csvTestGenerator(PATH + "log_10.csv", 0.00001, Log_10::log_10);
    }
    public static void main(String[] args) throws IOException {
        outputCsv();
    }
}
