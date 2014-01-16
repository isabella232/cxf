/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.cxf.jaxrs.spring;

import java.util.Collections;
import java.util.List;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.message.Message;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Import;

@Import(JaxRsConfig.class)
public abstract class AbstractSpringConfigurationFactory implements ApplicationContextAware {

    protected ApplicationContext applicationContext;
    
    protected Server createJaxRsServer() {

        JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
        factory.setAddress(getAddress());
        factory.setTransportId(getTransportId());
        factory.setBus(applicationContext.getBean(SpringBus.class));
        
        setRootResources(factory);
        factory.setProviders(getJaxrsProviders());
        factory.setInInterceptors(getInInterceptors());
        factory.setOutInterceptors(getOutInterceptors());
        factory.setOutFaultInterceptors(getOutFaultInterceptors());
        factory.setFeatures(getFeatures());
        finalizeFactorySetup(factory);
        return factory.create();
    }
    
    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        applicationContext = ac;
    }
    
    protected abstract void setRootResources(JAXRSServerFactoryBean factory);
    
    protected List<Object> getJaxrsProviders() {
        return Collections.emptyList();
    }
    
    protected List<Interceptor<? extends Message>> getInInterceptors() {
        return Collections.emptyList();
    }
    
    protected List<Interceptor<? extends Message>> getOutInterceptors() {
        return Collections.emptyList();
    }
    
    protected List<Feature> getFeatures() {
        return Collections.emptyList();
    }
    
    protected List<Interceptor<? extends Message>> getOutFaultInterceptors() {
        return Collections.emptyList();
    }
    
    protected String getAddress() {
        return "/";
    }
    
    protected String getTransportId() {
        return "http://cxf.apache.org/transports/http";
    }
    
    protected void finalizeFactorySetup(JAXRSServerFactoryBean factory) {
        // complete
    }
}
