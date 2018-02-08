package com.csi.itaca.people.api;

import com.csi.itaca.common.GlobalErrorConstants;

public class ErrorConstants extends GlobalErrorConstants {

    public static final String VALIDATION_PERSON_TYPE_INVALID       = "validation.person_type.invalid";
    public static final String VALIDATION_DATE_OF_BIRTH_REQUIRED    = "validation.dateOfBirth.required";
    public static final String VALIDATION_NO_FILTER_PROVIDED        = "validation.no.filter.provided";

    public static final String DB_PERSON_NOT_FOUND                  = "db.personNotFound";
    public static final String DB_DUPLICATE_PERSON_NOT_ALLOWED      = "db.duplicate.person.not.allowed";
    public static final String DB_PERSON_WITH_NO_DETAILS            = "db.person.with.no.details";

    public static final String DB_PERSON_DETAIL_NOT_FOUND           = "db.personDetailNotFound";

}
