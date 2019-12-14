package com.restaurant.waiterapp.api.resources;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
@JsonIgnoreProperties
public class TableResponse implements Serializable, Parcelable {

    private Long id;
    private ArrayList<ReservationResponse> tableReservations;
    public TableResponse(){super();}
    public TableResponse(Long id, ArrayList<ReservationResponse> tableReservations) {
        this.id = id;
        this.tableReservations = tableReservations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArrayList<ReservationResponse> getTableReservations() {
        return tableReservations;
    }

    public void setTableReservations(ArrayList<ReservationResponse> tableReservations) {
        this.tableReservations = tableReservations;
    }

    @Override
    public String toString() {
        String waiter="No waiter assigned";
        if(isNotEmpty(tableReservations)&& tableReservations.get(0).getOrderEntity()!=null){
            waiter=tableReservations.get(0).getOrderEntity().getWaiter();
        }
        return "TableResponse{" +
                "id=" + id +
                ", tableReservations=" + waiter+
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeList(this.tableReservations);
    }

    protected TableResponse(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.tableReservations = new ArrayList<ReservationResponse>();
        in.readList(this.tableReservations, ReservationResponse.class.getClassLoader());
    }

    public static final Creator<TableResponse> CREATOR = new Creator<TableResponse>() {
        @Override
        public TableResponse createFromParcel(Parcel source) {
            return new TableResponse(source);
        }

        @Override
        public TableResponse[] newArray(int size) {
            return new TableResponse[size];
        }
    };
}
