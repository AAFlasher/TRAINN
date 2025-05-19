-- Drop the existing trains table if it exists
DROP TABLE IF EXISTS trains;

-- Create the trains table
CREATE TABLE trains (
    id INT PRIMARY KEY AUTO_INCREMENT,
    train_number VARCHAR(10) NOT NULL,
    from_station VARCHAR(50) NOT NULL,
    to_station VARCHAR(50) NOT NULL,
    departure_time TIME NOT NULL,
    train_type VARCHAR(20) NOT NULL
);

-- Insert train routes from Cairo
INSERT INTO trains (train_number, from_station, to_station, departure_time, train_type) VALUES
-- Cairo to Alexandria (Express)
('EG-1001', 'Cairo', 'Alexandria', '08:00:00', 'Express'),
('EG-1002', 'Cairo', 'Alexandria', '10:00:00', 'Express'),
('EG-1003', 'Cairo', 'Alexandria', '12:00:00', 'Express'),
('EG-1004', 'Cairo', 'Alexandria', '14:00:00', 'Express'),
('EG-1005', 'Cairo', 'Alexandria', '16:00:00', 'Express'),
('EG-1006', 'Cairo', 'Alexandria', '18:00:00', 'Express'),
('EG-1007', 'Cairo', 'Alexandria', '20:00:00', 'Express'),
('EG-1008', 'Cairo', 'Alexandria', '22:00:00', 'Express'),

-- Cairo to Giza (Local)
('EG-2001', 'Cairo', 'Giza', '07:00:00', 'Local'),
('EG-2002', 'Cairo', 'Giza', '09:00:00', 'Local'),
('EG-2003', 'Cairo', 'Giza', '11:00:00', 'Local'),
('EG-2004', 'Cairo', 'Giza', '13:00:00', 'Local'),
('EG-2005', 'Cairo', 'Giza', '15:00:00', 'Local'),
('EG-2006', 'Cairo', 'Giza', '17:00:00', 'Local'),
('EG-2007', 'Cairo', 'Giza', '19:00:00', 'Local'),
('EG-2008', 'Cairo', 'Giza', '21:00:00', 'Local'),

-- Cairo to Port Said (High-Speed)
('EG-3001', 'Cairo', 'Port Said', '06:00:00', 'High-Speed'),
('EG-3002', 'Cairo', 'Port Said', '10:00:00', 'High-Speed'),
('EG-3003', 'Cairo', 'Port Said', '14:00:00', 'High-Speed'),
('EG-3004', 'Cairo', 'Port Said', '18:00:00', 'High-Speed'),
('EG-3005', 'Cairo', 'Port Said', '22:00:00', 'High-Speed'),

-- Cairo to Suez (Express)
('EG-4001', 'Cairo', 'Suez', '07:30:00', 'Express'),
('EG-4002', 'Cairo', 'Suez', '09:30:00', 'Express'),
('EG-4003', 'Cairo', 'Suez', '11:30:00', 'Express'),
('EG-4004', 'Cairo', 'Suez', '13:30:00', 'Express'),
('EG-4005', 'Cairo', 'Suez', '15:30:00', 'Express'),
('EG-4006', 'Cairo', 'Suez', '17:30:00', 'Express'),
('EG-4007', 'Cairo', 'Suez', '19:30:00', 'Express'),
('EG-4008', 'Cairo', 'Suez', '21:30:00', 'Express'),

-- Cairo to Ismailia (Local)
('EG-5001', 'Cairo', 'Ismailia', '08:30:00', 'Local'),
('EG-5002', 'Cairo', 'Ismailia', '10:30:00', 'Local'),
('EG-5003', 'Cairo', 'Ismailia', '12:30:00', 'Local'),
('EG-5004', 'Cairo', 'Ismailia', '14:30:00', 'Local'),
('EG-5005', 'Cairo', 'Ismailia', '16:30:00', 'Local'),
('EG-5006', 'Cairo', 'Ismailia', '18:30:00', 'Local'),
('EG-5007', 'Cairo', 'Ismailia', '20:30:00', 'Local'),
('EG-5008', 'Cairo', 'Ismailia', '22:30:00', 'Local'),

-- Cairo to Luxor (High-Speed)
('EG-6001', 'Cairo', 'Luxor', '07:00:00', 'High-Speed'),
('EG-6002', 'Cairo', 'Luxor', '11:00:00', 'High-Speed'),
('EG-6003', 'Cairo', 'Luxor', '15:00:00', 'High-Speed'),
('EG-6004', 'Cairo', 'Luxor', '19:00:00', 'High-Speed'),
('EG-6005', 'Cairo', 'Luxor', '23:00:00', 'High-Speed'),

