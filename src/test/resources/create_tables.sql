drop table if exists user_condition;
drop table if exists daily_kcal;
drop table if exists daily_ate_food;
drop table if exists user_photo;
drop table if exists user_kcal;

create table daily_ate_food
(
    id               bigint        not null unique auto_increment,
    user_id          bigint        not null,
    product_name     varchar(100)  not null,
    product_weight   decimal(5, 1) not null,
    product_kcals    int           not null,
    product_proteins int           not null,
    product_fats     int           not null,
    product_carbs    int           not null,

    primary key (id)
);

create table daily_kcal
(
    id      bigint   not null unique auto_increment,
    protein smallint not null,
    fat     smallint not null,
    carb    smallint not null,

    primary key (id)
);

create table user_condition
(
    id            bigint        not null unique auto_increment,
    user_id       bigint        not null unique,
    daily_kcal_id bigint        not null unique,
    gender        varchar(10)   not null,
    age           smallint      not null,
    height        smallint      not null,
    weight        smallint      not null,
    intensity     varchar(30)   not null,
    goal          varchar(12)   not null,
    fat_percent   decimal(3, 1) not null,

    primary key (id),
    foreign key (daily_kcal_id)
        references daily_kcal (id)
        on update cascade
        on delete cascade
);

create table user_photo
(
    id         bigint  not null unique auto_increment,
    user_id    bigint  not null,
    photo_path varchar not null unique,
    photo_date date    not null,

    primary key (id)
);

create table user_kcal
(
    id          bigint not null unique auto_increment,
    proteins    int    not null,
    fats        int    not null,
    carbs       int    not null,
    user_id     bigint not null,
    in_priority bool   not null,
    kcals       int    not null,
    only_kcals  bool   not null,

    primary key (id)
);
