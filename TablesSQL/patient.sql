
create Table Patient(
	code number(9) PRIMARY KEY,
	nom varchar2(25),
	prenom varchar2(25),
	sexe varchar2(5),
	dateNaiss varchar2(10),
	adresse varchar2(30),
	tel varchar2(15),
	email varchar2(30),
	dateInscri varchar2(10),
	drConsu varchar2(10),
	taille varchar2(3),
	poids varchar2(3),
	groupeSang varchar2(4),
	tension varchar2(8),
	maladeChronique varchar2(1),
	tauxGlsm varchar2(5),
	vue varchar2(2),
	antMedi varchar2(100)
);
	
commit;