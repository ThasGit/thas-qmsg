SET NAMES utf8;

CREATE TABLE IF NOT EXISTS `qmsg_token`
(
    `id`           BIGINT(11) PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
    `gmt_create`   DATETIME DEFAULT NOW() NOT NULL COMMENT '创建时间',
    `gmt_modified` DATETIME DEFAULT NOW() NOT NULL COMMENT '更新时间',
    `token`        VARCHAR(255)           NOT NULL COMMENT 'token',
    `expire`       DATETIME               NOT NULL COMMENT '过期时间',
    `issuer`       VARCHAR(20)            NOT NULL COMMENT '签发人',
    `audience`     VARCHAR(20)            NOT NULL COMMENT '受众',
    `status`       TINYINT                NOT NULL DEFAULT 0 COMMENT 'token状态 0有效1无效2加白3加黑',
    INDEX `idx_token_status` (`token`, `status`) COMMENT '查询token的状态',
    INDEX `idx_expire_token` (`expire`, `token`) COMMENT '统计所有过期的token',
    INDEX `idx_audience_status` (`audience`, `status`) COMMENT '统计用户的token',
    INDEX `idx_issuer_token` (`issuer`, `token`) COMMENT '统计签发者的token'
);

