package sentry.core.exceptions;

public class NoSuchHookPointException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 874216260078062784L;

	public NoSuchHookPointException() {
		super();
	}

	public NoSuchHookPointException(String message) {
		super(message);
	}

	public NoSuchHookPointException(Throwable cause) {
		super(cause);
	}

	public NoSuchHookPointException(String message, Throwable cause) {
		super(message, cause);
	}

}
