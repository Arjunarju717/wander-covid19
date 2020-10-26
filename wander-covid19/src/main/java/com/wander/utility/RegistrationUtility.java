package com.wander.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import com.wander.constant.RegistrationConstants;
import com.wander.dto.ResetPasswordDTO;
import com.wander.dto.ResponseDTO;
import com.wander.dto.UserAccountDTO;
import com.wander.dto.UserDTO;
import com.wander.entity.Role;
import com.wander.entity.User;

public final class RegistrationUtility {

	private static Logger logger = LogManager.getLogger(RegistrationUtility.class);

	private RegistrationUtility() {
	}

	private static final String EMAIL_REGEX = "^(.+)@(.+)$";

	private static List<String> genderList = Arrays.asList("Male", "Female", "Other");

	public static boolean validateUserDTO(UserDTO userDTO) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Validate user details : %s", userDTO.toString()));
		}
		StringBuilder sb = new StringBuilder();
		boolean isValid = true;

		String username = userDTO.getUserName();
		if (isEmpty(username)) {
			if (sb.length() != 0) {
				sb.append(", ");
			}
			sb.append(RegistrationConstants.USERNAME_REQUIRED_MESSAGE);
			isValid = false;
		}

		String phone = userDTO.getPhone() != null ? userDTO.getPhone().toString() : null;
		if (isEmpty(phone)) {
			if (sb.length() != 0) {
				sb.append(", ");
			}
			sb.append(RegistrationConstants.USER_PHONE_REQUIRED_MESSAGE);
			isValid = false;
		} else if (isNotValidLength(phone, RegistrationConstants.USER_PHONE_LENGTH)) {
			if (sb.length() != 0) {
				sb.append(", ");
			}
			sb.append(RegistrationConstants.USER_PHONE_LENGTH_VALIDATION_MESSAGE);
			isValid = false;
		}

		String emailaddress = userDTO.getEmailAddress();
		if (isEmpty(emailaddress)) {
			if (sb.length() != 0) {
				sb.append(", ");
			}
			sb.append(RegistrationConstants.USER_EMAILADDRESS_REQUIRED_MESSAGE);
			isValid = false;
		} else if (!isEmailValid(emailaddress)) {
			if (sb.length() != 0) {
				sb.append(", ");
			}
			sb.append(RegistrationConstants.USER_EMAILADDRESS_VALIDATION_MESSAGE);
			isValid = false;
		}

		String password = userDTO.getPassword();
		if (isEmpty(password)) {
			if (sb.length() != 0) {
				sb.append(", ");
			}
			sb.append(RegistrationConstants.USER_PASSWORD_REQUIRED_MESSAGE);
			isValid = false;
		}

		String confirmedPassword = userDTO.getConfirmedPassword();
		if (isEmpty(confirmedPassword)) {
			if (sb.length() != 0) {
				sb.append(", ");
			}
			sb.append(RegistrationConstants.USER_CONFIRM_PASSWORD_REQUIRED_MESSAGE);
			isValid = false;
		}
		if (!isEmpty(password) && !isEmpty(confirmedPassword) && !doesPasswordMatch(password, confirmedPassword)) {
			if (sb.length() != 0) {
				sb.append(", ");
			}
			sb.append(RegistrationConstants.USER_PASSWORD_VALIDATION_MESSAGE);
			isValid = false;
		}
		if (!isValid) {
			if (sb.length() != 0) {
				sb.append(".");
			}
			userDTO.setErrorMessage(sb.toString());
		}
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Is user details valid : %s", isValid));
		}
		return isValid;
	}

	public static boolean validateUserAccountDTO(UserAccountDTO userAccountDTO) {
		if (logger.isDebugEnabled()) {
			logger.debug("Validating user account details : %s", userAccountDTO.toString());
		}
		StringBuilder sb = new StringBuilder();
		boolean isValid = true;

		String userId = userAccountDTO.getUserId() != null ? userAccountDTO.getUserId().toString() : null;
		if (isEmpty(userId)) {
			if (sb.length() != 0) {
				sb.append(", ");
			}
			sb.append(RegistrationConstants.USER_ID_MESSAGE);
			isValid = false;
		}

		String username = userAccountDTO.getUserName();
		if (isEmpty(username)) {
			if (sb.length() != 0) {
				sb.append(", ");
			}
			sb.append(RegistrationConstants.USERNAME_REQUIRED_MESSAGE);
			isValid = false;
		}

		String phone = userAccountDTO.getPhone() != null ? userAccountDTO.getPhone().toString() : null;
		if (isEmpty(phone)) {
			if (sb.length() != 0) {
				sb.append(", ");
			}
			sb.append(RegistrationConstants.USER_PHONE_REQUIRED_MESSAGE);
			isValid = false;
		} else if (isNotValidLength(phone, RegistrationConstants.USER_PHONE_LENGTH)) {
			if (sb.length() != 0) {
				sb.append(", ");
			}
			sb.append(RegistrationConstants.USER_PHONE_LENGTH_VALIDATION_MESSAGE);
			isValid = false;
		}

		String emailaddress = userAccountDTO.getEmailAddress();
		if (isEmpty(emailaddress)) {
			if (sb.length() != 0) {
				sb.append(", ");
			}
			sb.append(RegistrationConstants.USER_EMAILADDRESS_REQUIRED_MESSAGE);
			isValid = false;
		} else if (!isEmailValid(emailaddress)) {
			if (sb.length() != 0) {
				sb.append(", ");
			}
			sb.append(RegistrationConstants.USER_EMAILADDRESS_VALIDATION_MESSAGE);
			isValid = false;
		}

		String livingIn = userAccountDTO.getLivingIn();
		if (isEmpty(livingIn)) {
			if (sb.length() != 0) {
				sb.append(", ");
			}
			sb.append(RegistrationConstants.USER_LIVING_IN_MESSAGE);
			isValid = false;
		}

		String dob = userAccountDTO.getDob() != null ? userAccountDTO.getDob().toString() : null;
		if (isEmpty(dob)) {
			if (sb.length() != 0) {
				sb.append(", ");
			}
			sb.append(RegistrationConstants.USER_DOB_MESSAGE);
			isValid = false;
		}

		String gender = userAccountDTO.getGender();
		if (isEmpty(gender)) {
			if (sb.length() != 0) {
				sb.append(", ");
			}
			sb.append(RegistrationConstants.USER_GENDER_MESSAGE);
			isValid = false;
		} else if (!validateGender(gender)) {
			if (sb.length() != 0) {
				sb.append(", ");
			}
			sb.append(RegistrationConstants.USER_GENDER_VALIDATION_MESSAGE);
			isValid = false;
		}

		if (!isValid) {
			if (sb.length() != 0) {
				sb.append(".");
			}
			userAccountDTO.setErrorMessage(sb.toString());
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Is user account details valid : %s", isValid);
		}
		return isValid;
	}

	private static boolean validateGender(String gender) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Is gender valid : %s", gender));
		}
		return genderList.stream().anyMatch(s -> s.equalsIgnoreCase(gender));
	}

	public static boolean isEmpty(String stringValue) {
		if (logger.isDebugEnabled()) {
			logger.debug("Inside isEmpty method");
		}
		return stringValue == null || stringValue.trim().isEmpty();
	}

	public static boolean isNotValidLength(String stringValue, int length) {
		if (logger.isDebugEnabled()) {
			logger.debug("Inside isNotValidLength method");
		}
		return (!isEmpty(stringValue) && stringValue.trim().length() != length);
	}

	public static boolean isEmailValid(String emailAddress) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Is emailAddress valid : %s", emailAddress));
		}
		Pattern pattern = Pattern.compile(EMAIL_REGEX);
		Matcher matcher = pattern.matcher(emailAddress);
		return matcher.matches();
	}

	public static boolean doesPasswordMatch(String password, String confirmedPassword) {
		if (logger.isDebugEnabled()) {
			logger.debug("Inside doesPasswordMatch method");
		}
		return password.equals(confirmedPassword);
	}

	public static User getDefaultUserProperties(String roleName) {
		if (logger.isDebugEnabled()) {
			logger.debug("Inside getDefaultUserProperties method");
		}
		User user = new User();
		List<Role> roles = new ArrayList<>();
		Role role = new Role();
		user.setId(getUniqueID());
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setEnabled(true);
		role.setId(2L);
		role.setName(roleName);
		roles.add(role);
		user.setRoles(roles);
		return user;
	}

	public static String encryptPassword(String rawPassword) {
		if (logger.isDebugEnabled()) {
			logger.debug("Inside encryptPassword method");
		}
		return PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(rawPassword);
	}

	public static boolean isPasswordMatching(String rawPassword, String encodedPassword) {
		if (logger.isDebugEnabled()) {
			logger.debug("Inside isPasswordMatching method");
		}
		return PasswordEncoderFactories.createDelegatingPasswordEncoder().matches(rawPassword, encodedPassword);
	}

	public static Long getUniqueID() {
		if (logger.isDebugEnabled()) {
			logger.debug("Inside getUniqueID method");
		}
		DateFormat df = new SimpleDateFormat("yy");
		long year = Long.parseLong(df.format(Calendar.getInstance().getTime()) + "00000000");
		long randomNumber = (long) (Math.random() * 100000000L);
		return year + randomNumber;
	}

	public static String getUniquePassword() {
		if (logger.isDebugEnabled()) {
			logger.debug("Inside getUniquePassword method");
		}
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 8;
		return new Random().ints(leftLimit, rightLimit + 1).filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
				.limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
	}

	public static ResponseEntity<ResponseDTO> fillResponseEntity(String message, HttpStatus status) {
		ResponseDTO responseDTO = new ResponseDTO(message);
		return new ResponseEntity<>(responseDTO, status);
	}

	public static boolean validateResetPasswordDTO(ResetPasswordDTO resetPasswordDTO) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Validating resetPassword DTO for userID : %s", resetPasswordDTO.getUserName()));
		}
		StringBuilder sb = new StringBuilder();
		boolean isValid = true;
		
		String userName = resetPasswordDTO.getUserName();
		if (isEmpty(userName)) {
			if (sb.length() != 0) {
				sb.append(", ");
			}
			sb.append(RegistrationConstants.USERNAME_REQUIRED_MESSAGE);
			isValid = false;
		}

		String defaultPassword = resetPasswordDTO.getDefaultPassword();
		if (isEmpty(defaultPassword)) {
			if (sb.length() != 0) {
				sb.append(", ");
			}
			sb.append(RegistrationConstants.USER_DEFAULT_PASSWORD_REQUIRED_MESSAGE);
			isValid = false;
		}

		String newPassword = resetPasswordDTO.getNewPassword();
		if (isEmpty(newPassword)) {
			if (sb.length() != 0) {
				sb.append(", ");
			}
			sb.append(RegistrationConstants.USER_NEW_PASSWORD_REQUIRED_MESSAGE);
			isValid = false;
		}

		String confirmedPassword = resetPasswordDTO.getConfirmPassword();
		if (isEmpty(confirmedPassword)) {
			if (sb.length() != 0) {
				sb.append(", ");
			}
			sb.append(RegistrationConstants.USER_CONFIRM_PASSWORD_REQUIRED_MESSAGE);
			isValid = false;
		}
		if (!isEmpty(newPassword) && !isEmpty(confirmedPassword)
				&& !doesPasswordMatch(newPassword, confirmedPassword)) {
			if (sb.length() != 0) {
				sb.append(", ");
			}
			sb.append(RegistrationConstants.USER_PASSWORD_VALIDATION_MESSAGE);
			isValid = false;
		}
		if (!isValid) {
			if (sb.length() != 0) {
				sb.append(".");
			}
			resetPasswordDTO.setErrorMessage(sb.toString());
		}
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Is resetPasswordDTO details valid : %s", isValid));
		}
		return isValid;
	}

}
