package crawler.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class used for simplify string operation such as substring or replacement
 * 
 * @author lijun
 * @version 1.0
 */
public class StringOperator {
	
	private static final char ESC = (char)27;
	private static final char NEWLINE = (char)10;
	private static final char CARRIAGE_RETURN = (char)13;
	private static final char PACKAGE_SEPARATOR = '.';
	
	public static boolean validPassword(String password)
	{
		if(password==null || password.length()<1)
			return false;
		char[] a = password.toCharArray();
		boolean hasNumber = false;
		boolean hasSpecialChar = false;
		boolean hasCharactor = false;
		boolean hasErrorChar = false;
		for(char c:a)
		{
			if((c>32 && c<48) || (c>57 && c<65) || (c>90 && c<97) || (c>122 && c<127))
				hasSpecialChar = true;
			if((c>64 && c<91) || (c>96 && c<123))
				hasCharactor = true;
			if(c>47 && c<58)
				hasNumber = true;
			if(c<33 || c>126)
				hasErrorChar = true;
		}
//		System.out.println(hasNumber+" "+hasSpecialChar+" "+hasCharactor+" "+hasErrorChar);
		if(hasNumber && hasSpecialChar && hasCharactor && !hasErrorChar)
			return true;
		return false;
	}
	
	public static String md5(String arg){
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(arg.getBytes());
			result = new BigInteger(1, md.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static double addpositive(String values)
	{
		String[] arrvalue = values.split(",");
		
		double total = 0;
		for(int i=0;i<arrvalue.length;i++)
		{
			double tmp = StringOperator.toDouble(arrvalue[i]);
			if(tmp>0)
				total += tmp;
		}
		return total;
	}
	
  public static String htmlSpecialCharsConvert(String html){
	  if(html == null)
		  return "";
	  return html.replaceAll("&","&amp;")
	  	.replaceAll("\"","&quot;")
		.replaceAll("'","&#039;")
		.replaceAll("<","&lt;")
		.replaceAll(">","&gt;")
	  .replaceAll(" ","&nbsp;");
  }
	  
	/**
	 * convert an inputstream into a stringbuffer,without changing its charset.
	 * @param in
	 * @param charsetName
	 * @return
	 */
	public static StringBuffer InputStreamToSB(InputStream in,String charsetName)
	{
		if(in==null)
			return null;
		StringBuffer sb = new StringBuffer("");
		try{
			InputStreamReader isr = new InputStreamReader(in,charsetName);
			int c = -1;
			while ((c=isr.read())!=-1){
				sb.append((char)c);	
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return sb;
	}
	/*public static StringBuffer InputStreamToSB(InputStream in)
	{
		if(in==null)
			return null;
		StringBuffer sb = new StringBuffer("");
		try{
			BufferedReader buffer=new BufferedReader(new InputStreamReader(in,"UTF-8"));
			String tempstr="";
			while ((tempstr=buffer.readLine())!=null){
				sb.append(tempstr);	
			}
			buffer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return sb;
	}*/
	
	/**
	 * find the maximum string length in the elements in input string array 
	 * @param arr
	 * @return
	 */
	public static int maxLengthInStringArray(String[] arr)
	{
		int maxLength = 0;
		if(arr!=null)
		{
			for(int i=0;i<arr.length;i++)
			{
				if(maxLength<arr[i].length())
					maxLength = arr[i].length();
			}
		}
		return maxLength;
	}
	
	public static String getSpaceStringByLength(int length)
	{
		StringBuffer sb = new StringBuffer("");
		for(int i=0;i<length;i++){
			sb.append(" "); 
		}
		return sb.toString();
	}
	
	public static String parseMyEL(String input,Hashtable htModel)
	{
		Pattern p = Pattern.compile(".*[#][{].+[}].*");
		Matcher m = p.matcher(input);
		if(m.matches())
			input = StringOperator.parseStringValue(input, htModel);
		return input;
	}
	/**
	 * 动态参数替换
	 * @param input:如#{ShFile.getName}
	 * @param htModel:保存了类实例，用于JAVA反射
	 * @return
	 */
	public static String parseStringValue(String input,Hashtable htModel)
	{
		StringBuffer sb = new StringBuffer(input);
		String toReplace = "";
		String replacement = "";
		String className = "";
		String methodName = "";
		int startIdx = sb.toString().indexOf("#{");
		int endIdx = sb.toString().indexOf("}");
		int pointIdx = -1;
		Object replaceObject = null;
		int offline = 0;
		Pattern p = null;
		while(startIdx!=-1 && endIdx!=-1 && startIdx < endIdx)
		{
			offline=0;
//			Logger.debugLine("","endIdx="+endIdx+" startIdx="+startIdx+" sb.length="+sb.toString().length());
			toReplace = sb.toString().substring(startIdx , endIdx+1);
//			Logger.debugLine("","toReplace="+toReplace);
			
			p = Pattern.compile("^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])[.](25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)[.](25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)[.](25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$");
//			Logger.debugLine("","toReplace="+toReplace);
			if(p.matcher(toReplace.substring(2,toReplace.length()-1)).matches()==false)//not an ip
	  			pointIdx = toReplace.lastIndexOf(PACKAGE_SEPARATOR);
//	  		Logger.debugLine("","pointIdx="+pointIdx);
			if(pointIdx!=-1)
			{
				methodName = toReplace.substring(pointIdx+1,toReplace.length()-1);
				className = toReplace.substring(2,pointIdx);//2表示'#{'的长度
//				Logger.debugLine("","methodName="+methodName);
//				Logger.debugLine("","classname:"+className);
				replaceObject = ReflectionOperator.methodReflect(htModel.get(className),methodName,null,null);
			}
			else
			{
				String parameterName = toReplace.substring(2,toReplace.length()-1);
				int idx = parameterName.indexOf(":");
				if(idx!=-1)
					parameterName = parameterName.substring(0,idx);
				replaceObject = htModel.get(parameterName);
			}
			if(replaceObject!=null)
			{
				replacement = replaceObject.toString();
				sb.delete(startIdx , endIdx+1);
				sb.insert(startIdx, replacement);
				offline = replacement.length()-(endIdx+1-startIdx);
			}
//			else
//				break;//直接返回输入字符串
//			Logger.debugLine("","string="+sb.toString());
//			Logger.debugLine("","endIdx="+endIdx+" offline="+offline+" left="+sb.toString().substring(endIdx+1+offline));
			startIdx = endIdx+1+offline+sb.toString().substring(endIdx+1+offline).indexOf("#{");
			endIdx = endIdx+1+offline+sb.toString().substring(endIdx+1+offline).indexOf("}");
			
//			Logger.debugLine("","endIdx="+endIdx);
		}
//		Logger.debugLine("","input="+sb.toString());
		return sb.toString();
	}
	
	public static String dayOfWeekConvert(int dayofweek,String lang)
	{
		switch(dayofweek)
		{
			case 1:
				if(lang.indexOf("en")!=-1)
					return "Sunday";
				else
					return "日";
			case 2:
				if(lang.indexOf("en")!=-1)
					return "Monday";
				else
					return "一";
			case 3:
				if(lang.indexOf("en")!=-1)
					return "Tuesday";
				else
					return "二";
			case 4:
				if(lang.indexOf("en")!=-1)
					return "Wednesday";
				else
					return "三";
			case 5:
				if(lang.indexOf("en")!=-1)
					return "Thursday";
				else
					return "四";
			case 6:
				if(lang.indexOf("en")!=-1)
					return "Friday";
				else
					return "五";	
			case 7:
				if(lang.indexOf("en")!=-1)
					return "Saturday";
				else
					return "六";					
		}
		return "";
	}
	public static String ISO2GB(String str){
		try{
			 return new String(str.getBytes("8859_1"),"gb2312");
		}catch(Exception e){
			return str;
		}		
	}
	
	public static String ISO2UTF(String str){
		try{
			 return new String(str.getBytes("8859_1"),"UTF-8");
		}catch(Exception e){
			return str;
		}		
	}
	
	public static String ISO2GBORUTF(String input)
	{
		if(getEncoding(input).equals("ISO8859_1"))
		{
			if(getEncoding(ISO2GB(input)).equals("UTF-8"))
			{
//				System.out.println("input changed to ="+ISO2UTF(input));
				return ISO2UTF(input);
			}
			else
				return ISO2GB(input);
		}
		else if(getEncoding(input).equals("UTF-8"))
			return UTF2GB(input);
		return input;
	}
	
	public static String GB2ISO(String str){
		try{
			 return new String(str.getBytes("gb2312"),"8859_1");
		}catch(Exception e){
			return str;
		}		
	}
	public static byte[] gbk2utf8(String chenese) {
        char c[] = chenese.toCharArray();
        byte[] fullByte = new byte[3 * c.length];
        for (int i = 0; i < c.length; i++) {
            int m = (int) c[i];
            String word = Integer.toBinaryString(m);

            StringBuffer sb = new StringBuffer();
            int len = 16 - word.length();
            for (int j = 0; j < len; j++) {
                sb.append("0");
            }
            sb.append(word);
            sb.insert(0, "1110");
            sb.insert(8, "10");
            sb.insert(16, "10");

            String s1 = sb.substring(0, 8);
            String s2 = sb.substring(8, 16);
            String s3 = sb.substring(16);

            byte b0 = Integer.valueOf(s1, 2).byteValue();
            byte b1 = Integer.valueOf(s2, 2).byteValue();
            byte b2 = Integer.valueOf(s3, 2).byteValue();
            byte[] bf = new byte[3];
            bf[0] = b0;
            fullByte[i * 3] = bf[0];
            bf[1] = b1;
            fullByte[i * 3 + 1] = bf[1];
            bf[2] = b2;
            fullByte[i * 3 + 2] = bf[2];

        }
        return fullByte;
    }

	
	public static String GB2UTF(String str){
		try{
			 return new String(str.getBytes("gb2312"),"UTF-8");
		}catch(Exception e){
			return str;
		}		
	}
	
	public static String UF2ISO(String str){
		try{
			 return new String(str.getBytes("UTF-8"),"8859_1");
		}catch(Exception e){
			return str;
		}		
	}
	
	public static String UTF2GB(String str){
		try{
			 return new String(str.getBytes("UTF-8"),"GB2312");
		}catch(Exception e){
			return str;
		}		
	}
	public static String ToGB(String str){
		try{
			 return new String(str.getBytes(),"GB2312");
		}catch(Exception e){
			return str;
		}		
	}
	public static String ToUTF(String str){
		try{
			 return new String(str.getBytes(),"UTF-8");
		}catch(Exception e){
			return str;
		}		
	}
	/**
	 * get the first part or second part by the "note" and "position"
	 * 
	 * @param input
	 * @param note
	 *            分隔关键字
	 * @param position
	 *            若为1,则返回第一部分，否则返回第二部分
	 * @return
	 */
	public static String getSubString(String input, String note, int position) {
		int index = input.indexOf(note);
		String strResult = "";
		if (position == 1)
			strResult = input.substring(0, index);
		else
			strResult = input.substring(index + 1);
		return strResult;
	}

	/**
	 * replace the byte in "content" with "replacement"
	 * 
	 * @param content
	 * @param original
	 * @param replacement
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String allReplace(String content, byte original, String replacement){
		StringBuffer strResult = new StringBuffer();
		byte[] strCont = content.getBytes();
		for (int i = 0; i < strCont.length; i++) {
			if (strCont[i] == original) {
				strResult.append(replacement);
//				Log.ShowByLine("Replace -" + (byte)original +"- to be -"+replacement+"-");
			} else
				strResult.append((char) strCont[i]);
		}
		return strResult.toString();
	}

	public static String allReplace(String content, byte original, String replacement,String logPath) {
		StringBuffer strResult = new StringBuffer();
		byte[] strCont = content.getBytes();
		for (int i = 0; i < strCont.length; i++) {
			if (strCont[i] == original) {
				strResult.append(replacement);
//				Log.ShowByLine(logPath,"Replace -" + (byte)original +"- to be -"+replacement+"-");
			} else
				strResult.append((char) strCont[i]);
		}
		return strResult.toString();
	}
	/**
	 * 把"content"内双引号里的内容的反斜杠转义
	 * 
	 * @param content
	 * @return
	 */
	public static String removeTranMean(String content) {
		StringBuffer strResult = new StringBuffer();
		byte[] strCont = content.getBytes();
		boolean isQuote = false;
		boolean hasAppend = false;
		for (int i = 0; i < strCont.length; i++) {
			if (strCont[i] == '"' && isQuote == false)
				isQuote = true;
			else if (strCont[i] == '"' && isQuote == true)
				isQuote = false;
			if (isQuote == true) {
				// System.out.println(isQuote);
				if (strCont[i] == '\\') {
					// System.out.println("strCont[i]=='n'");
					strResult.append("\\\\\\");
					hasAppend = true;
				}
			}

			if (hasAppend == false) {
				strResult.append((char) strCont[i]);
			} else
				hasAppend = false;
		}
		return strResult.toString();
	}

	/**
	 * count the amount of char "c" in "input"
	 * 
	 * @param input
	 * @param c
	 * @return
	 */
	public static int countChar(String input, char c) {
		int count = 0;
		if (input != null && !input.equals("")) {
			char[] chs = new char[input.length()];
			input.getChars(0, input.length(), chs, 0);
			for (int i = 0; i < chs.length; i++) {
				if (chs[i] == c)
					count++;
			}
		}
		return count;
	}

	/**
	 * change string into int
	 * 
	 * @param strValue
	 * @return
	 */
	public static int toInt(String strValue) {
		int intValue = -1;
		if(strValue!=null && !strValue.equals(""))
		{
			try {
				intValue=(int)Double.parseDouble(strValue);
//				intValue = Integer.parseInt(strValue);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return intValue;
	}
	
	public static double toDouble(String strValue) {
		double dValue = 0;
		if(strValue!=null && !strValue.equals("") && !strValue.equals("N/A"))
		{
			try {
				dValue=Double.parseDouble(strValue);
			} catch (Exception ex) {
//				ex.printStackTrace();
				System.out.println("Invalid double:"+strValue);
				dValue = 0;
			}
		}
		return dValue;
	}
	
	public static long toLong(String strValue) {
		long intValue = 0;
		if(strValue!=null && !strValue.equals("") && !strValue.equals("N/A"))
		{
			try {
				intValue = (long)Double.parseDouble(strValue);
//				intValue = Integer.parseInt(strValue);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return intValue;
	}
/*	public static String toGb2312(String str,String fromCharSet,String toCharSet)
	{
        if(str==null)
            return null;
        try{
            String convert=new String(str.getBytes(fromCharSet),"gb2312");
            return convert;
        }catch(Exception e){
        	e.printStackTrace();
        }
        return null;
	}*/
	
	public static String beanAttribute2String(Object bean)
	{
		StringBuffer sb = new StringBuffer("");
		if(bean!=null)
		{
			Class beanClass = bean.getClass();
			sb.append(beanClass.getName()).append("\n");
			Field[] fields = beanClass.getDeclaredFields();
			boolean accessible = true;
			if(fields!=null)
			{
				try {
					for(Field field:fields){
						if(field.isAccessible()==false)
						{
							field.setAccessible(true);
							accessible = false;
						}
						sb.append(field.getName()).append(":");
						Object filedObject = field.get(bean);
						if(filedObject instanceof Collection)
						{
							List fieldInList = (List)filedObject;
							for(Object obj:fieldInList)
							{
								sb.append(obj.toString());
							}
						}
						else
							sb.append(filedObject);
						sb.append("\n");
						if(accessible == false)
						{
							field.setAccessible(false);
							accessible = true;
						}
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
	
	static class JavaBean{
		private String a = "aaa";
		public String b = null;
		public int c = 0;
	}

	public static String getESC() {
		return new String(new byte[]{ESC});
	}

	public static String getCARRIAGE_RETURN() {
		return new String(new byte[]{CARRIAGE_RETURN});
	}

	public static String getNEWLINE() {
		return new String(new byte[]{NEWLINE});
	}
	
	public static String getEncoding(String content)
	{
		if(content==null)
			return "ISO8859_1";
		String encodeName = "";
		byte[] temp;  
		String compstring;  
		try {
			temp = content.getBytes("ISO8859_1");
			compstring = new String(temp,"ISO8859_1");  
			if(content.equals(compstring))  
			{
//				System.out.println("it's ISO8859_1");
				encodeName = "ISO8859_1";
			}
			else
			{
//				System.out.println("it's NOT ISO8859_1");
				temp = content.getBytes("GB2312");
				compstring = new String(temp,"GB2312");  
				if(content.equals(compstring))  
				{
//					System.out.println("it's GB2312");
					encodeName = "GB2312";
				}
				else
				{
//					System.out.println("it's NOT GB2312");
					
					temp = content.getBytes("UTF-8");
					compstring = new String(temp,"UTF-8");  
					if(content.equals(compstring))  
					{
//						System.out.println("it's UTF-8");
						encodeName = "UTF-8";
					}
//					else
//						System.out.println("it's NOT UTF-8");
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}  
		return encodeName;
	}
	
	/**
	 * @param formua:e.g. echo 200 + 5 + 1 + 22 >> aa ,the result is 228
	 * @return
	 */
	public static String stringAdd(String formula)
	{
		StringTokenizer st = new StringTokenizer(formula);
		String lastToken = "";
		String currentToken = "";
		int total = 0;
		StringBuffer result = new StringBuffer("");
		boolean isBegin = false;
		boolean hasAddTotal = false;
	    while (st.hasMoreTokens()) {
	    	currentToken = st.nextToken();
	    	if(currentToken.equals("+"))
	    	{
	    		if(isBegin == false)
	    		{
	    			total = total + StringOperator.toInt(lastToken);
//	    			System.out.println("lastToken:"+lastToken+":"+StringOperator.toInt(lastToken));
	    			isBegin = true;
	    		}
	    		if(st.hasMoreTokens())//取下一个数字
	    		{
	    			currentToken = st.nextToken();
	    			total = total + StringOperator.toInt(currentToken);
//	    			System.out.println("currentToken:"+currentToken+":"+StringOperator.toInt(currentToken));
	    		}
	    	}
	    	else
	    	{
	    		if(isBegin == false)
		    		result.append(lastToken).append(" ");
	    		else
	    		{
	    			if(hasAddTotal == false)
	    			{
	    				result.append(total).append(" ");
	    				hasAddTotal = true;
	    			}
	    			result.append(currentToken).append(" ");
	    		}
	    	}
	    	lastToken = currentToken;
	    }
//	    System.out.println("total:"+ total);
	    return result.toString().trim();
	}
	
	public static String list2String(ArrayList list)
	{
		if(list==null)
			return "";
		StringBuffer sb = new StringBuffer();
		for(Object row:list)
		{
			if(row instanceof ArrayList)
			{
				for(Object value:(ArrayList)row)
					sb.append(value).append("\t");
			}
			else
				sb.append(row).append("\t");
			sb.append("\n");
		}
		return sb.toString();
	}
	
	/**
	 * return value in hashmp that match the key, if no match can be found , return the key
	 * @param hm
	 * @param key
	 * @return
	 */
	public static String getMapValue(HashMap<String,String> hm,String key)
	{
		if(hm!=null){
			Object obj = hm.get(key);
			if(obj!=null)
				return obj.toString();
		}
		return key;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		System.out.println(StringOperator.stringAdd(" dsf echo 2 + 5 + 13 + 1 + 22 + 9 - 10 >> aa dfs"));
//		System.out.println(StringOperator.ISO2GB(FileOperator.read("C:\\a.txt")));
		/*Hashtable htModel = new Hashtable();
		String url = "select ip,actiontime,avg_value/1024/1024 as memfree from #{tablename} where #{hosts} and subname='phy_free' group by ip,actiontime order by ip,actiontime";
		htModel.put("tablename", "rrd_data_pdsn_hour_20090306");
//		System.out.println("url1="+url);
		url = StringOperator.parseStringValue(url,htModel);
		System.out.println("url3="+url);*/
//		getSpaceStringByLength(10);
		/*long a = 42202187;
		long b = 93;
		float c = (float)b/(float)a;
		
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMaximumFractionDigits(1); 
//		nf.format(c);
		System.out.println(nf.format(c));*/
		/*NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(2); 
		System.out.println(nf.format(new Double(0)));*/
		
//		Hashtable<String, Object> hashTmpParas = new Hashtable<String, Object>();
//		hashTmpParas.put("10.231.152.75","GD-GZ-PMC-AAA-1.CDMA");
//		hashTmpParas.put("areawhere"," gz,sz ");
//		System.out.println(StringOperator.parseMyEL("#{10.231.152.75}", hashTmpParas));
//		System.out.println(" from #{tablename} pcfip #{areawhere} ss".substring(18));
		/*System.out.println(StringOperator.toDouble("NaN"));
		if(StringOperator.toDouble("NaN") == Double.NaN)
			System.out.println("kao");
			If d1 and d2 both represent Double.NaN, then the equals method returns true, even though Double.NaN==Double.NaN has the value false. 
			*/
	/*	if(Double.toString(StringOperator.toDouble("NaN")).equals("NaN"))
			System.out.println(Double.toString(Double.NaN));*/
		System.out.println(StringOperator.md5("asaedds1"));
		System.out.println(StringOperator.toDouble("20.0"));
	}
}
