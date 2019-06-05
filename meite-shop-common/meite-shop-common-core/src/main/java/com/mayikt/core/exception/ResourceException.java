package com.mayikt.core.exception;

public class ResourceException extends RuntimeException {

	/**  */
	private static final long serialVersionUID = 1L;
	
	private String msgCode;

	public String getMsgCode() {
		return msgCode;
	}

	public ResourceException() {
		super();
	}

	public ResourceException(String msg) {
		super(msg);
	}

	public ResourceException(String msgCode, String msg) {
		super(msg);
		this.msgCode = msgCode;
	}

	public ResourceException(String msgCode, String msg, Throwable e) {
		super(msg, e);
		this.msgCode = msgCode;
	}

	public ResourceException(String msg, Throwable e) {
		super(msg, e);
	}

	public ResourceException(Exception e) {
		super(e);
	}

	public String toString() {
		return new StringBuilder(125).append("ErrMsg:").append(super.getMessage()).toString();
	}

}
