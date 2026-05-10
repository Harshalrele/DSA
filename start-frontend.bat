@echo off
REM Move into the frontend folder.
cd /d "%~dp0frontend"

REM Install React/Vite packages if node_modules is missing.
npm install

REM Start the website on http://localhost:3000.
npm run dev
