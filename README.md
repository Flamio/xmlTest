mvn clean compile - для генерации объектов из xsd схемы

в параметры программы передать входной xml файл, например /home/test/test.xml

в результате работы программы, если нет ошибок, создастся файл changed.xml в директории входного файла


пример xml файла : 

<?xml version="1.0" encoding="UTF-8" standalone="yes"?>

```

<person>
<fio>asdjlfn</fio>
<birthday>2020-08-10</birthday>
<address>
  <street>sadfasfd</street>
  <house>123</house>
  <city>Izhevsk</city>
</address>
</person>

```