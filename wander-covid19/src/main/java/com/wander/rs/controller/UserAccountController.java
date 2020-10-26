package com.wander.rs.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wander.dto.ResponseDTO;
import com.wander.dto.UserAccountDTO;
import com.wander.entity.UserAccount;
import com.wander.service.UserAccountService;

@RestController
@RequestMapping("userAccount/")
public class UserAccountController {

	private Logger logger = LogManager.getLogger(UserAccountController.class);

	@Autowired
	private UserAccountService userAccountService;

	@PatchMapping("update")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<ResponseDTO> userAccount(@RequestBody UserAccountDTO userAccountDTO) {
		if (logger.isDebugEnabled()) {
			logger.debug("Inside userAccount method");
		}
		return userAccountService.saveUser(userAccountDTO);
	}

	@GetMapping("getUserDataById/{userId}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public UserAccount getUserDataById(@PathVariable Long userId) {
		if (logger.isDebugEnabled()) {
			logger.debug("Inside getUserDataById method");
		}
		return userAccountService.getUserDataById(userId);
	}

	@PatchMapping("deactivate/{userID}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<ResponseDTO> deactivate(@PathVariable Long userID) {
		if (logger.isDebugEnabled()) {
			logger.debug("Inside deactivate method");
		}
		return userAccountService.deactivateUser(userID);
	}

	@PatchMapping("activate/{userID}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<ResponseDTO> activate(@PathVariable Long userID) {
		if (logger.isDebugEnabled()) {
			logger.debug("Inside sendInterest method");
		}
		return userAccountService.activateUser(userID);
	}
}
