package com.eviden.fct.tiendaaplicaciones.domain.usecases;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.eviden.fct.tiendaaplicaciones.domain.appservices.AuthenticationApplicationService;
import com.eviden.fct.tiendaaplicaciones.domain.appservices.FilesApplicationService;
import com.eviden.fct.tiendaaplicaciones.domain.appservices.ImageType;
import com.eviden.fct.tiendaaplicaciones.domain.appservices.VideoType;
import com.eviden.fct.tiendaaplicaciones.domain.entities.Content;
import com.eviden.fct.tiendaaplicaciones.domain.entities.ContentMedia;
import com.eviden.fct.tiendaaplicaciones.domain.entities.ContentMediaType;
import com.eviden.fct.tiendaaplicaciones.domain.entities.ContentState;
import com.eviden.fct.tiendaaplicaciones.domain.entities.User;
import com.eviden.fct.tiendaaplicaciones.domain.services.ContentDomainService;
import com.eviden.fct.tiendaaplicaciones.domain.services.ContentMediaDomainService;
import com.eviden.fct.tiendaaplicaciones.transversal.AppConstants;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.BadRequestSingleErrorException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.ConflictException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.ForbiddenException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.NotFoundException;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.UnauthorizedException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ContentMediaUploadUseCase {

	private final FilesApplicationService filesService;
	private final AuthenticationApplicationService authService;
	private final ContentDomainService contentService;
	private final ContentMediaDomainService contentMediaService;
	
	public ContentMedia invoke(Long contentId, MultipartFile file) throws NotFoundException, ForbiddenException, ConflictException, UnauthorizedException, IOException, BadRequestSingleErrorException {
		
		User user = authService.getAuthenticatedUser().orElseThrow(UnauthorizedException::new);
		Content content = contentService.get(contentId);
		
		if (content.getState() == ContentState.DELETED) throw new NotFoundException();
		if (!content.getDeveloper().getId().equals(user.getId())) throw new ForbiddenException();
		
		// Gets type
		String contentType = file.getContentType();
		ContentMediaType type;
		
		if (contentType.startsWith("image/")) {
	        type = ContentMediaType.IMAGE;
	    } else if (contentType.startsWith("video/")) {
	        type = ContentMediaType.VIDEO;
	    } else {
	    	throw new BadRequestSingleErrorException(AppConstants.ERRORS_FILE_NOT_VIDEO);
	    }
		
		// Creates media
		ContentMedia media = new ContentMedia();
		media.setContent(content);
		media.setType(type);
		media = contentMediaService.create(media);
		
		// Uploads file
		if (type == ContentMediaType.IMAGE) {
			filesService.uploadImage(ImageType.CONTENT_MEDIA_IMAGE, media.getId(), file);
		} else {
			filesService.uploadVideo(VideoType.CONTENT_MEDIA_VIDEO, media.getId(), file);
		}
		
		return media;
		
	}
	
}
