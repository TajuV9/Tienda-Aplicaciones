package com.eviden.fct.tiendaaplicaciones.application.mappers;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.eviden.fct.tiendaaplicaciones.application.dtos.ContentResponseCompleteInformationDto;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Content;
import com.eviden.fct.tiendaaplicaciones.domain.entities.ContentState;
import com.eviden.fct.tiendaaplicaciones.transversal.AppConstants;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ContentResponseCompleteInformationDtoMapper implements ResponseDtoMapper<Content, ContentResponseCompleteInformationDto> {
	
	@Value("${app.base-url}")
	private String baseUrl;
	
	private final ContentMediaResponseInformationDtoMapper contentMediaResponseInformationDtoMapper;

	@Override
	public ContentResponseCompleteInformationDto transform(Content entity) {
		
		String imageUrl = entity.getImage() ? String.format(AppConstants.PATH_CONTENT_ICON, baseUrl, entity.getId()) : null;
		String fileUrl = entity.getFileName() != null ? String.format(AppConstants.PATH_CONTENT_FILE, baseUrl, entity.getId()) : null;
		Double roundedRating = entity.getRating() != null ? new BigDecimal(entity.getRating()).setScale(1, RoundingMode.HALF_UP).doubleValue() : 0.0;

		return ContentResponseCompleteInformationDto.builder()
				.id(entity.getId())
				.title(entity.getName())
				.description(entity.getDescription())
				.imageUrl(imageUrl)
				.downloadUrl(fileUrl)
				.creatorName(String.format("%s %s", entity.getDeveloper().getName(), entity.getDeveloper().getLastName()))
				.category(entity.getCategory())
				.rating(roundedRating)
				.downloads(entity.getTimesDownloaded())
				.published(entity.getState() == ContentState.PUBLISHED)
				.media(entity.getMedia().stream().map(contentMediaResponseInformationDtoMapper::transform).toList())
				.build();
		
	}
	
}
