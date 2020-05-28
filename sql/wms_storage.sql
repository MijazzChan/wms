CREATE TABLE storage
(
    item_id     BIGINT       NOT NULL
        PRIMARY KEY,
    item_amount BIGINT       NULL,
    item_name   VARCHAR(255) NULL
);

INSERT INTO wms.storage (item_id, item_amount, item_name) VALUES (1001, 7220, 'Test Item');
INSERT INTO wms.storage (item_id, item_amount, item_name) VALUES (1002, 5450, 'Honda');
INSERT INTO wms.storage (item_id, item_amount, item_name) VALUES (1003, 5652, 'Nissan');
INSERT INTO wms.storage (item_id, item_amount, item_name) VALUES (1004, 6496, 'Toyota');
INSERT INTO wms.storage (item_id, item_amount, item_name) VALUES (1005, 6430, 'Hyundai');
INSERT INTO wms.storage (item_id, item_amount, item_name) VALUES (1006, 8482, 'Benz');
INSERT INTO wms.storage (item_id, item_amount, item_name) VALUES (1007, 3371, 'Audi');
INSERT INTO wms.storage (item_id, item_amount, item_name) VALUES (1008, 2244, 'BWM');
INSERT INTO wms.storage (item_id, item_amount, item_name) VALUES (1009, 2983, 'Adidas');
INSERT INTO wms.storage (item_id, item_amount, item_name) VALUES (1010, 7454, 'Nike');
INSERT INTO wms.storage (item_id, item_amount, item_name) VALUES (1011, 7527, 'UA');
INSERT INTO wms.storage (item_id, item_amount, item_name) VALUES (1012, 8489, 'iPhone');
INSERT INTO wms.storage (item_id, item_amount, item_name) VALUES (1013, 2883, 'Samsung');