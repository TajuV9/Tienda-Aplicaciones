package com.eviden.fct.tiendaaplicaciones.application.mappers;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.application.dtos.ContentResponseBasicInformationDto;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Content;
import com.eviden.fct.tiendaaplicaciones.domain.entities.ContentState;
import com.eviden.fct.tiendaaplicaciones.transversal.AppConstants;

@Component
public class ContentResponseBasicInformationDtoMapper implements ResponseDtoMapper<Content, ContentResponseBasicInformationDto> {
	
	@Value("${app.base-url}")
	private String baseUrl;

	@Override
	public ContentResponseBasicInformationDto transform(Content entity) {
		
		String imageUrl = entity.getImage() ? String.format(AppConstants.PATH_CONTENT_ICON, baseUrl, entity.getId()) : null;
		Double roundedRating = entity.getRating() != null ? new BigDecimal(entity.getRating()).setScale(1, RoundingMode.HALF_UP).doubleValue() : 0.0;
		
		return ContentResponseBasicInformationDto.builder()
				.id(entity.getId())
				.title(entity.getName())
				.imageUrl(imageUrl)
				.published(entity.getState() == ContentState.PUBLISHED)
				.rating(roundedRating)
				.build();
		
	}
	
}
