--Creacion de tabla para auditoria
CREATE TABLE audit_cambios (
	id SERIAL PRIMARY KEY,
	nombre_tabla TEXT NOT NULL,
	operacion TEXT NOT NULL,
	usuario TEXT DEFAULT session_user,
	fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	datos_viejos JSONB,
	datos_nuevos JSONB
);
--Creación de función para trigger
CREATE OR REPLACE FUNCTION audit()
RETURNs TRIGGER AS $$
BEGIN
	IF (TG_OP = 'UPDATE') THEN
		INSERT INTO audit_cambios(nombre_tabla, operacion, datos_viejos, datos_nuevos)
		VALUES (TG_TABLE_NAME, TG_OP, row_to_json(OLD), row_to_json(NEW));
		RETURN NEW;
	ELSEIF (TG_OP = 'INSERT') THEN
		INSERT INTO audit_cambios(nombre_tabla, operacion, datos_nuevos)
		VALUES (TG_TABLE_NAME, TG_OP, row_to_json(NEW));
		RETURN NEW;
	END IF;
	RETURN NULL;
END;
$$ LANGUAGE plpgsql;

--Creación de trigger para Asset ads
CREATE TRIGGER trigger_audit
AFTER INSERT OR UPDATE ON activos_tecnologicos
FOR EACH ROW EXECUTE FUNCTION audit();
