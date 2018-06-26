package cn.com.gree.dao.impl;

import cn.com.gree.dao.BaseDao;
import cn.com.gree.dao.utils.Pagination;
import cn.com.gree.dao.utils.QueryCondition;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
@Repository("BaseDao")
public class BaseDaoImpl implements BaseDao {

    private static final String paramName = "gree_";

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public <T> void save(T entity) {
        em.persist(entity);
    }

    @Override
    public <T> void batchSave(List<T> entities) {
        entities.forEach(this::save);
    }

    @Override
    @Transactional
    public <T> void update(T entity) {
        em.merge(entity);
    }

    @Override
    @Transactional
    public <T> void delete(T entity) {
        if(entity != null){
            em.remove(entity);
        }
    }

    @Override
    public <T> void delete(Class<T> clazz, Object id) {
        T entity = em.find(clazz,id);
        delete(entity);
    }

    @Override
    public <T> void delete(Class<T> clazz, List<Object> ids) {
        for(Object o : ids){
            delete(o);
        }
    }

    @Override
    public <T> List<T> getAll(Class<T> clazz) {
        StringBuffer jpql = new StringBuffer(" select o from ").append(clazz.getSimpleName()).append(" o ");
        return em.createQuery(jpql.toString()).getResultList();
    }

    @Override
    public <T> T getById(Class<T> clazz, Object id) {
        return em.find(clazz, id);
    }

    @Override
    public <T> List<T> getByIds(Class<T> clazz, List<Object> ids) {
        List<T> list = new ArrayList<>();
        for(Object o : ids){
            list.add(getById(clazz,o));
        }
        return list;
    }

    @Override
    public <T> List<T> getByJpql(String jpql, Object... objects) {
        Query query = createQuery(jpql,objects);
        return query.getResultList();
    }

    @Override
    @Transactional
    public int executeJpql(String jpql, Object... objects) {
        Query query = createQuery(jpql,objects);
        return query.executeUpdate();
    }

    @Override
    public <T> List<T> get(Class<T> clazz, List<QueryCondition> queryConditions) {
        return get(clazz,queryConditions,null,0,0);
    }

    @Override
    public <T> List<T> get(Class<T> clazz, List<QueryCondition> queryConditions, int currentPage, int pageSize) {
        return get(clazz,queryConditions,null,currentPage,pageSize);
    }

    @Override
    public <T> List<T> get(Class<T> clazz, List<QueryCondition> queryConditions, String orderBy) {
        return get(clazz,queryConditions,orderBy,0,0);
    }

    @Override
    public <T> List<T> get(Class<T> clazz, List<QueryCondition> queryConditions, String orderBy, int currentPage, int pageSize) {
        Query query = getQuery(clazz,queryConditions,orderBy,false);
        return getPageRecord(query,currentPage,pageSize);
    }

    @Override
    public <T> long getRecordCount(Class<T> clazz, List<QueryCondition> queryConditions) {
        Query query = getQuery(clazz,queryConditions,null,true);
        Object result = null;
        try {
            result = query.getSingleResult();
        }catch (NoResultException e){
            result = null;
        }
        if(result != null){
            return (long) result;
        }else {
            return 0L;
        }
    }

    @Override
    public long getRecordCountByJpql(String jpql, Object... objects) {
        Query query = em.createQuery(jpql);
        if(objects != null){
            for(int i = 0 ; i < objects.length ; i++){
                query.setParameter(i + 1, objects[i]);
            }
        }
        List result = null;
        try {
            result = query.getResultList();
        } catch (NoResultException e){
            result = null;
        }
        if(result != null){
            return result.size();
        }else {
            return 0L;
        }
    }

    @Override
    public <T> List<T> getPageQuery(String jpql, int currentPage, int pageSize, Object... objects) {
        Query query = this.createQuery(jpql,objects);
        return getPageRecord(query,currentPage,pageSize);
    }

