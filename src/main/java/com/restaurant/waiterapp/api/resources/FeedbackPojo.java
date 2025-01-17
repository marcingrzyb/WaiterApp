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

public class FeedbackPojo {
    private FeedbackEnum serviceGrade;
    private FeedbackEnum beverageGrade;
    private FeedbackEnum dishGrade;
    private Long orderId;

    public FeedbackPojo(FeedbackEnum serviceGrade, FeedbackEnum beverageGrade,
                        FeedbackEnum dishGrade, Long orderId) {
        this.serviceGrade = serviceGrade;
        this.beverageGrade = beverageGrade;
        this.dishGrade = dishGrade;
        this.orderId = orderId;
    }

    public FeedbackPojo() {
    }

    public FeedbackEnum getServiceGrade() {
        return serviceGrade;
    }

    public FeedbackEnum getBeverageGrade() {
        return beverageGrade;
    }

    public FeedbackEnum getDishGrade() {
        return dishGrade;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setServiceGrade(FeedbackEnum serviceGrade) {
        this.serviceGrade = serviceGrade;
    }

    public void setBeverageGrade(FeedbackEnum beverageGrade) {
        this.beverageGrade = beverageGrade;
    }

    public void setDishGrade(FeedbackEnum dishGrade) {
        this.dishGrade = dishGrade;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}