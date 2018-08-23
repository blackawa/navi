create table workspaces (
  id serial primary key,
  team_id varchar(256) not null,
  access_token varchar(1024) not null
);
