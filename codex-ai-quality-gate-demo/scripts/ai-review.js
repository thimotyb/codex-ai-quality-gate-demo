// scripts/ai-review.js
// npm i openai glob minimist
import fs from 'fs';
import { glob } from 'glob';
import minimist from 'minimist';
import OpenAI from 'openai';

const args = minimist(process.argv.slice(2));
const model = args.model || process.env.MODEL || 'gpt-5';
const promptFile = args['prompt-file'];
const out = args.out || 'report/ai-review.json';
const sourceGlob = args['source-glob'] || 'src/main/java/**/*.java';

if (!process.env.OPENAI_API_KEY) {
  console.error('Missing OPENAI_API_KEY');
  process.exit(2);
}

const client = new OpenAI({ apiKey: process.env.OPENAI_API_KEY });

const files = await glob(sourceGlob);
const bundled = await Promise.all(files.map(async f => ({
  path: f,
  language: 'java',
  content: fs.readFileSync(f, 'utf8')
})));

const prompt = JSON.parse(fs.readFileSync(promptFile, 'utf8'));
prompt.inputs.files = bundled;

const messages = [
  { role: 'system', content: 'You are a rigorous Java code auditor. Return valid JSON only.' },
  { role: 'user', content: JSON.stringify(prompt) }
];

const resp = await client.chat.completions.create({
  model,
  messages,
  response_format: { type: 'json_object' }
});

const text = resp.choices?.[0]?.message?.content || '';
let report;
try {
  report = JSON.parse(text);
} catch (e) {
  console.error('Model did not return valid JSON.');
  console.error(text);
  process.exit(3);
}

fs.mkdirSync('report', { recursive: true });
fs.writeFileSync(out, JSON.stringify(report, null, 2), 'utf8');
console.log(`Wrote ${out}`);
