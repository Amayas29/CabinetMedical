
create Table Consultation (
		codeConsu number(12) PRIMARY KEY,
		codePatient number(9),
		nomMedcin varchar2(25),
		commentaire varchar2(300),
		CONSTRAINTS cleEt2 FOREIGN KEY (codePatient) REFERENCES Patient On Delete Cascade
);
		
commit;