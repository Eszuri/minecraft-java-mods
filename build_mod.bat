@echo off
setlocal enabledelayedexpansion
title Minecraft Mod Compiler

echo ========================================================
echo         Compiling Stackable Items Mod (.jar)
echo ========================================================
echo.

:: Check Java
where java >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    if exist "C:\Program Files\Java\jdk-26.0.1" (
        set "JAVA_HOME=C:\Program Files\Java\jdk-26.0.1"
        set "PATH=!JAVA_HOME!\bin;!PATH!"
        echo [INFO] Menggunakan JDK dari C:\Program Files\Java\jdk-26.0.1
    ) else (
        echo [ERROR] JDK tidak ditemukan! Pastikan Java 25/26 terinstall.
        pause
        exit /b 1
    )
)

echo [1/2] Menjalankan Gradle build...
call gradlew.bat build
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Build gagal! Periksa pesan error di atas.
    pause
    exit /b 1
)

echo.
echo [2/2] Menyiapkan folder output...
if not exist "output" mkdir output

:: Copy jar to output folder ONLY
for %%F in (build\libs\stackable_items-*.jar) do (
    echo %%~nxF | findstr /i "sources" >nul
    if errorlevel 1 (
        set "JAR_FILE=%%F"
        set "JAR_NAME=%%~nxF"
        copy /y "%%F" "output\" >nul
        echo [OK] Mod JAR tersimpan di: output\!JAR_NAME!
    )
)

echo.
echo ========================================================
echo           BUILD BERHASIL SELESAI!
echo ========================================================
echo File mod hanya disimpan di folder output: output\!JAR_NAME!
echo.
pause
