package com.gstraliote.order;

import com.gstraliote.utils.enums.HttpMethods;

public record OrderMessageWrapper(
        OrderDTO orderDTO,
        Long orderId,
        HttpMethods httpMethod
) {
}
