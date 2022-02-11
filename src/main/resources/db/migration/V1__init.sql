create table type
(
    id   bigserial   not null primary key,
    name varchar(32) not null unique
);

create table effectiveness
(
    id      bigserial   not null primary key,
    type    bigint      not null,
    against bigint      not null,
    effect  varchar(32) not null check (
            effect in ('super effective',
                       'not very effective',
                       'no effect')),

    unique (type, against),
    foreign key (type) references type (id) on delete restrict,
    foreign key (against) references type (id) on delete restrict
);

create table pokemon
(
    id             bigserial   not null primary key,
    index          int         not null unique,
    name           varchar(64) not null unique,
    description    text        not null,
    primary_type   bigint      not null,
    secondary_type bigint      null,

    foreign key (primary_type) references type (id) on delete restrict,
    foreign key (secondary_type) references type (id) on delete restrict
);
