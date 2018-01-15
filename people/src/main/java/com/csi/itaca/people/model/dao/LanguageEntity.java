package com.csi.itaca.people.model.dao;


import com.csi.itaca.people.model.Language;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "per_language")
public class LanguageEntity implements Language {
	
	public static final String ID 		= "id";
	public static final String I18N_KEY = "i18nKey";
	
	@Id
	@Column(name="language_id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "per_language_seq")
	@SequenceGenerator(name = "per_language_seq", sequenceName = "per_language_seq", allocationSize = 1)
	private Long id;
	
	@Column(name="i18n_Key")
	private String i18nKey;

}
