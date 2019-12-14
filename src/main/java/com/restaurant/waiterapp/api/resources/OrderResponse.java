package com.restaurant.waiterapp.api.resources;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse implements Serializable, Parcelable {

    private Long id;

    private String waiter;

    private String chef;

    private String bartender;

    private List<FoodResponse> dishes;

    private List<FoodResponse> beverages;

    private StageEnum stage;

    public OrderResponse() {
        super();
    }

    public OrderResponse(Long id, String waiter, String chef, String bartender, List<FoodResponse> dishes, List<FoodResponse> beverages, StageEnum stage) {
        this.id = id;
        this.waiter = waiter;
        this.chef = chef;
        this.bartender = bartender;
        this.dishes = dishes;
        this.beverages = beverages;
        this.stage = stage;
    }

    public String getWaiter() {
        return waiter;}



    public void setWaiter(String waiter) {
        this.waiter = waiter;
    }

    public String getChef() {
        return chef;
    }

    public void setChef(String chef) {
        this.chef = chef;
    }

    public String getBartender() {
        return bartender;
    }

    public void setBartender(String bartender) {
        this.bartender = bartender;
    }

    public List<FoodResponse> getDishes() {
        return dishes;
    }

    public void setDishes(List<FoodResponse> dishes) {
        this.dishes = dishes;
    }

    public List<FoodResponse> getBeverages() {
        return beverages;
    }

    public void setBeverages(List<FoodResponse> beverages) {
        this.beverages = beverages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StageEnum getStage() {
        return stage;
    }

    public void setStage(StageEnum stage) {
        this.stage = stage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.waiter);
        dest.writeString(this.chef);
        dest.writeString(this.bartender);
        dest.writeList(this.dishes);
        dest.writeList(this.beverages);
        dest.writeInt(this.stage == null ? -1 : this.stage.ordinal());
    }

    protected OrderResponse(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.waiter = in.readString();
        this.chef = in.readString();
        this.bartender = in.readString();
        this.dishes = new ArrayList<FoodResponse>();
        in.readList(this.dishes, FoodResponse.class.getClassLoader());
        this.beverages = new ArrayList<FoodResponse>();
        in.readList(this.beverages, FoodResponse.class.getClassLoader());
        int tmpStage = in.readInt();
        this.stage = tmpStage == -1 ? null : StageEnum.values()[tmpStage];
    }

    public static final Creator<OrderResponse> CREATOR = new Creator<OrderResponse>() {
        @Override
        public OrderResponse createFromParcel(Parcel source) {
            return new OrderResponse(source);
        }

        @Override
        public OrderResponse[] newArray(int size) {
            return new OrderResponse[size];
        }
    };
}