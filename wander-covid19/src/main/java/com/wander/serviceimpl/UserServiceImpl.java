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

import com.wander.constant.EmailConstants;
import com.wander.constant.RegistrationConstants;
import com.wander.dto.ResetPasswordDTO;
import com.wander.dto.ResponseDTO;
import com.wander.dto.UserDTO;
import com.wander.entity.User;
import com.wander.entity.UserAccount;
import com.wander.pcenum.UserRole;
import com.wander.pcenum.UserStatus;
import com.wander.repository.UserRepository;
import com.wander.service.UserService;
import com.wander.service.WDEmailService;
import com.wander.utility.RegistrationUtility;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	private Logger logger = LogManager.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WDEmailService pcEmailService;

	@Override
	@Modifying
	@Transactional
	public ResponseEntity<ResponseDTO> saveUser(UserDTO userDTO) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Create new user with details : %s", userDTO.toString()));
		}
		User user = null;
		if (RegistrationUtility.validateUserDTO(userDTO) && !isUserRegistered(userDTO)) {
			user = RegistrationUtility.getDefaultUserProperties(UserRole.U.toString());
			String password = RegistrationUtility.encryptPassword(userDTO.getPassword());
			UserAccount userAccount = fillUserAccount(userDTO, user.getId());
			user.setUsername(userDTO.getUserName());
			user.setPassword(password);
			userAccount.setUser(user);
			user.setUserAccount(userAccount);
			userRepository.save(user);
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("User created successfully : %s", user.toString()));
			}
			return RegistrationUtility.fillResponseEntity(RegistrationConstants.USER_CREATED_SUCCESSFULLY,
					HttpStatus.CREATED);
		}
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Unable to create user : %s", userDTO.getErrorMessage()));
		}
		return RegistrationUtility.fillResponseEntity(userDTO.getErrorMessage(), HttpStatus.BAD_REQUEST);
	}

	@Override
	@Modifying
	@Transactional
	public ResponseEntity<ResponseDTO> deleteUser(Long userID) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Delete user with userID : %s", userID));
		}
		Optional<User> userOptional = userRepository.findById(userID);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			userRepository.delete(user);
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("User with userID-%s deleted successfully", userID));
			}
			return RegistrationUtility.fillResponseEntity(RegistrationConstants.USER_DELETED_SUCCESSFULLY,
					HttpStatus.OK);
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("No user exists with given userID : %s", userID));
			}
			return RegistrationUtility.fillResponseEntity(RegistrationConstants.NO_USER_ID_MESSAGE,
					HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	@Modifying
	@Transactional
	public ResponseEntity<ResponseDTO> resetUserPassword(ResetPasswordDTO resetPasswordDTO) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Inside resetPassword method for userID : %s", resetPasswordDTO.getUserName()));
		}
		if (RegistrationUtility.validateResetPasswordDTO(resetPasswordDTO)) {
			Optional<User> userOptional = userRepository.findUserByName(resetPasswordDTO.getUserName());
			if (userOptional.isPresent()) {
				User user = userOptional.get();
				if (RegistrationUtility.isPasswordMatching(resetPasswordDTO.getDefaultPassword(), user.getPassword())) {
					String newEncryptedPassword = RegistrationUtility
							.encryptPassword(resetPasswordDTO.getNewPassword());
					user.setPassword(newEncryptedPassword);
					user.getUserAccount().setUpdatedDate(new Date());
					userRepository.save(user);
					pcEmailService.resetPasswordMail(user.getUserAccount().getEmailAddress());
					if (logger.isDebugEnabled()) {
						logger.debug(String.format("Password reset was successful for user : %s",
								resetPasswordDTO.getUserName()));
					}
					return RegistrationUtility.fillResponseEntity(RegistrationConstants.PASSWORD_RESET_SUCCESS_MESSAGE,
							HttpStatus.OK);
				} else {
					if (logger.isDebugEnabled()) {
						logger.debug(String.format("Old password is incorrect : %s", resetPasswordDTO.getUserName()));
					}
					return RegistrationUtility.fillResponseEntity(
							RegistrationConstants.USER_DEFAULT_PASSWORD_INCORRECT_MESSAGE, HttpStatus.BAD_REQUEST);
				}
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug(String.format("No user found with userID : %s", resetPasswordDTO.getUserName()));
				}
				return RegistrationUtility.fillResponseEntity(RegistrationConstants.NO_USER_ID_MESSAGE,
						HttpStatus.BAD_REQUEST);
			}
		}
		String errorMessage = resetPasswordDTO.getErrorMessage();
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("PasswordResetDTO validation fails : %s", errorMessage));
		}
		return RegistrationUtility.fillResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);
	}

	@Override
	@Modifying
	@Transactional
	public ResponseEntity<ResponseDTO> forgotUserPassword(String userName) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Send password reset mail to user : %s", userName));
		}
		if (null != userName) {
			Optional<User> userOptional = userRepository.findUserByName(userName);
			if (userOptional.isPresent()) {
				User user = userOptional.get();
				if (null != user.getUserAccount().getEmailAddress()) {
					String password = RegistrationUtility.getUniquePassword();
					String encryptedPassword = RegistrationUtility.encryptPassword(password);
					user.setPassword(encryptedPassword);
					user.getUserAccount().setUpdatedDate(new Date());
					userRepository.save(user);
					ResponseEntity<ResponseDTO> emailResponse = pcEmailService
							.forgotPassword(user.getUserAccount().getEmailAddress(), password);
					if (emailResponse.getStatusCodeValue() != 200) {
						return emailResponse;
					}
					if (logger.isDebugEnabled()) {
						logger.debug(String.format("Password reset mail sent successfully to user : %s", userName));
					}
					return RegistrationUtility.fillResponseEntity(EmailConstants.EMAIL_SENT_SUCCESSFULLY,
							HttpStatus.OK);
				} else {
					if (logger.isDebugEnabled()) {
						logger.debug(String.format("Email address is not available for user : %s", userName));
					}
					return RegistrationUtility.fillResponseEntity(RegistrationConstants.EMAILADDRESS_NOT_AVAILABLE,
							HttpStatus.BAD_REQUEST);
				}
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug(String.format("No user exists with userName : %s", userName));
				}
				return RegistrationUtility.fillResponseEntity(RegistrationConstants.NO_USER_WITH_USERNAME_MESSAGE,
						HttpStatus.BAD_REQUEST);
			}
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("User name is required : %s", userName));
			}
			return RegistrationUtility.fillResponseEntity(RegistrationConstants.USERNAME_REQUIRED_MESSAGE,
					HttpStatus.BAD_REQUEST);
		}
	}

	public UserAccount fillUserAccount(UserDTO userDTO, Long userId) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Fill user account details for userID : %s", userId));
		}
		Date date = new Date();
		UserAccount userAccount = new UserAccount();
		userAccount.setUserAccountId(userId);
		userAccount.setCreatedDate(date);
		userAccount.setEmailAddress(userDTO.getEmailAddress());
		userAccount.setStatus(UserStatus.A.asChar());
		userAccount.setUpdatedDate(date);
		userAccount.setUserName(userDTO.getUserName());
		userAccount.setPhone(userDTO.getPhone());
		userAccount.setProfileUpdated(false);
		return userAccount;
	}

	public boolean isUserRegistered(UserDTO userDTO) {
		if (logger.isDebugEnabled()) {
			logger.debug("Check if user is registered");
		}
		Optional<User> userOptional = userRepository.findUserByName(userDTO.getUserName());
		if (userOptional.isPresent()) {
			userDTO.setErrorMessage(RegistrationConstants.USER_ALREADY_REGISTERED);
			return true;
		}
		return false;
	}
}
