CREATE TABLE employee
(
    em_id    BIGINT       NOT NULL
        PRIMARY KEY,
    em_age   INT          NOT NULL,
    em_name  VARCHAR(255) NULL,
    em_sex   INT          NOT NULL,
    is_admin BIT          NOT NULL,
    passwd   VARCHAR(255) NULL
);

INSERT INTO wms.employee (em_id, em_age, em_name, em_sex, is_admin, passwd) VALUES (201701, 20, 'root', 1, true, '$2a$12$9seCiw8sR52jVTQtcye./OQrzzLfb12yB.Lv8EH6R1LwONIkCIsve');
INSERT INTO wms.employee (em_id, em_age, em_name, em_sex, is_admin, passwd) VALUES (201702, 25, 'user', 1, false, '$2a$12$Z33Z9F6zFO6WtHJKAGojqeeBmsMEzIgwREo/qwEsycsTo7xWgj6ke');
INSERT INTO wms.employee (em_id, em_age, em_name, em_sex, is_admin, passwd) VALUES (201704, 29, 'zstu', 0, false, '$2a$12$MKCUe5O15BjCjd3lEguGa.SappicbOb4wUwENUd6qxXrZLQk8zl3.');
INSERT INTO wms.employee (em_id, em_age, em_name, em_sex, is_admin, passwd) VALUES (201705, 34, 'hdu', 0, false, '$2a$12$t.jFyvBmHMPomHpmlrGec.u9h3dl3BiWHjQJO3P6cKQdySr2PlVJq');
INSERT INTO wms.employee (em_id, em_age, em_name, em_sex, is_admin, passwd) VALUES (201706, 17, 'sci', 1, false, '$2a$12$os8TcEGlffvvCwMIOemywuXantkF.CaOUNGabfN1q1RLUHM1/bPE6');
INSERT INTO wms.employee (em_id, em_age, em_name, em_sex, is_admin, passwd) VALUES (201707, 19, 'database', 0, false, '$2a$12$W7bqkRtFGFpfzMx5PKtaI.EIWKAfQ7FLrXJVUX9bC4SMkjCTVzNu.');
INSERT INTO wms.employee (em_id, em_age, em_name, em_sex, is_admin, passwd) VALUES (201708, 20, 'mijazz', 1, false, '$2a$12$n1opgpg0tNqa/dOnRj1cMuAkoWyNlewhutva..RqNVeJ4FXhEP9ea');
INSERT INTO wms.employee (em_id, em_age, em_name, em_sex, is_admin, passwd) VALUES (201709, 20, 'testadd', 0, false, '$2a$12$fB8M30WOAeSTWCGpvxt0IeZyY9UiXcXmjpyKM1ySbSD4HtcsJueVu');
INSERT INTO wms.employee (em_id, em_age, em_name, em_sex, is_admin, passwd) VALUES (201710, 20, 'testdel', 1, false, '$2a$12$h3TtorG34S2X9VBd0uqDHOTh4ltSmSK5ioM2.YAIHZSysl00nynw.');