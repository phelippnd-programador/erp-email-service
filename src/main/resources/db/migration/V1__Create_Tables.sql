create table email (
id int not null primary key auto_increment,
email_from varchar(45),
email_to varchar(45),
subject varchar(124),
texto TEXT,
email_mensagem_retorno TEXT,
status varchar (10),
send_date datetime not null default CURRENT_TIMESTAMP
);