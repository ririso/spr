package com.spr.example;

import java.util.*;
import java.util.stream.Collectors;

/**
 * StreamAPI よく使うパターン集
 * 基本的なStreamの処理方法と従来の書き方を比較して学習できます
 */
public class StreamPatternExample {

    public static void main(String[] args) {
        System.out.println("=== StreamAPI よく使うパターン集 ===\n");

        // 1. フィルタリング (filter)
        filterExample();

        // 2. 変換 (map)
        mapExample();

        // 3. 平坦化 (flatMap)
        flatMapExample();

        // 4. ソート (sorted)
        sortedExample();

        // 5. 重複削除 (distinct)
        distinctExample();

        // 6. 集計 (count, sum, max, min, average)
        aggregateExample();

        // 7. 条件判定 (anyMatch / allMatch / noneMatch)
        matchExample();

        // 8. 最初/最後の要素 (findFirst, findAny)
        findExample();

        // 9. グルーピング (Collectors.groupingBy)
        groupingExample();

        // 10. 結合 (Collectors.joining)
        joiningExample();

        // 実務でよく使う複合パターン
        practicalExamples();
    }

    /**
     * 1. フィルタリング (filter)
     * 条件に合う要素のみを抽出する
     */
    private static void filterExample() {
        System.out.println("=== 1. フィルタリング (filter) ===");

        final var names = List.of("Tom", "Bob", "Alice");

        // Stream版：Aで始まる名前のみ抽出
        final var result = names.stream()
            .filter(name -> name.startsWith("A"))
            .toList();

        System.out.println("Stream版 - Aで始まる名前: " + result);

        /* 従来の書き方
        final var resultOld = new ArrayList<String>();
        for (String name : names) {
            if (name.startsWith("A")) {
                resultOld.add(name);
            }
        }
        System.out.println("従来版 - Aで始まる名前: " + resultOld);
        */

        System.out.println();
    }

    /**
     * 2. 変換 (map)
     * 要素を別の形に変換する
     */
    private static void mapExample() {
        System.out.println("=== 2. 変換 (map) ===");

        final var names = List.of("Tom", "Bob", "Alice");

        // Stream版：文字列の長さのリストに変換
        final var lengths = names.stream()
            .map(String::length)
            .toList();

        System.out.println("Stream版 - 文字列の長さ: " + lengths);

        /* 従来の書き方
        final var lengthsOld = new ArrayList<Integer>();
        for (String name : names) {
            lengthsOld.add(name.length());
        }
        System.out.println("従来版 - 文字列の長さ: " + lengthsOld);
        */

        System.out.println();
    }

    /**
     * 3. 平坦化 (flatMap)
     * ネストしたコレクションを平坦化する
     */
    private static void flatMapExample() {
        System.out.println("=== 3. 平坦化 (flatMap) ===");

        final var numbers = List.of(List.of(1, 2), List.of(3, 4));

        // Stream版：ネストしたリストを平坦化
        final var flat = numbers.stream()
            .flatMap(List::stream)
            .toList();

        System.out.println("Stream版 - 平坦化: " + flat);

        /* 従来の書き方
        final var flatOld = new ArrayList<Integer>();
        for (List<Integer> list : numbers) {
            for (Integer num : list) {
                flatOld.add(num);
            }
        }
        System.out.println("従来版 - 平坦化: " + flatOld);
        */

        System.out.println();
    }

    /**
     * 4. ソート (sorted)
     * 要素を並び替える
     */
    private static void sortedExample() {
        System.out.println("=== 4. ソート (sorted) ===");

        final var names = List.of("Tom", "Bob", "Alice");

        // Stream版：アルファベット順でソート
        final var sorted = names.stream()
            .sorted()
            .toList();

        System.out.println("Stream版 - ソート: " + sorted);

        /* 従来の書き方
        final var sortedOld = new ArrayList<>(names);
        Collections.sort(sortedOld);
        System.out.println("従来版 - ソート: " + sortedOld);
        */

        // カスタムソート：文字列の長さで降順
        final var sortedByLength = names.stream()
            .sorted((a, b) -> Integer.compare(b.length(), a.length()))
            .toList();

        System.out.println("文字列長さ降順: " + sortedByLength);

        System.out.println();
    }

    /**
     * 5. 重複削除 (distinct)
     * 重複する要素を削除する
     */
    private static void distinctExample() {
        System.out.println("=== 5. 重複削除 (distinct) ===");

        final var nums = List.of(1, 2, 2, 3, 3, 3);

        // Stream版：重複削除
        final var unique = nums.stream()
            .distinct()
            .toList();

        System.out.println("Stream版 - 重複削除: " + unique);

        /* 従来の書き方
        final var uniqueOld = new ArrayList<Integer>();
        for (Integer num : nums) {
            if (!uniqueOld.contains(num)) {
                uniqueOld.add(num);
            }
        }
        System.out.println("従来版 - 重複削除: " + uniqueOld);
        */

        System.out.println();
    }

