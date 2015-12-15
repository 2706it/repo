/**
 * 
 */
package zakupki;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author igor
 *
 */
public class ZLogic implements InterfaceZlogic {

	/* (non-Javadoc)
	 * @see zakupki.InterfaceZlogic#start()
	 */
	public static void start() {
		// TODO Auto-generated method stub
		String line;
		String url = "http://zakupki.gov.ru/epz/main/public/home.html";
		String params = "";
		System.out.println("start!");
		line = httpGet(url);
	}

	/* (non-Javadoc)
	 * @see zakupki.InterfaceZlogic#httpGet()
	 */
	@Override
	public String httpGet(String url) {
		// TODO Auto-generated method stub
		String line = "";
		try {
			URL url2 = new URL(url);
			BufferedReader reader = new BufferedReader(new InputStreamReader(url2.openStream(),"UTF-8"));
			line = reader.readLine();
			reader.close();
		} catch (MalformedURLException e) {
					        // ...
		} catch (IOException e) {
					        // ...
		}
		return line;
	}

	/* (non-Javadoc)
	 * @see zakupki.InterfaceZlogic#httpPost()
	 */
	@Override
	public void httpPost() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see zakupki.InterfaceZlogic#parseAnswer()
	 */
	@Override
	public void parseAnswer() {
		// TODO Auto-generated method stub

	}

}
