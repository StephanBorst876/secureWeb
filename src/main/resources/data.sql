-- 
INSERT INTO artikel VALUES (60,'Komijnen Kaas',10.00,878);
INSERT INTO artikel VALUES (63,'jonge kaas',2.25,990);
INSERT INTO artikel VALUES (64,'oude kaas',3.75,988);

INSERT INTO account (id,username,password,rol) VALUES (204,'boer','$2a$10$Kjrtr2Mm6v/HzA2YAHVihejqb3uiKzUDNbJGcIWTP5W2T5BGXowYO','beheerder');
INSERT INTO account (id,username,password,rol) VALUES (205,'Pascal','$2a$12$a3mmayZiUfvPntn7GUVeLu5dcQMEaDg83CuJkU3rcjWlFvIuZeo4m','klant');
INSERT INTO account (id,username,password,rol) VALUES (206,'Olaf','$2a$10$6gb18xtFEIji9ADyEYUti.RCQOAfUDtgKoiBz1npg/evN5LrTUNYe','medewerker');
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

INSERT INTO bestelling (id, prijs, klant_id)
VALUES (1, 45.20, 206);
INSERT INTO bestelling (id, prijs, klant_id)
VALUES (2, 12.00, 237);
INSERT INTO bestelling (id, prijs, klant_id)
VALUES (3, 120.00, 238);

INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (1, 60, 5, 50.00, 1);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (2, 63, 2, 4.50, 1);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (3, 60, 2, 20.00, 2);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (4, 64, 10, 37.50, 2);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (5, 60, 100, 1000.00, 3);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (6, 63, 100, 225.00, 3);
INSERT INTO bestelregel (id, artikel_id, aantal, prijs, bestelling_id)
VALUES (7, 64, 100, 3750.00, 3);
