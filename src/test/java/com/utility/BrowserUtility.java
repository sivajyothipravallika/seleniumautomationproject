package com.utility;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.constants.Browser;

public abstract class BrowserUtility {

    // instance variable
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    Logger logger = LoggerUtility.getLogger(this.getClass());

    static {
        java.util.logging.Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder").setLevel(Level.OFF);
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    // constructor
    public BrowserUtility(WebDriver driver) {
        BrowserUtility.driver.set(driver); // initialize the instance variable 'driver'
    }

    public BrowserUtility(String browserName){
        if(browserName.equalsIgnoreCase("chrome")){
            driver.set(new ChromeDriver());
        }else if (browserName.equalsIgnoreCase("edge")){
            driver.set(new EdgeDriver());
        }else if (browserName.equalsIgnoreCase("firefox")){
            driver.set(new FirefoxDriver());
        }else {
            logger.error("Invalid Browser Name... Please select chrome, edge or firefox");
            System.err.println("Invalid Browser Name... Please select chrome, edge or firefox");
        }
    }

    public BrowserUtility(Browser browserName){
        logger.info("Launching Browser for "+browserName);
        if(browserName == Browser.CHROME){
            driver.set(new ChromeDriver());
        }else if (browserName == Browser.EDGE){
            driver.set(new EdgeDriver());
        }else if (browserName == Browser.FIREFOX){
            driver.set(new FirefoxDriver());
        }
    }

    public BrowserUtility(Browser browserName, boolean isHeadless){
        logger.info("Launching Browser for "+browserName);
        if(browserName == Browser.CHROME){
            if(isHeadless){
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless=new"); // driver going to launch the browser in headless mode
                options.addArguments("--window-size=1920,1080");
                driver.set(new ChromeDriver(options));
            }else{
                driver.set(new ChromeDriver());
            }
        }else if (browserName == Browser.EDGE){
            if(isHeadless){
                EdgeOptions options = new EdgeOptions();
                options.addArguments("--headless=old"); // driver going to launch the browser in headless mode
                options.addArguments("disable-gpu");
                driver.set(new EdgeDriver(options));
            }else{
                driver.set(new EdgeDriver());
            }
        }else if (browserName == Browser.FIREFOX){
            if(isHeadless){
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments("--headless=old"); // driver going to launch the browser in headless mode
                options.addArguments("--window-size=1920,1080");
                driver.set(new FirefoxDriver(options));
            }else{
                driver.set(new FirefoxDriver());
            }

        }
    }

    // navigate to website
    @SuppressWarnings("null")
    public void goToWebsite(String url){
        logger.info("Visiting website "+url);
        driver.get().get(url); // navigate to the specified URL
    }

    // maximise the browser window
    public void maximiseBrowserWindow(){
        logger.info("Maximizing the browser window ");
        driver.get().manage().window().maximize(); // maximise the browser window
    }

    public void clickOn(By locator){
        logger.info("Finding element with the locator "+locator);
        @SuppressWarnings("null")
        WebElement element = driver.get().findElement(locator);
        logger.info("Element found and now performing click "+locator);
        element.click();
    }

    public void enterText(By locator, String text){
        logger.info("Finding element with the locator "+locator);
        @SuppressWarnings("null")
        WebElement element = driver.get().findElement(locator);
        logger.info("Element found and now enter text "+text);
        element.sendKeys(text);
    }

    @SuppressWarnings("null")
    public String getVisibleText(By locator){
        logger.info("Finding element with the locator "+locator);
        WebElement element = driver.get().findElement(locator);
        logger.info("Element found and now returning the visible text "+element.getText());
        return element.getText();
    }

    public String takeScreenShot(String name) {
        TakesScreenshot screenshot = (TakesScreenshot) driver.get();
        File screenshotData = screenshot.getScreenshotAs(OutputType.FILE);
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH-mm-ss");
        String timeStamp = format.format(date);
        String path = "./screenshots/" +name+" - "+timeStamp+".png";
        File screenshotFile = new File(path);
        try {
            FileUtils.copyFile(screenshotData, screenshotFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;

    }

    public void quit(){
        logger.info("Closing the browser");
        WebDriver currentDriver = driver.get();
        if(currentDriver != null) {
            currentDriver.quit(); // close the browser
            driver.remove();
        }
    }
}
