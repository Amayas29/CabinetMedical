
create Table AutreMaladieChronique(
		code number(9),
		codeSeq number(9),
		nom varchar2(25),
		commentaire varchar2(50),
 		CONSTRAINTS cleP PRIMARY KEY (code, codeSeq),
		CONSTRAINTS cleEt1 FOREIGN KEY (code) REFERENCES Patient
);	
	
commit;