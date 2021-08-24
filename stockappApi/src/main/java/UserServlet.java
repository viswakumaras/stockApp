import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.stream.Collectors;


public class UserServlet extends HttpServlet {

    UserService ps;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String wholePath=req.getRequestURI();
        String getReq =wholePath.substring(wholePath.lastIndexOf("/") + 1);
        if(getReq.equalsIgnoreCase("auth")) {
            //  String reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            String userName = req.getParameter("username");
            String password = req.getParameter("password");
            int rc = HttpServletResponse.SC_OK;
            boolean res = ps.loginValidation(userName, password);
            if (!res) {
                rc = HttpServletResponse.SC_BAD_REQUEST;
            }
            this.outputResponse(resp, Boolean.toString(res), rc);
        }
        if(getReq.equalsIgnoreCase("id")){
            String userName = req.getParameter("username");
            String password = req.getParameter("password");
            int rc = HttpServletResponse.SC_OK;
            int res =UserService.getUserId(userName,password);
            if (res==0) {
                rc = HttpServletResponse.SC_BAD_REQUEST;
            }
            this.outputResponse(resp,Integer.toString(res), rc);
        }
        if(getReq.equalsIgnoreCase("add")){
            String userName = req.getParameter("username");
            String password = req.getParameter("password");
            int rc = HttpServletResponse.SC_OK;
            boolean res =UserService.signUp(userName,password);
            if (!res) {
                rc = HttpServletResponse.SC_BAD_REQUEST;
            }
            this.outputResponse(resp,Boolean.toString(res), rc);
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

