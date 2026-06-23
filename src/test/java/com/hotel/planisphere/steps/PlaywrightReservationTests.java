package com.hotel.planisphere.steps;

import com.hotel.planisphere.context.PlaywrightContext;
import com.hotel.planisphere.page.PlansPage;
import com.hotel.planisphere.page.ReservationPageHelper;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("宿泊予約 - Playwright バリデーションテスト")
public class PlaywrightReservationTests {

    private Page page;

    @BeforeEach
    public void setUp() {
        page = PlaywrightContext.page();
        PlansPage.navigate();
        // 予約ページを開く
        PlansPage.clickReserveButton();
    }

    @Test
    @DisplayName("UT-014: 明日の日付は入力可能")
    public void testTomorrowDateAccepted() {
        String tomorrow = ReservationPageHelper.getTomorrowDate();
        ReservationPageHelper.fillCheckInDate(tomorrow);

        assertEquals(0, page.locator(".invalid-feedback").count(),
                "エラーメッセージが表示されています");
        assertTrue(ReservationPageHelper.getTotalAmount() > 0,
                "合計金額が表示されていません");
    }

    @Test
    @DisplayName("UT-015: 過去の日付でエラー")
    public void testPastDateError() {
        String yesterday = ReservationPageHelper.getYesterdayDate();
        ReservationPageHelper.fillCheckInDate(yesterday);
        ReservationPageHelper.fillNights("1");
        ReservationPageHelper.fillHeadCount("1");
        ReservationPageHelper.fillGuestName("テスト 太郎");
        ReservationPageHelper.selectContact("希望しない");
        ReservationPageHelper.submit();

        assertTrue(ReservationPageHelper.hasDateError(),
                "宿泊日エラーが表示されていません");
    }

    @Test
    @DisplayName("UT-016: 3ヶ月後の日付は入力可能")
    public void test3MonthsLaterAccepted() {
        String threeMonthsLater = ReservationPageHelper.get3MonthsLaterDate();
        ReservationPageHelper.fillCheckInDate(threeMonthsLater);
        ReservationPageHelper.fillNights("1");
        ReservationPageHelper.fillHeadCount("1");
        ReservationPageHelper.fillGuestName("テスト 太郎");
        ReservationPageHelper.selectContact("希望しない");
        ReservationPageHelper.submit();

        assertTrue(ReservationPageHelper.isConfirmDialogVisible(),
                "予約確認ダイアログが表示されていません");
    }

    @Test
    @DisplayName("UT-017: 3ヶ月と1日後でエラー")
    public void test4MonthsLaterError() {
        String fourMonthsLater = ReservationPageHelper.get4MonthsLaterDate();
        ReservationPageHelper.fillCheckInDate(fourMonthsLater);
        ReservationPageHelper.fillNights("1");
        ReservationPageHelper.fillHeadCount("1");
        ReservationPageHelper.fillGuestName("テスト 太郎");
        ReservationPageHelper.selectContact("希望しない");
        ReservationPageHelper.submit();

        assertTrue(ReservationPageHelper.hasDateError(),
                "宿泊日エラーが表示されていません");
    }

    @Test
    @DisplayName("UT-018: 人数変更で金額がリアルタイム更新")
    public void testHeadCountPriceUpdate() {
        String tomorrow = ReservationPageHelper.getTomorrowDate();
        ReservationPageHelper.fillCheckInDate(tomorrow);
        ReservationPageHelper.fillNights("1");
        ReservationPageHelper.fillHeadCount("1");

        int amount1 = ReservationPageHelper.getTotalAmount();

        ReservationPageHelper.fillHeadCount("5");

        int amount5 = ReservationPageHelper.getTotalAmount();

        assertTrue(amount5 > amount1,
                "人数変更後の金額が高くなっていません");
    }

    @Test
    @DisplayName("UT-019: 宿泊数変更で金額が連動")
    public void testNightsPriceUpdate() {
        String tomorrow = ReservationPageHelper.getTomorrowDate();
        ReservationPageHelper.fillCheckInDate(tomorrow);
        ReservationPageHelper.fillNights("1");
        ReservationPageHelper.fillHeadCount("2");

        int amount1 = ReservationPageHelper.getTotalAmount();

        ReservationPageHelper.fillNights("3");

        int amount3 = ReservationPageHelper.getTotalAmount();

        assertTrue(amount3 > amount1,
                "宿泊数変更後の金額が高くなっていません");
    }

