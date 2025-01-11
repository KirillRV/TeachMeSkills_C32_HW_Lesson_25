package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import listener.LoginListener;
import model.User;

import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private LoginListener loginListener;

    @Override
    public void init() throws ServletException {
        loginListener = new LoginListener();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String ipAddress = request.getRemoteAddr();

        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            response.getWriter().write("Hello, " + user.getUsername() + "! Your role: " + user.getRole());
            loginListener.log("User '" + user.getUsername() + "' with role '" + user.getRole() + "' accessed home page from IP " + ipAddress);
        } else {
            response.sendRedirect("error401.html");
        }
    }
}