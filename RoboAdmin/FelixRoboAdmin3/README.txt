Luca Bettelli - 24 marzo 2010

RoboAdmin README File - Versione 1.4


0.PREMESSA
==========

La presente versione di RoboAdmin è stata testata su Windows e Linux. Alcuni bundle
di comunicazione usano codice nativo e potrebbero non funzionare su altre
piattaforme.


1.ISTRUZIONI DI INSTALLAZIONE
=============================

1.1.CONTENUTO DEL FILE ZIP
- FelixRoboAdmin3:
  Cartella contenente il progetto RoboAdmin (librerie, sorgenti e file di build
  per NetBeans).
- bundlebuild.bat:
  Script Windows per la creazione dei bundle jar.
- bundlebuild.sh:
  Script Linux per la creazione dei bundle jar.
- Caratteristiche plug-in.docx:
  Tabella comparativa delle funzionalità implementate per ogni bundle.
- localhost.sql:
  Dump del data base "roboadmindb" che può essere importato da PHPMyAdmin.

1.2.IMPORTAZIONE DEL PROGETTO IN NETBEANS
Dal menù File -> Open Project... selezionare la directory FelixRoboAdmin3. Le
librerie sono già incluse nel progetto: porvare con un build dell'intero progetto.

1.3.DIRECTORY DEL PROGETTO
- bin:
  Contiene il jar di felix, può essere usato via riga di comando per l'avvio di
  Felix.
- build:
  Contiene le classi compilate suddivise per package. La gestione di questa
  directory è automatica in NetBeans.
- bundle:
  Contiene i file jar corrispondenti ai bundle che verranno caricati da Felix.
- conf:
  Contiene il file config.properties indispensabile per la configurazione di
  Felix.
- dist:
  Directory creata automaticamente da NetBeans contenente il jar
  dell'applicazione e le librerie.
- doc:
  Contiene la documentazione del progetto.
- felix-cache:
  Directory gestita da Felix; contiene i bundle caricati da Felix nella sua
  ultima esecuzione e alcune informazioni utili al ripristino di Felix.
- lib:
  Contiene le librerie usate dal progetto. E' stata suddivisa in base alle
  librerie usate da ciascun bundle di comunicazione.
- nbproject:
  Contiene i file con le proprieta del progetto e di build usati da NetBeans.
- properties:
  Contiene i file di proprietà per i bundle di RoboAdmin.
- src:
  Contiene i sorgenti dei bundle di RoboAdmin.

1.4.FILES DI CONFIGURAZIONE
A parte il file di configurazione di Felix, situato nella directory conf, i file
di configurazione dei bundle si trovano tutti in properties.
Il file config.properties con la configurazione di Felix. Al suo interno è
necessario specificare la proprietà org.osgi.framework.system.packages.extra per
indicare i packages usati nei bundle di RoboAdmin in modo che Felix generi
correttamente il classpath (non è sufficiente includere le librerie nel progetto
NetBeans).
La proprietà org.osgi.framework.storage.clean indica quando viene svuotata la
cache ed è impostata a onFirstInit dal momento che in questa fase di
progettazione i bundle cambiano frequentemente.
La proprietà felix.auto.start.1 contiene tutti i bundle che devono essere
attivati all'avvio di Felix: i bundle di comunicazione sono avviati
dall'interprete e NON vanno indicati qui.
MySqlDB.properties contiene le proprietà relative al bundle del Data Base. I
valori delle proprietà dipendono strettamente dalla configurazione del data base
che si intende utilizzare. E' anche possibile usare un data base remoto
specificando host e porta.
RoboAdmin.properties contiene le proprietà dell'interpete. Qui si possono
specificare i comandi riconosciuti dall'interprete per le varie funzioni
gestite internamente dall'interprete, il nome del file di log e le informazioni
del destinatario degli avvisi via mail.
Gli altri file di proprietà sono specifici per ogni servizio di comunicazione.
In ciascuno è necessario specificare il nome utente e la password per il login
al servizio. Talvolta è possibile indicare a quale server e porta ci si vuole
connettere.
Per il servizio IRC i canali a cui ci si connette all'avvio vanno inseriti nel
file IrcChannelList.txt (un canale per ogni linea).

