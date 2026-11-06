package com.example.AppTienda.service;

import com.example.AppTienda.dto.request.OrderRequestDTO;
import com.example.AppTienda.dto.response.OrderResponseDTO;
import com.example.AppTienda.entity.Orders;
import com.example.AppTienda.entity.Products;
import com.example.AppTienda.entity.Users;
import com.example.AppTienda.enums.OrderStatusEnum;
import com.example.AppTienda.enums.RoleEnum;
import com.example.AppTienda.exception.CustomException;
import com.example.AppTienda.repository.OrdersRepository;
import com.example.AppTienda.repository.ProductsRepository;
import com.example.AppTienda.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final ProductsRepository productsRepository;
    private final UserRepository userRepository;
    private final ProductsService productsService;
    private final UsersService usersService;

    public OrdersService(OrdersRepository ordersRepository,
                         ProductsRepository productsRepository,
                         UserRepository userRepository,
                         ProductsService productsService,
                         UsersService usersService) {
        this.ordersRepository = ordersRepository;
        this.productsRepository = productsRepository;
        this.userRepository = userRepository;
        this.productsService = productsService;
        this.usersService = usersService;
    }

    public OrderResponseDTO createOrder(OrderRequestDTO dto, Integer clientId) {
        Products product = productsRepository.findById(dto.getProductId())
                .orElseThrow(() -> new CustomException("Producto no encontrado", HttpStatus.NOT_FOUND));

        if (product.getStock() < dto.getQuantity()) {
            throw new CustomException("Stock insuficiente para realizar el pedido", HttpStatus.BAD_REQUEST);
        }

        Users client = userRepository.findById(clientId)
                .orElseThrow(() -> new CustomException("Cliente no encontrado", HttpStatus.NOT_FOUND));
       
        product.setStock(product.getStock() - dto.getQuantity());
        productsRepository.save(product);
       
        BigDecimal total = product.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity()));

        Orders order = Orders.builder()
                .client(client)
                .product(product)
                .quantity(dto.getQuantity())
                .total(total)
                .status(OrderStatusEnum.PENDIENTE)
                .build();

        return mapToDTO(ordersRepository.save(order));
    }

    public List<OrderResponseDTO> getMyOrders(Integer clientId) {
        return ordersRepository.findByClientId(clientId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<OrderResponseDTO> getSalesOrders(Integer sellerId) {
        return ordersRepository.findByProductSellerId(sellerId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<OrderResponseDTO> getAllOrders() {
        return ordersRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public OrderResponseDTO updateStatus(Integer orderId, OrderStatusEnum newStatus, Integer currentUserId, Integer currentUserRolId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new CustomException("Pedido no encontrado", HttpStatus.NOT_FOUND));

        if (currentUserRolId == RoleEnum.VENDEDOR.getId() && !order.getProduct().getSeller().getId().equals(currentUserId)) {
            throw new CustomException("Acceso denegado: no puedes modificar el estado de un pedido ajeno", HttpStatus.FORBIDDEN);
        }

        order.setStatus(newStatus);
        return mapToDTO(ordersRepository.save(order));
    }

    public OrderResponseDTO cancelOrder(Integer orderId, Integer clientId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new CustomException("Pedido no encontrado", HttpStatus.NOT_FOUND));

        if (!order.getClient().getId().equals(clientId)) {
            throw new CustomException("Acceso denegado: el pedido pertenece a otro cliente", HttpStatus.FORBIDDEN);
        }

        if (order.getStatus() != OrderStatusEnum.PENDIENTE) {
            throw new CustomException("Solo se pueden cancelar pedidos en estado PENDIENTE", HttpStatus.BAD_REQUEST);
        }

        order.setStatus(OrderStatusEnum.CANCELADO);
    
        Products product = order.getProduct();
        product.setStock(product.getStock() + order.getQuantity());
        productsRepository.save(product);

        return mapToDTO(ordersRepository.save(order));
    }

    private OrderResponseDTO mapToDTO(Orders order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .client(usersService.mapToUserResponse(order.getClient()))
                .product(productsService.mapToDTO(order.getProduct()))
                .quantity(order.getQuantity())
                .total(order.getTotal())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .build();
    }
}