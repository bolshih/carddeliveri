import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.conditions.Enabled;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;

public class OrderCardDeliveriTest {

    @BeforeEach
    public void setup() {
        open("http://localhost:9999");
    }
    public String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shuldTestWebUI() {
        String meetingData = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='city'] input").setValue("Улан-Удэ");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(meetingData);
        $("[data-test-id='name'] input").setValue("Александр Семенов-Баринов");
        $("[name=phone]").setValue("+74785847656");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + meetingData), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }


    @Test
    void shuldTestWebUIComlexElements() {
        String meetingDay = generateDate(4, "dd");
        String meetingMonth = generateDate(4, "MM");
        String meetingData = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=city] input").setValue("Ул");
        $(".menu-item").shouldBe(Condition.visible);
        $x("//*[contains(text(), 'Улан-Удэ')]").click();
        $("[data-test-id=date] input").click();
        $(".calendar").shouldBe(Condition.visible);
        SelenideElement calendar = $(".calendar");
        //проверка что дата записи не выпадает на следующем месяц
        if (LocalDate.now().format(DateTimeFormatter.ofPattern("MM")).equals(meetingMonth)) {
            calendar.$$(".calendar__day").find(exactText(meetingDay)).click();
        } else {
            calendar.$("[data-step='1']").click();
            calendar.$$(".calendar__day").find(exactText(meetingDay)).click();
        }
        $("[data-test-id=name] input").setValue("Александр Семенов-Баринов");
        $("[name=phone]").setValue("+74785847656");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + meetingData), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
}
