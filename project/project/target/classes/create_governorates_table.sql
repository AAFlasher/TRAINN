-- Create governorates table
CREATE TABLE IF NOT EXISTS governorates (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- Insert Egyptian governorates
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
('North Coast')
ON DUPLICATE KEY UPDATE name = VALUES(name); 