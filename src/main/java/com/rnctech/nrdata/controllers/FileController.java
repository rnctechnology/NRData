package com.rnctech.nrdata.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.rnctech.nrdata.services.RepoService;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
/*
 * @contributor zilin
 * 2020.09.25
 * 
 * File upload/download controller
 */

@RestController
@RequestMapping(value = "/schema")
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private RepoService repoService;

	@PostMapping("/upload")
	public FileResponse uploadFile(@RequestParam("file") MultipartFile file) {
		try {
			String fileName = repoService.storeFile(file);

			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/file/download/")
					.path(fileName).toUriString();

			return new FileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
		} catch (Exception e) {
			return new FileResponse(file.getName(), e.getMessage());
		}
	}

	@PostMapping("/uploadFiles")
	public List<FileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		return Arrays.asList(files).stream().map(file -> uploadFile(file)).collect(Collectors.toList());
	}

	@GetMapping("/download/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
		try {
			Resource resource = repoService.loadFile(fileName);
			
			String contentType = "text/html";
			try {
				contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());				
			} catch (IOException ex) {
				logger.info("Could not determine file type.");
			}

			if (contentType == null) {
				contentType = "application/octet-stream";
			}

			return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	public class FileResponse {
		private String fileName;
		private String downloadUri;
		private String fileType;
		private long size;
		private String errmsg = "";

		public FileResponse(String fileName, String msg) {
			this.fileName = fileName;
			this.errmsg = msg;
		}

		public FileResponse(String fileName, String fileDownloadUri, String fileType, long size) {
			this.fileName = fileName;
			this.downloadUri = fileDownloadUri;
			this.fileType = fileType;
			this.size = size;
		}

		public String getErrmsg() {
			return errmsg;
		}

		public void setErrmsg(String errmsg) {
			this.errmsg = errmsg;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getFileDownloadUri() {
			return downloadUri;
		}

		public void setFileDownloadUri(String fileDownloadUri) {
			this.downloadUri = fileDownloadUri;
		}

		public String getFileType() {
			return fileType;
		}

		public void setFileType(String fileType) {
			this.fileType = fileType;
		}

		public long getSize() {
			return size;
		}

		public void setSize(long size) {
			this.size = size;
		}

	}
}
