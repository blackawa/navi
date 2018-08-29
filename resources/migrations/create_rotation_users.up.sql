create table rotation_users (
  id serial primary key,
  rotation_id integer not null,
  slack_user_id varchar(256) not null,
  foreign key (rotation_id) references rotations(id)
);
