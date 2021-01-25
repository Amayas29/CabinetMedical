
create Table Analyses (
		code number(12),
		codeSeq number(9),
		nom varchar2(40),
		effectue varchar2(1), 
		CONSTRAINTS Pk PRIMARY KEY (code, codeSeq),
		CONSTRAINTS cleEt6 FOREIGN KEY (code) REFERENCES Bilan On Delete Cascade
);
		
commit;