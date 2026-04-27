package com.eviden.fct.tiendaaplicaciones.application.services;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eviden.fct.tiendaaplicaciones.domain.appservices.FileType;
import com.eviden.fct.tiendaaplicaciones.domain.appservices.FilesApplicationService;
import com.eviden.fct.tiendaaplicaciones.domain.appservices.ImageType;
import com.eviden.fct.tiendaaplicaciones.domain.appservices.VideoType;
import com.eviden.fct.tiendaaplicaciones.transversal.AppConstants;
import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.BadRequestSingleErrorException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FilesService implements FilesApplicationService {

	@Override
	public Resource getFile(FileType fileType, Long id) throws IOException {
		Path path = Paths.get(String.format(FILE_FORMAT, fileType, id));
		return new UrlResource(path.toUri());
	}
	
	@Override
	public void uploadFile(FileType fileType, Long id, MultipartFile file) throws IOException {
		Path path = Paths.get(String.format(FILE_FORMAT, fileType, id));
		Files.createDirectories(path.getParent());
		
		try (InputStream stream = file.getInputStream()) {
			Files.copy(stream, path, StandardCopyOption.REPLACE_EXISTING);
		}
	}
	
	
	@Override
	public Resource getImage(ImageType imageType, Long id) throws IOException {
		Path path = Paths.get(String.format(IMAGE_FORMAT, imageType, id));
		return new UrlResource(path.toUri());
	}
	
	@Override
	public void uploadImage(ImageType imageType, Long id, MultipartFile file) throws IOException, BadRequestSingleErrorException {
		// Gets path
		Path path = Paths.get(String.format(IMAGE_FORMAT, imageType, id));
		Files.createDirectories(path.getParent());
		
		// Transform to PNG
		BufferedImage image = ImageIO.read(file.getInputStream());
	    if (image == null) {
	        throw new BadRequestSingleErrorException(AppConstants.ERRORS_FILE_NOT_IMAGE);
	    }
		
	    // Save the image
	    try (OutputStream out = Files.newOutputStream(path)) {
	        ImageIO.write(image, "png", out);
	    }
	}
	
	@Override
	public Resource getVideo(VideoType videoType, Long id) throws IOException {
		Path path = Paths.get(String.format(VIDEO_FORMAT, videoType, id));
		return new UrlResource(path.toUri());
	}
	
	@Override
	public void uploadVideo(VideoType videoType, Long id, MultipartFile file) throws IOException, BadRequestSingleErrorException {
		// Gets path
	    Path path = Paths.get(String.format(VIDEO_FORMAT, videoType, id));
	    Files.createDirectories(path.getParent());

	    // Validate Content-Type
	    String contentType = file.getContentType();
	    if (contentType == null || !contentType.equals("video/mp4")) {
	        throw new BadRequestSingleErrorException(AppConstants.ERRORS_FILE_NOT_VIDEO);
	    }

	    // Validate file extension
	    String originalFilename = file.getOriginalFilename();
	    if (originalFilename == null || !originalFilename.toLowerCase().endsWith(".mp4")) {
	        throw new BadRequestSingleErrorException(AppConstants.ERRORS_FILE_NOT_VIDEO);
	    }

	    // Save the file
	    try (InputStream in = file.getInputStream()) {
	        Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
	    }
	}
	
}
