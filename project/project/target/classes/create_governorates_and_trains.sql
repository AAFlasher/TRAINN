-- Drop existing tables if they exist
DROP TABLE IF EXISTS trains;
DROP TABLE IF EXISTS governorates;

-- Create governorates table
CREATE TABLE governorates (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- Insert all 27 governorates
INSERT INTO governorates (name) VALUES
('Cairo'),
('Alexandria'),
('Giza'),
('Sharkia'),
('Dakahlia'),
('Beheira'),
('Gharbia'),
('Kafr El Sheikh'),
('Menoufia'),
('Ismailia'),
('Port Said'),
('Suez'),
('Damietta'),
('North Sinai'),
('South Sinai'),
('Beni Suef'),
('Fayoum'),
('Minya'),
('Assiut'),
('Sohag'),
('Qena'),
('Luxor'),
('Aswan'),
('Red Sea'),
('New Valley'),
('Matrouh'),
('North Coast');

-- Create trains table
CREATE TABLE trains (
    id INT PRIMARY KEY AUTO_INCREMENT,
    train_number VARCHAR(10) NOT NULL,
    from_station VARCHAR(100) NOT NULL,
    to_station VARCHAR(100) NOT NULL,
    departure_time VARCHAR(10) NOT NULL,
    train_type VARCHAR(20) NOT NULL
);

-- Insert sample trains for major routes
INSERT INTO trains (train_number, from_station, to_station, departure_time, train_type) VALUES
-- Cairo to Major Cities
('1001', 'Cairo', 'Alexandria', '08:00', 'Express'),
('1002', 'Cairo', 'Alexandria', '12:00', 'Express'),
('1003', 'Cairo', 'Alexandria', '16:00', 'Express'),
('1004', 'Cairo', 'Alexandria', '20:00', 'Express'),

('2001', 'Cairo', 'Giza', '07:00', 'Local'),
('2002', 'Cairo', 'Giza', '11:00', 'Local'),
('2003', 'Cairo', 'Giza', '15:00', 'Local'),
('2004', 'Cairo', 'Giza', '19:00', 'Local'),

('3001', 'Cairo', 'Port Said', '06:00', 'Express'),
('3002', 'Cairo', 'Port Said', '10:00', 'Express'),
('3003', 'Cairo', 'Port Said', '14:00', 'Express'),
('3004', 'Cairo', 'Port Said', '18:00', 'Express'),

('4001', 'Cairo', 'Suez', '05:00', 'Local'),
('4002', 'Cairo', 'Suez', '09:00', 'Local'),
('4003', 'Cairo', 'Suez', '13:00', 'Local'),
('4004', 'Cairo', 'Suez', '17:00', 'Local'),

('5001', 'Cairo', 'Ismailia', '07:30', 'Local'),
('5002', 'Cairo', 'Ismailia', '11:30', 'Local'),
('5003', 'Cairo', 'Ismailia', '15:30', 'Local'),
('5004', 'Cairo', 'Ismailia', '19:30', 'Local'),

-- Upper Egypt Routes
('6001', 'Cairo', 'Luxor', '07:00', 'High-Speed'),
('6002', 'Cairo', 'Luxor', '15:00', 'High-Speed'),
('6003', 'Cairo', 'Luxor', '23:00', 'High-Speed'),

('7001', 'Cairo', 'Aswan', '06:00', 'High-Speed'),
('7002', 'Cairo', 'Aswan', '14:00', 'High-Speed'),
('7003', 'Cairo', 'Aswan', '22:00', 'High-Speed'),

('8001', 'Cairo', 'Assiut', '08:00', 'Express'),
('8002', 'Cairo', 'Assiut', '12:00', 'Express'),
('8003', 'Cairo', 'Assiut', '16:00', 'Express'),
('8004', 'Cairo', 'Assiut', '20:00', 'Express'),

-- Return Routes
('9001', 'Alexandria', 'Cairo', '09:00', 'Express'),
('9002', 'Alexandria', 'Cairo', '13:00', 'Express'),
('9003', 'Alexandria', 'Cairo', '17:00', 'Express'),
('9004', 'Alexandria', 'Cairo', '21:00', 'Express'),

('10001', 'Giza', 'Cairo', '08:00', 'Local'),
('10002', 'Giza', 'Cairo', '12:00', 'Local'),
('10003', 'Giza', 'Cairo', '16:00', 'Local'),
('10004', 'Giza', 'Cairo', '20:00', 'Local'),

('11001', 'Port Said', 'Cairo', '07:00', 'Express'),
('11002', 'Port Said', 'Cairo', '11:00', 'Express'),
('11003', 'Port Said', 'Cairo', '15:00', 'Express'),
('11004', 'Port Said', 'Cairo', '19:00', 'Express'),

('12001', 'Suez', 'Cairo', '06:00', 'Local'),
('12002', 'Suez', 'Cairo', '10:00', 'Local'),
('12003', 'Suez', 'Cairo', '14:00', 'Local'),
('12004', 'Suez', 'Cairo', '18:00', 'Local'),

('13001', 'Ismailia', 'Cairo', '08:30', 'Local'),
('13002', 'Ismailia', 'Cairo', '12:30', 'Local'),
('13003', 'Ismailia', 'Cairo', '16:30', 'Local'),
('13004', 'Ismailia', 'Cairo', '20:30', 'Local'),

('14001', 'Luxor', 'Cairo', '08:00', 'High-Speed'),
('14002', 'Luxor', 'Cairo', '16:00', 'High-Speed'),
('14003', 'Luxor', 'Cairo', '00:00', 'High-Speed'),

('15001', 'Aswan', 'Cairo', '07:00', 'High-Speed'),
('15002', 'Aswan', 'Cairo', '15:00', 'High-Speed'),
('15003', 'Aswan', 'Cairo', '23:00', 'High-Speed'),

('16001', 'Assiut', 'Cairo', '09:00', 'Express'),
('16002', 'Assiut', 'Cairo', '13:00', 'Express'),
('16003', 'Assiut', 'Cairo', '17:00', 'Express'),
('16004', 'Assiut', 'Cairo', '21:00', 'Express'); 