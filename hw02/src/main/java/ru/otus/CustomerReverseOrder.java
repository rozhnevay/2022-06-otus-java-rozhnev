package ru.otus;


import java.util.LinkedList;
import java.util.List;

public class CustomerReverseOrder {
    private final LinkedList<Customer> customerList = new LinkedList<>();

    public void add(Customer customer) {
        customerList.add(customer);
    }

    public Customer take() {
        return customerList.pollLast();
    }
}
