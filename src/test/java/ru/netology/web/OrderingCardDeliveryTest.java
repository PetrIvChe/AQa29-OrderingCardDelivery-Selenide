package ru.netology.web;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class OrderingCardDeliveryTest {

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    public void shouldBeHappyPass() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        String planningDate = generateDate(3);
        $("[data-test-id='date'] input").setValue(String.valueOf(planningDate));
        $("[data-test-id='name'] input").setValue("Иванов Леонид");
        $("[data-test-id='phone'] input").setValue("+79123456789");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__title").should(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldBe(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15));
    }

    @Test
    public void shouldSubmitRequestWithDoubleSurName() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Екатеринбург");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        String planningDate = generateDate(4);
        $("[data-test-id='date'] input").setValue(String.valueOf(planningDate));
        $("[data-test-id='name'] input").setValue("Иванов-Петров Леонид");
        $("[data-test-id='phone'] input").setValue("+79123456789");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__title").should(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldBe(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15));
    }

    @Test
    public void shouldReturnAlertWhenCityFieldIsEmpty() {
        open("http://localhost:9999/");
        //$("[data-test-id='city'] input").setValue("Екатеринбург");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        String planningDate = generateDate(5);
        $("[data-test-id='date'] input").setValue(String.valueOf(planningDate));
        $("[data-test-id='name'] input").setValue("Иванов-Петров Леонид");
        $("[data-test-id='phone'] input").setValue("+79123456789");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='city'].input_invalid .input__sub").should(visible, Duration.ofSeconds(15));
        $(".input__sub").shouldBe(Condition.text("Поле обязательно для заполнения"), Duration.ofSeconds(15));
    }

    @Test
    public void shouldReturnAlertWhenCityIsNotFederal() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Тихвин");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        String planningDate = generateDate(6);
        $("[data-test-id='date'] input").setValue(String.valueOf(planningDate));
        $("[data-test-id='name'] input").setValue("Иванов-Петров Леонид");
        $("[data-test-id='phone'] input").setValue("+79123456789");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='city'].input_invalid .input__sub").should(visible, Duration.ofSeconds(15));
        $(".input__sub").shouldBe(Condition.text("Доставка в выбранный город недоступна"), Duration.ofSeconds(15));
    }

    @Test
    public void shouldReturnAlertWhenOnEnglish() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        String planningDate = generateDate(7);
        $("[data-test-id='date'] input").setValue(String.valueOf(planningDate));
        $("[data-test-id='name'] input").setValue("Johan Buden");
        $("[data-test-id='phone'] input").setValue("+79123456789");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").should(visible, Duration.ofSeconds(15));
        $("[data-test-id='name'].input_invalid .input__sub").shouldBe(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."), Duration.ofSeconds(15));
    }

    @Test
    public void shouldReturnAlertWhenPhoneFieldIsEmpty() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        String planningDate = generateDate(8);
        $("[data-test-id='date'] input").setValue(String.valueOf(planningDate));
        $("[data-test-id='name'] input").setValue("Иванов-Петров Леонид");
        // $("[data-test-id='phone'] input").setValue("+79123456789");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='phone'].input_invalid .input__sub").should(visible, Duration.ofSeconds(15));
        $("[data-test-id='phone'].input_invalid .input__sub").shouldBe(Condition.text("Поле обязательно для заполнения"), Duration.ofSeconds(15));
    }

    @Test
    public void shouldReturnAlertWhenPhoneHave10Signs() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        String planningDate = generateDate(9);
        $("[data-test-id='date'] input").setValue(String.valueOf(planningDate));
        $("[data-test-id='name'] input").setValue("Иванов-Петров Леонид");
        $("[data-test-id='phone'] input").setValue("+7912345678");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        //$("[data-test-id='phone'].input_invalid .input__sub").should(visible, Duration.ofSeconds(15));
        $("[data-test-id='phone'].input_invalid .input__sub").shouldBe(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."), Duration.ofSeconds(15));
    }

    @Test
    public void shouldReturnAlertWhenPhoneHave11Signs() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        String planningDate = generateDate(10);
        $("[data-test-id='date'] input").setValue(String.valueOf(planningDate));
        $("[data-test-id='name'] input").setValue("Иванов-Петров Леонид");
        $("[data-test-id='phone'] input").setValue("+791234567890");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        //$("[data-test-id='phone'].input_invalid .input__sub").should(visible, Duration.ofSeconds(15));
        $("[data-test-id='phone'].input_invalid .input__sub").shouldBe(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."), Duration.ofSeconds(15)).should(visible, Duration.ofSeconds(15));
    }

    @Test
    public void shouldReturnAlertWhenPhoneDonNotHavePlus() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        String planningDate = generateDate(11);
        $("[data-test-id='date'] input").setValue(String.valueOf(planningDate));
        $("[data-test-id='name'] input").setValue("Иванов-Петров Леонид");
        $("[data-test-id='phone'] input").setValue("791234567890");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='phone'].input_invalid .input__sub").should(visible, Duration.ofSeconds(15));
        $("[data-test-id='phone'].input_invalid .input__sub").shouldBe(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."), Duration.ofSeconds(15));
    }

    @Test
    public void shouldReturnAlertWhenPhoneHavePlusInTheMiddle() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        String planningDate = generateDate(12);
        $("[data-test-id='date'] input").setValue(String.valueOf(planningDate));
        $("[data-test-id='name'] input").setValue("Иванов-Петров Леонид");
        $("[data-test-id='phone'] input").setValue("791234+567890");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='phone'].input_invalid .input__sub").should(visible, Duration.ofSeconds(15));
        $("[data-test-id='phone'].input_invalid .input__sub").shouldBe(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."), Duration.ofSeconds(15));
    }

    @Test
    public void shouldReturnAlertWenCheckboxIsEmpty() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Екатеринбург");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        String planningDate = generateDate(13);
        $("[data-test-id='date'] input").setValue(String.valueOf(planningDate));
        $("[data-test-id='name'] input").setValue("Иванов-Петров Леонид");
        $("[data-test-id='phone'] input").setValue("+79123456789");
        // $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='agreement'].input_invalid .checkbox__text").should(visible, Duration.ofSeconds(15));

    }

    @Test
    public void shouldReturnAlertWhenWrongDate() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        String planningDate = generateDate(2);
        $("[data-test-id='date'] input").setValue(String.valueOf(planningDate));
        $("[data-test-id='name'] input").setValue("Иванов Леонид");
        $("[data-test-id='phone'] input").setValue("+79123456789");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='date'] .input_invalid").shouldBe(Condition.text("Заказ на выбранную дату невозможен"), Duration.ofSeconds(15));
    }
}
