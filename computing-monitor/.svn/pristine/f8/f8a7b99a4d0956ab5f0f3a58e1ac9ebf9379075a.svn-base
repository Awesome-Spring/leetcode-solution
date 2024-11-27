CREATE TABLE bm_exception
(
    id           INT          NOT NULL AUTO_INCREMENT COMMENT 'ID',
    name         VARCHAR(255) NOT NULL COMMENT '异常名称',
    code         INT(10)      NOT NULL COMMENT '异常code', -- 唯一索引
    source       VARCHAR(255) NOT NULL COMMENT '异常数据来源   host-monitor上报;主动探测;desktop_start上报',
    detail       VARCHAR(255) NOT NULL COMMENT '详细信息',
    created_time DATETIME COMMENT '创建时间',
    updated_time DATETIME COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE INDEX idx_code (code)                           -- 添加唯一索引
) COMMENT '裸金属异常';


CREATE TABLE bm_exception_ruler
(
    id               INT          NOT NULL AUTO_INCREMENT COMMENT 'ID',
    name             VARCHAR(255) NOT NULL COMMENT '规则名称',
    count_window     INT          NOT NULL DEFAULT 1 COMMENT '数量窗口',
    occurrence_count INT          NOT NULL DEFAULT 1 COMMENT '在数量窗口中出现的次数',
    handle_type      INT          NOT NULL COMMENT '异常处理方式 1禁用 2扣分',
    deduct_score     INT          NOT NULL DEFAULT 0 COMMENT '扣除分数（禁用扣除0分）',
    created_time     DATETIME COMMENT '创建时间',
    updated_time     DATETIME COMMENT '更新时间',
    PRIMARY KEY (id)
) COMMENT '裸金属异常规则';

CREATE TABLE bm_exception_ruler_rel
(
    id           INT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    exception_id INT NOT NULL COMMENT '异常id',               -- exception_id  ruler_id联合唯一索引
    ruler_id     INT NOT NULL COMMENT '规则id',
    created_time DATETIME COMMENT '创建时间',
    updated_time DATETIME COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE INDEX idx_exception_ruler (exception_id, ruler_id) -- 添加联合唯一索引
) COMMENT '裸金属异常与异常规则关系';


CREATE TABLE bm_exception_report_record
(
    id                     INT          NOT NULL AUTO_INCREMENT COMMENT '上报记录ID',
    exception_id           INT          NOT NULL COMMENT '异常id',
    exception_code         INT          NOT NULL COMMENT '异常code',
    bm_scheduled_record_id BIGINT       NOT NULL COMMENT '裸金属调度记录ID',
    region_id              INT          NOT NULL COMMENT 'region ID',
    bm_id                  INT          NOT NULL COMMENT '裸金属ID',
    bm_mac                 VARCHAR(255) NOT NULL COMMENT '裸金属MAC地址',
    report_time            DATETIME NOT NULL COMMENT '上报时间',
    created_time           DATETIME COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE INDEX idx_bm_scheduled_record_id_exception_code (exception_code, bm_scheduled_record_id), -- 添加联合唯一索引
    INDEX idx_bm_id (bm_id),                                                                         -- 添加索引
    INDEX idx_region_id (region_id),                                                                 -- 添加索引
    INDEX idx_exception_id (exception_id),                                                           -- 添加索引
    INDEX idx_bm_scheduled_record_id (bm_scheduled_record_id),                                       -- 添加索引
    INDEX idx_created_time (created_time)
) COMMENT '裸金属异常上报记录';

CREATE TABLE bm_exception_handle_record
(
    id                            INT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    bm_exception_report_record_id INT NOT NULL COMMENT '异常上报记录ID',
    handle_type                   INT NOT NULL COMMENT '异常处理结果(1禁用 2扣分)',
    deduct_score                  INT NOT NULL DEFAULT 0 COMMENT '扣除分数（禁用扣除0分）',
    created_time                  DATETIME COMMENT '创建时间',
    PRIMARY KEY (id),
    INDEX idx_bm_exception_report_record_id (bm_exception_report_record_id) -- 添加索引
) COMMENT '裸金属异常处理记录';

