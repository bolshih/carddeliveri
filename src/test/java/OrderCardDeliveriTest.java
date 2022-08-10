import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class OrderCardDeliveriTest {

  @BeforeEach
    public void setup() {
      DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.forLanguageTag("ru"));
      Date currentDate = new Date();
      open("http://localhost:9999");
}

@Test
        void shuldTestWebUI() {
    //Configuration.holdBrowserOpen = true;
    $("[data-test-id=city] input").setValue("Улан-Удэ");
    $("[data-test-id=date] input").setValue("11.11.1394");
    $("[data-test-id=name] input").setValue("Александр Семенов-Баринов");
    $("[name=phone]").setValue("+74785847656");
    $("[data-test-id=agreement]").click();
    $(".button").click();
    $("[data-test-id=notification").shouldHave(Condition.visible, Duration.ofSeconds(15));
    }

}
