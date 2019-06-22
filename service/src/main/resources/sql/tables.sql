# --------------------------------------------------------------------------------------------------------------

CREATE TABLE `v_visit_log` (
  `id`          bigint(64)   NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `ip`          varchar(20)  NOT NULL DEFAULT ''
  COMMENT '操作地址的IP',
  `time`        datetime     NOT NULL
  COMMENT '操作时间',
  `remark`      varchar(255) NOT NULL DEFAULT ''
  COMMENT '操作内容',
  `operate_url` varchar(50)  NOT NULL DEFAULT ''
  COMMENT '操作的访问地址',
  `browser`     varchar(20)           DEFAULT ''
  COMMENT '操作的浏览器',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '访问记录表';

CREATE TABLE `v_account` (
  `id`                  bigint(64)   NOT NULL AUTO_INCREMENT,
  `account_id`          bigint(64)   NOT NULL
  COMMENT '用户Id',
  `account_name`        varchar(20)  NOT NULL
  COMMENT '用户Name',
  `account_password`        varchar(20)  NOT NULL
  COMMENT '用户Password',
  `account_phone`       varchar(11)  NOT NULL
  COMMENT '用户PhoneNumber',
  `account_email`       varchar(20)  NOT NULL
  COMMENT '用户Email',
  `account_sex`         int(2)   NOT NULL DEFAULT '0'
  COMMENT '用户性别 0表示女',
  `account_create_time` datetime     NOT NULL
  COMMENT '用户注册时间',
  `account_login_time`  datetime     NOT NULL
  COMMENT '用户最后登陆时间',
  `acount_icon`         varchar(100) NOT NULL
  COMMENT '用户头像url',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '用户表';

CREATE TABLE `v_notes` (
  `id`                 bigint(64)  NOT NULL AUTO_INCREMENT,
  `note_id`            bigint(64)  NOT NULL
  COMMENT '文章Id',
  `note_author_id`     bigint(64)  NOT NULL
  COMMENT '文章作者Id == 用户Id',
  `note_title`         varchar(20) NOT NULL
  COMMENT '文章Title',
  `note_content`       text        NOT NULL
  COMMENT '文章Content',
  `note_author_name`   varchar(20) NOT NULL
  COMMENT '文章作者Name == 用户Name',
  `note_describe`      varchar(20) NOT NULL
  COMMENT '文章描述',
  `note_create_time`   datetime    NOT NULL
  COMMENT '文章创建时间',
  `note_modified_time` datetime    NOT NULL
  COMMENT '文章最后修改时间',
  `note_words`         int(32)     NOT NULL
  COMMENT '文章字数',
  `note_reads`         int(32)     NOT NULL
  COMMENT '文章阅读数',
  `note_share`         int(32)     NOT NULL
  COMMENT '文章分享数',
  `note_likes`         int(32)     NOT NULL
  COMMENT '文章喜欢数',
  `note_permission`    tinyint(1)  NOT NULL DEFAULT '0'
  COMMENT '文章权限 0表示public',
  `note_type`          tinyint(2)  NOT NULL DEFAULT '0'
  COMMENT '文章类型 0表示个人文章 1表示技术文档 2表示普通文档',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '文章表';

CREATE TABLE `v_notes_account` (
  `id`             bigint(64) NOT NULL AUTO_INCREMENT,
  `note_id`        bigint(64) NOT NULL
  COMMENT '文章Id',
  `note_author_id` bigint(64) NOT NULL
  COMMENT '用户Id',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '文章对应用户';