CREATE TABLE "library"."user"
(
  ID               UUID      DEFAULT uuid_in((md5((random())::TEXT))::CSTRING),
  CREATE_TIMESTAMP TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  UPDATE_TIMESTAMP TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  NAME       VARCHAR(255)                         NOT NULL,
  FIRST_NAME       VARCHAR(255),
  MEMBER_SINCE TIMESTAMP,
  MEMBER_TILL TIMESTAMP,
  GENDER VARCHAR(255),
  PRIMARY KEY ( ID ),
  CONSTRAINT UN_NAME_FIRST_NAME UNIQUE ( NAME, FIRST_NAME )
);