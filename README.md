# SearchEngine
Данный проект реализует поисковый движок, предоставляющий пользователю специальный
API со следующими основными функциями:
<li>предварительное индексирование сайтов;</li>
<li>выдача основных сведений по сайтам;</li>
<li>поиск ключевых слов в проиндексированных сайтах и предоставление их пользователю.</li>

## Веб-страница
<p>
В проект также входит веб-страница, которая позволяет управлять процессами, реализованными
в движке.
<p>
Страница содержит три вкладки.

### Вкладка DASHBOARD

Эта вкладка открывается по умолчанию. На ней
отображается общая статистика по всем проиндексированным сайтам, а также
детальная статистика и статус по каждому из сайтов (статистика,
получаемая по запросу <i>/statistics</i>).

### Вкладка MANAGEMENT

На этой вкладке находятся инструменты управления
поисковым движком — запуск (запрос <i>/startIndexing</i>)
и остановка (запрос <i>/stopIndexing</i>) полной индексации
(переиндексации), а также возможность добавить (обновить)
отдельную страницу по ссылке (запрос <i>/indexPage/{pagePath}</i>).
Отметим, что если в последнем запросе присутствует только
URL сайта без завершающего слэша (/), как в приведённом выше
скриншоте, то индексироваться будет указанный сайт целиком.

### Вкладка SEARCH

Эта вкладка предназначена для тестирования поискового
движка. На ней находится поле поиска и выпадающий список с
выбором сайта, по которому искать, а при нажатии на кнопку
<i>SEARCH</i> выводятся результаты поиска (по запросу /search).

### Кодировка страницы
<p>
На всех вкладках присутствует выпадающий список <i>Language</i> (<i>Язык</i>).
При выборе одного из двух вариантов — <i>English</i> или <i>Русский</i> —
соответственно поменяется кодировка страницы. Пример русской
раскладки:

## Используемые технологии
Приложение построено на платформе <i>Spring Boot</i>.
<p>Необходимые компоненты собираются с помощью фреймворка Maven.
Maven подключает следующие относящиеся к <i>Spring Boot</i> стартеры:
<li>
<i>spring-boot-starter-web</i> — подтягивает в проект библиотеки, 
необходимые для выполнения Spring-MVC функций приложения. При этом обмен
данными между браузером и сервером выполняется по технологии AJAX;
</li>
<li>
<i>spring-boot-starter-data-jpa</i> — отвечает за подключение библиотек,
требующихся для работы приложения с базой данных;
</li>
<li>
<i>spring-boot-starter-thymeleaf</i> — шаблонизатор веб-страницы программы.
</li>
<p>
Для загрузки и разбора страниц с сайтов используется библиотека <i>jsoup</i>.
<p>
Данная версия программы работает с СУБД MySQL. Для этого 
подключается зависимость <i>mysql-connector-java</i>.
<p>
Для удобства написания (и чтения) программного кода и для
расширения функциональности языка Java используется библиотека
Lombok (зависимость <i>lombok</i>).