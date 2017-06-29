package crawler.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Handsomeli
 *
 */
public class ValidateOperator {

	public static boolean mailAddress(String mailAddr)
	{
		if(mailAddr!=null && !mailAddr.equals("")){
			Pattern p = Pattern.compile("^(([0-9a-zA-Z]+)|([0-9a-zA-Z]+[_.0-9a-zA-Z-]*[0-9a-zA-Z]+))@([a-zA-Z0-9-]+[.])+([a-zA-Z]{2}|net|com|gov|mil|org|edu|int)$");
			Matcher m = p.matcher(mailAddr);
			return m.find();
		}else
			return false;		
	}
	
//	浮点型判断
	 public static boolean isDecimal(String str) {
		 boolean result = false;
	  if(str==null || "".equals(str))
	   return result;  
	  Pattern pattern = Pattern.compile("\\-*[1-9]*[0-9]*(\\,\\d+)*(\\.?)[0-9]+(\\%?)");
	  result = pattern.matcher(str).matches();
	  if(result==false)
	  {
		  pattern = Pattern.compile("\\-*0+(\\.)+[0-9]+(\\%?)");
		  return pattern.matcher(str).matches();
	  }
	  return result;
	  
	 }
	 
	 public static boolean isFloat(String str) {
		 boolean result = false;
	  if(str==null || "".equals(str))
	   return result;  
	  if(str.startsWith("0") && !str.startsWith("0."))
		  return result;  
	  Pattern pattern = Pattern.compile("\\-*[1-9]*[0-9]*(\\,\\d+)*\\.[0-9]+");
	  result = pattern.matcher(str).matches();
	  if(result==false)
	  {
		  pattern = Pattern.compile("\\-*0+\\.[0-9]+");
		  return pattern.matcher(str).matches();
	  }
	  return result;
	  
	 }
	 
	 public static boolean isNumber(String str) {
		 boolean result = false;
	  if(str==null || "".equals(str))
	   return result;  
	  if(str.startsWith("0") && !str.equals("0"))
		  return result;  
	  Pattern pattern = Pattern.compile("\\-*[1-9]*[0-9]*(\\,\\d+)*");
	  result = pattern.matcher(str).matches();
	  return result;
	  
	 }
	 
	 public static boolean isPercent(String str) {
		 boolean result = false;
		 if(str.equals("0%"))
			 return true;
		  if(str==null || "".equals(str))
		   return result;  
		  if(str.startsWith("0") && !str.startsWith("0."))
			  return result;  
		  Pattern pattern = Pattern.compile("\\-*[1-9]*[0-9]*(\\,\\d+)*(\\.?)[0-9]+\\%");
		  result = pattern.matcher(str).matches();
		  if(result==false)
		  {
			  pattern = Pattern.compile("\\-*0+(\\.)+[0-9]+\\%");
			  return pattern.matcher(str).matches();
		  }
		  return result;
	  
	 }
	 
	public static void main(String[] args) {
//		System.out.println(isFloat("333333333.3333"));
//		System.out.println(isNumber("02"));
		System.out.println(isPercent("22,322.92%"));
		/*System.out.println(isDecimal("222.22"));
		System.out.println(isDecimal("22,22"));
		System.out.println(isDecimal("22,22,22.22"));
		System.out.println(isDecimal("22,22,22.22.33"));
		System.out.println(isDecimal("333%"));
		System.out.println(isDecimal("33.33%"));
		
		System.out.println(isDecimal("-2222"));
		System.out.println(isDecimal("-222.22"));
		System.out.println(isDecimal("-22,22"));
		System.out.println(isDecimal("-22,22,22.22"));
		System.out.println(isDecimal("-22,22,22.22.33"));
		System.out.println(isDecimal("-333%"));
		System.out.println(isDecimal("-33.33%"));
		
		System.out.println(isDecimal("scdsd%"));
		System.out.println(isDecimal("-33ssd.33%"));*/
		
		/*System.out.println(isDouble("-2222"));
		System.out.println(isDouble("-222.22"));
		System.out.println(isDouble("-22,22"));
		System.out.println(isDouble("-22,22,22.22"));
		System.out.println(isDouble("-22,22,22.22.33"));
		System.out.println(isDouble("-333%"));
		System.out.println(isDouble("-33.33%"));
		*/
	/*	System.out.println(isPercent("-2222"));
		System.out.println(isPercent("-222.22"));
		System.out.println(isPercent("-22,22"));
		System.out.println(isPercent("-22,22,22.22%"));
		System.out.println(isPercent("-22,22,22.22.33%"));
		System.out.println(isPercent("-333%"));*/
		System.out.println(isPercent("-30,3.33%"));
		
		/*Pattern p = Pattern.compile("^(([0-9a-zA-Z]+)|([0-9a-zA-Z]+[_.0-9a-zA-Z-]*[0-9a-zA-Z]+))@([a-zA-Z0-9-]+[.])+([a-zA-Z]{2}|net|com|gov|mil|org|edu|int)$");
		Matcher m = p.matcher(".MobileD_.ev@sanook.com");
				
		//使用find()方法查找第一个匹配的对象
		boolean result = m.find();*/
		//使用循环将句子里所有的kelvin找出并替换再将内容加到sb里
//		System.out.print(result);
		/*
		 * StringBuffer sb = new StringBuffer();
		int i=0;	
		 * while(result)
		{
		//	System.out.println(m.group().substring(6));
			i++;
			m.appendReplacement(sb, "href=\""+i+"/index.html");
		//	System.out.println("第"+i+"次匹配后sb的内容是："+sb);
			
			result = m.find();
		}
		//最后调用appendTail()方法将最后一次匹配后的剩余字符串加到sb里；
		m.appendTail(sb);
		System.out.println("调用m.appendTail(sb)后sb的最终内容是:"+ sb.toString());*/

	}

}
