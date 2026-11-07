package com.health.reservation.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.health.common.annotation.Excel;
import com.health.common.core.domain.BaseEntity;

/**
 * 套餐检查组关联对象 t_setmeal_checkgroup
 * 
 * @author 学员白
 * @date 2026-05-29
 */
public class TSetmealCheckgroup extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 套餐ID */
    private Long setmealId;

    /** 检查组ID */
    private Long checkgroupId;

    public void setSetmealId(Long setmealId) 
    {
        this.setmealId = setmealId;
    }

    public Long getSetmealId() 
    {
        return setmealId;
    }

    public void setCheckgroupId(Long checkgroupId) 
    {
        this.checkgroupId = checkgroupId;
    }

    public Long getCheckgroupId() 
    {
        return checkgroupId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("setmealId", getSetmealId())
            .append("checkgroupId", getCheckgroupId())
            .toString();
    }
}
