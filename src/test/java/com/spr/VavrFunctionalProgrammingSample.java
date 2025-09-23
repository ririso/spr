package com.spr;

import io.vavr.control.Option;
import io.vavr.control.Try;
import io.vavr.control.Either;
import io.vavr.collection.List;
import io.vavr.collection.Stream;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Vavr関数型プログラミングサンプル")
public class VavrFunctionalProgrammingSample {

    @Test
    @DisplayName("Option - null安全な値の取り扱い")
    public void optionSample() {
        // Optionの基本的な使い方
        Option<String> some = Option.of("Hello Vavr");
        Option<String> none = Option.of(null);

        System.out.println("=== Option サンプル ===");
        System.out.println("some: " + some);
        System.out.println("none: " + none);

        // map操作
        Option<String> upperCase = some.map(String::toUpperCase);
        System.out.println("upperCase: " + upperCase);

        // filter操作
        Option<String> filtered = some.filter(s -> s.length() > 5);
        System.out.println("filtered: " + filtered);

        // getOrElse - デフォルト値の提供
        String result = none.getOrElse("Default Value");
        System.out.println("getOrElse: " + result);

        // flatMap - ネストしたOptionの平坦化
        Option<String> nested = some.flatMap(s -> Option.of(s + " World"));
        System.out.println("flatMap: " + nested);

        assertTrue(some.isDefined());
        assertTrue(none.isEmpty());
    }

    @Test
    @DisplayName("Try - 例外安全な計算")
    public void trySample() {
        System.out.println("\n=== Try サンプル ===");

        // 成功ケース
        Try<Integer> success = Try.of(() -> Integer.parseInt("123"));
        System.out.println("success: " + success);

        // 失敗ケース
        Try<Integer> failure = Try.of(() -> Integer.parseInt("not-a-number"));
        System.out.println("failure: " + failure);

        // map操作
        Try<Integer> doubled = success.map(x -> x * 2);
        System.out.println("doubled: " + doubled);

        // recover - 失敗時の復旧
        Try<Integer> recovered = failure.recover(throwable -> -1);
        System.out.println("recovered: " + recovered);

        // getOrElse
        Integer result = failure.getOrElse(0);
        System.out.println("getOrElse: " + result);

        assertTrue(success.isSuccess());
        assertTrue(failure.isFailure());
    }

    @Test
    @DisplayName("Either - 成功と失敗の型安全な表現")
    public void eitherSample() {
        System.out.println("\n=== Either サンプル ===");

        // Right（成功）
        Either<String, Integer> right = Either.right(42);
        System.out.println("right: " + right);

        // Left（失敗）
        Either<String, Integer> left = Either.left("Error occurred");
        System.out.println("left: " + left);

        // map操作（Rightの場合のみ実行）
        Either<String, Integer> mapped = right.map(x -> x * 2);
        System.out.println("mapped: " + mapped);

        // mapLeft操作（Leftの場合のみ実行）
        Either<String, Integer> mappedLeft = left.mapLeft(error -> "Handled: " + error);
        System.out.println("mappedLeft: " + mappedLeft);

        // fold - 両方のケースを処理
        String result = right.fold(
            error -> "Error: " + error,
            value -> "Success: " + value
        );
        System.out.println("fold result: " + result);

        assertTrue(right.isRight());
        assertTrue(left.isLeft());
    }

    @Test
    @DisplayName("List - 関数型コレクション")
    public void listSample() {
        System.out.println("\n=== List サンプル ===");

        // Listの作成
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        System.out.println("numbers: " + numbers);

        // map操作
        List<Integer> doubled = numbers.map(x -> x * 2);
        System.out.println("doubled: " + doubled);

        // filter操作
        List<Integer> evens = numbers.filter(x -> x % 2 == 0);
        System.out.println("evens: " + evens);

        // fold操作（reduce）
        Integer sum = numbers.fold(0, (acc, x) -> acc + x);
        System.out.println("sum: " + sum);

        // flatMap操作
        List<Integer> duplicated = numbers.flatMap(x -> List.of(x, x));
        System.out.println("duplicated: " + duplicated);

        // take/drop操作
        List<Integer> first3 = numbers.take(3);
        List<Integer> skip2 = numbers.drop(2);
        System.out.println("first3: " + first3);
        System.out.println("skip2: " + skip2);

        // prepend/append
        List<Integer> extended = numbers.prepend(0).append(6);
        System.out.println("extended: " + extended);

        assertEquals(5, numbers.size());
        assertEquals(List.of(2, 4), evens);
        assertEquals(15, sum);
    }

