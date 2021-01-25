
create Table MaladiesChroniques (
		code number(9) PRIMARY KEY,
		cardio varchar2(9),
		endocrin varchar2(7),
		orl varchar2(8),
		rhumato varchar2(11),
		infectChro varchar2(7),
		CONSTRAINTS cleEt FOREIGN KEY (code) REFERENCES Patient
);
		
commit;