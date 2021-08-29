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
    review_id binary not null,
    content   varchar(255),
    place_id  binary,
    user_id   binary,
    primary key (review_id)
);

create table attached_photo
(
    review_id          binary not null,
    attached_photo_ids binary
);

alter table attached_photo
    add constraint FK_ foreign key (review_id) references review
