package com.inventory.dev.dao.impl;

import com.inventory.dev.dao.AuthDAO;
import com.inventory.dev.entity.AuthEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(rollbackFor = Exception.class)
public class AuthDAOImpl extends BaseDAOImpl<AuthEntity> implements AuthDAO<AuthEntity> {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public AuthEntity find(int roleId, int menuId) {
        String hql = "from AuthEntity model where model.role.id=:roleId and model.menu.id=:menuId";
        Query<AuthEntity> query = (Query<AuthEntity>) entityManager.createNativeQuery(hql, AuthEntity.class);
        query.setParameter("roleId", roleId);
        query.setParameter("menuId", menuId);
        List<AuthEntity> auths = query.getResultList();
        if (!CollectionUtils.isEmpty(auths)) {
            return auths.get(0);
        }
        return null;
    }
}
