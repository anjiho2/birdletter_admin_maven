package com.challabros.birdletter.admin.error;

public class BirdAdminException extends RuntimeException {
	public BirdAdminException(int errorCode)
    {
        this.errorCode = errorCode;
        message = null;
    }

    public BirdAdminException(int errorCode, String message)
    {
        this(errorCode);
        this.message = message;
    }

    public BirdAdminException(BirdAdminErrorCode birdAdminErrorCode)
    {
        errorCode = birdAdminErrorCode.code();
        message = birdAdminErrorCode.msg();
    }

    public BirdAdminException(BirdAdminErrorCode birdAdminErrorCode, String message)
    {
        errorCode = birdAdminErrorCode.code();
    }

    public int getErrorCode()
    {
        return errorCode;
    }

    public String getMessage()
    {
        return message;
    }

    private final int errorCode;
    private String message;
}
