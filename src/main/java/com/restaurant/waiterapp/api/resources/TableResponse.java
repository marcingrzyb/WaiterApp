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
        return "Table: "+ id + ", waiter: "+ waiter;
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
        this.tableReservations = new ArrayList<>();
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
