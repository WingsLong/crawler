package service;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import jdbc.MyConnection;
import pojo.Link;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tan on 2017/1/24.
 */
public class LinkService {
    public static int insert(String url,String website) {
        Connection conn = MyConnection.getConn();
        int i = 0;
        String sql = "insert into link (url,website) values(?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, url);
            pstmt.setString(2, website);
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }


    public static  List<Link> getAll(String website) {
        Connection conn = MyConnection.getConn();
        String sql = "select * from link where website=?";
        PreparedStatement pstmt;
        List<Link> linkList = new ArrayList();
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            pstmt.setString(1, website);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String url = rs.getString("url");
                Link link = new Link();
                link.setId(id);
                link.setUrl(url);
                linkList.add(link);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return linkList;
    }

}
