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
package org.kie.kogito.addons.quarkus.fabric8.k8s.service.catalog;

import org.junit.jupiter.api.BeforeEach;
import org.kie.kogito.addons.k8s.resource.catalog.KubernetesServiceCatalogTest;
import org.kie.kogito.addons.quarkus.k8s.test.utils.KubernetesMockServerTestResource;

import io.fabric8.kubernetes.client.KubernetesClient;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

import jakarta.inject.Inject;

import static org.kie.kogito.addons.quarkus.k8s.test.utils.KubeTestUtils.createKnativeServiceIfNotExists;

@QuarkusTest
@QuarkusTestResource(KubernetesMockServerTestResource.class)
class Fabric8KubernetesServiceCatalogTest extends KubernetesServiceCatalogTest {

    @Inject
    KubernetesClient client;

    @BeforeEach
    void beforeEach() {
        createKnativeServiceIfNotExists(client, "knative/quarkus-greeting.yaml", getNamespace(), getKnativeServiceName());
        createKnativeServiceIfNotExists(client, "knative/quarkus-greeting-kubernetes.yaml", getNamespace(), getKubernetesServiceName());
        createKnativeServiceIfNotExists(client, "knative/quarkus-greeting-openshift.yaml", getNamespace(), getOpenshiftServicename());
    }

    @Inject
    Fabric8KubernetesServiceCatalogTest(Fabric8KubernetesServiceCatalog fabric8KubernetesServiceCatalog) {
        super(fabric8KubernetesServiceCatalog);
    }
}
