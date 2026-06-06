-- 1. Poblar catálogo maestro de Categorías
INSERT INTO categorias (nombre, codigo_prefijo) VALUES 
('Laptop', 'LAP'),
('Monitor', 'MON'),
('Celular', 'CEL'),
('Impresora', 'IMP');

-- 2. Poblar tabla de Activos Tecnológicos
INSERT INTO activos_tecnologicos 
(id, folio_inventario, numero_serie, marca_modelo, estado, costo_adquisicion, fecha_ingreso, categoria_id) 
VALUES 
-- Laptops 
(UUID(), 'LAP-2026-001', 'SN-LAP-X901', 'Dell Latitude 7420', 'DISPONIBLE', 24500.00, CURRENT_TIMESTAMP, 1),
(UUID(), 'LAP-2026-002', 'SN-LAP-X902', 'Lenovo ThinkPad T14', 'ASIGNADO', 28000.50, CURRENT_TIMESTAMP, 1),
(UUID(), 'LAP-2026-003', 'SN-LAP-X903', 'HP EliteBook 840', 'EN_MANTENIMIENTO', 22000.00, CURRENT_TIMESTAMP, 1),

-- Monitores 
(UUID(), 'MON-2026-001', 'SN-MON-Y801', 'LG UltraGear 27"', 'DISPONIBLE', 6500.00, CURRENT_TIMESTAMP, 2),
(UUID(), 'MON-2026-002', 'SN-MON-Y802', 'Dell UltraSharp 24"', 'ASIGNADO', 5200.00, CURRENT_TIMESTAMP, 2),

-- Celulares 
(UUID(), 'CEL-2026-001', 'IMEI-1234567890', 'iPhone 13 Pro', 'ASIGNADO', 18500.00, CURRENT_TIMESTAMP, 3),
(UUID(), 'CEL-2026-002', 'IMEI-0987654321', 'Samsung Galaxy S22', 'BAJA', 15000.00, CURRENT_TIMESTAMP, 3);