package com.eviden.fct.tiendaaplicaciones.application.mappers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.application.dtos.ContentMediaResponseInformationDto;
import com.eviden.fct.tiendaaplicaciones.domain.entities.ContentMedia;
import com.eviden.fct.tiendaaplicaciones.transversal.AppConstants;

@Component
public class ContentMediaResponseInformationDtoMapper implements ResponseDtoMapper<ContentMedia, ContentMediaResponseInformationDto> {
	
	@Value("${app.base-url}")
	private String baseUrl;

	@Override
	public ContentMediaResponseInformationDto transform(ContentMedia entity) {
		
		String url = String.format(AppConstants.PATH_CONTENT_MEDIA, baseUrl, entity.getId());
		
		return ContentMediaResponseInformationDto.builder()
				.id(entity.getId())
				.type(entity.getType())
				.url(url)
				.build();
		
	}
	
}
