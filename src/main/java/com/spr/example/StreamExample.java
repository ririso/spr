package com.spr.example;

import com.spr.application.dto.TaskDto;
import com.spr.application.dto.GoodDto;

import java.util.*;
import java.util.stream.Collectors;

/**
 * StreamAPI研修用サンプルコード
 * 基本的なStreamの処理方法と従来の書き方を比較して学習できます
 */
public class StreamExample {

    public static void main(String[] args) {
        // サンプルデータの準備
        final var tasks = Arrays.asList(
            new TaskDto(1, 100, "レポート作成", false),
            new TaskDto(2, 100, "会議準備", false),
            new TaskDto(3, 200, "企画書作成", false),
            new TaskDto(4, 100, "削除されたタスク", true),
            new TaskDto(5, 200, "プレゼン準備", false),
            new TaskDto(6, 300, "システム設計", false)
        );

        final var goods = Arrays.asList(
            new GoodDto(1, 100, "ノートPC", "15inch", "Black", 5L, false),
            new GoodDto(2, 100, "マウス", "M", "White", 10L, false),
            new GoodDto(3, 200, "キーボード", "L", "Black", 3L, false),
            new GoodDto(4, 100, "削除された商品", "S", "Red", 0L, true),
            new GoodDto(5, 200, "モニター", "24inch", "Black", 2L, false)
        );

        System.out.println("=== StreamAPI研修用サンプル ===\n");

        // 1. フィルタリング（filter）
        filterExample(tasks);

        // 2. マッピング（map）
        mapExample(tasks);

        // 3. 収集（collect）
        collectExample(tasks);

        // 4. 検索（findFirst, anyMatch）
        findExample(tasks);

        // 5. 集計（count, sum, max）
        aggregateExample(goods);

        // 6. グループ化（groupingBy）
        groupingExample(tasks);

        // 7. ソート（sorted）
        sortExample(goods);

        // 8. 複合操作
        complexExample(tasks, goods);
    }

    /**
     * 1. フィルタリング（filter）
     * 条件に合う要素のみを抽出する
     */
    private static void filterExample(List<TaskDto> tasks) {
        System.out.println("=== 1. フィルタリング（filter） ===");

        // Stream版：削除されていないタスクのみ抽出
        final var activeTasks = tasks.stream()
            .filter(task -> !task.isDeleted())
            .collect(Collectors.toList());

        System.out.println("Stream版 - 削除されていないタスク数: " + activeTasks.size());

        /* 従来の書き方
        List<TaskDto> activeTasksOld = new ArrayList<>();
        for (TaskDto task : tasks) {
            if (!task.isDeleted()) {
                activeTasksOld.add(task);
            }
        }
        System.out.println("従来版 - 削除されていないタスク数: " + activeTasksOld.size());
        */

        // 特定のユーザーのタスクのみ抽出
        final var user100Tasks = tasks.stream()
            .filter(task -> task.userId().equals(100))
            .filter(task -> !task.isDeleted())
            .collect(Collectors.toList());

        System.out.println("ユーザー100の有効タスク数: " + user100Tasks.size());
        System.out.println();
    }

    /**
     * 2. マッピング（map）
     * 要素を別の形に変換する
     */
    private static void mapExample(List<TaskDto> tasks) {
        System.out.println("=== 2. マッピング（map） ===");

        // Stream版：タスク名のリストを取得
        final var taskNames = tasks.stream()
            .filter(task -> !task.isDeleted())
            .map(TaskDto::taskName)
            .collect(Collectors.toList());

        System.out.println("Stream版 - タスク名一覧: " + taskNames);

        /* 従来の書き方
        List<String> taskNamesOld = new ArrayList<>();
        for (TaskDto task : tasks) {
            if (!task.isDeleted()) {
                taskNamesOld.add(task.taskName());
            }
        }
        System.out.println("従来版 - タスク名一覧: " + taskNamesOld);
        */

        // タスク名を大文字に変換
        final var upperCaseNames = tasks.stream()
            .filter(task -> !task.isDeleted())
            .map(TaskDto::taskName)
            .map(String::toUpperCase)
            .collect(Collectors.toList());

        System.out.println("大文字変換: " + upperCaseNames);
        System.out.println();
    }

