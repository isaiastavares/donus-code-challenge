create table account
(
    id         uuid not null
        constraint account_pkey
            primary key,
    balance    numeric(19, 2),
    created_at timestamp,
    document   varchar(11),
    name       varchar(60),
    updated_at timestamp
);

create table transaction
(
    id            uuid not null
        constraint transaction_pkey
            primary key,
    account_id    uuid,
    account_id_to uuid,
    amount        numeric(19, 2),
    balance_from  numeric(19, 2),
    balance_to    numeric(19, 2),
    created_at    timestamp,
    type          varchar(15)
);

create unique index account_document_uindex
    on account (document);