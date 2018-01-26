import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
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

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class TestClass {

    private AppiumDriver<?> driver;

    @Before
    public final void nativeSetup() throws IOException {
        URL url;

        InputStream input = null;
        Properties prop = new Properties();
        input = new FileInputStream("src/resources/native_config.properties");
        prop.load(input);

        DesiredCapabilities capabilities = new DesiredCapabilities();

        File appDir = new File(System.getProperty("user.dir")); //think about it
        File app = new File(prop.getProperty("app"));

        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, prop.getProperty("deviceName"));

        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, prop.getProperty("platformName"));
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, prop.getProperty("platformVersion"));

        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());

        //capabilities.setCapability("appPackage", prop.getProperty("appPackage"));

        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, prop.getProperty("newCommandTimeout"));

        capabilities.setCapability(MobileCapabilityType.FULL_RESET, "true");

        if (prop.getProperty("useAvd").equals("true")) {
            capabilities.setCapability("avd", prop.getProperty("avd"));
        }
        try {
            url = new URL(prop.getProperty("driverURL"));
            driver = new AndroidDriver<MobileElement>(url, capabilities);
        } catch (MalformedURLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public static final String WELCOME_TEXT = "Hello";
    public static final String USER_NAME = "User";
    public static final String ALERT_TEXT = "Please enter a name!";

    @Test
    public final void nativeTest() {

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.epam.hello:id/editText")));

        WebElement editText = driver.findElement(By.id("com.epam.hello:id/editText"));
        WebElement okButton = driver.findElement(By.id("com.epam.hello:id/okButton"));
        WebElement helloText = driver.findElement(By.id("com.epam.hello:id/helloText"));

        editText.click();
        editText.sendKeys(USER_NAME);
        okButton.click();

        Assert.assertEquals((WELCOME_TEXT + " " + USER_NAME + "!").toLowerCase(), helloText.getText().toLowerCase());

        editText.clear();
        okButton.click();

        WebElement alertText = driver.findElement(By.id("android:id/message"));

        Assert.assertEquals(alertText.getText().toLowerCase(), ALERT_TEXT.toLowerCase());
    }


    @After
    public final void tearDown() {
        driver.quit();
    }
}


