package com.csi.itaca.people.model.dao;

import com.csi.itaca.people.model.AddressFormat1;
import com.csi.itaca.people.model.PersonWithDetails;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "dir_format_1")
//@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name="person_type", discriminatorType=DiscriminatorType.STRING)
public class AddressFormat1Entity implements AddressFormat1 {

	public static final String ID 						= "AddressId";
	public static final String id_poblacion 			= "Idpoblacion";
	public static final String id_cod_postal 			= "Idcodpostal";
	public static final String id_type_via 	            = "Idtypevia";
	public static final String nombre_via 				= "Nombrevia";
	public static final String numero_via 				= "Numerovia";
	public static final String complemento  			= "Complementos";


	@Id
	@Column(name="id_address")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_TYPE_ADDRESS")
	@SequenceGenerator(name = "SEQ_TYPE_ADDRESS", sequenceName = "SEQ_TYPE_ADDRESS", allocationSize = 1)
	private Long AddressId;

	@Column(name="id_poblacion")
	public String Idpoblacion;

	@Column(name="id_cod_postal")
	private String Idcodpostal;

	//@Column
	//private Long publica;

	@Column(name="id_type_via")
	private String Idtypevia;

	@Column(name="nombre_via")
	private String Nombrevia;


	@Column(name="numero_via")
	private String Numerovia;

	@Column(name="complemento")
	private String Complementos;


	@Override
	public String getIdpoblacion() {
		return Idpoblacion;
	}


	@Override
	public String getIdcodpostal() {
		return Idcodpostal;
	}

	@Override
	public String getIdtypevia() {
		return Idtypevia;
	}

	@Override
	public String getNombrevia() {
		return Nombrevia;
	}

	@Override
	public String getNumerovia() {
		return Numerovia;
	}

	@Override
	public String getComplementos() {
		return Complementos;
	}



}
