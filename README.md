# Codex AI Quality Gate – Jenkins in Docker

Questo pacchetto contiene una demo completa per mostrare come integrare un controllo AI (Code Smells & Security Bugs) dentro Jenkins, con quality gate che blocca la build.

## Contenuto
- **codex-ai-quality-gate-demo/**: progetto Maven Java 17 con codice volutamente vulnerabile/smelly, prompt JSON, script Node.js e Jenkinsfile.
- **Dockerfile**: immagine Jenkins LTS (JDK17) con Node, Maven, plugin base e demo copiati.
- **plugins.txt**: lista plugin installati.
- **casc.yaml**: configurazione Jenkins Configuration-as-Code (crea la credenziale `openai-api-key`).
- **init.groovy.d/01-seed.groovy**: script Groovy che genera un job pipeline “AI Quality Gate” basato sul Jenkinsfile della demo.
- **docker-compose.yml**: avvio semplificato del container Jenkins con volume persistente.

## Requisiti
- Docker + Docker Compose v2
- Una chiave API valida OpenAI (`OPENAI_API_KEY`)

## Avvio rapido
1. Esporta la tua chiave OpenAI come variabile d’ambiente:
   ```bash
   export OPENAI_API_KEY=sk-************************
   ```
   oppure su PowerShell:
   ```powershell
   $env:OPENAI_API_KEY="sk-************************"
   ```
2. Builda e avvia Jenkins:
   ```bash
   docker compose build
   docker compose up -d
   ```
3. Apri [http://localhost:8080](http://localhost:8080). Non c’è wizard iniziale: trovi già pronto il job **AI Quality Gate (Codex Demo)**.
4. Esegui il job: il Jenkinsfile lancia Maven test, poi lo script `scripts/ai-review.js` che invoca il modello con il prompt JSON. Se ci sono problemi CRITICAL/BLOCKER, la build fallisce.

## Note
- I pacchetti npm necessari (`openai`, `glob`, `minimist`) sono installati globalmente nell’immagine.
- La credenziale Jenkins `openai-api-key` viene creata automaticamente leggendo il valore dall’ambiente `OPENAI_API_KEY`.
- Il progetto demo include vulnerabilità intenzionali (SQL injection, command injection, chiave AES hardcoded, ecc.) utili per la didattica.
- I report AI sono archiviati in `report/ai-review.json` dentro Jenkins.

## Personalizzazioni
- Per aggiornare il demo, modifica la cartella `codex-ai-quality-gate-demo/` e ricostruisci l’immagine.
- Puoi sostituire la demo locale con un repo Git modificando lo script seed in `init.groovy.d/01-seed.groovy`.
