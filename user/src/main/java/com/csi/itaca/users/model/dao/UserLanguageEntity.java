package com.csi.itaca.users.model.dao;

import com.csi.itaca.users.model.UserLanguage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "usr_language")
public class UserLanguageEntity implements UserLanguage {

	public UserLanguageEntity(String code) {
		this.code = code;
	}

	@Id
	@Column(name = "language_id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "usr_language_id_seq")
	@SequenceGenerator(name = "usr_language_id_seq", sequenceName = "usr_language_id_seq", allocationSize = 1)
	private Long id;

	@Column(name = "code")
	private String code;

	@Column(name = "value")
	private String value;

}