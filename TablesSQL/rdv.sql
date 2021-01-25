
create table Rdv (
	codePatient number(9),
	dateRdv varchar2(11),
	numero number(3),
	CONSTRAINT cleP4 PRIMARY KEY (dateRdv, numero),
	CONSTRAINT cleEt11 FOREIGN KEY (codePatient) REFERENCES Patient On Delete Cascade
);

commit;