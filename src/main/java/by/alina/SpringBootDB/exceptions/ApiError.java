package by.alina.SpringBootDB.exceptions;

public class ApiError {
    private String message;
    private String debugMessage;

    public ApiError(String message, String debugMessage) {
        this.message = message;
        this.debugMessage = debugMessage;
    }

    public ApiError(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }
}
