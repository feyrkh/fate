CREATE TABLE Users (
  id          UUID UNIQUE,
  loginName   VARCHAR UNIQUE,
  displayName VARCHAR
);