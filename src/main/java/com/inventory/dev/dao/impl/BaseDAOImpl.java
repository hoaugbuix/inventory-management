package com.inventory.dev.dao.impl;

import com.inventory.dev.dao.BaseDAO;
import com.inventory.dev.entity.Paging;
import com.inventory.dev.model.mapper.RowMapper;
import org.apache.log4j.Logger;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
@Transactional(rollbackFor = Exception.class)
public class BaseDAOImpl<E> implements BaseDAO<E> {
    final static Logger log = Logger.getLogger(BaseDAOImpl.class);

    @PersistenceContext
    EntityManager entityManager;
    ResourceBundle resourceBundle = ResourceBundle.getBundle("db");
    public Connection getConnection(){
        try {
            Class.forName(resourceBundle.getString("driverName"));
            String url = resourceBundle.getString("url");
            String user = resourceBundle.getString("user");
            String password = resourceBundle.getString("password");
            return DriverManager.getConnection(url, user, password);
        }catch (ClassNotFoundException | SQLException e){
            return null;
        }
    }


    @Override
    public List<E> findAll(String queryStr, Map<String, Object> mapParams, Paging paging) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
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
        log.info("Find by ID= " + id);
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

    // Jdbc
    @Override
    public <E> List<E> queryJdbc(String sql, RowMapper<E> rowMapper, Object... parameters) {
        List<E> results = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            //set parameter
            setParameter(statement, parameters);
            resultSet =statement.executeQuery();
            while (resultSet.next()){
                results.add(rowMapper.mapRow(resultSet));
            }
            return results;
        }catch (SQLException e){
            return null;
        }finally {
            try {
                if (connection != null){
                    connection.close();
                }
                if (statement != null){
                    statement.close();
                }
                if (resultSet != null){
                        resultSet.close();
                }
            }catch (SQLException e2){
                    e2.printStackTrace();
            }
        }
    }



    @Override
    public void updateJdbc(String sql, Object... parameters) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
            connection.commit();
        }catch (SQLException e){
            if (connection != null){
                try {
                    connection.rollback();
                } catch (SQLException e1){
                    e1.printStackTrace();
                }
            }
        }finally {
            try {
                if (connection != null){
                    connection.close();
                }
                if (statement != null){
                    statement.close();
                }
            }catch (SQLException e2){
                e2.printStackTrace();
            }
        }
    }

    @Override
    public Integer insertJdbc(String sql, Object... parameters) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            Integer id = 0;
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setParameter(statement, parameters);
            statement.executeUpdate();
            // auto insert id
            resultSet = statement.getGeneratedKeys();
            while (resultSet.next()){
                id = resultSet.getInt(1);
            }
            connection.commit();
            return id;
        }catch (SQLException e){
            if (connection != null){
                try {
                    connection.rollback();
                }catch (SQLException e1){
                    e1.printStackTrace();
                }
            }
        }finally {
            try {
                if (connection != null){
                    connection.close();
                }
                if (statement != null){
                    statement.close();
                }
                if (resultSet != null){
                    resultSet.close();
                }
            }catch (SQLException e2){
                e2.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Integer countJdbc(String sql, Object... parameters) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            int count = 0;
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            setParameter(statement, parameters);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                count = resultSet.getInt(1);
            }
            return count;
        }catch (SQLException e){
            return 0;
        }finally {
            try {
                if (connection != null){
                    connection.close();
                }
                if (statement != null){
                    statement.close();
                }
                if (resultSet != null){
                    resultSet.close();
                }
            }catch (SQLException e1){
                return 0;
            }
        }
    }

    private void setParameter(PreparedStatement statement, Object... parameters) {
        try {
            for (int i = 0; i < parameters.length; i++){
                Object parameter = parameters[i];
                int index = i + 1;
                if (parameter instanceof Long){
                    statement.setLong(index,(Long) parameter);
                }
                if (parameter instanceof Integer){
                    statement.setInt(index,(Integer) parameter);
                }
                if (parameter instanceof String){
                    statement.setString(index, (String) parameter);
                }
                if (parameter instanceof Timestamp){
                    statement.setTimestamp(index, (Timestamp) parameter);
                }else {
                    statement.setObject(index, parameter);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    // regex
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
