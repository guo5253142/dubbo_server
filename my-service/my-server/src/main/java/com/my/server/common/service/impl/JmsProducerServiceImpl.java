package com.my.server.common.service.impl;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.my.common.common.service.JmsProducerService;
import com.my.common.system.domain.User;
@Service("jmsProducerServiceImpl")
public class JmsProducerServiceImpl implements JmsProducerService{
	private transient final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	JmsTemplate jmsTemplate;
	
	@Resource(name = "queueDestination")
	Destination destination;
	
	@Override
	public void sendMsg(final User user) {
		/*jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(msg);
                logger.info("发送消息 = [" + textMessage.getText() + "]");
                return textMessage;
            }
        });*/
		jmsTemplate.convertAndSend(destination, user);
	}

}
