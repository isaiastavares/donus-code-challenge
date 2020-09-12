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

create unique index account_document_uindex
    on account (document);