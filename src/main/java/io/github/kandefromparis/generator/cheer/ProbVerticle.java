/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kandefromparis.generator.cheer;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.RoutingContext;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author csabourdin
 */
class ProbVerticle extends AbstractVerticle {

    Integer livenesscounter = 0;
    Integer readinessCounter = 0;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    public void getLiveness(RoutingContext routingContext) {
        getOK(routingContext, livenesscounter++);
    }

    public void getReadiness(RoutingContext routingContext) {
        getOK(routingContext, readinessCounter++);
    }

    public void getOK(RoutingContext routingContext, Integer count) {
        if ((System.getenv("CHEER_LOG_LEVEL") != null)
                && (System.getenv("CHEER_LOG_LEVEL").equals("INFO") || System.getenv("CHEER_LOG_LEVEL").equals("DEBUG"))) {
            if (count > Integer.MAX_VALUE - 5) {
                count = 0;
                System.out.println("Varibale count tend to Max, Rolling count to 0");
            }
            System.out.println(sdf.format(Date.from(Instant.now()))+" "+routingContext.request().absoluteURI() + ("OK - " + count + " times"));

        }

        if (isAPositiveReasonableNumeric(System.getenv("CHEER_SLOW_READINESS")) &&
                routingContext.request().path().equals(ConfAPICall.READINESS)) {
            Integer duration = Integer.parseInt(System.getenv("CHEER_SLOW_READINESS"));
            System.out.println(sdf.format(Date.from(Instant.now()))+" "+"Waiting " + duration + " micro-secondes before respond to readiness prob");
            try {
                Thread.sleep(duration * 100);
            } catch (InterruptedException ex) {
                Logger.getLogger(ProbVerticle.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (isAPositiveReasonableNumeric(System.getenv("CHEER_SLOW_LIVENESS")) && 
                routingContext.request().path().equals(ConfAPICall.LIVENESS)) {
            Integer duration = Integer.parseInt(System.getenv("CHEER_SLOW_LIVENESS"));
            System.out.println(sdf.format(Date.from(Instant.now()))+" "+"Waiting " + duration + " micro-secondes before respond to liveness prob");
            try {
                Thread.sleep(duration * 100);
            } catch (InterruptedException ex) {
                Logger.getLogger(ProbVerticle.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        routingContext.response()
                .putHeader("content-type", "text/html; charset=utf-8")
                .setStatusCode(200)
                .end("OK - " + count + " times");

    }

    public static boolean isAPositiveReasonableNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer d = Integer.parseInt(str);
            if (d <= 1) {
                return false;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
