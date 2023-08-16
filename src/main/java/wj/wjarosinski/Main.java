package wj.wjarosinski;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import wj.wjarosinski.handlers.AdminHandler;
import wj.wjarosinski.handlers.ClaimHandler;
import wj.wjarosinski.services.AdminService;
import wj.wjarosinski.services.ClaimService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.*;

public class Main {
    public static void main(String[] args) throws IOException {
        int serverPort = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);

        ClaimHandler claimHandler = new ClaimHandler(new ObjectMapper(), new ClaimService());
        AdminHandler adminHandler = new AdminHandler(new ObjectMapper(), new AdminService());
        server.createContext("/api/claims/make-claim", claimHandler::handle);
        server.createContext("/api/admin/rates", adminHandler::handle);
        server.createContext("/api/admin/reimbursement-options", adminHandler::handle);
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    public static Map<String, List<String>> splitQuery(String query) {
        if (query == null || "".equals(query)) {
            return Collections.emptyMap();
        }

        return Pattern.compile("&").splitAsStream(query)
                .map(s -> Arrays.copyOf(s.split("="), 2))
                .collect(groupingBy(s -> decode(s[0]), mapping(s -> decode(s[1]), toList())));

    }
    private static String decode(final String encoded) {
        return encoded == null ? null : URLDecoder.decode(encoded, StandardCharsets.UTF_8);
    }
}