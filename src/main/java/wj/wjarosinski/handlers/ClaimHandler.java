//handles users' claims and server's response

package wj.wjarosinski.handlers;

import java.io.InputStream;
import java.io.OutputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import wj.wjarosinski.utils.ClaimBuilder;
import wj.wjarosinski.models.Claim;
import wj.wjarosinski.models.requests.ClaimRequest;
import wj.wjarosinski.models.responses.HttpResponse;
import wj.wjarosinski.services.ClaimService;

public class ClaimHandler extends Handler {
    private final ClaimService claimService;
    public ClaimHandler(ObjectMapper objectMapper, ClaimService claimService) {
        super(objectMapper);
        this.claimService = claimService;
    }

    @Override
    protected void execute(HttpExchange exchange) throws Exception {
        byte[] response;
        if ("POST".equals(exchange.getRequestMethod())) {
            HttpResponse e = process(exchange.getRequestBody());
            exchange.getResponseHeaders().putAll(e.getHeaders());
            exchange.sendResponseHeaders(e.getCode(), 0);
            response = super.writeResponse(e.getBody());
        } else {
            throw new NoSuchMethodException(
                    "Method " + exchange.getRequestMethod() + " is not allowed for " + exchange.getRequestURI());
        }

        OutputStream os = exchange.getResponseBody();
        os.write(response);
        os.close();
    }

    private HttpResponse process(InputStream is)
            throws Exception {
        final ClaimRequest claimRequest = super.readRequest(is, ClaimRequest.class);

        Claim claim = new ClaimBuilder()
                .withDates(claimRequest.getDayDateList())
                .withReimbursements(claimRequest.getReimbursementList())
                .build();

        var finalCost = claimService.calculateClaim(claim);

        return new HttpResponse(finalCost,
                getHeaders("Content-Type", "application/json"), HttpResponse.Code.OK);
    }
}
