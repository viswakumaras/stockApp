import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Products {

      private   String itemName;
       private String category;
      private   int price;

       private   int available;
      private   int id;
      private  String status="n";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private LocalDateTime date=LocalDateTime.now();

 DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd-MM-yyy");
 String time=date.format(formatter);

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public String getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public int getAvailable() {
        return available;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "  " +
                "  " + itemName + '\'' +"                Date of order Placed =  "+getTime()+"\n"+
                ", SubTotal  =" + price +" "+
                " Quantity --";
    }

    public String displayProducts(){
        return "  " +
                " Product Id = "+id+
                "  itemName='" + itemName + '\'' +
                ", price = " + price +
                " , Available = "+available+
                " category ="+category+

                " ";
    }
//     @Override
//    public String toString(){
//        return ""+"item Name--"+itemName + '\''+"price --"+price +" Quantity--"+available+" time of checkOut---"+getTime()+"";
//    }

    public Products() {

        }

        public int getID() {
            return id;
        }




    }



