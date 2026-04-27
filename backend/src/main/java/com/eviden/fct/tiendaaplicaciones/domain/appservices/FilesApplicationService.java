package com.eviden.fct.tiendaaplicaciones.domain.appservices;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.BadRequestSingleErrorException;

public interface FilesApplicationService {

	public static final String FILE_FORMAT = "uploads/%s_%s";
	public static final String IMAGE_FORMAT = "images/%s_%s.png";
	public static final String VIDEO_FORMAT = "videos/%s_%s.mp4";
	
	public void uploadFile(FileType fileType, Long id, MultipartFile file) throws IOException;
	public Resource getFile(FileType fileType, Long id) throws IOException;
	
	public void uploadImage(ImageType imageType, Long id, MultipartFile file) throws IOException, BadRequestSingleErrorException;
	public Resource getImage(ImageType imageType, Long id) throws IOException;
	
	public void uploadVideo(VideoType videoType, Long id, MultipartFile file) throws IOException, BadRequestSingleErrorException;
	public Resource getVideo(VideoType videoType, Long id) throws IOException;
}
