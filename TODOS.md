# TODOS

- [ ] Løse hartbeat problematikken på en elegant måte (med Java Sockets som utgangspunkt)
- [x] Skrive en tekstlig kommunikasjonsprotokoll med linjeskift som endetegn (som og inkluderer hartbeat sendingene)
- [ ] Skrive server+client GUI (fxml) og tilhørende java klasser.
- [ ] Lage et diagram (UML?) for oppbygging av software lagene (kommunikasjon(Sockets), backend, frontend).
- [ ] Skriver dialogbokser for port og ipaddresse for client og server (fxml + java). (HC)
- [ ] Skrive en (innebygget) dialogboks for feilmeldinger (fxml + java).
- [ ] Skrive en protollparser (clientsiden) for tekststrengene "0;0;0\n", "0;1;0\n" ("{0,1};{0,1};{0,1}\n")(S)
- [ ] Skrive en protollgenerator (serversiden) som skaper datastrengene til klientene.
- [ ] Lage en statisk klasse (singleton) som nåværende datasignal for sockettrådene. (hp)
- [ ] Sikre at sockettrådene til serverkoden (poc) fungerer tilstrekkelig.
- [ ] Lage logikk for "automodus" (dynamisk)
- [ ] Skrive en logger for server og klientsiden (ingen lagring til fil, kun minne)
- [ ] Sørge for at klienter som kobler til får siste gyldige datasignal.
- [ ] Undersøke hvordan (om) javatråder kan fingres (for å reagere på events) (evt en liste med tråder)
- [ ] Skrive dokumentasjon
- [ ] Wireshark'e
