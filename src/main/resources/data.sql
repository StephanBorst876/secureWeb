INSERT INTO artikel VALUES (60,'komijnen kaas',2.00,878);
INSERT INTO artikel VALUES (61,'blauwe kaas', 2.50,50);
INSERT INTO artikel VALUES (62,'brie',1.99,5000);
INSERT INTO artikel VALUES (63,'jonge kaas',1.25,990);
INSERT INTO artikel VALUES (64,'oude kaas',1.75,988);
INSERT INTO artikel VALUES (65,'geitenkaas',2.00,632);
INSERT INTO artikel VALUES (66,'emmentaler',2.10,320);
INSERT INTO artikel VALUES (67,'gorgonzola',2.75,1108);
INSERT INTO artikel VALUES (68,'parmezaanse kaas',2.49,462);
INSERT INTO artikel VALUES (69,'mozzarella',3.04,123);

INSERT INTO account (id,username,password,rol) VALUES (204,'boer','$2a$10$Kjrtr2Mm6v/HzA2YAHVihejqb3uiKzUDNbJGcIWTP5W2T5BGXowYO','beheerder');
INSERT INTO account (id,username,password,rol) VALUES (205,'Pascal','$2a$12$a3mmayZiUfvPntn7GUVeLu5dcQMEaDg83CuJkU3rcjWlFvIuZeo4m','klant');
INSERT INTO account (id,username,password,rol) VALUES (206,'Olaf','$2a$12$sByavLS.cRc6YGf3AvKKtuhAaW/6PJpePkPdm7p/wpkhwd.T4pgIu','medewerker');
INSERT INTO account (id,username,password,rol) VALUES (207,'Liz','$2a$12$o/gbDUwHgWdMsiuYVJMDWekIGywHz6Y.LnvvWDj.QRcB6IEkrT/xG','klant');
INSERT INTO account (id,username,password,rol) VALUES (321,'Karel','$2a$12$UkZBgb7LrYQGYNG96c3n6OpBdf8o6s4QIvoCLiTiutaefsU6r31xS','klant');
INSERT INTO account (id,username,password,rol) VALUES (322,'Klant','$2a$12$vr86u/1t.wM0yUVGnF8jp.vnnLdOy24FZjRSFsOhUNkgXdd4kQsdu','klant');

INSERT INTO klant (id,voornaam,tussenvoegsel,achternaam,account_id) VALUES (206,'liz','','natukunda',207);
INSERT INTO klant (id,voornaam,tussenvoegsel,achternaam,account_id) VALUES (237,'Karel','de','Grote',321);
INSERT INTO klant (id,voornaam,tussenvoegsel,achternaam,account_id) VALUES (238,'klant','van','klant',322);
INSERT INTO klant (id,voornaam,tussenvoegsel,achternaam,account_id) VALUES (239,'Pascal','','Stevens',205);

INSERT INTO adres (adres_id,straatnaam,huisnummer,toevoeging,postcode,woonplaats,adres_type,klant_id)
VALUES (40,'straat','1','','1234AB','plaats','POSTADRES',206);
INSERT INTO adres (adres_id,straatnaam,huisnummer,toevoeging,postcode,woonplaats,adres_type,klant_id)
VALUES (41,'Straat','1','','1234AZ','Plaats','POSTADRES',237);
INSERT INTO adres (adres_id,straatnaam,huisnummer,toevoeging,postcode,woonplaats,adres_type,klant_id)
VALUES (42,'Schalmeistraat','19','c','6217ET','Maastricht','POSTADRES',239);
INSERT INTO adres (adres_id,straatnaam,huisnummer,toevoeging,postcode,woonplaats,adres_type,klant_id)
VALUES (46,'Schalmeistraat','17','c','6217ET','Maastricht','BEZORGADRES',239);

INSERT INTO adres (adres_id,straatnaam,huisnummer,toevoeging,postcode,woonplaats,adres_type,klant_id)
VALUES (43,'poststraat','11','','9999AS','postplaats','POSTADRES',238);
INSERT INTO adres (adres_id,straatnaam,huisnummer,toevoeging,postcode,woonplaats,adres_type,klant_id)
VALUES (44,'bezorgstraat','11','','9999AS','bezorgplaats','BEZORGADRES',238);
INSERT INTO adres (adres_id,straatnaam,huisnummer,toevoeging,postcode,woonplaats,adres_type,klant_id)
VALUES (45,'factuurstraat','11','','9999AS','factuurplaats','FACTUURADRES',238);

INSERT INTO bestelling (id, prijs, klant_id)
VALUES (1, 12.50, 206);
INSERT INTO bestelling (id, prijs, klant_id)
VALUES (2, 21.50, 237);
INSERT INTO bestelling (id, prijs, klant_id)
VALUES (3, 500.00, 238);
INSERT INTO bestelling (id, prijs, klant_id)
VALUES (4, 19.16, 238);
INSERT INTO bestelling (id, prijs, klant_id)
VALUES (5, 21.28, 238);
INSERT INTO bestelling (id, prijs, klant_id)
VALUES (6, 65.96, 237);
INSERT INTO bestelling (id, prijs, klant_id)
VALUES (7, 24.25, 206);
INSERT INTO bestelling (id, prijs, klant_id)
VALUES (8, 73.27, 238);
INSERT INTO bestelling (id, prijs, klant_id)
VALUES (9, 14.25, 206);
INSERT INTO bestelling (id, prijs, klant_id)
VALUES (10, 101.05, 237);

INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (1, 60, 5, 10.00, 1);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (2, 63, 2, 2.50, 1);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (3, 60, 2, 4.00, 2);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (4, 64, 10, 17.50, 2);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (5, 60, 100, 200.00, 3);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (6, 63, 100, 125.00, 3);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (7, 64, 100, 175.00, 3);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (8, 65, 3, 6.00, 4);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (9, 66, 1, 2.10, 4);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (10, 68, 2, 4.98, 4);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (11, 69, 2, 6.08, 4);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (12, 69, 7, 21.28, 5);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (13, 60, 2, 4.00, 6);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (14, 63, 5, 6.25, 6);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (15, 65, 9, 18.00, 6);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (16, 66, 3, 6.30, 6);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (17, 67, 7, 19.25, 6);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (18, 69, 4, 12.16, 6);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (19, 66, 5, 10.50, 7);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (20, 67, 5, 13.75, 7);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (21, 61, 11, 27.50, 8);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (22, 62, 23, 45.77, 8);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (23, 65, 3, 6.00, 9);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (24, 67, 3, 8.25, 9);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (25,62 , 15, 29.85, 10);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (26, 64, 20, 35.00, 10);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (27, 66, 10, 21.00, 10);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (28, 69, 5, 15.20, 10);