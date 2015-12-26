/**
 * 
 */
package zakupki;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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
	final String UL_CLASS_PAGING = "<li><a href=\"javascript:goToPage(";
	String workdir = "";
	
	ArrayList<String> al_Type = new ArrayList<String>();
	ArrayList<String> al_Description = new ArrayList<String>();
	ArrayList<String> al_Organization = new ArrayList<String>();
	ArrayList<String> al_Amount = new ArrayList<String>();
	ArrayList<String> al_Currency = new ArrayList<String>();
	ArrayList<String> al_Published = new ArrayList<String>();
	public void start(String q) {
		// TODO Auto-generated method stub
		String url = "http://zakupki.gov.ru/epz/order/quicksearch/search.html?searchString=";
		String query = "";
		try {
			query = URLEncoder.encode(q, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		System.out.println("start!");
		url = setURLPage(q, 1);
		ArrayList<String> line = ZCommon.UrlHttpGetNew(url);
		//System.out.println(line);
		parseAnswer(line, q);
		writeAnswer(q);
	}
	/* (non-Javadoc)
	 * @see zakupki.InterfaceZlogic#parseAnswer()
	 */
	@Override
	public void parseAnswer(ArrayList<String> al, String q) {
		// TODO Auto-generated method stub
		//Check number of page
		int numPage = getCountPage(al);
		String pageURL = "";
		System.out.println("Count page: "+numPage);
		//load from all pages
		for (int j=1; j<= numPage; j++){
			pageURL = setURLPage(q, j);
			//System.out.println(pageURL);
			al = ZCommon.UrlHttpGetNew(pageURL);//TODO - сделать норм URL
			for (int i = 0; i < al.size(); i++) {
				if (al.get(i).contains(DIV_CLASS_REGISTERBOX)){
					//System.out.println("Find div "+i);
					parseItem(i, al);
				}
			}
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	public String setURLPage(String query, int numPage){
		try {
			query = URLEncoder.encode(query, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = "http://zakupki.gov.ru/epz/order/quicksearch/update.html?"+
					"placeOfSearch=FZ_44&"+
					"_placeOfSearch=on&"+
					"placeOfSearch=FZ_223&"+
					"_placeOfSearch=on&"+
					"_placeOfSearch=on&"+
					"priceFrom=0&"+
					"priceTo=200+000+000+000&"+
					"publishDateFrom=&"+ //publish date from
					"publishDateTo=&"+   //publish date to
					"updateDateFrom=&"+
					"updateDateTo=&"+
					"orderStages=AF&"+ //подача заявок
					"_orderStages=on&"+ 
					"orderStages=CA&"+ //работа коммисии
					"_orderStages=on&"+
					"orderStages=PC&"+ //закупка завершена
					"_orderStages=on&"+
					"orderStages=PA&"+ //закупка отменена
					"_orderStages=on&"+
					"sortDirection=false&"+
					"sortBy=UPDATE_DATE&"+
					"recordsPerPage=_50&"+
					"pageNo="+numPage+"&"+
					"searchString="+query+"&"+
					"strictEqual=false&"+
					"morphology=true&"+ //с учетом всех форм слова
					"showLotsInfo=false&"+
					"isPaging=true&"+
					"isHeaderClick=&"+
					"checkIds=";
		return url;
	}
	
	public String setURLPageExtendedSearch(String query, int numPage){
		String districtIds = "5277399";
		String regionIds = "5277407";
		try {
			query = URLEncoder.encode(query, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = "http://zakupki.gov.ru/epz/order/extendedsearch/search.html?"
				+ "placeOfSearch=FZ_44"
				+ "&placeOfSearch=FZ_223"
				+ "&orderPriceFrom="
				+ "&orderPriceTo="
				+ "&orderPriceCurrencyId=-1"
				+ "&deliveryAddress="
				+ "&participantName="
				+ "&orderPublishDateFrom=14.12.2015"
				+ "&orderPublishDateTo=20.12.2015"
				+ "&orderUpdateDateFrom="
				+ "&orderUpdateDateTo="
				+ "&customer.title="
				+ "&customer.code="
				+ "&customer.fz94id="
				+ "&customer.fz223id="
				+ "&customer.inn="
				+ "&agency.title="
				+ "&agency.code="
				+ "&agency.fz94id="
				+ "&agency.fz223id="
				+ "&agency.inn="
				+ "&districtIds="+districtIds
				+ "&regionIds="+regionIds 
				+ "&orderStages=AF"
				+ "&orderStages=CA"
				+ "&orderStages=PC"
				+ "&orderStages=PA"
				+ "&searchTextInAttachedFile="
				+ "&applSubmissionCloseDateFrom="
				+ "&applSubmissionCloseDateTo="
				+ "&searchString="+query
				+ "&morphology=true"
				+ "&strictEqual=false";
		return url;
		/* districtIds - федеральный округ
		 * 5277399 - ДФО
		 * 
		 * 
		 * regionIds - Субъект РФ
		 * -------------------------
		 * 5277403 - Амурская обл
		 * 5277407 - Еврейская Аобл
		 * 5277404 - Камчатский край
		 * 5277405 - Магаданская обл
		 * 5277401 - Приморский край
		 * 5277400 - Саха/Якутия респ
		 * 5277406 - Сахалинская обл
		 * 5277402 - Хабаровский край
		 * 5277408 - Чукотский АО
		 * -------------------------
		 * 
		 * */
	}
	
	public int getCountPage(ArrayList<String> al){
		int count = 0;
		int i = 0;
		boolean condition = true;
		ArrayList<String> page = new ArrayList<>();
		String pg = "";
		while (condition){
			if (al.get(i).contains(UL_CLASS_PAGING)){
				page.add(al.get(i));
				pg = al.get(i);
			}
			if (i+1>=al.size()){
				condition = false;
			}
			i++;
		}
		pg = ZCommon.removeHTMLSimbols(pg);
		pg = pg.trim();
		count = Integer.parseInt(pg);
		//System.out.println("count: "+count);
		return count;
	}
	
	public void parseItem(int pos, ArrayList<String> al){
		//тут парсим кусок div
		boolean condition = true;
		int i = pos + 1;
		while (condition){
			if (al.get(i).endsWith(TD_CLASS_DESCRIPT)){
				saveItemDescription(i, al);
			} else if (al.get(i).endsWith(TD_CLASS_AMOUNT)){
				saveItemAmount(i, al);
			} else if (al.get(i).endsWith(TD_CLASS_TENDER)){
				saveItemType(i, al);
			} else if (al.get(i).endsWith(TD_CLASS_PUBLISH)){
				saveItemPublish(i, al);
			} else if (al.get(i).contains(DIV_CLASS_REGISTERBOX)){
				condition = false;
			}
			if (i+1>=al.size()){
				condition = false;
			}
			i++;
			
		}//while
	}
	
	public void saveItemDescription(int pos, ArrayList<String> al){
		//тут парсим кусок до закрывающего td и сохраняем в базу или в файл csv - описание
		boolean condition = true;
		int i = pos + 1;
		String description = "";
		String nameOrganization = "";
		//String regNumber; TODO
		while (condition){
			if (al.get(i).contains("<dd class=\"nameOrganization\">")){
				do {
					nameOrganization = nameOrganization + al.get(i).trim();
					i++;
				} while (!al.get(i).contains("</dd>"));
				nameOrganization = nameOrganization + "</dd>";
				//nameOrganization = al.get(i+3);
			} else if (al.get(i).contains("<dd>")){
				condition = false;
				do {
					description = description + al.get(i).trim();
					i++;
				} while (!al.get(i).contains("</dd>"));
				description = description + "</dd>";
			}
			if (i+1>=al.size()){
				condition = false;
			}
			i++;
		}//while
		description = ZCommon.removeHTMLSimbols(description);
		al_Description.add(description);
		//System.out.println("Description: "+description);
		nameOrganization = ZCommon.removeHTMLSimbols(nameOrganization);
		//System.out.println("Name Org: "+nameOrganization);
		al_Organization.add(nameOrganization);
	}
	
	public void saveItemAmount(int pos, ArrayList<String> al){
		//тут парсим кусок до закрывающего td и сохраняем в базу или в файл csv - деньги
		boolean condition = true;
		int i = pos + 1;
		String amount = "";
		String currency = "";
		
		while (condition){
			if (al.get(i).contains("<dt>")){
				amount = al.get(i);
			} else if (al.get(i).contains("<dd>")){
				condition = false;
				currency = al.get(i);
			}
			if (i+1>=al.size()){
				condition = false;
			}
			i++;
		}//while
		amount = amount.trim();
		amount = ZCommon.removeHTMLSimbols(amount);
		amount = amount.replaceAll(" ", "");
		//System.out.println("Amount: "+amount);
		al_Amount.add(amount);
		currency = currency.trim();
		currency = ZCommon.removeHTMLSimbols(currency);
		al_Currency.add(currency);
		//System.out.println("Currency: "+currency);
	}
	
	public void saveItemType(int pos, ArrayList<String> al){
		//тут парсим кусок до закрывающего td и сохраняем в базу или в файл csv - тип тендера
		boolean condition = true;
		int i = pos + 1;
		String type = "";
		
		while (condition){
			if (al.get(i).contains("<dt>")){
				condition = false;
				type = al.get(i);
			} 
			if (i+1>=al.size()){
				condition = false;
			}
			i++;
		}//while
		type = type.trim();
		type = ZCommon.removeHTMLSimbols(type);
		//System.out.println("Type: "+type);
		al_Type.add(type);
	}
	
	public void saveItemPublish(int pos, ArrayList<String> al){
		//тут парсим кусок до закрывающего td и сохраняем в базу или в файл csv - тип тендера
		boolean condition = true;
		int i = pos + 1;
		String publishing = "";
		
		while (condition){
			if (al.get(i).contains("<li class=\"publishingDate\">")){
				condition = false;
				publishing = al.get(i);
			} 
			if (i+1>=al.size()){
				condition = false;
			}
			i++;
		}//while
		publishing = publishing.trim();
		publishing = ZCommon.removeHTMLSimbols(publishing);
		//System.out.println("Publish at: "+publishing);
		al_Published.add(publishing);
	}
	
	public void writeAnswer(String q){
		//quick search
		String filename = workdir+"\\"+q+"_output.csv";
		ZCommon.writeFile(filename, "Type;Description;Organization;Amount;Currency;Published", false);
		String text = "";
		for (int i = 0; i < al_Type.size(); i++) {
			text = al_Type.get(i)+";"+al_Description.get(i)+";"+al_Organization.get(i)+";"+al_Amount.get(i)+";"+al_Currency.get(i)+";"+al_Published.get(i);
			ZCommon.writeFile(filename, text, true);
		}
	}
	

}
