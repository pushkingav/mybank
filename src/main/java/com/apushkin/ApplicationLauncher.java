package com.apushkin;

import com.apushkin.context.BankAppConfiguration;
import jakarta.servlet.ServletContext;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class ApplicationLauncher {
    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        int port = 8080;

        String serverPort = System.getProperty("server.port");
        if (serverPort != null) {
            port = convertStringToInt(serverPort);
        }
        tomcat.setPort(port == -1 ? 8080 : port);
        tomcat.getConnector();

        Context tomcatCtx = tomcat.addContext("", null);
        WebApplicationContext webApplicationContext = createWebApplicationContext(tomcatCtx.getServletContext());
        DispatcherServlet dispatcherServlet = new DispatcherServlet(webApplicationContext);
        Wrapper servlet = Tomcat
                .addServlet(tomcatCtx, "dispatcherServlet", dispatcherServlet);
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/*");

        tomcat.start();
    }

    public static WebApplicationContext createWebApplicationContext(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext springCtx
                = new AnnotationConfigWebApplicationContext();
        springCtx.register(BankAppConfiguration.class);
        springCtx.setServletContext(servletContext);
        springCtx.refresh();
        springCtx.registerShutdownHook();
        return springCtx;
    }

    static int convertStringToInt(String port) {
        assert port != null && !port.isEmpty();
        int i;
        try {
            i = Integer.parseInt(port);
        } catch (NumberFormatException e) {
            String msg = String.format("Invalid server port provided: %s. Using default value: 8080.", port);
            System.out.println(msg);
            return -1;
        }
        return i;
    }
}