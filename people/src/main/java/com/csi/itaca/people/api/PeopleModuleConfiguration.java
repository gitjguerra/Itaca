package com.csi.itaca.people.api;

import com.csi.itaca.config.model.ConfigurationBase;
import lombok.*;

@Getter
@Setter
@ToString
public class PeopleModuleConfiguration extends ConfigurationBase {

	private Boolean duplicatePeopleAllowed = false;
	private IndividualLogicalPrimaryKey individualLogicalPrimaryKey = new IndividualLogicalPrimaryKey();
	private CompanyLogicalPrimaryKey companyLogicalPrimaryKey = new CompanyLogicalPrimaryKey();
	
	@Getter
	@Setter
	@ToString
	@NoArgsConstructor
	@EqualsAndHashCode
	public static class IndividualLogicalPrimaryKey {
		private Boolean identificationCodeInLogicalKey = true;
		private Boolean identificationTypeInLogicalKey = false;
		private Boolean externalReferenceCodeInLogicalKey = false;
		private Boolean dateOfBirthInLogicalKey = false;
	}
	
	@Getter
	@Setter
	@ToString
	@NoArgsConstructor
	@EqualsAndHashCode
	public static class CompanyLogicalPrimaryKey {
		private Boolean identificationCodeInLogicalKey = true;
		private Boolean identificationTypeInLogicalKey = false;
	}

}
