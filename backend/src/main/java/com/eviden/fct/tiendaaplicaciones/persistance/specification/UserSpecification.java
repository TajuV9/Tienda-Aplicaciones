package com.eviden.fct.tiendaaplicaciones.persistance.specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.domain.Specification;

import com.eviden.fct.tiendaaplicaciones.domain.entities.User;

import jakarta.persistence.criteria.Predicate;

public class UserSpecification {
	public static Specification<User> isLongTermUsere(){
		return (root, query, builder) ->{
			LocalDate date = LocalDate.now().minusYears(1);
			return builder.lessThan(root.get("createdAt"),date);
		};
	}
	
	public static Specification<User> filterBy(Map<String,Object> filters)
	{
		return(root,query,builder) ->
		{
			List<Predicate> predicates = new ArrayList<>();
			if (filters.containsKey("userName")) {
				String userName = filters.get("userName").toString().toLowerCase();
				predicates.add(builder.like(builder.lower(root.get("userName")), "%"+userName+"%"));
			}
			if(filters.containsKey("name")){
				String name = filters.get("name").toString().toLowerCase();
				predicates.add(builder.like(builder.lower(root.get("name")), "%" + name +"%"));
			};
			if(filters.containsKey("lastName")){
				String lastName = filters.get("lastName").toString().toLowerCase();
				predicates.add(builder.like(builder.lower(root.get("lastName")), "%" + lastName +"%"));
			};
			if(filters.containsKey("email")) {
				String email = filters.get("email").toString();
				predicates.add(builder.like(builder.lower(root.get("email")),"%" + email +"%"));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
	
	public static Specification<User> recentlyCreatedUser() {
		return(root,query,builder) ->{
			LocalDate date = LocalDate.now().minusMonths(3);
			return builder.lessThan(root.get("createdAt"), date);
		};
	}
	
	public static Specification<User> recentlyUpdatedUser(){
		return(root,query,builder) ->{
			LocalDate date = LocalDate.now().minusMonths(3);
			return builder.lessThan(root.get("updatedAt"), date);
		};
	}
}
