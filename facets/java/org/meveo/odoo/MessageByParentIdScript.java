package org.meveo.odoo;

import static org.meveo.odoo.ProductDetailService.*;

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
        Object[] messages;
        try {
            messages = retrieveAnswers();
            if (messages != null) {
                result = "{\"status\": \"success\", \"result\": " + toJson(messages) + "}";
            } else {
                result = "{\"status\": \"success\", \"result\": {}";
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = "{\"status\": \"fail\", \"result\": \"" + e.getMessage() + "\"}";
        }
    }

    public Object[] retrieveAnswers() {
        Map<String, Object> fields = new HashMap<>();
        String[] fieldNames = {"body"};
        fields.put("fields", fieldNames);
        List filters = List.of(List.of(List.of("parent_id", "=",messageId )));
        Object[] messages;
        try {
            messages = odooService.executeQuery("mail.message", "search_read", filters, fields);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve message. (id: " + messageId + ")", e);
        }

        return messages;
    }

}