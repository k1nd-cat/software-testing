import 'dart:math';

List<int> radixSort(List<int> array) {
  final maxDigit = _maxDigit(array);
  var result = [...array];
  for (int digit = 0; digit < maxDigit; digit++) {
    result = countingSort(result, digit);
  }
  return result;
}

List<int> countingSort(List<int> array, [int rank = 0]) {
  final indexes = List.generate(10, (_) => <int>[]);
  for (int i = 0; i < array.length; i++) {
    final value = _digit(array[i], rank);
    indexes[value].add(i);
  }

  List<int> result = [];
  for (var index in indexes) {
    for (var value in index) {
      result.add(array[value]);
    }
  }

  return result;
}

int _maxDigit(List<int> array) {
  var max = 0;
  for (var value in array) {
    if (value > max) {
      max = value;
    }
  }

  if (max == 0) return 1;
  return (log(max) / log(10)).floor() + 1;
}

int _digit(int number, int position) =>
    (number ~/ (pow(10, position).toInt())) % 10;
