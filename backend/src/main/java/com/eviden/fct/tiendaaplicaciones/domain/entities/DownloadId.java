package com.eviden.fct.tiendaaplicaciones.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DownloadId {

	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "content_id")
	private Long contentId;
	
	
}
