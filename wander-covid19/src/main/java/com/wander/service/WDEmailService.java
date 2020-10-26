package com.wander.service;

import org.springframework.http.ResponseEntity;

import com.wander.dto.ResponseDTO;

public interface WDEmailService {
	
	public ResponseEntity<ResponseDTO> forgotPassword(String emailAddress, String password);
	
	public ResponseEntity<ResponseDTO> resetPasswordMail(String emailAddress);

}
