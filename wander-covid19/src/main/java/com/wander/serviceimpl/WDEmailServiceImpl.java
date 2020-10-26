package com.wander.serviceimpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wander.constant.EmailConstants;
import com.wander.dto.MailDTO;
import com.wander.dto.ResponseDTO;
import com.wander.exception.WanderException;
import com.wander.service.MailService;
import com.wander.service.WDEmailService;
import com.wander.utility.RegistrationUtility;

@Service
@Transactional(readOnly = true)
public class WDEmailServiceImpl implements WDEmailService {

	private Logger logger = LogManager.getLogger(WDEmailServiceImpl.class);

	@Autowired
	private MailService mailService;

	@Override
	public ResponseEntity<ResponseDTO> forgotPassword(String emailAddress, String password) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Send forgot password mail to emailAddress : %s", emailAddress));
		}
		MailDTO mailDTO = getForgetMailDTO(emailAddress, password);
		try {
			mailService.sendMail(mailDTO);
		} catch (WanderException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("Exception occurred while sending forgot password mail : %s", e.toString()));
			}
			return RegistrationUtility.fillResponseEntity(EmailConstants.EMAIL_EXCEPTION_MESSAGE,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (logger.isDebugEnabled()) {
			logger.debug(
					String.format("Forgot password mail was sent successfully to emailAddress : %s", emailAddress));
		}
		return RegistrationUtility.fillResponseEntity(EmailConstants.EMAIL_SENT_SUCCESSFULLY, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseDTO> resetPasswordMail(String emailAddress) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Send reset password mail to emailAddress : %s", emailAddress));
		}
		MailDTO mailDTO = getResetMailDTO(emailAddress);
		try {
			mailService.sendMail(mailDTO);
		} catch (WanderException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("Exception occurred while sending reset password mail : %s", e.toString()));
			}
			return RegistrationUtility.fillResponseEntity(EmailConstants.EMAIL_EXCEPTION_MESSAGE,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Password reset mail was sent successfully to emailAddress : %s", emailAddress));
		}
		return RegistrationUtility.fillResponseEntity(EmailConstants.EMAIL_SENT_SUCCESSFULLY, HttpStatus.OK);
	}

	private MailDTO getForgetMailDTO(String emailAddress, String password) {
		if (logger.isDebugEnabled()) {
			logger.debug("Get forgetMailDTO");
		}
		MailDTO mailDTO = getDefaultMailDTO();
		mailDTO.setMailTo(emailAddress);
		mailDTO.setMailSubject(EmailConstants.FORGOT_PASSWORD_SUBJECT);
		mailDTO.setMailContent("Your password has been changed..!!!\n\nLogin with new password: " + password
				+ "\n\nThanks\nwww.wander.com");
		return mailDTO;
	}

	private MailDTO getResetMailDTO(String emailAddress) {
		if (logger.isDebugEnabled()) {
			logger.debug("Get resetMailDTO");
		}
		MailDTO mailDTO = getDefaultMailDTO();
		mailDTO.setMailTo(emailAddress);
		mailDTO.setMailSubject(EmailConstants.RESET_PASSWORD_SUBJECT);
		mailDTO.setMailContent(
				"Your password reset was successful..!!!\n\nLogin with new password\n\nThanks\nwww.wander.digital");
		return mailDTO;
	}

	private MailDTO getDefaultMailDTO() {
		MailDTO mailDTO = new MailDTO();
		mailDTO.setMailFrom(EmailConstants.WD_EMAIL_ID);
		return mailDTO;
	}
}
