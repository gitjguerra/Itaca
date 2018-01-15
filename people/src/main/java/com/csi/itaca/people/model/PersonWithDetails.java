package com.csi.itaca.people.model;

import java.util.List;

public interface PersonWithDetails extends Person {
	
	List<? extends PersonDetail> getDetails();
	
}
