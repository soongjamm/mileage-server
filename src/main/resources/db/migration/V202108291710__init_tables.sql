create table place
(
    place_id binary  not null,
    reviewed boolean not null default false,
    version  integer not null,
    primary key (place_id)
);

create table user
(
    user_id binary not null,
    primary key (user_id)
);

create table review
(
    review_id        binary not null,
    content          varchar(255),
    place_id         binary not null,
    user_id          binary not null,
    created_date     datetime not null,
    origin_review_id binary not null,
    primary key (review_id)
);

create table attached_photo
(
    review_id          binary not null,
    attached_photo_ids binary
);

create table review_outbox
(
    outbox_id binary       not null,
    review_id binary       not null,
    action    varchar(30)  not null,
    payload   varchar(255) not null,
    primary key (outbox_id)
);

alter table attached_photo
    add constraint FK_attached_photo foreign key (review_id) references review on delete cascade;
