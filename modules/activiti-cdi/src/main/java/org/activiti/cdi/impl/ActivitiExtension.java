/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.cdi.impl;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import org.activiti.cdi.annotation.BusinessProcessScoped;
import org.activiti.cdi.impl.context.BusinessProcessContext;
import org.activiti.engine.ProcessEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CDI-Extension registering a custom context for {@link BusinessProcessScoped}
 * beans.
 *
 * Also starts / stops the activiti {@link ProcessEngine} and deploys all
 * processes listed in the 'processes.xml'-file.
 *
 * @author Daniel Meyer
 */
public class ActivitiExtension implements Extension {

    private static final Logger logger = LoggerFactory.getLogger(ActivitiExtension.class);

    public void beforeBeanDiscovery(@Observes final BeforeBeanDiscovery event) {
        logger.info("Adding BusinessProcessScoped.");
        event.addScope(BusinessProcessScoped.class, true, true);
    }

    public void afterBeanDiscovery(@Observes AfterBeanDiscovery event, BeanManager manager) {
        logger.info( "Adding BusinessProcessContext.");
        event.addContext(new BusinessProcessContext(manager));
    }

    public void afterDeploymentValidation(@Observes AfterDeploymentValidation event, BeanManager beanManager) {
        logger.info( "Doing nothing after deployment validation.");
    }
}
