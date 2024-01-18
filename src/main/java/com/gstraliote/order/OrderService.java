package com.gstraliote.order;

import com.gstraliote.client.Client;
import com.gstraliote.client.ClientRepository;
import com.gstraliote.orderItems.OrderItemsDTO;
import com.gstraliote.orderItems.OrderItems;
import com.gstraliote.product.Product;
import com.gstraliote.product.ProductRepository;
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
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ClientRepository clientRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void createOrder(OrderDTO orderDTO) {
        Order orderEntity = convertToEntity(orderDTO);

        List<Product> products = orderDTO.orderItems().stream()
                .map(orderItemsDTO -> productRepository.findById(orderItemsDTO.productId())
                        .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com o ID: " + orderItemsDTO.productId())))
                .toList();

        List<OrderItems> orderItems = orderDTO.orderItems().stream()
                .map(orderItemsDTO -> convertOrderItemsToEntity(orderItemsDTO, orderEntity, products))
                .collect(Collectors.toList());

        orderEntity.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(orderEntity);

        convertToDTO(savedOrder);
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
    public void updateOrder(Long orderId, OrderDTO updatedOrderDTO) {
        orderRepository.findById(orderId)
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

        return orderEntity;
    }

    private OrderItems convertOrderItemsToEntity(OrderItemsDTO orderItemsDTO, Order orderEntity, List<Product> products) {
        OrderItems orderItemsEntity = new OrderItems();
        orderItemsEntity.setQuantity(orderItemsDTO.quantity());

        Product product = products.stream()
                .filter(p -> p.getId().equals(orderItemsDTO.productId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com o ID: " + orderItemsDTO.productId()));

        orderItemsEntity.setProduct(product);

        orderItemsEntity.setOrder(orderEntity);

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
                orderItemsEntity.getProduct().getId()
        );
    }

    private void updateOrderEntity(Order existingOrder, OrderDTO updatedOrderDTO) {
        existingOrder.setStatus(updatedOrderDTO.status());
        existingOrder.setTotalOrderValue(updatedOrderDTO.totalOrderValue());
    }
}
