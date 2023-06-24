package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class ApplicationForCard {

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {

        open("http://localhost:9999");
    }

    @Test
    void CorrectValueOnlyNameTest() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Мария");
        form.$("[data-test-id=phone] input").setValue("+79315684565");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $("[data-test-id=order-success]").shouldHave(Condition.text("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void CorrectValueNameSurnameTest() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Мария Петрова");
        form.$("[data-test-id=phone] input").setValue("+79315684565");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $("[data-test-id=order-success]").shouldHave(Condition.text("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void CorrectValueWithDashTest() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Анна-Мария Петрова-Водкина");
        form.$("[data-test-id=phone] input").setValue("+79315684565");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $("[data-test-id=order-success]").shouldHave(Condition.text("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void CorrectValueNameLargeLettersTest() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("АННА-МАРИЯ Петрова-Водкина");
        form.$("[data-test-id=phone] input").setValue("+79315684565");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $("[data-test-id=order-success]").shouldHave(Condition.text("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }


    @Test
    void IncorrectValuePhoneWithLetterTest() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Мария Петрова-Водкина");
        form.$("[data-test-id=phone] input").setValue("+7931568456О");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void IncorrectValueNameLatTest() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Irina");
        form.$("[data-test-id=phone] input").setValue("+79315684565");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void IncorrectValueFirstLetterOfNameLatTest() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Mария Петрова");
        form.$("[data-test-id=phone] input").setValue("+7931568456О");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void IncorrectValuePhone12DigitsTest() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Мария Петрова");
        form.$("[data-test-id=phone] input").setValue("+793156845685");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void CheckboxNotClickedTest() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Мария Петрова");
        form.$("[data-test-id=phone] input").setValue("+79315684561");
        form.$("[data-test-id=agreement]");
        form.$(".button").click();
        $("[data-test-id=agreement].input_invalid").shouldHave(Condition.text("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй"));
    }

    @Test
    void IncorrectValueFieldNameEmptyTest() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("");
        form.$("[data-test-id=phone] input").setValue("+79315684561");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void IncorrectValueFieldPhoneEmptyTest() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Мария Петрова");
        form.$("[data-test-id=phone] input").setValue("");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

}
