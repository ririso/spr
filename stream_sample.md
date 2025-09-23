# Java Stream API よく使うパターン集

## 1. フィルタリング (filter)

``` java
List<String> names = List.of("Tom", "Bob", "Alice");
List<String> result = names.stream()
    .filter(name -> name.startsWith("A"))
    .toList(); // ["Alice"]
```

------------------------------------------------------------------------

## 2. 変換 (map)

``` java
List<String> names = List.of("Tom", "Bob", "Alice");
List<Integer> lengths = names.stream()
    .map(String::length)
    .toList(); // [3, 3, 5]
```

------------------------------------------------------------------------

## 3. 平坦化 (flatMap)

``` java
List<List<Integer>> numbers = List.of(List.of(1, 2), List.of(3, 4));
List<Integer> flat = numbers.stream()
    .flatMap(List::stream)
    .toList(); // [1, 2, 3, 4]
```

------------------------------------------------------------------------

## 4. ソート (sorted)

``` java
List<String> names = List.of("Tom", "Bob", "Alice");
List<String> sorted = names.stream()
    .sorted()
    .toList(); // ["Alice", "Bob", "Tom"]
```

------------------------------------------------------------------------

## 5. 重複削除 (distinct)

``` java
List<Integer> nums = List.of(1, 2, 2, 3, 3, 3);
List<Integer> unique = nums.stream()
    .distinct()
    .toList(); // [1, 2, 3]
```

------------------------------------------------------------------------

## 6. 集計 (count, sum, max, min, average)

``` java
List<Integer> nums = List.of(1, 2, 3, 4, 5);
long count = nums.stream().count(); // 5
int sum = nums.stream().mapToInt(Integer::intValue).sum(); // 15
int max = nums.stream().mapToInt(Integer::intValue).max().orElse(0); // 5
```

------------------------------------------------------------------------

## 7. 条件判定 (anyMatch / allMatch / noneMatch)

``` java
List<String> names = List.of("Tom", "Bob", "Alice");
boolean hasAlice = names.stream().anyMatch(n -> n.equals("Alice")); // true
boolean allShort = names.stream().allMatch(n -> n.length() <= 5);   // true
```

------------------------------------------------------------------------

## 8. 最初/最後の要素 (findFirst, findAny)

``` java
List<String> names = List.of("Tom", "Bob", "Alice");
String first = names.stream().findFirst().orElse("N/A"); // "Tom"
```

------------------------------------------------------------------------

## 9. グルーピング (Collectors.groupingBy)

``` java
List<String> names = List.of("Tom", "Bob", "Alice", "Anna");
Map<Integer, List<String>> grouped = names.stream()
    .collect(Collectors.groupingBy(String::length));
// {3=[Tom, Bob], 5=[Alice], 4=[Anna]}
```

------------------------------------------------------------------------

## 10. 結合 (Collectors.joining)

``` java
List<String> names = List.of("Tom", "Bob", "Alice");
String joined = names.stream()
    .collect(Collectors.joining(", "));
// "Tom, Bob, Alice"
```

------------------------------------------------------------------------

## 📌 実務で頻出するTOP5

1.  **filter + map + toList**
2.  **filter + map + collect(Collectors.groupingBy)**
3.  **mapToInt().sum() / average()**
4.  **anyMatch / allMatch**
5.  **distinct + sorted**
