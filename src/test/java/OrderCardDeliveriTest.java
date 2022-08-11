import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.conditions.Enabled;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Locale;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;

public class OrderCardDeliveriTest {
    Date date = new Date();
    //Date date2 = new Date();
    Date currentDate = new Date();
    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.forLanguageTag("ru"));

    @BeforeEach
    public void setup() {
        int day = currentDate.getDate();
        day = day + 5;
        date.setDate(day);
        System.out.println(df.format(date));
        open("http://localhost:9999");
        //int day2 = 28;
        //date2.setDate(day2);
    }


    @Test
    void shuldTestWebUI() {
        $("[data-test-id=city] input").setValue("Улан-Удэ");
        $("[data-test-id=date] input").setValue(df.format(currentDate));
        $("[data-test-id=name] input").setValue("Александр Семенов-Баринов");
        $("[name=phone]").setValue("+74785847656");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=notification").shouldHave(Condition.visible, Duration.ofSeconds(15));
    }


    @Test
    void shuldTestWebUIComlexElements() {
        $("[data-test-id=city] input").setValue("Ул");
        $(".menu-item").shouldBe(Condition.visible);
        $x("//*[contains(text(), 'Улан-Удэ')]").click();
        $("[data-test-id=date] input").click();
        SelenideElement calendar = $(".calendar");
        if (currentDate.getDate() < 28) { //проверка что дата записи не выпадает на следующем месяц
            calendar.$$(".calendar__day").find(exactText(String.valueOf(date.getDate()))).click();
        } else {
            calendar.$("[data-step='1']").click();
            calendar.$$(".calendar__day").find(exactText(String.valueOf(date.getDate()))).click();
        }
        $("[data-test-id=name] input").setValue("Александр Семенов-Баринов");
        $("[name=phone]").setValue("+74785847656");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=notification").shouldHave(Condition.visible, Duration.ofSeconds(15));
    }
}
