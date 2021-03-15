package com.inventory.dev.dao.impl;

import com.inventory.dev.dao.BaseDAO;
import com.inventory.dev.entity.Paging;
import org.apache.log4j.Logger;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
@Transactional(rollbackFor = Exception.class)
public class BaseDAOImpl<E> implements BaseDAO<E> {
    final static Logger log = Logger.getLogger(BaseDAOImpl.class);
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<E> findAll(String queryStr, Map<String, Object> mapParams, Paging paging) {
        log.info("find all record from db");
        StringBuilder queryString = new StringBuilder("");
        StringBuilder countQuery = new StringBuilder();
        countQuery.append(" select count(*) from ").append(getGenericName()).append(" as model where model.activeFlag=1");
        queryString.append(" from ").append(getGenericName()).append(" as model where model.activeFlag=1");
        if (queryStr != null && !queryStr.isEmpty()) {
            queryString.append(queryStr);
            countQuery.append(queryStr);
        }
        Query<E> query = (Query<E>) entityManager.createQuery(queryString.toString());
        Query<E> countQ = (Query<E>) entityManager.createQuery(countQuery.toString());
        if (mapParams != null && !mapParams.isEmpty()) {
            for (String key : mapParams.keySet()) {
                query.setParameter(key, mapParams.get(key));
                countQ.setParameter(key, mapParams.get(key));
            }
        }
        if (paging != null) {
            query.setFirstResult(paging.getOffset());
            query.setMaxResults(paging.getRecordPerPage());
            E totalRecords = countQ.uniqueResult();
            paging.setTotalRows((Long) totalRecords);
        }
        log.info("Query find all ====>" + queryString.toString());
        return query.list();
    }

    @Override
    public E findById(Class<E> e, Serializable id) {
        log.info("Find by ID= "+ id);
        return entityManager.find(e, id);
    }

    @Override
    public List<E> findByProperty(String property, Object value) {
        log.info("Find by property");
        StringBuilder queryString = new StringBuilder();
        queryString.append(" from ").append(getGenericName()).append(" as model where model.activeFlag=1 and model.").append(property).append("=?0");
        log.info(" query find by property ===>" + queryString.toString());
        Query<E> query = (Query<E>) entityManager.createQuery(queryString.toString());
        query.setParameter(0, value);
        return query.getResultList();
    }

    @Override
    public void save(E instance) {
        log.info("save instance");
        entityManager.persist(instance);
    }

    @Override
    public int insert(E instance) {
        log.info(" save instance");
        Integer id = (Integer) entityManager.createNativeQuery((String) instance).executeUpdate();
        return id;
    }

    @Override
    public void update(E instance) {
        log.info("update");
        entityManager.merge(instance);
    }

    //
    public String getGenericName() {
        String s = getClass().getGenericSuperclass().toString();
        Pattern pattern = Pattern.compile("\\<(.*?)\\>");
        Matcher m = pattern.matcher(s);
        String generic = "null";
        if (m.find()) {
            generic = m.group(1);
        }
        return generic;
    }
}