1.5.CONFIGURAZIONE DEL DATA BASE
Il database MySQL installato assieme al pacchetto XAMPP non ha inizialmente una
password di root. Per colmare questa lacuna si può usare l'interfaccia di
configurazione PHPMyAdmin (deve essere attivo il web server Apache), di default
raggiungibile all'URL http://localhost/phpmyadmin. E' possibile impostare la
password di root dalla scheda Privilegi, cliccando sul link Modifica Privilegi
nella riga dell'utente root. La password usata deve essere indicata in
MySqlDB.properties per poter accedere al DB da RoboAdmin.
Per continuare ad utilizzare PHPMyAdmin bisogna inserire la nuova password anche
nel file config.inc.php nella directory xampp/phpMyAdmin.
Se il data base roboadmindb non esiste può essere importato direttamente dal
file localhost.sql incluso nello zip.
Il data base è composto di quattro tabelle: log, mp, users, accept.
La nuova tabella accept, in particolare, specifica a quali account si vuole
autorizzare l'accesso a RoboAdmin per ogni protocollo usato.

1.6.NOTE SULLE LIBRERIE IN CODICE NATIVO
La libreria del protocollo Skype per Java è un wrapper per le librerie in codice
nativo fornite agli sviluppatori da Skype stessa. Le librerie si interfacciano al
client Skype, che deve quindi essere installato, funzionante e connesso al
servizio. Il wrapper java è multipiattaforma e non necessita modifiche al
passaggio da un sistema operativo all'altro.
All'interno della directory lib/skype si trovano, oltre ai due file jar, le
librerie specifiche per windows, linux e mac. Per poterle usare vanno inserire
in una directory già presente nel path di sistema o nella working directory del
progetto (modificabile nella finestra Project Properties di NetBeans).
Anche per il protocollo OSCAR è possibile procedere allo stesso modo: in
lib/oscar sono presenti le librerie dll per windows e le librerie per linux (vanno
copiate in una delle directory del path).

1.7.ULTERIORI DETTAGLI
Per maggiori informazioni fare riferimento al documento PDF inserito nella directory
'doc' del progetto.


2.AGGIORNAMENTI RISPETTO ALLA VERSIONE 1.3
==========================================

- Aggiornate le librerie per i bundle MSNP, Yahoo, Skype, IRC.
- Adesso l'eliminazione dei contatti nel bundle Yahoo funziona correttamente.
- Eliminati i metodi "pericolosi" dal bundle data base.
- Inserita tesi nella documentazione del progetto.
- Aggiunti i file per la libreria OSCAR su Linux.


3.COSA MANCA NEL CODICE ATTUALE
===============================

- Mancano i log nei bundle di comunicazione. Scrive solo su standard output.


4.PROPOSTE DI ESTENSIONE
========================

- Inserire un metodo richiamabile dall'interprete per mettere uno stato occupato
  o assente a RoboAdmin quando un amministratore si è loggato.
- Valutare la possibilità di implementare il trasferimento di file.
- In base a cosa RA sceglie i meeting point da avviare? Usare dei parametri
  salvati nel DB? Quali parametri?
- Come avviare e spegnere servizi di comunicazione via chat? Nuovi comandi per
  l'interprete?
- I provider dei servizi di chat possono bloccare comportamenti tipici degli
  spammer consentendo solo un certo numero di messaggi al mese (al giorno,
  all'ora, al minuto...). Informarsi a quanto ammonta questo limite per ogni
  servizio e gestire da RoboAdmin il comportamento nel caso si stia per
  raggiungere il limite (invitare l'admin a passare ad un altro servizio?).
- Raggruppare più linee di risposta in un unico messaggio allontanerebbe il
  limite antispam dei servizi di comunicazione... ma quanto posso farli grandi i
  messaggi?
- Chat diverse su thread diversi per evitare attese: forse si può fare, ma
  dipende dalla libreria... e non tutte sono thread safe!
- L'istallazione di un bundle è macchinosa e invasiva: si riesce ad avviare un
  bundle di comunicazione senza modificare il file di configurazione di Felix?
  Come caricare un package senza modificare quel file? Bisognerebbe modificare
  il classpath in Felix a run time... si può fare?
- Si può usare un sistema di log uniforme che scriva su DB, su file e su stdout
  contemporaneamente? Invocando una sola funzione potrei avere un bel log
  dettagliato (con nome della classe, data e ora, priorità, messaggio ed
  eventualmente uno stack trace per le eccezioni).
