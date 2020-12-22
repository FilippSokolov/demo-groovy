package com.example.demo_groovy

import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.logevents.SelenideLogger
import io.qameta.allure.Feature
import io.qameta.allure.Owner
import io.qameta.allure.Story
import io.qameta.allure.selenide.AllureSelenide
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.support.FindBy

import static com.codeborne.selenide.Condition.attribute
import static com.codeborne.selenide.Condition.visible
import static com.codeborne.selenide.Selectors.byClassName
import static com.codeborne.selenide.Selectors.byId
import static com.codeborne.selenide.Selectors.byXpath
import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.Selenide.open
import static org.junit.jupiter.api.Assertions.assertEquals

@Story("users")
@Owner("big-duck")
class DemoPageTest {
    @FindBy(className = "demo")
    private final MainPage mainPage = new MainPage()

    @Feature(value = "frontend")
    @BeforeAll
    static void setUpAllure() {
        SelenideLogger.addListener("allure", new AllureSelenide())

        String xa = "demo"
    }

    @BeforeEach
    void setUp() {
        Configuration.startMaximized = true
        open("https://www.jetbrains.com/")
    }

    @Feature(value = "frontend")
    @Test
    void search() {
        mainPage.searchButton.click()

        $(byId("header-search")).sendKeys("Selenium")
        $(byXpath("//button[@type='submit' and text()='Search']")).click()

        $(byClassName("js-search-input")).shouldHave(attribute("value", "Selenium"))
    }

    @Feature(value = "frontend") // no autocompletion
    @Test
    void toolsMenu() {
        mainPage.toolsMenu.hover()

        $(byClassName("menu-main__popup-wrapper")).shouldBe(visible)
    }

    @Test
    void navigationToAllTools() {
        mainPage.seeAllToolsButton.click()

        $(byClassName("products-list")).shouldBe(visible)
        assertEquals("All Developer Tools and Products by JetBrains", Selenide.title())
    }
}
