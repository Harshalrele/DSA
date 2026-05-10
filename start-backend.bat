@echo off
REM Move into the backend folder.
cd /d "%~dp0backend"

REM Start Spring Boot on http://localhost:8080.
call mvnw.cmd spring-boot:run
