package cl.bennu.reports.commons.dto.base;

/**
 * Created by Camilo on 06-10-2014.
 */
public class ResponseJSON extends BaseDTO {

    private String message;
    private Long responseType;
    private Boolean response;
    private Object o;

    public Boolean getResponse() {
        return response;
    }

    public void setResponse(Boolean response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getResponseType() {
        return responseType;
    }

    public void setResponseType(Long responseType) {
        this.responseType = responseType;
    }

    public Object getO() {
        return o;
    }

    public void setO(Object o) {
        this.o = o;
    }
}
