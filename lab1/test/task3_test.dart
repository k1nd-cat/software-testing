import 'package:lab1/task3.dart';
import 'package:test/test.dart';

void main() {
  group('Задание 3: Тестирование предметной области', () {
    final securityGuard = SecurityGuard('Георгий', 48);
    final captain = Captain('Олег', 53);
    final human1 = Human('Андрей', 16);
    final human2 = Human('Алиса', 15);

    group('Тестирование поведения охранника', () {
      test('Обхват охранником за шею одного персонажа', () {
        final actual = securityGuard.gradAroundTheNeck(human1);
        final expected = 'Охранник обхватил за шею Андрей.';
        expect(actual, expected);
      });

      test('Обхват охранником за шею двух персонажей', () {
        final actual = securityGuard.gradAroundTheNeck(human1, human2);
        final expected = 'Охранник обхватил за шею Андрей и Алиса.';
        expect(actual, expected);
      });

      test('Ошибка, если охранник пытается обхватить одного персонажа дважды.',
          () {
        expect(() => securityGuard.gradAroundTheNeck(human1, human1),
            throwsArgumentError);
      });

      test('Охранник поклонился', () {
        final actual = securityGuard.bowDown(Emotion.dismissively, human1);
        final expected = 'Охранник пренебрежительно поклонился Андрей.';
        expect(actual, expected);
      });

      test('Охранник выволакивает одного персонажа', () {
        final actual = securityGuard.dragOut(human1);
        final expected =
            'Охранник выволок Андрей с мостика, не обращая внимание на их сопротивление.';
        expect(actual, expected);
      });

      test('Охранник выволакивает двух персонажей', () {
        final actual = securityGuard.dragOut(human1, human2);
        final expected =
            'Охранник выволок Андрей и Алиса с мостика, не обращая внимание на их сопротивление.';
        expect(actual, expected);
      });

      test('Ошибка, если охранник выволакивает одного персонажа дважды', () {
        actual() => securityGuard.dragOut(human1, human1);
        expect(actual, throwsArgumentError);
      });
    });

    group('Проверка остальных сущностей', () {
      test('Изменение состояния двери', () {
        final door = Door();
        final actual = door.changeIsOpen();
        final expected = 'Стальная дверь открылась.';
        expect(actual, expected);
      });

      test('Мурлыканье капитана', () {
        final actual = captain.doPurr(Emotion.respectfully);
        final expected = 'Капитан почтительно промурлыкал что-то.';
        expect(actual, expected);
      });

      test('В здании остался только охранник', () {
        final building = Building(
          'Мостик',
          [securityGuard, human2],
        );
        final actual = building.charactersExit([human2]);
        final expected = 'Охранник снова остался один.';
        expect(actual, expected);
      });

      test('В здании остался только обычный персонаж', () {
        final building = Building(
          'Мостик',
          [securityGuard, human2],
        );
        final actual = building.charactersExit([securityGuard]);
        final expected = 'Алиса снова остался один.';
        expect(actual, expected);
      });

      test('В здании никого не осталось', () {
        final building = Building(
          'Мостик',
          [securityGuard, human2],
        );
        final actual = building.charactersExit([securityGuard, human2]);
        final expected = '';
        expect(actual, expected);
      });

      test('В здании осталось больше одного человека', () {
        final building = Building(
          'Мостик',
          [securityGuard, captain, human2, captain],
        );
        final actual = building.charactersExit([human2]);
        final expected = '';
        expect(actual, expected);
      });

      test('Из здания выходят персонажи, которых изначально там не было', () {
        final building = Building(
          'Мостик',
          [securityGuard, human2, captain],
        );
        actual() => building.charactersExit([human1, captain]);
        expect(actual, throwsArgumentError);
      });
    });

    test('Составленная полностью предметная область', () {
      final door = Door(material: Material.steel, isOpen: true);
      final building = Building(
        'Мостик',
        [securityGuard, captain, human2, human1],
      );
      var actual = '${securityGuard.gradAroundTheNeck(human1, human2)} ';
      actual += '${securityGuard.bowDown(Emotion.respectfully, captain)} ';
      actual += securityGuard.dragOut(human1, human2);
      actual += '${building.charactersExit([human1, human2])} ';
      actual += '${door.changeIsOpen()} ';
      actual += '${building.charactersExit([securityGuard])} ';
      actual += '${captain.doPurr(Emotion.thoughtfully)} ';
      actual += captain.leafBook();
      final expected = 'Охранник обхватил за шею Андрей и Алиса. Охранник почтительно поклонился Олег. Охранник выволок Андрей и Алиса с мостика, не обращая внимание на их сопротивление. Стальная дверь закрылась. Капитан снова остался один. Капитан задумчиво промурлыкал что-то. Капитан полистал свою книжку со стихами.';
      expect(actual, expected);
    });
  });
}
