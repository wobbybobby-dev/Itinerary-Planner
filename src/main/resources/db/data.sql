INSERT INTO users (username, email, password, full_name)
VALUES ('anoushka', 'anoushkaroy142g@mail.com', 'password123', 'Anoushka Roy');

INSERT INTO trips (
    user_id,
    trip_name,
    destination,
    start_date,
    end_date,
    description,
    status
)
VALUES (
    1,
    'Goa Vacation',
    'Goa',
    '2026-06-10',
    '2026-06-15',
    'Summer trip',
    'PLANNED'
);