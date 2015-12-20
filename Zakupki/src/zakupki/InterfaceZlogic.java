/**
 * 
 */
package zakupki;

import java.util.ArrayList;

/**
 * @author igor
 *
 */
public interface InterfaceZlogic {
	void start(String q);
	void parseAnswer(ArrayList<String> al, String url);
	public int getCountPage(ArrayList<String> al);
	public void parseItem(int pos, ArrayList<String> al);
}
