package com.example.springboot.pojo;

import java.io.Serializable;
import java.util.Date;

public class ColumnDetailHis implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column column_detail_his.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column column_detail_his.column_name_cn
     *
     * @mbggenerated
     */
    private String columnNameCn;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column column_detail_his.column_name_en
     *
     * @mbggenerated
     */
    private String columnNameEn;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column column_detail_his.table_name
     *
     * @mbggenerated
     */
    private String tableName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column column_detail_his.parent_column_en
     *
     * @mbggenerated
     */
    private String parentColumnEn;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column column_detail_his.create_by
     *
     * @mbggenerated
     */
    private String createBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column column_detail_his.create_at
     *
     * @mbggenerated
     */
    private Date createAt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column column_detail_his.update_by
     *
     * @mbggenerated
     */
    private String updateBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column column_detail_his.is_delete
     *
     * @mbggenerated
     */
    private Byte isDelete;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column column_detail_his.update_at
     *
     * @mbggenerated
     */
    private Date updateAt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column column_detail_his.column_type
     *
     * @mbggenerated
     */
    private String columnType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column column_detail_his.column_example
     *
     * @mbggenerated
     */
    private String columnExample;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column column_detail_his.remark
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table column_detail_his
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column column_detail_his.id
     *
     * @return the value of column_detail_his.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column column_detail_his.id
     *
     * @param id the value for column_detail_his.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column column_detail_his.column_name_cn
     *
     * @return the value of column_detail_his.column_name_cn
     *
     * @mbggenerated
     */
    public String getColumnNameCn() {
        return columnNameCn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column column_detail_his.column_name_cn
     *
     * @param columnNameCn the value for column_detail_his.column_name_cn
     *
     * @mbggenerated
     */
    public void setColumnNameCn(String columnNameCn) {
        this.columnNameCn = columnNameCn == null ? null : columnNameCn.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column column_detail_his.column_name_en
     *
     * @return the value of column_detail_his.column_name_en
     *
     * @mbggenerated
     */
    public String getColumnNameEn() {
        return columnNameEn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column column_detail_his.column_name_en
     *
     * @param columnNameEn the value for column_detail_his.column_name_en
     *
     * @mbggenerated
     */
    public void setColumnNameEn(String columnNameEn) {
        this.columnNameEn = columnNameEn == null ? null : columnNameEn.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column column_detail_his.table_name
     *
     * @return the value of column_detail_his.table_name
     *
     * @mbggenerated
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column column_detail_his.table_name
     *
     * @param tableName the value for column_detail_his.table_name
     *
     * @mbggenerated
     */
    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column column_detail_his.parent_column_en
     *
     * @return the value of column_detail_his.parent_column_en
     *
     * @mbggenerated
     */
    public String getParentColumnEn() {
        return parentColumnEn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column column_detail_his.parent_column_en
     *
     * @param parentColumnEn the value for column_detail_his.parent_column_en
     *
     * @mbggenerated
     */
    public void setParentColumnEn(String parentColumnEn) {
        this.parentColumnEn = parentColumnEn == null ? null : parentColumnEn.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column column_detail_his.create_by
     *
     * @return the value of column_detail_his.create_by
     *
     * @mbggenerated
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column column_detail_his.create_by
     *
     * @param createBy the value for column_detail_his.create_by
     *
     * @mbggenerated
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column column_detail_his.create_at
     *
     * @return the value of column_detail_his.create_at
     *
     * @mbggenerated
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column column_detail_his.create_at
     *
     * @param createAt the value for column_detail_his.create_at
     *
     * @mbggenerated
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column column_detail_his.update_by
     *
     * @return the value of column_detail_his.update_by
     *
     * @mbggenerated
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column column_detail_his.update_by
     *
     * @param updateBy the value for column_detail_his.update_by
     *
     * @mbggenerated
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column column_detail_his.is_delete
     *
     * @return the value of column_detail_his.is_delete
     *
     * @mbggenerated
     */
    public Byte getIsDelete() {
        return isDelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column column_detail_his.is_delete
     *
     * @param isDelete the value for column_detail_his.is_delete
     *
     * @mbggenerated
     */
    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column column_detail_his.update_at
     *
     * @return the value of column_detail_his.update_at
     *
     * @mbggenerated
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column column_detail_his.update_at
     *
     * @param updateAt the value for column_detail_his.update_at
     *
     * @mbggenerated
     */
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column column_detail_his.column_type
     *
     * @return the value of column_detail_his.column_type
     *
     * @mbggenerated
     */
    public String getColumnType() {
        return columnType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column column_detail_his.column_type
     *
     * @param columnType the value for column_detail_his.column_type
     *
     * @mbggenerated
     */
    public void setColumnType(String columnType) {
        this.columnType = columnType == null ? null : columnType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column column_detail_his.column_example
     *
     * @return the value of column_detail_his.column_example
     *
     * @mbggenerated
     */
    public String getColumnExample() {
        return columnExample;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column column_detail_his.column_example
     *
     * @param columnExample the value for column_detail_his.column_example
     *
     * @mbggenerated
     */
    public void setColumnExample(String columnExample) {
        this.columnExample = columnExample == null ? null : columnExample.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column column_detail_his.remark
     *
     * @return the value of column_detail_his.remark
     *
     * @mbggenerated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column column_detail_his.remark
     *
     * @param remark the value for column_detail_his.remark
     *
     * @mbggenerated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table column_detail_his
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", columnNameCn=").append(columnNameCn);
        sb.append(", columnNameEn=").append(columnNameEn);
        sb.append(", tableName=").append(tableName);
        sb.append(", parentColumnEn=").append(parentColumnEn);
        sb.append(", createBy=").append(createBy);
        sb.append(", createAt=").append(createAt);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", updateAt=").append(updateAt);
        sb.append(", columnType=").append(columnType);
        sb.append(", columnExample=").append(columnExample);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}