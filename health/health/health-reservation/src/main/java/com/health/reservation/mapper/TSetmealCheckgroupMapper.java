package com.health.reservation.mapper;

import java.util.List;
import com.health.reservation.domain.TSetmealCheckgroup;
import org.apache.ibatis.annotations.Select;

/**
 * 套餐检查组关联Mapper接口
 * 
 * @author 学员白
 * @date 2026-05-29
 */
public interface TSetmealCheckgroupMapper 
{
    /**
     * 查询套餐检查组关联
     * 
     * @param setmealId 套餐检查组关联主键
     * @return 套餐检查组关联
     */
    public TSetmealCheckgroup selectTSetmealCheckgroupBySetmealId(Long setmealId);

    /**
     * 查询套餐检查组关联列表
     * 
     * @param tSetmealCheckgroup 套餐检查组关联
     * @return 套餐检查组关联集合
     */
    public List<TSetmealCheckgroup> selectTSetmealCheckgroupList(TSetmealCheckgroup tSetmealCheckgroup);

    /**
     * 新增套餐检查组关联
     * 
     * @param tSetmealCheckgroup 套餐检查组关联
     * @return 结果
     */
    public int insertTSetmealCheckgroup(TSetmealCheckgroup tSetmealCheckgroup);

    /**
     * 修改套餐检查组关联
     * 
     * @param tSetmealCheckgroup 套餐检查组关联
     * @return 结果
     */
    public int updateTSetmealCheckgroup(TSetmealCheckgroup tSetmealCheckgroup);

    /**
     * 删除套餐检查组关联
     * 
     * @param setmealId 套餐检查组关联主键
     * @return 结果
     */
    public int deleteTSetmealCheckgroupBySetmealId(Long setmealId);

    /**
     * 批量删除套餐检查组关联
     * 
     * @param setmealIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTSetmealCheckgroupBySetmealIds(Long[] setmealIds);
    @Select("select count(*) from t_setmeal_checkgroup where checkgroup_id=#{id}")
    int selectCountByCheckgroupId(Long id);
}
