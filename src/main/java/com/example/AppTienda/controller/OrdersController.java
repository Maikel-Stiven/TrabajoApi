package com.example.AppTienda.controller;

import com.example.AppTienda.dto.HttpGlobalResponseDTO;
import com.example.AppTienda.dto.request.OrderRequestDTO;
import com.example.AppTienda.dto.response.OrderResponseDTO;
import com.example.AppTienda.enums.OrderStatusEnum;
import com.example.AppTienda.enums.RoleEnum;
import com.example.AppTienda.security.RequiresRole;
import com.example.AppTienda.service.OrdersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tienda/orders")
public class OrdersController {

    private final OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping
    @RequiresRole(RoleEnum.CLIENTE)
    public ResponseEntity<HttpGlobalResponseDTO<OrderResponseDTO>> createOrder(
            @Valid @RequestBody OrderRequestDTO requestDTO,
            HttpServletRequest request) {
        Integer clientId = (Integer) request.getAttribute("userId");
        OrderResponseDTO order = ordersService.createOrder(requestDTO, clientId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(HttpGlobalResponseDTO.of("Pedido creado exitosamente", order));
    }

    @GetMapping("/mine")
    @RequiresRole(RoleEnum.CLIENTE)
    public ResponseEntity<HttpGlobalResponseDTO<List<OrderResponseDTO>>> getMyOrders(HttpServletRequest request) {
        Integer clientId = (Integer) request.getAttribute("userId");
        List<OrderResponseDTO> orders = ordersService.getMyOrders(clientId);
        return ResponseEntity.ok(HttpGlobalResponseDTO.of("Mis pedidos obtenidos exitosamente", orders));
    }

    @GetMapping("/sales")
    @RequiresRole(RoleEnum.VENDEDOR)
    public ResponseEntity<HttpGlobalResponseDTO<List<OrderResponseDTO>>> getSalesOrders(HttpServletRequest request) {
        Integer sellerId = (Integer) request.getAttribute("userId");
        List<OrderResponseDTO> sales = ordersService.getSalesOrders(sellerId);
        return ResponseEntity.ok(HttpGlobalResponseDTO.of("Ventas obtenidas exitosamente", sales));
    }

    @GetMapping
    @RequiresRole(RoleEnum.ADMIN)
    public ResponseEntity<HttpGlobalResponseDTO<List<OrderResponseDTO>>> getAllOrders() {
        List<OrderResponseDTO> orders = ordersService.getAllOrders();
        return ResponseEntity.ok(HttpGlobalResponseDTO.of("Listado general de pedidos", orders));
    }

    @PatchMapping("/{id}/status")
    @RequiresRole({RoleEnum.VENDEDOR, RoleEnum.ADMIN})
    public ResponseEntity<HttpGlobalResponseDTO<OrderResponseDTO>> updateOrderStatus(
            @PathVariable Integer id,
            @RequestParam OrderStatusEnum status,
            HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        Integer rolId = (Integer) request.getAttribute("rolId");
        OrderResponseDTO order = ordersService.updateStatus(id, status, userId, rolId);
        return ResponseEntity.ok(HttpGlobalResponseDTO.of("Estado de pedido actualizado", order));
    }

    @PatchMapping("/{id}/cancel")
    @RequiresRole(RoleEnum.CLIENTE)
    public ResponseEntity<HttpGlobalResponseDTO<OrderResponseDTO>> cancelOrder(
            @PathVariable Integer id,
            HttpServletRequest request) {
        Integer clientId = (Integer) request.getAttribute("userId");
        OrderResponseDTO order = ordersService.cancelOrder(id, clientId);
        return ResponseEntity.ok(HttpGlobalResponseDTO.of("Pedido cancelado exitosamente", order));
    }
}