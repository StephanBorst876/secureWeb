INSERT INTO artikel VALUES (60,'Komijnen Kaas',10.00,878);
INSERT INTO artikel VALUES (63,'jonge kaas',2.25,990);
INSERT INTO artikel VALUES (64,'oude kaas',3.75,988);

INSERT INTO account (id,username,password,rol) VALUES (204,'Piet','$2a$12$9ZfjY8eSNGOivd/RKiBgt.duuPSYaC2ZoUCpyedwS0cix81bRnYJK','beheerder');
INSERT INTO account (id,username,password,rol) VALUES (205,'Pascal','$2a$12$a3mmayZiUfvPntn7GUVeLu5dcQMEaDg83CuJkU3rcjWlFvIuZeo4m','klant');
INSERT INTO account (id,username,password,rol) VALUES (206,'Olaf','$2a$12$IP1U.gDu51hpWJhjpWpRl.Q8et1Qw5Uyk.jfu6Ntq15isbC3J/.Ry','medewerker');
INSERT INTO account (id,username,password,rol) VALUES (207,'Liz','$2a$12$o/gbDUwHgWdMsiuYVJMDWekIGywHz6Y.LnvvWDj.QRcB6IEkrT/xG','klant');
INSERT INTO account (id,username,password,rol) VALUES (321,'Karel','$2a$12$UkZBgb7LrYQGYNG96c3n6OpBdf8o6s4QIvoCLiTiutaefsU6r31xS','klant');
INSERT INTO account (id,username,password,rol) VALUES (322,'Klant','$2a$12$eh4wg5izU2jCKhrB13f.sOauo0NkJqF2rmrEpP7HQBiEpqF/3W.yu','klant');

INSERT INTO klant (id,voornaam,tussenvoegsel,achternaam,account_id) VALUES (206,'liz','','natukunda',207);
INSERT INTO klant (id,voornaam,tussenvoegsel,achternaam,account_id) VALUES (237,'Karel','de','Grote',321);
INSERT INTO klant (id,voornaam,tussenvoegsel,achternaam,account_id) VALUES (238,'klant','van','klant',322);

INSERT INTO adres (adres_id,straatnaam,huisnummer,toevoeging,postcode,woonplaats,adres_type,klant_id)
VALUES (40,'straat','1','','1234AB','plaats','POSTADRES',206);
INSERT INTO adres (adres_id,straatnaam,huisnummer,toevoeging,postcode,woonplaats,adres_type,klant_id)
VALUES (41,'Straat','1','','1234AZ','Plaats','POSTADRES',237);

INSERT INTO adres (adres_id,straatnaam,huisnummer,toevoeging,postcode,woonplaats,adres_type,klant_id)
VALUES (43,'poststraat','11','','9999AS','postplaats','POSTADRES',238);
INSERT INTO adres (adres_id,straatnaam,huisnummer,toevoeging,postcode,woonplaats,adres_type,klant_id)
VALUES (44,'bezorgstraat','11','','9999AS','bezorgplaats','BEZORGADRES',238);
INSERT INTO adres (adres_id,straatnaam,huisnummer,toevoeging,postcode,woonplaats,adres_type,klant_id)
VALUES (45,'factuurstraat','11','','9999AS','factuurplaats','FACTUURADRES',238);
