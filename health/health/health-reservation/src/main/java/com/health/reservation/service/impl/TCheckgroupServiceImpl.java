package com.health.reservation.service.impl;

import java.util.List;
import com.health.common.utils.DateUtils;
import com.health.reservation.dto.TCheckgroupDto;
import com.health.reservation.mapper.TCheckgroupCheckitemMapper;
import com.health.reservation.mapper.TSetmealCheckgroupMapper;
import com.health.reservation.vo.TCheckgroupVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.health.reservation.mapper.TCheckgroupMapper;
import com.health.reservation.domain.TCheckgroup;
import com.health.reservation.service.ITCheckgroupService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 检查组管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-05-28
 */
@Transactional
@Service
public class TCheckgroupServiceImpl implements ITCheckgroupService 
{
    @Autowired
    private TCheckgroupMapper tCheckgroupMapper;
    @Autowired
    private TCheckgroupCheckitemMapper tCheckgroupCheckitemMapper;
    @Autowired
    private TSetmealCheckgroupMapper tSetmealCheckgroupMapper;

    /**
     * 查询检查组管理
     * 
     * @param id 检查组管理主键
     * @return 检查组管理
     */
    @Override
    public TCheckgroupVo selectTCheckgroupById(Long id)
    {
       
        //根据检查组id查询检查组信息
        TCheckgroup tCheckgroup = tCheckgroupMapper.selectTCheckgroupById(id);
        //根据检查组id查询检检查项的关联信息
        List <Long> checkItemIds= tCheckgroupCheckitemMapper.selectCheckitemIds(id);
        //封装数据
        TCheckgroupVo tCheckgroupVo = new TCheckgroupVo();
        BeanUtils.copyProperties(tCheckgroup,tCheckgroupVo);
        tCheckgroupVo.setCheckItemIds(checkItemIds);
        //返回结果
        return tCheckgroupVo;
    }

    /**
     * 查询检查组管理列表
     * 
     * @param tCheckgroup 检查组管理
     * @return 检查组管理
     */
    @Override
    public List<TCheckgroup> selectTCheckgroupList(TCheckgroup tCheckgroup)
    {
        return tCheckgroupMapper.selectTCheckgroupList(tCheckgroup);
    }

    /**
     * 新增检查组管理
     * 
     * @param tCheckgroup 检查组管理
     * @return 结果
     */
    @Override

    public int insertTCheckgroup(TCheckgroupDto dto)
    {
//        tCheckgroup.setCreateTime(DateUtils.getNowDate());
//        return tCheckgroupMapper.insertTCheckgroup(tCheckgroup);
        //保存检查组基本信息
        dto.setCreateTime(DateUtils.getNowDate());
        int rows = tCheckgroupMapper.insertTCheckgroup(dto);
        //获取检查组id
        //保存检查组和检查项的关联关系
        if (rows >0 &&dto.getCheckItemIds()!= null &&  dto.getCheckItemIds().size() >0) {
            tCheckgroupCheckitemMapper.insertCheckitemIds(dto.getId(), dto.getCheckItemIds());
        }
        return  rows;
    }

    /**
     * 修改检查组管理
     * 
     * @param tCheckgroup 检查组管理
     * @return 结果
     */
    @Override
    public int updateTCheckgroup(TCheckgroupDto dto)
    {
        try {
            //修改检查组基本信息
            dto.setUpdateTime(DateUtils.getNowDate());
            int rows = tCheckgroupMapper.updateTCheckgroup(dto);
            //判断是否有关联关系
            if (dto.getCheckItemIds() != null && dto.getCheckItemIds().size() >0 && rows >0) {
                //有则删除关联信息后再添加
                tCheckgroupCheckitemMapper.deleteTCheckgroupCheckitemByCheckgroupId(dto.getId());
                //添加关联关系
                tCheckgroupCheckitemMapper.insertCheckitemIds(dto.getId(), dto.getCheckItemIds());
            }
            return rows;
        }catch (Exception e){
            throw new RuntimeException("修改检查组失败");}

    }

    /**
     * 批量删除检查组管理
     * 
     * @param ids 需要删除的检查组管理主键
     * @return 结果
     */
    @Override
    public int deleteTCheckgroupByIds(Long[] ids)
    {
        return tCheckgroupMapper.deleteTCheckgroupByIds(ids);
    }

    /**
     * 删除检查组管理信息
     * 
     * @param id 检查组管理主键
     * @return 结果
     */
    @Override
    public int deleteTCheckgroupById(Long id)
    {
        //检查检查组和套餐是否有关联关系
       int count= tSetmealCheckgroupMapper.selectCountByCheckgroupId(id);
       //有关联关系则不能删除
        if(count>0){
            throw new RuntimeException("当前检查组被套餐引用，不能删除");
        }
        //和套餐没有关联信息，先删除检查组和检查项的关联关系
        tCheckgroupCheckitemMapper.deleteTCheckgroupCheckitemByCheckgroupId(id);
        //删除检查组
        return tCheckgroupMapper.deleteTCheckgroupById(id);
    }
}
