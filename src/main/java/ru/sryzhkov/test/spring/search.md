### Верхнеуровневый принцип работы полнотекстового поиска

Представим у нас магазин товаров, товар описывается тремя полями
```json
{
"id": 17,
"name": "big black box",
"type": "box_type"
}
```

Мы хотим уметь полнотекстово поискать по названию, а также пофильтровать товары по типу

Для полнотекстового поиска обычно используется обратный индекс. Посмотрим, как происходит добавление документа

1. Берем поле name
2. Бьем исходное имя на токены: ["big", "black", "box"]
3. Добавляем инфу, что токены "big", "black" и "box" встречаются в документе 17

Обратный будет представлять собой мапу token -> [document_id] и для поля name будет выглядить так:
```json
{
"big": [17],
"black": [17],
"box": [17]
}
```

Для остальных индексируемых полей строим аналогично

---

Загрузив несколько документов, получится примерно такое:

Для поля name:
```json
{
"big": [17, 23],
"black": [15, 17, 29],
"box": [17, 18, 22],
"ball": [13, 21],
"small": [13, 29],
"white": [1, 5]
}

```

Для поля type:
```json
{
"box_type": [17, 18, 22],
"ball_type": [13, 21]
}
```

---

И наконец, чтобы сделать поиск name = "small" and type = ball_type, нужно

1. Посмотреть в обратный индекс name на токен small: получим документы [13, 29]
2. Посмотреть в обратный индекс type на токен ball_type: получим документы [13, 21]
3. Пересечь выборки: получим документы [13]





### Elasticsearch

#### Как работает индексация и поиск?
Индексация данных:
- Документ (JSON) отправляется в Elasticsearch.

- Текст разбивается на токены (анализ через анализаторы).

- Строится обратный индекс (как в Lucene).

- Данные распределяются по шардам (можно настроить количество).

Поиск:
- Запрос поступает в кластер.

- Координатор (coordinating node) распределяет запрос по шардам.

- Каждый шард выполняет поиск локально.

- Результаты агрегируются, ранжируются и возвращаются.

```java
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class ElasticsearchExample {
    public static void main(String[] args) {
        try (RestHighLevelClient client = createClient()) {
            SearchRequest request = new SearchRequest("products");
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            
            sourceBuilder.query(QueryBuilders.matchQuery("name", "iphone"));
            request.source(sourceBuilder);
            
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            System.out.println("Результаты: " + response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```