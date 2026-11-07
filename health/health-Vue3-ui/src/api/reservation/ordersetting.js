// src/api/reservation/ordersetting.js
import request from '@/utils/request'

/**
 * 下载预约设置模板文件
 * @returns {Promise<Blob>} 返回二进制文件流（用于前端下载）
 */
export function downloadTemplate() {
    return request({
        url: '/reservation/ordersetting/downloadTemplate',
        method: 'get',
        responseType: 'blob'
    })
}

/**
 * 根据年月获取预约设置数据
 * @param {string} month - 格式：'YYYY-MM'，例如 '2025-10'
 * @returns {Promise}
 */
export function getOrderSettingByMonth(month) {
    return request({
        url: '/reservation/ordersetting/getOrderSettingByMonth',
        method: 'get',
        params: { month }
    })
}

/**
 * 编辑某天的可预约人数
 * @param {Object} data
 * @param {number} data.number - 可预约人数
 * @param {string} data.orderDate - 日期，格式 'YYYY-MM-DD'
 * @returns {Promise}
 */
export function editNumberByOrderDate(data) {
    return request({
        url: '/reservation/ordersetting/editNumberByOrderDate',
        method: 'put',
        data
    })
}

/**
 * 添加预约设置
 * @param data
 * @returns {*}
 */
export function addOrderSetting(data) {
    return request({
        url: '/reservation/ordersetting/add',
        method: 'post',
        data
    })
}


/**
 * 上传 Excel 文件
 * @param {File} file - 选中的文件
 * @returns {Promise}
 */
export function uploadOrderSettingExcel(file) {
    const formData = new FormData()
    formData.append('excelFile', file)

    return request({
        url: '/reservation/ordersetting/upload',
        method: 'post',
        data: formData,
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}