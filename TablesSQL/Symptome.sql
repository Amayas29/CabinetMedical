
create Table Symptome (
		code number(12),
		codeSeq number(9),
		description varchar2(100),
 		CONSTRAINTS cleP2 PRIMARY KEY (code, codeSeq),
		CONSTRAINTS cleEt9 FOREIGN KEY (code) REFERENCES Consultation On Delete Cascade
);
		
commit;