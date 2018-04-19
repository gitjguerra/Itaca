package com.csi.itaca.people.service;

import com.csi.itaca.people.api.ErrorConstants;
import com.csi.itaca.people.model.dao.NationalityEntity;
import com.csi.itaca.people.model.dto.NationalityDTO;
import com.csi.itaca.people.repository.NationalityRepository;
import com.csi.itaca.tools.utils.beaner.Beaner;
import com.csi.itaca.tools.utils.jpa.JpaUtils;
import com.csi.itaca.tools.utils.jpa.Order;
import com.csi.itaca.tools.utils.jpa.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import javax.persistence.criteria.Predicate;
import java.util.List;

@Service
public class PeopleNationalitiesBusinessServiceImpl implements PeopleNationalitiesBusinessService {

	@Autowired
	private NationalityRepository nationalityRepository;

	@Autowired
	private PeopleManagementService peopleManagementService;

	@Autowired
	private Beaner beaner;

	@Override
	@Transactional(readOnly = true)
	public List<NationalityDTO> getPeopleNationalities(Long personDetailId) {
			return getPeopleNationalities(personDetailId,null,null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<NationalityDTO> getPeopleNationalities(Long personDetail, Pagination pagination, Order order) {
		Specification<NationalityEntity> spec = (root, query, cb) -> {
			Predicate p = cb.equal(root.get(NationalityEntity.PERSON_DETAIL_ID), personDetail);
			if (order != null && order.getField() != null) {
				if(order.isAscending()){
					query.orderBy(cb.asc(root.get(JpaUtils.getField(NationalityEntity.class, order))));
				} else {
					query.orderBy(cb.desc(root.get(JpaUtils.getField(NationalityEntity.class, order))));
				}
			}

			return p;
		};

		List<? extends NationalityEntity> nationalityEntities = null;
		if (pagination != null) {
			PageRequest pr = new PageRequest(pagination.getPageNo() - 1, pagination.getItemsPerPage());
			nationalityEntities = nationalityRepository.findAll(spec, pr).getContent();
		}
		else {
			nationalityEntities = nationalityRepository.findAll(spec);
		}

		return beaner.transform(nationalityEntities, NationalityDTO.class);
	}

	@Override
	@Transactional
	public boolean deleteNationality(Long idNationality, Errors errTracking) {
		NationalityEntity nationalityEntity = nationalityRepository.findOne(idNationality);
		if (nationalityEntity != null) {
			nationalityRepository.delete(nationalityEntity);
			return true;
		} else {
			errTracking.reject(ErrorConstants.DB_ITEM_NOT_FOUND);
			return false;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public NationalityDTO getNationality(Long idNationality, Errors errTracking) {
		NationalityEntity nationalityEntity = nationalityRepository.findOne(idNationality);
		if (nationalityEntity!=null) {
			return beaner.transform(nationalityEntity, NationalityDTO.class);
		}
		else {
			errTracking.reject(ErrorConstants.DB_ITEM_NOT_FOUND);
			return null;
		}
	}

	@Override
	@Transactional
	public NationalityDTO saveOrUpdateNationality(NationalityDTO NationalityDTO, Errors errTracking) {

		NationalityEntity nationalityEntityToSave = beaner.transform(NationalityDTO, NationalityEntity.class);
		NationalityEntity nationalitySavedEntity = nationalityRepository.save(nationalityEntityToSave);
		return beaner.transform(nationalitySavedEntity, NationalityDTO.class);

	}


	@Override
	@Transactional(readOnly = true)
	public Long countNationalities(Long personDetailId) {

	Specification<NationalityEntity> spec = (root, query, cb) -> {
			Predicate p = null;
			if (personDetailId != null) {
				p = cb.equal(root.get(NationalityEntity.PERSON_DETAIL_ID), personDetailId);
			}
			return p;
		};
		return nationalityRepository.count(spec);

	}


}