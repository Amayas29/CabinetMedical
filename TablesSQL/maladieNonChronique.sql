
create Table MaladieNonChronique(
		code number(12),
		codeSeq number(9),
		nom varchar2(25),
		commentaire varchar2(30),
 		CONSTRAINTS cleP1 PRIMARY KEY (code, codeSeq),
		CONSTRAINTS cleEt8 FOREIGN KEY (code) REFERENCES Consultation On Delete Cascade
);
		
commit;