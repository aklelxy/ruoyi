package com.health.reservation.service;

import java.util.List;
import com.health.reservation.domain.TCheckitem;

/**
 * 检查项管理Service接口
 * 
 * @author ruoyi
 * @date 2026-05-17
 */
public interface ITCheckitemService 
{
    /**
     * 查询检查项管理
     * 
     * @param id 检查项管理主键
     * @return 检查项管理
     */
    public TCheckitem selectTCheckitemById(Long id);

    /**
     * 查询检查项管理列表
     * 
     * @param tCheckitem 检查项管理
     * @return 检查项管理集合
     */
    public List<TCheckitem> selectTCheckitemList(TCheckitem tCheckitem);

    /**
     * 新增检查项管理
     * 
     * @param tCheckitem 检查项管理
     * @return 结果
     */
    public int insertTCheckitem(TCheckitem tCheckitem);

    /**
     * 修改检查项管理
     * 
     * @param tCheckitem 检查项管理
     * @return 结果
     */
    public int updateTCheckitem(TCheckitem tCheckitem);

    /**
     * 批量删除检查项管理
     * 
     * @param ids 需要删除的检查项管理主键集合
     * @return 结果
     */
    public int deleteTCheckitemByIds(Long[] ids);

    /**
     * 删除检查项管理信息
     * 
     * @param id 检查项管理主键
     * @return 结果
     */
    public int deleteTCheckitemById(Long id);
}
