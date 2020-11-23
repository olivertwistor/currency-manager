create table if not exists currency
(
    id      integer primary key,
    name    text    not null
);

create table if not exists exchange_rate
(
    date            text    not null,
    base_currency   integer not null,
    target_currency integer not null,
    rate            real    not null,

    primary key (date, base_currency, target_currency),

    foreign key (base_currency) references currency(id)
        on delete cascade on update cascade,
    foreign key (target_currency) references currency(id)
        on delete cascade on update cascade
);

create table if not exists trade
(
    id          integer primary key,
    date        text    not null,
    currency    integer not null ,
    amount      real    not null ,
    price       real    not null,
    fee         real    default 0.0,

    foreign key (currency) references currency(id)
        on delete cascade on update cascade
);
