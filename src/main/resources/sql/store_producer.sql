use inventory_management;
drop procedure if EXISTS futureUser;
DELIMITER $$
CREATE PROCEDURE futureUser(
    -- _first_name   VARCHAR(100),
--     _last_name    VARCHAR(100) ,
--     _avatar       VARCHAR(255) ,
--     _user_name    VARCHAR(50)  ,
--     _password     VARCHAR(100) ,
--     _email        VARCHAR(100) ,
--     _active_flag  INTEGER,
--     _created_date TIMESTAMP,
--     _updated_date TIMESTAMP
	IN _tables VARCHAR(255),
	IN _fields TEXT,
	IN _joins TEXT,
	IN _condition TEXT,
	IN _group TEXT,
	IN _order TEXT,
	IN _limit TEXT
)

body:BEGIN
		DECLARE code char(5) default '00000';
		DECLARE msg TEXT;
		DECLARE rowss INT;
		DECLARE result TEXT;
	
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
    BEGIN
		GET diagnostics condition 1
        code = returned_sqlstate, msg = message_text;
    END;
    
		SET @sql = CONCAT('INSERT INTO', _table, ' (', _field, ') VALUES (', _values, ');');
		prepare _query from @sql;
		execute _query;
    
    if code = '00000' then
		get diagnostics rowss = row_count;
        set @sql = concat('select max(', _increment,') as lastID from', _table, ';');
        prepare _query from @sql;
        execute _query;
    else
		set result = concat('insert failed, error = ', code, ', message = ', msg, @sql);
	select result as mysqlerror;
    end if;
END$$
DELIMITER ;

