package com.csi.itaca.people.model.dto;

import com.csi.itaca.common.model.BaseModelImpl;
import com.csi.itaca.common.model.dto.CountryDTO;
import com.csi.itaca.people.model.Identification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class IdentificationDTO extends BaseModelImpl implements Identification {

	private Long personDetailId;
	
	private IDTypeDTO idType;
	
	private CountryDTO country;
	
	private String identificationCode;
	
	private LocalDate issueDate;

}
