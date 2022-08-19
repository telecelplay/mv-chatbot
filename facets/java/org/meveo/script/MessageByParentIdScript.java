package org.meveo.script;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.meveo.service.script.Script;
import org.meveo.admin.exception.BusinessException;;
import org.meveo.commons.utils.ParamBeanFactory;
import org.meveo.odoo.OdooXmlRpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageByParentIdScript extends Script {
    private static final Logger LOG = LoggerFactory.getLogger(MessageByParentIdScript.class);
    private final ParamBeanFactory paramBeanFactory = getCDIBean(ParamBeanFactory.class);
    private final OdooXmlRpc odooService = new OdooXmlRpc(paramBeanFactory);
  
    private Integer messageId;
    private String result;

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }
  
    public String getResult() {
        return this.result;
    }
	    @Override
    public void execute(Map<String, Object> parameters) throws BusinessException {
        super.execute(parameters);
        Object message;
        try {
            message = (Map<String, Object>) retrieveAnswer();
            if (message != null) {
                result = "{\"status\": \"success\", \"result\": " + message + "}";
            } else {
                result = "{\"status\": \"success\", \"result\": {}";
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = "{\"status\": \"fail\", \"result\": \"" + e.getMessage() + "\"}";
        }
    }

    public Object retrieveAnswer() {
        List<Integer> ids = List.of(messageId);
        Map<String, Object> fields = new HashMap<>();
        String[] fieldNames = {"body"};
        fields.put("fields", fieldNames);
        Object[] messages;
        try {
            messages = odooService.executeQuery("mail.message", "read", ids, fields);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve message. (id: " + messageId + ")", e);
        }

        boolean hasMessage = messages != null && messages.length > 0 && messages[0] != null;
        Object message = null;
        if (hasMessage) {
            try {
                message = messages[0];
            } catch (Exception e) {
                throw new RuntimeException("Failed to retrieve message. (id: " + messageId + ")", e);
            }
        }
        return message;
    }

}