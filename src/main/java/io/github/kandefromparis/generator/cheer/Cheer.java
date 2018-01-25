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
public class Cheer {

    private static final AtomicInteger COUNTER = new AtomicInteger();

    private final int id;

    private String intro;

    private String cause;
    
    public Cheer(String intro, String cause) {
        this.id = COUNTER.getAndIncrement();
        this.intro = intro;
        this.cause = cause;
    }

    public Cheer() {
        this.id = COUNTER.getAndIncrement();
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the intro
     */
    public String getIntro() {
        return intro;
    }

    /**
     * @param intro the intro to set
     */
    public void setIntro(String intro) {
        this.intro = intro;
    }

    /**
     * @return the cause
     */
    public String getCause() {
        return cause;
    }

    /**
     * @param cause the cause to set
     */
    public void setCause(String cause) {
        this.cause = cause;
    }

}