    @Override
    public <T> Pagination<T> getPagination(Class<T> clazz, List<QueryCondition> queryConditions, String orderBy, int currentPage, int pageSize) {
        List<T> pageRecordList = get(clazz,queryConditions,orderBy,currentPage,pageSize);
        long totalCount = getRecordCount(clazz,queryConditions);
        return new Pagination<>(currentPage,pageSize,totalCount,pageRecordList);
    }

    @Override
    public Object getSingleResultByJpql(String jpql, Object... objects) {
        Query query = createQuery(jpql,objects);
        try {
            return query.getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }

    @Override
    public Object getSingleResultByLimit(String jpql, int limit, Object... objects) {
        Query query = createQuery(jpql,objects);
        query.setMaxResults(limit);
        try {
            return query.getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }

    @Override
    public <T> List<T> getByNativeSQL(Class<T> clazz, String sql) {
        return em.createNamedQuery(sql,clazz).getResultList();
    }

    @Override
    public <T> List<T> getByNativeSQL(Class<T> clazz, String sql, Object... objects) {
        Query query = em.createNamedQuery(sql,clazz);
        if(objects != null){
            for(int i = 0 ; i < objects.length ; i++){
                query.setParameter(i + 1, objects[i]);
            }
        }
        return query.getResultList();
    }

    @Override
    public int getCountByNativeSQL(String sql) {
        return em.createNamedQuery(sql).getResultList().size();
    }

    @Override
    public int getCountByNativeSQL(String sql, Object... objects) {
        Query query = em.createNamedQuery(sql);
        if(objects != null){
            for(int i = 0 ; i < objects.length ; i++){
                query.setParameter(i + 1, objects[i]);
            }
        }
        return query.getResultList().size();
    }

    /**
     * @author 260172
     * @date 2018/6/25 16:33
     * 设置分页
     */
    private <T> List<T> getPageRecord(Query query, int currentPage, int pageSize){
        if(currentPage == 0 && pageSize == 0){
            return query.getResultList();
        } else {
            return query.setFirstResult((currentPage - 1) * pageSize).setMaxResults(pageSize).getResultList();
        }
    }

    /**
     * @author 260172
     * @date 2018/6/25 16:16
     * 根据jpql和条件查询，返回query
     */
    private Query createQuery(String jpql, Object... objects){
        Query query = em.createQuery(jpql);
        if(objects != null){
            for(int i = 0 ; i < objects.length; i++){
                query.setParameter(i + 1,objects[i]);
            }
        }
        return query;
    }


    /**
     * @author 260172
     * @date 2018/6/25 16:16
     * 构造jpql语句
     */
    private Query getQuery(Class<?> clazz, List<QueryCondition> conditions, String orderBy, boolean isQueryTotal){
        String preJPQL = isQueryTotal ? " select count(*) from " : "select o from ";
        StringBuffer jpql = new StringBuffer(preJPQL).append(clazz.getSimpleName()).append(" o where 1 = 1");
        Query query = null;
        if(conditions != null){
            // 构造语句
            for(int i = 0 ; i < conditions.size(); i++){
                QueryCondition condition = conditions.get(i);
                if(condition != null){
                    if(condition.getOperator().equals(QueryCondition.CUSTOM)){
                        jpql.append(" and (").append(condition.getCustomJPQL()).append(" )");
                    }
                    if(condition.getValue() != null && !"".equals(condition.getValue())){
                        jpql.append(" and o.").append(condition.getField().trim()).append(" ")
                                .append(condition.getOperator()).append(":").append(paramName).append(i);
                    }
                }
            }
        }
        if(orderBy != null && !"".equals(orderBy)){
            jpql.append(" ").append(orderBy);
        }
        query = em.createQuery(jpql.toString());
        if(conditions != null){
            // 参数赋值
            for(int j = 0 ; j < conditions.size() ; j++){
                QueryCondition condition = conditions.get(j);
                if(condition != null){
                    if(condition.getValue() != null && !"".equals(condition.getValue())){
                        if(condition.getOperator().equals(QueryCondition.LIKE)){
                            query.setParameter(paramName + j, "%" + condition.getValue() + "%");
                        } else {
                            query.setParameter(paramName + j, condition.getValue());
                        }
                    }
                }
            }
        }
        return query;
    }

}
