use inventory_management;


drop procedure if EXISTS user_create;
DELIMITER $$
CREATE PROCEDURE user_create(
     in _first_name   VARCHAR(100),
     in _last_name    VARCHAR(100) ,
     in _avatar       VARCHAR(255) ,
     in _user_name    VARCHAR(50)  ,
     in _password     VARCHAR(100) ,
     in _email        VARCHAR(100) ,
     in _active_flag  INTEGER,
     in _created_date TIMESTAMP,
     in _updated_date TIMESTAMP
)

body:BEGIN
	SET max_sp_recursion_depth=255;
	if(select count(user.id) from user where user.user_name and user.email) > 0 then
		SET @message_text = CONCAT('User name \'', user_name, '\' already exists');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = @message_text;
    else
		insert into user(first_name, last_name, avatar, user_name, password, email, active_flag, created_date, updated_date)
        values (_first_name, _last_name, _avatar, _user_name, _password, _email, _active_flag, _created_date, _updated_date);
        select * from user where email = _email and user_name = _user_name;
    end if;
END$$
DELIMITER ;