-- Cairo to Aswan (Express)
('EG-7001', 'Cairo', 'Aswan', '08:00:00', 'Express'),
('EG-7002', 'Cairo', 'Aswan', '10:00:00', 'Express'),
('EG-7003', 'Cairo', 'Aswan', '12:00:00', 'Express'),
('EG-7004', 'Cairo', 'Aswan', '14:00:00', 'Express'),
('EG-7005', 'Cairo', 'Aswan', '16:00:00', 'Express'),
('EG-7006', 'Cairo', 'Aswan', '18:00:00', 'Express'),
('EG-7007', 'Cairo', 'Aswan', '20:00:00', 'Express'),
('EG-7008', 'Cairo', 'Aswan', '22:00:00', 'Express'),

-- Return routes to Cairo
-- Alexandria to Cairo (Express)
('EG-1101', 'Alexandria', 'Cairo', '08:00:00', 'Express'),
('EG-1102', 'Alexandria', 'Cairo', '10:00:00', 'Express'),
('EG-1103', 'Alexandria', 'Cairo', '12:00:00', 'Express'),
('EG-1104', 'Alexandria', 'Cairo', '14:00:00', 'Express'),
('EG-1105', 'Alexandria', 'Cairo', '16:00:00', 'Express'),
('EG-1106', 'Alexandria', 'Cairo', '18:00:00', 'Express'),
('EG-1107', 'Alexandria', 'Cairo', '20:00:00', 'Express'),
('EG-1108', 'Alexandria', 'Cairo', '22:00:00', 'Express'),

-- Giza to Cairo (Local)
('EG-2101', 'Giza', 'Cairo', '07:00:00', 'Local'),
('EG-2102', 'Giza', 'Cairo', '09:00:00', 'Local'),
('EG-2103', 'Giza', 'Cairo', '11:00:00', 'Local'),
('EG-2104', 'Giza', 'Cairo', '13:00:00', 'Local'),
('EG-2105', 'Giza', 'Cairo', '15:00:00', 'Local'),
('EG-2106', 'Giza', 'Cairo', '17:00:00', 'Local'),
('EG-2107', 'Giza', 'Cairo', '19:00:00', 'Local'),
('EG-2108', 'Giza', 'Cairo', '21:00:00', 'Local'),

-- Additional routes between other cities
-- Alexandria to Port Said (Express)
('EG-8001', 'Alexandria', 'Port Said', '08:00:00', 'Express'),
('EG-8002', 'Alexandria', 'Port Said', '12:00:00', 'Express'),
('EG-8003', 'Alexandria', 'Port Said', '16:00:00', 'Express'),
('EG-8004', 'Alexandria', 'Port Said', '20:00:00', 'Express'),

-- Port Said to Alexandria (Express)
('EG-8101', 'Port Said', 'Alexandria', '09:00:00', 'Express'),
('EG-8102', 'Port Said', 'Alexandria', '13:00:00', 'Express'),
('EG-8103', 'Port Said', 'Alexandria', '17:00:00', 'Express'),
('EG-8104', 'Port Said', 'Alexandria', '21:00:00', 'Express'),

-- Luxor to Aswan (Local)
('EG-9001', 'Luxor', 'Aswan', '07:00:00', 'Local'),
('EG-9002', 'Luxor', 'Aswan', '09:00:00', 'Local'),
('EG-9003', 'Luxor', 'Aswan', '11:00:00', 'Local'),
('EG-9004', 'Luxor', 'Aswan', '13:00:00', 'Local'),
('EG-9005', 'Luxor', 'Aswan', '15:00:00', 'Local'),
('EG-9006', 'Luxor', 'Aswan', '17:00:00', 'Local'),
('EG-9007', 'Luxor', 'Aswan', '19:00:00', 'Local'),
('EG-9008', 'Luxor', 'Aswan', '21:00:00', 'Local'),

-- Aswan to Luxor (Local)
('EG-9101', 'Aswan', 'Luxor', '08:00:00', 'Local'),
('EG-9102', 'Aswan', 'Luxor', '10:00:00', 'Local'),
('EG-9103', 'Aswan', 'Luxor', '12:00:00', 'Local'),
('EG-9104', 'Aswan', 'Luxor', '14:00:00', 'Local'),
('EG-9105', 'Aswan', 'Luxor', '16:00:00', 'Local'),
('EG-9106', 'Aswan', 'Luxor', '18:00:00', 'Local'),
('EG-9107', 'Aswan', 'Luxor', '20:00:00', 'Local'),
('EG-9108', 'Aswan', 'Luxor', '22:00:00', 'Local'),

-- New routes with different train numbers
-- Cairo to Sharm El Sheikh (High-Speed)
('EG-1201', 'Cairo', 'Sharm El Sheikh', '06:00:00', 'High-Speed'),
('EG-1202', 'Cairo', 'Sharm El Sheikh', '10:00:00', 'High-Speed'),
('EG-1203', 'Cairo', 'Sharm El Sheikh', '14:00:00', 'High-Speed'),
('EG-1204', 'Cairo', 'Sharm El Sheikh', '18:00:00', 'High-Speed'),
('EG-1205', 'Cairo', 'Sharm El Sheikh', '22:00:00', 'High-Speed'),

