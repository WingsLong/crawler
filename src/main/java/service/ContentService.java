package service;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import jdbc.MyConnection;

import java.sql.SQLException;

/**
 * Created by tan on 2017/1/24.
 */
public class ContentService {
    public static int insert(String company,long linkId,String url,String website,String jobs) {
        Connection conn = MyConnection.getConn();
        int i = 0;
        String sql = "insert into content (company,link_id,url,website,jobs) values(?,?,?,?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, company);
            pstmt.setLong(2, linkId);
            pstmt.setString(3, url);
            pstmt.setString(4, website);
            pstmt.setString(5, jobs);
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

}
