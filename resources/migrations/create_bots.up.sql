create table bots (
  workspace_id integer primary key,
  user_id varchar(256) not null,
  access_token varchar(1024) not null,
  foreign key (workspace_id) references workspaces(id)
);
