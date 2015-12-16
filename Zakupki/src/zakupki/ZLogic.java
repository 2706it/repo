/**
 * 
 */
package zakupki;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @author igor
 *
 */
public class ZLogic implements InterfaceZlogic {

	/* (non-Javadoc)
	 * @see zakupki.InterfaceZlogic#start()
	 */
	final String DIV_CLASS_REGISTERBOX = "<div class=\"registerBox\">";
	final String TD_CLASS_DESCRIPT = "<td class=\"descriptTenderTd\">";
	final String TD_CLASS_AMOUNT = "<td class=\"amountTenderTd \">";
	final String TD_CLASS_TENDER = "<td class=\"tenderTd\">";
	final String TD_CLASS_PUBLISH = "<td colspan=\"2\" class=\"publishingTd\">";
	public void start() {
		// TODO Auto-generated method stub
		String url = "http://zakupki.gov.ru/epz/order/quicksearch/search.html?searchString=";
		//http://zakupki.gov.ru/epz/order/quicksearch/search.html?searchString=
		String query = "";
		try {
			query = URLEncoder.encode("бумага", "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		System.out.println("start!");
		StringBuffer line = ZCommon.UrlHttpGetNew(url+query);
		System.out.println(line);
		parseAnswer(line);
	}
	/* (non-Javadoc)
	 * @see zakupki.InterfaceZlogic#parseAnswer()
	 */
	@Override
	public void parseAnswer(StringBuffer sb) {
		// TODO Auto-generated method stub
		// 1. найти  - дальше парсить данные.
		// 2. записать в файл или базу.
		// 3. ищем ссылку на след страницу
		String[] page = sb.toString().split("\n");
		for (int i = 0; i < page.length; i++) {
			if (page[i].startsWith(DIV_CLASS_REGISTERBOX)){
				parseItem(i, page);
			}
		}
		
	}
	
	public void parseItem(int pos, String[] page){
		//тут парсим кусок div
		int i = pos + 1;
		while (page[i].startsWith(DIV_CLASS_REGISTERBOX)){
			if (page[i].startsWith(TD_CLASS_DESCRIPT)){
				saveItemDescription(i, page);
			} else if (page[i].startsWith(TD_CLASS_AMOUNT)){
				saveItemAmount(i, page);
			} else if (page[i].startsWith(TD_CLASS_TENDER)){
				saveItemType(i, page);
			} else if (page[i].startsWith(TD_CLASS_PUBLISH)){
				saveItemPublish(i, page);
			}
			i++;
		}//while
	}
	
	public void saveItemDescription(int pos, String[] page){
		//тут парсим кусок до закрывающего td и сохраняем в базу или в файл csv - описание
	}
	
	public void saveItemAmount(int pos, String[] page){
		//тут парсим кусок до закрывающего td и сохраняем в базу или в файл csv - деньги
	}
	
	public void saveItemType(int pos, String[] page){
		//тут парсим кусок до закрывающего td и сохраняем в базу или в файл csv - тип тендера
	}
	
	public void saveItemPublish(int pos, String[] page){
		//тут парсим кусок до закрывающего td и сохраняем в базу или в файл csv - тип тендера
	}

}
