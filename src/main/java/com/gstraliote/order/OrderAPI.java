package com.gstraliote.order;

import com.gstraliote.utils.enums.HttpMethods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderAPI {

    private final OrderService orderService;

    private final OrderMessageSender orderMessageSender;

    public OrderAPI(OrderService orderService, OrderMessageSender orderMessageSender) {
        this.orderService = orderService;
        this.orderMessageSender = orderMessageSender;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        OrderDTO createdOrder = new OrderDTO(
                orderDTO.id(),
                orderDTO.orderDate(),
                orderDTO.status(),
                orderDTO.totalOrderValue(),
                orderDTO.clientId(),
                orderDTO.orderItems()
        );

        orderMessageSender.sendMessage(orderDTO, null, HttpMethods.POST);

        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<OrderDTO>> getAllOrders(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        Page<OrderDTO> orderPage = orderService.findAllPaged(pageRequest);

        return ResponseEntity.ok(orderPage);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long orderId) {
        OrderDTO orderDTO = orderService.getOrderById(orderId);

        return ResponseEntity.ok(orderDTO);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDTO> updateOrder(
            @PathVariable Long orderId,
            @RequestBody OrderDTO updatedOrderDTO) {

        OrderDTO updateOrder = new OrderDTO(
                updatedOrderDTO.id(),
                updatedOrderDTO.orderDate(),
                updatedOrderDTO.status(),
                updatedOrderDTO.totalOrderValue(),
                updatedOrderDTO.clientId(),
                updatedOrderDTO.orderItems()
        );

        orderMessageSender.sendMessage(updateOrder, orderId, HttpMethods.PUT);

        return ResponseEntity.ok(updatedOrderDTO);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderMessageSender.sendMessage(null, orderId, HttpMethods.DELETE);

        return ResponseEntity.noContent().build();
    }
}