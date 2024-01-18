package com.gstraliote.order;

import com.gstraliote.queue.Queues;
import com.gstraliote.utils.enums.HttpMethods;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderMessageSender {

    private final JmsTemplate jmsTemplate;

    public OrderMessageSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(OrderDTO orderDTO, Long orderId, HttpMethods httpMethod) {
        OrderMessageWrapper messageWrapper = new OrderMessageWrapper(orderDTO, orderId, httpMethod);

        jmsTemplate.convertAndSend(Queues.ORDER, messageWrapper);
    }
}