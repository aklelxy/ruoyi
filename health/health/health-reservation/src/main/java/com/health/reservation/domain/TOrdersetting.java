package com.health.reservation.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.health.common.annotation.Excel;
import com.health.common.core.domain.BaseEntity;

/**
 * 预约设置对象 t_ordersetting
 *
 * @author 学员白
 * @date 2026-05-29
 */
public class TOrdersetting extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 预约设置ID */
    private Long id;

    /** 预约日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "预约日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date orderDate;

    /** 可预约人数 */
    @Excel(name = "可预约人数")
    private Long number;

    /** 已预约人数 */
    @Excel(name = "已预约人数")
    private Long reservations;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setOrderDate(Date orderDate)
    {
        this.orderDate = orderDate;
    }

    public Date getOrderDate()
    {
        return orderDate;
    }

    public void setNumber(Long number)
    {
        this.number = number;
    }

    public Long getNumber()
    {
        return number;
    }

    public void setReservations(Long reservations)
    {
        this.reservations = reservations;
    }

    public Long getReservations()
    {
        return reservations;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("orderDate", getOrderDate())
                .append("number", getNumber())
                .append("reservations", getReservations())
                .toString();
    }
}
