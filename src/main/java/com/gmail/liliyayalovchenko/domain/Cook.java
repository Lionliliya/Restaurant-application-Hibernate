package com.gmail.liliyayalovchenko.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Cook extends Employee {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private List<ReadyMeal> readyMealList;

    public List<ReadyMeal> getReadyMealList() {
        return readyMealList;
    }

    public void setReadyMealList(List<ReadyMeal> readyMealList) {
        this.readyMealList = readyMealList;
    }

    @Override
    public String toString() {
        return "Cook{" +
                "readyMealList=" + printReadyMealList() +
                '}';
    }

    private String printReadyMealList() {
        StringBuilder sb = new StringBuilder();
        for (ReadyMeal readyMeal : readyMealList) {
            sb.append("Ready meal number " + readyMeal.getDishNumber() + "{ \n").append("   dish  = ").append(readyMeal.getDishId().getName()).append("\n  ").
                    append("employee = ").append(readyMeal.getEmployeeId().getFirstName() + " " + readyMeal.getEmployeeId().getSecondName()).
                    append("\n  ").append("  meal date = ").append(readyMeal.getMealDate()).append("\n  ").
                    append("  order = ").append(readyMeal.getOrderId().getOrderNumber()).append("\n  }\n");
        }

        return sb.toString();

    }
}
