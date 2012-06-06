connect 'jdbc:derby://localhost:1527/C:/temp/DB/DB;create=true;user=APP;password=APP';
create table EBAY(id int generated always as identity, name varchar(20), categorie varchar(50),cat_id int,PRIMARY KEY (id));



select * from EBAY;
drop table EBAY;
disconnect;
insert into TODO (id,summary,description) values (DEFAULT,'TEST SUMMARY', 'BESCHREIBUNG');

exit;