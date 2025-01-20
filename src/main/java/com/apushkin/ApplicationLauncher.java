package com.apushkin;

import com.apushkin.context.BankAppConfiguration;
import com.apushkin.web.MyBankHttpServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationLauncher {
    public static void main(String[] args) throws LifecycleException {
        AnnotationConfigApplicationContext springCtx
                = new AnnotationConfigApplicationContext(BankAppConfiguration.class);

        Tomcat tomcat = new Tomcat();
        int port = 8080;

        String serverPort = System.getProperty("server.port");
        if (serverPort != null) {
            port = convertStringToInt(serverPort);
        }
        tomcat.setPort(port == -1 ? 8080 : port);
        tomcat.getConnector();

        Context context = tomcat.addContext("", null);
        MyBankHttpServlet bankHttpServlet = springCtx.getBean(MyBankHttpServlet.class);
        Wrapper servlet = Tomcat
                .addServlet(context, "MyBankHttpServlet", bankHttpServlet);
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/*");

        tomcat.start();
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