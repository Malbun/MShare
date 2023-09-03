package ch.malbun.mshare;

import java.io.IOException;

public class MShareException extends IOException {
    public MShareException() {
        super();
    }

    public MShareException(String message) {
        super(message);
    }

    public MShareException(String message, Throwable cause) {
        super(message, cause);
    }

    public MShareException(Throwable cause) {
        super(cause);
    }
}
