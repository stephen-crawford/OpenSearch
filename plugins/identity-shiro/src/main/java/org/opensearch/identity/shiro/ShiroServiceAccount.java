/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */

package org.opensearch.identity.shiro;

import java.util.List;
import java.util.Objects;
import org.opensearch.Application;
import org.opensearch.identity.NamedPrincipal;
import org.opensearch.identity.ServiceAccount;

/**
 * This class defines a ServiceAccount in the context of the Shiro Identity Plugin.
 *
 * Here we can see the ShiroServiceAccount implement the ServiceAccount and be used to track permissions assigned to applications.
 */
public class ShiroServiceAccount implements ServiceAccount {

    private final Application application;
    private final NamedPrincipal name;

    /**
     * Creates a principal for an application identity
     * @param application The application to be associated with the service account
     */
    public ShiroServiceAccount(final Application application) {

        this.application = application;
        name = new NamedPrincipal(application.toString());
    }

    @Override
    public String getName() {
        return name.getName();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final ServiceAccount that = (ServiceAccount) obj;
        return Objects.equals(name, that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "ServiceAccount(" + "name=" + name + ")";
    }

    @Override
    public List<String> getPermissions() {
        return null;
    }
}
