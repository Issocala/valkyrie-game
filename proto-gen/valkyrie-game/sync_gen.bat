@echo off
set PROJECT_PATH=%~dp0
cd %PROJECT_PATH%
set CACHE_ROOT=%PROJECT_PATH%.cache
set PROTO_NAME=protocol

:backup
    For /f "tokens=1-3 delims=/ " %%a in ('date /t') do (set DATE=%%a-%%b-%%c)
    For /f "tokens=1-4 delims=/:." %%a in ("%TIME%") do (set TIME=%%a-%%b-%%c)    
    set CACHE_DIR=%CACHE_ROOT%/%PROTO_NAME%_%DATE%_%TIME%    
    if not exist %CACHE_DIR% ( md "%CACHE_DIR%" )
    @REM copy %PROJECT_PATH%\%PROTO_NAME%\* %CACHE_DIR% 
    @REM robocopy %PROJECT_PATH%/%PROTO_NAME%/ %CACHE_DIR% /COPYALL /E
    xcopy "%PROJECT_PATH%/%PROTO_NAME%" "%CACHE_DIR%" /E /Y 
    goto :sync_protocol

:sync_protocol
    rd  /s /q "%PROJECT_PATH%/%PROTO_NAME%"
    svn export svn://svn.longyu.com/valkyrie/trunk/protocol --force
    git restore ./%PROTO_NAME%/readme.md
    if not exist %PROTO_NAME%/readme.md ( echo this directory is generated by tool, DO NOT edit! >> %PROTO_NAME%/readme.md )
    goto :gen_protocol

:gen_protocol
    set PROTO_PATH=%PROJECT_PATH%/%PROTO_NAME%
    cd %PROTO_PATH%
    set PROJECT_ROOT=./../../../
    set OUTPUT_PATH=%PROJECT_ROOT%/src/main/scala/
    set JAVA_OUTPUT_PATH=%PROJECT_ROOT%/src/main/java/
    set "EXCUTE_PATH=./../../../../valkyrie-tool/scalapbc-0.10.11/bin/scalapbc"    
    if exist %OUTPUT_PATH% ( rd /s /q "%OUTPUT_PATH%%PROTO_NAME%" )    
    if exist %JAVA_OUTPUT_PATH% ( rd /s /q "%JAVA_OUTPUT_PATH%%PROTO_NAME%" )
    if not exist "%OUTPUT_PATH%" ( md "%OUTPUT_PATH%" ) 
    if not exist "%JAVA_OUTPUT_PATH%" ( md "%JAVA_OUTPUT_PATH%" ) 
    for /f "delims=" %%i in ('dir /b/a "*.proto"') do "%EXCUTE_PATH%" %%i --scala_out=java_conversions:"%OUTPUT_PATH%" --java_out="%JAVA_OUTPUT_PATH%"

pause
