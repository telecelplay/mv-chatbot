package org.meveo.odoo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.meveo.api.persistence.CrossStorageApi;
import org.meveo.commons.utils.ParamBeanFactory;
import org.meveo.commons.utils.StringUtils;
import org.meveo.model.customEntities.LiquimartProductCategory;
import org.meveo.model.storage.Repository;
import org.meveo.service.script.Script;
import org.meveo.admin.exception.BusinessException;
import org.meveo.service.storage.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Arrays.asList;

public class CreateMessage extends Script {
    private static final Logger LOG = LoggerFactory.getLogger(CreateMessage.class);
    private final CrossStorageApi crossStorageApi = getCDIBean(CrossStorageApi.class);
    private final RepositoryService repositoryService = getCDIBean(RepositoryService.class);
    private final ParamBeanFactory paramBeanFactory = getCDIBean(ParamBeanFactory.class);
    private final Repository defaultRepo = repositoryService.findDefaultRepository();

    private String subject;
    private String body;
    private String channel;
    private Integer partnerId;
    private Integer parentId;
    private String image;
    private final Map<String, Object> result = new HashMap<>();

    public Map<String, Object> getResult() {
        return this.result;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
  
    public void setBody(String body) {
        this.body = body;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }
  
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public void execute(Map<String, Object> parameters) throws BusinessException {
        super.execute(parameters);

        if (StringUtils.isBlank(body)) {
            result.put("status", "failed");
            result.put("result", "Message body is required.");
            return;
        }

        OdooXmlRpc odooService = new OdooXmlRpc(paramBeanFactory);
        List<Object> filterList = new ArrayList<>();
        filterList.add(asList("subject", "=", body));

        Map<String, Object> params = new HashMap<>();
        
        //message type
        params.put("message_type","comment");
        params.put("is_internal",Boolean.TRUE);
        params.put("subtype_id",1);
        
        //message content
        params.put("body", body);
        //params.put("subject", subject);
        
	    //channel
        params.put("model", "mail.channel");
        params.put("res_id",1);
        params.put("record_name","general");
        
         if (parentId != null) {
        	params.put("parent_id",parentId);
        }
        if (partnerId != null) {
            params.put("author_id", partnerId);
        }
        if (image != null) {
            params.put("image_1920", image);
        }
        Integer id;
        try {
            id = odooService.executeQuery("mail.message", "create", List.of(params), null);
        } catch (Exception e) {
          LOG.error("Error creating odoo message: {}",e);
            result.put("status", "failed");
            result.put("result", "Failed to create new mail message in Odoo.");
            return;
        }
        result.put("status", "success");
        result.put("message_id", id);
    }
}
