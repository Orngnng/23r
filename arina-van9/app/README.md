# Музыкальная коллекция альбомов

Веб-приложение для учёта персональной коллекции музыкальных альбомов.

## Функциональные возможности

- Регистрация и авторизация пользователей
- Добавление, редактирование и удаление альбомов
- Фильтрация альбомов по жанрам
- Разграничение доступа: каждый пользователь видит только свои альбомы

## Технологический стек

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA** — объектно-реляционное отображение
- **Hibernate** — реализация JPA
- **H2 Database** — встроенная реляционная СУБД
- **Thymeleaf** — серверный шаблонизатор
- **Bean Validation** — валидация данных
- **Maven** — система сборки

## Требования

- JDK 17 или выше
- Maven 3.8+ (опционально, можно использовать встроенный Maven Wrapper)

## Запуск приложения

### Вариант 1: с установленным Maven

```bash
cd app
mvn clean install
mvn spring-boot:run
```

### Вариант 2: с Maven Wrapper (если настроен)

```bash
cd app
./mvnw clean install
./mvnw spring-boot:run
```

### Вариант 3: через JAR-файл

```bash
cd app
mvn clean package
java -jar target/musical-albums-1.0.0.jar
```

После запуска приложение будет доступно по адресу: **http://localhost:8080**

## Структура проекта

```
app/
├── src/
│   ├── main/
│   │   ├── java/ru/spbume/musicalbums/
│   │   │   ├── MusicAlbumsApplication.java    # Точка входа
│   │   │   ├── controller/                    # Контроллеры MVC
│   │   │   │   ├── AuthController.java
│   │   │   │   └── AlbumController.java
│   │   │   ├── entity/                        # JPA-сущности
│   │   │   │   ├── User.java
│   │   │   │   └── Album.java
│   │   │   ├── repository/                    # Spring Data репозитории
│   │   │   │   ├── UserRepository.java
│   │   │   │   └── AlbumRepository.java
│   │   │   └── service/                       # Бизнес-логика
│   │   │       ├── UserService.java
│   │   │       └── AlbumService.java
│   │   └── resources/
│   │       ├── application.properties         # Конфигурация приложения
│   │       ├── templates/                     # Thymeleaf шаблоны
│   │       │   ├── login.html
│   │       │   ├── register.html
│   │       │   ├── albums.html
│   │       │   └── album-form.html
│   │       └── static/                        # Статические ресурсы
└── pom.xml                                    # Maven конфигурация
```

## Модель данных

### Таблица `users`
- `id` — первичный ключ
- `login` — уникальный логин пользователя
- `password` — пароль (в реальном приложении должен быть зашифрован)

### Таблица `albums`
- `id` — первичный ключ
- `user_id` — внешний ключ на таблицу users
- `title` — название альбома
- `artist` — исполнитель
- `year` — год выпуска
- `genre` — жанр (Рок, Поп, Джаз, Классика, Хип-хоп)

## База данных H2

База данных автоматически создаётся в директории `data/albums.mv.db` при первом запуске.

Для просмотра данных через веб-консоль H2:
1. Откройте http://localhost:8080/h2-console
2. JDBC URL: `jdbc:h2:file:./data/albums`
3. User Name: `sa`
4. Password: *(оставьте пустым)*

## Примечания

- Пароли хранятся в открытом виде. Для production-окружения рекомендуется использовать Spring Security с BCrypt.
- База данных H2 работает в файловом режиме, данные сохраняются между перезапусками.
- Для сброса базы данных удалите файл `data/albums.mv.db`.
