package com.health.reservation.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.health.common.annotation.Excel;
import com.health.common.core.domain.BaseEntity;

/**
 * 检查项管理对象 t_checkitem
 * 
 * @author ruoyi
 * @date 2026-05-17
 */
public class TCheckitem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 检查项编码 */
    @Excel(name = "检查项编码")
    private String code;

    /** 检查项名称 */
    @Excel(name = "检查项名称")
    private String name;

    /** 检查项性别 */
    @Excel(name = "检查项性别")
    private String sex;

    /** 适用年龄 */
    @Excel(name = "适用年龄")
    private String age;

    /** 价格 */
    @Excel(name = "价格")
    private Long price;

    /** 检查项类型 */
    @Excel(name = "检查项类型")
    private String type;

    /** 注意事项 */
    @Excel(name = "注意事项")
    private String attention;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setCode(String code) 
    {
        this.code = code;
    }

    public String getCode() 
    {
        return code;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    public void setSex(String sex) 
    {
        this.sex = sex;
    }

    public String getSex() 
    {
        return sex;
    }

    public void setAge(String age) 
    {
        this.age = age;
    }

    public String getAge() 
    {
        return age;
    }

    public void setPrice(Long price) 
    {
        this.price = price;
    }

    public Long getPrice() 
    {
        return price;
    }

    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }

    public void setAttention(String attention) 
    {
        this.attention = attention;
    }

    public String getAttention() 
    {
        return attention;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("code", getCode())
            .append("name", getName())
            .append("sex", getSex())
            .append("age", getAge())
            .append("price", getPrice())
            .append("type", getType())
            .append("attention", getAttention())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
