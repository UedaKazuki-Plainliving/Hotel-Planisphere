Feature: マイページ
  上田さんは自分の登録情報・会員ランクを確認したい。
  また、情報が変わったときに更新できることが必要。

  Background:
    Given "ichiro@example.com" でログインしている

  # ── 正常系 ──────────────────────────────

  Scenario: ログイン後にマイページで登録情報が確認できる
    When マイページを開く
    Then メールアドレスが表示されている
    And 氏名が表示されている
    And 会員ランクが表示されている

  Scenario: プレミアム会員の会員ランクがマイページに表示される
    Given "ichiro@example.com" でログインしている
    When マイページを開く
    Then マイページに "プレミアム会員" と表示される

  # ── 欠けている機能（未実装確認） ───────────

  @missing-feature
  Scenario: マイページから登録情報を編集できる
    When マイページを開く
    Then 情報編集ボタンが表示されている

  @missing-feature
  Scenario: マイページから過去の予約履歴が確認できる
    When マイページを開く
    Then 予約履歴セクションが表示されている
