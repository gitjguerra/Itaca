package com.csi.itaca.users.model.dao;

import com.csi.itaca.users.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "usr_user")
public class UserEntity implements User {

	// Fields used by the service implementations.
	public static final String ID 				= "id";
	public static final String USERNAME 		= "username";
	public static final String PASSWORD 		= "password";
	public static final String USER_LANGUAGE 	= "language_id";
	public static final String DESCRIPTION 		= "description";
	public static final String BLOCKED 			= "blocked";

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "usr_user_id_seq")
	@SequenceGenerator(name = "usr_user_id_seq", sequenceName = "usr_user_id_seq", allocationSize = 1)
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "company_code")
	private String companyCode;

	@Column(name = "description")
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "language_id")
	private UserLanguageEntity userLanguage;

	@Column(name = "company_start_date")
	private LocalDate companyStartDate;

	@Column(name = "company_end_date")
	private LocalDate companyEndDate;

	@Column(name = "email")
	private String email;

	@Column(name = "blocked")
	private boolean blocked;

	@Column(name = "blocked_date")
	private LocalDate blockedDate;

}
