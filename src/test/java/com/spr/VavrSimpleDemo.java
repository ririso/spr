package com.spr;

import io.vavr.control.Option;
import io.vavr.control.Try;
import io.vavr.collection.List;

public class VavrSimpleDemo {
    public static void main(String[] args) {
        System.out.println("=== Vavr関数型プログラミングデモ ===\n");

        // Option - null安全
        System.out.println("1. Option（null安全）:");
        Option<String> some = Option.of("Hello Vavr");
        Option<String> none = Option.of(null);

        System.out.println("  some値: " + some);
        System.out.println("  none値: " + none);
        System.out.println("  map変換: " + some.map(String::toUpperCase));
        System.out.println("  デフォルト値: " + none.getOrElse("デフォルト"));

        // Try - 例外安全
        System.out.println("\n2. Try（例外安全）:");
        Try<Integer> success = Try.of(() -> Integer.parseInt("123"));
        Try<Integer> failure = Try.of(() -> Integer.parseInt("abc"));

        System.out.println("  成功: " + success);
        System.out.println("  失敗: " + failure);
        System.out.println("  復旧: " + failure.recover(e -> -1));

        // List - 関数型コレクション
        System.out.println("\n3. List（関数型コレクション）:");
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);

        System.out.println("  元の値: " + numbers);
        System.out.println("  2倍: " + numbers.map(x -> x * 2));
        System.out.println("  偶数のみ: " + numbers.filter(x -> x % 2 == 0));
        System.out.println("  合計: " + numbers.fold(0, Integer::sum));

        // 実践例
        System.out.println("\n4. 実践例（チェーン処理）:");
        String result = Option.of("  hello world  ")
            .map(String::trim)
            .map(String::toUpperCase)
            .filter(s -> s.length() > 5)
            .map(s -> s + "!")
            .getOrElse("処理失敗");

        System.out.println("  結果: " + result);
    }
}