/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kandefromparis.generator.cheer;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author csabourdin
 */
public class NSLookupVerticle extends AbstractVerticle {

    public void getNSLookup(RoutingContext routingContext) {
        if (routingContext == null) {
            throw new NullPointerException("routingContext is null you might need to add a bodyhandler");
        }
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        final String fqdn = bodyAsJson.getString("fqdn");
        
        if (fqdn == null || bodyAsJson == null) {
            routingContext.response().setStatusCode(400).end();
        } else {
            try {
                InetAddress inetHost = InetAddress.getByName(fqdn);

                JsonObject responseJson = new JsonObject();                
                responseJson.put("host", inetHost.getHostName());
                responseJson.put("ip", inetHost.getHostAddress());
                responseJson.put("isReachable", inetHost.isReachable(1000));
                responseJson.put("isAnyLocalAddress", inetHost.isAnyLocalAddress());
                responseJson.put("isMulticastAddress", inetHost.isMulticastAddress());

                routingContext.response()
                        .setStatusCode(200)
                        .putHeader("content-type", "application/json; charset=utf-8")
                        .end(Json.encodePrettily(responseJson));

            } catch (UnknownHostException ex) {
                routingContext.response()
                        .setStatusCode(406)//406	Not Acceptable
                        .putHeader("X-exception-message", ex.getMessage())
                        .end();
                System.out.println("Unrecognized host");
            } catch (IOException ex) {
                routingContext.response()
                        .setStatusCode(406)//406	Not Acceptable
                        .putHeader("X-exception-message", ex.getMessage())
                        .end();
            }

        }
    }
}
