package comAI;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangsan
 * @create 2021-11-29 18:39
 */
public class houtai extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//            response.setContentType("application/json;charset=uft-8");
        response.setContentType("application/json;charset=utf-8");
//        System.out.println("111111111111");
        List<peovinc> list = new ArrayList<>();

            //URL样式；/sf/id=3  →返回省份下拉列表；
        Connection col = null;
        PreparedStatement ps = null;  //变为预编译的Statemnet对象
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            col = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","123456");
            String sql = "select id,name from province;  ";
            ps = col.prepareStatement(sql);
//            ps.setString(1, request.getParameter("number"));
            rs = ps.executeQuery();
            peovinc pro;
            String jsonFromJava="{}";//定义在这里是为了保证能够传送回去一个json格式的对象；
            PrintWriter pw =  response.getWriter();
            while(rs.next()){
                int id = Integer.valueOf(rs.getString("id"));
                String name = rs.getString("name");
//                String jiancheng = rs.getString("jiancheng");
//                String shenghui = rs.getString("shenghui");

                pro  = new peovinc(id,name);
                list.add(pro);

            }
            //转换为json对象；
            ObjectMapper om = new ObjectMapper();
            jsonFromJava = om.writeValueAsString(list);
            //{"id":1,"name":"河北"}{"id":2,"name":"山西"}{"id":3,"name":"内蒙古"}{"id":4,"name":"辽宁"}{"id":5,"name":"江苏"}{"id":6,"name":"浙江"}{"id":7,"name":"安徽"}{"id":8,"name":"福建"}{"id":9,"name":"江西"}
//                System.out.println("转换后的结果是" + jsonFromJava);
            pw.print(jsonFromJava);//传输回去字符串；
            System.out.println(jsonFromJava);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch(SQLException e ){
            e.printStackTrace();
        }finally {
            if (rs!=null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(ps!=null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (col !=null) {
                try {
                    col.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }





    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