    /**
     * 6. 集計 (count, sum, max, min, average)
     * 数値の集計処理
     */
    private static void aggregateExample() {
        System.out.println("=== 6. 集計 (count, sum, max, min, average) ===");

        final var nums = List.of(1, 2, 3, 4, 5);

        // Stream版：各種集計
        final var count = nums.stream().count();
        final var sum = nums.stream().mapToInt(Integer::intValue).sum();
        final var max = nums.stream().mapToInt(Integer::intValue).max().orElse(0);
        final var min = nums.stream().mapToInt(Integer::intValue).min().orElse(0);
        final var average = nums.stream().mapToInt(Integer::intValue).average().orElse(0.0);

        System.out.println("Stream版:");
        System.out.println("  count: " + count);
        System.out.println("  sum: " + sum);
        System.out.println("  max: " + max);
        System.out.println("  min: " + min);
        System.out.println("  average: " + String.format("%.1f", average));

        /* 従来の書き方
        final var countOld = nums.size();
        var sumOld = 0;
        var maxOld = Integer.MIN_VALUE;
        var minOld = Integer.MAX_VALUE;

        for (Integer num : nums) {
            sumOld += num;
            if (num > maxOld) maxOld = num;
            if (num < minOld) minOld = num;
        }
        final var averageOld = countOld > 0 ? (double) sumOld / countOld : 0.0;

        System.out.println("従来版:");
        System.out.println("  count: " + countOld);
        System.out.println("  sum: " + sumOld);
        System.out.println("  max: " + maxOld);
        System.out.println("  min: " + minOld);
        System.out.println("  average: " + String.format("%.1f", averageOld));
        */

        System.out.println();
    }

    /**
     * 7. 条件判定 (anyMatch / allMatch / noneMatch)
     * 条件に合う要素があるかどうかを判定
     */
    private static void matchExample() {
        System.out.println("=== 7. 条件判定 (anyMatch / allMatch / noneMatch) ===");

        final var names = List.of("Tom", "Bob", "Alice");

        // Stream版：条件判定
        final var hasAlice = names.stream().anyMatch(n -> n.equals("Alice"));
        final var allShort = names.stream().allMatch(n -> n.length() <= 5);
        final var noneLong = names.stream().noneMatch(n -> n.length() > 10);

        System.out.println("Stream版:");
        System.out.println("  Aliceが含まれるか: " + hasAlice);
        System.out.println("  全て5文字以下か: " + allShort);
        System.out.println("  10文字超えがないか: " + noneLong);

        /* 従来の書き方
        var hasAliceOld = false;
        var allShortOld = true;
        var noneLongOld = true;

        for (String name : names) {
            if (name.equals("Alice")) {
                hasAliceOld = true;
            }
            if (name.length() > 5) {
                allShortOld = false;
            }
            if (name.length() > 10) {
                noneLongOld = false;
            }
        }

        System.out.println("従来版:");
        System.out.println("  Aliceが含まれるか: " + hasAliceOld);
        System.out.println("  全て5文字以下か: " + allShortOld);
        System.out.println("  10文字超えがないか: " + noneLongOld);
        */

        System.out.println();
    }

    /**
     * 8. 最初/最後の要素 (findFirst, findAny)
     * 条件に合う最初の要素を取得
     */
    private static void findExample() {
        System.out.println("=== 8. 最初/最後の要素 (findFirst, findAny) ===");

        final var names = List.of("Tom", "Bob", "Alice");

        // Stream版：最初の要素取得
        final var first = names.stream().findFirst().orElse("N/A");
        final var any = names.stream().findAny().orElse("N/A");
        final var firstLong = names.stream()
            .filter(name -> name.length() > 3)
            .findFirst()
            .orElse("N/A");

        System.out.println("Stream版:");
        System.out.println("  最初の要素: " + first);
        System.out.println("  任意の要素: " + any);
        System.out.println("  4文字以上の最初の要素: " + firstLong);

        /* 従来の書き方
        final var firstOld = names.isEmpty() ? "N/A" : names.get(0);
        final var anyOld = names.isEmpty() ? "N/A" : names.get(0);
        var firstLongOld = "N/A";

        for (String name : names) {
            if (name.length() > 3) {
                firstLongOld = name;
                break;
            }
        }

        System.out.println("従来版:");
        System.out.println("  最初の要素: " + firstOld);
        System.out.println("  任意の要素: " + anyOld);
        System.out.println("  4文字以上の最初の要素: " + firstLongOld);
        */

        System.out.println();
    }

