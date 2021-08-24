import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.stream.Collectors;


public class OrderServlet extends HttpServlet {

    ProductService ps;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String wholePath=req.getRequestURI();
        String getReq =wholePath.substring(wholePath.lastIndexOf("/") + 1);
        if(getReq.equalsIgnoreCase("id")) {

            //  String reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            String id = req.getParameter("id");
            String userId=req.getParameter("userId");
            int rc = HttpServletResponse.SC_OK;
            try {
                ProductService.placeOrderService(Integer.parseInt(id), Integer.parseInt(userId));

            }
            catch(Exception e) {
                    rc = HttpServletResponse.SC_BAD_REQUEST;
            }
            this.outputResponse(resp,"success", rc);
        }
        if (getReq.equalsIgnoreCase("cart")){
            String userId=req.getParameter("id");
            int rc = HttpServletResponse.SC_OK;
            String gson="";
            try {
                gson= ProductService.readStillInCart(Integer.parseInt(userId));
            }
            catch(Exception e) {
                rc = HttpServletResponse.SC_BAD_REQUEST;
            }

            this.outputResponse(resp,gson, rc);
        }
        if (getReq.equalsIgnoreCase("checkout")){
            String userId=req.getParameter("id");
            int rc = HttpServletResponse.SC_OK;
            String gson="";
            try {
                 gson=  ProductService.readCheckedOut(Integer.parseInt(userId));
            }
            catch(Exception e) {
                rc = HttpServletResponse.SC_BAD_REQUEST;
            }

            this.outputResponse(resp,gson, rc);
        }

        if(getReq.equalsIgnoreCase("changetocheckout")){
            String userId=req.getParameter("id");
            int rc = HttpServletResponse.SC_OK;

            try {
               HomePage.storage.changeToCheckoutedOut(Integer.parseInt(userId));
            }
            catch(Exception e) {
                rc = HttpServletResponse.SC_BAD_REQUEST;
            }

            this.outputResponse(resp,"success", rc);
        }

    }



    private void outputResponse(HttpServletResponse response, String payload, int status) {
        response.setHeader("Content-Type", "application/json");
        try {
            response.setStatus(status);
            if (payload != null) {
                OutputStream outputStream = response.getOutputStream();
                outputStream.write(payload.getBytes());
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}


