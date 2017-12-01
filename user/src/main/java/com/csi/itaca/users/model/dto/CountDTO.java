package com.csi.itaca.users.model.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents totals form the database.
 * @author bboothe
 */

@Setter
@Getter
@NoArgsConstructor
public class CountDTO {

    // Fields
    public static final String USER_COUNT = "userCount";

    /**
     * Number of users based on a filter that was previously applied.
     */
    private Long userCount;
}
