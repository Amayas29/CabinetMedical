
create Table Compte (
        type varchar2(10), 
        utilisateur varchar2(25),
        motDePasse varchar2(25),
        nom varchar2(25),
        prenom varchar2(25),
        CONSTRAINTS cle PRIMARY KEY (type,utilisateur)
);

commit;
