

CREATE TABLE IF NOT EXISTS `news_sender` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `news_id` int(11) NOT NULL,
  `is_send` int(11) NOT NULL DEFAULT '0' COMMENT '(1 yuborilgan/ 0 yuborilmagan)',
  `send_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

ALTER TABLE `news_sender`
 ADD PRIMARY KEY (`id`);
 
ALTER TABLE `news_sender`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=0;