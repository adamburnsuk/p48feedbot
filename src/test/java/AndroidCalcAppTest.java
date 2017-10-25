
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.ClientProtocolException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;

import com.walmart.ste.stelabapi.LabManager;
import com.walmart.ste.stelabapi.NoAvailableDeviceException;
import com.walmart.ste.stelabapi.ReservationResponse;
import com.walmart.ste.stelabapi.SessionInfo;


public class AndroidCalcAppTest {
	WebDriver driver;
	DesiredCapabilities capabilities = new DesiredCapabilities();
	LabManager labManager = new LabManager();
	SessionInfo myInfo = new SessionInfo();
	ReservationResponse resResponse = new ReservationResponse();
	
	
	@BeforeTest
	public void setUp() throws Exception {
		
//		try {
//			try {
//				myInfo = labManager.Login("admin", "admin");
//			} catch (LoginException e) {
//
//				e.printStackTrace();
//			}
//		} catch (IOException e1) {
//
//			e1.printStackTrace();
//		}
//		System.out.println(myInfo.getUserId());
//		
//		try {
//			resResponse = labManager.createReservationByDeviceType("NOTE5", 30);	
//			
//			System.out.println(labManager);
//			
//		} catch (IOException | NoAvailableDeviceException e) {
//
//			e.printStackTrace();
//		}
//
//		try {
//			labManager.startAppiumForDevice(resResponse.getActiveReservation());
//		} catch (IOException | NoAvailableDeviceException e) {
//
//			e.printStackTrace();
//		}

		
		//The kind of mobile device or emulator to use - iPad Simulator, iPhone Retina 4-inch, Android Emulator, Galaxy S4 etc
		//Find your device name by running command 'adb devices' from command prompt
	
		//Which mobile OS platform to use - iOS, Android, or FirefoxOS
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("deviceName", "SamsungNote5");
		
		//Java package of the Android app you want to run- Ex: com.example.android.myApp
		//For Android calculator app, package name is 'com.android.calculator2'
		capabilities.setCapability("appPackage", "com.google.android.calculator");

		//Activity name for the Android activity you want to launch from your package
		//For Android calculator app, Activity name is 'com.android.calculator2.Calculator'
		capabilities.setCapability("appActivity", "com.android.calculator2.CalculatorGoogle");
		
		//Mac Mini Appium Connection with Egg Cloud
		//driver = new RemoteWebDriver(new URL("http://" + resResponse.getActiveReservation().getIp() + ":" + resResponse.getActiveReservation().getPort() + "/wd/hub"), capabilities);
		driver = new RemoteWebDriver(new URL("http://172.21.17.142:4723/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
	}

	//@Test
	public void Sum() {

		System.out.println("Calculate sum of two numbers");
		//Locate elements using By.name() to enter data and click +/= buttons
		driver.findElement(By.xpath("//android.widget.Button[@text='1']")).click();
		driver.findElement(By.xpath("//android.widget.Button[@text='+']")).click();
		driver.findElement(By.xpath("//android.widget.Button[@text='2']")).click();
		driver.findElement(By.xpath("//android.widget.Button[@text='=']")).click();
		String result = driver.findElement(By.id("com.google.android.calculator:id/result")).getText();
		
		//verify if result is 3
		Assert.assertTrue(result.endsWith("3"));
	}
	

	//@AfterTest
	public void End() throws ClientProtocolException, IOException, NoAvailableDeviceException {
		driver.quit();
		labManager.stopAppiumForDevice(resResponse.getActiveReservation());
		labManager.cancelReservation(resResponse.getActiveReservation());
	}
}
