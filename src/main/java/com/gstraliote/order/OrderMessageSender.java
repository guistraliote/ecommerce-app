package com.gstraliote.order;

import com.gstraliote.queue.Queues;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderMessageSender {

    private final JmsTemplate jmsTemplate;

    public OrderMessageSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(OrderDTO orderDTO) {
        jmsTemplate.convertAndSend(Queues.ORDER, orderDTO);
    }
}