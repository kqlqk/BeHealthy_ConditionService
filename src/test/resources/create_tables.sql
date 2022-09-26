drop table if exists user_condition;
drop table if exists kcals_info;

create table kcals_info
(
    id       bigint   not null unique auto_increment,
    proteins smallint not null,
    fats     smallint not null,
    carbs    smallint not null,

    primary key (id)
);

create table user_condition
(
    id            bigint      not null unique auto_increment,
    kcals_info_id bigint      not null unique,
    weight        smallint    not null,
    intensity     varchar(30) not null,
    user_id       bigint      not null unique,

    primary key (id),
    foreign key (kcals_info_id)
        references kcals_info (id)
        on update cascade
        on delete cascade
);
