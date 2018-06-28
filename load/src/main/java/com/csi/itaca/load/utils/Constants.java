package com.csi.itaca.load.utils;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class Constants {

    private static final String LOAD_COMPLETED_WITH_ERRORS = "-3";
    private static final String PRELOAD_COMPLETED_WITH_ERRORS = "-2";
    private static final String LOAD_FAILED = "-1";
    private static final String NOT_YET_STARTED = "0";
    private static final String UPLOADING_IN_PROGRESS = "100";
    private static final String UPLOADING_COMPLETED = "101";
    private static final String PRELOADING_IN_PROGRESS = "200";
    private static final String PRELOADING_IN_PROGRESS_WITH_ERRORS = "201";
    private static final String PRELOAD_COMPLETED = "202";
    private static final String PRELOAD_USER_CANCELLED = "203";
    private static final String LOADING_IN_PROGRESS = "300";
    private static final String LOADING_IN_PROGRESS_WITH_ERRORS = "301";
    private static final String LOAD_SUCCESSFUL = "302";
    private static final String LOAD__USER_CANCELLED = "303";
    private static final String EMPTY = "";

    private static final String ERROR_SEVERITY_LOW = "1";
    private static final String ERROR_SEVERITY_MEDIUM = "2";
    private static final String ERROR_SEVERITY_HIGH = "3";

    public static String getLoadCompletedWithErrors() {
        return LOAD_COMPLETED_WITH_ERRORS;
    }

    public static String getPreloadCompletedWithErrors() {
        return PRELOAD_COMPLETED_WITH_ERRORS;
    }

    public static String getLoadFailed() {
        return LOAD_FAILED;
    }

    public static String getNotYetStarted() {
        return NOT_YET_STARTED;
    }

    public static String getUploadingInProgress() {
        return UPLOADING_IN_PROGRESS;
    }

    public static String getUploadingCompleted() {
        return UPLOADING_COMPLETED;
    }

    public static String getPreloadingInProgress() {
        return PRELOADING_IN_PROGRESS;
    }

    public static String getPreloadingInProgressWithErrors() {
        return PRELOADING_IN_PROGRESS_WITH_ERRORS;
    }

    public static String getPreloadCompleted() {
        return PRELOAD_COMPLETED;
    }

    public static String getPreloadUserCancelled() {
        return PRELOAD_USER_CANCELLED;
    }

    public static String getLoadingInProgress() {
        return LOADING_IN_PROGRESS;
    }

    public static String getLoadingInProgressWithErrors() {
        return LOADING_IN_PROGRESS_WITH_ERRORS;
    }

    public static String getLoadSuccessful() {
        return LOAD_SUCCESSFUL;
    }

    public static String getErrorSeverityLow() {
        return ERROR_SEVERITY_LOW;
    }

    public static String getErrorSeverityMedium() {
        return ERROR_SEVERITY_MEDIUM;
    }

    public static String getErrorSeverityHigh() {
        return ERROR_SEVERITY_HIGH;
    }

}
