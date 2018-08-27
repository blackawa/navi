create table workspaces (
  team_id varchar(256) primary key,
  app_user_id varchar(256) not null,
  access_token varchar(1024) not null
);
