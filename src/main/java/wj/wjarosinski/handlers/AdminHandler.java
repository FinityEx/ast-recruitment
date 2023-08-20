// Handles admin requests and responses
package wj.wjarosinski.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import wj.wjarosinski.models.requests.AdminRequest;
import wj.wjarosinski.models.responses.HttpResponse;
import wj.wjarosinski.services.AdminService;

import java.io.InputStream;
import java.io.OutputStream;

import static wj.wjarosinski.models.responses.HttpResponse.Code.OK;

public class AdminHandler extends Handler {
    private final AdminService adminService;

    public AdminHandler(ObjectMapper objectMapper, AdminService adminService) {
        super(objectMapper);
        this.adminService = adminService;
    }

    @Override
    protected void execute(HttpExchange exchange) throws Exception {
        byte[] response;
        if ("POST".equals(exchange.getRequestMethod())) {
            HttpResponse e = process(exchange.getRequestBody());
            exchange.getResponseHeaders().putAll(e.getHeaders());
            exchange.sendResponseHeaders(e.getCode(), 0);
            response = super.writeResponse(e.getCode());
        } else if ("GET".equals(exchange.getRequestMethod()) && exchange.getRequestURI().toString()
                .equals("/api/admin/rates")) {
            HttpResponse e = new HttpResponse(AdminService.getCurrentRates(),
                    getHeaders("Content-Type", "application/json"), OK);
            exchange.getResponseHeaders().putAll(e.getHeaders());
            exchange.sendResponseHeaders(e.getCode(), 0);
            response = super.writeResponse(e.getBody());
        } else if ("GET".equals(exchange.getRequestMethod()) && exchange.getRequestURI().toString()
                .equals("/api/admin/reimbursement-options")) {
            HttpResponse e = new HttpResponse(AdminService.getAllReimbursementOptions(),
                    getHeaders("Content-Type", "application/json"), OK);
            exchange.getResponseHeaders().putAll(e.getHeaders());
            exchange.sendResponseHeaders(e.getCode(), 0);
            response = super.writeResponse(e.getBody());
        } else if ("GET".equals(exchange.getRequestMethod()) && exchange.getRequestURI().toString()
                .equals("/api/admin/reimbursement-options-enabled")) {
            HttpResponse e = new HttpResponse(AdminService.getAvailableReimbursementOptions(),
                    getHeaders("Content-Type", "application/json"), OK);
            exchange.getResponseHeaders().putAll(e.getHeaders());
            exchange.sendResponseHeaders(e.getCode(), 0);
            response = super.writeResponse(e.getBody());
        }
        else {
            throw new NoSuchMethodException(
                    "Method " + exchange.getRequestMethod() + " is not allowed for " + exchange.getRequestURI());
        }

        OutputStream os = exchange.getResponseBody();
        os.write(response);
        os.close();
    }

    private HttpResponse process(InputStream is)
            throws Exception {
        final AdminRequest adminRequest = super.readRequest(is, AdminRequest.class);
        adminService.setRatesAndLimits(adminRequest);

        return new HttpResponse(getHeaders("Content-Type", "application/json"), OK);
    }
}
