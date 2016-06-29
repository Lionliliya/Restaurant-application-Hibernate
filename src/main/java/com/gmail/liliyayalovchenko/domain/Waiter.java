package com.gmail.liliyayalovchenko.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Waiter extends Employee {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private List<Order> orderList;

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Waiter {\n").append(" id = ").append(getId()).append("\n").
                append(" name = ").append(getFirstName()).append(" ").append(getSecondName()).append("\n").
                append(" orders = {\n");
        orderList.forEach(order -> sb.append("   ").append(order).append(",\n"));
        sb.append("}\n").append(" }");
        return sb.toString();
    }
}
