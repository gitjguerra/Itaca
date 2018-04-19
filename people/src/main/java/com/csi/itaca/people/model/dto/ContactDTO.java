package com.csi.itaca.people.model.dto;

import com.csi.itaca.people.model.Contact;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class ContactDTO implements Contact {

	private Long id;
	
	@NotNull
	private Long contactType;
	
	@Size(max=100)
	@NotNull
	private String valueContact;
	
	private Long idAddress;
	
	private Long personDetailId;

}
