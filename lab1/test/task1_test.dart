import 'dart:math';

import 'package:lab1/task1.dart';
import 'package:test/test.dart';

void main() {
  group('Задание 1: Тестирование разложения tan(x) в степенной ряд', () {
    test('tan(0) должно быть равно 0', () {
      expect(tanSeries(0), closeTo(0, 1e-4));
    });

    test('tan(π/6) должно быть близко к 1/√3', () {
      expect(tanSeries(pi / 6), closeTo(1 / sqrt(3), 1e-4));
    });

    test('tan(π/4) должно быть близко к 1', () {
      expect(tanSeries(pi / 4), closeTo(1, 1e-4));
    });

    test('tan(x) для x > π/2 должно выбрасывать исключение', () {
      expect(() => tanSeries(pi / 2 + 0.1), throwsArgumentError);
    });

    test('tan(x) для x < -π/2 должно выбрасывать исключение', () {
      expect(() => tanSeries(-pi / 2 - 0.1), throwsArgumentError);
    });
  });
}
