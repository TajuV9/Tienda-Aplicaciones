package com.eviden.fct.tiendaaplicaciones.domain.entities;

import java.sql.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Download {

	@EmbeddedId
	private DownloadId id;
	
	@ManyToOne
	@MapsId("userId")
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@ManyToOne
	@MapsId("contentId")
	@JoinColumn(name = "content_id", nullable = false)
	private Content content;
	
	@NotNull
	@Column(nullable = false)
	private Long version;
	
	@Column(nullable = false)
	private Date createdAt;
	
	@Column(nullable = false)
	private Date updatedAt;
	
	public void setContentId(Long contentId) {
		if (this.id == null) this.id = new DownloadId();
		this.id.setContentId(contentId);
	}

}
