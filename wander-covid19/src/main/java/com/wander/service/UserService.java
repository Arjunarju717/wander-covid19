package com.wander.service;

import org.springframework.http.ResponseEntity;

import com.wander.dto.ResetPasswordDTO;
import com.wander.dto.ResponseDTO;
import com.wander.dto.UserDTO;

public interface UserService {
	
	public ResponseEntity<ResponseDTO> saveUser(UserDTO userDTO);
	
	public ResponseEntity<ResponseDTO> deleteUser(Long userID);
	
	public ResponseEntity<ResponseDTO> resetUserPassword(ResetPasswordDTO resetPasswordDTO);

	public ResponseEntity<ResponseDTO> forgotUserPassword(String userName);
	
}
