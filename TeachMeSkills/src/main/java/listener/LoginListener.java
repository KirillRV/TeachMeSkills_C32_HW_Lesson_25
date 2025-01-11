package listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;


@WebListener
public class LoginListener implements HttpSessionListener, ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger(LoginListener.class.getName());
    private static final String LOG_FILE_PATH = "user_login_log.txt";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private Path logFilePath;


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String realPath = sce.getServletContext().getRealPath("/");
        logFilePath = Paths.get(realPath, LOG_FILE_PATH);
        log("Application started.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log("Application stopped.");
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log("Session created: " + se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log("Session destroyed: " + se.getSession().getId());
    }

    public void logLoginAttempt(String username, boolean success, String ipAddress) {
        String status = success ? "successful" : "failed";
        log("Login attempt for user '" + username + "' from IP " + ipAddress + " was " + status);
    }

    public void logLogout(String username, String ipAddress) {
        log("User '" + username + "' logged out from IP " + ipAddress);
    }

    public void log(String message) {
        String logMessage = DATE_TIME_FORMATTER.format(LocalDateTime.now()) + " - " + message + System.lineSeparator();
        try {
            Files.writeString(logFilePath, logMessage, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error writing to log file: ", e); //Используем java.util.logging
        }
    }
}
