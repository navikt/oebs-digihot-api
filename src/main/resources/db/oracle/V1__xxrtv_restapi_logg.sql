-------------------------------------------------------------------------------
-- Skjema            : OEBS_API
-- Tabell            : XXRTV_DIGIHOT_LOGG
-- Kildefil          : V1__xxrtv_digihot_logg.sql
-- Beskrivelse       : Loggtabell for API-kall.
-------------------------------------------------------------------------------
CREATE TABLE xxrtv_digihot_logg( kall_logg_id        NUMBER        NOT NULL
                      , korrelasjon_id      VARCHAR2(50)  NOT NULL
                      , tidspunkt           TIMESTAMP(9)  NOT NULL
                      , type                VARCHAR2(10)  NOT NULL
                      , kall_retning        VARCHAR2(10)  NOT NULL
                      , method              VARCHAR2(10)
                      , operation           VARCHAR2(100) NOT NULL
                      , status              NUMBER
                      , kalltid             NUMBER        NOT NULL
                      , request             CLOB
                      , response            CLOB
                      , logginfo            CLOB
                      , CONSTRAINT xxrtv_kalo_digihot_pk PRIMARY KEY(kall_logg_id))
PARTITION BY RANGE(tidspunkt)
INTERVAL(NUMTOYMINTERVAL(1, 'MONTH'))
( PARTITION kall_logg_data_p1 VALUES LESS THAN( DATE '2021-09-01'));

CREATE INDEX xxrtv_kalo_digihot_U3 ON xxrtv_digihot_logg
(status) LOCAL;

-- Legg til sekvens og trigger
CREATE  SEQUENCE xxrtv_digihot_api_seq
START WITH 1
INCREMENT BY 1;

CREATE OR REPLACE TRIGGER xxrtv_kalo_digihot_bir
  BEFORE INSERT ON xxrtv_digihot_logg
  FOR EACH ROW
BEGIN
  SELECT xxrtv_digihot_api_seq.nextval
  INTO   :new.kall_logg_id
  FROM   DUAL;
END;

ALTER TABLE xxrtv_digihot_logg
    ADD (CONSTRAINT xxrtv_type_ck1 CHECK (type IN ('PLSQL', 'REST')));

ALTER TABLE xxrtv_digihot_logg
    ADD (CONSTRAINT xxrtv_kall_retning_ck1 CHECK (kall_retning IN ('INN','UT')));

CREATE INDEX xxrtv_kalo_U1 ON xxrtv_digihot_logg
    (operation, kall_retning);

CREATE INDEX xxrtv_kalo_U2 ON xxrtv_digihot_logg
    (korrelasjon_id);