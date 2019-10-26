package com.example.barberappoint;

public class Appointment {
    private String id;
    private String name;
    private String service;
    private String date;
    private String hour;
    private String price;
    private String barber;

    public Appointment(){}

    public Appointment(String name, String service, String date, String hour, String price, String barber) {
        this.setId(id);
        this.setName(name);
        this.setService(service);
        this.setDate(date);
        this.setHour(hour);
        this.setPrice(price);
        this.setBarber(barber);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBarber() {
        return barber;
    }

    public void setBarber(String barber) {
        this.barber = barber;
    }
}
