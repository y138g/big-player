/******************************************/
/*   DatabaseName = nfturbo   */
/*   TableName = users   */
/******************************************/
CREATE TABLE `user`
(
    `id`                   bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID（自增主键）',
    `gmt_create`           datetime NOT NULL COMMENT '创建时间',
    `gmt_modified`         datetime NOT NULL COMMENT '最后更新时间',
    `nick_name`            varchar(255) DEFAULT NULL COMMENT '用户昵称',
    `password_hash`        varchar(255) DEFAULT NULL COMMENT '密码哈希',
    `state`                varchar(64)  DEFAULT NULL COMMENT '用户状态（ACTIVE，FROZEN）',
    `invite_code`          varchar(255) DEFAULT NULL COMMENT '邀请码',
    `telephone`            varchar(20)  DEFAULT NULL COMMENT '手机号码',
    `inviter_id`           varchar(255) DEFAULT NULL COMMENT '邀请人用户ID',
    `last_login_time`      datetime     DEFAULT NULL COMMENT '最后登录时间',
    `profile_photo_url`    varchar(255) DEFAULT NULL COMMENT '用户头像URL',
    `user_role`            varchar(128) DEFAULT NULL COMMENT '用户角色',
    `deleted`              int          DEFAULT NULL COMMENT '是否逻辑删除，0为未删除，非0为已删除',
    `lock_version`         int          DEFAULT NULL COMMENT '乐观锁版本号',
    PRIMARY KEY (`id`)
);


/******************************************/
/*   DatabaseName = nfturbo   */
/*   TableName = user_operate_stream   */
/******************************************/
CREATE TABLE `user_operate_stream`
(
    `id`           bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID（自增主键）',
    `gmt_create`   datetime    DEFAULT NULL COMMENT '创建时间',
    `gmt_modified` datetime    DEFAULT NULL COMMENT '最后更新时间',
    `user_id`      varchar(64) DEFAULT NULL COMMENT '用户ID',
    `type`         varchar(64) DEFAULT NULL COMMENT '操作类型',
    `operate_time` datetime    DEFAULT NULL COMMENT '操作时间',
    `param`        text COMMENT '操作参数',
    `extend_info`  text COMMENT '扩展字段',
    `deleted`      int         DEFAULT NULL COMMENT '是否逻辑删除，0为未删除，非0为已删除',
    `lock_version` int         DEFAULT NULL COMMENT '乐观锁版本号',
    PRIMARY KEY (`id`)
);
