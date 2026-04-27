package com.hotel.planisphere.data;

/**
 * テストデータ一元管理クラス。
 * セレクタは page パッケージ、テストロジックは steps パッケージに置く。
 * このクラスはテストで使う値のみを保持する。
 */
public final class TestData {

    private TestData() {}

    /** 登録済みプレミアム会員 */
    public static final class PremiumUser {
        public static final String EMAIL    = "ichiro@example.com";
        public static final String PASSWORD = "password";
        public static final String RANK     = "プレミアム会員";
    }

    /** 登録済み一般会員 */
    public static final class StandardUser {
        public static final String EMAIL    = "sakura@example.com";
        public static final String PASSWORD = "password";
        public static final String RANK     = "一般会員";
    }

    /** 新規登録ユーザー（上田 和樹） */
    public static final class NewUser {
        public static final String EMAIL              = "ueda@example.com";
        public static final String PASSWORD           = "password123";
        public static final String NAME               = "上田 和樹";
        public static final String PASSWORD_TOO_SHORT = "pass";
        public static final String PASSWORD_MISMATCH  = "different456";
        public static final String PHONE_INVALID      = "0120123";
    }

    /** ログイン失敗シナリオ用 */
    public static final class InvalidCredentials {
        public static final String UNKNOWN_EMAIL   = "notexist@example.com";
        public static final String WRONG_PASSWORD  = "wrongpassword";
    }

    /** バリデーションエラーメッセージ */
    public static final class ErrorMessages {
        public static final String PASSWORD_TOO_SHORT    = "8文字以上で入力してください";
        public static final String PHONE_INVALID_FORMAT  = "指定されている形式で入力してください";
        public static final String PASSWORD_MISMATCH     = "入力されたパスワードと一致しません";
        public static final String REQUIRED_FIELD        = "このフィールドを入力してください";
        public static final String LOGIN_FAILED          = "メールアドレスまたはパスワードが違います";
        public static final String DATE_PAST             = "翌日以降の日付を入力してください";
        public static final String DATE_TOO_FAR          = "3ヶ月以内の日付を入力してください";
    }

    /** 予約フォームデータ */
    public static final class Reservation {
        public static final String GUEST_NAME    = "上田 和樹";
        public static final String CONTACT_NONE  = "希望しない";
        public static final String BREAKFAST     = "朝食バイキング";
        public static final String EARLY_CHECKIN = "昼からチェックインプラン";
        public static final String SIGHTSEEING   = "お得な観光プラン";
    }

    /** レスポンシブテスト用ビューポート */
    public static final int MOBILE_VIEWPORT_WIDTH  = 375;
    public static final int MOBILE_VIEWPORT_HEIGHT = 667;

    /** パフォーマンス要件しきい値（ミリ秒） */
    public static final int PERFORMANCE_TIMEOUT_MS = 3000;

    /** ScenarioContext キー */
    public static final class Keys {
        public static final String PLAN_COUNT_BEFORE = "planCountBefore";
        public static final String TOTAL_BEFORE      = "totalBefore";
        public static final String LOGGED_IN_EMAIL   = "loggedInEmail";
    }
}
