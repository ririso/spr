# 作業手順

## 起動前に下記のコマンドを実行
※dockerを起動した状態かつプロジェクト配下で下記コマンドを実行する
**docker compose up**

# 達成すること
APIを二つ作成する
1つ目は単一の`User`をレスポンスとして返却するAPI
2つ目は複数の`User`をレスポンスとして返却するAPI
※いずれも`User`は以下のプロパティを返す

- [userId]
- [userName]
- [age]
- [department]

## 1. OpenAPI 定義
定義を作成し、`query.yaml` ファイルにエンドポイントを記述する。

まずは`query.yaml`に指定のエンドポイントを定義する。



responseとparametersのyamlファイルを新規で作成して 参照させる。
※/common/tasks/{task_id}を参考にする

## 2. ソースコードの自動生成
OpenAPI 定義の実装完了後
画面右側のGradleタブから
`openapi tools` の `openApiGenerate` を使用して、`yaml` ファイルからソースコードを生成する。
例)Taskを生成する場合
1.Task.yamlを定義するとTask.javaが自動生成される
Task.yaml →　java/com/spr/generated/model/Task.java


<img width="500px" src="https://github.com/user-attachments/assets/b0404173-0b06-4e4f-850e-9187f0cf3e52">

### 生成されるファイル
- `CommonApi.java`(他にも定義したUser.javaなどができる)
※もしyamlの定義に変更が合った場合は一度`CommonApi.java`を削除して`openApiGenerate`を再実行する

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


Postman を使用してリクエストを実行し、レスポンスが返り、オブジェクト（`User`）が取得できれば成功。
ex.)Taskの場合
 `http://localhost:8080/v1/anv/common/tasks?user_id=1`
![sample](https://github.com/user-attachments/assets/62d7b305-d937-4ea6-a5ae-cc6f32939823)




## 補足
Shift キーを 2 回押すことでファイル検索できます！

## swagger-ui
http://localhost:8080/swagger-ui.html

## Googleスライド
https://docs.google.com/presentation/d/13S5mMOB2CYZx1iYkvbrfUTgaLAMhaiP9x2IFLgpWaIw/edit?usp=sharing
