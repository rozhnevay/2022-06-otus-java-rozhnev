package ru.otus.server;

import com.google.gson.Gson;
import java.util.Arrays;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.crm.service.TemplateProcessor;
import ru.otus.crm.service.UserAuthService;
import ru.otus.dao.UserDao;
import ru.otus.servlet.AuthorizationFilter;
import ru.otus.servlet.LoginServlet;

public class ClientsWebServerWithFilterBasedSecurity extends ClientsWebServerSimple {
    private final UserAuthService authService;
    private final UserDao userDao;

    public ClientsWebServerWithFilterBasedSecurity(int port,
                                                   UserAuthService authService,
                                                   UserDao userDao,
                                                   DBServiceClient dbServiceClient,
                                                   Gson gson,
                                                   TemplateProcessor templateProcessor) {
        super(port, dbServiceClient, gson, templateProcessor);
        this.userDao = userDao;
        this.authService = authService;
    }

    @Override
    protected Handler applySecurity(ServletContextHandler servletContextHandler, String... paths) {
        servletContextHandler.addServlet(new ServletHolder(new LoginServlet(templateProcessor, authService)), "/login");
        AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        Arrays.stream(paths).forEachOrdered(path -> servletContextHandler.addFilter(new FilterHolder(authorizationFilter), path, null));
        return servletContextHandler;
    }
}
