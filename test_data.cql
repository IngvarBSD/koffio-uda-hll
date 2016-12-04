CREATE TABLE IF NOT EXISTS udf.ware_hits (
	ware_id int,
	date_time timestamp,
	visitor_id int,
	PRIMARY KEY (ware_id, date_time)
);

INSERT INTO udf.ware_hits (ware_id, date_time, visitor_id) VALUES (1, toTimestamp(now()), 1);
INSERT INTO udf.ware_hits (ware_id, date_time, visitor_id) VALUES (1, toTimestamp(now()), 2);
INSERT INTO udf.ware_hits (ware_id, date_time, visitor_id) VALUES (1, toTimestamp(now()), 3);
INSERT INTO udf.ware_hits (ware_id, date_time, visitor_id) VALUES (1, toTimestamp(now()), 1);
INSERT INTO udf.ware_hits (ware_id, date_time, visitor_id) VALUES (1, toTimestamp(now()), 2);
INSERT INTO udf.ware_hits (ware_id, date_time, visitor_id) VALUES (1, toTimestamp(now()), 3);
INSERT INTO udf.ware_hits (ware_id, date_time, visitor_id) VALUES (1, toTimestamp(now()), 4);
INSERT INTO udf.ware_hits (ware_id, date_time, visitor_id) VALUES (1, toTimestamp(now()), 5);
INSERT INTO udf.ware_hits (ware_id, date_time, visitor_id) VALUES (1, toTimestamp(now()), 6);

SELECT count(visitor_id) as total_count FROM udf.ware_hits WHERE ware_id = 1; -- total_count = 9
SELECT udf.hll(visitor_id) as unique_users FROM udf.ware_hits WHERE ware_id = 1; --out aggregate returns unique_users = 6

--TRUNCATE udf.ware_hits;