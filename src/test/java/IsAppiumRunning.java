import java.net.MalformedURLException;

import com.walmart.qe.mobilebot.util.AppiumServerJava;

public class IsAppiumRunning {

	public static void main(String[] args) throws MalformedURLException {
		
		AppiumServerJava appServer = new AppiumServerJava();
		
		appServer.startServer("172.21.64.226", "4728", "15063522501227", "9728", "2111");
		
//		if(appServer.checkAppiumRunning("172.21.64.226", "4728", 2000)){
//			System.out.println("Appium is Running");
//		}else{
//			System.out.println("Appium is NOT Running");
//		};
	
		
	}
}
