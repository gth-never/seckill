create database seckill;
use seckill;
create table seckill
(
   `seckill_id` bigint not null auto_increment comment '商品库存id',
   `name` VARCHAR(120) not null comment '商品名称',
   `number` int not null comment '库存数量',
   `create_time` TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP comment '创建时间',
   `start_time` TIMESTAMP not null comment '秒杀开始时间',
   `end_time` TIMESTAMP not null comment '秒杀结束时间',
   PRIMARY KEY (seckill_id),
   KEY idx_start_time(start_time),
   KEY idx_end_time(end_time),
   KEY idx_create_time(create_time)
)engine=innodb auto_increment=1000 default charset=utf8 comment='秒杀库存表'

INSERT INTO seckill(name,number,start_time,end_time)
VALUES
('1000元秒杀iPhone6',100,'2015-11-1 00:00:00','2015-11-2 00:00:00'),
('500元秒杀ipad',200,'2015-11-1 00:00:00','2015-11-2 00:00:00'),
('300元秒杀小米',300,'2015-11-1 00:00:00','2015-11-2 00:00:00'),
('200元秒杀红米',400,'2015-11-1 00:00:00','2015-11-2 00:00:00');

CREATE TABLE success_killed
(
    `seckill_id` bigint not null comment '秒杀商品id',
    `user_phone` bigint not null comment '用户手机号',
    `state` tinyint not null DEFAULT -1 comment '状态表示:-1 失败 0 成功 1 已付款',
    `create_time` TIMESTAMP not null comment '创建时间',
    PRIMARY KEY (seckill_id,user_phone),
    KEY idx_create_time(create_time)
)engine=innodb default charset=utf8 comment='秒杀成功明细表';