# SPR プロジェクト UseCase テストガイド

## 概要
このドキュメントは、SPRプロジェクトでUseCaseテストを作成する際の詳細なガイドラインです。Clean ArchitectureのApplication Layer（UseCase）のテストに特化した情報と手順をまとめています。

## UseCase アーキテクチャ概要

### 役割と責務
```
Controller (Presentation Layer)
    ↓ 呼び出し
UseCase (Application Layer) ← ここをテスト
    ↓ 依存
DataSource (Infrastructure Layer)
```

**UseCaseの責務：**
- ビジネスロジックの実装
- DataSourceからのデータ取得・操作
- DTOの変換・加工
- トランザクション境界の定義

## 技術スタック

### テストフレームワーク
- **JUnit 5**: `org.junit.jupiter.api.Test`
- **Mockito Extension**: `@ExtendWith(MockitoExtension.class)`
- **AssertJ**: `assertThat()` による流暢なアサーション
- **Mockito**: `@Mock`, `@InjectMocks` によるDI

### 依存関係パターン
```java
@Repository
@RequiredArgsConstructor
public class GetTaskUseCase {
    private final TasksQueryDataSource tasksQueryDataSource;

    public TaskDto execute(Integer taskId) {
        return tasksQueryDataSource.getTask(taskId);
    }
}
```

## プロジェクト構造

### UseCase パッケージ構造
```
src/
├── main/java/com/spr/
│   ├── application/
│   │   ├── usecase/           # UseCaseクラス
│   │   │   ├── GetTaskUseCase.java
│   │   │   ├── GetGoodsUseCase.java
│   │   │   ├── GetTasksUseCase.java
│   │   │   └── ...
│   │   └── dto/               # データ転送オブジェクト
│   │       ├── TaskDto.java
│   │       └── GoodDto.java
│   └── infrastructure/        # DataSource インターフェース
│       ├── TasksQueryDataSource.java
│       └── GoodsQueryDataSource.java
└── test/java/com/spr/application/usecase/  # テストファイル配置場所
```

## UseCaseパターン分析

### パターン1: 単体取得UseCase
```java
public class GetTaskUseCase {
    public TaskDto execute(Integer taskId) {
        return tasksQueryDataSource.getTask(taskId);
    }
}
```

### パターン2: リスト取得UseCase
```java
public class GetGoodsUseCase {
    public List<GoodDto> execute(Integer userId) {
        return goodsQueryDataSource.getGoods(userId);
    }
}
```

## テストパターン詳細

### 1. 基本テストクラス構造
```java
@ExtendWith(MockitoExtension.class)
class GetTaskUseCaseTest {

    @Mock
    private TasksQueryDataSource tasksQueryDataSource;

    @InjectMocks
    private GetTaskUseCase getTaskUseCase;
}
```

### 2. 必須テストケース

#### A. 正常系テスト（単体取得）
```java
@Test
void execute_正常なタスクID_タスクを返す() {
    // Arrange - final + var宣言を使用
    final var taskId = 1;
    final var expectedTaskDto = new TaskDto(1, 100, "TestTask", false);
    when(tasksQueryDataSource.getTask(taskId)).thenReturn(expectedTaskDto);

    // Act
    final var actual = getTaskUseCase.execute(taskId);

    // Assert
    assertThat(actual).isEqualTo(expectedTaskDto);
    assertThat(actual.taskId()).isEqualTo(1);
    assertThat(actual.taskName()).isEqualTo("TestTask");

    // Verify
    verify(tasksQueryDataSource).getTask(taskId);
}
```

#### B. 正常系テスト（リスト取得）
```java
@Test
void execute_正常なuserID_タスクリストを返す() {
    // Arrange - final + var宣言を使用
    final var userId = 1;
    final var expectedTaskList = Arrays.asList(
        new TaskDto(1, 1, "Task1", false),
        new TaskDto(2, 1, "Task2", false)
    );
    when(tasksQueryDataSource.getTasks(userId)).thenReturn(expectedTaskList);

    // Act
    final var actual = getTaskUseCase.execute(userId);

    // Assert
    assertThat(actual).hasSize(2);
    assertThat(actual).containsExactlyElementsOf(expectedTaskList);

    // Verify
    verify(tasksQueryDataSource).getTasks(userId);
}
```

