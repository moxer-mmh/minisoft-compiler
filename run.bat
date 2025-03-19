@echo off
echo Checking for Maven installation...
where mvn >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo Maven not found. Please use compile-and-run.bat instead:
    echo .\compile-and-run.bat %1
    exit /b 1
)

echo Building minisoft-compiler using Maven...
call mvn clean package
if %ERRORLEVEL% NEQ 0 (
    echo Build failed! See errors above.
    exit /b %ERRORLEVEL%
)
echo Build successful!

if "%~1"=="" (
    echo Usage: run.bat [source-file]
    echo Example: run.bat .\src\main\resources\samples\example.ms
    exit /b 1
)

echo Running compiler on %1...
java -jar target\minisoft-compiler-1.0-SNAPSHOT.jar %1
