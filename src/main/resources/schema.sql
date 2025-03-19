create table if not exists transactions
(
    id          uuid  default random_uuid() primary key,
    amount      int,
    time_stamp  timestamp,
    reference   varchar(255)
);