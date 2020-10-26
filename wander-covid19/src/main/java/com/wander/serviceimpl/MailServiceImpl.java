package com.wander.serviceimpl;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.wander.constant.EmailConstants;
import com.wander.constant.WanderConstants;
import com.wander.dto.MailDTO;
import com.wander.exception.WanderException;
import com.wander.service.MailService;

@Service
public class MailServiceImpl implements MailService {

	private Logger logger = LogManager.getLogger(MailServiceImpl.class);

	@Autowired
	JavaMailSender mailSender;

	@Override
	public void sendMail(MailDTO mailDTO) throws WanderException {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Send mail with details: %s", mailDTO.toString()));
		}
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setSubject(mailDTO.getMailSubject());
			mimeMessageHelper
					.setFrom(new InternetAddress(EmailConstants.WD_EMAIL_ID, WanderConstants.WD_DOMAIN));
			mimeMessageHelper.setTo(mailDTO.getMailTo());
			mimeMessageHelper.setText(mailDTO.getMailContent());
			mailSender.send(mimeMessageHelper.getMimeMessage());
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("Exception occured while sending mail : %s", e.toString()));
			}
			throw new WanderException(e.toString());
		}
	}
}
