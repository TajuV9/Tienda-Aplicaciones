package com.eviden.fct.tiendaaplicaciones.application.mappers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.application.dtos.ProfileResponseDownloadDto;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Download;
import com.eviden.fct.tiendaaplicaciones.transversal.AppConstants;

@Component
public class ProfileResponseDownloadDtoMapper implements ResponseDtoMapper<Download, ProfileResponseDownloadDto> {
	
	@Value("${app.base-url}")
	private String baseUrl;

	@Override
	public ProfileResponseDownloadDto transform(Download entity) {
		
		String imageUrl = entity.getContent().getImage() ? String.format(AppConstants.PATH_CONTENT_ICON, baseUrl, entity.getContent().getId()) : null;
		String fileUrl = entity.getContent().getFileName() != null ? String.format(AppConstants.PATH_CONTENT_FILE, baseUrl, entity.getContent().getId()) : null;
		
		return ProfileResponseDownloadDto.builder()
				.contentId(entity.getContent().getId())
				.contentName(entity.getContent().getName())
				.contentIcon(imageUrl)
				.hasUpdates(entity.getVersion() != entity.getContent().getVersion())
				.downloadLink(fileUrl)
				.build();
		
	}
	
}
