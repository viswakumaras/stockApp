import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;


public class ProductServlet extends HttpServlet {

    private ProductService ps;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      String wholePath=req.getRequestURI();
        String getReq =wholePath.substring(wholePath.lastIndexOf("/") + 1);

        if(getReq.equalsIgnoreCase("view")){
            String products=ps.getProducts();
            this.outputResponse(resp,products,200);

        }

        if(getReq.equalsIgnoreCase("add")){
             doPost(req,resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name=req.getParameter("name");
        String id=req.getParameter("id");
        String category=req.getParameter("category");
        String price=req.getParameter("price");
        String available=req.getParameter("available");


        int rc = HttpServletResponse.SC_OK;


        if (!ProductService.addProducts(name,category,Integer.parseInt(price),Integer.parseInt(available),Integer.parseInt(id))) {
            rc = HttpServletResponse.SC_BAD_REQUEST;
        }
        this.outputResponse(resp,"success",rc);
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
