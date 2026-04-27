package com.eviden.fct.tiendaaplicaciones.domain.entities;

import java.sql.Date;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "content_id", nullable = false)
	private Content content;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(nullable = false)
	private Date createdAt;
	
	@Column(nullable = false)
	private Date updatedAt;

	@NotBlank
	@Length(max = 500)
	@Column(nullable = false)
	private String review;
	
	@Length(max = 500)
	@Column(nullable = true)
	private String answer;
	
	@NotNull
	@Min(1)
	@Max(5)
	@Column(nullable = true)
	private Integer rating;
}
