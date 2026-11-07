package com.health.reservation.mapper;

import java.util.List;
import com.health.reservation.domain.TOrdersetting;

/**
 * 预约设置Mapper接口
 *
 * @author 学员白
 * @date 2026-05-29
 */
public interface TOrdersettingMapper
{
    /**
     * 查询预约设置
     *
     * @param id 预约设置主键
     * @return 预约设置
     */
    public TOrdersetting selectTOrdersettingById(Long id);

    /**
     * 查询预约设置列表
     *
     * @param tOrdersetting 预约设置
     * @return 预约设置集合
     */
    public List<TOrdersetting> selectTOrdersettingList(TOrdersetting tOrdersetting);

    /**
     * 新增预约设置
     *
     * @param tOrdersetting 预约设置
     * @return 结果
     */
    public int insertTOrdersetting(TOrdersetting tOrdersetting);

    /**
     * 修改预约设置
     *
     * @param tOrdersetting 预约设置
     * @return 结果
     */
    public int updateTOrdersetting(TOrdersetting tOrdersetting);

    /**
     * 删除预约设置
     *
     * @param id 预约设置主键
     * @return 结果
     */
    public int deleteTOrdersettingById(Long id);

    /**
     * 批量删除预约设置
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTOrdersettingByIds(Long[] ids);
}
