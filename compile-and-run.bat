@echo off
echo Building MiniSoft compiler...
call mvn clean package

if %ERRORLEVEL% NEQ 0 (
    echo Maven build failed!
    exit /b %ERRORLEVEL%
)

echo Running MiniSoft compiler on example.ms...
java -jar target\minisoft-compiler-1.0-SNAPSHOT-jar-with-dependencies.jar src\main\resources\samples\example.ms

echo Done.