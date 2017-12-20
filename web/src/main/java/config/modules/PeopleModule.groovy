package config.modules

import com.csi.itaca.people.api.PeopleModuleConfiguration

config PeopleModuleConfiguration.class como (
	duplicatePeopleAllowed: false,
	individualLogicalPrimaryKey:
		(PeopleModuleConfiguration.IndividualLogicalPrimaryKey) [identificationCodeInLogicalKey: true,
																 identificationTypeInLogicalKey: true,
																 externalReferenceCodeInLogicalKey: false,
																 dateOfBirthInLogicalKey: false],
	companyLogicalPrimaryKey:
		(PeopleModuleConfiguration.CompanyLogicalPrimaryKey) [identificationCodeInLogicalKey: true,
															  identificationTypeInLogicalKey: true]
)
