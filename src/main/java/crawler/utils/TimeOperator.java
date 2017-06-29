package crawler.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Class used for time operation such as getting current time in some format,convert one time format into another
 * 
 * @author lijun
 * @version 1.0
 */
public class TimeOperator {
	
	/**
	 * 功能：返回一个月所有日期
	 * 形参：年月,如200904
	 * 返回值：一个月所有日期字符串的ArrayList，如20090603
	 */
	public static ArrayList<String> dateMonthToList(String day)
	{
		ArrayList<String> listDate = new ArrayList<String>();
		int year = Integer.parseInt(day.substring(0,4)) ;
		int month = Integer.parseInt(day.substring(4,6))-1;
		int date = 01 ;
		
		Calendar cal = Calendar.getInstance() ;
		cal.set(year,month,date) ;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd") ;
		
		while(month==cal.get(Calendar.MONTH))
		{
			sdf.setCalendar(cal) ;
			listDate.add(sdf.format(cal.getTime())) ;
			cal.add(Calendar.DATE, 1);
		}
		return listDate;
	}
	
	/**
	 * get time in long type and return in a desired time format
	 * 
	 * @param timeInLong
	 * @param timeFormat
	 * @return
	 */
	public static String getInputTimeInLong(long timeInLong, String timeFormat) {
		String dateString = "0000";
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(timeFormat);
			Date d = new Date(timeInLong);
			dateString = formatter.format(d);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dateString;
	}

