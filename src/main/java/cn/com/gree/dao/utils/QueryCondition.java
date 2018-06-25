package cn.com.gree.dao.utils;

public class QueryCondition {
    /** 等于 */
    public static final String EQUAL = "=";
    /** 不等于 */
    public static final String NOT_EQUAL = "<>";
    /** 小于 */
    public static final String LESS = "<";
    /** 大于 */
    public static final String GREATER = ">";
    /** 小于等于 */
    public static final String LESS_EQUAL = "<=";
    /** 大于等于 */
    public static final String GREATER_EQUAL = ">=";
    /** 相似 */
    public static final String LIKE = "like";
    /** in */
    public static final String IN = "in";
    /** 自定义jpql语句 */
    public static final String CUSTOM = "custom";

    /** 属性名 */
    private String field;
    /** 操作符 */
    private String operator;
    /** 值 */
    private Object value;
    /** 自定义jpql语句 */
    private String customJPQL;

    public QueryCondition(String customJPQL) {
        this.customJPQL = customJPQL;
        this.operator = CUSTOM;
    }

    public QueryCondition(String field, String operator, Object value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getCustomJPQL() {
        return customJPQL;
    }

    public void setCustomJPQL(String customJPQL) {
        this.customJPQL = customJPQL;
    }
}
