package com.moirae.rosettaflow.mapper.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * t_meta_data_details
 *
 * @author admin
 */
@Data
@TableName(value = "t_meta_data_details")
public class MetaDataDetailsOld implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 数据详情表ID(自增长)
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 元数据id
     */
    private String metaDataId;
    /**
     * 列索引
     */
    private Integer columnIndex;
    /**
     * 列名
     */
    private String columnName;
    /**
     * 列类型
     */
    private String columnType;
    /**
     * 列大小（byte）
     */
    private Long columnSize;
    /**
     * 列描述
     */
    private String columnDesc;
    /**
     * 状态: 0-无效，1- 有效
     */
    @TableField(value = "`status`")
    private Byte status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        MetaDataDetailsOld other = (MetaDataDetailsOld) that;
        return (this.getMetaDataId() == null ? other.getMetaDataId() == null : this.getMetaDataId().equals(other.getMetaDataId()))
                && (this.getColumnIndex() == null ? other.getColumnIndex() == null : this.getColumnIndex().equals(other.getColumnIndex()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMetaDataId() == null) ? 0 : getMetaDataId().hashCode());
        result = prime * result + ((getColumnIndex() == null) ? 0 : getColumnIndex().hashCode());
        result = prime * result + ((getColumnName() == null) ? 0 : getColumnName().hashCode());
        result = prime * result + ((getColumnType() == null) ? 0 : getColumnType().hashCode());
        result = prime * result + ((getColumnSize() == null) ? 0 : getColumnSize().hashCode());
        result = prime * result + ((getColumnDesc() == null) ? 0 : getColumnDesc().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                " [" +
                "Hash = " + hashCode() +
                ", id=" + id +
                ", metaDataId=" + metaDataId +
                ", columnIndex=" + columnIndex +
                ", columnName=" + columnName +
                ", columnType=" + columnType +
                ", columnSize=" + columnSize +
                ", columnDesc=" + columnDesc +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", serialVersionUID=" + serialVersionUID +
                "]";
    }
}