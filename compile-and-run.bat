@echo off
setlocal enabledelayedexpansion

set BASEDIR=%CD%
set CLASSPATH=.
set TARGET_DIR=target\classes
set LIB_DIR=lib
set ANTLR_JAR=%LIB_DIR%\antlr4-runtime-4.10.1.jar
set ANTLR_TOOL_JAR=%LIB_DIR%\antlr4-4.10.1-complete.jar
set GRAMMAR_FILE=%BASEDIR%\src\main\antlr4\com\minisoft\MiniSoft.g4
set SRC_DIR=src\main\java
set PARSER_PACKAGE=com.minisoft.parser
set PARSER_DIR=%SRC_DIR%\com\minisoft\parser
set GENERATED_DIR=target\generated-sources\antlr4
set TMP_DIR=tmp

echo Preparing directories...
rem Clean any previous build artifacts
if exist %TARGET_DIR% rmdir /s /q %TARGET_DIR%
if exist %PARSER_DIR% rmdir /s /q %PARSER_DIR%
if exist %TMP_DIR% rmdir /s /q %TMP_DIR%
if exist %GENERATED_DIR% rmdir /s /q %GENERATED_DIR%

rem Create necessary directories
mkdir %TARGET_DIR%
mkdir %PARSER_DIR%
mkdir %TMP_DIR%
mkdir %GENERATED_DIR%

if not exist %LIB_DIR% mkdir %LIB_DIR%

if not exist %ANTLR_JAR% (
    echo Downloading ANTLR runtime...
    powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/org/antlr/antlr4-runtime/4.10.1/antlr4-runtime-4.10.1.jar' -OutFile '%ANTLR_JAR%'"
    if !ERRORLEVEL! NEQ 0 (
        echo Failed to download ANTLR runtime.
        exit /b !ERRORLEVEL!
    )
)

if not exist %ANTLR_TOOL_JAR% (
    echo Downloading ANTLR tool...
    powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/org/antlr/antlr4/4.10.1/antlr4-4.10.1-complete.jar' -OutFile '%ANTLR_TOOL_JAR%'"
    if !ERRORLEVEL! NEQ 0 (
        echo Failed to download ANTLR tool.
        exit /b !ERRORLEVEL!
    )
)

set CLASSPATH=.;%ANTLR_JAR%;%ANTLR_TOOL_JAR%

rem Check if java is in PATH
where java >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo Error: Java not found in PATH. Please make sure Java is installed and added to your PATH.
    exit /b 1
)

echo Step 1: Generating parser from grammar...
if exist %GRAMMAR_FILE% (
    echo Grammar file found at: %GRAMMAR_FILE%
    
    rem Use direct Java command with full paths
    echo Running ANTLR tool to generate parser...
    java -jar "%BASEDIR%\%ANTLR_TOOL_JAR%" -visitor -no-listener ^
         -Dlanguage=Java -package %PARSER_PACKAGE% ^
         -o "%BASEDIR%\%PARSER_DIR%" "%GRAMMAR_FILE%"
    
    if !ERRORLEVEL! NEQ 0 (
        echo Parser generation failed! Error code: !ERRORLEVEL!
        exit /b !ERRORLEVEL!
    )
    
    rem Verify files were generated
    echo Checking for generated files in %PARSER_DIR%...
    if not exist "%PARSER_DIR%\MiniSoftLexer.java" (
        echo WARNING: MiniSoftLexer.java not found
        echo Directory contents:
        dir /b "%PARSER_DIR%"
        
        echo Checking if files were generated in a subdirectory...
        if exist "%PARSER_DIR%\src" (
            echo Found potential parser files in subdirectory, moving them up...
            move "%PARSER_DIR%\src\*.java" "%PARSER_DIR%\"
            rmdir /s /q "%PARSER_DIR%\src"
        ) else (
            echo No parser files generated. Trying direct file copy approach...
            echo ------------------------------------------------------
            echo Manual ANTLR command execution - copy and paste to run:
            echo java -jar "%BASEDIR%\%ANTLR_TOOL_JAR%" -visitor -no-listener -o "%BASEDIR%\%PARSER_DIR%" -package %PARSER_PACKAGE% "%GRAMMAR_FILE%"
            echo ------------------------------------------------------
            exit /b 1
        )
    )
    
    echo Parser files generated successfully.
    
    rem Extract ANTLR runtime classes
    echo Extracting ANTLR runtime classes...
    mkdir %TARGET_DIR%\org\antlr\v4\runtime
    mkdir %TMP_DIR%\antlrlib
    cd %TMP_DIR%\antlrlib
    jar xf ..\..\%ANTLR_JAR%
    xcopy /E /Y org\antlr\v4\runtime\* ..\..\%TARGET_DIR%\org\antlr\v4\runtime\
    cd ..\..\
) else (
    echo Grammar file not found: %GRAMMAR_FILE%
    exit /b 1
)