-- Sharm El Sheikh to Cairo (High-Speed)
('EG-1301', 'Sharm El Sheikh', 'Cairo', '07:00:00', 'High-Speed'),
('EG-1302', 'Sharm El Sheikh', 'Cairo', '11:00:00', 'High-Speed'),
('EG-1303', 'Sharm El Sheikh', 'Cairo', '15:00:00', 'High-Speed'),
('EG-1304', 'Sharm El Sheikh', 'Cairo', '19:00:00', 'High-Speed'),
('EG-1305', 'Sharm El Sheikh', 'Cairo', '23:00:00', 'High-Speed'),

-- Cairo to Hurghada (Express)
('EG-1401', 'Cairo', 'Hurghada', '08:00:00', 'Express'),
('EG-1402', 'Cairo', 'Hurghada', '10:00:00', 'Express'),
('EG-1403', 'Cairo', 'Hurghada', '12:00:00', 'Express'),
('EG-1404', 'Cairo', 'Hurghada', '14:00:00', 'Express'),
('EG-1405', 'Cairo', 'Hurghada', '16:00:00', 'Express'),
('EG-1406', 'Cairo', 'Hurghada', '18:00:00', 'Express'),
('EG-1407', 'Cairo', 'Hurghada', '20:00:00', 'Express'),
('EG-1408', 'Cairo', 'Hurghada', '22:00:00', 'Express'),

-- Hurghada to Cairo (Express)
('EG-1501', 'Hurghada', 'Cairo', '07:00:00', 'Express'),
('EG-1502', 'Hurghada', 'Cairo', '09:00:00', 'Express'),
('EG-1503', 'Hurghada', 'Cairo', '11:00:00', 'Express'),
('EG-1504', 'Hurghada', 'Cairo', '13:00:00', 'Express'),
('EG-1505', 'Hurghada', 'Cairo', '15:00:00', 'Express'),
('EG-1506', 'Hurghada', 'Cairo', '17:00:00', 'Express'),
('EG-1507', 'Hurghada', 'Cairo', '19:00:00', 'Express'),
('EG-1508', 'Hurghada', 'Cairo', '21:00:00', 'Express'),

-- Alexandria to Hurghada (Express)
('EG-1601', 'Alexandria', 'Hurghada', '08:00:00', 'Express'),
('EG-1602', 'Alexandria', 'Hurghada', '12:00:00', 'Express'),
('EG-1603', 'Alexandria', 'Hurghada', '16:00:00', 'Express'),
('EG-1604', 'Alexandria', 'Hurghada', '20:00:00', 'Express'),

-- Hurghada to Alexandria (Express)
('EG-1701', 'Hurghada', 'Alexandria', '09:00:00', 'Express'),
('EG-1702', 'Hurghada', 'Alexandria', '13:00:00', 'Express'),
('EG-1703', 'Hurghada', 'Alexandria', '17:00:00', 'Express'),
('EG-1704', 'Hurghada', 'Alexandria', '21:00:00', 'Express'),

-- Sharm El Sheikh to Hurghada (Local)
('EG-1801', 'Sharm El Sheikh', 'Hurghada', '07:00:00', 'Local'),
('EG-1802', 'Sharm El Sheikh', 'Hurghada', '09:00:00', 'Local'),
('EG-1803', 'Sharm El Sheikh', 'Hurghada', '11:00:00', 'Local'),
('EG-1804', 'Sharm El Sheikh', 'Hurghada', '13:00:00', 'Local'),
('EG-1805', 'Sharm El Sheikh', 'Hurghada', '15:00:00', 'Local'),
('EG-1806', 'Sharm El Sheikh', 'Hurghada', '17:00:00', 'Local'),
('EG-1807', 'Sharm El Sheikh', 'Hurghada', '19:00:00', 'Local'),
('EG-1808', 'Sharm El Sheikh', 'Hurghada', '21:00:00', 'Local'),

-- Hurghada to Sharm El Sheikh (Local)
('EG-1901', 'Hurghada', 'Sharm El Sheikh', '08:00:00', 'Local'),
('EG-1902', 'Hurghada', 'Sharm El Sheikh', '10:00:00', 'Local'),
('EG-1903', 'Hurghada', 'Sharm El Sheikh', '12:00:00', 'Local'),
('EG-1904', 'Hurghada', 'Sharm El Sheikh', '14:00:00', 'Local'),
('EG-1905', 'Hurghada', 'Sharm El Sheikh', '16:00:00', 'Local'),
('EG-1906', 'Hurghada', 'Sharm El Sheikh', '18:00:00', 'Local'),
('EG-1907', 'Hurghada', 'Sharm El Sheikh', '20:00:00', 'Local'),
('EG-1908', 'Hurghada', 'Sharm El Sheikh', '22:00:00', 'Local'); 