package com.csi.itaca.people.model.dto;

import com.csi.itaca.people.model.Language;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LanguageDTO implements Language {
	
	private Long id;
	
	private String i18nKey;

}
