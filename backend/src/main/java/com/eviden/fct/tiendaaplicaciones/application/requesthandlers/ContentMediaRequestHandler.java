package com.eviden.fct.tiendaaplicaciones.application.requesthandlers;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eviden.fct.tiendaaplicaciones.application.dtos.GenericSuccessDto;
import com.eviden.fct.tiendaaplicaciones.domain.usecases.ContentMediaDeleteUseCase;
import com.eviden.fct.tiendaaplicaciones.domain.usecases.ContentMediaDownloadUseCase;
import com.eviden.fct.tiendaaplicaciones.transversal.AppConstants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContentMediaRequestHandler {

	private final ContentMediaDownloadUseCase contentMediaDownloadUseCase;
	private final ContentMediaDeleteUseCase contentMediaDeleteUseCase;
	
	public ResponseEntity<Resource> handleMediaDownloadRequest(Long id) throws Exception {
		return contentMediaDownloadUseCase.invoke(id);
	}
	
	public GenericSuccessDto handleMediaDeleteRequest(Long id) throws Exception {
		contentMediaDeleteUseCase.invoke(id);
		return new GenericSuccessDto(AppConstants.SUCCESS_CONTENT_MEDIA_DELETED);
	}

}
