package org.meveo.script;

import java.util.Map;

import org.meveo.service.script.Script;
import org.meveo.api.rest.technicalservice.impl.EndpointRequest;
import org.meveo.admin.exception.BusinessException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class ChatBotScript extends Script {
	
    private Logger log = LoggerFactory.getLogger(ChatBotScript.class);
	
	public void execute(Map<String, Object> parameters) throws BusinessException {
        EndpointRequest req = (EndpointRequest) parameters.get("request");
        log.info("request {}", req);
	}
	
}