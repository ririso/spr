# SPR (Spring REST API) プロジェクト解説

## プロジェクト概要
このプロジェクトは、OpenAPI 仕様に基づいてSpring Boot REST APIを開発するための学習プロジェクトです。OpenAPI定義からソースコードを自動生成し、Clean Architectureパターンに従ってTasks/Goodsのデータ取得APIを実装しています。

## 技術スタック
- **Java 21** - プログラミング言語
- **Spring Boot 3.3.4** - アプリケーションフレームワーク
- **OpenAPI Generator 7.8.0** - API仕様からコード自動生成
- **MyBatis 3.0.3** - データアクセス・ORM
- **SQL Server (Azure SQL Edge)** - データベース
- **Docker Compose** - ローカル開発環境
- **Lombok** - Javaコード簡素化
- **SpringDoc OpenAPI** - Swagger UI統合

## プロジェクト構造

```
spr/
├── openapi/                      # OpenAPI仕様定義
│   ├── query.yaml               # メインAPI定義
│   ├── components/              # 再利用可能な定義
│   │   ├── paths/              # エンドポイント定義
│   │   ├── responses/          # レスポンス定義
│   │   └── parameters/         # パラメータ定義
│   └── ...
├── src/main/java/com/spr/
│   ├── Application.java         # Spring Bootメインクラス
│   ├── presentation/            # プレゼンテーション層
│   │   ├── CommonApi.java      # 自動生成APIインターフェース
│   │   └── CommonApiController.java # APIコントローラー実装
│   ├── application/             # アプリケーション層
│   │   ├── usecase/            # ユースケース
│   │   └── dto/                # データ転送オブジェクト
│   ├── infrastructure/          # インフラストラクチャ層
│   │   ├── mapper/             # MyBatisマッパー
│   │   └── *DataSource*        # データソース実装
│   └── generated/               # 自動生成されたモデル
├── build.gradle                 # Gradleビルド設定
├── docker-compose.yml          # Docker環境設定
├── init.sql                    # DB初期化スクリプト
└── README.md / dev-README.md   # プロジェクト手順書
```

## データベース設計

### Tasksテーブル
| カラム名 | 型 | 説明 |
|---------|----|----|
| id | INT (PK, IDENTITY) | タスクID |
| userId | INT | ユーザーID |
| taskName | NVARCHAR(255) | タスク名 |
| isDeleted | BIT | 削除フラグ |

### Goodsテーブル
| カラム名 | 型 | 説明 |
|---------|----|----|
| id | INT (PK, IDENTITY) | 商品ID |
| userId | INT | ユーザーID |
| goodsName | NVARCHAR(255) | 商品名 |
| size | NVARCHAR(10) | サイズ |
| color | NVARCHAR(50) | 色 |
| quantity | INT | 数量 |
| isDeleted | BIT | 削除フラグ |

## アーキテクチャパターン

本プロジェクトでは **Clean Architecture** を採用しています：

### 1. プレゼンテーション層 (Presentation Layer)
- `CommonApiController.java` - RESTコントローラー
- OpenAPIから自動生成された`CommonApi`インターフェースを実装

### 2. アプリケーション層 (Application Layer)
- `GetTaskUseCase.java` / `GetGoodsUseCase.java` - ビジネスロジック
- `TaskDto.java` / `GoodDto.java` - データ転送オブジェクト

### 3. インフラストラクチャ層 (Infrastructure Layer)
- `TasksQueryDataSource.java` / `GoodsQueryDataSource.java` - データソースインターフェース
- `TasksQueryDataSourceImpl.java` / `GoodsQueryDataSourceImpl.java` - データソース実装
- `TasksMapper.java` / `GoodsMapper.java` - MyBatisマッパー

## 開発フロー

### 1. 環境構築
```bash
# Dockerでデータベース起動
docker compose up
```

### 2. OpenAPI定義作成
- `openapi/query.yaml` でAPIエンドポイントを定義
- `components/` 配下でレスポンス・パラメータを定義

### 3. コード自動生成
```bash
# Gradleタスクでコード生成
./gradlew openApiGenerate
```
生成されるファイル：
- `CommonApi.java` - APIインターフェース
- `Task.java`, `Goods.java` - モデルクラス
- その他レスポンス・エラーモデル

### 4. 実装
必要な6つのファイルを作成：
- Controller（1ファイル）
- UseCase（1ファイル）
- DataSource（インターフェース + 実装クラス = 2ファイル）
- Mapper（インターフェース + 実装クラス = 2ファイル）

### 5. 動作確認
```bash
# アプリケーション起動
./gradlew bootRun
```

- **API動作確認**: Postman等で `http://localhost:8080/v1/anv/common/tasks?user_id=1`
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`

## 学習ポイント

1. **OpenAPI First開発** - 仕様書からコード自動生成
2. **Clean Architecture** - レイヤー分離とDependency Inversion
3. **Spring Boot** - DIコンテナとRESTアノテーション
4. **MyBatis** - SQLマッパーとデータアクセス
5. **Docker** - ローカル開発環境の構築

## API仕様

### 現在実装されているエンドポイント

#### GET /v1/anv/common/tasks/{task_id}
単一タスクの取得

#### GET /v1/anv/common/tasks?user_id={user_id}
ユーザーのタスク一覧取得

### 学習課題: Goods API実装
以下のプロパティを持つ`Goods`APIを実装することが学習目標：
- userId（ユーザーID）
- goodsName（商品名）
- size（サイズ）
- color（色）
- quantity（数量）

## トラブルシューティング

### OpenAPI Generator実行時
- YAML定義変更後は既存の`CommonApi.java`を削除してから再実行
- `sourceFolder`設定により`src/main/java`が自動追加されない設定

### データベース接続
- Docker ComposeでSQL Server（Azure SQL Edge）を使用
- 接続先：`localhost:1533`
- 認証：`sa` / `Audrey123!`

## 参考資料
- [Googleスライド](https://docs.google.com/presentation/d/13S5mMOB2CYZx1iYkvbrfUTgaLAMhaiP9x2IFLgpWaIw/edit?usp=sharing)
- IntelliJ IDEA: `Shift×2`でファイル検索可能