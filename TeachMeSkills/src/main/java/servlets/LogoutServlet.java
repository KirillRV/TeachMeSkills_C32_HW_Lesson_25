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

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
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
            session.invalidate();
            loginListener.logLogout(user.getUsername(), ipAddress);
        }
        response.sendRedirect("login.html");
    }
}