    @Test
    @DisplayName("DT-006: オプションなしの基本料金確認")
    public void testBasePrice() {
        String tomorrow = ReservationPageHelper.getTomorrowDate();
        ReservationPageHelper.fillCheckInDate(tomorrow);
        ReservationPageHelper.fillNights("1");
        ReservationPageHelper.fillHeadCount("2");

        int baseAmount = ReservationPageHelper.getTotalAmount();
        assertTrue(baseAmount > 0,
                "基本料金が表示されていません");
    }

    @Test
    @DisplayName("DT-007: 朝食オプション追加で金額増加")
    public void testBreakfastOptionPrice() {
        String tomorrow = ReservationPageHelper.getTomorrowDate();
        ReservationPageHelper.fillCheckInDate(tomorrow);
        ReservationPageHelper.fillNights("1");
        ReservationPageHelper.fillHeadCount("2");

        int baseAmount = ReservationPageHelper.getTotalAmount();

        ReservationPageHelper.checkBreakfast();
        page.waitForTimeout(500); // UI更新待ち

        int amountWithBreakfast = ReservationPageHelper.getTotalAmount();

        assertTrue(amountWithBreakfast > baseAmount,
                "朝食オプション追加後の金額が高くなっていません");
    }

    @Test
    @DisplayName("DT-008: チェックインオプション追加で金額増加")
    public void testCheckInOptionPrice() {
        String tomorrow = ReservationPageHelper.getTomorrowDate();
        ReservationPageHelper.fillCheckInDate(tomorrow);
        ReservationPageHelper.fillNights("1");
        ReservationPageHelper.fillHeadCount("2");

        int baseAmount = ReservationPageHelper.getTotalAmount();

        ReservationPageHelper.checkEarlyCheckIn();
        page.waitForTimeout(500);

        int amountWithCheckIn = ReservationPageHelper.getTotalAmount();

        assertTrue(amountWithCheckIn > baseAmount,
                "チェックインオプション追加後の金額が高くなっていません");
    }

    @Test
    @DisplayName("DT-010: 全オプション選択で合算")
    public void testAllOptionsPrice() {
        String tomorrow = ReservationPageHelper.getTomorrowDate();
        ReservationPageHelper.fillCheckInDate(tomorrow);
        ReservationPageHelper.fillNights("1");
        ReservationPageHelper.fillHeadCount("2");

        ReservationPageHelper.checkBreakfast();
        ReservationPageHelper.checkEarlyCheckIn();
        ReservationPageHelper.checkSightseeing();
        page.waitForTimeout(500);

        int totalAmount = ReservationPageHelper.getTotalAmount();
        assertTrue(totalAmount > 0,
                "全オプション選択後の合計金額が表示されていません");
    }

    @Test
    @DisplayName("UT-020: 氏名未入力でエラー")
    public void testMissingGuestName() {
        String tomorrow = ReservationPageHelper.getTomorrowDate();
        ReservationPageHelper.fillCheckInDate(tomorrow);
        ReservationPageHelper.fillNights("1");
        ReservationPageHelper.fillHeadCount("1");
        ReservationPageHelper.selectContact("希望しない");
        ReservationPageHelper.submit();

        assertTrue(ReservationPageHelper.hasNameError(),
                "氏名エラーが表示されていません");
    }

    @Test
    @DisplayName("UT-021: 全必須項目入力で確認画面表示")
    public void testConfirmDialogDisplay() {
        String tomorrow = ReservationPageHelper.getTomorrowDate();
        ReservationPageHelper.fillCheckInDate(tomorrow);
        ReservationPageHelper.fillNights("2");
        ReservationPageHelper.fillHeadCount("5");
        ReservationPageHelper.fillGuestName("テスト 太郎");
        ReservationPageHelper.selectContact("希望しない");
        ReservationPageHelper.submit();

        assertTrue(ReservationPageHelper.isConfirmDialogVisible(),
                "予約確認ダイアログが表示されていません");
    }
}
