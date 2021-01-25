
create Table Resultats (
		code number(12),
		codeSeq number(9),
		resultat varchar2(40),
		CONSTRAINTS Pkres PRIMARY KEY (code, codeSeq),
		CONSTRAINTS cleEt6res FOREIGN KEY (code) REFERENCES Bilan On Delete Cascade
);
		
commit;