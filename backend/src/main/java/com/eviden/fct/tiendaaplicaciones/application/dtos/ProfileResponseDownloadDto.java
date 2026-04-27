package com.eviden.fct.tiendaaplicaciones.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileResponseDownloadDto {
	
	private Long contentId;
	
	private String contentName;
	
	private String contentIcon;
	
	private Boolean hasUpdates;
	
	private String downloadLink;
	
}
