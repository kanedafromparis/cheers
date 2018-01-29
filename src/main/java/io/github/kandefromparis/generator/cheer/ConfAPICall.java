/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kandefromparis.generator.cheer;

/**
 * This Class is only a class that keep track of the URL
 *
 * @author csabourdin
 */
public enum ConfAPICall {
    ASSETX("/assets/*"),
    API_1_0_CHEERS("/api/1.0/cheers"),
    API_1_0_RANDOMCHEERS("/api/1.0/randomcheers"),
    API_1_0_INFO_RANDOM("/api/1.0/infos/random"),
    API_1_0_INFO_ENV("/api/1.0/infos/env"),
    API_1_0_INFO_RUNTIME("/api/1.0/infos/runtime"),
    API_1_0_INFO_FILES("/api/1.0/infos/file"),
    API_1_0_INFO_DNS("/api/1.0/info/dns"),
    LIVENESS("/liveness"),
    READINESS("/readiness");

    // Cheers API
    private final String url;

    ConfAPICall(String url) {
        this.url = url;
    }

    public String getURL() {
        return this.url;
    }

    public String build(String s) {
        return this.url + s;
    }
}
