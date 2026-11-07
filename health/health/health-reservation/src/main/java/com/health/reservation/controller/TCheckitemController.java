package com.health.reservation.controller;

import java.util.List;

import com.health.reservation.domain.TCheckgroup;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.health.common.annotation.Log;
import com.health.common.core.controller.BaseController;
import com.health.common.core.domain.AjaxResult;
import com.health.common.enums.BusinessType;
import com.health.reservation.domain.TCheckitem;
import com.health.reservation.service.ITCheckitemService;
import com.health.common.utils.poi.ExcelUtil;
import com.health.common.core.page.TableDataInfo;

/**
 * 检查项管理Controller
 *
 * @author ruoyi
 * @date 2026-05-17
 */
@RestController
@RequestMapping("/reservation/checkitem")
public class TCheckitemController extends BaseController
{
    @Autowired
    private ITCheckitemService tCheckitemService;

    /**
     * 查询检查项管理列表
     */
    @PreAuthorize("@ss.hasPermi('reservation:checkitem:list')")
    @GetMapping("/list")
    public TableDataInfo list(TCheckitem tCheckitem)
    {
        startPage();
        List<TCheckitem> list = tCheckitemService.selectTCheckitemList(tCheckitem);
        return getDataTable(list);
    }

    /**
     * 导出检查项管理列表
     */
    @PreAuthorize("@ss.hasPermi('reservation:checkitem:export')")
    @Log(title = "检查项管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TCheckitem tCheckitem)
    {
        List<TCheckitem> list = tCheckitemService.selectTCheckitemList(tCheckitem);
        ExcelUtil<TCheckitem> util = new ExcelUtil<TCheckitem>(TCheckitem.class);
        util.exportExcel(response, list, "检查项管理数据");
    }

    /**
     * 获取检查项管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('reservation:checkitem:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tCheckitemService.selectTCheckitemById(id));
    }

    /**
     * 新增检查项管理
     */
    @PreAuthorize("@ss.hasPermi('reservation:checkitem:add')")
    @Log(title = "检查项管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TCheckitem tCheckitem)
    {
        return toAjax(tCheckitemService.insertTCheckitem(tCheckitem));
    }

    /**
     * 修改检查项管理
     */
    @PreAuthorize("@ss.hasPermi('reservation:checkitem:edit')")
    @Log(title = "检查项管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TCheckitem tCheckitem)
    {
        return toAjax(tCheckitemService.updateTCheckitem(tCheckitem));
    }

    /**
     * 删除检查项管理
     */
    @PreAuthorize("@ss.hasPermi('reservation:checkitem:remove')")
    @Log(title = "检查项管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tCheckitemService.deleteTCheckitemByIds(ids));
    }

    /**
     * 查询所有检查项
     * @return
     */
    @PreAuthorize("@ss.hasPermi('reservation:checkitem:list')")
    @GetMapping("/all")
    public AjaxResult all()
    {
        return success(tCheckitemService.selectTCheckitemList(null));
    }
}
