<template>
  <div class="app-container">
    <div class="box">
      <div class="box ordersetting">
        <el-card class="box-card">
          <template #header>
            <div class="boxMain">
              <el-button style="margin-bottom: 20px; margin-right: 20px" type="primary" @click="handleDownloadTemplate">
                模板下载
              </el-button>
              <el-upload
                  class="upload-demo"
                  :show-file-list="false"
                  :http-request="customUpload"
                  :before-upload="beforeUpload"
              >
                <el-button type="primary">上传文件</el-button>
              </el-upload>
            </div>
          </template>
          <div>
            操作说明：请点击"模板下载"按钮获取模板文件，在模板文件中录入预约设置数据后点击"上传文件"按钮上传模板文件。
          </div>
        </el-card>

        <div class="calendar">
          <!-- 年份 月份 -->
          <div class="month">
            <div class="currentdate">
              <span class="choose-year">{{ currentYear }}年</span>
              <span class="choose-month">{{ currentMonth }}月</span>
            </div>
            <div class="choose">
              <el-button link @click="goCurrentMonth" class="gotoday">今天</el-button>
              <el-button link @click="pickPre">❮</el-button>
              <el-button link @click="pickNext">❯</el-button>
            </div>
          </div>

          <!-- 星期 -->
          <ul class="weekdays">
            <li>周一</li>
            <li>周二</li>
            <li>周三</li>
            <li>周四</li>
            <li>周五</li>
            <li>周六</li>
            <li>周日</li>
          </ul>

          <!-- 日期 -->
          <ul class="days">
            <li v-for="(dayobject, index) in days" :key="index">
              <!-- 非当前月份 -->
              <div class="other-month" v-if="dayobject.day.getMonth() + 1 !== currentMonth">
                {{ dayobject.day.getDate() }}
              </div>

              <!-- 当前月 -->
              <div class="everyday" v-else>
                <span class="datenumber">{{ dayobject.day.getDate() }}</span>

                <template v-for="(obj, idx) in leftobj" :key="idx">
                  <div v-if="obj.date === dayobject.day.getDate()" :class="obj.number > obj.reservations ? 'usual' : 'fulled'">
                    <p>可预约{{ obj.number }}人</p>
                    <p>已预约{{ obj.reservations }}人</p>
                    <p v-if="obj.number <= obj.reservations">已满</p>
                  </div>
                </template>

                <button
                    v-if="dayobject.day > today"
                    @click="handleOrderSet(dayobject.day)"
                    class="orderbtn"
                >
                  设置
                </button>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  downloadTemplate,
  getOrderSettingByMonth,
  editNumberByOrderDate,
  addOrderSetting,uploadOrderSettingExcel
} from '@/api/reservation/ordersetting'

// 响应式数据
const today = ref(new Date())
const currentYear = ref(0)
const currentMonth = ref(0)
const days = ref([])
const leftobj = ref([])

// 格式化日期为 YYYY-MM-DD
const formatDate = (year, month, day) => {
  const y = year
  const m = String(month).padStart(2, '0')
  const d = String(day).padStart(2, '0')
  return `${y}-${m}-${d}`
}

// 初始化日历和数据
const initData = async (dateStr = null) => {
  const now = new Date()
  const targetDate = dateStr ? new Date(dateStr) : now
  const year = targetDate.getFullYear()
  const month = targetDate.getMonth() + 1 // 1-based

  currentYear.value = year
  currentMonth.value = month

  // 获取当月第一天是周几（周一为1，周日为0 → 转为周日=7）
  const firstDay = new Date(year, month - 1, 1)
  let firstDayOfWeek = firstDay.getDay() // 0=周日, 1=周一...
  if (firstDayOfWeek === 0) firstDayOfWeek = 7

  // 生成日历（6周 * 7天 = 42天）
  const calendarDays = []
  const totalDays = 42

  // 上月补全
  const prevMonthLastDate = new Date(year, month - 1, 0).getDate()
  for (let i = firstDayOfWeek - 1; i > 0; i--) {
    const d = new Date(year, month - 2, prevMonthLastDate - i + 1)
    calendarDays.push({ day: d })
  }

  // 本月
  const currentMonthLastDate = new Date(year, month, 0).getDate()
  for (let i = 1; i <= currentMonthLastDate; i++) {
    const d = new Date(year, month - 1, i)
    calendarDays.push({ day: d })
  }

  // 下月补全
  let nextDay = 1
  while (calendarDays.length < totalDays) {
    const d = new Date(year, month, nextDay++)
    calendarDays.push({ day: d })
  }

  days.value = calendarDays

  // 加载预约设置数据
  // 加载预约设置数据
  try {
    const res = await getOrderSettingByMonth(`${year}-${String(month).padStart(2, '0')}`)

    if (res.code === 200) {
      const rawData = Array.isArray(res.data) ? res.data : []
      const processedData = rawData.map(item => ({
        id: item.id, //  必须保留 id
        date: item.date,
        number: item.number,
        reservations: item.reservations !== null ? item.reservations : 0
      }))
      leftobj.value = processedData
    } else {
      ElMessage.warning(res.msg || '暂无预约设置数据')
    }
  } catch (error) {
    console.error('加载预约数据失败:', error)
    ElMessage.error('加载预约数据失败，请稍后重试')
  }
}

