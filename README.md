# redes-t1000
Tarea 1 | Redes de Computadores (ILI-256) 2015-1  | UTFSM Santiago

En	la	ingeniería	 de	 software	 se	 denomina	 aplicación	 web	 a	 aquellas	 herramientas que	los	usuarios	pueden	utilizar	ccediendo	a	un servidor	web a	través de Internet o	de	una intranet mediante	un navegador (Fuente:	Wikipedia).	
Ustedes	 han	 sido contratados	 por	 la	 corporación	 Cloud	 Productions	 para	 diseñar una	 solución	 propietaria	 de	 sus	 servidores,	 con	 los	 cuales	 planean	 servir	 sus	aplicaciones	web.
Como	 requerimiento	 para	 poder	 llevar	 a	 cabo	 este	 propósito,	 se	 realizará	 la		implementación	de	un	servidor,	el	cual	escuchará	por	medio	de	un	puerto	a	elección	que	 debe	 ser	 configurable	 y	 responder	 con	 páginas	 de	 bienvenida	 y	 acerca	 de	 la	empresa	 (estáticas).	 Pudiendo	 además	 brindar	la	 posibilidad	 cualquier	 	 cliente	 de	
enlazar	con	el	servidor	para	recibir	las	respuestas	de	éste. Cada	URI	que	el	servidor	deba	responder	debe	ir	asociado	a	un	handler,	esto	es	una	clase	que	se	encarga	de	procesar	el	HTTP	Request,	y	devolver	un	HTTP	Response.

Cabe	 destacar la	 diferencia	 entre	 una	 URL	 y una	URI.
URL	:	estos	son	los	strings	que	tienen	el	protocolo	como	parte	de	su	estructura,	ej:
• http://www.google.com
• ftp://192.168.1.131

URI: son	 una	 compacta	 secuencia	 de	 caracteres	que	identifica	una	recurso	físico	o	abstracto.

La	diferencia	que	uno	debe	tener	en	cuenta	es	que	si	hacemos	un	paralelo	con	una	persona	viviendo	en	su	hogar,	URL	sería	su	dirección	o	como	ubicarlo,	mientras	que	la URI	seria	su	nombre	o	identificador	único.	