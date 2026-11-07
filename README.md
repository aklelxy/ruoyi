# 体检预约管理系统（health）

> 基于 [RuoYi-Vue3](https://github.com/yangzongzhuan/RuoYi-Vue3)（Vue 3 + Element Plus + Vite）与若依 Spring Boot 3 后端二次开发的体检预约管理平台。
> 本仓库为作者在企业工程实践项目基础上的改造与业务实现，主要用于毕业设计（论文）课题的研究与开发。

---

## 一、项目简介

本项目面向体检机构 / 医院健康管理中心的预约业务，提供「号源配置 → 预约管理 → 数据统计」的后台管理能力。

系统以若依（RuoYi）前后端分离框架为底座，复用其用户、角色、菜单、部门数据权限、字典、定时任务、代码生成等基础能力，并在此基础上新增了独立的体检预约业务模块 `health-reservation`，实现了预约号源的日历化管理。

当前阶段已完成**预约号源管理**模块；后续将围绕毕业设计课题，扩展会员、套餐、订单等业务，并接入大语言模型智能体（Agent），实现「自然语言对话即可完成预约办理」。

## 二、功能特性

### 已实现（本人开发）

- **预约号源日历视图**：按月展示每日「可预约人数 / 已预约人数」，已满日期高亮提示；支持月份切换与回到今天。
- **号源设置**：对尚未到来的日期设置可预约人数，支持单个日期设置与修改。
- **Excel 批量导入 / 导出**：提供标准模板下载，填写后上传导入；支持导出预约设置数据。
- **列表管理**：按日期范围查询、批量删除、导出。
- **号源原子扣减接口**：`occupySlot` 通过数据库条件更新保证不超卖，供小程序 / 智能体等外部端调用。
- **权限控制**：基于若依 RBAC，菜单与按钮级权限（`reservation:ordersetting:*`）。

### 框架内置能力（若依提供）

用户管理、角色管理、菜单管理、部门管理（数据权限）、岗位管理、字典管理、参数管理、通知公告、操作日志、登录日志、在线用户、定时任务、代码生成、服务监控。

### 后续规划

- 会员 / 体检套餐 / 预约订单模块；
- 基于 Spring AI 的体检预约智能体：工具调用（Function Calling / MCP）、多轮任务编排、写操作二次确认、权限透传；
- 面向业务场景的智能体评测集与指标体系。

## 三、技术栈

| 分类 | 技术 | 版本 / 说明 |
| --- | --- | --- |
| 后端框架 | Spring Boot | 3.5.11（JDK 17） |
| 安全 | Spring Security + JWT | 无状态鉴权 |
| 持久层 | MyBatis + PageHelper | XML 映射 |
| 数据库 | MySQL | 8.0 |
| 连接池 | Druid | 1.2.28 |
| 缓存 | Redis | 会话 / 验证码 |
| 定时任务 | Quartz | 若依内置 |
| 工具库 | Hutool、Fastjson2、POI、Velocity | 代码生成、Excel |
| 前端框架 | Vue 3 + Vite | RuoYi-Vue3 |
| UI 组件 | Element Plus | 2.13.x |
| 状态管理 | Pinia | — |
| 路由 | Vue Router 4 | 动态路由 |
| 图表 | ECharts | 5.6 |

## 四、目录结构

```
ruoyi/
├── README.md
└── health/
    ├── health/                        # 后端（Maven 多模块）
    │   ├── health-admin               # 启动模块：配置、主类、Controller 入口
    │   ├── health-framework           # 核心框架：Security、JWT、拦截器、数据权限
    │   ├── health-system              # 系统管理：用户、角色、菜单、字典等
    │   ├── health-common              # 公共模块：工具类、注解、Excel、分页
    │   ├── health-quartz              # 定时任务
    │   ├── health-generator           # 代码生成器
    │   ├── health-reservation         # 【业务模块】体检预约号源管理
    │   ├── health-ui                  # 旧版 Vue2 前端（保留，未使用）
    │   ├── sql/                       # 数据库脚本
    │   │   ├── ry_20260417.sql        # 若依基础表
    │   │   ├── quartz.sql             # 定时任务表
    │   │   ├── t_ordersetting.sql     # 预约设置表
    │   │   ├── t_ordersetting_menu.sql# 预约管理菜单与权限
    │   │   └── t_ordersetting_upgrade.sql
    │   └── pom.xml
    └── health-Vue3-ui/                # 前端（Vue3）
        ├── src/
        │   ├── api/reservation/       # 预约模块接口封装
        │   ├── views/reservation/     # 预约设置页面（日历 + 列表）
        │   ├── views/system/          # 若依系统管理页面
        │   └── ...
        ├── .env.development           # 开发环境配置（接口代理）
        └── package.json
```

## 五、快速开始

### 1. 环境要求

| 组件 | 版本 |
| --- | --- |
| JDK | 17+ |
| Maven | 3.6+ |
| MySQL | 5.7 / 8.0 |
| Redis | 5.0+ |
| Node.js | 18+（建议 20 LTS） |

### 2. 数据库初始化

```sql
-- 1. 创建数据库
CREATE DATABASE `health` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- 2. 依次导入脚本（在 health/health/sql 目录下）
--    ry_20260417.sql     若依基础表 + 初始数据
--    quartz.sql          定时任务表
--    t_ordersetting.sql  预约设置业务表
--    t_ordersetting_menu.sql  预约管理菜单与按钮权限
```

> 数据库连接配置位于 `health/health/health-admin/src/main/resources/application-druid.yml`，
> 请将 `url / username / password` 修改为自己本地环境的值（仓库中不含真实密码）。

Redis 配置位于 `health/health/health-admin/src/main/resources/application.yml`，同样请替换为本地配置。

### 3. 启动后端

```bash
cd health/health
mvn clean package -DskipTests
java -jar health-admin/target/health-admin.jar
```

或在 IDEA 中运行 `com.health.HealthApplication`（启动类位于 `health-admin` 模块）。

默认端口：`8080`，接口前缀：`/dev-api`（前端代理）或直接访问 `http://localhost:8080`。

### 4. 启动前端

```bash
cd health/health-Vue3-ui
npm install
npm run dev
```

默认访问地址：`http://localhost:80`（端口可在 `vite.config.js` 中修改）。
开发环境下 `/dev-api` 已代理至 `http://localhost:8080`，配置见 `.env.development`。

### 5. 默认账号

| 账号 | 密码 | 说明 |
| --- | --- | --- |
| admin | admin123 | 超级管理员 |

> 生产环境请务必修改默认密码，并关闭 Druid 监控页的匿名访问。

## 六、核心模块：预约号源管理（health-reservation）

### 6.1 数据表

`t_ordersetting`（预约设置表）：

| 字段 | 类型 | 说明 |
| --- | --- | --- |
| id | int | 主键 |
| order_date | date | 预约日期（唯一） |
| number | int | 可预约人数 |
| reservations | int | 已预约人数 |

### 6.2 接口清单

| 方法 | 路径 | 权限 | 说明 |
| --- | --- | --- | --- |
| GET | `/reservation/ordersetting/list` | `reservation:ordersetting:list` | 分页查询 |
| GET | `/reservation/ordersetting/getOrderSettingByMonth?month=yyyy-MM` | list | 按月查询日历数据 |
| GET | `/reservation/ordersetting/getByOrderDate?orderDate=yyyy-MM-dd` | query | 查询某日号源可用情况 |
| GET | `/reservation/ordersetting/availabilityList` | list | 号源可用列表 |
| POST | `/reservation/ordersetting/add` | add | 新增号源设置 |
| PUT | `/reservation/ordersetting/editNumberByOrderDate` | edit | 修改某日可预约人数 |
| POST | `/reservation/ordersetting/occupySlot?orderDate=` | edit | 占用一个号源（供小程序 / 智能体调用） |
| GET | `/reservation/ordersetting/downloadTemplate` | list | 下载 Excel 导入模板 |
| POST | `/reservation/ordersetting/upload` | add | 上传 Excel 批量导入 |
| POST | `/reservation/ordersetting/export` | export | 导出 |
| DELETE | `/reservation/ordersetting/{ids}` | remove | 批量删除 |

### 6.3 并发与一致性

号源扣减采用「数据库条件更新」实现原子扣减，避免并发下的超卖：

```sql
UPDATE t_ordersetting
SET reservations = IFNULL(reservations, 0) + 1
WHERE order_date = #{orderDate}
  AND IFNULL(reservations, 0) < number;
```

返回受影响行数为 0 时判定为「号源已满」，由 Service 层转换为业务异常。

## 七、本人工作说明

| 类别 | 内容 |
| --- | --- |
| 前期基础 | 企业工程实践环节提供的若依 v3.9.2 工程原型（含框架与体检预约雏形） |
| 本人完成 | 后端多模块重命名与工程改造；`health-reservation` 预约业务模块（实体 / Mapper / Service / Controller / VO / 单元测试）；前端预约设置页面（日历视图 + 列表管理）；数据库脚本与菜单权限配置；Excel 模板导入导出；号源原子扣减接口 |
| 后续增量 | 会员 / 套餐 / 订单模块，智能体（Agent）工具层与编排、评测体系（见课题规划） |
| 开源声明 | 若依为 MIT 协议开源项目，本仓库保留其版权声明并在此基础上次开发 |

## 八、开源协议与致谢

- 本项目基于 [RuoYi-Vue3](https://github.com/yangzongzhuan/RuoYi-Vue3)（MIT License）与 [RuoYi-Vue](https://gitee.com/y_project/RuoYi-Vue) 二次开发，遵循 MIT 协议，保留原始版权声明。
- 感谢若依作者及开源社区。
- 本项目中的业务代码、页面与数据库设计仅用于学习与毕业设计研究，不含任何真实患者数据。

## 九、常见问题

**Q1：启动报 Redis 连接失败？**
检查 `application.yml` 中的 Redis 地址与密码，确保本地 Redis 已启动。

**Q2：前端请求 404 / 跨域？**
确认后端已启动在 8080 端口，且 `.env.development` 中 `VITE_APP_BASE_API` 为 `/dev-api`，Vite 代理指向 `http://localhost:8080`。

**Q3：导入 Excel 提示日期格式错误？**
模板中日期列格式需为 `yyyy-MM-dd`，且不支持导入过去日期。

**Q4：菜单里看不到「预约管理」？**
需执行 `t_ordersetting_menu.sql` 并重新登录（或清除 Redis 缓存）以刷新菜单权限。
