package com.my.common.common.service;

import com.my.common.system.domain.User;

public interface JmsProducerService {
	 void sendMsg(final User user);
}
