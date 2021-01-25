
create Table Ordannance (
		code number(12) PRIMARY KEY,
		dateConsu varchar2(11), 
		CONSTRAINTS cleEt7 FOREIGN KEY (code) REFERENCES Consultation On Delete Cascade
);
		
commit;