


import java.util.Scanner;

public class HomePage {

   static  Scanner sc = new Scanner(System.in);

    static Storage storage=new SqlService();

    public static void main(String[] args) {

        System.out.println("type sql to use sql as storage in run config");
        System.out.println(" ");
        System.out.println("type json to use json as storage in run config");
        System.out.println(" ");

       ProductService.i++;
   //     System.out.println(ProductService.getProducts());
      UserService.loginScreen();
    }





    public static void mainMenu() {
        System.out.println(" ");
        System.out.println("-------Available Products------------");
        ProductService.displayProduct();
        System.out.println(" ");
        System.out.println("Press");
        System.out.println("1.To Place order");
        System.out.println("2.To View Cart ");
        System.out.println("3.Password Change");
        System.out.println("4.To  logout");
        int ch = sc.nextInt();

        switch (ch) {

            case 1:{

                System.out.println("Enter product ID");
                int id = sc.nextInt();
                System.out.println("Enter Product Quantity");
                int qty = sc.nextInt();

                ProductService.placeOrder(id, qty); //products amount with respective quantity user enter will be calculated and added to temp cart

                System.out.println("Do you want to checkout out or place more orders ,Press {Y for checkout /N for place more orders}");
                String choice = sc.next();

                if (choice.equalsIgnoreCase("y")) {

                   storage.changeToCheckoutedOut(UserService.loginUserId);

                   storage.readCart(UserService.loginUserId);

                    mainMenu();
                } else {
                    mainMenu();
                }
                break;
            }

            case 2:
            {

              storage.readCart(UserService.loginUserId);

           if(storage.checkCartIsEmpty(UserService.loginUserId)){
               System.out.println("Press N to main menu");
               String choice = sc.next();
               if(choice!=null){
                   mainMenu();
               }
               break;
           }
           else {
               System.out.println("Do you want to checkout out or place more orders ,Press {Y for checkout /N for place more orders}");
               String choice = sc.next();
               if (choice.equalsIgnoreCase("y")) {

                   storage.changeToCheckoutedOut(UserService.loginUserId);

                   mainMenu();
               } else {
                   mainMenu();
               }
           }
               break;
            }
            case 3:{
                System.out.println("Enter new password");
                String newPass=sc.next();
                storage.changePassword(UserService.loginUserName,newPass);
                System.out.println("success");
                mainMenu();
            }
            case 4:{
                UserService.loginScreen();
            }

        }

        }
        public static void adminChoice(){
            System.out.println(" ");
            System.out.println("Press");
            System.out.println("1.To Change Product name");
            System.out.println("2.Add a new product");
            System.out.println("3.View Low quantity Products");
            System.out.println("3.To  logout");
            int ch = sc.nextInt();
            switch (ch){
                case 1:{
                    System.out.println(" ");
                    ProductService.displayProduct();
                    System.out.println(" ");
                    System.out.println(" Enter Product ID");
                    int id=sc.nextInt();
                    System.out.println("Enter new name");
                    String newName=sc.next();
                    System.out.println(" ");
                    System.out.println("Enter quantity");
                    int quantity=sc.nextInt();

                    storage.updateQuantity(id,quantity);
                    storage.updateName(id,newName);

                    System.out.println("success");
                    adminChoice();
                    break;
                }
                case 3: {
                    for (Products x : storage.getProductArrayList()) {
                        if (x.getAvailable() < 3) {
                            System.out.println(x.getItemName() + " Quantity left - " + x.getAvailable());

                        }
                        else{
                            System.out.println("No items less then 3");
                            break;
                        }
                    }
                    adminChoice();
                    break;
                }
                case 4:{
                   UserService.loginScreen();
                }
                case 2:{
                    System.out.println("Enter product Name");
                    String name=sc.next();
                    System.out.println("Enter category");
                    String type=sc.next();
                    System.out.println("Available");
                    int available=sc.nextInt();
                    System.out.println("Price");
                    int price=sc.nextInt();
                    System.out.println("ID");
                    int id= sc.nextInt();
                    storage.addProduct(name,type,available,price,id);
                    System.out.println("success");
                    adminChoice();
                }
            }
        }

    }


