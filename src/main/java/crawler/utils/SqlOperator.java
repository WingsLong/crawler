package crawler.utils;

public class SqlOperator {

	public static String getSecondSql(String StopTime,String StartTime)
	{
		return " hour(timediff("+StopTime+","+StartTime+"))*60*60+minute(timediff("+StopTime+","+StartTime+"))*60+second(timediff("+StopTime+","+StartTime+")) " ;
	}
	
	/**
	 * @param sql_prefix, like " and platform in("
	 * @param list
	 * @return
	 */
	public static String getInSql(String sql_prefix,Object[] list)
	{
		StringBuffer sql = new StringBuffer("");
		if(list!=null && list.length>0)
		{
			sql = new StringBuffer(sql_prefix);
			for(Object platform:list)
			{
				sql.append("'").append(platform).append("',");
			}
			sql.deleteCharAt(sql.length()-1);
			sql.append(") ");
		}
		return sql.toString();
	}
}
