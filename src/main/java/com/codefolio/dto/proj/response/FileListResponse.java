package com.codefolio.dto.proj.response;

import lombok.Getter;

@Getter
public class FileListResponse{
	
	private Object file;
	
	public FileListResponse(Object file) {
		this.file = file;
	}
}