DROP TABLE IF EXISTS word_card;
DROP TABLE IF EXISTS result;
DROP TABLE IF EXISTS wuser;

CREATE TABLE wuser (
  id   INT NOT NULL,
  role CHAR,
  PRIMARY KEY (id)
)
  ENGINE = INNODB;

CREATE TABLE word_card (
  id       INT,
  wuser_id INT,
  INDEX par_ind (wuser_id),
  FOREIGN KEY (wuser_id)
  REFERENCES wuser (id)
    ON DELETE CASCADE
)
  ENGINE = INNODB;

CREATE TABLE result (
  id       INT,
  result   INT,
  wuser_id INT,
  INDEX par_ind (wuser_id),
  FOREIGN KEY (wuser_id)
  REFERENCES wuser (id)
    ON DELETE CASCADE
)
  ENGINE = INNODB;