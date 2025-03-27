# 作業手順

## 1. OpenAPI 定義
OpenAPI の定義を作成し、`yaml` ファイルとして実装する。

## 2. ソースコードの自動生成
`openapi tools` の `openApiGenerate` を使用して、`yaml` ファイルからソースコードを生成する。

### 生成されるファイル
- `CommonApi.java`(他にも定義したGoods.javaなどができます)

## 3. コントローラーの実装
`CommonApiController` にて、`CommonApi` に実装されたメソッドをオーバーライドする。

## 4. 必要なファイルの作成
以下の 4 つの主要なコンポーネントを作成する。

- **Controller**
- **UseCase**
- **DataSource**（実装クラスとインターフェース）
- **Mapper**（実装クラスとインターフェース）

合計 **6 つのファイル**（インターフェースを含む）を作成する。

## 5. 動作確認
Gradleで
Tasks/application/bootRun 実行
![sample](img/response.pdf)

Postman を使用してリクエストを実行し、レスポンスが返り、オブジェクト（`Goods`）が取得できれば成功。



## 補足
Shift キーを 2 回押すことでファイル検索できます！


## Googleスライド
