//Entry point for the application, declares server port, endpoints and Handler classes

package wj.wjarosinski;


import com.sun.net.httpserver.HttpServer;
import com.fasterxml.jackson.databind.ObjectMapper;
import wj.wjarosinski.handlers.AdminHandler;
import wj.wjarosinski.handlers.ClaimHandler;
import wj.wjarosinski.services.AdminService;
import wj.wjarosinski.services.ClaimService;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    final static int SERVER_PORT = 8080;

    public static void main(String[] args) throws IOException {
        final HttpServer server = HttpServer.create(new InetSocketAddress(SERVER_PORT), 0);
        final ClaimHandler claimHandler = new ClaimHandler(new ObjectMapper(), new ClaimService());
        final AdminHandler adminHandler = new AdminHandler(new ObjectMapper(), new AdminService());

        server.createContext("/api/claims/make-claim", claimHandler::handle);
        server.createContext("/api/admin/rates", adminHandler::handle);
        server.createContext("/api/admin/reimbursement-options", adminHandler::handle);
        server.createContext("/api/admin/reimbursement-options-enabled", adminHandler::handle);
        server.setExecutor(null);
        server.start();
        System.out.println("Backend server is up and running");
    }


}