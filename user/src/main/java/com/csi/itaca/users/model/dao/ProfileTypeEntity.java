package com.csi.itaca.users.model.dao;

import com.csi.itaca.users.model.ProfileType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "USR_PROFILE_TYPE")
public class ProfileTypeEntity implements ProfileType {

	@Id
	@Column(name = "profile_type_id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "usr_profile_type_id_seq")
	@SequenceGenerator(name = "usr_profile_type_id_seq", sequenceName = "usr_profile_type_id_seq", allocationSize = 1)
	private Long id;

	@Column(name = "value")
	private String value;
}
