
/*

drop table PARAMETROS_REPORTE
drop table REPORTE
drop table CONEXIONES
drop table CONTROLADOR
drop table AREA
drop table TIPO_PARAMETRO_REPORTE

*/

CREATE TABLE [REPORTE] (
  [RPTE_SEQ_CDG] numeric(16, 0) IDENTITY NOT NULL,
  [AREA_SEQ_CDG] numeric(16, 0) NOT NULL,
  [CONX_SEQ_CDG] numeric(16, 0) NOT NULL,
  [RPTE_NOMBRE] varchar(50) NOT NULL UNIQUE,
  [RPTE_DESCRIPCION] varchar(500) NULL,
  [RPTE_SQL] varchar(max) NOT NULL,
  [RPTE_ACTIVO] bit NOT NULL,
  [RPTE_USUARIO_CREADOR] varchar(60) NOT NULL,
  [RPTE_FECHA_CREACION] datetime NOT NULL,
  [RPTE_USUARIO_MODIFICADOR] varchar(60) NULL,
  [RPTE_FECHA_MODIFICACION] datetime NULL,
  PRIMARY KEY CLUSTERED ([RPTE_SEQ_CDG])
    WITH (
      PAD_INDEX = OFF, IGNORE_DUP_KEY = OFF, STATISTICS_NORECOMPUTE = OFF,
      ALLOW_ROW_LOCKS = OFF, ALLOW_PAGE_LOCKS = OFF)
)
GO


CREATE TABLE [dbo].[AREA] (
  [AREA_SEQ_CDG] numeric(16, 0) IDENTITY NOT NULL,
  [AREA_NOMBRE] varchar(80) NOT NULL UNIQUE,
  PRIMARY KEY CLUSTERED ([AREA_SEQ_CDG])
    WITH (
      PAD_INDEX = OFF, IGNORE_DUP_KEY = OFF, STATISTICS_NORECOMPUTE = OFF,
      ALLOW_ROW_LOCKS = OFF, ALLOW_PAGE_LOCKS = OFF)
)
GO

CREATE TABLE [dbo].[PARAMETROS_REPORTE] (
  [PARE_SEQ_CDG] numeric(16, 0) IDENTITY NOT NULL,
  [TPPR_SEQ_CDG] numeric(16, 0) NOT NULL,
  [RPTE_SEQ_CDG] numeric(16, 0) NOT NULL,
  [PARE_NOMBRE] varchar(50) NOT NULL,
  [PARE_REQUERIDO] bit NULL,
  [PARE_COMO] bit NULL,
  [PARE_DATO1] VARCHAR(10) NULL,
  [PARE_DATO2] VARCHAR(10) NULL,
  PRIMARY KEY CLUSTERED ([PARE_SEQ_CDG])
    WITH (
      PAD_INDEX = OFF, IGNORE_DUP_KEY = OFF, STATISTICS_NORECOMPUTE = OFF,
      ALLOW_ROW_LOCKS = OFF, ALLOW_PAGE_LOCKS = OFF)
)
GO

CREATE TABLE [dbo].[TIPO_PARAMETRO_REPORTE] (
  [TPPR_SEQ_CDG] numeric(16, 0) IDENTITY NOT NULL,
  [TPPR_NOMBRE] varchar(50) NOT NULL UNIQUE,
  PRIMARY KEY CLUSTERED ([TPPR_SEQ_CDG])
    WITH (
      PAD_INDEX = OFF, IGNORE_DUP_KEY = OFF, STATISTICS_NORECOMPUTE = OFF,
      ALLOW_ROW_LOCKS = OFF, ALLOW_PAGE_LOCKS = OFF)
)
GO

CREATE TABLE [dbo].[CONEXIONES] (
  [CONX_SEQ_CDG] numeric(16, 0) IDENTITY NOT NULL,
  [CTRL_SEQ_CDG] numeric(16, 0) NOT NULL,
  [CONX_NOMBRE] varchar(50) NOT NULL UNIQUE,
  [CONX_URL] varchar(500) NOT NULL,
  [CONX_USER] varchar (60) NULL,
  [CONX_PASS] varchar(32)  NULL,
  [CONX_USUARIO_CREADOR] varchar(60) NOT NULL,
  [CONX_FECHA_CREACION] datetime NOT NULL,
  [CONX_USUARIO_MODIFICADOR] varchar(60) NULL,
  [CONX_FECHA_MODIFICACION] datetime NULL,
  PRIMARY KEY CLUSTERED ([CONX_SEQ_CDG])
    WITH (
      PAD_INDEX = OFF, IGNORE_DUP_KEY = OFF, STATISTICS_NORECOMPUTE = OFF,
      ALLOW_ROW_LOCKS = OFF, ALLOW_PAGE_LOCKS = OFF)
)
GO

CREATE TABLE [dbo].[CONTROLADOR] (
  [CTRL_SEQ_CDG] numeric(16, 0) IDENTITY NOT NULL,
  [CTRL_NOMBRE] varchar(60) NOT NULL UNIQUE,
  PRIMARY KEY CLUSTERED ([CTRL_SEQ_CDG])
    WITH (
      PAD_INDEX = OFF, IGNORE_DUP_KEY = OFF, STATISTICS_NORECOMPUTE = OFF,
      ALLOW_ROW_LOCKS = OFF, ALLOW_PAGE_LOCKS = OFF)
)
GO

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


ALTER TABLE [dbo].[CONEXIONES]
ADD CONSTRAINT [CONEXIONES_fk] FOREIGN KEY ([CTRL_SEQ_CDG]) 
  REFERENCES [dbo].[CONTROLADOR] ([CTRL_SEQ_CDG]) 
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
GO

ALTER TABLE [dbo].[PARAMETROS_REPORTE]
ADD CONSTRAINT [PARAMETROS_REPORTE_fk] FOREIGN KEY ([TPPR_SEQ_CDG]) 
  REFERENCES [dbo].[TIPO_PARAMETRO_REPORTE] ([TPPR_SEQ_CDG]) 
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
GO

ALTER TABLE [dbo].[REPORTE]
ADD CONSTRAINT [REPORTE_fk] FOREIGN KEY ([AREA_SEQ_CDG]) 
  REFERENCES [dbo].[AREA] ([AREA_SEQ_CDG]) 
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
GO

ALTER TABLE [dbo].[REPORTE]
ADD CONSTRAINT [REPORTE_fk2] FOREIGN KEY ([CONX_SEQ_CDG]) 
  REFERENCES [dbo].[CONEXIONES] ([CONX_SEQ_CDG]) 
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
GO

ALTER TABLE [dbo].[PARAMETROS_REPORTE]
ADD CONSTRAINT [PARAMETROS_REPORTE_fk2] FOREIGN KEY ([RPTE_SEQ_CDG]) 
  REFERENCES [dbo].[REPORTE] ([RPTE_SEQ_CDG]) 
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
GO

