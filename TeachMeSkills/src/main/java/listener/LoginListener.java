package listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebListener
public class LoginListener implements HttpSessionListener, ServletContextListener {
    private static final String LOG_FILE_PATH = "C:\\Java-job\\TeachMeSkills_C32_HW_Lesson_25\\source\\user_login_log.txt";

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        String sessionId = se.getSession().getId();
        log("Session created: " + sessionId);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        String sessionId = se.getSession().getId();
        log("Session destroyed: " + sessionId);
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log("Application started.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log("Application stopped.");
    }

    public void logLoginAttempt(String username, boolean success, String ipAddress) {
        String status = success ? "successful" : "failed";
        log("Login attempt for user '" + username + "' from IP " + ipAddress + " was " + status);
    }

    public void logLogout(String username, String ipAddress) {
        log("User  '" + username + "' logged out from IP " + ipAddress);
    }

    public void log(String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String logMessage = timestamp + " - " + message + System.lineSeparator();
        try {
            Files.write(Paths.get(LOG_FILE_PATH), logMessage.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}