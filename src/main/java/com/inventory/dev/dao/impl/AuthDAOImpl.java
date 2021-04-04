package com.inventory.dev.dao.impl;

import com.inventory.dev.dao.AuthDAO;
import com.inventory.dev.entity.AuthEntity;
import com.inventory.dev.model.mapper.AuthMapper;
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

    //jdbc

    @Override
    public AuthEntity findAuthByRoleIdAndMenuIdJdbc(int roleId, int menuId) {
        String sql = "SELECT * FROM auth  where  role_id = ? and menu_id = ? ";
        List<AuthEntity> auth = queryJdbc(sql, new AuthMapper(), roleId, menuId);
        return auth.isEmpty() ? null : auth.get(0);
    }

    @Override
    public Integer SaveAuthJdbc(AuthEntity auth) {
        StringBuilder sql = new StringBuilder("INSERT INTO auth ");
        sql.append("(role_id, menu_id, permission, active_flag, created_date, updated_date)");
        sql.append("VALUES (?, ?, ?, ?, ?, ?)");
        return insertJdbc(sql.toString(),auth.getRoles(), auth.getMenus(), auth.getPermission(), auth.getActiveFlag(), auth.getCreatedDate(), auth.getUpdatedDate() );
    }

    @Override
    public void updateAuthJdbc(AuthEntity auth) {
        StringBuilder sql = new StringBuilder("UPDATE auth SET");
        sql.append("role_id = ?, menu_id = ?, permission = ?, active_flag = ?, created_date = ?, updated_date = ?");
        updateJdbc(sql.toString(),auth.getRoles(), auth.getMenus(), auth.getPermission(), auth.getActiveFlag(), auth.getCreatedDate(), auth.getUpdatedDate() );
    }
}
