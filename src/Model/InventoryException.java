package Model;

public class InventoryException extends Exception {
    public InventoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public InventoryException(String message) {
        super(message);
    }
}
