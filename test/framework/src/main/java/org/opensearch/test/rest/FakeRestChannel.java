/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */

/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
/*
 * Modifications Copyright OpenSearch Contributors. See
 * GitHub history for details.
 */

package org.opensearch.test.rest;

import org.opensearch.rest.AbstractRestChannel;
import org.opensearch.rest.RestRequest;
import org.opensearch.rest.RestResponse;
import org.opensearch.core.rest.RestStatus;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public final class FakeRestChannel extends AbstractRestChannel {
    private final CountDownLatch latch;
    private final AtomicInteger responses = new AtomicInteger();
    private final AtomicInteger errors = new AtomicInteger();
    private RestResponse capturedRestResponse;

    public FakeRestChannel(RestRequest request, boolean detailedErrorsEnabled, int responseCount) {
        super(request, detailedErrorsEnabled);
        this.latch = new CountDownLatch(responseCount);
    }

    @Override
    public void sendResponse(RestResponse response) {
        this.capturedRestResponse = response;
        if (response.status() == RestStatus.OK) {
            responses.incrementAndGet();
        } else {
            errors.incrementAndGet();
        }
        latch.countDown();
    }

    public RestResponse capturedResponse() {
        return capturedRestResponse;
    }

    public AtomicInteger responses() {
        return responses;
    }

    public AtomicInteger errors() {
        return errors;
    }
}
