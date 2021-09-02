create table mileage_log
(
    mileage_id       binary       not null,
    user_id          binary       not null,
    review_id        binary       not null,
    origin_review_id binary       not null,
    amount           int          not null,
    reason           varchar(255) not null,
    created_date     datetime     not null,
    primary key (mileage_id)
);