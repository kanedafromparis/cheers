/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kandefromparis.generator.cheer;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.RoutingContext;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author csabourdin
 */
class ProbVerticle extends AbstractVerticle {

    Integer livenesscounter=0;
    Integer readinessCounter=0;
    public void getLiveness(RoutingContext routingContext) {
        getOK(routingContext, livenesscounter++);
    }

    public void getReadiness(RoutingContext routingContext) {
        getOK(routingContext, readinessCounter++);
    }

    public void getOK(RoutingContext routingContext, Integer count) {
        if ((System.getenv("CHEER_LOG_LEVEL") != null)
                && (System.getenv("CHEER_LOG_LEVEL").equals("INFO")|| System.getenv("CHEER_LOG_LEVEL").equals("DEBUG"))) {
            System.out.println(routingContext.request().absoluteURI());

        }

        if (isNumeric(System.getenv("CHEER_SLOW_READINESS"))) {
            Integer duration = Integer.parseInt(System.getenv("CHEER_SLOW_READINESS"));
            System.out.println("Waiting " + duration + " micro-secondes before respond to readiness prob");
            try {
                Thread.sleep(duration * 100);
            } catch (InterruptedException ex) {
                Logger.getLogger(ProbVerticle.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (isNumeric(System.getenv("CHEER_SLOW_LIVENESS"))) {
            Integer duration = Integer.parseInt(System.getenv("CHEER_SLOW_LIVENESS"));
            System.out.println("Waiting " + duration + " micro-secondes before respond to liveness prob");
            try {
                Thread.sleep(duration * 100);
            } catch (InterruptedException ex) {
                Logger.getLogger(ProbVerticle.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        routingContext.response()
                .putHeader("content-type", "text/html; charset=utf-8")
                .setStatusCode(200)
                .end("OK - "+count+" times");

    }

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
