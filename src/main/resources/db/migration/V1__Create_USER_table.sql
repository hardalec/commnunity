create table USER
(
    ID           INTEGER auto_increment
        primary key,
    ACCOUNT_ID   VARCHAR(255),
    GMT_CREATE   BIGINT not null,
    GMT_MODIFIED BIGINT not null,
    NAME         VARCHAR(255),
    TOKEN        VARCHAR(36)
);

