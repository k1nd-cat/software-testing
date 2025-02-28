class Human {
  final String name;
  int _age;

  Human(this.name, this._age);

  int get age => _age;

  void doBirthday() => _age++;
}

class SecurityGuard extends Human {
  SecurityGuard(super.name, super.age);

  String gradAroundTheNeck(Human human1, [Human? human2]) {
    if (human2 == human1) {
      throw ArgumentError(
          'Охранник не может обхватить одного человека дважды.');
    }
    return 'Охранник обхватил за шею ${human1.name}${human2 != null ? ' и ${human2.name}' : ''}.';
  }

  String bowDown(Emotion emotion, Human whom) =>
      'Охранник $emotion поклонился ${whom.name}.';

  String dragOut(Human human1, [Human? human2]) {
    if (human2 == human1) {
      throw ArgumentError(
          'Охранник не может обхватить одного человека дважды.');
    }
    return 'Охранник выволок ${human1.name}${human2 != null ? ' и ${human2.name}' : ''} с мостика, не обращая внимание на их сопротивление.';
  }
}

class Captain extends Human {
  Captain(super.name, super.age);

  String doPurr(Emotion emotion) => 'Капитан $emotion промурлыкал что-то.';

  String leafBook() => 'Капитан полистал свою книжку со стихами.';
}

class Door {
  final Material material;
  bool _isOpen;

  Door({
    this.material = Material.steel,
    bool isOpen = false,
  }) : _isOpen = isOpen;

  bool get isOpen => _isOpen;

  String changeIsOpen() {
    _isOpen = !_isOpen;
    return '$material дверь ${_isOpen ? 'открылась' : 'закрылась'}.';
  }
}

class Building {
  final String name;
  List<Human> charactersInside;

  Building(this.name, this.charactersInside);

  String charactersExit(List<Human> characters) {
    if (!characters
        .every((character) => charactersInside.contains(character))) {
      throw ArgumentError(
          'Из $name не могут выйти персонажи, которых изначально там не было');
    }

    charactersInside.removeWhere((character) => characters.contains(character));
    if (charactersInside.length == 1) {
      var lastCharacter = '';
      switch (charactersInside.first) {
        case Captain _:
          lastCharacter = 'Капитан';
          break;
        case SecurityGuard _:
          lastCharacter = 'Охранник';
          break;
        default:
          lastCharacter = charactersInside.first.name;
      }
      return '$lastCharacter снова остался один.';
    }

    return '';
  }
}

enum Emotion {
  respectfully,
  dismissively,
  thoughtfully;

  @override
  String toString() => switch (this) {
        Emotion.respectfully => 'почтительно',
        Emotion.dismissively => 'пренебрежительно',
        Emotion.thoughtfully => 'задумчиво',
      };
}

enum Material {
  steel,
  tree;

  @override
  String toString() => switch (this) {
        Material.steel => 'Стальная',
        Material.tree => 'Деревянная',
      };
}
