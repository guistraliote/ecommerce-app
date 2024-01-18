package com.gstraliote.order;

import com.gstraliote.queue.Queues;
import com.gstraliote.utils.enums.HttpMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class OrderMessageReceiver {

    private final OrderService orderService;

    @Autowired
    public OrderMessageReceiver(OrderService orderService) {
        this.orderService = orderService;
    }

    @JmsListener(destination = Queues.ORDER)
    public void receiveMessage(OrderMessageWrapper messageWrapper) {
        OrderDTO orderDTO = messageWrapper.orderDTO();
        Long orderId = messageWrapper.orderId();
        HttpMethods httpMethod = messageWrapper.httpMethod();

        switch (httpMethod) {
            case POST:
                orderService.createOrder(orderDTO);
                break;
            case PUT:
                orderService.updateOrder(orderId, orderDTO);
                break;
            case DELETE:
                orderService.deleteOrder(orderId);
                break;
        }

        System.out.println("Received order: " + orderDTO);
    }
}