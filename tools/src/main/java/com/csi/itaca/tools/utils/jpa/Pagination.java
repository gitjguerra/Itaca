package com.csi.itaca.tools.utils.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Bean to represent pagination.
 * @author bboothe
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Pagination {

    /** The current page number. */
    private int pageNo;

    /** Number of items per page.*/
    private int itemsPerPage;

    public Pagination(int pageNo, int itemsPerPage) {
        this.pageNo = pageNo;
        this.itemsPerPage = itemsPerPage;
    }
}
