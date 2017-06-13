package util;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
/**
 *
 *
 *@autor Johnzz
 *@date 2017-06-13 14:08
 *
 **/
public class UrlUtils {
    /**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     * @param URL  url地址
     * @return  url请求参数部分
     */
    public static Map<String, String> getUrlParameters(String URL) {
        Map<String, String> mapRequest = new HashMap<String, String>();

        String[] arrSplit=null;

        String strUrlParam= truncateUrlPage(URL);
        if(StringUtils.isEmpty(strUrlParam)) {
            return mapRequest;
        }

        arrSplit=strUrlParam.split("[&]");//每个键值为一组
        for(String strSplit : arrSplit) {
            String[] arrSplitEqual=null;
            arrSplitEqual= strSplit.split("[=]");

            //解析出键值
            if(arrSplitEqual.length>1) {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
            } else {
                if(StringUtils.isNotEmpty(arrSplitEqual[0])) {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }


    public static String getUrlParameter(String url, String keyword) {
        String[] arrSplit=null;

        String strUrlParam= truncateUrlPage(url);
        if(StringUtils.isEmpty(strUrlParam)) {
            return null;
        }

        arrSplit=strUrlParam.split("[&]");//每个键值为一组
        for(String strSplit : arrSplit) {
            String[] arrSplitEqual=null;
            arrSplitEqual= strSplit.split("[=]");
            if(arrSplitEqual[0].equals(keyword)) {
                return arrSplitEqual.length>1?arrSplitEqual[1]:"";
            }
        }
        return null;
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     *
     * @param strURL url地址
     * @return url请求参数部分
     */
    private static String truncateUrlPage(String strURL) {
        String strAllParam=null;
        String[] arrSplit=null;

        strURL=strURL.trim();

        arrSplit=strURL.split("[?]");
        if(strURL.length()>1) {
            if(arrSplit.length>1) {
                if(arrSplit[1]!=null) {
                    strAllParam = arrSplit[1];
                }
            }
        }
        return strAllParam;
    }


}
