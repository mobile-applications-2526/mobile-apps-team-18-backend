DROP TABLE IF EXISTS TASKS;
DROP TABLE IF EXISTS EVENTS;
DROP TABLE IF EXISTS DORMS;
DROP TABLE IF EXISTS USERS;

CREATE TABLE USERS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username TEXT NOT NULL,
    email TEXT,
    geboortedatum DATE,
    locatie TEXT,
    password TEXT NOT NULL
); 

CREATE TABLE DORMS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name TEXT NOT NULL,
    code TEXT NOT NULL
);

CREATE TABLE TASKS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title TEXT NOT NULL,
    description TEXT NOT NULL,
    task_type TEXT NOT NULL,
    "date" DATE NOT NULL,
    done BOOLEAN,
    
    assigned_user_id BIGINT NOT NULL,
    created_by_id BIGINT,
    dorm_id BIGINT,

    FOREIGN KEY (assigned_user_id) REFERENCES USERS(id),
    FOREIGN KEY (created_by_id) REFERENCES USERS(id),
    FOREIGN KEY (dorm_id) REFERENCES DORMS(id)
);

CREATE TABLE EVENTS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name TEXT NOT NULL,
    description TEXT NOT NULL,
    "date" DATE NOT NULL,
    organizer_id BIGINT NOT NULL,
    done BOOLEAN,
    
    dorm_id BIGINT,

    FOREIGN KEY (organizer_id) REFERENCES USERS(id),
    FOREIGN KEY (dorm_id) REFERENCES DORMS(id)
);