package com.eviden.fct.tiendaaplicaciones.application.requesthandlers;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eviden.fct.tiendaaplicaciones.application.dtos.ContentMediaResponseInformationDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.ContentRequestCreateDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.ContentRequestFilterDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.ContentRequestUpdateDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.ContentResponseCompleteInformationDto;
import com.eviden.fct.tiendaaplicaciones.application.dtos.GenericSuccessDto;
import com.eviden.fct.tiendaaplicaciones.application.mappers.ContentMediaResponseInformationDtoMapper;
import com.eviden.fct.tiendaaplicaciones.application.mappers.ContentRequestCreateDtoMapper;
import com.eviden.fct.tiendaaplicaciones.application.mappers.ContentRequestFilterDtoMapper;
import com.eviden.fct.tiendaaplicaciones.application.mappers.ContentRequestUpdateDtoMapper;
import com.eviden.fct.tiendaaplicaciones.application.mappers.ContentResponseCompleteInformationDtoMapper;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Content;
import com.eviden.fct.tiendaaplicaciones.domain.entities.ContentMedia;
import com.eviden.fct.tiendaaplicaciones.domain.filters.ContentFilter;
import com.eviden.fct.tiendaaplicaciones.domain.usecases.ContentCreateUseCase;
import com.eviden.fct.tiendaaplicaciones.domain.usecases.ContentDeleteUseCase;
import com.eviden.fct.tiendaaplicaciones.domain.usecases.ContentDownloadIconUseCase;
import com.eviden.fct.tiendaaplicaciones.domain.usecases.ContentDownloadFileUseCase;
import com.eviden.fct.tiendaaplicaciones.domain.usecases.ContentGetUseCase;
import com.eviden.fct.tiendaaplicaciones.domain.usecases.ContentMediaUploadUseCase;
import com.eviden.fct.tiendaaplicaciones.domain.usecases.ContentGetFilteredUseCase;
import com.eviden.fct.tiendaaplicaciones.domain.usecases.ContentUpdateUseCase;
import com.eviden.fct.tiendaaplicaciones.domain.usecases.ContentUploadIconUseCase;
import com.eviden.fct.tiendaaplicaciones.domain.usecases.ContentUploadFileUseCase;
import com.eviden.fct.tiendaaplicaciones.transversal.AppConstants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContentRequestHandler {

	private final ContentCreateUseCase contentCreateUseCase;
	private final ContentUpdateUseCase contentUpdateUseCase;
	private final ContentDeleteUseCase contentDeleteUseCase;
	private final ContentGetUseCase contentGetUseCase;
	private final ContentGetFilteredUseCase contentGetFilteredUseCase;
	private final ContentUploadFileUseCase contentUploadFileUseCase;
	private final ContentDownloadFileUseCase contentDownloadFileUseCase;
	private final ContentUploadIconUseCase contentUploadIconUseCase;
	private final ContentDownloadIconUseCase contentDownloadIconUseCase;
	private final ContentMediaUploadUseCase contentMediaUploadUseCase;
	
	private final ContentRequestCreateDtoMapper contentRequestCreateDtoMapper;
	private final ContentRequestUpdateDtoMapper contentRequestUpdateDtoMapper;
	private final ContentRequestFilterDtoMapper contentRequestFilterDtoMapper;
	private final ContentResponseCompleteInformationDtoMapper contentResponseCompleteInformationDtoMapper;
	private final ContentMediaResponseInformationDtoMapper contentMediaResponseInformationDtoMapper;
	
	
	
	public ContentResponseCompleteInformationDto handleCreateRequest(ContentRequestCreateDto data) throws Exception {
		Content content = contentRequestCreateDtoMapper.transform(data);
		Content created = contentCreateUseCase.invoke(content);
		return contentResponseCompleteInformationDtoMapper.transform(created);
	}
	
	public ContentResponseCompleteInformationDto handleUpdateRequest(Long id, ContentRequestUpdateDto data) throws Exception {
		Content content = contentRequestUpdateDtoMapper.transform(data);
		content.setId(id);
		Content updated = contentUpdateUseCase.invoke(content);
		return contentResponseCompleteInformationDtoMapper.transform(updated);
	}
	
	public GenericSuccessDto handleDeleteRequest(Long id) throws Exception {	
		contentDeleteUseCase.invoke(id);
		return new GenericSuccessDto(AppConstants.SUCCESS_CONTENT_DELETED);
	}
	
	public ContentResponseCompleteInformationDto handleGetRequest(Long id) throws Exception {	
		Content content = contentGetUseCase.invoke(id);
		return contentResponseCompleteInformationDtoMapper.transform(content);
	}
	
	public List<ContentResponseCompleteInformationDto> handleGetWithFiltersRequest(ContentRequestFilterDto dto) throws Exception {	
		ContentFilter filter = contentRequestFilterDtoMapper.transform(dto);
		List<Content> content = contentGetFilteredUseCase.invoke(filter);
		return content.stream().map(contentResponseCompleteInformationDtoMapper::transform).toList();
	}
	
	public GenericSuccessDto handleFileUploadRequest(Long id, MultipartFile file) throws Exception {
		contentUploadFileUseCase.invoke(id, file);
		return new GenericSuccessDto(AppConstants.SUCCESS_CONTENT_UPDATED);
	}
	
	public ResponseEntity<Resource> handleFileDownloadRequest(Long id) throws Exception {
		return contentDownloadFileUseCase.invoke(id);
	}

	public GenericSuccessDto handleIconUploadRequest(Long id, MultipartFile file) throws Exception {
		contentUploadIconUseCase.invoke(id, file);
		return new GenericSuccessDto(AppConstants.SUCCESS_CONTENT_UPDATED);
	}
	
	public ResponseEntity<Resource> handleIconDownloadRequest(Long id) throws Exception {
		return contentDownloadIconUseCase.invoke(id);
	}
	
	public ContentMediaResponseInformationDto handleMediaUploadRequest(Long id, MultipartFile file) throws Exception {
		ContentMedia media = contentMediaUploadUseCase.invoke(id, file);
		return contentMediaResponseInformationDtoMapper.transform(media);
	}

}
