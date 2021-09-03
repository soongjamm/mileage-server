alter table attached_photo add constraint fk_review_id foreign key (review_id) references review (review_id) on delete cascade;
create index idx_review on mileage_log (review_id);
create index idx_user_amount on mileage_log (user_id, point);
