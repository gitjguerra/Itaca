package com.csi.itaca.people.repository;

import com.csi.itaca.people.model.dao.LanguageEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LanguageRepository extends PagingAndSortingRepository<LanguageEntity, Long>
										,JpaSpecificationExecutor<LanguageEntity> {

}
