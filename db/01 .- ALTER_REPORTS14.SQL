
CREATE TABLE [dbo].[TIPO_RESPUESTA_REPORTE] (
  [TPRR_SEQ_CDG] numeric(16, 0) IDENTITY NOT NULL,
  [TPRR_NOMBRE] varchar(50) NOT NULL UNIQUE,
  PRIMARY KEY CLUSTERED ([TPRR_SEQ_CDG])
    WITH (
      PAD_INDEX = OFF, IGNORE_DUP_KEY = OFF, STATISTICS_NORECOMPUTE = OFF,
      ALLOW_ROW_LOCKS = OFF, ALLOW_PAGE_LOCKS = OFF)
)
GO

CREATE TABLE [dbo].[LOG_REPORTES] (
  [LOG_SEQ_CDG] numeric(16, 0) IDENTITY NOT NULL,
  [LOG_NOMBRE] varchar(200) NOT NULL,
  [LOG_HOST] varchar(60) NOT NULL,
  [LOG_USUARIO] varchar (60) NULL,
  [LOG_SQL] varchar(max) NOT NULL,
  [LOG_URL] varchar(200) NOT NULL,
  [LOG_FECHA_INICIO_CONSULTA_DB] datetime NOT NULL,
  [LOG_FECHA_TERMINO_CONSULTA_DB] datetime NULL,
  [LOG_FECHA_INICIO_GENERACION_REPORTE] datetime NULL,
  [LOG_FECHA_TERMINO_GENERACION_REPORTE] datetime NULL,
  [TPRR_SEQ_CDG] numeric(16, 0) NULL,
  [LOG_DESCRIPCION] varchar(200) NULL,
  PRIMARY KEY CLUSTERED ([LOG_SEQ_CDG])
    WITH (
      PAD_INDEX = OFF, IGNORE_DUP_KEY = OFF, STATISTICS_NORECOMPUTE = OFF,
      ALLOW_ROW_LOCKS = OFF, ALLOW_PAGE_LOCKS = OFF)
)
GO


ALTER TABLE [dbo].[LOG_REPORTES]
ADD CONSTRAINT [LOG_REPORTES_fk] FOREIGN KEY ([TPRR_SEQ_CDG])
  REFERENCES [dbo].[TIPO_RESPUESTA_REPORTE] ([TPRR_SEQ_CDG])
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
GO


INSERT INTO TIPO_RESPUESTA_REPORTE (TPRR_NOMBRE) SELECT 'OK';
INSERT INTO TIPO_RESPUESTA_REPORTE (TPRR_NOMBRE) SELECT 'Error General';
INSERT INTO TIPO_RESPUESTA_REPORTE (TPRR_NOMBRE) SELECT 'Alerta';
INSERT INTO TIPO_RESPUESTA_REPORTE (TPRR_NOMBRE) SELECT 'Error en SQL';
INSERT INTO TIPO_RESPUESTA_REPORTE (TPRR_NOMBRE) SELECT 'Error en parametros';
INSERT INTO TIPO_RESPUESTA_REPORTE (TPRR_NOMBRE) SELECT 'Error generando reporte';
INSERT INTO TIPO_RESPUESTA_REPORTE (TPRR_NOMBRE) SELECT 'Driver no soportado';