	/**
	 * get current system time and return in a desired time format
	 * @param format
	 * @param localeFR
	 * @return
	 */
	public static String getCurrentTime(String format,Locale localeFR) {
		String dateString = "0000";
		try {
//			Locale localeFR = Locale.TAIWAN;
			SimpleDateFormat formatter = new SimpleDateFormat(format,localeFR);
			Date d = new Date();
			dateString = formatter.format(d);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dateString;
	}
	
	/**
	 * get current system time and return in a desired time format
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurrentTime(String format) {
		String dateString = "0000";
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			Date d = new Date();
			dateString = formatter.format(d);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dateString;
	}

	public static String timeFormatConvert(String toFormat, String time) {
		String datestr = "";
		
		if(time!=null && time.trim().length()>=8 && time.indexOf("-")!=-1)
		{
			try {
				String fromFormat = "yyyy";
				String[] arr = time.split("-");
				if(arr.length==3)
				{
					if(arr[1].length()==1)
						fromFormat += "-M";
					else if(arr[1].length()==2)
							fromFormat += "-MM";
					if(arr[2].length()==1)
						fromFormat += "-d";
					else if(arr[2].length()==2)
						fromFormat += "-dd";
					if(fromFormat.length()==time.length())
					{
						SimpleDateFormat sdf = new SimpleDateFormat(fromFormat);
						Date date = sdf.parse(time);
						sdf = new SimpleDateFormat(toFormat);
						datestr = sdf.format(date);
					}
					else
						System.out.println("fromFormat="+fromFormat+" time="+time);
				}
				else
					System.out.println("time is wrong:"+time);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		else
			System.out.println("format="+toFormat+" time="+time);
		return datestr;
	}
	
	/**
	 * transform one time format to another,such as "2006 Jan 19 20:03:18" to "20060119200318"
	 * 
	 * @param fromFormat1
	 * @param toFormat2
	 * @param time
	 * @return
	 */
	public static String timeFormatConvert(String fromFormat1, String toFormat2, String time) {
		String datestr = "";
		
		if(time!=null && time.trim().length()==fromFormat1.length())
		{
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(fromFormat1/* "yyyy MMM dd HH:mm:ss" */, Locale.US);
				Date date = sdf.parse(time);
				sdf = new SimpleDateFormat(toFormat2/* "yyyyMMddHHmmss" */);
				datestr = sdf.format(date);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		else
			System.out.println("fromFormat1="+fromFormat1+" time="+time);
		return datestr;
	}

	public static Date timeString2Date(String fromFormat, String time) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(fromFormat, Locale.US);
			return sdf.parse(time);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new Date();
	}
	
	public static String timeFormatConvert(String toFormat, Date date) {
		if(date == null)
			return "";
		SimpleDateFormat formatter = null;
		try {
			formatter = new SimpleDateFormat(toFormat);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return formatter.format(date);
	}
	/**
	 * @return this year
	 */
	public static String getYear() {
		return getCurrentTime("yyyyMMddHHmmss").substring(0, 4);
	}

	/**
	 * convert "xxxxxx" seconds into "xx天xx时xx分xx秒"
	 * 
	 * @param input
	 * @return
	 */
	public static String TimeConvert(String input) {
		int day = 0;
		int hour = 0;
		int minute = 0;
		int second = 0;

		try {
			int time = Integer.parseInt(input);
			// ****** from second to minute *******
			if (time >= 60) {
				minute = time / 60;
				second = time % 60;
			} else
				return input + "秒";
			// ****** from minute to hour *******
			if (minute >= 60) {
				hour = minute / 60;
				minute = minute % 60;
			} else
				return minute + "分" + second + "秒";

			if (hour >= 24) {
				day = hour / 24;
				hour = hour % 24;
				return day + "天" + hour + "时" + minute + "分" + second + "秒";
			} else
				return hour + "时" + minute + "分" + second + "秒";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "0 秒";
	}

	public static int getRealMonth(Calendar c)
	{
		return c.get(Calendar.MONTH)+1;
	}
	
	public static int getDayOfWeek(Calendar c)
	{
//		Calendar c=Calendar.getInstance();
		return c.get(Calendar.DAY_OF_WEEK);
	}
	
	public static int getDayOfMonth(Calendar c)
	{
//		Calendar c=Calendar.getInstance();
		return c.get(Calendar.DAY_OF_MONTH);
	}
	
	public static int getHourOfDay(Calendar c)
	{
		return c.get(Calendar.HOUR_OF_DAY);
	}
	public static int getMinOfHour(Calendar c)
	{
		return c.get(Calendar.MINUTE);
	}
	
	/**
	 * Add all date in the datespan into the list(including the fromdate and enddate)
	 * @param datePattern:such as "yyMMdd"
	 * @param dateSpan:such as "070329-070430"
	 * @return
	 */
	public static ArrayList<String> datespanToList(String datePattern,String dateSpan)
	{
		ArrayList<String> listDate = new ArrayList<String>();
		String fromDate = "";
		String toDate = "";
		int index = dateSpan.indexOf("-");
		if(index!=-1)
		{
			fromDate = dateSpan.substring(0,index);
//			System.out.println("fromDate="+fromDate);
			toDate = dateSpan.substring(index+1);
//			System.out.println("toDate="+toDate);
			if(fromDate.compareTo(toDate)>0)
				return listDate;
			Calendar c = Calendar.getInstance();
			Date d;
			try {
				SimpleDateFormat sf = new SimpleDateFormat(datePattern);
				d = sf.parse(fromDate);
				c.setTime(d);
//				System.out.println(TimeOperator.timeFormatConvert("yyMMdd",c.getTime()));
				String tempDate = "";
				for(int i=1;i<5000;i++)//should not be more than 5000 days!
				{
					tempDate = TimeOperator.timeFormatConvert(datePattern,c.getTime());
					listDate.add(tempDate);
					if(toDate.equals(tempDate))
						break;
					c.add(Calendar.DAY_OF_MONTH, 1);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		else
			listDate.add(dateSpan);
		return listDate;
	}
	
	public static ArrayList<String> minutespanToList(String datePattern,String dateSpan,int minutespan)
	{
		ArrayList<String> listDate = new ArrayList<String>();
		String fromDate = "";
		String toDate = "";
		int index = dateSpan.indexOf("-");
		if(index!=-1 && minutespan>0)
		{
			fromDate = dateSpan.substring(0,index);
//			System.out.println("fromDate="+fromDate);
			toDate = dateSpan.substring(index+1);
//			System.out.println("toDate="+toDate);
			if(fromDate.compareTo(toDate)>0)
				return listDate;
			Calendar c = Calendar.getInstance();
			Date d;
			try {
				SimpleDateFormat sf = new SimpleDateFormat(datePattern);
				d = sf.parse(fromDate);
				c.setTime(d);
//				System.out.println(TimeOperator.timeFormatConvert("yyMMdd",c.getTime()));
				String tempDate = "";
				for(int i=1;i<5000;i++)//should not be more than 5000 items!
				{
					tempDate = TimeOperator.timeFormatConvert(datePattern,c.getTime());
					listDate.add(tempDate);
					if(toDate.compareTo(tempDate)<1)
						break;
					c.add(Calendar.MINUTE, minutespan);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		else
			listDate.add(dateSpan);
		return listDate;
	}
	
	public static ArrayList<String> datespanToList(String inputDatePattern,String dateSpan,String outputDatePattern)
	{
		ArrayList<String> listDate = new ArrayList<String>();
		String fromDate = "";
		String toDate = "";
		int index = dateSpan.indexOf("-");
		if(index!=-1)
		{
			fromDate = dateSpan.substring(0,index);
//			System.out.println("fromDate="+fromDate);
			toDate = dateSpan.substring(index+1);
//			System.out.println("toDate="+toDate);
			if(fromDate.compareTo(toDate)>0)
				return listDate;
			Calendar c = Calendar.getInstance();
			Date d;
			try {
				SimpleDateFormat sf = new SimpleDateFormat(inputDatePattern);
				d = sf.parse(fromDate);
				c.setTime(d);
//				System.out.println(TimeOperator.timeFormatConvert("yyMMdd",c.getTime()));
				String tempDate = "";
				for(int i=1;i<5000;i++)//should not be more than 5000 days!
				{
					tempDate = TimeOperator.timeFormatConvert(inputDatePattern,c.getTime());
					listDate.add(TimeOperator.timeFormatConvert(outputDatePattern,c.getTime()));
					if(toDate.equals(tempDate))
						break;
					c.add(Calendar.DAY_OF_MONTH, 1);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		else
			listDate.add(dateSpan);
		return listDate;
	}
	
	public static ArrayList<String> datespanToHourList(String inputDatePattern,String dateSpan,String outputDatePattern)
	{
		ArrayList<String> listDate = new ArrayList<String>();
		String fromDate = "";
		String toDate = "";
		int index = dateSpan.indexOf("-");
		if(index!=-1)
		{
			fromDate = dateSpan.substring(0,index);
//			System.out.println("fromDate="+fromDate);
			toDate = dateSpan.substring(index+1);
//			System.out.println("toDate="+toDate);
			if(fromDate.compareTo(toDate)>0)
				return listDate;
			Calendar c = Calendar.getInstance();
			Date d;
			try {
				SimpleDateFormat sf = new SimpleDateFormat(inputDatePattern);
				d = sf.parse(fromDate);
				c.setTime(d);
//				System.out.println(TimeOperator.timeFormatConvert("yyMMdd",c.getTime()));
				String tempDate = "";
				for(int i=1;i<5000;i++)//should not be more than 5000 days!
				{
					tempDate = TimeOperator.timeFormatConvert(inputDatePattern,c.getTime());
					listDate.addAll(oneday24HourList(TimeOperator.timeFormatConvert(outputDatePattern,c.getTime())));
					if(toDate.equals(tempDate))
						break;
					c.add(Calendar.DAY_OF_MONTH, 1);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		else
			listDate.addAll(oneday24HourList(dateSpan));
		return listDate;
	}
	
	public static ArrayList<String> oneday24HourList(String date)
	{
		ArrayList<String> listHour = new ArrayList<String>();
		String hour = "";
		for(int i=0;i<24;i++)
		{
			if(i<10)
				hour = "0"+i;
			else
				hour = ""+i;
			listHour.add(date+hour);
		}
		return listHour;
	}
	
	public static ArrayList<String> datespanTo15MinList(String inputDatePattern,String dateSpan,String outputDatePattern)
	{
		ArrayList<String> listDate = new ArrayList<String>();
		String fromDate = "";
		String toDate = "";
		int index = dateSpan.indexOf("-");
		if(index!=-1)
		{
			fromDate = dateSpan.substring(0,index);
//			System.out.println("fromDate="+fromDate);
			toDate = dateSpan.substring(index+1);
//			System.out.println("toDate="+toDate);
			if(fromDate.compareTo(toDate)>0)
				return listDate;
			Calendar c = Calendar.getInstance();
			Date d;
			try {
				SimpleDateFormat sf = new SimpleDateFormat(inputDatePattern);
				d = sf.parse(fromDate);
				c.setTime(d);
//				System.out.println(TimeOperator.timeFormatConvert("yyMMdd",c.getTime()));
				String tempDate = "";
				for(int i=1;i<5000;i++)//should not be more than 5000 days!
				{
					tempDate = TimeOperator.timeFormatConvert(inputDatePattern,c.getTime());
					listDate.addAll(oneday15MinuteList(TimeOperator.timeFormatConvert(outputDatePattern,c.getTime())));
					if(toDate.equals(tempDate))
						break;
					c.add(Calendar.DAY_OF_MONTH, 1);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		else
			listDate.addAll(oneday15MinuteList(dateSpan));
		return listDate;
	}
	
	public static ArrayList<String> oneday15MinuteList(String date)
	{
		ArrayList<String> list15Minute = new ArrayList<String>();
		String hour = "";
		for(int i=0;i<24;i++)
		{
			if(i<10)
				hour = "0"+i;
			else
				hour = ""+i;
			list15Minute.add(date+hour+"00");
			list15Minute.add(date+hour+"15");
			list15Minute.add(date+hour+"30");
			list15Minute.add(date+hour+"45");
		}
		return list15Minute;
	}
	public static String dateOperatorOnToday(String datePattern,int precision,int operand)
	{
		Calendar c = Calendar.getInstance();
		c.add(precision,operand);
		return TimeOperator.timeFormatConvert(datePattern,c.getTime());
	}
	
	public static String dateBeforeToday(String datePattern,int days)
	{
		Calendar c = Calendar.getInstance();
		return TimeOperator.dateBeforeGivenDate(datePattern,days,c);
	}
	
	public static String dateBeforeGivenDate(String datePattern,int days,Calendar c)
	{
//		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH,days);
		return TimeOperator.timeFormatConvert(datePattern,c.getTime());
	}
	
	public static String hoursBeforeGivenHour(String hourPattern,int hours,String hour) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat(hourPattern); 
		Calendar calendar = Calendar.getInstance(); 
		Date date1 = sdf.parse(hour);
		calendar.setTime(date1);
		return TimeOperator.hourBeforeGivenHour(hourPattern, hours, calendar);
	}
	public static String hourBeforeGivenHour(String hourPattern,int hours,Calendar c)
	{
//		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY,hours);
		return TimeOperator.timeFormatConvert(hourPattern,c.getTime());
	}
	
	public static String minutesBeforeGivenHour(String timePattern,int minutes,String time) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat(timePattern); 
		Calendar calendar = Calendar.getInstance(); 
		Date date1 = sdf.parse(time);
		calendar.setTime(date1);
		return TimeOperator.minutesBeforeGivenHour(timePattern, minutes, calendar);
	}
	public static String minutesBeforeGivenHour(String timePattern,int minutes,Calendar c)
	{
		c.add(Calendar.MINUTE,minutes);
		return TimeOperator.timeFormatConvert(timePattern,c.getTime());
	}
	
	public static String dateBeforeGivenDate(String datePattern,int days,String date) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 
		Calendar calendar = Calendar.getInstance(); 
		Date date1 = sdf.parse(date);
		calendar.setTime(date1);
		return TimeOperator.dateBeforeGivenDate("yyyyMMdd", days, calendar);
	}
	
	public static String datetimeBeforeNow(String datePattern,int hours)
	{
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY,hours);
		return TimeOperator.timeFormatConvert(datePattern,c.getTime());
	}
	
	public static String datetimeBeforeNow(String datePattern,int calendarType,int amount)
	{
		Calendar c = Calendar.getInstance();
		c.add(calendarType,amount);
		return TimeOperator.timeFormatConvert(datePattern,c.getTime());
	}
	
	public static long toSeconds(String totalTime)
	{
		final int SEC = 4;//仅获取 天，时，分,秒 四段
		String[] arrTime = totalTime.split("::");
		if(arrTime.length<SEC)
			return -1;
		int[] intTime = new int[arrTime.length];
		for(int i=0;i<SEC;i++)
		{
			intTime[i]=StringOperator.toInt(arrTime[i]);
			if(intTime[i]==-1)//验证
				return -1;
		}
		long totalSeconds = 0;
		totalSeconds += intTime[0]*24*60*60;
		totalSeconds += intTime[1]*60*60;
		totalSeconds += intTime[2]*60;
		totalSeconds += intTime[3];
		return totalSeconds;
	}
	
	/**
	 * @param strDate 
	 * @return how many days in this month
	 * @throws ParseException 
	 */
	public static int daysOfMonth(String year,String month) throws ParseException
	{
		String strDate = year+month+"01"; 
		return daysOfMonth(strDate); 
	}
	
	/**
	 * @param strDate in format yyyyMMdd
	 * @return how many days in this month
	 * @throws ParseException
	 */
	public static int daysOfMonth(String strDate) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 
		Calendar calendar = Calendar.getInstance(); 
		Date date1 = sdf.parse(strDate);
		calendar.setTime(date1); //放入你的日期 
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  
	}
	
  /**计算两个日期相差多少天
 * @param dStart
 * @param dEnd
 * @return
 */
public static int datediff(Date dStart, Date dEnd) 
  {
      if (dStart == null || dEnd == null) {
         return -8888;
      }
      Calendar calendar = Calendar.getInstance(Locale.CHINA);
      calendar.setTime(dStart);
      long lStart = calendar.getTimeInMillis();
      calendar.setTime(dEnd);
      long lEnd = calendar.getTimeInMillis();
      return Integer.parseInt(Long.toString( (lEnd - lStart) / (24*3600* 1000)))+1;
   }
	  
/**计算两个日期相差多少秒
 * @param dStart
 * @param dEnd
 * @return
 */
public static long secondsdiff(Date dStart, Date dEnd) 
  {
      if (dStart == null || dEnd == null) {
         return -8888;
      }
      Calendar calendar = Calendar.getInstance(Locale.CHINA);
      calendar.setTime(dStart);
      long lStart = calendar.getTimeInMillis();
      calendar.setTime(dEnd);
      long lEnd = calendar.getTimeInMillis();
      return (lEnd - lStart)/1000;
   }

	/**
	 * @param datetime: given date
	 * @param startDate
	 * @param endDate
	 * @return: the week of the given date(the week contains 7 days,but the begin of the week is 7*n+startDate,not the normal calendar week scope.)
	 */
	public static String weekScope(String datetime,String startDate,String endDate) {
	
		String week_begin = "";
		String week_end  = "";
		String end_Date = "";
		try {
			week_begin = startDate;
			week_end = TimeOperator.dateBeforeGivenDate("yyyyMMdd", 6, week_begin);
//			System.out.println("week_begin="+week_begin+" week_end="+week_end);
			end_Date = endDate;
			
			if(datetime.compareTo(week_end)<=0)
				return week_begin+"~"+week_end;
			while(week_end.compareTo(end_Date)<0)
			{
				week_begin = TimeOperator.dateBeforeGivenDate("yyyyMMdd", 7, week_begin);
				week_end = TimeOperator.dateBeforeGivenDate("yyyyMMdd", 7, week_end);
//				System.out.println("week_begin="+week_begin+" week_end="+week_end);
				if(datetime.compareTo(week_end)<=0)
					break;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(week_end.compareTo(end_Date)>0)
			week_end = end_Date;
		return week_begin+"~"+week_end;
	}
	public static void main(String[] args) throws ParseException {
		
//		System.out.println(TimeOperator.getRealMonth(Calendar.getInstance()));
		System.out.println(TimeOperator.timeFormatConvert("yyyyMMddHHmm","yyyy-MM-dd HH:mm","201012282113"));
//		System.out.println( TimeOperator.timeString2Date("yyyyMMdd","20090807").toLocaleString());
		// Log.ShowByLine(TimeOperator.getInputTimeInLong(900000, "yyyy-MM-dd hh:mm"));
//		TimeOperator.getCurrentTime("yyyy-MM-dd");
		/*ArrayList<String> listDate = TimeOperator.datespanToList("yyyyMMdd","20070329-20070330","yyyy-MM-dd");
		for(String date:listDate)
		{
			System.out.println(date);
		}*/
//		);
		/*Calendar c = Calendar.getInstance();
		int week = TimeOperator.getDayOfWeek(c);
		String datetime = "";
		if(week<5)//周四以前
		{
			datetime = TimeOperator.dateBeforeToday("yyyyMMdd", -(week-1+3+7));
		}
		else
			datetime = TimeOperator.dateBeforeToday("yyyyMMdd", -(week-1+3));
		
		Date d;
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		d = sf.parse(datetime);*/
//		System.out.println(timeFormatConvert("yyyyMMddHHmmss","yyyy-MM-dd HH:mm:ss","20100428143000"));
		System.out.println(TimeOperator.minutesBeforeGivenHour("yyyyMMddHHmmss",15,"20101014115920"));
		/*Calendar c = Calendar.getInstance();
		int week = TimeOperator.getDayOfWeek(c);
		System.out.println(week);*/
//		SimpleDateFormat bartDateFormat = 
//			new SimpleDateFormat("yyyy年MM月d日"); 
//
//			Date date = new Date(); 
//
//			System.out.println(bartDateFormat.format(date)); 


//		System.out.println(TimeOperator.datetimeBeforeNow("HH", -1));
//		System.out.println(TimeOperator.getCurrentTime("HH"));
//		System.out.println(TimeOperator.datetimeBeforeNow("yyMMddHHmm",Calendar.MINUTE, -30));
//		System.out.println(TimeOperator.datetimeBeforeNow("yyMMddHHmm",Calendar.HOUR, -3));
//		System.out.println(TimeOperator.getHourOfDay(Calendar.getInstance()));
//		System.out.println(TimeOperator.dateBeforeToday("yyyyMMdd", -4));
		
		/*Date d = null;
//		String startDate = "20081001";
		String startDate = "20081031";
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		try {
			d = sf.parse(startDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		System.out.println(TimeOperator.dateBeforeGivenDate("yyyyMMdd", 3, c));*/
		
//		System.out.println(Integer.parseInt("02")-1); 
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 
		Calendar calendar = Calendar.getInstance(); 
		Date date1=null;
		try {
			date1 = sdf.parse("20081001");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calendar.setTime(date1); //放入你的日期 
		System.out.println(TimeOperator.dateBeforeGivenDate("yyyyMMdd", 3, calendar));*/
//		System.out.println(TimeOperator.dateBeforeGivenDate("yyyyMMdd", 3, "20090225"));
//		System.out.println(TimeOperator.dateBeforeGivenDate("yyyyMMdd", -3, "20090228"));
		/*String date="20090225";
		Logger.infoLine("","URL date="+date);
		String add1_date = TimeOperator.dateBeforeGivenDate("yyyyMMdd", 1, date);
		Logger.infoLine("","After one day="+add1_date);*/
		/*ArrayList<String> dates = TimeOperator.datespanToList("yyyyMMdd", "20090226-20090225");
		for(String date:dates){
			System.out.println(date);
		}*/
	}

}
