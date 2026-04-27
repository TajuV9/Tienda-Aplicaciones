package com.eviden.fct.tiendaaplicaciones.persistance.specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.domain.entities.Content;
import com.eviden.fct.tiendaaplicaciones.domain.filters.ContentFilter;
import com.eviden.fct.tiendaaplicaciones.domain.repositories.ContentSpecificationDefinition;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

@Component
public class ContentSpecification implements ContentSpecificationDefinition {

	@Override
	public Specification<Content> filterBy(Map<String, Object> filters) {
		return (root, _, cb) -> {

			List<Predicate> predicates = new ArrayList<>();
			if (filters.containsKey("name")) {
				String name = filters.get("name").toString().toLowerCase();
				predicates.add(cb.like(cb.lower(root.get("name")), "%" + name + "%"));
			}
			if (filters.containsKey("category")) {
				Join<Object, Object> categoryJoin = root.join("categories", JoinType.INNER);
				predicates.add(
						cb.equal(cb.lower(categoryJoin.get("name")), filters.get("category").toString().toLowerCase()));
			}
			if (filters.containsKey("price")) {
				BigDecimal price = new BigDecimal(filters.get("price").toString());
				predicates.add(cb.equal(root.get("price"), price));
			}
			if (filters.containsKey("developer")) {
				Join<Object, Object> categoryJoin = root.join("developer", JoinType.INNER);
				predicates.add(cb.equal(cb.lower(categoryJoin.get("developer")),
						filters.get("developer").toString().toLowerCase()));
			}
			if (filters.containsKey("minPrice")) {
				BigDecimal price = new BigDecimal(filters.get("minPrice").toString());
				predicates.add(cb.lessThanOrEqualTo(root.get("price"), price));
			}
			if (filters.containsKey("maxPrice")) {
				BigDecimal price = new BigDecimal(filters.get("maxPrice").toString());
				predicates.add(cb.lessThanOrEqualTo(root.get("price"), price));
			}

			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}

	@Override
	public Specification<Content> filterBy(ContentFilter filters) {
		return (root, _, cb) -> {

			List<Predicate> predicates = new ArrayList<>();
			if (filters.getName() != null) {
				String name = filters.getName().toString().toLowerCase();
				predicates.add(cb.like(cb.lower(root.get("name")), "%" + name + "%"));
			}
			if (filters.getCategory() != null) {
				int category = filters.getCategory().ordinal();
				predicates.add(cb.equal(root.get("category"), category));
			}
			if (filters.getState() != null) {
				int state = filters.getState().ordinal();
				predicates.add(cb.equal(root.get("state"), state));
			}

			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}

	@Override
	public Specification<Content> isExpensiveContent() {
		return (root, _, builder) -> {
			BigDecimal price = BigDecimal.valueOf(100);
			return builder.greaterThan(root.get("price"), price);
		};
	}

	@Override
	public Specification<Content> nameContains(String name) {
		return (root, _, builder) -> {
			return builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
		};
	}

	@Override
	public Specification<Content> recentlyUpdated() {
		return (root, _, builder) -> {
			LocalDate date = LocalDate.now().minusWeeks(1);
			return builder.lessThan(root.get("updatedAt"), date);
		};
	}

}
