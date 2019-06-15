package com.yx.model;

public class CustomerListModel {
    public String title;
    public int type;
    public boolean ispick;
    public CustomerModel customerModel;

    public CustomerListModel(String title, int type, boolean ispick, CustomerModel customerModel) {
        this.title = title;
        this.type = type;
        this.ispick = ispick;
        this.customerModel = customerModel;
    }
}
