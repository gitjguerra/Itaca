package com.csi.itaca.common.exception;

public class BusinessOperationException extends Exception {
    private static final long serialVersionUID = -8944910060589155984L;
    private String i18nErrorKey;

    public static final String DEFAULT_ERROR_KEY = "general.error";

    public BusinessOperationException(String i18nErrorKey) {
        super(i18nErrorKey);
        this.i18nErrorKey = i18nErrorKey;
    }

    public BusinessOperationException(String i18nErrorKey, Throwable cause) {
        super(i18nErrorKey, cause);
        this.i18nErrorKey = i18nErrorKey;
    }

    public String getI18nErrorKey() {
        return this.i18nErrorKey;
    }

    public void setI18nErrorKey(String i18nErrorKey) {
        this.i18nErrorKey = i18nErrorKey;
    }
}
