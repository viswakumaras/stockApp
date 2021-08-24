import com.google.gson.Gson;
import exception.DBException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class ProductService {

    static Random random = new Random();

    // generate a random integer from 0 to 899, then add 100
    static int x = random.nextInt(900) + 100;

 static int i=x;


    public  static void placeOrder(int si,int quantity) {


try {

    for (Products productObj : HomePage.storage.getProductArrayList()) {

        if (productObj.getId() == si) {


            HomePage.storage.updateQuantity(si,Math.abs(productObj.getAvailable() - quantity));

            int totalPrice= productObj.getPrice()*quantity;

            HomePage.storage.writeCart(UserService.loginUserId,productObj.getId(),quantity);


        //    HomePage. storage.readCart(UserService.loginUserId);


            System.out.println(" ");


        }

    }

}
catch (Exception e){
    System.out.println("Product ID not found !");
    System.out.println(e);
    HomePage.mainMenu();
}

    }

    public  static void placeOrderService(int id,int userId) {

        try {

            for (Products productObj : HomePage.storage.getProductArrayList()) {

                if (productObj.getId() == id) {

                    HomePage.storage.updateQuantity(id,Math.abs(productObj.getAvailable() - 1));

                    HomePage.storage.writeCart(userId,productObj.getId(),1);

                    System.out.println(" ");

                }

            }

        }
        catch (Exception e){
         //   System.out.println("Product ID not found !");
            System.out.println(e);
       //     HomePage.mainMenu();
        }

    }

    public  static String readStillInCart(int user_id){
        Connection connection = null;
        PreparedStatement pst = null;
        PreparedStatement pst2=null;
        PreparedStatement pst3=null;
        PreparedStatement pst4=null;

        ResultSet rs = null;

        ResultSet rs2=null;

        ResultSet rs3=null;
        String sql= "select product_id,quantity,order_item from order_items where order_id=?";

        String sql4="select order_id from orders where user_id=? AND status=0";

        try {
            connection = ConnectionUtil.createConnection();

            pst = connection.prepareStatement(sql);

            //    pst2=connection.prepareStatement(sql3);

            pst3=connection.prepareStatement(sql4);
            pst3.setInt(1,user_id);
            rs3=pst3.executeQuery();
            //   pst2.setInt(1,user_id);

            //  rs2=pst2.executeQuery();

            JSONArray checkedOut=new JSONArray();

            while(rs3.next()) {
                pst.setInt(1,rs3.getInt("order_id"));
                rs = pst.executeQuery();
                while (rs.next()) {
                    int product_id = rs.getInt("product_id");
                    int quantity = rs.getInt("quantity");
                    JSONObject product1=new JSONObject();
                    for(Products x:HomePage.storage.getProductArrayList()){
                        if(x.getId()==product_id){
                            product1.put("productName",x.getItemName());
                        }
                    }
                    product1.put("quantity",quantity);
                    product1.put("OrderId",rs3.getInt("order_id"));
                    checkedOut.add(product1);

                }
            }


            return checkedOut.toString();


        } catch (SQLException e) {
            throw new DBException("Can't able to read");
        } finally {
            ConnectionUtil.closeConnection(rs, pst, connection);
        }
    }


    public  static String readCheckedOut(int user_id){
        Connection connection = null;
        PreparedStatement pst = null;
        PreparedStatement pst2=null;
        PreparedStatement pst3=null;
        PreparedStatement pst4=null;

        ResultSet rs = null;

        ResultSet rs2=null;

        ResultSet rs3=null;
        String sql= "select product_id,quantity,order_item from order_items where order_id=?";

          String sql4="select order_id from orders where user_id=? AND status=1";

        try {
            connection = ConnectionUtil.createConnection();

            pst = connection.prepareStatement(sql);

        //    pst2=connection.prepareStatement(sql3);

             pst3=connection.prepareStatement(sql4);
             pst3.setInt(1,user_id);
            rs3=pst3.executeQuery();
         //   pst2.setInt(1,user_id);

          //  rs2=pst2.executeQuery();

            JSONArray checkedOut=new JSONArray();

            while(rs3.next()) {
                pst.setInt(1,rs3.getInt("order_id"));
                rs = pst.executeQuery();
                while (rs.next()) {
                    int product_id = rs.getInt("product_id");
                    int quantity = rs.getInt("quantity");
                    JSONObject product1=new JSONObject();
                    for(Products x:HomePage.storage.getProductArrayList()){
                        if(x.getId()==product_id){
                            product1.put("productName",x.getItemName());
                        }
                    }
                    product1.put("quantity",quantity);
                    product1.put("OrderId",rs3.getInt("order_id"));
                    checkedOut.add(product1);

                }
            }


return checkedOut.toString();


        } catch (SQLException e) {
            throw new DBException("Can't able to read");
        } finally {
            ConnectionUtil.closeConnection(rs, pst, connection);
        }
    }


    public static Products getProductObj(int si){
        Products obj=new Products();
        for(Products x:HomePage.storage.getProductArrayList()){
            if(si ==x.getId()){
              obj=x;
              break;
            }
        }
        return  obj;
    }


public  static void displayProduct(){
        for(Products x:HomePage.storage.getProductArrayList()){
            System.out.println(x.displayProducts());
        }
 }
    public static String getProducts(){

        if (HomePage.storage.getProductArrayList() == null)
            return null;
        Gson gson = new Gson();
        String json = null;
        try {
            json = gson.toJson(HomePage.storage.getProductArrayList());
        }
        catch (Exception e) {}
        return json;
    }

    public  static boolean  addProducts(String name,String category,int price,int available,int id){
        try {
            HomePage.storage.addProduct(name, category, available, price, id);

            return true;
        }
        catch(Exception e){
            return false;
        }
    }

}

