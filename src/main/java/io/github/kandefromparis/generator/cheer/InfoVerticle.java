/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kandefromparis.generator.cheer;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author csabourdin
 */
public class InfoVerticle extends AbstractVerticle {

    public void getRandomValue(RoutingContext routingContext) {
        Random rand = new Random();
        ValueInfo info = new ValueInfo("Random Value", String.valueOf(rand.nextInt()), "Integer");

        routingContext.response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(info));

    }

    public void getEnvValue(RoutingContext routingContext) {
        //
               List<ValueInfo> env = new ArrayList();

        Enumeration<Object> keys = System.getProperties().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = System.getProperties().get(key);
            env.add(new ValueInfo("properties java:"+String.valueOf(key), String.valueOf(value)));
        }
         
        Iterator<String> keys_ = System.getenv().keySet().iterator();
        while (keys_.hasNext()) {
            Object key = keys_.next();
            Object value = System.getenv().get(key);
            env.add(new ValueInfo(String.valueOf(key), String.valueOf(value)));
        }
        //
        routingContext.response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(env));

    }
    /**
     * 
     * @todo
     * @param routingContext 
     */
    public void getFilesValue(RoutingContext routingContext) {
        
        routingContext.response()
                .setStatusCode(501) // https://fr.wikipedia.org/wiki/Liste_des_codes_HTTP
                .end();

    }

    public void getMemoryValue(RoutingContext routingContext) {
        final Map<String, String> mem = new LinkedHashMap<>();
        Runtime runtime = Runtime.getRuntime();
        NumberFormat format = NumberFormat.getInstance();
        StringBuilder sb = new StringBuilder();
        long maxMemory = runtime.maxMemory();
        long allocatedMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();

        mem.put("free memory", format.format(freeMemory / 1024));
        mem.put("allocated memory", format.format(allocatedMemory / 1024));
        mem.put("max memory", format.format(maxMemory / 1024));
        mem.put("total free memory", format.format((freeMemory + (maxMemory - allocatedMemory)) / 1024));
        mem.put("available processors", String.valueOf(runtime.availableProcessors()));

        routingContext.response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(mem));

    }

}
