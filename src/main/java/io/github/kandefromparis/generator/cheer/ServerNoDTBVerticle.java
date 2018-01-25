/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * https://en.wikipedia.org/wiki/List_of_Care_Bear_characters 
* https://fr.wikipedia.org/wiki/Bisounours
 */
package io.github.kandefromparis.generator.cheer;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author csabourdin
 */
public class ServerNoDTBVerticle extends AbstractVerticle {

    private final Map<Integer, Cheer> hugs = new LinkedHashMap<>();

    @Override
    public void start(Future<Void> fut) {

        // Create a router object.
        Router router = Router.router(vertx);

        // Serve static resources from the /assets directory
        router.route("/assets/*").handler(StaticHandler.create("assets"));
        router.get("/").handler(this::getRedirect);
        
        // version 1.0 no databases
        createSomeData();
        router.get(ConfAPICall.API_1_0_CHEERS).handler(this::getAll);
        router.route(ConfAPICall.API_1_0_CHEERS + "*").handler(BodyHandler.create());
        router.post(ConfAPICall.API_1_0_CHEERS).handler(this::addOne);
        router.get(ConfAPICall.API_1_0_CHEERS + "/:id").handler(this::getOne);
        router.delete(ConfAPICall.API_1_0_CHEERS + "/:id").handler(this::deleteOne);
        
        router.get(ConfAPICall.API_1_0_CHEERS + "Size").handler(this::getSize);
        router.get(ConfAPICall.API_1_0_RANDOMCHEERS).handler(this::getOneAtRandom);
        

        InfoVerticle info = new InfoVerticle();
        router.get(ConfAPICall.API_1_0_INFO_RANDOM).handler(info::getRandomValue);
        router.get(ConfAPICall.API_1_0_INFO_ENV).handler(info::getEnvValue);
        router.get(ConfAPICall.API_1_0_INFO_RUNTIME).handler(info::getMemoryValue);
        router.get(ConfAPICall.API_1_0_INFO_FILES).handler(info::getFilesValue);

        NSLookupVerticle ns = new NSLookupVerticle();
        router.route(ConfAPICall.API_1_0_INFO_DNS + "*").handler(BodyHandler.create());
        router.post(ConfAPICall.API_1_0_INFO_DNS).handler(ns::getNSLookup);

                
        ProbVerticle prob = new ProbVerticle();
        router.get(ConfAPICall.LIVENESS).handler(prob::getLiveness);
        router.get(ConfAPICall.READINESS).handler(prob::getReadiness);

        vertx
                .createHttpServer()
                .requestHandler(router::accept)
                .listen(
                        // Retrieve the port from the configuration,
                        // default to 8080.
                        config().getInteger("http.port", 8080),
                        result -> {
                            if (result.succeeded()) {
                                fut.complete();
                            } else {
                                fut.fail(result.cause());
                            }
                        }
                );
    }

    private void getRedirect(RoutingContext routingContext) {
        routingContext.response()
                .setStatusCode(307)
                .putHeader("Location", "/assets/index.html")
                .end();
    }

