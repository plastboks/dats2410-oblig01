# Status
Dokument for statusoppdateringer

## 2016-02-21 (generell oversikt)

### Protokoll
Kommunikasjonsprotokollen mellom server og klient er bestemt til å være en veldig enkel
tekstlig linjeskift-protokoll (endetegn). Selve payloaden er definert til å være:

* `"X;Y;Z"`

Hvor X,Y, og Z kan være enten 0 eller 1 (0=off,1=on). X = grønn, Y = gul, og Z = rød.
Skilletegnet er satt til semikolon.

Mer data i payloaden er det ikke per overføring. Det er med andre ord tiltenkt at klienten
ikke selv vet hvor lenge en status varer. Den gjør enkelt og greit det den for beskjed om
til neste overføring kommer fra serveren.

### Hartbeat

Etter litt testing fram og tilbake, viser det seg at Socket grensesnittet til Java ikke er
så godt å bruke til keepalive/isalive testing. Mister klienten kommunikasjon med serveren,
eller motsatt vil ikke disse Socket'ene nødvendigvis bry seg nevneverdig. Det er
problematisk. En løsningen på dette vil være å implementere et hartbeat som server og
klient utveksler med jevne mellomrom. Protokoll payloaden har da to datatyper; en for
signaloverføring (nevnt over), og en for hartbeat. Pr nå er ikke nøkkelordet til hartbeat
payloaden definert, men noe så enkelt som "ping", "tick" o.l er tilstrekkelig. Det er
heller ikke satt noe fast intervall på hvor ofte dette skal forekomme. En gang i sekundet
kan være en god start, men det er viktig at det taes med i betraktningen at det ikke blir
en unødig menge av data på nettverket.

### Funksjonalitetsbeskrivelse

Sluttproduktet er ment å forme to jar filer for kjøring. En server.jar og en klient.jar.
Logikken bak dette er åpenbar og selvforklarende.

#### Logging

Pr nå er det ment at både klient og server skal logge alle overføringer seg i mellom 
(minus hartbeat). Dette skal vises i en egen fane i GUIet.

#### Klient.jar (GUI - JavaFX)

Når klienten startes skal en dialogboks dukke opp, og be brukeren taste inn IP adresse og
port nummer til serveren. Etter bruker har trykket "ok", må disse verdiene testes og 
en socketforbindelse opprettes. Feiler dette må klienten enten avslutte med en passende
dialogboks, eller be brukeren taste inn nye verdier.

Når kommunikasjonen er etablert, vil ikke klienten ha annen funksjonalitet enn å vise
nåværende status og avslutt.

#### Server.jar (GUI - JavaFX)

Serveren må på samme måte som klienten starte med en dialogboks som ber brukeren taste inn
portnummer. Dette portnummeret må testes og en socket må settes opp. Skulle portnummeret
brukeren taster inn være problematisk, må en dialogboks dukke opp som informerer om dette.
Programmet avsluttes, eller bruker prøver et annet portnummer.

Når serveren har startet opp vil et grensesnitt tilsvarende vedlagte skjermbilder (mockups)
vises. Det er ikke tiltenkt at serveren kan startes og stoppes utover dette.

Til høyre i GUIet vises en liste over aktive tilknyttede klienter.

##### Servermodus 1: Automodus

En av de to modusene serveren kan stå i er "Auto". Dette moduset vil simulere et vanlig
lyskryss. Serveren vil da på engenhånd sende ut signaler til klientene i et dynamisk
intervall. Det er viktig at dette moduset sender gyldige signaler i riktig rekkefølge.

Gyldige signaler i rekkefølge er:
* Tekstlig: {Rød, Rød+Gul, Grønn, Gul, Rød}
* Protkoll: {"0;0;1", "0;1;1", "1;0;0", "0;1;0", "0;0;1"}

##### Servermodus 2: Manuell

I det andre moduset "manuell" er det ikke så mye fokus på riktig og galt. Dette moduset
er ment å ettergi det brukeren ber om, med blinking som en tilleggsfunksjonalitet.

### Kodestruktur

Vedlagte skjermbilder IMG_20160217_093634.jpg og IMG_20160217_093642.jpg er referanse.

#### Server - GUI

Serverkoden vil få en normal Controller+View struktur med FXML som view filer. Utover
hovedvinduet må dialogbokser og alertbokser kodes opp. En løsning er å benytte en nyere
versjon av JDK8, hvor dette er innebygget. Her må det taes en avgjørelse om vi skal lage
dette på egenhånd, eller forvente at sensor har en nyere JDK.

#### Server - ServerDispatcher.java (maks en instans - singleton)

Som en del av serveren vil det være en ServerDispatcher.java klasse. Denne klassens
eneste oppgave er å holde styr på SocketThread.java trådene. Når en ny klient ber om
tilknytting vil det være denne klassen som håndterer oppstart av en ny SocketThread
instans.

#### Server - SocketThread.java

Det er i denne klassen selve kommunikasjonen med klienten forekommer. Det vil være en
instans per klient av denne klassen. ServerDispatcher.java innholder en liste over disse
instansene.

#### Server - MessageHandler.java (maks en instans - singleton)

For å ikke oppholde ServerDispatcher.java med unødig ansvar og tidsforbruk er all payload
behandling ment å håndteres av MessageHandler.java. Det er MessageHandler sammmen med
ProtocolGenerator som sørger for integriteten til payloaden.

To løsninger på meldingshåndtering er:

##### 1

Når hendelse oppstår vil den varsle ServerDispatcher.java. ServerDispacher.java sender
videre dataene til MessageHandler.java, og etter "pinger" alle trådene om å hente ny
informasjon fra MessageHandler.java

##### 2

Når en hendelse oppstår vil den melde ifra til MessageHandler.java om å sette inn en ny
payload. Når MessageHandler har gjort dette vil den videre informere ServerDispatcher om
å si ifra til alle trådene, som igjen ber om payloaden fra MessageHandleren.

#### Server - Events

Events kan oppstå direkte fra GUIet (hovedsaklig i manuelt modus), eller fra en Auto.java?
klasse som utfører en simulasjon.


#### Klient - GUI

Klientkoden vil være veldig enkel i forhold til serverkoden. Her vil det på lik linje
eksistere enkel kode for JavaFX delen (controller+view)


#### Klient - SocketThread (maks en instans)

Ut over GUI tråden, vil klienten ha en sockettråd. Denne tråden står for å hente
innkommende overføringer, og sende de til Protocolparseren.

#### Klient - ProtocolParser.java (maks en instans)

Klienten vil ha en "protocolparser" for tolking av innkommende payload. Når, og om, 
protokollparseren er ferdig med parsingen vil den sende oppdateringer direkte til guiet.
