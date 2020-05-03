create table products
(
    id         int auto_increment
        primary key,
    name       varchar(45) not null,
    type       varchar(45) not null,
    capacity   varchar(5)  not null,
    color      varchar(15) not null,
    price      double      not null,
    currency   char(5)     not null,
    date_added date        not null,
    image_name varchar(70) not null,
    constraint name_UNIQUE
        unique (name)
);

create table roles
(
    id   int auto_increment
        primary key,
    name varchar(20) not null
);

create table statuses
(
    id   int auto_increment
        primary key,
    name varchar(45) not null
);

create table users
(
    id         int auto_increment
        primary key,
    login      varchar(45)          not null,
    password   varchar(45)          not null,
    first_name varchar(45)          not null,
    last_name  varchar(45)          not null,
    role_id    int                  not null,
    is_blocked tinyint(1) default 0 not null,
    constraint login_UNIQUE
        unique (login),
    constraint role_id
        foreign key (role_id) references roles (id)
            on update cascade on delete cascade
);

create table cart
(
    id         int auto_increment
        primary key,
    user_id    int           not null,
    product_id int           not null,
    quantity   int default 1 not null,
    constraint id_product
        foreign key (product_id) references products (id)
            on update cascade on delete cascade,
    constraint id_user
        foreign key (user_id) references users (id)
            on update cascade on delete cascade
);

create index product_id_idx
    on cart (product_id);

create index user_id_idx
    on cart (user_id);

create table orders
(
    id           int auto_increment
        primary key,
    bill         double not null,
    user_id      int    not null,
    status_id    int    not null,
    date_created date   not null,
    constraint status_id
        foreign key (status_id) references statuses (id)
            on update cascade on delete cascade,
    constraint users_id
        foreign key (user_id) references users (id)
            on update cascade on delete cascade
);

create index status_id_idx
    on orders (status_id);

create index user_id_idx
    on orders (user_id);

create table orders_products
(
    order_id   int not null,
    product_id int not null,
    quantity   int not null,
    constraint orders_id
        foreign key (order_id) references orders (id)
            on update cascade on delete cascade,
    constraint product_id
        foreign key (product_id) references products (id)
            on update cascade on delete cascade
);

create index order_id_idx
    on orders_products (order_id);

create index products_id_idx
    on orders_products (product_id);

create index role_id_idx
    on users (role_id);
