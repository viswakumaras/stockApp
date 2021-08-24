import java.util.ArrayList;

interface Storage{

    public  ArrayList<Users> getUserArrayList();

    public  ArrayList<Products> getProductArrayList();

    public void writeCart(int userId, int productId, int quantity);

    public  void readCart(int userId);

    public  void changeToCheckoutedOut(int userId);

    public  boolean checkCartIsEmpty(int userId);

    public void  updateQuantity(int si, int quantity);

    public  void  updateName(int si, String name);

    public void addProduct(String name, String type, int availabe, int price, int id);

    public void changePassword(String loggedInUser, String pass);

    public  void addNewUser(String username, String password);


    }
