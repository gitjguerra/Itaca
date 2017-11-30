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
@Table(name = "usr_user_config")
public class UserConfigEntity implements UserConfig {

	public static final String USER_TABLE 		= "user";

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
