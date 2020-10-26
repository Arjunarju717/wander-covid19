package com.wander.service;

import com.wander.dto.MailDTO;
import com.wander.exception.WanderException;

public interface MailService {
	
	public void sendMail(MailDTO mailDTO) throws WanderException;

}
