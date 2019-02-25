package com.kking.admin.shiro;

import com.kking.admin.util.RedisCacheManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;


public class MyRedisSessionDAO extends AbstractSessionDAO {
    Log log = LogFactory.getLog(MyRedisSessionDAO.class);
    private final String REDIS_HASH = "SESSION";

    @Autowired
    RedisCacheManager redisCacheManager;

    @Override
    protected Serializable doCreate(Session session) {
        Serializable id = super.generateSessionId(session);
        ((SimpleSession)session).setId(id);
        log.debug("doCreate session:" + id);
        redisCacheManager.hset(REDIS_HASH,id.toString(),session);
        return id;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session = (Session)redisCacheManager.hget(REDIS_HASH,sessionId.toString());
        log.debug("doReadSession session:" + sessionId);
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        Serializable id = session.getId();
        if(id == null){
            throw new NullPointerException();
        }
        redisCacheManager.hset(REDIS_HASH,id.toString(),session);

    }

    @Override
    public void delete(Session session) {
        Serializable id = session.getId();
        redisCacheManager.hdel(REDIS_HASH,id.toString());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return redisCacheManager.hgetAllValues(REDIS_HASH);
    }
}