-- 初始化数据sql
INSERT INTO `computing_monitor`.`bm_exception` (`id`, `name`, `code`, `source`, `detail`, `created_time`, `updated_time`) VALUES (1, '串流程序卡死', 1, '主动探测', '收到desktop_start上报未知断链;电源状态为开机;未查询到蓝屏信息;算力主机135端口通;算力主机8800端口不通', now(), now());
INSERT INTO `computing_monitor`.`bm_exception` (`id`, `name`, `code`, `source`, `detail`, `created_time`, `updated_time`) VALUES (2, '系统卡死', 2, '主动探测', '收到desktop_start上报未知断链;电源状态为开机;未查询到蓝屏信息;算力主机135端口不通', now(), now());
INSERT INTO `computing_monitor`.`bm_exception` (`id`, `name`, `code`, `source`, `detail`, `created_time`, `updated_time`) VALUES (3, '上电失败', 3, '主动探测', '电源盒接口上电返回失败 或 返回成功后查询电源状态3次都失败', now(), now());
INSERT INTO `computing_monitor`.`bm_exception` (`id`, `name`, `code`, `source`, `detail`, `created_time`, `updated_time`) VALUES (4, 'cpu节能未关闭', 4, 'host-monitor上报', 'Host_Monitor开机检查', now(), now());
INSERT INTO `computing_monitor`.`bm_exception` (`id`, `name`, `code`, `source`, `detail`, `created_time`, `updated_time`) VALUES (5, '无独立显卡', 5, 'host-monitor上报', 'Host_Monitor轮询检查（wmi接口）', now(), now());
INSERT INTO `computing_monitor`.`bm_exception` (`id`, `name`, `code`, `source`, `detail`, `created_time`, `updated_time`) VALUES (6, '未安装显卡驱动', 6, 'host-monitor上报', 'Host_Monitor轮询检查（wmi接口）', now(), now());
INSERT INTO `computing_monitor`.`bm_exception` (`id`, `name`, `code`, `source`, `detail`, `created_time`, `updated_time`) VALUES (7, '显卡异常', 7, 'host-monitor上报', 'Host_Monitor轮询检查（wmi接口）', now(), now());
INSERT INTO `computing_monitor`.`bm_exception` (`id`, `name`, `code`, `source`, `detail`, `created_time`, `updated_time`) VALUES (8, 'GPU显存为0', 8, 'host-monitor上报', 'Host_Monitor轮询检查（wmi接口）', now(), now());
INSERT INTO `computing_monitor`.`bm_exception` (`id`, `name`, `code`, `source`, `detail`, `created_time`, `updated_time`) VALUES (9, '开机失败', 9, 'host-monitor上报', '未收到Host_Monitor上报开机成功事件', now(), now());
INSERT INTO `computing_monitor`.`bm_exception` (`id`, `name`, `code`, `source`, `detail`, `created_time`, `updated_time`) VALUES (10, '网络百兆', 10, 'host-monitor上报', 'Host_Monitor探测到网络百兆', now(), now());
INSERT INTO `computing_monitor`.`bm_exception` (`id`, `name`, `code`, `source`, `detail`, `created_time`, `updated_time`) VALUES (11, '主机异常断电', 11, '主动探测', 'desktop_start（瘦终端串流独立进程）上报未知断链,电源已关', now(), now());
INSERT INTO `computing_monitor`.`bm_exception` (`id`, `name`, `code`, `source`, `detail`, `created_time`, `updated_time`) VALUES (12, 'ip冲突断链', 12, 'desktop_start上报', 'desktop_start（瘦终端串流独立进程）上报IP冲突断链', now(), now());
INSERT INTO `computing_monitor`.`bm_exception` (`id`, `name`, `code`, `source`, `detail`, `created_time`, `updated_time`) VALUES (13, '蓝屏断链', 13, 'desktop_start上报', 'desktop_start（瘦终端串流独立进程）上报蓝屏断链', now(), now());


