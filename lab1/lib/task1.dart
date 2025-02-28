import 'dart:math';

double tanSeries(double x, [int terms = 7]) {
  if (x.abs() >= (pi / 2)) {
    throw ArgumentError('Ряд Тейлора для tg(x) сходится только при |x| < π/2');
  }

  var result = 0.0;
  for (var n = 1; n <= terms; n++) {
    final numerator = _coefficient(n);
    final exponent = n + n - 1;
    result += numerator * pow(x, exponent);
  }

  return result;
}

double _coefficient(int n) {
  const coefficients = [
    1.0,
    1.0 / 3,
    2.0 / 15,
    17.0 / 315,
    62.0 / 2835,
    1382.0 / 155925,
    21844.0 / 6081075
  ];

  if (n < 1 || n > 7) {
    throw ArgumentError('Реализованы только коэффициенты для n <= ${coefficients.length}');
  }
  return coefficients[n - 1];
}
