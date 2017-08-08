DROP TABLE IF EXISTS word_card;
DROP TABLE IF EXISTS result;
DROP TABLE IF EXISTS wuser;

CREATE TABLE wuser (
  id   INT  NOT NULL AUTO_INCREMENT,
  role CHAR,
  PRIMARY KEY (id)
)
  ENGINE = INNODB;

CREATE TABLE word_card (
  id       INT  NOT NULL AUTO_INCREMENT,
  pic_location VARCHAR(50),
  theme VARCHAR(50),
  hung VARCHAR(100),
  engl VARCHAR(100),
  PRIMARY KEY (id)

)
  ENGINE = INNODB;

CREATE TABLE result (
  id       INT  NOT NULL AUTO_INCREMENT,
  result   INT,
  wuser_id INT,
  INDEX par_ind (wuser_id),
  FOREIGN KEY (wuser_id)
  REFERENCES wuser (id)
    ON DELETE CASCADE,
  PRIMARY KEY (id)
)
  ENGINE = INNODB;