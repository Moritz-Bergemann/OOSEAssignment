package Controller;

public class StockManagerException extends Exception{
    public StockManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public StockManagerException(String message) {
        super(message);
    }
}
