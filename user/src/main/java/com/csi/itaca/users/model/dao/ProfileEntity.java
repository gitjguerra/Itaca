package com.csi.itaca.users.model.dao;

import com.csi.itaca.users.model.Profile;
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
@Table(name = "USR_PROFILE")
public class ProfileEntity implements Profile {

	@Id
	@Column(name = "profile_id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "usr_profile_id_seq")
	@SequenceGenerator(name = "usr_profile_id_seq", sequenceName = "usr_profile_id_seq", allocationSize = 1)
	private Long id;

	@Column(name = "value")
	private String value;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profile_type_id")
	private ProfileTypeEntity profileType;
}
