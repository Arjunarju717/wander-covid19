package com.wander.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.wander.dto.ResponseDTO;

public interface TokenService {

	public ResponseEntity<ResponseDTO> revokeToken(HttpServletRequest httpServletRequest);

}
