package no.nith.pg6100.service.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
