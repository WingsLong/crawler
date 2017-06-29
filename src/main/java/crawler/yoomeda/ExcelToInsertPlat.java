package crawler.yoomeda;

import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.Connection;
import crawler.utils.ExcelImport;
import jdbc.MyConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 *
 *@autor Administrator
 *@date 2017-06-29 22:27
 *
 **/
public class ExcelToInsertPlat {

    private static Logger log = LoggerFactory.getLogger(ExcelToInsertPlat.class);

    public static void main(String[] args) throws Exception {
        Connection conn = MyConnection.getConn();
        try {
            PreparedStatement preStat = conn.prepareStatement("INSERT INTO `oa_plat` (`type`, `owner_id`, `title`, `uid`, `prices`, `position`, `tags`, `fans`, `data`, `icon_url`,  `locked`, `check_status`) " +
                    "VALUES ( ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?, ?, ?);");

            HashMap<String, ArrayList<ArrayList<String>>> excelSheets = ExcelImport.read("E:\\WorkFiles\\普宁资源.xls");
            for(ArrayList<ArrayList<String>> excelSheet : excelSheets.values()) {
                //======================  sheet里面的每一行数据【第二行開始寫入】 ======================
                for(int i=2; i<excelSheet.size(); i++) {
                    ArrayList<String> list = excelSheet.get(i);
                    ResultSet resultSet = conn.createStatement().executeQuery("select id from oa_plat where type=4 and uid='" + list.get(1) + "'");
                    if(resultSet.next()) {
                        log.info("公众号已经存在，无需重复插入：{}", list.get(1));
                        continue;
                    }
                    preStat.setInt(1, 4);   //微信类型type
                    preStat.setInt(2, 20);  //所属用户组
                    preStat.setString(3, list.get(0));  //微信名称
                    preStat.setString(4, list.get(1));  //微信号，微信唯一标识

                    //============== 微信参考价格 ============
                    Map<String, BigDecimal> pricesMap = new LinkedHashMap<>();
                    pricesMap.put("price1", new BigDecimal(list.get(4)));
                    pricesMap.put("price2", new BigDecimal(list.get(5)));
                    preStat.setString(5, JSONObject.toJSONString(pricesMap));

                    Map<String, String> positionMap = new LinkedHashMap<>();
                    positionMap.put("province", list.get(6));
                    positionMap.put("city", list.get(7));
                    positionMap.put("county", "null");
                    preStat.setString(6, JSONObject.toJSONString(positionMap));
                    preStat.setString(7, "[103,93,108]");
                    preStat.setInt(8, new BigDecimal(list.get(2)).intValue());
                    preStat.setString(9, "");
                    preStat.setString(10, "");
                    preStat.setInt(11, 0);
                    preStat.setInt(12, 0);
                    preStat.addBatch();
                }
            }
            log.info("插入结果列表为：{}", JSONObject.toJSONString(preStat.executeBatch()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