#### C. 例外処理テスト
```java
@Test
void execute_DataSourceが例外をスロー_例外が伝播される() {
    // Arrange - final + var宣言を使用
    final var taskId = 999;
    final var expectedException = new RuntimeException("Task not found");
    when(tasksQueryDataSource.getTask(taskId)).thenThrow(expectedException);

    // Act & Assert
    assertThatThrownBy(() -> getTaskUseCase.execute(taskId))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Task not found");

    // Verify
    verify(tasksQueryDataSource).getTask(taskId);
}
```

#### D. 境界値テスト
```java
@Test
void execute_存在しないID_nullを返す() {
    // Arrange - final + var宣言を使用
    final var taskId = -1;
    when(tasksQueryDataSource.getTask(taskId)).thenReturn(null);

    // Act
    final var actual = getTaskUseCase.execute(taskId);

    // Assert
    assertThat(actual).isNull();

    // Verify
    verify(tasksQueryDataSource).getTask(taskId);
}
```

### 3. アサーションパターン

#### AssertJを使用した検証
```java
// 単一オブジェクトの検証
assertThat(actual).isEqualTo(expected);
assertThat(actual.taskId()).isEqualTo(1);
assertThat(actual.taskName()).isNotNull();

// リストの検証
assertThat(actualList).hasSize(2);
assertThat(actualList).isEmpty();
assertThat(actualList).containsExactlyElementsOf(expectedList);
assertThat(actualList.get(0).taskId()).isEqualTo(1);

// 例外の検証
assertThatThrownBy(() -> useCase.execute(invalidId))
        .isInstanceOf(RuntimeException.class)
        .hasMessage("Expected message");
```

### 4. モック検証パターン
```java
// メソッド呼び出しの検証
verify(dataSource).getTask(taskId);
verify(dataSource).getTasks(userId);

// 呼び出し回数の検証
verify(dataSource, times(1)).getTask(taskId);
verify(dataSource, never()).getTask(anyInt());

// 引数の検証
verify(dataSource).getTask(eq(1));
verify(dataSource).getTasks(any(Integer.class));
```

## データモデル構造（UseCase観点）

### TaskDto（レコード形式）
```java
public record TaskDto(
    Integer taskId,     // アクセス: taskDto.taskId()
    Integer userId,     // アクセス: taskDto.userId()
    String taskName,    // アクセス: taskDto.taskName()
    Boolean isDeleted   // アクセス: taskDto.isDeleted()
) {}
```

### GoodDto（レコード形式）
```java
public record GoodDto(
    Integer goodId,     // アクセス: goodDto.goodId()
    Integer userId,     // アクセス: goodDto.userId()
    String goodsName,   // アクセス: goodDto.goodsName()
    String size,        // アクセス: goodDto.size()
    String color,       // アクセス: goodDto.color()
    Long quantity,      // アクセス: goodDto.quantity()
    Boolean isDeleted   // アクセス: goodDto.isDeleted()
) {}
```

## コーディング規約

### 1. final修飾子の徹底使用
- **ローカル変数**: `final var taskId = 1;`
- **DTOインスタンス**: `final var taskDto = new TaskDto(...);`
- **リスト**: `final var taskList = Arrays.asList(...);`

### 2. var宣言の積極的使用
- **基本原則**: `final var 変数名 = 初期値;`
- **例**: `final var taskId = 1;` (Integer型として推論)
- **例**: `final var taskDto = new TaskDto(...);` (TaskDto型として推論)

### 3. テストメソッド命名規約
```java
void execute_入力条件_期待される結果() {
    // テスト実装
}
```

**例:**
- `execute_正常なタスクID_タスクを返す()`
- `execute_存在しないID_nullを返す()`
- `execute_DataSourceが例外をスロー_例外が伝播される()`

## Claude Code依頼時の必須情報

