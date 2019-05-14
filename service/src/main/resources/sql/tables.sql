CREATE TABLE `sys_log` (
  `id`          bigint(40)   NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `ip`          varchar(20)  NOT NULL DEFAULT ''
  COMMENT '操作地址的IP',
  `create_by`   datetime     NOT NULL
  COMMENT '操作时间',
  `remark`      varchar(255) NOT NULL DEFAULT ''
  COMMENT '操作内容',
  `operate_url` varchar(50)  NOT NULL DEFAULT ''
  COMMENT '操作的访问地址',
  `operate_by`  varchar(20)           DEFAULT ''
  COMMENT '操作的浏览器',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `sys_view` (
  `id`        bigint(40)  NOT NULL AUTO_INCREMENT,
  `ip`        varchar(20) NOT NULL
  COMMENT '访问IP',
  `create_by` datetime    NOT NULL
  COMMENT '访问时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `tbl_message` (
  `id`           bigint(40)   NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `content`      varchar(200) NOT NULL DEFAULT ''
  COMMENT '留言/评论内容',
  `create_by`    datetime     NOT NULL
  COMMENT '创建日期',
  `email`        varchar(20)  NOT NULL DEFAULT ''
  COMMENT '邮箱，用于回复消息',
  `name`         varchar(20)  NOT NULL DEFAULT ''
  COMMENT '用户自己定义的名称',
  `ip`           varchar(20)  NOT NULL DEFAULT ''
  COMMENT '留言/评论IP',
  `is_effective` tinyint(1)   NOT NULL DEFAULT '1'
  COMMENT '是否有效，默认为1为有效，0为无效',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '因为message分为两种，一种是留言，一种是评论，这里搞成一张表是因为它们几乎是拥有相同的字段，我觉得没必要分成两张表来进行维护';

CREATE TABLE `tbl_sort_info` (
  `id`           bigint(40)  NOT NULL AUTO_INCREMENT,
  `name`         varchar(20) NOT NULL
  COMMENT '分类名称',
  `number`       tinyint(10) NOT NULL DEFAULT '0'
  COMMENT '该分类下的文章数量',
  `create_by`    datetime    NOT NULL
  COMMENT '分类创建时间',
  `modified_by`  datetime    NOT NULL
  COMMENT '分类修改时间',
  `is_effective` tinyint(1)  NOT NULL DEFAULT '1'
  COMMENT '是否有效，默认为1有效，为0无效',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `tbl_article_info` (
  `id`          bigint(40)   NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  `title`       varchar(50)  NOT NULL DEFAULT ''
  COMMENT '文章标题',
  `summary`     varchar(300) NOT NULL DEFAULT ''
  COMMENT '文章简介，默认100个汉字以内',
  `is_top`      tinyint(1)   NOT NULL DEFAULT '0'
  COMMENT '文章是否置顶，0为否，1为是',
  `traffic`     int(10)      NOT NULL DEFAULT '0'
  COMMENT '文章访问量',
  `create_by`   datetime     NOT NULL
  COMMENT '创建时间',
  `modified_by` datetime     NOT NULL
  COMMENT '修改日期',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `tbl_article_content` (
  `id`           bigint(40) NOT NULL AUTO_INCREMENT,
  `content`      text       NOT NULL,
  `article_id`   bigint(40) NOT NULL
  COMMENT '对应文章ID',
  `create_by`    datetime   NOT NULL
  COMMENT '创建时间',
  `modifield_by` datetime   NOT NULL
  COMMENT '更新时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `tbl_article_message` (
  `id`           bigint(40) NOT NULL AUTO_INCREMENT,
  `article_id`   bigint(40) NOT NULL
  COMMENT '文章ID',
  `message_id`   bigint(40) NOT NULL
  COMMENT '对应的留言ID',
  `create_by`    datetime   NOT NULL
  COMMENT '创建时间',
  `is_effective` tinyint(1) NOT NULL DEFAULT '1'
  COMMENT '是否有效，默认为1有效，置0无效',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `tbl_article_sort` (
  `id`           bigint(40) NOT NULL AUTO_INCREMENT,
  `sort_id`      bigint(40) NOT NULL
  COMMENT '分类id',
  `article_id`   bigint(40) NOT NULL
  COMMENT '文章id',
  `create_by`    datetime   NOT NULL
  COMMENT '创建时间',
  `modified_by`  datetime   NOT NULL
  COMMENT '更新时间',
  `is_effective` tinyint(1)          DEFAULT '1'
  COMMENT '表示当前数据是否有效，默认为1有效，0则无效',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `tbl_article_picture` (
  `id`          bigint(40)   NOT NULL AUTO_INCREMENT,
  `article_id`  bigint(40)   NOT NULL
  COMMENT '对应文章id',
  `picture_url` varchar(100) NOT NULL DEFAULT ''
  COMMENT '图片url',
  `create_by`   datetime     NOT NULL
  COMMENT '创建时间',
  `modified_by` datetime     NOT NULL
  COMMENT '更新时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '这张表用来保存题图url，每一篇文章都应该有题图';

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