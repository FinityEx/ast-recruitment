package wj.wjarosinski.handlers;

import java.io.InputStream;
import java.security.InvalidKeyException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jdi.request.ExceptionRequest;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import io.vavr.control.Try;

public abstract class Handler {

    private final ObjectMapper objectMapper;

    public Handler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void handle(HttpExchange exchange) {
        Try.run(() -> execute(exchange))
                .onFailure(thr -> {
                    exchange.close();
                    thr.printStackTrace();
                });
    }

    protected abstract void execute(HttpExchange exchange) throws Exception;


    protected <T> T readRequest(InputStream is, Class<T> type) throws Exception {
        return Try.of(() -> objectMapper.readValue(is, type))
                .getOrElseThrow((e) -> new Exception(e.getMessage()));
    }

    protected <T> byte[] writeResponse(T response) throws Exception {
        return Try.of(() -> objectMapper.writeValueAsBytes(response))
                .getOrElseThrow(() -> new Exception("Invalid request"));
    }

    protected static Headers getHeaders(String key, String value) {
        Headers headers = new Headers();
        headers.set(key, value);
        return headers;
    }
}