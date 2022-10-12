package tw.com.cybersoft.fsd.logic;

import org.joda.time.LocalDateTime;

public class Utils {
	
	public static String nowStr() {
		return LocalDateTime.now().toString();
	}
	
}
