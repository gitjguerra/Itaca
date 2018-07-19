package com.csi.itaca.load.utils;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class Constants {

    private static final String LOAD_COMPLETED_WITH_ERRORS = "-300";
    private static final String LOAD_COMPLETED_WITH_ERRORS_DESC = "Load completed with errors.";

    private static final String PRELOAD_COMPLETED_WITH_ERRORS = "-200";
    private static final String PRELOAD_COMPLETED_WITH_ERRORS_DESC = "Preload completed with errors.";

    private static final String PRELOAD_FILE_TYPE_NOT = "-201";
    private static final String PRELOAD_FILE_TYPE_NOT_DESC = "Preload file type not recognised.";

    private static final String UPLOAD_LOAD_FAILED = "-1";
    private static final String UPLOAD_LOAD_FAILED_DESC = "Upload failed.";

    private static final String NOT_YET_STARTED = "0";
    private static final String NOT_YET_STARTED_DESC = "Not yet started.";

    private static final String UPLOADING_IN_PROGRESS = "100";
    private static final String UPLOADING_IN_PROGRESS_DESC = "Uploading in progress.";

    private static final String UPLOADING_COMPLETED = "101";
    private static final String UPLOADING_COMPLETED_DESC = "Uploading completed.";

    private static final String PRELOADING_IN_PROGRESS = "200";
    private static final String PRELOADING_IN_PROGRESS_DESC = "Preloading in progress.";

    private static final String PRELOADING_IN_PROGRESS_WITH_ERRORS = "201";
    private static final String PRELOADING_IN_PROGRESS_WITH_ERRORS_DESC = "Preloading in progress (errors detected).";

    private static final String PRELOAD_COMPLETED = "202";
    private static final String PRELOAD_COMPLETED_DESC = "Preload completed.";

    private static final String PRELOAD_USER_CANCELLED = "203";
    private static final String PRELOAD_USER_CANCELLED_DESC = "Preload user cancelled.";

    private static final String LOADING_IN_PROGRESS = "300";
    private static final String LOADING_IN_PROGRESS_DESC = "Loading in progress.";

    private static final String LOADING_IN_PROGRESS_WITH_ERRORS = "301";
    private static final String LOADING_IN_PROGRESS_WITH_ERRORS_DESC = "loading in progress (errors detected).";

    private static final String LOAD_SUCCESSFUL = "302";
    private static final String LOAD_SUCCESSFUL_DESC = "Load successful.";

    private static final String LOAD__USER_CANCELLED = "303";
    private static final String LOAD__USER_CANCELLED_DESC = "Load user cancelled";

    private static final String EMPTY = "";
    private static final String JOB_NAME = "preload-job";
    private static final String STEP_NAME = "preload-data-step";

    private static final String ERROR_SEVERITY_LOW = "1";
    private static final String ERROR_SEVERITY_MEDIUM = "2";
    private static final String ERROR_SEVERITY_HIGH = "3";

    private static final String MAX_LENGTH_MSSG_ERROR = "200";

    public static String getLoadCompletedWithErrors() {
        return LOAD_COMPLETED_WITH_ERRORS;
    }

    public static String getLoadCompletedWithErrorsDesc() {
        return LOAD_COMPLETED_WITH_ERRORS_DESC;
    }

    public static String getPreloadCompletedWithErrors() {
        return PRELOAD_COMPLETED_WITH_ERRORS;
    }

    public static String getPreloadCompletedWithErrorsDesc() {
        return PRELOAD_COMPLETED_WITH_ERRORS_DESC;
    }

    public static String getPreloadFileTypeNot() {
        return PRELOAD_FILE_TYPE_NOT;
    }

    public static String getPreloadFileTypeNotDesc() {
        return PRELOAD_FILE_TYPE_NOT_DESC;
    }

    public static String getUploadLoadFailed() {
        return UPLOAD_LOAD_FAILED;
    }

    public static String getUploadLoadFailedDesc() {
        return UPLOAD_LOAD_FAILED_DESC;
    }

    public static String getNotYetStarted() {
        return NOT_YET_STARTED;
    }

    public static String getNotYetStartedDesc() {
        return NOT_YET_STARTED_DESC;
    }

    public static String getUploadingInProgress() {
        return UPLOADING_IN_PROGRESS;
    }

    public static String getUploadingInProgressDesc() {
        return UPLOADING_IN_PROGRESS_DESC;
    }

    public static String getUploadingCompleted() {
        return UPLOADING_COMPLETED;
    }

    public static String getUploadingCompletedDesc() {
        return UPLOADING_COMPLETED_DESC;
    }

    public static String getPreloadingInProgress() {
        return PRELOADING_IN_PROGRESS;
    }

    public static String getPreloadingInProgressDesc() {
        return PRELOADING_IN_PROGRESS_DESC;
    }

    public static String getPreloadingInProgressWithErrors() {
        return PRELOADING_IN_PROGRESS_WITH_ERRORS;
    }

    public static String getPreloadingInProgressWithErrorsDesc() {
        return PRELOADING_IN_PROGRESS_WITH_ERRORS_DESC;
    }

    public static String getPreloadCompleted() {
        return PRELOAD_COMPLETED;
    }

    public static String getPreloadCompletedDesc() {
        return PRELOAD_COMPLETED_DESC;
    }

    public static String getPreloadUserCancelled() {
        return PRELOAD_USER_CANCELLED;
    }

    public static String getPreloadUserCancelledDesc() {
        return PRELOAD_USER_CANCELLED_DESC;
    }

    public static String getLoadingInProgress() {
        return LOADING_IN_PROGRESS;
    }

    public static String getLoadingInProgressDesc() {
        return LOADING_IN_PROGRESS_DESC;
    }

    public static String getLoadingInProgressWithErrors() {
        return LOADING_IN_PROGRESS_WITH_ERRORS;
    }

    public static String getLoadingInProgressWithErrorsDesc() {
        return LOADING_IN_PROGRESS_WITH_ERRORS_DESC;
    }

    public static String getLoadSuccessful() {
        return LOAD_SUCCESSFUL;
    }

    public static String getLoadSuccessfulDesc() {
        return LOAD_SUCCESSFUL_DESC;
    }

    public static String getLoad_userCancelled() {
        return LOAD__USER_CANCELLED;
    }

    public static String getLoad_userCancelledDesc() {
        return LOAD__USER_CANCELLED_DESC;
    }

    public static String getEMPTY() {
        return EMPTY;
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

    public static String getMaxLengthMssgError() {
        return MAX_LENGTH_MSSG_ERROR;
    }

    public static String getJobName() {
        return JOB_NAME;
    }

    public static String getStepName() {
        return STEP_NAME;
    }
}
