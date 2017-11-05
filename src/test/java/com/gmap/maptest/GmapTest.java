package com.gmap.maptest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;


public class GmapTest {
    private AppiumDriver driver;
    private WebDriverWait webDriverWait;
    List<MobileElement> listOfResults;
    String resName;
    MobileElement element;

    @Before
    public void setup() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.0");
        desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Moto");
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.google.android.apps.maps");
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.google.android.maps.MapsActivity");
        driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), desiredCapabilities);
        webDriverWait = new WebDriverWait(driver, 30);
    }

    @Test
    public void testGmap() {

        // How to click the first element in the search result
        driver.findElementById("com.google.android.apps.maps:id/search_omnibox_text_box").click();
        driver.findElementById("com.google.android.apps.maps:id/search_omnibox_edit_text").sendKeys("restaurants near me \n");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.google.android.apps.maps:id/recycler_view")));
        listOfResults = driver.findElementsById("com.google.android.apps.maps:id/title");
        for (MobileElement e : listOfResults) {
            System.out.println("Restaurant Name : " + e.getText().toString() + "\n");
        }
        resName = listOfResults.get(0).getText();
        listOfResults.get(0).click();
        element = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + resName + "\");"));
        Assert.assertTrue(element.isDisplayed());

        //How to pick the search result randomly

        driver.navigate().back();

        driver.navigate().back();

        driver.findElementById("com.google.android.apps.maps:id/search_omnibox_text_box").click();
        driver.findElementById("com.google.android.apps.maps:id/search_omnibox_edit_text").sendKeys("restaurants near me \n");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.google.android.apps.maps:id/recycler_view")));
        listOfResults = driver.findElementsById("com.google.android.apps.maps:id/title");

        Random random = new Random();
        MobileElement element1 = listOfResults.get(random.nextInt(listOfResults.size()));

        resName = element1.getText();
        System.out.println("Random Restaurant Name : " + resName);
        element1.click();

        element = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + resName + "\");"));
        Assert.assertTrue(element.isDisplayed());

    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
