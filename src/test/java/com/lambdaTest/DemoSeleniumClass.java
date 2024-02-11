package com.lambdaTest;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Set;

/*
 * Selnium 4 class w/o Grid
 *
 */
public class DemoSeleniumClass {

    WebDriver driver;

    @BeforeClass
    public void init(){
        driver=new ChromeDriver();
    }

    private static void waitForPageLoad(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));
    }

    private static void scrollToSelector(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    @Test
    public void testLambda1() throws InterruptedException {
        driver.get("https://www.lambdatest.com");
        driver.manage().window().maximize();
        waitForPageLoad(driver);

        scrollToSelector(driver, By.xpath("//img[@title='Jenkins']"));
        //Thread.sleep(5000);

        System.out.println("Base Url: "+driver.getCurrentUrl());
        String href = driver.findElement(By.xpath("//a[text()='See All Integrations']")).getAttribute("href");
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(href);
        System.out.println("New Tab Url: "+driver.getCurrentUrl());

        //Assertion on New Tab Url
        Assert.assertEquals(driver.getCurrentUrl(), href);

        //Switch to Base Url
        String baseWindowHandle = driver.getWindowHandle();
        Set<String> windowHandles = driver.getWindowHandles();
        for (String handle : windowHandles) {
            if (!handle.equals(baseWindowHandle)) {
                driver.switchTo().window(handle);
            }
        }

        //Close the Base Url
        driver.close();
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }


}
