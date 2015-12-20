package zakupki;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class ZCommon {

	public ZCommon() {
		// TODO Auto-generated constructor stub
	}
	public static String UrlHttpPost(String url, String nameParam, String parameters){
		//��������� ��� ���������� �������� post �������
		HttpPost post = new HttpPost();
		HttpResponse response;
		HttpClient httpClient = new DefaultHttpClient();
		String line = "";
		int connTry = 0;
		do { // ������� ��� connection timeout ����������� ��� 4 ����. �� ���� ���� �������.
		try {
			post = new HttpPost(url);
			ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
	        params.add(new BasicNameValuePair(nameParam, parameters));
	        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,HTTP.UTF_8);
			post.setEntity(entity);
			response = httpClient.execute(post);
			line = EntityUtils.toString(response.getEntity());
			post.abort();
			connTry = 5;
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			connTry++;
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//System.out.println("Error111 ");
			//e1.printStackTrace();
		} 
		} while (connTry<5);
		return line;
	}//URlHttpPost
	public static String UrlHttpGet(String url){
		String line = "";
		int connTry = 0;
		do { // ������� ��� connection timeout ����������� ��� 4 ����. �� ���� ���� �������.
		try {
	        URL url2 = new URL(url);
	        BufferedReader reader = new BufferedReader(new InputStreamReader(url2.openStream(),"UTF-8"));
			line = reader.readLine(); 
			reader.close();
	        connTry = 5;
	    } catch (MalformedURLException e) {
	        // ...
	    	System.out.println("Error: "+e);
			e.printStackTrace();
	    } catch (IOException e) {
	    	connTry++;
	    	//System.out.println("Error: "+e);
			//e.printStackTrace();
	    }//try
		} while(connTry<5);
		return line;
	}//UrlHttpGet
	public static ArrayList<String> UrlHttpGetNew(String url){
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		HttpResponse response = null;
		try {
			response = client.execute(request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int responseCode = response.getStatusLine().getStatusCode();
		//System.out.println("respCode: "+responseCode);
		// Get the response
		BufferedReader rd = null;
		try {
			rd = new BufferedReader
			  (new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(rd);    
		String line = "";
		ArrayList<String> answerAL = new ArrayList<>();
		//StringBuffer textView = new StringBuffer();
		try {
			
			while ((line = rd.readLine()) != null) {
				byte ptext[] = line.getBytes(); 
				line = new String(ptext);
				//textView.append(line);
				//System.out.println(line);
				answerAL.add(line);
			}
			//System.out.println(textView);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return answerAL;
    
	}
	 public static String removeHTMLSimbols(String htmlString)
	    {
	          // Remove HTML tag from java String   
	        String noHTMLString = htmlString;//.replace("\<.*?\>", "");
	        //Remove <li> </li>
	        noHTMLString = noHTMLString.replaceAll("<li>", "");
	        noHTMLString = noHTMLString.replaceAll("<li([^<]*)>", "");
	        noHTMLString = noHTMLString.replaceAll("</li>", "");
	        //Remove <dt></dt> 
	        noHTMLString = noHTMLString.replaceAll("<dt>|</dt>", "");
	        //Remove <dd></dd> 
	        noHTMLString = noHTMLString.replaceAll("<dd>", "");
	        noHTMLString = noHTMLString.replaceAll("<dd([^<]*)>", "");
	        noHTMLString = noHTMLString.replaceAll("</dd>", "");
	        //Remove title="
	        noHTMLString = noHTMLString.replaceAll("title=\"", "");
	        //Remove <a></a>
	        noHTMLString = noHTMLString.replaceAll("<a([^<]*)>", "");
	        noHTMLString = noHTMLString.replaceAll("</a>", "");
	        //Remove <b></b>
	        noHTMLString = noHTMLString.replaceAll("<b>|</b>", "");
	        // Remove Carriage return from java String
	        noHTMLString = noHTMLString.replaceAll("<br/>", " ");
	        
	        // Remove New line from java string and replace html break
	        //noHTMLString = noHTMLString.replaceAll("n", " ");
	        //noHTMLString = noHTMLString.replaceAll("'", "&#39;");
	        noHTMLString = noHTMLString.replaceAll("&quot;","\"");
	        noHTMLString = noHTMLString.replaceAll("&nbsp;"," ");
	        noHTMLString = noHTMLString.replaceAll("&laquo;|&raquo;","\"");
	        //some symbols
	        noHTMLString = noHTMLString.replaceAll("&#8470;","�");
	        noHTMLString = noHTMLString.replaceAll("&mdash;","�");
	        noHTMLString = noHTMLString.replaceAll("&ndash;","-");
	        noHTMLString = noHTMLString.replaceAll("&#034;","\"");
	        //alphabet 
	        noHTMLString = noHTMLString.replaceAll("&#1040;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1041;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1042;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1043;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1044;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1045;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1046;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1047;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1048;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1049;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1050;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1051;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1052;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1053;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1054;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1055;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1056;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1057;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1058;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1059;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1060;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1061;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1062;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1063;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1064;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1065;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1066;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1067;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1068;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1069;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1070;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1071;","�");
	        
	        noHTMLString = noHTMLString.replaceAll("&#1072;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1073;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1074;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1075;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1076;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1077;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1078;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1079;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1080;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1081;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1082;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1083;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1084;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1085;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1086;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1087;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1088;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1089;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1090;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1091;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1092;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1093;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1094;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1095;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1096;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1097;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1098;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1099;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1100;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1101;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1102;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1103;","�");
	        noHTMLString = noHTMLString.replaceAll("&#1105;","�");
	        
	        //if it has ; we need to replace this to .
	        noHTMLString = noHTMLString.replaceAll(";", ".");
	        return noHTMLString;
	 }
	
	 public static void writeFileUTF(String tofile, String text, boolean append){
			BufferedWriter out;
			try {
				out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tofile, append), "UTF-8"));
				if (!text.equals(""))
					out.append(text+"\n");
				out.close();
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}// write to file UTF-8
	 public static void writeFile(String tofile, String text, boolean append){
			FileWriter writer = null;
		    try {
		     writer = new FileWriter(new File(tofile), append);
		     if (!text.equals(""))
		    	 writer.write(text+"\n");
		     writer.close();
		    } catch (Exception ex) {
		    	System.out.println("Error: "+ex);
				ex.printStackTrace();
		    }
	}// write to file


}
