/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kandefromparis.generator.cheer;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author csabourdin
 */
public class ValueInfo {

    private static final AtomicInteger COUNTER = new AtomicInteger();

    private final int id;

    private String name;

    private String value;

    private String type = "String";

    public ValueInfo(String name, String value) {
        this(name, value, "String");
    }

    public ValueInfo(String name, Integer value) {
        this(name, String.valueOf(value), "Integer");
    }

    public ValueInfo(String name, Boolean value) {
        this(name, String.valueOf(value), "Boolean");
    }
    
    public ValueInfo(String name, String value, String type) {
        this.id = COUNTER.getAndIncrement();
        this.name = name;
        this.value = value;
        this.type = type;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

}
