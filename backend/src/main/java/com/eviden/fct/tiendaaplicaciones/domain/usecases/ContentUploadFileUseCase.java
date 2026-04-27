package com.eviden.fct.tiendaaplicaciones.domain.usecases;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.eviden.fct.tiendaaplicaciones.domain.appservices.AuthenticationApplicationService;
import com.eviden.fct.tiendaaplicaciones.domain.appservices.FileType;
import com.eviden.fct.tiendaaplicaciones.domain.appservices.FilesApplicationService;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Content;
import com.eviden.fct.tiendaaplicaciones.domain.entities.ContentState;
import com.eviden.fct.tiendaaplicaciones.domain.entities.User;
import com.eviden.fct.tiendaaplicaciones.domain.services.ContentDomainService;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.ConflictException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.ForbiddenException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.NotFoundException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.UnauthorizedException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ContentUploadFileUseCase {

	private final FilesApplicationService filesService;
	private final AuthenticationApplicationService authService;
	private final ContentDomainService contentService;
	
	public void invoke(Long contentId, MultipartFile file) throws NotFoundException, ForbiddenException, ConflictException, UnauthorizedException, IOException {
		
		User user = authService.getAuthenticatedUser().orElseThrow(UnauthorizedException::new);
		Content content = contentService.get(contentId);
		
		if (content.getState() == ContentState.DELETED) throw new NotFoundException();
		if (!content.getDeveloper().getId().equals(user.getId())) throw new ForbiddenException();
		
		// Uploads file
		String fileName = file.getOriginalFilename();
		filesService.uploadFile(FileType.CONTENT_FILE, contentId, file);
		
		// Sets data
		Content changes = new Content();
		changes.setVersion(content.getVersion() + 1);
		changes.setFileName(fileName);
		changes.setState(ContentState.PUBLISHED);
		contentService.update(contentId, changes);
		
	}
	
}
