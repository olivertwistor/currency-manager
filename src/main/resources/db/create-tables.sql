create table if not exists currency
(
    id      integer primary key,
    name    text    not null
);

create table if not exists exchange_rate
(
    date            integer not null,
    base_currency   integer not null,
    target_currency integer not null,
    exchange_rate   real not null,

    primary key (date, base_currency, target_currency)
);

create table if not exists trade
(
    id          integer primary key,
    date        integer not null,
    currency    integer not null ,
    amount      real    not null ,
    price       real    not null,
    fee         real    default 0.0,

    foreign key (currency) references currency(id)
        on delete cascade on update cascade
);
