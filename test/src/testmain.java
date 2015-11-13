import java.text.CharacterIterator;
import java.text.StringCharacterIterator;


public class testmain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(checkSymbols("abc'd(e<f"));
	}
	public static String checkSymbols(String checkString){
		//��������� � ���������� ��������� ������� � ������, � ������:
		// ' - ��������� �������
		// <> - ����� ������ ������
		// () - ������ ������
		// , - ������� . - �����, ?! - �������������� �����
		/*
		checkString = checkString.replaceAll("'", "\\\\'");
		checkString = checkString.replaceAll("<", "\\\\<");
		checkString = checkString.replaceAll(">", "\\\\>");
		checkString = checkString.replaceAll("(", "\\\\(");
		checkString = checkString.replaceAll(")", "\\\\)");
		checkString = checkString.replaceAll(",", "\\\\,");
		checkString = checkString.replaceAll(".", "\\\\.");
		checkString = checkString.replaceAll("?", "\\\\?");
		checkString = checkString.replaceAll("!", "\\\\!");
		some comment for testing commit
		another comment for commit
		*/
		 if (checkString == null || checkString.isEmpty()) {
		        return "";
		    }
		    int len = checkString.length();
		    // ������� ��������� �����, ����� �� �������� ������ �����
		    final StringBuilder result = new StringBuilder(len + len / 4);
		    final StringCharacterIterator iterator = new StringCharacterIterator(checkString);
		    char ch = iterator.current();
		    while (ch != CharacterIterator.DONE) {
		        if (ch == '\'') {
		            result.append("\\\'");
		        } else if (ch == '(') {
		            result.append("\\(");
		        } else if (ch == '>') {
		            result.append("\\>");
		        } else if (ch == '"') {
		            result.append("\\\"");
		        } else {
		            result.append(ch);
		        }
		        ch = iterator.next();
		    }
		  return result.toString();
	}//checkSymbols

}
