package com.restaurant.waiterapp.api.resources;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ReservationResponse implements Serializable {
    private final Long id;
    private String customerName;

    private LocalDateTime timeOfReservation;

    private TableResponse tableReservation;

    private OrderResponse orderEntity;

    public ReservationResponse(Long id, String customerName, LocalDateTime timeOfReservation, TableResponse tableReservation, OrderResponse orderEntity) {
        this.id = id;
        this.customerName = customerName;
        this.timeOfReservation = timeOfReservation;
        this.tableReservation = tableReservation;
        this.orderEntity = orderEntity;
    }

    public Long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDateTime getTimeOfReservation() {
        return timeOfReservation;
    }

    public void setTimeOfReservation(LocalDateTime timeOfReservation) {
        this.timeOfReservation = timeOfReservation;
    }

    public TableResponse getTableReservation() {
        return tableReservation;
    }

    public void setTableReservation(TableResponse tableReservation) {
        this.tableReservation = tableReservation;
    }

    public OrderResponse getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderResponse orderEntity) {
        this.orderEntity = orderEntity;
    }
}
