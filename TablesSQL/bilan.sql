
create Table Bilan (
		code number(12) PRIMARY KEY,
		dateConsu varchar2(11),
		effectue varchar2(1),
		CONSTRAINTS cleEt5 FOREIGN KEY (code) REFERENCES Consultation On Delete Cascade
);
		
commit;