# SPR プロジェクト Controller テストガイド

## 概要
このドキュメントは、SPRプロジェクトでControllerテストを作成する際の詳細なガイドラインです。Claude Codeに依頼する際の具体的な情報と手順をまとめています。

## プロジェクト固有情報

### アーキテクチャ概要
```
Controller (Presentation Layer)
    ↓ 依存
UseCase (Application Layer)
    ↓ 依存
DataSource (Infrastructure Layer)
```

### 技術スタック
- **テストフレームワーク**: JUnit 5 (`org.junit.jupiter.api.Test`)
- **Spring Boot Test**: `@WebMvcTest` でWebレイヤーのみテスト
- **MockMvc**: HTTPリクエスト・レスポンスのテスト
- **Mockito**: `@MockBean` でUseCaseをモック化
- **Jackson**: JSONシリアライゼーション (`ObjectMapper`)

### プロジェクト構造
```
src/
├── main/java/com/spr/
│   ├── presentation/           # Controller層
│   │   ├── CommonApi.java     # OpenAPI自動生成インターフェース
│   │   └── CommonApiController.java
│   ├── application/
│   │   ├── usecase/           # ビジネスロジック
│   │   │   ├── GetTaskUseCase.java
│   │   │   ├── GetGoodsUseCase.java
│   │   │   └── ...
│   │   └── dto/               # データ転送オブジェクト
│   │       ├── TaskDto.java
│   │       ├── GoodDto.java
│   │       └── ...
│   └── generated/model/       # OpenAPI自動生成モデル
│       ├── Task.java
│       ├── GetCommonTasksResponse.java
│       └── ...
└── test/java/com/spr/presentation/  # テストファイル配置場所
```

## エンドポイントパターン

### 既存のAPIパターン
1. **単体取得**: `GET /v1/anv/common/{resource}/{resource_id}`
2. **リスト取得**: `GET /v1/anv/common/{resource}?user_id={userId}`

### URLパターン例
```
GET /v1/anv/common/tasks/{task_id}      # 単一タスク取得
GET /v1/anv/common/tasks?user_id=1      # ユーザーのタスク一覧
```

## データモデル構造

### TaskDto（レコード形式）
```java
public record TaskDto(
    Integer taskId,
    Integer userId,
    String taskName,
    Boolean isDeleted
) {}
```

### GoodDto（レコード形式）
```java
public record GoodDto(
    Integer goodId,
    Integer userId,
    String goodsName,
    String size,
    String color,
    Long quantity,
    Boolean isDeleted
) {}
```

### 自動生成されるレスポンスモデル
- **Task.java**: `id`, `userId`, `taskName`
- **GetCommonTasksResponse.java**: リストレスポンスのラッパー

## テストパターン詳細

### 1. 基本テストクラス構造
```java
@WebMvcTest({ControllerClass}.class)
class {ControllerClass}Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private {UseCase} {useCaseField};

    @Autowired
    private ObjectMapper objectMapper;
}
```

### 2. 必須テストケース

#### A. 正常系テスト（単体取得）
```java
@Test
void get{Entity}_正常な{Entity}ID_{Entity}を返す() throws Exception {
    // 1. テストデータ準備（final + var宣言を使用）
    final var {entity}Id = 1;
    final var expected{Entity}Dto = new {DTO_CLASS_NAME}(...);

    // 2. MockMvc でリクエスト実行
    // 3. HTTPステータス・レスポンス内容検証
    // 4. モック呼び出し検証
}
```

#### B. 正常系テスト（リスト取得）
```java
@Test
void get{Entities}_正常なuserID_{Entity}リストを返す() throws Exception {
    // リスト形式のレスポンス検証（final + var宣言を使用）
    final var userId = 1;
    final var expected{Entity}List = Arrays.asList(...);
}
```

#### C. 例外処理テスト
```java
@Test
void get{Entity}_UseCaseが例外をスロー_例外が伝播される() throws Exception {
    // RuntimeException → ServletException へのラップを検証（final + var宣言を使用）
    final var {entity}Id = 999;
}
```

#### D. バリデーションテスト
```java
@Test
void get{Entity}_無効なパスパラメータ_バッドリクエストを返す() throws Exception {
    // 型変換エラーのテスト（文字列リテラルなのでfinal不要）
}
```

### 3. 検証パターン

#### HTTPレスポンス検証
```java
.andExpect(status().isOk())
.andExpect(content().contentType(MediaType.APPLICATION_JSON))
.andExpect(jsonPath("$.id").value(expectedId))
.andExpect(jsonPath("$.fieldName").value(expectedValue))
```

