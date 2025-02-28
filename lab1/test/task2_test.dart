import 'dart:math';

import 'package:lab1/task2.dart';
import 'package:test/test.dart';

void main() {
  final random = Random();
  group('Задание 2: Тестирование сортировки подсчётом', () {
    for (int i = 10; i <= 100; i += 20) {
      final List<int> randomArray = List.generate(i, (_) => random.nextInt(10));
      test('Массив с $i элементами', () {
        final result = [...randomArray]..sort();
        expect(countingSort(randomArray), result);
      });
    }
  });

  group('Задание 2: Тестирования алгоритма сортировки Radix sort', () {
    for (int count = 10; count <= 10000; count *= 10) {
      for (int maxValue = 100; maxValue < 10000; maxValue *= 10) {
        final List<int> randomArray = List.generate(count, (_) => random.nextInt(maxValue));
        test('Массив с $count элементами и максимальным числом ${maxValue - 1}',
            () {
          final result = [...randomArray]..sort();
          expect(radixSort(randomArray), result);
        });
      }
    }
  });
}
