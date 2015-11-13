

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import java.net.URLEncoder;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.Charsets;




/**
 * Servlet implementation class HelloWorld
 */
@WebServlet("/HelloWorld")
public class HelloWorld extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1251";
	private static final int CR = (int)'\r';
	private static final int LF = (int)'\n';   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloWorld() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = "";
		//PrintWriter out = response.getWriter();
        //out.print("<h1>Hello Servlet1</h1>");
		//req.getRequestDispatcher("mypage.jsp").forward(req, resp);
		
		request.getRequestDispatcher("New_HW.jsp").forward(request, response);
		//super.doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//first method
		/*String description = request.getParameter("description");
		String txtfile = request.getParameter("txtfile");
		
		response.getWriter().println("description="+description);
		response.getWriter().println("data="+txtfile);
		
		response.getWriter().println("POST request body:");
		
		InputStreamReader reader = new InputStreamReader(request.getInputStream());
		int c;
		while ((c=reader.read())>=0) {
			response.getWriter().print((char)c);
		}*/
		//another method
		//Поток, в который будет писаться содержимое (в принципе может быть любой OutputStream)
        //request.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();
		FileOutputStream fos = new FileOutputStream("savefile.txt");
	    int[] dataSlice = extractData(request);
	    int i;
	    for(i=0; i<dataSlice.length;i++) { 
	    	//out.println(dataSlice[i]);
	    	fos.write(dataSlice[i]);
	    }
	    fos.flush();
	    fos.close();
	    out.println("Good!");
	    String s = "";
	    String example = "";
	    try {
			File settingsfile = new File("savefile.txt");
			final int length = (int) settingsfile.length();
			if (length != 0) {
				char[] cbuf = new char[length];
				InputStreamReader isr = new InputStreamReader(
						new FileInputStream(settingsfile)); //CP1251
				
				final int read = isr.read(cbuf);
				s = new String(cbuf, 0, read);
				
				String[] str = s.split("\n");
				byte ptext[] = s.getBytes("CP1251");
				example = new String(ptext, Charsets.UTF_8);
				//if (example.contains("Строка")){
				//	example = "";
				//}
				//out.println(s);
				isr.close();
			}
		} catch (FileNotFoundException e) {
			//System.out.println("File settings.cfg not found!");
			//System.exit(2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    request.setAttribute("file", example);
	    
	    request.getRequestDispatcher("Response.jsp").forward(request, response);
		//out.println(request.getParameter("name1"));
		//out.println(request.getParameter("name2"));
		//out.println(request.getParameterNames().toString());
	}
	private int[] extractData(HttpServletRequest request) throws IOException
    {
// Содержимое пришедших байтов их запроса (содержимое приходящего файла)
    InputStream is = request.getInputStream();
    int[] data = new int[request.getContentLength()];
    int bytes;
    int counter = 0;
    while((bytes=is.read())!=-1)
      {
      data[counter]=bytes;
      counter++;
      }
    is.close();

// Определение индексов срезки
    int i;
    int beginSliceIndex = 0;
// Конечный индекс срезки - длина границы + доп. символы.
    int endSliceIndex = data.length - getBoundary(request).length()-9;

    for(i = 0; i < data.length; i++)
      {
// Начальный индекс срезки: после того как встретятся 2 раза подряд \r\n
      if(data[i] == CR && data[i+1] == LF && data[i+2] == CR && data[i+3] == LF)
        {
        beginSliceIndex = i+4;
        break;
      }
    }

  int[] dataSlice = new int[endSliceIndex-beginSliceIndex+1];
  for(i = beginSliceIndex; i<=endSliceIndex; i++)
    {
    dataSlice[i-beginSliceIndex]=data[i];
    }

  return dataSlice;
  }
	// Поиск границы
	private String getBoundary(HttpServletRequest request)
	  {
	  String cType = request.getContentType();
	  return cType.substring(cType.indexOf("boundary=")+9);
	  }
}

