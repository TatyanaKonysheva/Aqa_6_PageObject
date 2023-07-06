package ru.netology.page.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.page.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    // к сожалению, разработчики не дали нам удобного селектора, поэтому так
    private ElementsCollection cards = $$(".list__item div");
    private SelenideElement heading = $("[data-test-id='dashboard']");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        heading.shouldBe(Condition.visible);
    }

    public int getCardBalance (int index){
        var text = cards.get(index).getText();
        return extractBalance(text);
    }

    public TransferPage selectCardForTransfer(DataHelper.CardInfo cardInfo){
        cards.findBy(Condition.attribute("data-test-id", cardInfo.getTestIdId()))
                .$("button").click();
        return new TransferPage();
    }

//    public int getFirstCardBalance() {
//        val text = cards.first().text();
//        return extractBalance(text);
//    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}
