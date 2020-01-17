/*
 *
 *   Copyright 2020 Marcin Grzyb
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

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
