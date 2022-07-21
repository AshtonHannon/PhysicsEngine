@ECHO off
ECHO Available commands: init, clone
:start
SET /p input_command="> "
IF "%input_command%"=="init" (GOTO init
) ELSE IF "%input_command%"=="clone" (GOTO clone
) ELSE IF "%input_command%"=="exit" (GOTO exit)

:init
ECHO This 'init' command will run throught the process of initializing
ECHO git locally as well as synchorinizing the current local directory
ECHO with the remote directory. This will prompt you for your SSH
ECHO password 3 times as required for git pull, fetch, and push.
ECHO This command should ONLY be run ONCE for a FIRST TIME setup.
ECHO.
SET /p init_url=What is the url for the repository? 
ECHO init
git.exe init
ECHO remote
git.exe remote add origin %init_url%
ECHO branch
git.exe branch -M main
ECHO fetch
git.exe fetch --all
ECHO pull
git.exe pull --rebase origin main
ECHO reset
git.exe reset
ECHO add
git.exe add .
ECHO commit
git.exe commit -m "Initial commit"
ECHO push --set-upstream origin main
git.exe push -u origin main
GOTO start


:clone
SET /p clone_url=What is the url of the repository to clone? 
git.exe clone %clone_url%
GOTO start


PAUSE
:exit
