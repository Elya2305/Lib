ALTER TABLE books DROP COLUMN IF EXISTS txt;
ALTER TABLE books ADD COLUMN txt VARCHAR;

-- //////////////////////////// triggers for join tables//////////////////////
CREATE OR REPLACE FUNCTION book_join_tables_function_trigger()
              RETURNS trigger AS
              $BODY$
              DECLARE
              book_id_value int8;
BEGIN
    IF TG_ARGV[0] = 'NEW' THEN
			book_id_value := NEW.book_id;
ELSE
		  book_id_value := OLD.book_id;
END IF;

UPDATE books
SET txt = to_tsvector('simple', COALESCE(title, '') || ' ' ||

                                (SELECT COALESCE(string_agg(au.name, ' '), '')
                                 FROM authors au
                                          LEFT JOIN books_authors ba ON ba.authors_id = au.id
                                 WHERE ba.book_id = book_id_value) || ' ' ||

                                (SELECT COALESCE(string_agg(cl.title, ' '), '')
                                 FROM classifications cl
                                          LEFT JOIN books_classifications bc ON bc.classifications_id = cl.id
                                 WHERE bc.book_id = book_id_value) || ' ' ||

                                (SELECT COALESCE(string_agg(lb.title, ' '), '')
                                 FROM labels lb
                                          LEFT JOIN books_labels bl ON bl.labels_id = lb.id
                                 WHERE bl.book_id = book_id_value))
WHERE id = book_id_value;


IF TG_ARGV[0] = 'NEW'
			THEN RETURN NEW;
ELSE
			RETURN OLD;
END IF;

END
$BODY$
LANGUAGE 'plpgsql';

-- //////////////////////////////////books_classifications//////////////////////

DROP TRIGGER IF EXISTS fts_classifications_labels_insert_trigger ON books_classifications;

CREATE TRIGGER fts_classifications_labels_insert_trigger
    AFTER INSERT
    ON books_classifications
    FOR EACH ROW
    EXECUTE PROCEDURE book_join_tables_function_trigger('NEW');

DROP TRIGGER IF EXISTS fts_books_classifications_delete_trigger ON books_classifications;

CREATE TRIGGER fts_books_classifications_delete_trigger
    AFTER DELETE
    ON books_classifications
    FOR EACH ROW
    EXECUTE PROCEDURE book_join_tables_function_trigger('OLD');


-- //////////////////////////////////books_labels//////////////////////
DROP TRIGGER IF EXISTS fts_books_labels_insert_trigger ON books_labels;

CREATE TRIGGER fts_books_labels_insert_trigger
    AFTER INSERT
    ON books_labels
    FOR EACH ROW
    EXECUTE PROCEDURE book_join_tables_function_trigger('NEW');

DROP TRIGGER IF EXISTS fts_books_labels_delete_trigger ON books_labels;

CREATE TRIGGER fts_books_labels_delete_trigger
    AFTER DELETE
    ON books_labels
    FOR EACH ROW
    EXECUTE PROCEDURE book_join_tables_function_trigger('OLD');

-- //////////////////////////////////books_authors//////////////////////
DROP TRIGGER IF EXISTS fts_books_authors_insert_trigger ON books_authors;

CREATE TRIGGER fts_books_authors_insert_trigger
    AFTER INSERT
    ON books_authors
    FOR EACH ROW
    EXECUTE PROCEDURE book_join_tables_function_trigger('NEW');

DROP TRIGGER IF EXISTS fts_books_authors_delete_trigger ON books_authors;

CREATE TRIGGER fts_books_authors_delete_trigger
    AFTER DELETE
    ON books_authors
    FOR EACH ROW
    EXECUTE PROCEDURE book_join_tables_function_trigger('OLD');



-- //////////////////////////////////authors-labels-books//////////////////////

CREATE OR REPLACE FUNCTION tables_update_trigger_func()
              RETURNS trigger AS
              $BODY$
              DECLARE
              table_name_v VARCHAR;
table_column VARCHAR;
BEGIN
    table_name_v := TG_ARGV[0];
table_column := TG_ARGV[1];

EXECUTE format('UPDATE books
		SET txt =
		to_tsvector($escape$ simple $escape$ , COALESCE(title, $escape$$escape$) || $escape$ $escape$ ||
					(SELECT COALESCE(string_agg(au.name, $escape$ $escape$), $escape$$escape$)
					FROM authors au
					LEFT JOIN books_authors ba ON ba.authors_id = au.id
					WHERE ba.book_id = id) || $escape$ $escape$ ||

					(SELECT COALESCE(string_agg(cl.title, $escape$ $escape$), $escape$$escape$)
					FROM classifications cl
					LEFT JOIN books_classifications bc ON bc.classifications_id = cl.id
					WHERE bc.book_id = id) || $escape$ $escape$ ||

					(SELECT COALESCE(string_agg(lb.title, $escape$ $escape$), $escape$$escape$)
					FROM labels lb
					LEFT JOIN books_labels bl ON bl.labels_id = lb.id
					WHERE bl.book_id = id))
		WHERE id IN (SELECT book_id FROM %s WHERE %s = %s);', table_name_v, table_column, NEW.id);


RETURN NEW;
END
$BODY$
LANGUAGE 'plpgsql';


DROP TRIGGER IF EXISTS authors_update_trigger ON authors;

CREATE TRIGGER authors_update_trigger
    AFTER INSERT OR UPDATE
    ON authors
    FOR EACH ROW
EXECUTE PROCEDURE tables_update_trigger_func('books_authors', 'authors_id');

DROP TRIGGER IF EXISTS labels_update_trigger ON labels;

CREATE TRIGGER labels_update_trigger
    AFTER INSERT OR UPDATE
    ON labels
    FOR EACH ROW
EXECUTE PROCEDURE tables_update_trigger_func('books_labels', 'labels_id');

DROP TRIGGER IF EXISTS classifications_update_trigger ON classifications;

CREATE TRIGGER classifications_update_trigger
    AFTER INSERT OR UPDATE
    ON classifications
    FOR EACH ROW
EXECUTE PROCEDURE tables_update_trigger_func('books_classifications', 'classifications_id');
