import pandas as pd
import matplotlib.pyplot as plt

# Чтение данных из CSV файла
# Указываем разделитель как точку с запятой и десятичный разделитель как запятую
data = pd.read_csv('testing_lab2_output2.csv', sep=';', decimal=',', names=['x', 'y'])

# Построение графика
# plt.figure(figsize=(10, 6))
plt.plot(data['x'], data['y'], linestyle='-', color='b')
plt.title('График зависимости y от x')
plt.xlabel('x')
plt.ylabel('y')
# plt.grid(True)

# Установка ограничений по высоте (оси y)
plt.ylim(-30, 50)

# Отображение графика
plt.show()