#### モック検証
```java
verify(useCase).execute(expectedParameter);
```

### 4. 例外処理の特殊パターン
```java
// Spring Bootでは未処理例外がServletExceptionでラップされる
try {
    mockMvc.perform(get("/api/path/{id}", invalidId));
} catch (Exception e) {
    assert e.getCause() instanceof RuntimeException;
    assert "Expected message".equals(e.getCause().getMessage());
}
```

## Claude Code依頼時の必須情報

### 1. 基本情報
- **対象Controller**: クラス名と場所
- **依存UseCase**: クラス名とメソッド名
- **DTO**: フィールド構造
- **エンドポイント**: URLパターンとHTTPメソッド

### 2. テスト要件
- **テストケース**: 必要なテストパターン
- **テストデータ**: サンプル値の指定
- **検証項目**: 確認すべきレスポンスフィールド

### 3. プロジェクト固有設定
- **パッケージ構造**: `com.spr.*`
- **APIベースパス**: `/v1/anv`
- **テスト配置場所**: `src/test/java/com/spr/presentation/`

### 4. コーディング規約
- **final修飾子の徹底使用**: 変数、引数、フィールドなど、再代入されないものには必ずfinal修飾子を宣言する
  - ローカル変数: `final var taskId = 1;`
  - DTOインスタンス: `final var expectedTaskDto = new TaskDto(...);`
  - メソッドパラメータ: メソッド定義時に可能な限りfinalを付与
  - フィールド: `@Autowired`や`@MockBean`でない限りfinalを使用

- **var宣言の積極的使用**: 型推論が可能な変数宣言では可能な限りvarを使用する
  - 基本原則: `final var 変数名 = 初期値;`
  - 例: `final var taskId = 1;` (Integer型として推論)
  - 例: `final var taskDto = new TaskDto(...);` (TaskDto型として推論)
  - 注意: メソッドパラメータや戻り値の型は明示的に記載する

## 依頼例テンプレート

```
以下の情報でControllerテストを作成してください：

【対象Controller】
- クラス名: CommonApiController
- 場所: src/main/java/com/spr/presentation/CommonApiController.java

【依存UseCase】
- クラス名: GetTaskUseCase
- メソッド: TaskDto execute(Integer taskId)

【エンドポイント】
- URL: GET /v1/anv/common/tasks/{task_id}
- パラメータ: task_id (Integer)
- レスポンス: Task (id, userId, taskName)

【テストケース】
1. 正常系: 有効なtask_idで正常なTaskを返す
2. 正常系: 異なるtask_idで対応するTaskを返す
3. 例外系: UseCaseが例外をスローする場合
4. 例外系: 無効なパスパラメータでバッドリクエスト
5. モック検証: UseCaseが正しく呼び出される

【テストデータ】
- TaskDto(1, 100, "TestTask", false)
- TaskDto(2, 200, "AnotherTask", false)

【コーディング要件】
- 再代入されない変数、引数、フィールドには可能な限りfinal修飾子を宣言すること
- 変数宣言では可能な限りvar宣言を使用すること（例: final var taskId = 1;）
```

## トラブルシューティング

### よくある問題

1. **URLパス不一致**
   - OpenAPI定義: `/common/tasks/{task_id}`
   - 実際のURL: `/v1/anv/common/tasks/{task_id}`
   - 解決: ベースパス `/v1/anv` を含む完全パスを使用

2. **例外処理テストの失敗**
   - 問題: `ServletException`がキャッチされない
   - 解決: try-catch文を使用して例外を直接検証

3. **JSON フィールドマッピングエラー**
   - 問題: DTOとレスポンスモデルのフィールド名不一致
   - 解決: 自動生成されたモデルクラスのフィールド名を確認

4. **MockBean 設定ミス**
   - 問題: 依存するUseCaseがモック化されていない
   - 解決: `@MockBean` アノテーションと正確なクラス名を確認

## パフォーマンス考慮事項

- `@WebMvcTest` でWebレイヤーのみテスト（高速）
- 必要最小限のBean読み込み
- インメモリテストでDB接続不要

## メンテナンス

### テンプレート更新時期
- 新しいエンドポイントパターン追加時
- 共通的な例外処理パターン変更時
- Spring Boot バージョンアップ時

### ドキュメント更新
- 新しいテストパターン発見時
- プロジェクト構造変更時
- 依頼時によくある質問が発生した場合