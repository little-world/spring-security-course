# RSA

## Theorie
Laten we eerst zelf eens een RSA encryptie doorvoeren. Het principe werkt zo:

1. neem twee grote (maar het werkt ook met kleine) priemgetallen p,q
2. De modulus is n=pq
3. Kies een E zdd

	( E<p.q ) & ( ggd(E,(p-1)(q-1))=1 )

      maw E en (p-1)(q-1) zijn relatief priem (bezitten geen gemeenschappelijke factoren). Omdat (p-1)(q-1) even is, is E dus altijd oneven (waarom?). De E heet de publieke exponent
4. Bereken de multiplicatieve inverse D van E, dus bepaal D zdd

	DE mod (p-1)(q-1)=1

      of te wel DE-1 is deelbaar door (p-1)(q-1)
      Hoe doe je dat? Wel je zoekt een gehele positieve X die voldoet aan

	D=(X(p-1)(q-1)+1)/E

      Als je X laat oplopen komt je vanzelf een deelbare breuk tegen. Dat duurt eindig want natuurlijk geldt D<pd. De D heet de private exponent.
5. Je kan nu encrypten op de volgende manier

	G=B^E mod pq , met B<pq

      waarbij B je boodschap is en G de geheime boodschap, Encrypten mag iedereen, dus je publieke sleutel is (E, pq)
6. Decrypten gaat zo:

	B=G^D mod pq

      natuurlijk is G<pq. Decrypten mag je alleen zelf, je privesleutel is (D,pq)
7. De andere getallen in de key boven zijn

	exponent1    = D mod (p-1)
   	exponent2    = D mod (q-1)
   	coefficient  = (inv q) mod p


En dienen alleen om de decryptie gemakkelijker te maken. (bij encryptie mag je die helemaal niet weten)



## Voorbeeld

Het leuke is dat dit een symmetrisch proces is. Als iemand mij een bericht wil sturen, dat gebruikt hij de public key om het de coderen, en ik mijn private key om het te decoderen. Als ik iemand een bericht wil sturen en ik wil de ontvanger ervan overtuigen dat het bericht van mij komt, dan maak ik van mijn bericht een hash die ik met mijn privesleutel codeer. De ontvanger kan vervolgens deze gecodeerde hash met de publieke sleutel decoderen (dat kan iedereen) zelf de hash bepalen en vaststellen dat deze identiek zijn.

Laten we een voorbeeld nemen, Stel je wilt bytes encrypten met RSA. Er zijn 256 mogelijkheden, dus we moeten pq>256. Neem

1. priem 1: p=17 dan p-1=16
	 priem 2: q=23 dan q-1 22
2. pq=17.23=391
	 (p-1)(q-1)=16.22=352
3. ontbinden van 352 levert 352=2.2.2.2.2.11     
	` [onder linux: factor 352 ]`    
	 We kunnen nu een E kiezen zdd E<391 en die
	 geen priemfactoren gemeenschappelijk heeft met 352
	 bijvoorbeeld E=3.7.13=273
4. Nu moeten we opzoek naar een D en X die voldoen aan
	 D=(352X+1)/273
	 Als we dit benaderen dan zien we dat
	 273D~352X => 280D~350X => 4D~5X
	 Na wat proberen vinden we een oplossing
	 D=49, X=38 (niet interessant).
	 Even testen: 49.273 mod 352 = 13377 mod 352 = 1     
	` [onder linux: echo "(49*273)%352" | bc ]`     
5. Encrypten met de publieke key, neem bijvoorbeeld
	 de letter A (ascii 65), we krijgen dan met B=65
	 G=65^273 mod 391 = 286     
	` [onder linux: echo "(65^273)%391" | bc ]`
6. Decrypten met de private key,
	 G=286^49 mod 391 = 65 Jaa het klopt!     
	` [onder linux: echo "(286^49)%391" | bc ]`

Hiermee hebben een afbeelding gemaakt van de getallen (0..390)->(0..390). Nadat we wat tekst hebben geprocessed, is die afbeelding gemakkelijk te ontcijferen. Normaal nemen we de priemfactoren echter heel erg groot, zdd de modulus 1024 bit wordt. De afbeelding is dan heel erg fors. (0..2^1024-1)->(0..2^1024-1). De enige hoop is dus uit de modulus de twee priemgetallen te halen. Echter, dit is een onbegonnen klus, omdat er geen goed algoritme voor is. Het blijft proberen. 