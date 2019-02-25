package com.kking.admin.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

public class MySessionManager extends DefaultWebSessionManager {
    Log log = LogFactory.getLog(MyRedisSessionDAO.class);

    private static final String AUTHORIZATION = "X-Token";

    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";
    private static final String MY_SESSION_ATTRIBUTE = "MY_SESSION_ATTRIBUTE";

    public MySessionManager() {
        super();
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String id = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
        //如果请求头中有 Authorization 则其值为sessionId
        if (!StringUtils.isEmpty(id)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
        } else {
            //否则按默认规则从cookie取sessionId
            return super.getSessionId(request, response);
        }
    }

    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        Serializable sessionId = getSessionId(sessionKey);

        if (sessionId == null) {
            return null;
        }

        ServletRequest request = WebUtils.getRequest(sessionKey);

        if(request.getAttribute(MY_SESSION_ATTRIBUTE) != null){
            log.debug("Get Session from request!");
            return (Session)request.getAttribute(MY_SESSION_ATTRIBUTE);
        }else{
            log.debug("Get Session from redis!");
            Session s = retrieveSessionFromDataSource(sessionId);
            if (s == null) {
                //session ID was provided, meaning one is expected to be found, but we couldn't find one:
                String msg = "Could not find session with ID [" + sessionId + "]";
                throw new UnknownSessionException(msg);
            }
            request.setAttribute(MY_SESSION_ATTRIBUTE,s);
            return s;
        }
    }
}