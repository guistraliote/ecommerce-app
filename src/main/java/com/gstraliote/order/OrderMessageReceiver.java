package com.gstraliote.order;

import com.gstraliote.queue.Queues;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class OrderMessageReceiver {

    @JmsListener(destination = Queues.ORDER)
    public void receiveMessage(OrderDTO orderDTO) {

        System.out.println("Received order: " + orderDTO);
    }
}