package com.yx.model;

import java.io.Serializable;

public class CustomerModel implements Serializable {
    public int customer_number;
    public int customer_type;
    public int customer_id;
    public String customer_name;
    public String customer_time;
    public String customer_title;

    public CustomerModel(int customer_number, int customer_type, int customer_id, String customer_name, String customer_time, String customer_title) {
        this.customer_number = customer_number;
        this.customer_type = customer_type;
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.customer_time = customer_time;
        this.customer_title = customer_title;
    }

    public CustomerModel() {
    }

    @Override
    public String toString() {
        return "CustomerModel{" +
                "customer_number=" + customer_number +
                ", customer_type=" + customer_type +
                ", customer_id=" + customer_id +
                ", customer_name='" + customer_name + '\'' +
                ", customer_time='" + customer_time + '\'' +
                ", customer_title='" + customer_title + '\'' +
                '}';
    }
}
