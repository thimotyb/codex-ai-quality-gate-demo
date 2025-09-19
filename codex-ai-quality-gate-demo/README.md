# Codex AI Quality Gate Demo (Java + Jenkins)

This repo demonstrates a JSON-structured prompt for Code Smells & Security Bugs,
an AI review script, and a Jenkins pipeline that fails on critical findings.

## Quick start
1. Install Node 18+ and Maven 3.9+.
2. `npm i openai glob minimist` in project root.
3. Export `OPENAI_API_KEY` or set a Jenkins credential named `openai-api-key`.
4. Run locally:
   ```bash
   mvn -q test
   node scripts/ai-review.js --model gpt-5-thinking      --prompt-file prompts/java-code-review.json      --source-glob "src/main/java/**/*.java"      --out report/ai-review.json
   cat report/ai-review.json
   ```
5. In Jenkins, create a pipeline with this Jenkinsfile and the credential.
   The build fails if `quality_gate_status` == `FAIL`.

## Intentional issues for teaching
- `UserDao#createUser` uses string concatenation in SQL (SQL Injection).
- `UserDao#runCommand` uses `Runtime.exec` with user input (Command Injection).
- `CryptoUtil` uses a hardcoded AES key and ECB mode.

Use these to show how the model classifies findings with CWE/OWASP tags and severities.
