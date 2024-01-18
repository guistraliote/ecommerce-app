package com.gstraliote.order;

import com.gstraliote.queue.Queues;
import com.gstraliote.utils.enums.HttpMethods;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class OrderMessageReceiver {

    private OrderService orderService;

    @JmsListener(destination = Queues.ORDER)
    public void receiveMessage(OrderMessageWrapper messageWrapper) {
        OrderDTO orderDTO = messageWrapper.getOrderDTO();
        Long orderId = messageWrapper.getOrderId();
        HttpMethods httpMethod = messageWrapper.getHttpMethod();

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