// 下载模板
const handleDownloadTemplate = async () => {
  try {
    const blob = await downloadTemplate()
    const url = window.URL.createObjectURL(blob)
    const a = document.createElement('a')
    a.href = url
    a.download = 'ordersetting_template.xlsx'
    document.body.appendChild(a)
    a.click()
    document.body.removeChild(a)
    window.URL.revokeObjectURL(url)
  } catch (error) {
    console.error('模板下载失败:', error)
    ElMessage.error('模板下载失败，请稍后重试')
  }
}

// 自定义上传方法
const customUpload = async (options) => {
  const { file } = options
  try {
    const res = await uploadOrderSettingExcel(file)
    handleSuccess(res) // 你已有的成功回调
  } catch (error) {
    console.error('上传失败:', error)
    ElMessage.error('上传失败，请稍后重试')
  }
}
// 上传前校验
const beforeUpload = (file) => {
  const validTypes = [
    'application/vnd.ms-excel',
    'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
  ]
  const isValid = validTypes.includes(file.type)
  if (!isValid) {
    ElMessage.error('仅支持 .xls 或 .xlsx 格式文件！')
  }
  return isValid
}

// 上传成功
const handleSuccess = (response) => {
  if (response.code === 200) {
    ElMessage.success(response.msg || '上传成功')
    initData() // 重新加载当前月份数据
  } else {
    ElMessage.error(response.msg || '上传失败')
  }
}
const handleOrderSet = (day) => {
  const clickDate = day.getDate()
  const clickDateStr = formatDate(day.getFullYear(), day.getMonth() + 1, clickDate)

  // 查找是否已有该日期的设置（包含 id）
  const existing = leftobj.value.find(item => item.date === clickDate)

  ElMessageBox.prompt(
      `请输入可预约人数${existing ? '（当前值: ' + existing.number + '）' : ''}`,
      '预约设置',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /^[1-9]\d*$/,
        inputErrorMessage: '请输入大于0的整数',
        inputValue: existing ? String(existing.number) : ''
      }
  )
      .then(async ({ value }) => {
        const number = Number(value)

        try {
          let res
          if (existing) {
            //打印existing
            console.log(existing)
            // ✅ 修改：需要传 id
            res = await editNumberByOrderDate({
              id: existing.id,        // ⬅️ 必须传 id
              number: number,
              orderDate: clickDateStr // 有些接口需要完整日期
            })
          } else {
            // ✅ 新增：不传 id 或 id 为 null
            res = await addOrderSetting({
              number: number,
              orderDate: clickDateStr
            })
          }

          // 统一处理响应
          if (res.code === 200) {
            ElMessage.success(existing ? '修改成功' : '新增成功')

            // ✅ 更新本地数据
            if (existing) {
              existing.number = number
              // id 不变
            } else {
              // 假设后端返回新增记录的 id（理想情况）
              // 如果没有，可以临时用负数或时间戳，但最好从 res.data 拿
              leftobj.value.push({
                date: clickDate,
                number: number,
                reservations: 0,
                id: res.data?.id || null // 如果后端返回了新 id，就用它
              })
            }
          } else {
            ElMessage.error(res.msg || (existing ? '修改失败' : '新增失败'))
          }
        } catch (error) {
          console.error('操作失败:', error)
          ElMessage.error('操作失败，请稍后重试')
        }
      })
      .catch(() => {
        ElMessage.info('已取消')
      })
}
// 切换到今天所在月份
const goCurrentMonth = () => {
  initData()
}

// 上一月
const pickPre = () => {
  const prev = new Date(currentYear.value, currentMonth.value - 1, 0)
  initData(formatDate(prev.getFullYear(), prev.getMonth() + 1, 1))
}

// 下一月
const pickNext = () => {
  const next = new Date(currentYear.value, currentMonth.value, 1)
  initData(formatDate(next.getFullYear(), next.getMonth() + 1, 1))
}

// 初始化
onMounted(() => {
  initData()
})
</script>

<style scoped>
.app-container {
  padding: 20px;
}

.box {
  background: #fff;
  border-radius: 4px;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.05);
}

.ordersetting {
  padding: 20px;
}

.calendar {
  margin-top: 20px;
}

.month {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
  border-bottom: 1px solid #eee;
}

.currentdate {
  font-size: 16px;
  font-weight: bold;
}

.choose {
  display: flex;
  align-items: center;
}

.gotoday {
  color: #409eff;
}

.weekdays {
  list-style: none;
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  padding: 0;
  margin: 0;
}

.weekdays li {
  text-align: center;
  padding: 10px 0;
  background-color: #fafafa;
  font-weight: bold;
}

.days {
  list-style: none;
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 1px;
  background-color: #eee;
  padding: 0;
  margin: 0;
}

.days li {
  height: 120px;
  background-color: white;
  padding: 5px;
  position: relative;
  overflow: hidden;
}

.other-month {
  opacity: 0.4;
}

.everyday {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
}

.datenumber {
  font-weight: bold;
  font-size: 14px;
}

.usual {
  font-size: 12px;
  color: #67c23a;
}

.fulled {
  font-size: 12px;
  color: #f56c6c;
}

.orderbtn {
  position: absolute;
  bottom: 5px;
  right: 5px;
  padding: 2px 6px;
  font-size: 12px;
  background-color: #409eff;
  color: white;
  border: none;
  border-radius: 3px;
  cursor: pointer;
}

.orderbtn:hover {
  background-color: #66b1ff;
}
</style>