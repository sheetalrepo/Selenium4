package com.lambdaTest;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * #How To Run
 * Right click and run "demo_lambada.xml"
 *
 * #To get username/accesskey
 * Create account with Lambada Test and go to Profile > Account Settings
 * https://accounts.lambdatest.com/security
 * Or
 * https://automation.lambdatest.com/build
 * Click Access Key Icon (Top right)
 *
 * #To generate capabilities
 * Click "?" next to "Access Key Icon"
 *
 * #To check Test Run Result:
 * https://automation.lambdatest.com/build
 *
 *
 * #Topics:
 * https://accounts.lambdatest.com/dashboard
 *
 * Manual testing using Real Time testing
 *  - https://app-beta.lambdatest.com/console/realtime/browser/desktop
 * Automation TCs
 *  - Parallel Run
 *  - Geo Location
 *  - Video Recording
 *  - Bug Filing
 *  - Log Analysis
 *
 */
public class Abc {

    public String username = "sheetal.singh8";  //TODO 1
    public String accesskey = "ySKYxu5M3WUWgP6aSGQ6i5wdCSe8VN8YuxODvS5SZxYTAeY5Va";      //TODO 2
    public RemoteWebDriver driver = null;
    public String gridURL = "@hub.lambdatest.com/wd/hub";
    boolean status = false;

    @BeforeTest
    @Parameters(value={"browser","version","platform"})
    public void setUp(String browser, String version, String platform){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", browser);
        capabilities.setCapability("version", version);
        capabilities.setCapability("platform", platform); // If this cap isn't specified, it will just get the any available one
        capabilities.setCapability("build", "Abc");
        capabilities.setCapability("name", "Sample Wiki Test");
        capabilities.setCapability("geoLocation", "JP"); //Geo Location
        capabilities.setCapability("network", true); // To enable network logs
        capabilities.setCapability("visual", true); // To enable step by step screenshot
        capabilities.setCapability("video", true); // To enable video recording
        capabilities.setCapability("console", true); // To capture console logs
        capabilities.setCapability("terminal", true); // To capture console logs
        try {
            driver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + gridURL), capabilities);
        } catch (MalformedURLException e) {
            System.out.println("Invalid grid URL");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void googleTest() throws InterruptedException {
        driver.get("https://www.google.com");
        driver.manage().window().maximize();
        System.out.println("### "+driver.getCurrentUrl());
        System.out.println("### "+driver.getTitle());
        Thread.sleep(5000);
    }

    @Test
    public void testWikiPage(){
        System.out.println("### Browser Details:");

        try{
            driver.get("https://en.wikipedia.org/wiki/Wikipedia");
            driver.manage().window().maximize();

            System.out.println("### "+driver.getCurrentUrl());
            driver.findElement(By.name("search")).sendKeys("Oldest Scriptures");
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[@id=\"searchform\"]/div/button")).click();
            Thread.sleep(2000);
            System.out.println("### "+driver.getCurrentUrl());
            driver.get("https://www.google.com/");
            Thread.sleep(2000);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    @AfterTest
    public void tearDown() throws Exception {
        if (driver != null) {
            ((JavascriptExecutor) driver).executeScript("lambda-status=" + status);
            driver.quit();
        }
    }

}
