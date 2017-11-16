package com.cg.ars.exception;

/**
 * @description User defined exception class
 * @author INTKHAB
 *
 */
public class AirlineException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AirlineException() {
		super();
	}

	public AirlineException(String arg0) {
		super(arg0);

	}

	public AirlineException(Throwable arg0) {
		super(arg0);

	}

	public AirlineException(String arg0, Throwable arg1) {
		super(arg0, arg1);

	}

	public AirlineException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);

	}
}