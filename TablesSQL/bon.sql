
create Table Bon (
		code number(12) PRIMARY KEY,
		codePatient number(9),
		dateConsu varchar2(11),
		montantTotal number(12, 1),
		montantPaye number(12, 1),
		CONSTRAINTS cleEt3 FOREIGN KEY (code) REFERENCES Consultation On Delete Cascade,
		CONSTRAINTS cleEt4 FOREIGN KEY (codePatient) REFERENCES Patient
);
		
commit;