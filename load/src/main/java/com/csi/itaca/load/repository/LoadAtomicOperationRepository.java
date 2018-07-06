package com.csi.itaca.load.repository;

import com.csi.itaca.load.model.LoadAtomicOperation;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Robert on 19/06/2018.
 */
public interface LoadAtomicOperationRepository extends PagingAndSortingRepository<LoadAtomicOperation, Long>, JpaSpecificationExecutor<LoadAtomicOperation>  {


}
