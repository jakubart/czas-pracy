# Czas pracy
> Ewidencja pracowników.

## Opis aplikacji

Aplikacja pozwala na zapisywanie godzin przebytych w pracy pracowników.
Na podstawie wprowadzonych informacji można wygenerować zestawienie dla danego pracownika lub działu.
System zapisuje informacjie w bazie danych o rekordach usuniętych oraz zmodyfikowanych.

## Użyte technologie
* Java - 1.8
* JavaFX
* Apache POI - 4.0.1
* MySQL
* PLSQL

## Opis uruchomienia

Przed uruchomieniem aplikacji należy uruchumić dowolny server db (osobiście użyłem MySQL v. 8.0.12).
Nastęnie należy stworzyć nową bazę danych o dowolnej nazwie (moja nazwa czas_pracy) oraz 3 tabele wedlug poniższego schematu:
![Example screenshot](./img/db.JPG)

W klasie sample.database.ConnectionHelper należy podać wszystkie infomracjie o połączeniu do bazy danych(adres, nazwa bazy danych, haslo i login).
