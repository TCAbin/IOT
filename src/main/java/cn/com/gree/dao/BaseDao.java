package cn.com.gree.dao;

import cn.com.gree.dao.utils.Pagination;
import cn.com.gree.dao.utils.QueryCondition;

import java.util.List;

public interface BaseDao {

    /**
     * @author 260172
     * @date 2018/6/25 15:22
     * 新建实体
     */
    <T> void save(T entity);

    /**
     * @author 260172
     * @date 2018/6/25 15:23
     * 批量新建实体
     */
    <T> void batchSave(List<T>entities);

    /**
     * @author 260172
     * @date 2018/6/25 15:23
     * 更新实体
     */
    <T> void update(T entity);

    /**
     * @author 260172
     * @date 2018/6/25 15:24
     * 删除实体
     */
    <T> void delete(T entity);

    /**
     * @author 260172
     * @date 2018/6/25 15:25
     * 根据主键删除实体
     */
    <T> void delete(Class<T> clazz, Object id);

    /**
     * @author 260172
     * @date 2018/6/25 15:25
     * 根据主键批量删除实体
     */
    <T> void delete(Class<T> clazz, List<Object> ids);

    /**
     * @author 260172
     * @date 2018/6/25 15:26
     * 根据类型查找所有实体
     */
    <T> List<T> getAll(Class<T> clazz);

    /**
     * @author 260172
     * @date 2018/6/25 15:26
     * 根据主键查找实体
     */
    <T> T getById(Class<T> clazz, Object id);

    /**
     * @author 260172
     * @date 2018/6/25 15:27
     * 根据主键批量查找实体
     */
    <T> List<T> getByIds(Class<T> clazz, List<Object> ids);

    /**
     * @author 260172
     * @date 2018/6/25 15:28
     * 根据jpql语句查询
     */
    <T> List<T> getByJpql(String jpql, Object... objects);

    /**
     * @author 260172
     * @date 2018/6/25 15:29
     * 执行jpql，返回受影响条数 insert/update/delete/drop/create
     */
    int executeJpql(String jpql, Object... objects);

    /**
     * @author 260172
     * @date 2018/6/25 15:30
     * 根据条件查找
     */
    <T> List<T> get(Class<T> clazz, List<QueryCondition> queryConditions);


    /**
     * @author 260172
     * @date 2018/6/25 15:31
     * 根据条件分页查找
     */
    <T> List<T> get(Class<T> clazz, List<QueryCondition> queryConditions,int currentPage,int pageSize);

    /**
     * @author 260172
     * @date 2018/6/25 15:31
     * 根据条件排序查找
     */
    <T> List<T> get(Class<T> clazz, List<QueryCondition> queryConditions,String orderBy);

    /**
     * @author 260172
     * @date 2018/6/25 15:32
     * 根据条件排序分页查找
     */
    <T> List<T> get(Class<T> clazz, List<QueryCondition> queryConditions,String orderBy, int currentPage,int pageSize);

    /**
     * @author 260172
     * @date 2018/6/25 15:32
     * 根据条件查询记录
     */
    <T> long getRecordCount(Class<T> clazz, List<QueryCondition> queryConditions);

    /**
     * @author 260172
     * @date 2018/6/25 15:34
     * 根据jpql查询记录
     */
    long getRecordCountByJpql(String jpql, Object... objects);

    /**
     * @author 260172
     * @date 2018/6/25 15:34
     * 根据jpql分页查询
     */
    <T> List<T> getPageQuery(String jpql, int currentPage,int pageSize, Object... objects);

    /**
     * @author 260172
     * @date 2018/6/25 15:36
     * 分页查询
     */
    <T> Pagination<T> getPagination(Class<T> clazz, List<QueryCondition> queryConditions, String orderBy, int currentPage,int pageSize);

    /**
     * @author 260172
     * @date 2018/6/25 15:37
     * 获取唯一结果集
     */
    Object getSingleResultByJpql(String jpql, Object... objects);

    /**
     * @author 260172
     * @date 2018/6/25 15:37
     * 获取唯一结果集
     */
    Object getSingleResultByLimit(String jpql, int limit, Object... objects);


    /**
     * @author Abin
     * @date 2018/7/9 14:17
     * 获取最大结果集
     */
    <T> List<T> getMaxResultByJpql(String jpql, int limit, Object... objects);

    /**
     * @author 260172
     * @date 2018/6/25 15:39
     * 原生sql查询
     */
    <T> List<T> getByNativeSQL(Class<T> clazz,String sql);

    /**
     * @author 260172
     * @date 2018/6/25 15:39
     * 原生sql查询
     */
    <T> List<T> getByNativeSQL(Class<T> clazz,String sql, Object... objects);

    /**
     * @author 260172
     * @date 2018/6/25 15:40
     * 原生sql查询 返回结果集条数
     */
    int getCountByNativeSQL(String sql);

    /**
     * @author 260172
     * @date 2018/6/25 15:40
     * 原生sql查询 返回结果集条数
     */
    int getCountByNativeSQL(String sql, Object... objects);
}
