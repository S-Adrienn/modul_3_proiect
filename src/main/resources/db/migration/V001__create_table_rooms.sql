create table rooms (
    id serial primary key,
    number_of_beds int not null,
    price_per_night decimal not null,
    is_visible boolean not null
);