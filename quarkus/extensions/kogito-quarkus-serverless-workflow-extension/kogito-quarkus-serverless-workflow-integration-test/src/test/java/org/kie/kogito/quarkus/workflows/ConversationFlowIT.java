/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.kie.kogito.quarkus.workflows;

import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTestResource(OperationsMockService.class)
@QuarkusIntegrationTest
@Disabled("Disabled due to bug https://github.com/quarkusio/quarkus/issues/46801. When issues are resolved, we need to reenable.")
class ConversationFlowIT {

    @BeforeAll
    static void init() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void sanityVerification() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(Map.of("fahrenheit", "100", "clusterName", "cluster1"))
                .post("/fahrenheit_to_celsius")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("workflowdata.fahrenheit", is("100"))
                .body("workflowdata.celsius", is(37.808f)); //values from mock server
    }

    @Test
    void wrongCluster() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(Map.of("fahrenheit", "100", "clusterName", "cluster2"))
                .post("/fahrenheit_to_celsius")
                .then()
                .statusCode(400);
    }

    @Test
    void wrongData() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(Map.of("fahrenheit", "100"))
                .post("/fahrenheit_to_celsius")
                .then()
                .statusCode(400);
    }
}
