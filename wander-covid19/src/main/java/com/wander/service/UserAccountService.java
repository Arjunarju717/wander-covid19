package com.wander.service;

import org.springframework.http.ResponseEntity;

import com.wander.dto.ResponseDTO;
import com.wander.dto.UserAccountDTO;
import com.wander.entity.UserAccount;

public interface UserAccountService {
	
	public ResponseEntity<ResponseDTO> saveUser(UserAccountDTO userAccountDTO);
	
	public UserAccount getUserDataById(Long userid);
	
	public ResponseEntity<ResponseDTO> deactivateUser(Long userID);

	public ResponseEntity<ResponseDTO> activateUser(Long userID);
	
	
}
