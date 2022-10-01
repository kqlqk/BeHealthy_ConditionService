drop table if exists user_condition;
drop table if exists kcals_info;
drop table if exists daily_food;

create table daily_food
(
    id               bigint       not null unique auto_increment,
    user_id          bigint       not null,
    product_name     varchar(100) not null,
    product_weight   smallint     not null,
    product_kcals    smallint     not null,
    product_proteins smallint     not null,
    product_fats     smallint     not null,
    product_carbs    smallint     not null,

    primary key (id)
);

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
    user_id       bigint      not null unique,
    kcals_info_id bigint      not null unique,
    gender        varchar(10) not null,
    age           smallint    not null,
    height        smallint    not null,
    weight        smallint    not null,
    intensity     varchar(30) not null,
    goal          varchar(12) not null,

    primary key (id),
    foreign key (kcals_info_id)
        references kcals_info (id)
        on update cascade
        on delete cascade
);
