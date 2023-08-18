package wj.wjarosinski.models.responses;

import com.sun.net.httpserver.Headers;

import java.util.List;

public class HttpResponse {
    public enum Code{
        OK(200),
        CREATED(201),
        ACCEPTED(202),
        BAD_REQUEST(400),
        METHOD_NOT_ALLOWED(405);

        Code(int code){this.code = code;}
        private int code;

        public int getCode() {
            return code;
        }
    }
    private final Object body;
    private final Headers headers;
    private final Code code;

    public HttpResponse(Headers headers, Code code){
        this(null, headers, code);
    }

    public HttpResponse(Object body, Headers headers, Code code) {
        this.body = body;
        this.headers = headers != null ? headers : new Headers();
        this.code = code;
        this.headers.put("Access-Control-Allow-Origin", List.of("*"));
    }

    public Object getBody() {
        return body;
    }

    public Headers getHeaders() {
        return headers;
    }

    public int getCode() {
        return code.getCode();
    }
}
