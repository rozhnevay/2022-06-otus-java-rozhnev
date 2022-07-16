package ru.otus;


import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    private final TreeMap<Customer, String> customerStringTreeMap = new TreeMap<>(Comparator.comparing(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        return entryClone(customerStringTreeMap.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return entryClone(customerStringTreeMap.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        customerStringTreeMap.put(customer, data);
    }

    private Map.Entry<Customer, String> entryClone(Map.Entry<Customer, String> original) {
        if (original == null) {
            return null;
        }

        Customer customer = original.getKey();
        Customer clone = new Customer(customer.getId(), customer.getName(), customer.getScores());
        return new AbstractMap.SimpleEntry<>(clone, original.getValue());
    }
}
