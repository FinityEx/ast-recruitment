package wj.wjarosinski.models.requests;

import java.util.Date;
import java.util.List;

public class HttpRequest<T> {

    private final T body;

    public HttpRequest(T body) {
        this.body = body;
    }

    public T getBody() {
        return body;
    }


}
