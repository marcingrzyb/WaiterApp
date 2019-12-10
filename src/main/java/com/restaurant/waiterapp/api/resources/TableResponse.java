package com.restaurant.waiterapp.api.resources;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

public class TableResponse implements Serializable {

    private Long id;
    private List<ReservationResponse> tableReservations;

    public TableResponse(Long id, List<ReservationResponse> tableReservations) {
        this.id = id;
        this.tableReservations = tableReservations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<ReservationResponse> getTableReservations() {
        return tableReservations;
    }

    public void setTableReservations(List<ReservationResponse> tableReservations) {
        this.tableReservations = tableReservations;
    }

    @Override
    public String toString() {
        String waiter="";
        if(isNotEmpty(tableReservations)){
            waiter=tableReservations.get(0).getOrderEntity().getWaiter();
        }
        return "TableResponse{" +
                "id=" + id +
                ", tableReservations=" + waiter+
                '}';
    }
}
