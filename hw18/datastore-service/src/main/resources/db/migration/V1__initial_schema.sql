create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);

create table address
(
    id        bigserial   not null primary key,
    client_id bigint      not null,
    street    varchar(50) not null
);