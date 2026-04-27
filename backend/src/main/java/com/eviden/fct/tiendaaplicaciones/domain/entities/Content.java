package com.eviden.fct.tiendaaplicaciones.domain.entities;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Content {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Length(min = 3, max = 50)
	@Column(name = "content_name", length = 50, nullable = false)
	private String name;
	
	@NotBlank
	@Length(max = 1000)
	private String description;

	@Column(nullable = false)
	private Boolean image;
	
	@Column(nullable = true)
	private String fileName;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "developer_id", nullable = false)
	private User developer;

	@NotNull
	@Column(nullable = false)
	private Category category;

	@NotNull
	@Column(nullable = false)
	private BigDecimal price;

	@OneToMany(mappedBy = "content", cascade = CascadeType.ALL)
	private List<Download> downloads;
	
	@OneToMany(mappedBy = "content", cascade = CascadeType.ALL)
	private List<ContentMedia> media;

	@Column(nullable = false)
	private Date createdAt;

	@Column(nullable = false)
	private Date updatedAt;
	
	@Formula("(SELECT AVG(r.rating) FROM Review r WHERE r.content_id = id)")
	private Double rating;
	
	@Column
	private ContentState state;
	
	@NotNull
	@Column(nullable = false)
	private Long version;
	
	@Formula("(SELECT count(*) FROM download d WHERE d.content_id = id)")
	private Long timesDownloaded;
	
}
