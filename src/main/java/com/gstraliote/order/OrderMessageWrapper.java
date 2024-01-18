package com.gstraliote.order;

import com.gstraliote.utils.enums.HttpMethods;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
public class OrderMessageWrapper implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final OrderDTO orderDTO;
    private final Long orderId;
    private final HttpMethods httpMethod;

    public OrderMessageWrapper(OrderDTO orderDTO, Long orderId, HttpMethods httpMethod) {
        this.orderDTO = orderDTO;
        this.orderId = orderId;
        this.httpMethod = httpMethod;
    }
}
