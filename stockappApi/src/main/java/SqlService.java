import exception.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class SqlService implements Storage{

    public  void addNewUser(String username,String password) {
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "insert into user_details(user_name,user_role,user_pass,user_status) values ( ?,?,?,?)";
        try {
            connection = ConnectionUtil.createConnection();
            pst = connection.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, "customer");
            pst.setString(3, password);
            pst.setBoolean(4,true);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Can't able to register or user already exists");
        } finally {

            ConnectionUtil.closeConnection(rs, pst, connection);
        }
    }


    public  ArrayList<Products> getProductArrayList(){
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Products> productList = new ArrayList<>();
        String sql = "SELECT product_id,item_name,category,price,available FROM products ORDER BY product_id ASC";
        try {
            // Step 1: Get the connection
            connection = ConnectionUtil.createConnection();
            pst = connection.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                int product_id=rs.getInt("product_id");
                String item_name=rs.getString("item_name");
                String category=rs.getString("category");
                int available=rs.getInt("available");
                int price=rs.getInt("price");

                Products products=new Products();
                products.setItemName(item_name);
                products.setAvailable(available);
                products.setPrice(price);
                products.setId(product_id);
                products.setCategory(category);

                productList.add(products);

            }

        } catch (SQLException e) {

            throw new DBException("Unable to fetch products");
        } finally {

            ConnectionUtil.closeConnection(rs, pst, connection);
        }
        return productList;
    }

    public  ArrayList<Users> getUserArrayList(){
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Users> usersList = new ArrayList<>();
        String sql = "select user_name,user_pass,user_role,user_id,user_status from user_details order by user_id ASC";
        try {
            // Step 1: Get the connection
            connection = ConnectionUtil.createConnection();
            pst = connection.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                int user_id=rs.getInt("user_id");
                String user_name=rs.getString("user_name");
                String user_pass=rs.getString("user_pass");
                String user_role=rs.getString("user_role");
                boolean status=rs.getBoolean("user_status");

               Users users=new Users();
               users.setId(user_id);
               users.setPassword(user_pass);
               users.setName(user_name);
               users.setStatus(status);
               users.setRole(user_role);
               usersList.add(users);
            }

        } catch (SQLException e) {

            throw new DBException("Unable to fetch products");
        } finally {

            ConnectionUtil.closeConnection(rs, pst, connection);
        }
        return usersList;
    }

     public  void addProduct(String item_name,String category,int price,int product_id,int available){

         Connection connection = null;
         PreparedStatement pst = null;
         ResultSet rs = null;
         String sql = "insert into products(product_id,item_name,category,price,available) values(?,?,?,?,?)";
         try {
             connection = ConnectionUtil.createConnection();
             pst = connection.prepareStatement(sql);
             pst.setInt(1, product_id);
             pst.setString(2,item_name );
             pst.setString(3,category);
             pst.setInt(4,price);
             pst.setInt(5,available);
             pst.executeUpdate();
         } catch (SQLException e) {
             throw new DBException("Can't able to add product");
         } finally {

             ConnectionUtil.closeConnection(rs, pst, connection);
         }
     }

     public  void changePassword(String name,String newPass){
         Connection connection = null;
         PreparedStatement pst = null;
         ResultSet rs = null;
         String sql = "update user_details set user_pass=? where user_name=?";
         try {
             connection = ConnectionUtil.createConnection();
             pst = connection.prepareStatement(sql);
             pst.setString(1,newPass);
             pst.setString(2,name);
             pst.executeUpdate();
         } catch (SQLException e) {
             throw new DBException("Can't able to change password");
         } finally {

             ConnectionUtil.closeConnection(rs, pst, connection);
         }
     }

     public   void updateQuantity(int product_id,int quantity){
         Connection connection = null;
         PreparedStatement pst = null;
         ResultSet rs = null;
         String sql = "update products set available=? where product_id=?";
         try {
             connection = ConnectionUtil.createConnection();
             pst = connection.prepareStatement(sql);
             pst.setInt(1,quantity);
             pst.setInt(2,product_id);
             pst.executeUpdate();
         } catch (SQLException e) {
             throw new DBException("Can't able to change password");
         } finally {

             ConnectionUtil.closeConnection(rs, pst, connection);
         }
     }

    public   void updateName(int product_id,String newName){
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "update products set item_name=? where product_id=?";
        try {
            connection = ConnectionUtil.createConnection();
            pst = connection.prepareStatement(sql);
            pst.executeUpdate();
            pst.setString(1,newName);
            pst.setInt(2,product_id);

        } catch (SQLException e) {
            throw new DBException("Can't able to change password");
        } finally {

            ConnectionUtil.closeConnection(rs, pst, connection);
        }
    }


     public static int getPreviousOrderId(int user_id){
         Connection connection = null;
         PreparedStatement pst = null;
         ResultSet rs = null;
         int newOrder_id = 0;
         String sql= "select max(order_id) from orders where user_id=?";

         try {
             connection = ConnectionUtil.createConnection();
             pst = connection.prepareStatement(sql);
             pst.setInt(1,user_id);
             rs=pst.executeQuery();
             while(rs.next()) {
                 if(rs.getInt("max")!=0) {
                     newOrder_id = rs.getInt("max");
                 }
             }
         } catch (SQLException e) {
             throw new DBException("Can't able to previous order");
         } finally {

             ConnectionUtil.closeConnection(rs, pst, connection);
         }
            return newOrder_id;
     }


     public  void writeCart(int user_id,int product_id,int quantity){
         Connection connection = null;

         PreparedStatement pst = null;


         PreparedStatement pst3=null;

         PreparedStatement pst4=null;



         ResultSet rs2=null;
         int num=0;

         String sql2="insert into order_items(order_id,product_id,quantity) values(?,?,?)";



         String sql4="update order_items set quantity=? where product_id=?";

         String sql5="select quantity from order_items where product_id=?";
         try {
             connection = ConnectionUtil.createConnection();

             pst = connection.prepareStatement(sql2);


             pst3=connection.prepareStatement(sql4);

             pst4=connection.prepareStatement(sql5);

             int oldQuantity=0;
             pst4.setInt(1,product_id);
             rs2= pst4.executeQuery();
             while(rs2.next()){
                 oldQuantity=rs2.getInt("quantity");
             }

            if(getPreviousOrderId(user_id)==0){
                createNewRow(user_id);
                if(checkProductPresence(product_id,getPreviousOrderId(user_id))) {
                    pst3.setInt(1,oldQuantity+quantity);
                    pst3.setInt(2,product_id);
                    pst3.executeUpdate();

                }
                else{
                    pst.setInt(1, getPreviousOrderId(user_id));
                    pst.setInt(2, product_id);
                    pst.setInt(3, quantity);
                    pst.executeUpdate();
                }

            }
            else{

                if(checkProductPresence(product_id,getPreviousOrderId(user_id))) {
                    pst3.setInt(1,oldQuantity+quantity);
                    pst3.setInt(2,product_id);
                    pst3.executeUpdate();

                }
                else{
                    pst.setInt(1, getPreviousOrderId(user_id));
                    pst.setInt(2, product_id);
                    pst.setInt(3, quantity);
                    pst.executeUpdate();
                }
            }
         } catch (SQLException e) {
             throw new DBException("Error in writing cart");
         } finally {

             ConnectionUtil.closeConnection(rs2, pst, connection);

         }

     }

     public  void changeToCheckoutedOut(int user_id){
         Connection connection = null;

         PreparedStatement pst = null;

         ResultSet rs = null;

         String sql= "update orders set status=1  where user_id=?";


         try {
             connection = ConnectionUtil.createConnection();

             pst = connection.prepareStatement(sql);

             pst.setInt(1,user_id);

             pst.executeUpdate();

             createNewRow(user_id);


         } catch (SQLException e) {
             throw new DBException("Can't able to checkout");
         } finally {
             ConnectionUtil.closeConnection(rs, pst, connection);
         }
     }

     public  void readCart(int user_id){
         Connection connection = null;

         PreparedStatement pst = null;
         PreparedStatement pst2=null;

         ResultSet rs = null;

         ResultSet rs2=null;

         String sql= "select product_id,quantity,order_item from order_items where order_id=?";
         String sql2="select order_id,status from orders where user_id=?";

         try {
             connection = ConnectionUtil.createConnection();

             pst = connection.prepareStatement(sql);

             pst2=connection.prepareStatement(sql2);

             pst2.setInt(1,user_id);

             rs2=pst2.executeQuery();

             while(rs2.next()) {
                 pst.setInt(1,rs2.getInt("order_id"));
                 rs = pst.executeQuery();
                 int status=rs2.getInt("status");

                 System.out.println("Order Id---"+rs2.getInt("order_id"));

                if(status==0){
                    System.out.println("-----still in cart-------");
                }
                else{
                    System.out.println("-----checkedout---------");
                }
                 while (rs.next()) {
                     int product_id = rs.getInt("product_id");
                     int quantity = rs.getInt("quantity");

                     for(Products x:getProductArrayList()){
                         if(x.getId()==product_id){
                             System.out.println("Product Name---"+x.getItemName());
                         }
                     }
                     System.out.println("quantity -   " + quantity);

                 }
             }

         } catch (SQLException e) {
             throw new DBException("Can't able to read");
         } finally {
             ConnectionUtil.closeConnection(rs, pst, connection);
         }
     }

     public static void createNewRow(int user_id){
         Connection connection = null;

         PreparedStatement pst = null;
         PreparedStatement pst2=null;


         ResultSet rs = null;

         int max=0;

         String sql= "insert into orders(order_id,user_id,status) values(?,?,?)";

         String sql2="select max(order_id) from orders";

         try {
             connection = ConnectionUtil.createConnection();
             pst = connection.prepareStatement(sql);
             pst2=connection.prepareStatement(sql2);
             rs=pst2.executeQuery();
             while(rs.next()){
                 max=rs.getInt("max");
             }
             if(getPreviousOrderId(user_id)==0) {
                 pst.setInt(1, max+1);
                 pst.setInt(2, user_id);
                 pst.setInt(3, 0);
                 pst.executeUpdate();
             }

             else{

                 pst.setInt(1, max+1);
                 pst.setInt(2, user_id);
                 pst.setInt(3, 0);
                 pst.executeUpdate();
             }

         } catch (SQLException e) {
             throw new DBException("Can't able create row");
         } finally {

             ConnectionUtil.closeConnection(rs, pst, connection);

         }

     }


     public  boolean checkCartIsEmpty(int user_id){
         Connection connection = null;

         PreparedStatement pst = null;

         ResultSet rs = null;

         boolean isEmpty=false;
         int num=0;

         String sql= "select order_id from orders where user_id=? and status=0";

         try {
             connection = ConnectionUtil.createConnection();
             pst = connection.prepareStatement(sql);
             pst.setInt(1,user_id);
             rs=pst.executeQuery();

             while(rs.next()){
                  num=rs.getInt("order_id");
             }
             if(num==0){
                 isEmpty=true;
             }
         } catch (SQLException e) {
             throw new DBException("Can't check if cart is empty");
         } finally {

             ConnectionUtil.closeConnection(rs, pst, connection);

         }
         return isEmpty;

     }

     public static boolean checkProductPresence(int product_id,int order_id){
         Connection connection = null;

         PreparedStatement pst = null;

         ResultSet rs = null;

         boolean isEmpty=false;
         int num=0;

         String sql="select product_id from order_items where product_id=? and order_id=?";

         try {
             connection = ConnectionUtil.createConnection();
             pst = connection.prepareStatement(sql);
             pst.setInt(1,product_id);
             pst.setInt(2,order_id);

             rs=pst.executeQuery();

             while(rs.next()) {
                 num = rs.getInt("product_id");
             }
             if(num!=0){
                 isEmpty=true;
             }

         } catch (SQLException e) {
             throw new DBException("Can't check if cart is empty");
         } finally {

             ConnectionUtil.closeConnection(rs, pst, connection);

         }
         return isEmpty;

     }



     }






