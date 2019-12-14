package com.restaurant.waiterapp.api.resources;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.io.Serializable;
import java.time.LocalDateTime;

@JsonIgnoreProperties
public class ReservationResponse implements Serializable, Parcelable {
    private Long id;
    private Integer duration;

    private String customerName;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timeOfReservation;

    private OrderResponse orderEntity;

    public ReservationResponse(){super();}

    public ReservationResponse(Long id, Integer duration, String customerName, LocalDateTime timeOfReservation, OrderResponse orderEntity) {
        this.id = id;
        this.duration = duration;
        this.customerName = customerName;
        this.timeOfReservation = timeOfReservation;
        this.orderEntity = orderEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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

    public OrderResponse getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderResponse orderEntity) {
        this.orderEntity = orderEntity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.duration);
        dest.writeString(this.customerName);
        dest.writeSerializable(this.timeOfReservation);
        dest.writeParcelable(this.orderEntity, flags);
    }

    protected ReservationResponse(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.duration = (Integer) in.readValue(Integer.class.getClassLoader());
        this.customerName = in.readString();
        this.timeOfReservation = (LocalDateTime) in.readSerializable();
        this.orderEntity = in.readParcelable(OrderResponse.class.getClassLoader());
    }

    public static final Creator<ReservationResponse> CREATOR = new Creator<ReservationResponse>() {
        @Override
        public ReservationResponse createFromParcel(Parcel source) {
            return new ReservationResponse(source);
        }

        @Override
        public ReservationResponse[] newArray(int size) {
            return new ReservationResponse[size];
        }
    };
}
