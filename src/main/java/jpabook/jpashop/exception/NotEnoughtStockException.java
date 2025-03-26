package jpabook.jpashop.exception;

public class NotEnoughtStockException extends RuntimeException{
    public NotEnoughtStockException() {
        super();
    }

    public NotEnoughtStockException(Throwable cause) {
        super(cause);
    }

    public NotEnoughtStockException(String message) {
        super(message);
    }

    public NotEnoughtStockException(String message, Throwable cause) {
        super(message, cause);
    }
}
