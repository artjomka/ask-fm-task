CREATE TABLE QUESTION (
  id           VARCHAR(36)  NOT NULL,
  created      TIMESTAMP    NOT NULL,
  text         VARCHAR(256) NOT NULL,
  country_code VARCHAR(3)   NOT NULL
);

CREATE TABLE COUNTRY (
  id               VARCHAR(36)  NOT NULL,
  name             VARCHAR(256) NOT NULL,
  code             VARCHAR(3)   NOT NULL,
  limit_per_second NUMERIC(3)   NOT NULL

);

ALTER TABLE QUESTION
  ADD CONSTRAINT QUESTION_PK PRIMARY KEY (id);

ALTER TABLE COUNTRY
  ADD CONSTRAINT COUNTRY_PK PRIMARY KEY (id);