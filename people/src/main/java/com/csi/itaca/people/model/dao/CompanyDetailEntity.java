package com.csi.itaca.people.model.dao;

import com.csi.itaca.people.model.CompanyDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("company")
public class CompanyDetailEntity extends PersonDetailEntity implements CompanyDetail {
	
}