echo Step 2: Setting up build environment...
set CLASSPATH=%CLASSPATH%;%TARGET_DIR%;%SRC_DIR%

echo Step 3: Compiling Java files...

rem First, compile ANTLR generated files individually
echo Compiling parser files...
if exist %PARSER_DIR%\MiniSoftLexer.java (
    echo Compiling lexer...
    javac -d %TARGET_DIR% -cp %CLASSPATH% %PARSER_DIR%\MiniSoftLexer.java
    if !ERRORLEVEL! NEQ 0 (
        echo Failed to compile lexer
        exit /b !ERRORLEVEL!
    )
)

if exist %PARSER_DIR%\MiniSoftParser.java (
    echo Compiling parser...
    javac -d %TARGET_DIR% -cp %CLASSPATH% %PARSER_DIR%\MiniSoftParser.java
    if !ERRORLEVEL! NEQ 0 (
        echo Failed to compile parser
        exit /b !ERRORLEVEL!
    )
)

if exist %PARSER_DIR%\MiniSoftBaseVisitor.java (
    echo Compiling visitor...
    javac -d %TARGET_DIR% -cp %CLASSPATH% %PARSER_DIR%\MiniSoftBaseVisitor.java
    if !ERRORLEVEL! NEQ 0 (
        echo Failed to compile visitor
        exit /b !ERRORLEVEL!
    )
)

if exist %PARSER_DIR%\MiniSoftVisitor.java (
    echo Compiling visitor interface...
    javac -d %TARGET_DIR% -cp %CLASSPATH% %PARSER_DIR%\MiniSoftVisitor.java
    if !ERRORLEVEL! NEQ 0 (
        echo Failed to compile visitor interface
        exit /b !ERRORLEVEL!
    )
)

rem Now compile the rest in the correct order
echo Compiling helper classes...
javac -d %TARGET_DIR% -cp %CLASSPATH% %SRC_DIR%\com\minisoft\symboltable\SymbolType.java
javac -d %TARGET_DIR% -cp %CLASSPATH% %SRC_DIR%\com\minisoft\symboltable\Symbol.java
javac -d %TARGET_DIR% -cp %CLASSPATH% %SRC_DIR%\com\minisoft\symboltable\SymbolTable.java
javac -d %TARGET_DIR% -cp %CLASSPATH% %SRC_DIR%\com\minisoft\error\ErrorHandler.java
javac -d %TARGET_DIR% -cp %CLASSPATH% %SRC_DIR%\com\minisoft\codegen\Quadruple.java

echo Compiling CodeGenerator...
javac -d %TARGET_DIR% -cp %CLASSPATH% %SRC_DIR%\com\minisoft\codegen\CodeGenerator.java
if %ERRORLEVEL% NEQ 0 (
    echo Failed to compile CodeGenerator.
    exit /b %ERRORLEVEL%
)

echo Compiling semantic analyzer...
javac -d %TARGET_DIR% -cp %CLASSPATH% %SRC_DIR%\com\minisoft\semantic\SemanticAnalyzer.java
if %ERRORLEVEL% NEQ 0 (
    echo Failed to compile SemanticAnalyzer.
    exit /b %ERRORLEVEL%
)

echo Compiling main class...
javac -d %TARGET_DIR% -cp %CLASSPATH% %SRC_DIR%\com\minisoft\Main.java
if %ERRORLEVEL% NEQ 0 (
    echo Failed to compile Main class.
    exit /b %ERRORLEVEL%
)

echo Compilation successful!

rem Check for specified source file
if "%~1"=="" (
    echo Usage: compile-and-run.bat [source-file]
    echo Example: compile-and-run.bat .\src\main\resources\samples\example.ms
    exit /b 1
)

echo Running compiler on %1...
java -cp %CLASSPATH%;%TARGET_DIR% com.minisoft.Main %1
