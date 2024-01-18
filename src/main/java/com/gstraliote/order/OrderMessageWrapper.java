package com.gstraliote.order;

import com.gstraliote.utils.enums.HttpMethods;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

public record OrderMessageWrapper(
        OrderDTO orderDTO,
        Long orderId,
        HttpMethods httpMethod
) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}