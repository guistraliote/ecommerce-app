package com.gstraliote.order;

import com.gstraliote.orderItems.OrderItemsDTO;
import com.gstraliote.client.Client;
import com.gstraliote.orderItems.OrderItems;
import com.gstraliote.client.ClientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;

    public OrderService(OrderRepository orderRepository, ClientRepository clientRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
    }

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order orderEntity = convertToEntity(orderDTO);
        Order savedOrder = orderRepository.save(orderEntity);

        return convertToDTO(savedOrder);
    }

    @Transactional(readOnly = true)
    public Page<OrderDTO> findAllPaged(PageRequest pageRequest) {
        Page<Order> orderPage = orderRepository.findAll(pageRequest);

        return orderPage.map(this::convertToDTO);
    }

    @Transactional(readOnly = true)
    public OrderDTO getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Transactional
    public OrderDTO updateOrder(Long orderId, OrderDTO updatedOrderDTO) {
        return orderRepository.findById(orderId)
                .map(existingOrder -> {
                    updateOrderEntity(existingOrder, updatedOrderDTO);
                    Order updatedOrder = orderRepository.save(existingOrder);
                    return convertToDTO(updatedOrder);
                })
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado com o ID: " + orderId));
    }

    @Transactional
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    private Order convertToEntity(OrderDTO orderDTO) {
        Order orderEntity = new Order();
        orderEntity.setStatus(orderDTO.status());
        orderEntity.setTotalOrderValue(orderDTO.totalOrderValue());

        if (orderDTO.clientId() != null) {
            Client client = clientRepository.findById(orderDTO.clientId())
                    .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
            orderEntity.setClient(client);
        }

        if (orderDTO.orderItems() != null) {
            List<OrderItems> orderItems = orderDTO.orderItems().stream()
                    .map(this::convertToEntity)
                    .collect(Collectors.toList());
            orderEntity.setOrderItems(orderItems);
        }

        return orderEntity;
    }

    private OrderItems convertToEntity(OrderItemsDTO orderItemsDTO) {
        OrderItems orderItemsEntity = new OrderItems();
        orderItemsEntity.setQuantity(orderItemsDTO.quantity());

        return orderItemsEntity;
    }

    private OrderDTO convertToDTO(Order orderEntity) {
        return new OrderDTO(
                orderEntity.getId(),
                orderEntity.getOrderDate(),
                orderEntity.getStatus(),
                orderEntity.getTotalOrderValue(),
                (orderEntity.getClient() != null) ? orderEntity.getClient().getId() : null,
                convertToDTO(orderEntity.getOrderItems())
        );
    }

    private List<OrderItemsDTO> convertToDTO(List<OrderItems> orderItems) {
        return orderItems.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private OrderItemsDTO convertToDTO(OrderItems orderItemsEntity) {
        return new OrderItemsDTO(
                orderItemsEntity.getId(),
                orderItemsEntity.getQuantity(),
                orderItemsEntity.getProduct().getId(),
                orderItemsEntity.getProductName(),
                orderItemsEntity.getProductPrice()
        );
    }

    private void updateOrderEntity(Order existingOrder, OrderDTO updatedOrderDTO) {
        existingOrder.setStatus(updatedOrderDTO.status());
        existingOrder.setTotalOrderValue(updatedOrderDTO.totalOrderValue());
    }
}
