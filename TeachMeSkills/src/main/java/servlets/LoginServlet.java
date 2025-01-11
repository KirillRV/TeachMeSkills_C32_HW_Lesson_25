package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import listener.LoginListener;
import model.User;
import repository.UserRepository; // Предполагается наличие этого репозитория

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.util.Arrays;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserRepository userRepository;
    private LoginListener loginListener;

    @Override
    public void init() throws ServletException {
        userRepository = new UserRepository();
        loginListener = new LoginListener();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String ipAddress = request.getRemoteAddr();

        try {
            User user = userRepository.findUser(username, hashPassword(password));
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user); // Храним объект User, а не только username и role
                response.sendRedirect("home");
                loginListener.logLoginAttempt(username, true, ipAddress);
            } else {
                response.sendRedirect("login.html?error=true");
                loginListener.logLoginAttempt(username, false, ipAddress);
            }
        } catch (NoSuchAlgorithmException e) {
            response.getWriter().println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        return Arrays.toString(hash);
    }
}