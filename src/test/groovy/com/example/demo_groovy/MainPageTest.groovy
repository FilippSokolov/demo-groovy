package com.example.demo_groovy

import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.logevents.SelenideLogger
import io.qameta.allure.Feature
import io.qameta.allure.Issue
import io.qameta.allure.Owner
import io.qameta.allure.Story
import io.qameta.allure.selenide.AllureSelenide
import org.junit.jupiter.api.*
import org.openqa.selenium.support.FindBy

import static org.junit.jupiter.api.Assertions.*

import static com.codeborne.selenide.Condition.attribute
import static com.codeborne.selenide.Condition.visible
import static com.codeborne.selenide.Selectors.*
import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.Selenide.open

@Story("users")
@Issue("IDEA-12")
@Owner('big-duck')
class MainPageTest {
    @FindBy(className = "demo")
    private final MainPage mainPage = new MainPage()

    @Owner('big-duck')
    @BeforeAll
    static void setUpAllure() {
        SelenideLogger.addListener("allure", new AllureSelenide())
    }

    @BeforeEach
    void setUp() {
        Configuration.startMaximized = true
        open("https://www.jetbrains.com/")
    }

    @Owner("big-duck")
    @Test
    void search() {
        mainPage.searchButton.click()

        $(byId("header-search")).sendKeys("Selenium")
        $(byXpath("//button[@type='submit' and text()='Search']")).click()

        $(byClassName("js-search-input")).shouldHave(attribute("value", "Selenium"))
    }

    @Feature(value = "backend") // no autocompletion
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
