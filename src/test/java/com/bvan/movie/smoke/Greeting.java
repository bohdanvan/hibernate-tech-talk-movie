package com.bvan.movie.smoke;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Greeting {

    @Id
    @GeneratedValue
    private Long id;

    private String greeting;

    Greeting() {
    }

    public Greeting(String greeting) {
        this.greeting = greeting;
    }

    public Long getId() {
        return id;
    }

    public Greeting setId(Long id) {
        this.id = id;
        return this;
    }

    public String getGreeting() {
        return greeting;
    }

    public Greeting setGreeting(String greeting) {
        this.greeting = greeting;
        return this;
    }
}
