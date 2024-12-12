# Анотації і кодогенерація в Java

## Опис проєкту

Проєкт реалізує анотацію `@Serialized`, яка приймає клас серіалізатора. Під час компіляції для класів, позначених цією анотацією, автоматично генеруються нові класи з ім'ям, що має суфікс `Serialized`. Ці класи включають метод `serialize()`, який використовує вказаний серіалізатор для перетворення об'єктів у строкове подання.

## Автор

- **Ім'я:** Літош Данило
- **Група:** ІА-21

## Інструкції зі збірки та запуску

### Вимоги

- **Java:** Версія 17.
- **Maven** або інший інструмент для керування залежностями.

### Клонування репозиторію

Клонуйте репозиторій на вашу локальну машину за допомогою команди:

```bash
git clone https://github.com/L1tosh/kpi-trps-java.git
cd kpi-trps-java
```

### Збірка проєкту

#### За допомогою командного рядка

1. Перейдіть до кореневої директорії проєкту:
   ```bash
   cd kpi-trps-java
   ```
2. Скомпілюйте проєкт за допомогою `javac`:
   ```bash
   mvn clean compile
   ```
3. Запустіть проєкт:
   ```bash
   mvn exec:java -Dexec.mainClass="com.example.app.Main"
   ```

#### У IntelliJ IDEA

Може бути проблема компіляції проекта, для цього вам потрібно:

1. Відкрийте IntelliJ IDEA та завантажте проєкт:

    - Натисніть "Open" і оберіть директорію проєкту.

2. Увімкніть підтримку анотацій та кодогенерації:

    - Перейдіть до **File > Settings > Build, Execution, Deployment > Compiler > Annotation Processors**.
    - Увімкніть опцію "Enable annotation processing".
   
3. Також зайдіть в File > Project Structure > app > Add content root та обрати в модулі app місце куди компілюються згенеровані класи "target/generated-sources/". 



## Приклад використання анотації

Ось приклад коду, який демонструє використання анотації `@Serialized`:

```java
@Serialized(JsonSerializer.class)
public class User {
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getters and setters omitted for brevity
}
```

Після компіляції буде згенеровано клас `UserSerialized` з методом `serialize()`.