    @Test
    @DisplayName("Stream - 遅延評価ストリーム")
    public void streamSample() {
        System.out.println("\n=== Stream サンプル ===");

        // 無限ストリーム
        Stream<Integer> infiniteStream = Stream.iterate(1, x -> x + 1);
        List<Integer> first10 = infiniteStream.take(10).toList();
        System.out.println("first10: " + first10);

        // フィボナッチ数列
        Stream<Integer> fibonacci = Stream.of(1, 1)
            .appendSelf(self -> self.zip(self.tail()).map(t -> t._1 + t._2));
        List<Integer> fib10 = fibonacci.take(10).toList();
        System.out.println("fibonacci: " + fib10);

        // 素数生成（エラトステネスの篩の簡単版）
        Stream<Integer> primes = Stream.iterate(2, x -> x + 1)
            .filter(this::isPrime);
        List<Integer> first10Primes = primes.take(10).toList();
        System.out.println("first10Primes: " + first10Primes);

        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), first10);
        assertEquals(List.of(1, 1, 2, 3, 5, 8, 13, 21, 34, 55), fib10);
    }

    @Test
    @DisplayName("Function - 高階関数")
    public void functionSample() {
        System.out.println("\n=== Function サンプル ===");

        // Function1（1引数関数）
        Function1<Integer, Integer> square = x -> x * x;
        Function1<Integer, String> toString = Object::toString;

        System.out.println("square(5): " + square.apply(5));

        // 関数合成
        Function1<Integer, String> squareAndToString = square.andThen(toString);
        System.out.println("squareAndToString(5): " + squareAndToString.apply(5));

        // Function2（2引数関数）
        Function2<Integer, Integer, Integer> add = (x, y) -> x + y;
        System.out.println("add(3, 4): " + add.apply(3, 4));

        // カリー化
        Function1<Integer, Function1<Integer, Integer>> curriedAdd = add.curried();
        Function1<Integer, Integer> add5 = curriedAdd.apply(5);
        System.out.println("add5(3): " + add5.apply(3));

        // 部分適用
        Function1<Integer, Integer> add10 = add.apply(10);
        System.out.println("add10(7): " + add10.apply(7));

        assertEquals(25, square.apply(5));
        assertEquals("25", squareAndToString.apply(5));
        assertEquals(8, add5.apply(3));
    }

    @Test
    @DisplayName("Tuple - 複数の値のペア")
    public void tupleSample() {
        System.out.println("\n=== Tuple サンプル ===");

        // Tuple2の作成
        Tuple2<String, Integer> person = Tuple.of("Alice", 30);
        System.out.println("person: " + person);

        // 値の取得
        String name = person._1;
        Integer age = person._2;
        System.out.println("name: " + name + ", age: " + age);

        // map操作
        Tuple2<String, Integer> upperCasePerson = person.map1(String::toUpperCase);
        Tuple2<String, Integer> olderPerson = person.map2(a -> a + 1);
        System.out.println("upperCasePerson: " + upperCasePerson);
        System.out.println("olderPerson: " + olderPerson);

        // Tupleのリスト
        List<Tuple2<String, Integer>> people = List.of(
            Tuple.of("Alice", 30),
            Tuple.of("Bob", 25),
            Tuple.of("Charlie", 35)
        );

        // 年齢でソート
        List<Tuple2<String, Integer>> sortedByAge = people.sortBy(person_ -> person_._2);
        System.out.println("sortedByAge: " + sortedByAge);

        assertEquals("Alice", person._1);
        assertEquals(Integer.valueOf(30), person._2);
    }

    @Test
    @DisplayName("実践的な使用例 - ユーザー管理システム")
    public void practicalExample() {
        System.out.println("\n=== 実践的な使用例 ===");

        // ユーザーデータ
        List<User> users = List.of(
            new User("1", "Alice", "alice@example.com", 30),
            new User("2", "Bob", "bob@example.com", 25),
            new User("3", "Charlie", "charlie@example.com", 35),
            new User("4", "Diana", null, 28) // nullのメール
        );

        // 30歳以上のユーザーのメールアドレスを取得（null安全）
        List<String> adultEmails = users
            .filter(user -> user.age() >= 30)
            .map(user -> Option.of(user.email()))
            .filter(Option::isDefined)
            .map(Option::get);

        System.out.println("30歳以上のメールアドレス: " + adultEmails);

        // ユーザー検索（Option使用）
        Option<User> foundUser = findUserById(users, "2");
        String result = foundUser
            .map(user -> "Found: " + user.name())
            .getOrElse("User not found");
        System.out.println("ユーザー検索結果: " + result);

        // 年齢の平均値計算（Try使用）
        Try<Double> averageAge = Try.of(() -> {
            List<Integer> ages = users.map(User::age);
            double sum = ages.fold(0, Integer::sum);
            return sum / users.size();
        });
        System.out.println("平均年齢: " + averageAge.getOrElse(0.0));

        assertEquals(2, adultEmails.size());
        assertTrue(foundUser.isDefined());
        assertTrue(averageAge.isSuccess());
    }

    // ヘルパーメソッド
    private boolean isPrime(int n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    private Option<User> findUserById(List<User> users, String id) {
        return users.find(user -> user.id().equals(id));
    }

    // ユーザークラス（record使用）
    public record User(String id, String name, String email, int age) {}
}