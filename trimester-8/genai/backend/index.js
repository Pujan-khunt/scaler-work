import express from 'express';
import cors from 'cors';
import dotenv from 'dotenv';
import fetch from 'node-fetch';
import * as personas from './personas.js';

dotenv.config();

const app = express();
app.use(cors());
app.use(express.json());

app.post('/api/chat', async (req, res) => {
  try {
    const { messages, persona } = req.body;
    
    // Support both array format (messages[]) and single message format (message: string)
    const chatHistory = messages || (req.body.message ? [{ role: 'user', content: req.body.message }] : null);

    if (!chatHistory || !persona) {
      return res.status(400).json({ error: 'Missing message content or persona selection' });
    }

    let systemPrompt = '';
    try {
      systemPrompt = personas[persona];
      if (!systemPrompt) {
        throw new Error("Selected persona not found in registry");
      }
    } catch (err) {
      return res.status(400).json({ error: `Could not load persona data for ${persona}` });
    }
    const routerKey = process.env.OPENROUTER_API_KEY;

    if (!routerKey) {
        return res.status(401).json({ error: 'API Key not configured in environment' });
    }
    
    // Normalize message format for OpenRouter API
    const formattedMessages = chatHistory.map(m => ({
        role: m.role === 'model' ? 'assistant' : m.role,
        content: m.content || m.text
    }));

    // Make request to OpenRouter
    const response = await fetch("https://openrouter.ai/api/v1/chat/completions", {
      method: "POST",
      headers: {
        "Authorization": `Bearer ${routerKey}`,
        "Content-Type": "application/json",
        "HTTP-Referer": "http://localhost:3000",
        "X-Title": "Persona AI Chatbot",
      },
      body: JSON.stringify({
        "model": "nvidia/nemotron-3-nano-30b-a3b:free", // Used via OpenRouter
        "messages": [
          { "role": "system", "content": systemPrompt },
          ...formattedMessages 
        ]
      })
    });
    
    if (!response.ok) {
       const text = await response.text();
       console.error(`OpenRouter Error ${response.status}:`, text);
       throw new Error(`OpenRouter API error: ${response.status}`);
    }

    const data = await response.json();
    const reply = data.choices[0].message.content;

    res.json({ reply });
  } catch (error) {
    console.error('Error:', error);
    res.status(500).json({ error: error.message || 'Failed to communicate with AI. Please try again.' });
  }
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Server running on port ${PORT}`);
});