    /**
     * 3. 収集（collect）
     * StreamをListやSetなどのコレクションに変換
     */
    private static void collectExample(List<TaskDto> tasks) {
        System.out.println("=== 3. 収集（collect） ===");

        // Stream版：ユーザーIDの重複なしリスト
        final var userIds = tasks.stream()
            .map(TaskDto::userId)
            .collect(Collectors.toSet());

        System.out.println("Stream版 - ユニークなユーザーID: " + userIds);

        /* 従来の書き方
        Set<Integer> userIdsOld = new HashSet<>();
        for (TaskDto task : tasks) {
            userIdsOld.add(task.userId());
        }
        System.out.println("従来版 - ユニークなユーザーID: " + userIdsOld);
        */

        // カンマ区切りの文字列に変換
        final var taskNamesString = tasks.stream()
            .filter(task -> !task.isDeleted())
            .map(TaskDto::taskName)
            .collect(Collectors.joining(", "));

        System.out.println("タスク名をカンマ区切り: " + taskNamesString);
        System.out.println();
    }

    /**
     * 4. 検索（findFirst, anyMatch）
     * 条件に合う要素を検索する
     */
    private static void findExample(List<TaskDto> tasks) {
        System.out.println("=== 4. 検索（findFirst, anyMatch） ===");

        // Stream版：最初に見つかった特定ユーザーのタスク
        final var firstTask = tasks.stream()
            .filter(task -> task.userId().equals(100))
            .filter(task -> !task.isDeleted())
            .findFirst();

        System.out.println("Stream版 - ユーザー100の最初のタスク: " +
            firstTask.map(TaskDto::taskName).orElse("なし"));

        /* 従来の書き方
        TaskDto firstTaskOld = null;
        for (TaskDto task : tasks) {
            if (task.userId().equals(100) && !task.isDeleted()) {
                firstTaskOld = task;
                break;
            }
        }
        System.out.println("従来版 - ユーザー100の最初のタスク: " +
            (firstTaskOld != null ? firstTaskOld.taskName() : "なし"));
        */

        // 特定の条件に合うタスクが存在するか
        final var hasReportTask = tasks.stream()
            .anyMatch(task -> task.taskName().contains("レポート"));

        System.out.println("レポート関連のタスクがあるか: " + hasReportTask);
        System.out.println();
    }

    /**
     * 5. 集計（count, sum, max）
     * 数値の集計処理
     */
    private static void aggregateExample(List<GoodDto> goods) {
        System.out.println("=== 5. 集計（count, sum, max） ===");

        // Stream版：有効な商品の総在庫数
        final var totalQuantity = goods.stream()
            .filter(good -> !good.isDeleted())
            .mapToLong(GoodDto::quantity)
            .sum();

        System.out.println("Stream版 - 総在庫数: " + totalQuantity);

        /* 従来の書き方
        long totalQuantityOld = 0;
        for (GoodDto good : goods) {
            if (!good.isDeleted()) {
                totalQuantityOld += good.quantity();
            }
        }
        System.out.println("従来版 - 総在庫数: " + totalQuantityOld);
        */

        // 最大在庫数
        final var maxQuantity = goods.stream()
            .filter(good -> !good.isDeleted())
            .mapToLong(GoodDto::quantity)
            .max();

        System.out.println("最大在庫数: " + maxQuantity.orElse(0));

        // 平均在庫数
        final var avgQuantity = goods.stream()
            .filter(good -> !good.isDeleted())
            .mapToLong(GoodDto::quantity)
            .average()
            .orElse(0.0);

        System.out.println("平均在庫数: " + String.format("%.1f", avgQuantity));
        System.out.println();
    }

