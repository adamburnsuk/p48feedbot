

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.walmart.qe.mobilebot.stelabapi.LabManager;
import com.walmart.qe.mobilebot.stelabapi.LoginException;
import com.walmart.qe.mobilebot.stelabapi.NoAvailableDeviceException;
import com.walmart.qe.mobilebot.stelabapi.ReservationResponse;
import com.walmart.qe.mobilebot.stelabapi.SessionInfo;

public class Test {

	public static void main(String[] args) throws ClientProtocolException, IOException, NoAvailableDeviceException {

		LabManager eggLogin = new LabManager();
		SessionInfo myInfo = new SessionInfo();
		ReservationResponse resResponse = new ReservationResponse();
		
		try {
			try {
				myInfo = eggLogin.Login("admin", "admin");
			} catch (LoginException e) {

				e.printStackTrace();
			}
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		System.out.println(myInfo.getUserId());
		
		try {
			resResponse = eggLogin.createReservationByDeviceType("MC40", 30);
			
			System.out.println(eggLogin);
			
		} catch (IOException | NoAvailableDeviceException e) {

			e.printStackTrace();
		}

		try {
			eggLogin.startAppiumForDevice(resResponse.getActiveReservation());
		} catch (IOException | NoAvailableDeviceException e) {

			e.printStackTrace();
		}
		
		try {
			eggLogin.stopAppiumForDevice(resResponse.getActiveReservation());
		} catch (IOException | NoAvailableDeviceException e) {

			e.printStackTrace();
		}

		
		eggLogin.cancelReservation(resResponse.getActiveReservation());
		
		System.out.println(resResponse.toString());
		System.out.println("Done");
	}

}
