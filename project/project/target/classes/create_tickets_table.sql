-- Create tickets table with foreign key to users
CREATE TABLE IF NOT EXISTS tickets (
    ticket_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    from_station VARCHAR(100) NOT NULL,
    to_station VARCHAR(100) NOT NULL,
    journey_date DATE NOT NULL,
    ticket_type VARCHAR(50) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    train_number VARCHAR(20) NOT NULL,
    departure_time VARCHAR(10) NOT NULL,
    train_type VARCHAR(50) NOT NULL,
    number_of_tickets INT NOT NULL,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create index on user_id for faster lookups
CREATE INDEX idx_tickets_user_id ON tickets(user_id); 