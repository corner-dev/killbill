/*
 * Copyright 2010-2011 Ning, Inc.
 *
 * Ning licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.ning.billing.entitlement.glue;

import org.skife.config.ConfigurationObjectFactory;

import com.google.inject.AbstractModule;
import com.ning.billing.config.IEntitlementConfig;
import com.ning.billing.entitlement.alignment.IPlanAligner;
import com.ning.billing.entitlement.alignment.PlanAligner;
import com.ning.billing.entitlement.api.IEntitlementService;
import com.ning.billing.entitlement.api.billing.EntitlementBillingApi;
import com.ning.billing.entitlement.api.billing.IEntitlementBillingApi;
import com.ning.billing.entitlement.api.user.EntitlementUserApi;
import com.ning.billing.entitlement.api.user.IEntitlementUserApi;
import com.ning.billing.entitlement.engine.core.ApiEventProcessor;
import com.ning.billing.entitlement.engine.core.Engine;
import com.ning.billing.entitlement.engine.core.IApiEventProcessor;
import com.ning.billing.entitlement.engine.dao.EntitlementDao;
import com.ning.billing.entitlement.engine.dao.IEntitlementDao;
import com.ning.billing.util.clock.Clock;
import com.ning.billing.util.clock.IClock;



public class EntitlementModule extends AbstractModule {


    protected void installClock() {
        bind(IClock.class).to(Clock.class).asEagerSingleton();
    }

    protected void installConfig() {
        final IEntitlementConfig config = new ConfigurationObjectFactory(System.getProperties()).build(IEntitlementConfig.class);
        bind(IEntitlementConfig.class).toInstance(config);
    }

    protected void installApiEventProcessor() {
        bind(IApiEventProcessor.class).to(ApiEventProcessor.class).asEagerSingleton();
    }

    protected void installEntitlementDao() {
        bind(IEntitlementDao.class).to(EntitlementDao.class).asEagerSingleton();
    }

    protected void installEntitlementCore() {
        bind(IEntitlementService.class).to(Engine.class).asEagerSingleton();
        bind(Engine.class).asEagerSingleton();
        bind(IPlanAligner.class).to(PlanAligner.class).asEagerSingleton();
        bind(IEntitlementUserApi.class).to(EntitlementUserApi.class).asEagerSingleton();
        bind(IEntitlementBillingApi.class).to(EntitlementBillingApi.class).asEagerSingleton();
    }

    protected void installInjectorMagic() {
        bind(InjectorMagic.class).asEagerSingleton();
    }

    @Override
    protected void configure() {
        installInjectorMagic();
        installConfig();
        installClock();
        installApiEventProcessor();
        installEntitlementDao();
        installEntitlementCore();
    }
}
