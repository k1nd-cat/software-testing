import 'package:lab2_utils/lab2_utils.dart' as lab2_utils;
import 'package:lab2_utils/primitive_functions.dart';
import 'dart:math';

void main(List<String> arguments) {
  final values = [
    -4.03949,
    -3.9731,
    -4.01581,
    -2.22666,
    -2.1077,
    -7.21673,
    -0.933541,
    -5.36429,
  ];

  final funExpected = [
    12.79736,
    13.88843,
    18.27061,
    22.87177,
    101.25361,
    -57.33944,
    -57.33944,
    -175.57074,
  ];

  for (var i = 0; i < values.length; i++) {
    print('${values[i]}, ${tan(values[i])}, ${funExpected[i]}');
  }
}

