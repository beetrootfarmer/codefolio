package com.codefolio.dto.proj.response;

import lombok.Getter;

@Getter
public class ProjListResponse{
	
	private Object proj;
	
	public ProjListResponse(Object proj) {
		this.proj = proj;
	}
}