    private void addOne(RoutingContext routingContext) {
        // Read the request's content and create an instance of Cheer.
        final Cheer hug = Json.decodeValue(routingContext.getBodyAsString(),
                Cheer.class);
        // Add it to the backend map
        hugs.put(hug.getId(), hug);

        // Return the created hug as JSON
        routingContext.response()
                .setStatusCode(201)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(hug));
    }
    private void getSize(RoutingContext routingContext) {
        ValueInfo info = new ValueInfo("size", String.valueOf(this.hugs.size()), "Integer");

        // Return the created hug as JSON
        routingContext.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(info));
    }

    private void getOne(RoutingContext routingContext) {
        final String id = routingContext.request().getParam("id");
        if (id == null) {
            routingContext.response().setStatusCode(400).end();
        } else {
            final Integer idAsInteger = Integer.valueOf(id);
            Cheer hug = hugs.get(idAsInteger);
            if (hug == null) {
                routingContext.response().setStatusCode(404).end();
            } else {
                routingContext.response()
                        .putHeader("content-type", "application/json; charset=utf-8")
                        .end(Json.encodePrettily(hug));
            }
        }
    }

    private void getOneAtRandom(RoutingContext routingContext) {
        int size = this.hugs.size();
        // SecureRandom is more secure (less predictable then Random
        // but this is not the purpose of this demo
        Random rand = new Random();
        Object key = this.hugs.keySet().toArray()[rand.nextInt(size)];
        Cheer hug = this.hugs.get(key);
        if (hug == null) {
            routingContext.response().setStatusCode(404).end();
        } else {
            routingContext.response()
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(Json.encodePrettily(hug));
        }

    }

    private void updateOne(RoutingContext routingContext) {
        final String id = routingContext.request().getParam("id");
        JsonObject json = routingContext.getBodyAsJson();
        if (id == null || json == null) {
            routingContext.response().setStatusCode(400).end();
        } else {
            final Integer idAsInteger = Integer.valueOf(id);
            Cheer hug = hugs.get(idAsInteger);
            if (hug == null) {
                routingContext.response().setStatusCode(404).end();
            } else {
                hug.setIntro(json.getString("intro"));
                hug.setCause(json.getString("cause"));
                routingContext.response()
                        .putHeader("content-type", "application/json; charset=utf-8")
                        .end(Json.encodePrettily(hug));
            }
        }
    }
/**
 * @todo it might be nice to have a check protocle
 * @param routingContext 
 */
    private void deleteOne(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id");
        if (id == null) {
            routingContext.response().setStatusCode(400).end();
        } else {
            Integer idAsInteger = Integer.valueOf(id);
            hugs.remove(idAsInteger);
        }
        routingContext.response().setStatusCode(204).end();
    }

    private void getAll(RoutingContext routingContext) {
        // Write the HTTP response
        // The response is in JSON using the utf-8 encoding
        // We returns the list of bottles
        routingContext.response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(hugs.values()));
    }

    private void createSomeData() {
        Cheer cool = new Cheer("Your are super", "because you stand steel ;-)");
        hugs.put(cool.getId(), cool);
        cool = new Cheer("You look beatifull", "because you shine happiness");
        hugs.put(cool.getId(), cool);
        cool = new Cheer("You are great", "because you always stand up for me");
        hugs.put(cool.getId(), cool);
        cool = new Cheer("You enlight my day", "because your intelligence shine");
        hugs.put(cool.getId(), cool);
        cool = new Cheer("It is great to be your friend", "because you always listen");
        hugs.put(cool.getId(), cool);
        cool = new Cheer("You are great-3", "because you always stand up for me");
        hugs.put(cool.getId(), cool);
        cool = new Cheer("You are great-4", "because you always stand up for me");
        hugs.put(cool.getId(), cool);
        cool = new Cheer("You are great-5", "because you always stand up for me");
        hugs.put(cool.getId(), cool);
        cool = new Cheer("You are great-6", "because you always stand up for me");
        hugs.put(cool.getId(), cool);
        cool = new Cheer("You are great-7", "because you always stand up for me");
        hugs.put(cool.getId(), cool);
        cool = new Cheer("You are great-8", "because you always stand up for me");
        hugs.put(cool.getId(), cool);
        cool = new Cheer("You are great-9", "because you always stand up for me");
        hugs.put(cool.getId(), cool);
        cool = new Cheer("You are great-10", "because you always stand up for me");
        hugs.put(cool.getId(), cool);
        cool = new Cheer("You are great-11", "because you always stand up for me");
        hugs.put(cool.getId(), cool);
        cool = new Cheer("You are great-12", "because you always stand up for me");
        hugs.put(cool.getId(), cool);
        cool = new Cheer("You are great-13", "because you always stand up for me");
        hugs.put(cool.getId(), cool);
        cool = new Cheer("You are great-14", "because you always stand up for me");
        hugs.put(cool.getId(), cool);
        cool = new Cheer("You are great-15", "because you always stand up for me");
        hugs.put(cool.getId(), cool);

    }
}
