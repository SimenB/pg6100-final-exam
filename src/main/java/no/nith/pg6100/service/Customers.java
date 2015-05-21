package no.nith.pg6100.service;

import no.nith.pg6100.service.dto.Customer;

import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class Customers {
    private List<Customer> customers = new ArrayList<>();

    public int add(Customer customer) {
        customers.add(customer);
        return customers.size() - 1;
    }

    public Customer get(int index) {
        return customers.get(index);
    }

    public List<Customer> getAll() {
        return customers;
    }
}
