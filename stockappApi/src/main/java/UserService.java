import java.util.Scanner;


public class UserService {

    static String loginUserRole="";
    static String loginUserName= "";
    static Integer loginUserId;
    static Users loggedInUserObj;

    static Scanner sc=new Scanner(System.in);

    public static void loginScreen(){
        System.out.println("Press");
        System.out.println("1.existing user");
        System.out.println("2.new user ");
        int login=sc.nextInt();
        if(login == 1) {
            System.out.println("Enter your name");
            String userName = sc.next();
            System.out.println("Enter you password");
            String password = sc.next();
            UserService.loginUserId = 0;
            UserService.loginUserRole = "";
            UserService.loginUserName = "";

            if (UserService.loginValidation(userName, password)) {
                System.out.println(loginUserRole);
                if (loginUserRole.equalsIgnoreCase("customer")) {
                    System.out.println("Welcome " + loginUserName);
                    HomePage.mainMenu();
                } else {
                    System.out.println("welcome admin" + loginUserName);
                    HomePage.adminChoice();
                }
            } else {
                System.out.println("Invalid credentials");
                loginScreen();
            }
        }
        else{
            System.out.println("Enter your name");
            String userName = sc.next();
            System.out.println("Enter you password");
            String password = sc.next();
            HomePage.storage.addNewUser(userName,password);
            loginScreen();
        }
    }

    public static boolean loginValidation(String userName, String password){
        boolean validUser=false;
        for(Users x: HomePage.storage.getUserArrayList()) {

            if (x.getName().equalsIgnoreCase(userName) && x.getPassword().equalsIgnoreCase(password)) {
                x.setStatus(true);
                validUser=true;
                loggedInUserObj=x;
                loginUserRole=x.getRole();
                loginUserName=x.getName();
                loginUserId=x.getId();
                break;
            }

        }
        return validUser;
    }


    public static Users getUserObj(){

        return loggedInUserObj;
    }

    public static Users getUserObjById(int id){
        Users userObj = null;
        for(Users x: HomePage.storage.getUserArrayList()){
            if(x.getId()==id){
                userObj=x;
            }
        }
        return userObj;
    }
    public static boolean signUp(String name,String pass){
        try{
            HomePage.storage.addNewUser(name,pass);

            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public  static int getUserId(String name,String pass){
        try{
            int id=0;
            for(Users x:HomePage.storage.getUserArrayList()){
                if(x.getName().equalsIgnoreCase(name)&&x.getPassword().equalsIgnoreCase(pass)){
                    id=x.getId();
                }
            }
            return id;
        }
        catch(Exception e){
            return 0;
        }
    }







}
