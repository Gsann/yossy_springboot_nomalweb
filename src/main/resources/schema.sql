DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS entry;
CREATE TABLE user (
  id            BIGINT AUTO_INCREMENT           COMMENT 'ユーザーID',
  name          VARCHAR(60) NOT NULL            COMMENT 'ユーザー名',
  email         VARCHAR(120) NOT NULL           COMMENT 'メールアドレス',
  password      VARCHAR(120) NOT NULL           COMMENT 'パスワード',
  roles         VARCHAR(120)                    COMMENT 'ロール',
  enable_flag   TINYINT(1) NOT NULL DEFAULT 1   COMMENT '1:有効 0:無効',
  PRIMARY KEY (id),
  UNIQUE KEY (email)
);
CREATE TABLE entry (
  entry_id      BIGINT AUTO_INCREMENT           COMMENT 'エントリーID',
  name          VARCHAR(60) NOT NULL            COMMENT '投稿名',
  user_name     VARCHAR(120) NOT NULL           COMMENT 'ユーザー名',
  roles         VARCHAR(120)                    COMMENT 'ロール',
  enable_flag   TINYINT(1) NOT NULL DEFAULT 1   COMMENT '1:有効 0:無効',
  create_dt     DATETIME NOT NULL               COMMENT '作成日時',
  update_dt     DATETIME NOT NULL               COMMENT '更新日時',
  PRIMARY KEY (entry_id)
);
CREATE TABLE comment (
  comment_id    BIGINT AUTO_INCREMENT           COMMENT 'コメントID',
  entry_id      BIGINT                          COMMENT 'エントリーID',
  comment       VARCHAR(140) NOT NULL           COMMENT 'コメント',
  user_name     VARCHAR(120) NOT NULL           COMMENT 'ユーザー名',
  roles         VARCHAR(120)                    COMMENT 'ロール',
  enable_flag   TINYINT(1) NOT NULL DEFAULT 1   COMMENT '1:有効 0:無効',
  create_dt     DATETIME NOT NULL               COMMENT '作成日時',
  update_dt     DATETIME NOT NULL               COMMENT '更新日時',
  PRIMARY KEY (comment_id),
  CONSTRAINT FK__comment__entry_id FOREIGN KEY (entry_id) REFERENCES entry (entry_id)
);