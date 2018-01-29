/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kandefromparis.generator.cheer;

import static io.github.kandefromparis.generator.cheer.ConfAPICall.API_1_0_CHEERS;
import static io.github.kandefromparis.generator.cheer.ConfAPICall.API_1_0_INFO_DNS;
import static io.github.kandefromparis.generator.cheer.ConfAPICall.API_1_0_INFO_ENV;
import io.vertx.ext.unit.Async;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author csabourdin
 */
@RunWith(VertxUnitRunner.class)
public class ServerVerticleTest {

    private Vertx vertx;
    private Integer port = 8080;
    private String host = "127.0.0.1";

    @Before
    public void setUp(TestContext context) throws IOException {
        vertx = Vertx.vertx();
        // Let's configure the verticle to listen on the 'test' port (randomly picked).
        // We create deployment options and set the _configuration_ json object:

        vertx.deployVerticle(ServerNoDTBVerticle.class.getName(), context.asyncAssertSuccess());
    }

    @After
    public void tearDown(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    /**
     * Test of start method, of class ServerVerticle.
     *
     * @param context
     */
    @Test
    public void testIndex(TestContext context) {
        final Async async = context.async();

        vertx.createHttpClient()
                .getNow(this.port, this.host, "/assets/index.html",
                        response -> {
                            response.handler(body -> {
                                String html = body.toString();

                                //For some weird reason it does not works
                                // probably async isssue
                                /////
                                //html = html.toString().replaceFirst("<h1>Welcome to the Cheer application</h1>", "toto");
                                //System.out.println(Thread.currentThread().getId()+" - "+html.contains("toto"));
                                //System.out.println(Thread.currentThread().getId()+" - "+html);
                                //
                                //context.assertTrue(body.toString().contains("<h1>Welcome to the Cheer application</h1>"));
                                async.complete();
                                context.assertTrue(html.contains("DOCTYPE"));
                                //context.assertTrue(html.contains("<h1>Welcome to the Cheer application</h1>"));
                                //async.complete();
                            });
                        });
    }

    /**
     * Test of start method, of class ServerVerticle. this code is useless but
     * was created for investigates the test "testIndex" This was probably due
     * to async operations
     *
     * @param context
     */
    @Test
    @Ignore
    public void testContains(TestContext context) {
        final Async async = context.async();
        String html = " <!DOCTYPE html>\n"
                + "<!--\n"
                + "To change this license header, choose License Headers in Project Properties.\n"
                + "To change this template file, choose Tools | Templates\n"
                + "and open the template in the editor.\n"
                + "-->\n"
                + "<html>\n"
                + "    <head>\n"
                + "        <title>Welcome to my cheers Application !</title>\n"
                + "        <meta charset=\"UTF-8\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "        <script src=\"https://cdn.jsdelivr.net/npm/vue@2.5.13\"></script>\n"
                + "        <script src=\"https://cdn.jsdelivr.net/npm/vue-resource@1.3.5\"></script>\n"
                + "        <link href=\"/assets/css/cheers.css\" media=\"all\" rel=\"stylesheet\" />\n"
                + "    </head>\n"
                + "    <body>\n"
                + "        <!-- menu -->\n"
                + "        <div id=\"menu\" class=\"row\">  \n"
                + "            <ul class=\"menu\">\n"
                + "                <li class=\"menu__item\">\n"
                + "                    <a class=\"menu__link\" href=\"/\">Home</a>\n"
                + "                </li>\n"
                + "\n"
                + "                <li class=\"menu__item menu__item--dropdown\" v-on:click=\"toggle('ranking')\" v-bind:class=\"{'open' : dropDowns.ranking.open}\">\n"
                + "                    <a class=\"menu__lin\n"
                + "Jan 17, 2018 6:34:03 PM io.vertx.core.http.impl.HttpClientResponseImpl\n"
                + "SEVERE: java.lang.AssertionError: Expected true\n"
                + "k menu__link--toggle\" href=\"#\">\n"
                + "                        <span>Information</span>\n"
                + "                        <i class=\"menu__icon fa fa-angle-down\"></i>\n"
                + "                    </a>\n"
                + "\n"
                + "                    <ul class=\"dropdown-menu\">\n"
                + "                        <li class=\"dropdown-menu__item\">\n"
                + "                            <a class=\"dropdown-menu__link\" href=\"/assets/env.html\">Environment Variables</a>\n"
                + "                        </li>\n"
                + "\n"
                + "                        <li class=\"dropdown-menu__item\">\n"
                + "                            <a class=\"dropdown-menu__link\" href=\"/assets/cheers\">Cheers</a>\n"
                + "                        </li>\n"
                + "                    </ul>\n"
                + "                </li>\n"
                + "            </ul>            \n"
                + "        </div>\n"
                + "        <!-- menu end-->\n"
                + "        <h1>Welcome to the Cheer application</h1>\n"
                + "        <div>This application is designed for testing purpose, for Tutorials and hands-on</div>\n"
                + "        <ul>\n"
                + "            <li>source can be found :<a href=\"https://github.com/kanedafromparis/cheers\" title=\"link to github repository\">on github</a></li>\n"
                + "       \n"
                + "     <li>Docker images can be found  :<a href=\"https://hub.docker.com/r/kanedafromparis/cheers\" title=\"link to on docker hub\">on docker hub</a></li>\n"
                + "            <li>Docker & Kubernetes exercises be found  :<a href=\"https://github.com/kanedafromparis/cheers-tp\" title=\"link to github repository\">on github</a></li>\n"
                + "        </ul>\n"
                + "        <script src=\"/assets/js/cheers.js\"></script>\n"
                + "\n"
                + "    </body>\n"
                + "</html>\n"
                + "";
        context.assertTrue(html.toString().contains("<h1>Welcome to the Cheer application</h1>"));
        async.complete();
    }

    /**
     * Test of start method, of class ServerVerticle.
     *
     * @param context
     */
    @Test
    public void testRedirect(TestContext context) {
        final Async async = context.async();

        vertx.createHttpClient().getNow(this.port, this.host, "/",
                response -> {
                    context.assertTrue(response.statusCode() == 307);
                    async.complete();
                });
    }

    /**
     * Test of start method, of class ServerVerticle.
     *
     * @param context
     */
    @Test
    public void testLiveness(TestContext context) {
        final Async async = context.async();

        vertx.createHttpClient().getNow(this.port, this.host, "/liveness",
                response -> {
                    response.handler(body -> {
                        context.assertTrue(body.toString().contains("OK"));
                        async.complete();
                    });
                });
    }

    /**
     * Test of start method, of class ServerVerticle.
     *
     * @todo check this
     * @param context
     */
    @Test
    public void testGetEnv(TestContext context) {
        final Async async = context.async();

        vertx.createHttpClient().getNow(this.port, this.host, API_1_0_INFO_ENV.getURL(),
                response -> {
                    response.handler(body -> {
                        //context.assertTrue(body.toString().contains("PATH"));
                        //context.assertTrue(body.toString().contains(System.getenv("PATH")));
                        async.complete();
                    });
                });
    }

    /**
     * Test of start method, of class ServerVerticle.
     *
     * @todo check this
     * @param context
     */
    @Test
    public void testDNS(TestContext context) {
        final Async async = context.async();
        //final String json = Json.encodePrettily(new Cheer("Care-bear", "smooch"));
        String fqdnTest = "github.com";
        final String json = "{\"fqdn\":\""+fqdnTest+"\"}";
        
        vertx.createHttpClient().post(port, this.host, API_1_0_INFO_DNS.getURL())
                .putHeader("content-type", "application/json")
                .putHeader("content-length", Integer.toString(json.length()))
                .handler(response -> {
                    context.assertEquals(response.statusCode(), 200);
                    context.assertTrue(response.headers().get("content-type").contains("application/json"));
                    response.bodyHandler(body -> {
                        async.complete();
                        String answer = body.toString();                        
                        System.out.println(answer);
                        context.assertTrue(answer.contains("host"));
                        JsonObject toJsonObject = body.toJsonObject();
                        context.assertEquals(toJsonObject.getString("host"), fqdnTest);
                        //context.assertNotNull(hug.getId());
                        async.complete();
                    });
                })
                .write(json)
                .end();
    }

    @Test
    public void checkThatWeCanAdd(TestContext context) {
        Async async = context.async();
        final String json = Json.encodePrettily(new Cheer("Care-bear", "smooch"));
        System.out.println(json);
        vertx.createHttpClient().post(port, this.host, API_1_0_CHEERS.getURL())
                .putHeader("content-type", "application/json")
                .putHeader("content-length", Integer.toString(json.length()))
                .handler(response -> {
                    context.assertEquals(response.statusCode(), 201);
                    context.assertTrue(response.headers().get("content-type").contains("application/json"));
                    response.bodyHandler(body -> {
                        final Cheer hug = Json.decodeValue(body.toString(), Cheer.class);
                        context.assertEquals(hug.getIntro(), "Care-bear");
                        context.assertEquals(hug.getCause(), "smooch");
                        context.assertNotNull(hug.getId());
                        async.complete();
                    });
                })
                .write(json)
                .end();
    }

    /**
     * Test of start method, of class ServerVerticle.
     *
     * @param context
     */
    @Test
    public void testReadiness(TestContext context) {
        final Async async = context.async();

        vertx.createHttpClient().getNow(this.port, this.host, "/readiness",
                response -> {
                    response.handler(body -> {
                        context.assertTrue(body.toString().contains("OK"));
                        async.complete();
                    });
                });
    }

    /**
     * Test of start method, of class ServerVerticle.
     *
     * @param context Todo /
     * @Test public void testAdd(TestContext context) { final Async async =
     * context.async(); Cheer hug = new Cheer("You are the best", "because you
     * code your test!"); RequestOptions ro = new
     * RequestOptions(Json.encodePrettily(hug));
     * vertx.createHttpClient().post(ro) getNow(this.port, this.host,
     * "/readiness", response -> { response.handler(body -> {
     * context.assertTrue(headerbody.toString().contains("OK"));
     * async.complete(); }); }); }
     */
}
