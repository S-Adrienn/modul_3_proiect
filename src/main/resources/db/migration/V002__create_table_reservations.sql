create table reservations (
    id serial primary key,
    date_of_check_in date not null,
    date_of_check_out date not null,
    guest_name varchar (64) not null,
    phone_number varchar (20) not null,
    total_price decimal not null,
    is_deleted boolean not null,
    room_id int not null
);