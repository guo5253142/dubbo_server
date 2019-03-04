package com.my.server.jms;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;
@Component
public class MyMessageConvert implements MessageConverter{

	@Override
	public Object fromMessage(Message message) throws JMSException, MessageConversionException {
		// TODO Auto-generated method stub
		ObjectMessage msg=(ObjectMessage)message;
		return msg.getObject();
	}

	@Override
	public Message toMessage(Object obj, Session session) throws JMSException, MessageConversionException {
		// TODO Auto-generated method stub
		return session.createObjectMessage((Serializable)obj);
	}

}
