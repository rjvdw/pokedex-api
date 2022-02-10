create table type
(
    id   bigserial   not null primary key,
    name varchar(32) not null unique
);

create table pokemon
(
    id             bigserial   not null primary key,
    index          int         not null unique,
    name           varchar(64) not null unique,
    description    text        not null,
    primary_type   bigint      not null,
    secondary_type bigint      null,

    foreign key (primary_type) references type (id),
    foreign key (secondary_type) references type (id)
);
