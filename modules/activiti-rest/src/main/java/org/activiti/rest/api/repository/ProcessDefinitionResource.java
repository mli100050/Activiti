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

package org.activiti.rest.api.repository;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.rest.api.ActivitiUtil;
import org.activiti.rest.api.SecuredResource;
import org.activiti.rest.application.ActivitiRestServicesApplication;
import org.restlet.resource.Get;

/**
 * @author Frederik Heremans
 */
public class ProcessDefinitionResource extends SecuredResource {
  
  @Get
  public ProcessDefinitionResponse getProcessDefinition() {
    if(authenticate() == false) return null;

    String processDefinitionId = getAttribute("processDefinitionId");
    if(processDefinitionId == null) {
      throw new ActivitiIllegalArgumentException("The processDefinitionId cannot be null");
    }
    
    ProcessDefinition processDefinition = ActivitiUtil.getRepositoryService().createProcessDefinitionQuery()
             .processDefinitionId(processDefinitionId).singleResult();
    
    if(processDefinition == null) {
      throw new ActivitiObjectNotFoundException("Could not find a process definition with id '" + processDefinitionId + "'.", ProcessDefinition.class);
    }
    
    return getApplication(ActivitiRestServicesApplication.class).getRestResponseFactory()
      .createProcessDefinitionResponse(this, processDefinition);
  }
}
