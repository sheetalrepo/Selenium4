package com.lambdaTest;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Set;

/*
 * Selenium 4 class with LambdaTest Grid
 *
 */
public class DemoSeleniumClassGrid {

    public String username = "sheetal.singh8";
    public String accesskey = "ySKYxu5M3WUWgP6aSGQ6i5wdCSe8VN8YuxODvS5SZxYTAeY5Va";
    public RemoteWebDriver driver = null;
    public String gridURL = "@hub.lambdatest.com/wd/hub";
    boolean status = false;

    @BeforeTest
    @Parameters(value={"browser","version","platform"})
    public void setUp(String browser, String version, String platform){

        ChromiumOptions browserOptions;

        if(browser.equalsIgnoreCase("Chrome"))
             browserOptions = new ChromeOptions();
        else if (browser.equalsIgnoreCase("MicrosoftEdge"))
             browserOptions = new EdgeOptions();
        else
             browserOptions = new ChromeOptions();


        browserOptions.setPlatformName(platform);
        browserOptions.setBrowserVersion(version);
        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("username", "sheetal.singh8");
        ltOptions.put("accessKey", "ySKYxu5M3WUWgP6aSGQ6i5wdCSe8VN8YuxODvS5SZxYTAeY5Va");
        ltOptions.put("visual", true);
        ltOptions.put("video", true);
        ltOptions.put("network", true);
        ltOptions.put("build", "Final Exam Demo Build");
        ltOptions.put("project", "Final Exam Demo Project");
        ltOptions.put("selenium_version", "4.0.0");
        ltOptions.put("w3c", true);
        browserOptions.setCapability("LT:Options", ltOptions);


        try {
            //driver = new RemoteWebDriver(new URL("https://sheetal.singh8:ySKYxu5M3WUWgP6aSGQ6i5wdCSe8VN8YuxODvS5SZxYTAeY5Va@hub.lambdatest.com/wd/hub"), capabilities);
            //driver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + gridURL), capabilities);
            driver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + gridURL), browserOptions);
        } catch (MalformedURLException e) {
            System.out.println("Invalid grid URL");
        } catch (Exception e) {
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@ EXCEPTION @@@@@@@@@@@@@@@@@@@@@@@@@@2");
            System.out.println(e.getMessage());
        }
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
        if (driver != null) {
            ((JavascriptExecutor) driver).executeScript("lambda-status=" + status);
            driver.quit();
        }
    }


}
