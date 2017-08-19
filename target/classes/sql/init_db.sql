DROP TABLE IF EXISTS word_card;
DROP TABLE IF EXISTS result;

CREATE TABLE word_card (
  id           INT NOT NULL AUTO_INCREMENT,
  pic_location VARCHAR(255),
  theme        VARCHAR(255),
  hun          VARCHAR(100),
  eng          VARCHAR(100),
  PRIMARY KEY (id)
)
  ENGINE = INNODB;

CREATE TABLE result (
  id         INT NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(255),
  last_name  VARCHAR(255),
  start_time VARCHAR(100),
  end_time   VARCHAR(100),
  result     INT,
  PRIMARY KEY (id)
)
  ENGINE = INNODB;




