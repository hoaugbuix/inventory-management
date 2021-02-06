-- drop schema inventory_management;
-- create schema inventory_management;
use
inventory_management;

CREATE TABLE user
(
    id           INTEGER      NOT NULL AUTO_INCREMENT,
    first_name   VARCHAR(100) NOT NULL,
    last_name    VARCHAR(100) NOT NULL,
    avatar       VARCHAR(255) NOT NULL,
    user_name    VARCHAR(50)  NOT NULL,
    password     VARCHAR(100) NOT NULL,
    email        VARCHAR(100) NOT NULL,
    -- verification_code VARCHAR(100) NOT NULL,
    active_flag  INTEGER      NOT NULL DEFAULT 1,
    created_date TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE user_role
(
    id           INTEGER   NOT NULL AUTO_INCREMENT,
    user_id      INTEGER   NOT NULL,
    role_id      INTEGER   NOT NULL,
    active_flag  INTEGER   NOT NULL DEFAULT 1,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);


CREATE TABLE role
(
    id           INTEGER     NOT NULL AUTO_INCREMENT,
    role_name    VARCHAR(50) NOT NULL,
    description  VARCHAR(255) NULL,
    active_flag  INTEGER              DEFAULT 1,
    created_date TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE auth
(
    id           INTEGER   NOT NULL AUTO_INCREMENT,
    role_id      INTEGER   NOT NULL,
    menu_id      INTEGER   NOT NULL,
    permission   INTEGER   NOT NULL DEFAULT 1,
    active_flag  INTEGER   NOT NULL DEFAULT 1,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE menu
(
    id           INTEGER      NOT NULL AUTO_INCREMENT,
    parent_id    INTEGER      NOT NULL,
    url          VARCHAR(100) NOT NULL,
    name         VARCHAR(100) NOT NULL,
    order_index  INTEGER      NOT NULL DEFAULT 1,
    active_flag  INTEGER      NOT NULL DEFAULT 1,
    created_date TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    auth_id      INTEGER      NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE category
(
    id           INTEGER      NOT NULL AUTO_INCREMENT,
    product_id   INTEGER      NOT NULL,
    name         VARCHAR(100) NOT NULL,
    code         VARCHAR(50)  NOT NULL,
    description  TEXT,
    active_flag  INTEGER      NOT NULL DEFAULT 1,
    created_date TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE product_in_stock
(
    id           INTEGER   NOT NULL AUTO_INCREMENT,
    product_id   INTEGER   NOT NULL,
    qty          INTEGER   NOT NULL,
    active_flag  INTEGER   NOT NULL DEFAULT 1,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);


CREATE TABLE product_info
(
    id           INTEGER      NOT NULL AUTO_INCREMENT,
    code         VARCHAR(50)  NOT NULL,
    name         VARCHAR(100) NOT NULL,
    description  TEXT,
    img_url      VARCHAR(200) NOT NULL,
    active_flag  INTEGER      NOT NULL DEFAULT 1,
    created_date TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    cate_id      INTEGER      NOT NULL,
    PRIMARY KEY (id)
);



CREATE TABLE history
(
    id           INTEGER        NOT NULL AUTO_INCREMENT,
    action_name  VARCHAR(100)   NOT NULL,
    type         INTEGER        NOT NULL,
    product_id   INTEGER        NOT NULL,
    qty          INTEGER        NOT NULL,
    price        DECIMAL(15, 2) NOT NULL,
    active_flag  INTEGER        NOT NULL DEFAULT 1,
    created_date TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);


CREATE TABLE invoice
(
    id           INTEGER        NOT NULL AUTO_INCREMENT,
    code         VARCHAR(50)    NOT NULL,
    type         INTEGER        NOT NULL,
    product_id   INTEGER        NOT NULL,
    qty          INTEGER        NOT NULL,
    price        DECIMAL(15, 2) NOT NULL,
    to_date      TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    from_date    TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    active_flag  INTEGER        NOT NULL DEFAULT 1,
    created_date TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
ALTER TABLE user_role
    ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES user (id);
ALTER TABLE user_role
    ADD CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES role (id);
ALTER TABLE auth
    ADD CONSTRAINT fk_auth_menu FOREIGN KEY (menu_id) REFERENCES menu (id);
ALTER TABLE auth
    ADD CONSTRAINT fk_auth_role FOREIGN KEY (role_id) REFERENCES role (id);
ALTER TABLE product_info
    ADD CONSTRAINT fk_cate FOREIGN KEY (cate_id) REFERENCES product_info (id);
ALTER TABLE product_in_stock
    ADD CONSTRAINT fk_stock_product FOREIGN KEY (product_id) REFERENCES product_info (id);
ALTER TABLE history
    ADD CONSTRAINT fk_history FOREIGN KEY (product_id) REFERENCES product_info (id);
ALTER TABLE invoice
    ADD CONSTRAINT fk_invoice FOREIGN KEY (product_id) REFERENCES product_info (id);