import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class JsonService implements Storage{


    /**
     * Write in json file
     * @param array
     * @param fileName
     */
    public  void jsonWrite(JSONArray array, String fileName){
        try {
            FileWriter file = new FileWriter("/home/local/ZOHOCORP/viswa-12069/IdeaProjects/stockapp/src/main/java/"+fileName+".json");
            file.write(array.toJSONString());
            file.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public  ArrayList<Users> getUserArrayList(){

        ArrayList<Users> usersArrayList=new ArrayList<>();
        JSONArray userJsonArray=new JSONArray();
        try {

            JSONParser jsonParser = new JSONParser();
            userJsonArray = (JSONArray) jsonParser.parse(new FileReader("/home/local/ZOHOCORP/viswa-12069/IdeaProjects/stockapp/src/main/java/user.json"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for(int i=0;i<userJsonArray.size();i++){

            JSONObject employeeObject = (JSONObject) userJsonArray.get(i);
            String name = (String) employeeObject.get("name");
            String pass = (String) employeeObject.get("pass");
            String role= (String)employeeObject.get("role");
            Users user= new Users();
            user.setName(name);
            user.setRole(role);
            user.setPassword(pass);
            user.setId(usersArrayList.size()+1);
            usersArrayList.add(user);

        }
        return usersArrayList;
    }

    public  ArrayList<Products> getProductArrayList(){
        JSONParser jsonParser = new JSONParser();
        JSONArray productsJsonArray=new JSONArray();
        ArrayList<Products> productsArrayList=new ArrayList<>();

        try  {

            productsJsonArray = (JSONArray) jsonParser.parse(new FileReader("/home/local/ZOHOCORP/viswa-12069/IdeaProjects/stockapp/src/main/java/data.json"));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < productsJsonArray.size(); i++) {

            JSONObject productObject = (JSONObject) productsJsonArray.get(i);

            String name = (String) productObject.get("name");


            String type = (String) productObject.get("type");


            Long q = (Long) productObject.get("quantity");

            Long price = (Long) productObject.get("price");

            Long id = (Long) productObject.get("id");


            Products s1 = new Products();

            s1.setId(Math.toIntExact(id));
            s1.setPrice(Math.toIntExact(price));
            s1.setAvailable(Integer.parseInt(String.valueOf(q)));
            s1.setCategory(type);
            s1.setItemName(name);
            productsArrayList.add(s1);
        }
        return productsArrayList;

    }


    static JSONObject userIdObj=new JSONObject();
  static JSONObject cartString =new JSONObject();



    public  void writeCart(int userId, int productId, int quantity){
        JSONParser jsonParser=new JSONParser();
        JSONObject cart=new JSONObject();

 //Reading the json
        try{
            cart =(JSONObject) jsonParser.parse(new FileReader("/home/local/ZOHOCORP/viswa-12069/IdeaProjects/stockapp/src/main/java/OrdersPlacedFile.json"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if((JSONObject)cart.get(Integer.toString(userId))==null ) {

            JSONObject productObj=new JSONObject();

            productObj.put(String.valueOf(productId),quantity);

            JSONObject orderIdObj = new JSONObject();

            orderIdObj.put(String.valueOf(ProductService.i), productObj);

            JSONObject empty = new JSONObject();

            JSONObject sts = new JSONObject();

            sts.put("yes", empty);


            sts.put("no", orderIdObj);


            userIdObj.put(String.valueOf(userId), sts);
        }

        else{

             cartString =(JSONObject) cart.get(Integer.toString(userId));

            JSONObject noObj=(JSONObject) cartString.get("no");


            JSONObject newObj=(JSONObject)noObj.get(Integer.toString(ProductService.i));

            if(((JSONObject) noObj.get(Integer.toString(ProductService.i)))== null){


                JSONObject temp =new JSONObject();

                temp.put(String.valueOf(productId),quantity);

                noObj.put(String.valueOf(ProductService.i),temp);

                userIdObj.put(String.valueOf(userId),cartString);
            }
            else {
                if (newObj.get(Integer.toString(productId)) == null) {
                    newObj.put(String.valueOf(productId), quantity);
                    userIdObj.put(String.valueOf(userId), cartString);

                }
            else{

               Long newQuantity=(Long)newObj.get(Integer.toString(productId))+quantity;


                    newObj.remove(Integer.toString(productId));
                    newObj.put(String.valueOf(productId),newQuantity);

                   userIdObj.put(String.valueOf(userId),cartString);
                }
            }
        }

//writing in json
        try {
            FileWriter file = new FileWriter("/home/local/ZOHOCORP/viswa-12069/IdeaProjects/stockapp/src/main/java/OrdersPlacedFile.json");
            file.write(userIdObj.toString());
            file.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public  void readCart(int userId){
        JSONParser jsonParser=new JSONParser();
        JSONObject cart=new JSONObject();
        try{
            cart =(JSONObject) jsonParser.parse(new FileReader("/home/local/ZOHOCORP/viswa-12069/IdeaProjects/stockapp/src/main/java/OrdersPlacedFile.json"));

    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    } catch (ParseException e) {
        e.printStackTrace();
    }

        JSONObject cartString =(JSONObject) cart.get(Integer.toString(userId));

        JSONObject yesObj= (JSONObject) cartString.get("yes");
        JSONObject noObj=(JSONObject) cartString.get("no");


        JSONObject orderId=(JSONObject)noObj.get(Integer.toString(ProductService.i));

        Set<String> set = yesObj.keySet();

        System.out.println("----CheckedOut items ---");
      for(String x:set){

         JSONObject str=(JSONObject) yesObj.get(x);
          System.out.println("Order ID -- "+x);

         Set<String> innerSet=str.keySet();

         for (String x1:innerSet){
             Long quantity=(Long)str.get(x1);
             System.out.println("Product Id-- "+x1+" Quantity --"+quantity);

         }

      }


        System.out.println("----------Still In Cart-----------");
if(orderId!=null) {
    Set<String> set2 = orderId.keySet();
    for (String x : set2) {

        Long val = (Long) orderId.get(x);

        System.out.println("product Id--- " + x + " Quantity " + val);
    }
}
else{
    System.out.println("no items");
}

    }


    public  void changeToCheckoutedOut(int userId){
        JSONParser jsonParser=new JSONParser();
        JSONObject cart=new JSONObject();

        try{
            cart =(JSONObject) jsonParser.parse(new FileReader("/home/local/ZOHOCORP/viswa-12069/IdeaProjects/stockapp/src/main/java/OrdersPlacedFile.json"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JSONObject cartString =(JSONObject) cart.get(Integer.toString(userId));
        JSONObject yesObj= (JSONObject) cartString.get("yes");
        JSONObject noObj=(JSONObject) cartString.get("no");

        JSONObject temp =(JSONObject)noObj.get(Integer.toString(ProductService.i));

         yesObj.put(ProductService.i,temp);
       ProductService.i++;
       JSONObject empty= new JSONObject();

       cartString.put("yes",yesObj);
       cartString.put("no",empty);

        try {
            FileWriter file = new FileWriter("/home/local/ZOHOCORP/viswa-12069/IdeaProjects/stockapp/src/main/java/OrdersPlacedFile.json");
            file.write(cart.toString());
            file.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

     public  void  updateQuantity(int si, int quantity){

         JSONParser jsonParser = new JSONParser();
         JSONArray productsJsonArray =new JSONArray();
         try  {

             productsJsonArray = (JSONArray) jsonParser.parse(new FileReader("/home/local/ZOHOCORP/viswa-12069/IdeaProjects/stockapp/src/main/java/data.json"));


         } catch (FileNotFoundException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         } catch (ParseException e) {
             e.printStackTrace();
         }

         for(int i=0;i<productsJsonArray.size();i++){
             JSONObject userObject = (JSONObject) productsJsonArray.get(i);

             Long name = (Long) userObject.get("id");
             if(Math.toIntExact(name)==si){
                 userObject.put("quantity",quantity);
                 break;
             }
         }
         jsonWrite(productsJsonArray,"data");

     }

    public void  updateName(int si,String name){

        JSONParser jsonParser = new JSONParser();
        JSONArray productsJsonArray =new JSONArray();
        try  {

            productsJsonArray = (JSONArray) jsonParser.parse(new FileReader("/home/local/ZOHOCORP/viswa-12069/IdeaProjects/stockapp/src/main/java/data.json"));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for(int i=0;i<productsJsonArray.size();i++){
            JSONObject userObject = (JSONObject) productsJsonArray.get(i);

            Long id = (Long) userObject.get("id");
            if(Math.toIntExact(id)==si){
                userObject.put("name",name);
                break;
            }
        }
        jsonWrite(productsJsonArray,"data");

    }

    public void addProduct(String name,String type,int availabe,int price,int id ){
        JSONParser jsonParser = new JSONParser();
        JSONArray productsJsonArray=new JSONArray();
        try  {

            productsJsonArray = (JSONArray) jsonParser.parse(new FileReader("/home/local/ZOHOCORP/viswa-12069/IdeaProjects/stockapp/src/main/java/data.json"));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("type", type);
        jsonObject.put("quantity",availabe);
        jsonObject.put("price",price);
        jsonObject.put("id",id);
        productsJsonArray.add(jsonObject);

        jsonWrite(productsJsonArray,"data");
    }

    public  void changePassword(String loggedInUser,String pass) {
        JSONArray userJsonArray=new JSONArray();
        try {
            JSONParser jsonParser = new JSONParser();
            userJsonArray = (JSONArray) jsonParser.parse(new FileReader("/home/local/ZOHOCORP/viswa-12069/IdeaProjects/stockapp/src/main/java/user.json"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for(int i=0;i<userJsonArray.size();i++){
            JSONObject userObject = (JSONObject) userJsonArray.get(i);
            String name = (String) userObject.get("name");
            if(name.equalsIgnoreCase(loggedInUser)){
                userObject.put("pass",pass);
                break;
            }
        }
        jsonWrite(userJsonArray,"user");
    }
    /**
     * add new user
     * @param username
     * @param password
     */
    public void addNewUser(String username, String password){


        JSONArray userJsonArray=new JSONArray();
        try {
            JSONParser jsonParser = new JSONParser();
            userJsonArray = (JSONArray) jsonParser.parse(new FileReader("/home/local/ZOHOCORP/viswa-12069/IdeaProjects/stockapp/src/main/java/user.json"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", username);
        jsonObject.put("pass", password);
        jsonObject.put("role","customer");


        userJsonArray.add(jsonObject);
        jsonWrite(userJsonArray,"user");
        System.out.println("success");
        UserService.loginScreen();
    }


    public  boolean checkCartIsEmpty(int userId){
        JSONParser jsonParser=new JSONParser();
        JSONObject cart=new JSONObject();
         boolean isTrue=false;
        try{
            cart =(JSONObject) jsonParser.parse(new FileReader("/home/local/ZOHOCORP/viswa-12069/IdeaProjects/stockapp/src/main/java/OrdersPlacedFile.json"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JSONObject cartSring =(JSONObject) cart.get(Integer.toString(userId));
        JSONObject yesObj= (JSONObject) cartSring.get("yes");
        JSONObject noObj=(JSONObject) cartSring.get("no");

        JSONObject empty=new JSONObject();

        if(noObj.isEmpty()) {
            isTrue=true;
        }
        return isTrue;
    }


}
