Feature: ログイン
  上田さんはパスワードを忘れがち。
  ログインに失敗したとき「何が間違っているか」が具体的にわかることが重要。

  Background:
    Given ログインページを開いている

  # ── 正常系 ──────────────────────────────

  Scenario: 正しい認証情報でログインできる
    When メールアドレスに "ichiro@example.com" を入力する
    And パスワードに "password" を入力する
    And ログインボタンをクリックする
    Then マイページに遷移する

  Scenario: ログアウトするとトップページに戻る
    Given "ichiro@example.com" でログインしている
    When マイページでログアウトボタンをクリックする
    Then トップページに遷移する

  # ── 異常系 ──────────────────────────────

  Scenario: 間違ったパスワードではログインできない
    When メールアドレスに "ichiro@example.com" を入力する
    And パスワードに "wrongpassword" を入力する
    And ログインボタンをクリックする
    Then ログインエラーが表示される

  Scenario: 存在しないメールアドレスではログインできない
    When メールアドレスに "notexist@example.com" を入力する
    And パスワードに "password" を入力する
    And ログインボタンをクリックする
    Then ログインエラーが表示される

  # ── セキュリティ ─────────────────────────
  # メール不存在とパスワード誤りで同じエラーメッセージが出ることを確認する
  # （アカウントの存在を外部に漏らさないため）

  @security
  Scenario: メールアドレス不存在とパスワード誤りで同じエラーメッセージが返る
    When メールアドレスに "notexist@example.com" を入力する
    And パスワードに "password" を入力する
    And ログインボタンをクリックする
    Then ログインエラーメッセージを記録する
    When メールアドレスに "ichiro@example.com" を入力する
    And パスワードに "wrongpassword" を入力する
    And ログインボタンをクリックする
    Then ログインエラーメッセージが先ほどと同じである

  # ── 欠けている機能（未実装確認） ───────────

  @missing-feature
  Scenario: パスワードを忘れたときにリセットできる
    When パスワードを忘れた場合のリンクをクリックする
    Then パスワードリセットページに遷移する