### 1. 基本情報
- **対象UseCase**: クラス名、メソッド名、戻り値型
- **依存DataSource**: インターフェース名とメソッド
- **DTO**: フィールド構造とアクセサメソッド
- **ビジネスロジック**: UseCaseの責務と処理内容

### 2. テスト要件
- **テストケース**: 正常系、異常系、境界値のパターン
- **テストデータ**: サンプル値とエッジケース
- **検証項目**: 確認すべきDTOフィールドと条件

### 3. プロジェクト固有設定
- **パッケージ構造**: `com.spr.application.usecase.*`
- **テスト配置場所**: `src/test/java/com/spr/application/usecase/`
- **アノテーション**: `@ExtendWith(MockitoExtension.class)`, `@Mock`, `@InjectMocks`

## 依頼例テンプレート

```
以下の情報でUseCaseテストを作成してください：

【対象UseCase】
- クラス名: GetTaskUseCase
- 場所: src/main/java/com/spr/application/usecase/GetTaskUseCase.java
- メソッド: TaskDto execute(Integer taskId)

【依存DataSource】
- インターフェース名: TasksQueryDataSource
- メソッド: TaskDto getTask(Integer taskId)

【DTO】
- クラス名: TaskDto
- フィールド: taskId (Integer), userId (Integer), taskName (String), isDeleted (Boolean)
- アクセサ: taskDto.taskId(), taskDto.userId(), taskDto.taskName(), taskDto.isDeleted()

【ビジネスロジック】
- 単純なデータ取得（変換・加工なし）
- DataSourceから取得した結果をそのまま返却

【テストケース】
1. 正常系: 有効なtaskIdで正常なTaskDtoを返す
2. 正常系: 異なるtaskIdで対応するTaskDtoを返す
3. 異常系: DataSourceが例外をスローする場合
4. 境界値: 存在しないIDでnullを返す場合
5. 境界値: nullのIDを渡す場合
6. モック検証: DataSourceが正しく呼び出される

【テストデータ】
- TaskDto(1, 100, "TestTask", false)
- TaskDto(2, 200, "AnotherTask", false)

【コーディング要件】
- 再代入されない変数、引数、フィールドには可能な限りfinal修飾子を宣言すること
- 変数宣言では可能な限りvar宣言を使用すること（例: final var taskId = 1;）
```

## トラブルシューティング

### よくある問題

1. **モック設定の不備**
   - 問題: `when().thenReturn()` の設定ミス
   - 解決: 引数の型と値を正確に指定

2. **アサーション対象の間違い**
   - 問題: DTOのフィールドアクセスでメソッド名ミス
   - 解決: レコードクラスのアクセサメソッド名を確認 (`taskDto.taskId()`)

3. **例外テストの失敗**
   - 問題: `assertThatThrownBy()` の使用方法
   - 解決: ラムダ式内でメソッド実行、チェーン式で例外検証

4. **MockitoExtension設定忘れ**
   - 問題: `@Mock`, `@InjectMocks` が機能しない
   - 解決: クラスレベルに `@ExtendWith(MockitoExtension.class)` を追加

## ビジネスロジックテストの拡張

### 複雑なUseCaseのテストパターン
```java
// データ変換がある場合
@Test
void execute_データ変換_期待される形式で返す() {
    // 変換前後のデータを検証
}

// 条件分岐がある場合
@Test
void execute_削除フラグがtrue_結果に含まれない() {
    // 条件に応じた結果を検証
}

// 複数DataSource呼び出しがある場合
@Test
void execute_複数DataSource呼び出し_すべて正しく実行される() {
    // 複数のモック検証
}
```

## パフォーマンス考慮事項

- **単体テスト**: Infrastructure層をモック化（高速）
- **軽量**: SpringContextの読み込み不要
- **独立性**: 各テストが完全に独立

## メンテナンス

### テンプレート更新時期
- 新しいUseCaseパターン追加時
- 共通的なビジネスロジック変更時
- テストフレームワークバージョンアップ時

### ドキュメント更新
- 新しいテストパターン発見時
- DTO構造変更時
- 依頼時によくある質問が発生した場合