import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import wj.wjarosinski.handlers.ClaimHandler;
import wj.wjarosinski.models.requests.ClaimRequest;
import wj.wjarosinski.models.responses.HttpResponse;
import wj.wjarosinski.services.ClaimService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClaimHandlerTest {

    private ClaimHandler claimHandler;
    private ObjectMapper objectMapper;
    private ClaimService claimServiceMock;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        claimServiceMock = mock(ClaimService.class);
        claimHandler = new ClaimHandler(objectMapper, claimServiceMock);
    }

    @Test
    public void testExecute_ValidClaimRequest_ReturnsExpectedResponse() throws Exception {
        ClaimRequest claimRequest = new ClaimRequest();
        // Set up mock ClaimService behavior
        when(claimServiceMock.calculateClaim(any())).thenReturn(100.0);

        // Create a mock HttpExchange and input stream for request body
        HttpExchange httpExchangeMock = mock(HttpExchange.class);
        ByteArrayInputStream requestBodyStream = new ByteArrayInputStream(objectMapper.writeValueAsBytes(claimRequest));
        when(httpExchangeMock.getRequestBody()).thenReturn(requestBodyStream);

        // Create a mock output stream to capture the response
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
        when(httpExchangeMock.getResponseBody()).thenReturn(responseStream);

        // Simulate executing the execute method
        claimHandler.handle(httpExchangeMock);

        // Verify that ClaimService.calculateClaim was called and capture the response body
        verify(claimServiceMock, times(1)).calculateClaim(any());

        // Deserialize the response and verify its contents
        HttpResponse response = objectMapper.readValue(responseStream.toByteArray(), HttpResponse.class);
        assertEquals(HttpResponse.Code.OK.getCode(), response.getCode());
        assertEquals(100.0, response.getBody());
    }

    // Subclass of ClaimHandler to expose the protected execute method

}
