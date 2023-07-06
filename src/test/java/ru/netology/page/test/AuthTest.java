package ru.netology.page.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.page.data.DataHelper;
import ru.netology.page.page.DashboardPage;
import ru.netology.page.page.LoginPage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthTest {
    DashboardPage dashboardPage;

    @BeforeEach
        //@DisplayName("Should successfully login with active registered user")
    void setup() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        var firstCardInfo = DataHelper.getFirstCardInfo();
        var secondCardInfo = DataHelper.getSecondCardInfo();
        var firstCardBalance = dashboardPage.getCardBalance(0);
        var secondCardBalance = dashboardPage.getCardBalance(1);
        var amount = DataHelper.generaleValidAmount(firstCardBalance);
        var expectedBalanceFirstCard = firstCardBalance - amount;
        var expectedBalanceSecondCard = secondCardBalance + amount;
        var transferPage = dashboardPage.selectCardForTransfer(secondCardInfo);
        dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), firstCardInfo);
        var actualBalanceFirstCard = dashboardPage.getCardBalance(0);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(1);
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }

//    @Test
//    void shouldTransferError() {
//        var firstCardInfo = DataHelper.getFirstCardInfo();
//        var secondCardInfo = DataHelper.getSecondCardInfo();
//        var firstCardBalance = dashboardPage.getCardBalance(0);
//        var secondCardBalance = dashboardPage.getCardBalance(1);
//        var amount = DataHelper.generaleInvalidAmount(firstCardBalance);
//        var expectedBalanceFirstCard = firstCardBalance - amount;
//        var expectedBalanceSecondCard = secondCardBalance + amount;
//        var transferPage = dashboardPage.selectCardForTransfer(secondCardInfo);
//        dashboardPage = transferPage.errorMessage(String.valueOf(amount), firstCardInfo);
//
//    }
}

