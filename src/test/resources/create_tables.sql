drop table if exists user_condition;
drop table if exists daily_kcals;
drop table if exists daily_ate_food;

create table daily_ate_food
(
    id               bigint        not null unique auto_increment,
    user_id          bigint        not null,
    product_name     varchar(100)  not null,
    product_weight   decimal(4, 1) not null,
    product_kcals    decimal(4, 1) not null,
    product_proteins decimal(4, 1) not null,
    product_fats     decimal(4, 1) not null,
    product_carbs    decimal(4, 1) not null,

    primary key (id)
);

create table daily_kcals
(
    id       bigint   not null unique auto_increment,
    proteins smallint not null,
    fats     smallint not null,
    carbs    smallint not null,

    primary key (id)
);

create table user_condition
(
    id            bigint        not null unique auto_increment,
    user_id       bigint        not null unique,
    kcals_info_id bigint        not null unique,
    gender        varchar(10)   not null,
    age           smallint      not null,
    height        smallint      not null,
    weight        smallint      not null,
    intensity     varchar(30)   not null,
    goal          varchar(12)   not null,
    fat_percent   decimal(3, 1) not null,

    primary key (id),
    foreign key (kcals_info_id)
        references daily_kcals (id)
        on update cascade
        on delete cascade
);
