package com.csi.itaca.tools.utils.jpa;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import javax.persistence.criteria.Predicate;
import java.lang.reflect.Field;

/**
 * JPA Utility class.
 * @author bboothe
 */
public class JpaUtils {

    /**
     * Builds the pagination request.
     * @param pagination
     * @return
     */
    public static PageRequest buildPageRequest(Pagination pagination) {
        PageRequest pageRequest = null;

        if (pagination != null) {
            pageRequest = new PageRequest(pagination.getPageNo() - 1, pagination.getItemsPerPage());
        }

        return pageRequest;
    }

    /**
     * Applies the given order to the specification.
     * @param entityType the entity class.
     * @param order the order to apply.
     * @param spec the specification.
     * @param <T> entity type.
     * @return
     */
    public static <T> Specification<T> applyOrder(Class<T> entityType, Order order, Specification<T> spec) {
        if (order != null && order.getField() != null && !order.getField().isEmpty()) {
            Specification<T> spec1 = (root, query, cb) -> {
                Predicate p = null;
                if (order != null && order.getField() != null) {
                    if (order.isAscending()) {
                        query.orderBy(cb.asc(root.get(getField(entityType, order))));
                    } else {
                        query.orderBy(cb.desc(root.get(getField(entityType, order))));
                    }
                }
                return p;
            };
            spec = Specifications.where(spec).and(spec1);
        }
        return spec;
    }


    public static <T> String getField(Class<T> entityType, Order order) {
        Field[] fields = entityType.getFields();
        String[] searchFields = order.getField().split("\\.");
        String orderField;
        String value = "";
        if (searchFields.length > 0) {
            orderField = searchFields[0];
            for (Field field : fields) {
                if (field.getName().replace("_", "").toUpperCase().contains(orderField.toUpperCase())) {
                    try {
                        value = (String) field.get(field.getName());
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return value;
    }
}
