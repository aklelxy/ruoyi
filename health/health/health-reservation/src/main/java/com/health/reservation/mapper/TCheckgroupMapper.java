package com.health.reservation.mapper;

import java.util.List;
import com.health.reservation.domain.TCheckgroup;

/**
 * 检查组管理Mapper接口
 * 
 * @author ruoyi
 * @date 2026-05-28
 */
public interface TCheckgroupMapper 
{
    /**
     * 查询检查组管理
     * 
     * @param id 检查组管理主键
     * @return 检查组管理
     */
    public TCheckgroup selectTCheckgroupById(Long id);

    /**
     * 查询检查组管理列表
     * 
     * @param tCheckgroup 检查组管理
     * @return 检查组管理集合
     */
    public List<TCheckgroup> selectTCheckgroupList(TCheckgroup tCheckgroup);

    /**
     * 新增检查组管理
     * 
     * @param tCheckgroup 检查组管理
     * @return 结果
     */
    public int insertTCheckgroup(TCheckgroup tCheckgroup);

    /**
     * 修改检查组管理
     * 
     * @param tCheckgroup 检查组管理
     * @return 结果
     */
    public int updateTCheckgroup(TCheckgroup tCheckgroup);

    /**
     * 删除检查组管理
     * 
     * @param id 检查组管理主键
     * @return 结果
     */
    public int deleteTCheckgroupById(Long id);

    /**
     * 批量删除检查组管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTCheckgroupByIds(Long[] ids);
}
