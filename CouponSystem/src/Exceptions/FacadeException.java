package Exceptions;

public class FacadeException extends CouponSystemException {
	public FacadeException() {
		super();
	}

	public FacadeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FacadeException(String message, Throwable cause) {
		super(message, cause);
	}

	public FacadeException(String message) {
		super(message);
	}

	public FacadeException(Throwable cause) {
		super(cause);
	}
}