INSERT INTO `computing_monitor`.`bm_exception_ruler` (`id`, `name`, `count_window`, `occurrence_count`, `handle_type`, `deduct_score`, `created_time`, `updated_time`) VALUES (1, '上电失败', 2, 2, 1, 0, now(), now());
INSERT INTO `computing_monitor`.`bm_exception_ruler` (`id`, `name`, `count_window`, `occurrence_count`, `handle_type`, `deduct_score`, `created_time`, `updated_time`) VALUES (2, 'CPU节能未关闭', 1, 1, 1, 0, now(), now());
INSERT INTO `computing_monitor`.`bm_exception_ruler` (`id`, `name`, `count_window`, `occurrence_count`, `handle_type`, `deduct_score`, `created_time`, `updated_time`) VALUES (3, 'GPU:无独立显卡', 1, 1, 1, 0, now(), now());
INSERT INTO `computing_monitor`.`bm_exception_ruler` (`id`, `name`, `count_window`, `occurrence_count`, `handle_type`, `deduct_score`, `created_time`, `updated_time`) VALUES (4, '网络百兆', 1, 1, 1, 0, now(), now());
INSERT INTO `computing_monitor`.`bm_exception_ruler` (`id`, `name`, `count_window`, `occurrence_count`, `handle_type`, `deduct_score`, `created_time`, `updated_time`) VALUES (5, '系统卡死', 1, 1, 2, 5, now(), now());
INSERT INTO `computing_monitor`.`bm_exception_ruler` (`id`, `name`, `count_window`, `occurrence_count`, `handle_type`, `deduct_score`, `created_time`, `updated_time`) VALUES (6, 'GPU:未安装驱动', 1, 1, 2, 10, now(), now());
INSERT INTO `computing_monitor`.`bm_exception_ruler` (`id`, `name`, `count_window`, `occurrence_count`, `handle_type`, `deduct_score`, `created_time`, `updated_time`) VALUES (7, 'GPU:显卡异常', 1, 1, 2, 10, now(), now());
INSERT INTO `computing_monitor`.`bm_exception_ruler` (`id`, `name`, `count_window`, `occurrence_count`, `handle_type`, `deduct_score`, `created_time`, `updated_time`) VALUES (8, 'GPU:显存为0', 1, 1, 2, 10, now(), now());
INSERT INTO `computing_monitor`.`bm_exception_ruler` (`id`, `name`, `count_window`, `occurrence_count`, `handle_type`, `deduct_score`, `created_time`, `updated_time`) VALUES (9, '开机失败', 1, 1, 2, 10, now(), now());
INSERT INTO `computing_monitor`.`bm_exception_ruler` (`id`, `name`, `count_window`, `occurrence_count`, `handle_type`, `deduct_score`, `created_time`, `updated_time`) VALUES (10, '蓝屏断链', 1, 1, 2, 5, now(), now());


INSERT INTO `computing_monitor`.`bm_exception_ruler_rel` (`id`, `exception_id`, `ruler_id`, `created_time`, `updated_time`) VALUES (1, 10, 4, now(), now());
INSERT INTO `computing_monitor`.`bm_exception_ruler_rel` (`id`, `exception_id`, `ruler_id`, `created_time`, `updated_time`) VALUES (2, 4, 2, now(), now());
INSERT INTO `computing_monitor`.`bm_exception_ruler_rel` (`id`, `exception_id`, `ruler_id`, `created_time`, `updated_time`) VALUES (3, 2, 5, now(), now());
INSERT INTO `computing_monitor`.`bm_exception_ruler_rel` (`id`, `exception_id`, `ruler_id`, `created_time`, `updated_time`) VALUES (4, 9, 9, now(), now());
INSERT INTO `computing_monitor`.`bm_exception_ruler_rel` (`id`, `exception_id`, `ruler_id`, `created_time`, `updated_time`) VALUES (5, 5, 3, now(), now());
INSERT INTO `computing_monitor`.`bm_exception_ruler_rel` (`id`, `exception_id`, `ruler_id`, `created_time`, `updated_time`) VALUES (6, 13, 10, now(), now());
INSERT INTO `computing_monitor`.`bm_exception_ruler_rel` (`id`, `exception_id`, `ruler_id`, `created_time`, `updated_time`) VALUES (7, 6, 6, now(), now());
INSERT INTO `computing_monitor`.`bm_exception_ruler_rel` (`id`, `exception_id`, `ruler_id`, `created_time`, `updated_time`) VALUES (8, 7, 7, now(), now());
INSERT INTO `computing_monitor`.`bm_exception_ruler_rel` (`id`, `exception_id`, `ruler_id`, `created_time`, `updated_time`) VALUES (9, 8, 8, now(), now());
INSERT INTO `computing_monitor`.`bm_exception_ruler_rel` (`id`, `exception_id`, `ruler_id`, `created_time`, `updated_time`) VALUES (10, 3, 1, now(), now());