    /**
     * 9. グルーピング (Collectors.groupingBy)
     * 特定の条件でグループ分けする
     */
    private static void groupingExample() {
        System.out.println("=== 9. グルーピング (Collectors.groupingBy) ===");

        final var names = List.of("Tom", "Bob", "Alice", "Anna");

        // Stream版：文字列の長さでグループ化
        final var grouped = names.stream()
            .collect(Collectors.groupingBy(String::length));

        System.out.println("Stream版 - 文字列長でグループ化: " + grouped);

        /* 従来の書き方
        final var groupedOld = new HashMap<Integer, List<String>>();
        for (String name : names) {
            final var length = name.length();
            groupedOld.computeIfAbsent(length, k -> new ArrayList<>()).add(name);
        }
        System.out.println("従来版 - 文字列長でグループ化: " + groupedOld);
        */

        // カウント版
        final var groupCount = names.stream()
            .collect(Collectors.groupingBy(String::length, Collectors.counting()));

        System.out.println("グループ別カウント: " + groupCount);

        System.out.println();
    }

    /**
     * 10. 結合 (Collectors.joining)
     * 要素を文字列として結合する
     */
    private static void joiningExample() {
        System.out.println("=== 10. 結合 (Collectors.joining) ===");

        final var names = List.of("Tom", "Bob", "Alice");

        // Stream版：カンマ区切りで結合
        final var joined = names.stream()
            .collect(Collectors.joining(", "));

        final var prefixedJoined = names.stream()
            .collect(Collectors.joining(", ", "[", "]"));

        System.out.println("Stream版 - カンマ区切り: " + joined);
        System.out.println("Stream版 - プレフィックス付き: " + prefixedJoined);

        /* 従来の書き方
        final var joinedOld = new StringBuilder();
        for (int i = 0; i < names.size(); i++) {
            if (i > 0) joinedOld.append(", ");
            joinedOld.append(names.get(i));
        }

        final var prefixedJoinedOld = new StringBuilder("[");
        for (int i = 0; i < names.size(); i++) {
            if (i > 0) prefixedJoinedOld.append(", ");
            prefixedJoinedOld.append(names.get(i));
        }
        prefixedJoinedOld.append("]");

        System.out.println("従来版 - カンマ区切り: " + joinedOld.toString());
        System.out.println("従来版 - プレフィックス付き: " + prefixedJoinedOld.toString());
        */

        System.out.println();
    }

    /**
     * 実務でよく使う複合パターン
     */
    private static void practicalExamples() {
        System.out.println("=== 実務でよく使う複合パターン ===");

        final var employees = List.of(
            new Employee("Alice", 25, "Engineering"),
            new Employee("Bob", 30, "Sales"),
            new Employee("Charlie", 35, "Engineering"),
            new Employee("Diana", 28, "Sales"),
            new Employee("Eve", 32, "Marketing")
        );

        // 1. filter + map + toList
        final var engineeringNames = employees.stream()
            .filter(emp -> "Engineering".equals(emp.department()))
            .map(Employee::name)
            .toList();

        System.out.println("1. Engineering部門の社員名: " + engineeringNames);

        // 2. filter + map + collect(groupingBy)
        final var ageByDept = employees.stream()
            .filter(emp -> emp.age() >= 30)
            .collect(Collectors.groupingBy(
                Employee::department,
                Collectors.mapping(Employee::age, Collectors.toList())
            ));

        System.out.println("2. 30歳以上の部門別年齢: " + ageByDept);

        // 3. mapToInt().sum() / average()
        final var totalAge = employees.stream()
            .mapToInt(Employee::age)
            .sum();

        final var avgAge = employees.stream()
            .mapToInt(Employee::age)
            .average()
            .orElse(0.0);

        System.out.println("3. 年齢合計: " + totalAge + ", 平均年齢: " + String.format("%.1f", avgAge));

        // 4. anyMatch / allMatch
        final var hasYoung = employees.stream()
            .anyMatch(emp -> emp.age() < 30);

        final var allAdult = employees.stream()
            .allMatch(emp -> emp.age() >= 18);

        System.out.println("4. 30歳未満がいるか: " + hasYoung + ", 全員成人か: " + allAdult);

        // 5. distinct + sorted
        final var uniqueDeptsSorted = employees.stream()
            .map(Employee::department)
            .distinct()
            .sorted()
            .toList();

        System.out.println("5. ユニークな部門（ソート済み）: " + uniqueDeptsSorted);

        System.out.println();
    }

    // サンプル用のレコードクラス
    private record Employee(String name, int age, String department) {}
}