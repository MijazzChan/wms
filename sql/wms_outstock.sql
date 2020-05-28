CREATE TABLE outstock
(
    transaction_id BIGINT      NOT NULL
        PRIMARY KEY,
    date           DATETIME(6) NULL,
    item_id        BIGINT      NULL,
    out_count      BIGINT      NULL,
    via_id         BIGINT      NULL,
    CONSTRAINT outstock___emidfk
        FOREIGN KEY (via_id) REFERENCES employee (em_id),
    CONSTRAINT outstock___itemidfk
        FOREIGN KEY (item_id) REFERENCES storage (item_id)
            ON DELETE CASCADE
);

INSERT INTO wms.outstock (transaction_id, date, item_id, out_count, via_id) VALUES (10, '2020-05-21 06:55:41.669000000', 1001, 2, 201701);
INSERT INTO wms.outstock (transaction_id, date, item_id, out_count, via_id) VALUES (11, '2020-05-21 07:25:50.068000000', 1002, 20, 201701);
INSERT INTO wms.outstock (transaction_id, date, item_id, out_count, via_id) VALUES (12, '2020-05-21 07:26:02.147000000', 1008, 267, 201701);
INSERT INTO wms.outstock (transaction_id, date, item_id, out_count, via_id) VALUES (13, '2020-05-21 07:26:11.811000000', 1005, 28, 201701);
INSERT INTO wms.outstock (transaction_id, date, item_id, out_count, via_id) VALUES (14, '2020-05-21 07:26:17.973000000', 1012, 234, 201701);
INSERT INTO wms.outstock (transaction_id, date, item_id, out_count, via_id) VALUES (15, '2020-05-21 07:26:25.132000000', 1001, 7, 201701);
INSERT INTO wms.outstock (transaction_id, date, item_id, out_count, via_id) VALUES (16, '2020-05-21 07:26:32.902000000', 1004, 72, 201701);
INSERT INTO wms.outstock (transaction_id, date, item_id, out_count, via_id) VALUES (17, '2020-05-21 07:27:00.940000000', 1011, 3, 201701);
INSERT INTO wms.outstock (transaction_id, date, item_id, out_count, via_id) VALUES (18, '2020-05-21 07:27:04.262000000', 1011, 34, 201701);
INSERT INTO wms.outstock (transaction_id, date, item_id, out_count, via_id) VALUES (19, '2020-05-21 07:27:10.605000000', 1003, 38, 201701);
INSERT INTO wms.outstock (transaction_id, date, item_id, out_count, via_id) VALUES (20, '2020-05-21 07:27:22.038000000', 1004, 2, 201701);
INSERT INTO wms.outstock (transaction_id, date, item_id, out_count, via_id) VALUES (21, '2020-05-21 07:27:25.467000000', 1004, 8, 201701);
INSERT INTO wms.outstock (transaction_id, date, item_id, out_count, via_id) VALUES (22, '2020-05-21 07:27:30.124000000', 1008, 81, 201701);
INSERT INTO wms.outstock (transaction_id, date, item_id, out_count, via_id) VALUES (27, '2020-05-27 03:55:35.792000000', 1006, 500, 201701);