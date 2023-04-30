CREATE FUNCTION delete_hobbies(hobby_name VARCHAR)
    RETURNS BOOLEAN
    LANGUAGE PLPGSQL
AS
$$
BEGIN
    DELETE FROM hobbies h where h.hobby = hobby_name;
END;
$$;


CREATE FUNCTION delete_hobbies(hobby_name VARCHAR, id_no NUMERIC)
    RETURNS BOOLEAN
    LANGUAGE PLPGSQL
AS
$$
BEGIN
    DELETE FROM hobbies h where h.hobby = hobby_name AND h.id = id_no;
END;
$$