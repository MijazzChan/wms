CREATE TABLE instock
(
    transaction_id BIGINT      NOT NULL
        PRIMARY KEY,
    date           DATETIME(6) NULL,
    in_count       BIGINT      NULL,
    item_id        BIGINT      NULL,
    via_id         BIGINT      NULL,
    CONSTRAINT instock___emidfk
        FOREIGN KEY (via_id) REFERENCES employee (em_id),
    CONSTRAINT instock___itemidfk
        FOREIGN KEY (item_id) REFERENCES storage (item_id)
            ON DELETE CASCADE
);

INSERT INTO wms.instock (transaction_id, date, in_count, item_id, via_id) VALUES (1, '2020-05-13 04:28:33.839000000', 80, 1001, 201705);
INSERT INTO wms.instock (transaction_id, date, in_count, item_id, via_id) VALUES (2, '2020-05-20 04:42:28.782000000', 250, 1008, 201701);
INSERT INTO wms.instock (transaction_id, date, in_count, item_id, via_id) VALUES (3, '2020-05-20 04:50:12.469000000', 24, 1002, 201701);
INSERT INTO wms.instock (transaction_id, date, in_count, item_id, via_id) VALUES (4, '2020-05-20 04:51:01.146000000', 34, 1002, 201701);
INSERT INTO wms.instock (transaction_id, date, in_count, item_id, via_id) VALUES (5, '2020-05-20 04:53:12.064000000', 33, 1002, 201701);
INSERT INTO wms.instock (transaction_id, date, in_count, item_id, via_id) VALUES (6, '2020-05-20 04:56:55.500000000', 93, 1002, 201702);
INSERT INTO wms.instock (transaction_id, date, in_count, item_id, via_id) VALUES (7, '2020-05-20 04:57:07.030000000', 103, 1001, 201701);
INSERT INTO wms.instock (transaction_id, date, in_count, item_id, via_id) VALUES (8, '2020-05-20 04:58:05.221000000', 765, 1001, 201701);
INSERT INTO wms.instock (transaction_id, date, in_count, item_id, via_id) VALUES (9, '2020-05-20 04:58:41.300000000', 2883, 1013, 201701);
INSERT INTO wms.instock (transaction_id, date, in_count, item_id, via_id) VALUES (23, '2020-05-21 07:29:05.445000000', 43, 1001, 201706);
INSERT INTO wms.instock (transaction_id, date, in_count, item_id, via_id) VALUES (24, '2020-05-21 07:29:11.067000000', 289, 1002, 201701);
INSERT INTO wms.instock (transaction_id, date, in_count, item_id, via_id) VALUES (25, '2020-05-21 07:29:13.637000000', 281, 1002, 201701);
INSERT INTO wms.instock (transaction_id, date, in_count, item_id, via_id) VALUES (26, '2020-05-21 07:29:19.762000000', 12, 1010, 201701);
INSERT INTO wms.instock (transaction_id, date, in_count, item_id, via_id) VALUES (28, '2020-05-28 10:26:49.313000000', 3, 1002, 201701);