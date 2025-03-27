# 作業手順

# 達成すること
以下のプロパティを持つ`Goods`をレスポンスとして返却すること
- [userId]
- [goodsName]
- [size]
- [color]
- [quantity]

## 1. OpenAPI 定義
OpenAPI の定義を作成し、`yaml` ファイルを実装する。

## 2. ソースコードの自動生成
`openapi tools` の `openApiGenerate` を使用して、`yaml` ファイルからソースコードを生成する。
<img width="500px" src="https://github.com/user-attachments/assets/b0404173-0b06-4e4f-850e-9187f0cf3e52">

### 生成されるファイル
- `CommonApi.java`(他にも定義したGoods.javaなどができる)

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


Postman を使用してリクエストを実行し、レスポンスが返り、オブジェクト（`Goods`）が取得できれば成功。
ex.)Taskの場合
 `http://localhost:8080/v1/anv/common/tasks?user_id=1`
![sample](https://github.com/user-attachments/assets/62d7b305-d937-4ea6-a5ae-cc6f32939823)




## 補足
Shift キーを 2 回押すことでファイル検索できます！

## swagger-ui
http://localhost:8080/swagger-ui.html

## Googleスライド
https://docs.google.com/presentation/d/13S5mMOB2CYZx1iYkvbrfUTgaLAMhaiP9x2IFLgpWaIw/edit?usp=sharing
