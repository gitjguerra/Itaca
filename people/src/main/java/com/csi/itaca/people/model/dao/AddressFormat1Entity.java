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

	public static final String ID 						= "addressId";
	public static final String id_poblacion 			= "idpoblacion";
	public static final String id_cod_postal 			= "idcodpostal";
	public static final String id_type_via 	            = "idtypevia";
	public static final String nombre_via 				= "nombrevia";
	public static final String numero_via 				= "numerovia";
	public static final String complemento  			= "complementos";


	@Id
	@Column(name="id_address")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_TYPE_ADDRESS")
	@SequenceGenerator(name = "SEQ_TYPE_ADDRESS", sequenceName = "SEQ_TYPE_ADDRESS", allocationSize = 1)
	private Long addressId;

	@Column(name="id_poblacion")
	private String idpoblacion;

	@Column(name="id_cod_postal")
	private String idcodpostal;

	//@Column
	//private Long publica;

	@Column(name="id_type_via")
	private String idtypevia;

	@Column(name="nombre_via")
	private String nombrevia;


	@Column(name="numero_via")
	private String numerovia;

	@Column(name="complemento")
	private String complementos;

	@Override
	public String getidpoblacion() {
		return idpoblacion;
	}

	@Override
	public String getidcodpostal() {
		return idcodpostal;
	}

	@Override
	public String getidtypevia() {
		return idtypevia;
	}

	@Override
	public String getnombrevia() {
		return nombrevia;
	}

	@Override
	public String getnumerovia() {
		return numerovia;
	}

	@Override
	public String getcomplementos() {
		return complementos;
	}
}