    /**
     * 6. グループ化（groupingBy）
     * 特定の条件でグループ分けする
     */
    private static void groupingExample(List<TaskDto> tasks) {
        System.out.println("=== 6. グループ化（groupingBy） ===");

        // Stream版：ユーザーIDでグループ化
        final var tasksByUser = tasks.stream()
            .filter(task -> !task.isDeleted())
            .collect(Collectors.groupingBy(TaskDto::userId));

        System.out.println("Stream版 - ユーザー別タスク数:");
        tasksByUser.forEach((userId, userTasks) ->
            System.out.println("  ユーザー" + userId + ": " + userTasks.size() + "件"));

        /* 従来の書き方
        Map<Integer, List<TaskDto>> tasksByUserOld = new HashMap<>();
        for (TaskDto task : tasks) {
            if (!task.isDeleted()) {
                tasksByUserOld.computeIfAbsent(task.userId(), k -> new ArrayList<>()).add(task);
            }
        }
        System.out.println("従来版 - ユーザー別タスク数:");
        for (Map.Entry<Integer, List<TaskDto>> entry : tasksByUserOld.entrySet()) {
            System.out.println("  ユーザー" + entry.getKey() + ": " + entry.getValue().size() + "件");
        }
        */

        // ユーザー別のタスク数をカウント
        final var taskCountByUser = tasks.stream()
            .filter(task -> !task.isDeleted())
            .collect(Collectors.groupingBy(TaskDto::userId, Collectors.counting()));

        System.out.println("ユーザー別タスク数（カウント版）: " + taskCountByUser);
        System.out.println();
    }

    /**
     * 7. ソート（sorted）
     * 要素を並び替える
     */
    private static void sortExample(List<GoodDto> goods) {
        System.out.println("=== 7. ソート（sorted） ===");

        // Stream版：在庫数で降順ソート
        final var sortedGoods = goods.stream()
            .filter(good -> !good.isDeleted())
            .sorted((g1, g2) -> Long.compare(g2.quantity(), g1.quantity()))
            .collect(Collectors.toList());

        System.out.println("Stream版 - 在庫数降順:");
        sortedGoods.forEach(good ->
            System.out.println("  " + good.goodsName() + ": " + good.quantity() + "個"));

        /* 従来の書き方
        List<GoodDto> sortedGoodsOld = new ArrayList<>();
        for (GoodDto good : goods) {
            if (!good.isDeleted()) {
                sortedGoodsOld.add(good);
            }
        }
        sortedGoodsOld.sort((g1, g2) -> Long.compare(g2.quantity(), g1.quantity()));
        System.out.println("従来版 - 在庫数降順:");
        for (GoodDto good : sortedGoodsOld) {
            System.out.println("  " + good.goodsName() + ": " + good.quantity() + "個");
        }
        */

        // 商品名でアルファベット順ソート
        final var sortedNames = goods.stream()
            .filter(good -> !good.isDeleted())
            .map(GoodDto::goodsName)
            .sorted()
            .collect(Collectors.toList());

        System.out.println("商品名アルファベット順: " + sortedNames);
        System.out.println();
    }

    /**
     * 8. 複合操作
     * 複数のStream操作を組み合わせた実用的な例
     */
    private static void complexExample(List<TaskDto> tasks, List<GoodDto> goods) {
        System.out.println("=== 8. 複合操作 ===");

        // 複雑なクエリ：ユーザー100の有効タスク名を大文字に変換してカンマ区切りで取得
        final var user100TaskNames = tasks.stream()
            .filter(task -> task.userId().equals(100))
            .filter(task -> !task.isDeleted())
            .map(TaskDto::taskName)
            .map(String::toUpperCase)
            .sorted()
            .collect(Collectors.joining(", "));

        System.out.println("ユーザー100のタスク（大文字・ソート済み）: " + user100TaskNames);

        // 在庫が5個以上の商品の平均在庫数
        final var highStockAverage = goods.stream()
            .filter(good -> !good.isDeleted())
            .filter(good -> good.quantity() >= 5L)
            .mapToLong(GoodDto::quantity)
            .average()
            .orElse(0.0);

        System.out.println("在庫5個以上の商品の平均在庫数: " + String.format("%.1f", highStockAverage));

        // 最も在庫の多い商品のユーザーID
        final var topStockUserId = goods.stream()
            .filter(good -> !good.isDeleted())
            .max(Comparator.comparing(GoodDto::quantity))
            .map(GoodDto::userId);

        System.out.println("最大在庫商品のユーザーID: " + topStockUserId.orElse(-1));
        System.out.println();
    }
}