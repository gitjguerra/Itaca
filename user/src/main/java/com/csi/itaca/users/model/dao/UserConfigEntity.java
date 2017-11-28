package com.csi.itaca.users.model.dao;

import com.csi.itaca.users.model.UserConfig;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "USR_USER_CONFIG")
public class UserConfigEntity implements UserConfig {

	// Fields used by the service implementations.
	public static final String USER 		= "user_id";

	@Id
	@Column(name = "user_config_id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "usr_user_config_id_seq")
	@SequenceGenerator(name = "usr_user_config_id_seq", sequenceName = "usr_user_config_id_seq", allocationSize = 1)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profile_id")
	private ProfileEntity profile;

}
