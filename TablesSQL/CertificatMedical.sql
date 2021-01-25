
create Table CertificatMedical (
		code number(12) PRIMARY KEY ,
		dateConsu varchar2(11),
		obs varchar2(300),
		typeC varchar2(30),
		CONSTRAINTS cleEt10 FOREIGN KEY (code) REFERENCES Consultation On Delete Cascade
);
		
commit;