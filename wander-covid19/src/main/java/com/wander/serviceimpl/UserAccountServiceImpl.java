package com.wander.serviceimpl;

import java.util.Date;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wander.constant.RegistrationConstants;
import com.wander.dto.ResponseDTO;
import com.wander.dto.UserAccountDTO;
import com.wander.entity.User;
import com.wander.entity.UserAccount;
import com.wander.pcenum.UserStatus;
import com.wander.repository.UserAccountRepository;
import com.wander.repository.UserRepository;
import com.wander.service.UserAccountService;
import com.wander.utility.RegistrationUtility;

@Service
@Transactional(readOnly = true)
public class UserAccountServiceImpl implements UserAccountService {

	private Logger logger = LogManager.getLogger(UserAccountServiceImpl.class);

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	@Modifying
	@Transactional
	public ResponseEntity<ResponseDTO> saveUser(UserAccountDTO userAccountDTO) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Updating user account with details : %s", userAccountDTO.toString()));
		}
		UserAccount userAccount = null;
		if (RegistrationUtility.validateUserAccountDTO(userAccountDTO)) {
			Optional<User> userOptional = userRepository.findById(userAccountDTO.getUserId());
			if (userOptional.isPresent() && userOptional.get().getUserAccount() != null
					&& userOptional.get().getUserAccount().getStatus() != ' '
					&& userOptional.get().getUserAccount().getStatus() != 'O') {
				userAccount = userOptional.get().getUserAccount();
				fillModifiedUserAccoutDetails(userAccount, userAccountDTO);
				userAccountRepository.save(userAccount);
				if (logger.isDebugEnabled()) {
					logger.debug(String.format("User updated successfully : %s", userAccountDTO.toString()));
				}
				return RegistrationUtility.fillResponseEntity(RegistrationConstants.USER_UPDATED_SUCCESSFULLY,
						HttpStatus.OK);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(
						String.format("No active user exists with given user ID : %s", userAccountDTO.getUserId()));
			}
			return RegistrationUtility.fillResponseEntity(RegistrationConstants.NO_ACTIVE_USER_ID_MESSAGE,
					HttpStatus.BAD_REQUEST);
		}
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Unable to update user details : %s", userAccountDTO.getErrorMessage()));
		}
		return RegistrationUtility.fillResponseEntity(userAccountDTO.getErrorMessage(), HttpStatus.BAD_REQUEST);
	}

	@Override
	@Modifying
	@Transactional
	public ResponseEntity<ResponseDTO> deactivateUser(Long userID) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Deactiving user by userID : %s", userID));
		}
		Date date = new Date();
		if (userID > 0) {
			Optional<UserAccount> userAccountOptional = userAccountRepository.getActiveUser(userID);
			if (userAccountOptional.isPresent()) {
				UserAccount userAccount = userAccountOptional.get();
				userAccount.setStatus(UserStatus.O.asChar());
				userAccount.setUpdatedDate(date);
				userAccountRepository.save(userAccount);
				if (logger.isDebugEnabled()) {
					logger.debug(String.format("User with Id-%s is deactivated successfully", userID));
				}
				return RegistrationUtility.fillResponseEntity(RegistrationConstants.USER_DEACTIVATED_SUCCESSFULLY,
						HttpStatus.OK);
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug(String.format("No active user exists with given userID : %s", userID));
				}
				return RegistrationUtility.fillResponseEntity(RegistrationConstants.NO_ACTIVE_USER_ID_MESSAGE,
						HttpStatus.BAD_REQUEST);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Invalid userID : %s", userID));
		}
		return RegistrationUtility.fillResponseEntity(RegistrationConstants.INVALID_USER_ID_MESSAGE,
				HttpStatus.BAD_REQUEST);
	}

	@Override
	@Modifying
	@Transactional
	public ResponseEntity<ResponseDTO> activateUser(Long userID) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Activating user by userID : %s", userID));
		}
		Date date = new Date();
		if (userID > 0) {
			Optional<UserAccount> userAccountOptional = userAccountRepository.getObsoleteUser(userID);
			if (userAccountOptional.isPresent()) {
				UserAccount userAccount = userAccountOptional.get();
				userAccount.setStatus(UserStatus.A.asChar());
				userAccount.setUpdatedDate(date);
				userAccountRepository.save(userAccount);
				if (logger.isDebugEnabled()) {
					logger.debug(String.format("User with userID-%s activated successfully", userID));
				}
				return RegistrationUtility.fillResponseEntity(RegistrationConstants.USER_ACTIVATED_SUCCESSFULLY,
						HttpStatus.OK);
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug(String.format("No active user exists with userID : %s", userID));
				}
				return RegistrationUtility.fillResponseEntity(RegistrationConstants.NO_INACTIVE_USER_ID_MESSAGE,
						HttpStatus.BAD_REQUEST);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Invalid userID : %s", userID));
		}
		return RegistrationUtility.fillResponseEntity(RegistrationConstants.INVALID_USER_ID_MESSAGE,
				HttpStatus.BAD_REQUEST);
	}

	@Override
	public UserAccount getUserDataById(Long userId) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Get user data by userID : %s", userId));
		}
		Optional<UserAccount> optionalUser = userAccountRepository.findById(userId);
		if (optionalUser.isPresent()) {
			return optionalUser.get();
		}
		return null;
	}


	private UserAccount fillModifiedUserAccoutDetails(UserAccount userAccount, UserAccountDTO userAccountDTO) {
		if (logger.isDebugEnabled()) {
			logger.debug("Find modified user accout details");
		}
		userAccount.setCompany(userAccountDTO.getCompany());
		userAccount.setDob(userAccountDTO.getDob());
		userAccount.setEducation(userAccountDTO.getEducation());
		userAccount.setEmailAddress(userAccountDTO.getEmailAddress());
		userAccount.setGender(userAccountDTO.getGender());
		userAccount.setJobTitle(userAccountDTO.getJobTitle());
		userAccount.setLivingIn(userAccountDTO.getLivingIn());
		userAccount.setPhone(userAccountDTO.getPhone());
		userAccount.setUpdatedDate(new Date());
		userAccount.setProfileUpdated(true);
		userAccount.setUserName(userAccountDTO.getUserName());
		return userAccount;
	}
}
