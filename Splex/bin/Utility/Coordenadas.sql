.VERSION

.DATABASE
.show
.tables

CREATE TABLE arsenal(
	codigo VARCHAR(1),
	arsenalBelico VARCHAR(20));

INSERT INTO arsenal(codigo, arsenalBelico) VALUES 
("a","Avión"),
("b","Barco"),
("c","Convoy"),
("d","Dron"),
("t","Tanque");

CREATE TABLE infClasiffied (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
	geoposicion VARCHAR(20),
	lunes VARCHAR(20),
	martes VARCHAR(20),
	miercoles VARCHAR(20),
	jueves VARCHAR(20),
	viernes VARCHAR(20),
	tipoArsenal VARCHAR(20)
);

CREATE TABLE usuarios (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL,
    contraseña TEXT NOT NULL,
    fecha_creacion TIME DEFAULT CURRENT_TIME
);

INSERT INTO usuarios (nombre, contraseña)  VALUES
("profe","81dc9bdb52d04dc20036dbd8313ed055"),
("user1","11e53e50e9c2770cb2681b7f02466945"),
("user2","cf13f5514ff6022fe428396950effce6");

SELECT * FROM arsenal;
SELECT * FROM usuarios;
SELECT * FROM infClasiffied;
DROP TABLE usuarios;
DROP TABLE informacionClasificada;

CREATE TABLE Horario_Coordenada (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    Horarios TEXT NOT NULL,
    Lunes TEXT NOT NULL,
    Martes TEXT NOT NULL,
    Miercoles TEXT NOT NULL,
    Jueves TEXT NOT NULL,
    Viernes TEXT NOT NULL
);
SELECT * FROM Horario_Coordenada
