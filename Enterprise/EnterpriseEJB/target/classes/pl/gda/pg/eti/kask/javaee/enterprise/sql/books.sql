delete from books_authors;
delete from authors;
delete from books;

insert into authors (name, surname, version) values ('Orson Scott', 'Card', 1);
insert into authors (name, surname, version) values ('Aaron', 'Johnston', 1);
insert into authors (name, surname, version) values ('Maciej', 'Guzek', 1);
insert into authors (name, surname, version) values ('Maja', 'Kossakowska', 1);
insert into authors (name, surname, version) values ('Neil', 'Gaiman', 1);

insert into books (title, cover, publish_date, type, version) values ('Gra Endera', 'SOFT', TIMESTAMP('2011-02-15 00:00:00'), 'book', 1);
insert into books (title, cover, publish_date, type, version) values ('Trzeci Świat', 'SOFT', TIMESTAMP('2009-10-01 00:00:00'), 'book', 1);
insert into books (title, cover, publish_date, type, version) values ('W Przededniu', 'SOFT', TIMESTAMP('2013-03-19 00:00:00'), 'book', 1);
insert into books (title, cover, publish_date, type, version) values ('Ruda Sfora', 'SOFT', TIMESTAMP('2011-01-14 00:00:00'), 'book', 1);
insert into books (title, cover, publish_date, volume, type, version) values ('Sandman - Pora Mgieł', 'HARD', TIMESTAMP('2010-05-17 00:00:00'), 4, 'comics', 1);

insert into books_authors(book, author) values ((select id from books where title = 'Gra Endera'), (select id from authors where name = 'Orson Scott'));
insert into books_authors(book, author) values ((select id from books where title = 'W Przededniu'), (select id from authors where name = 'Aaron'));
insert into books_authors(book, author) values ((select id from books where title = 'Trzeci Świat'), (select id from authors where name = 'Maciej'));
insert into books_authors(book, author) values ((select id from books where title = 'W Przededniu'), (select id from authors where name = 'Orson Scott'));
insert into books_authors(book, author) values ((select id from books where title = 'Ruda Sfora'), (select id from authors where name = 'Maja'));
insert into books_authors(book, author) values ((select id from books where title = 'Sandman - Pora Mgieł'), (select id from authors where name = 'Neil'));
