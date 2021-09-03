create table if not exists place
(
    place_id binary(36)  not null,
    reviewed boolean not null default false,
    version  integer not null,
    primary key (place_id)
);

create table if not exists user
(
    user_id binary(36) not null,
    primary key (user_id)
);

create table if not exists review
(
    review_id        binary(36) not null,
    content          varchar(255),
    place_id         binary(36) not null,
    user_id          binary(36) not null,
    created_date     datetime not null,
    review_status varchar(20) not null,
    origin_review_id binary(36) not null,
    primary key (review_id)
    );

create table if not exists attached_photo
(
    review_id          binary(36) not null,
    attached_photo_ids binary(36)
);

create table if not exists review_outbox
(
    outbox_id binary(36)       not null,
    review_id binary(36)       not null,
    action    varchar(30)  not null,
    payload   varchar(255) not null,
    primary key (outbox_id)
);

create table if not exists mileage_log
(
    mileage_id       binary(36)       not null,
    user_id          binary(36)       not null,
    review_id        binary(36)       not null,
    origin_review_id binary(36)       not null,
    point           int          not null,
    reason           varchar(255) not null,
    created_date     datetime     not null,
    primary key (mileage_id)
);
