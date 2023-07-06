package ru.netology.page.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.page.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement heading = $("[data-test-id='dashboard']");
    private SelenideElement amountNew = $("[data-test-id='amount'] input");
    private SelenideElement from = $("[data-test-id='from'] input");
    private SelenideElement actionButton = $("[data-test-id='action-transfer']");
    private SelenideElement errorMessage = $("[data-test-id='error-notification']");

    public TransferPage() {
        heading.shouldBe(Condition.visible);
    }

    public void makeTransfer(String amountTransfer, DataHelper.CardInfo cardInfo) {
        amountNew.setValue(amountTransfer);
        from.setValue(cardInfo.getCardNumber());
        actionButton.click();
    }

    public DashboardPage makeValidTransfer(String amountTransfer, DataHelper.CardInfo cardInfo) {
        makeTransfer(amountTransfer, cardInfo);
        return new DashboardPage();
    }

    public void errorMessage(String expectedText) {
        errorMessage.shouldHave(exactText(expectedText), Duration.ofSeconds(15)).shouldBe(visible);
    }
}


