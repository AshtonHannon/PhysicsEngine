@ECHO off
ECHO Available commands: add, commit, push, rerun, reset, default, clear, exit, help
:start
SET /p input_command="> "
IF "%input_command%"=="add" (GOTO add
) ELSE IF "%input_command%"=="commit" (GOTO commit
) ELSE IF "%input_command%"=="push" (GOTO push
) ELSE IF "%input_command%"=="rerun" (GOTO ask_for_rerun
) ELSE IF "%input_command%"=="reset" (GOTO reset
) ELSE IF "%input_command%"=="default" (GOTO default
) ELSE IF "%input_command%"=="" (GOTO default
) ELSE IF "%input_command%"=="clear" (GOTO clear
) ELSE IF "%input_command%"=="exit" (GOTO exit
) ELSE IF "%input_command%"=="help" (GOTO help)
GOTO start


:help
ECHO Available commands: add, commit, push, rerun, reset, default, clear, exit, help
GOTO start


:add
SET /p add_all=Would you like to add all [y/n]? 
IF %add_all%==y (
	ECHO ... Okay, adding all && SET add_param=-A
) ELSE IF %add_all%==Y (
	ECHO ... Okay, adding all && SET add_param=-A
) ELSE ( 
	SET /p add_param=Okay, enter add parameters: 
)
git.exe add %add_param%
SET "add_all="
SET "add_param="
GOTO start


:commit
ECHO You can either set a small commit message with this input, or 
ECHO alternatively you can input 'FILE' to be prompted to input a commit
ECHO message by means of a local file.
ECHO.
SET /p commit_message=What is the commit message? 
IF "%commit_message%"=="FILE" (GOTO commit_file)
git.exe commit -m "%commit_message%"
ECHO Commit Message: %commit_message%
SET "commit_message="
GOTO start


:commit_file
SET /p commit_filename=What is the name of the file? 
ECHO Filename: %commit_filename%
IF NOT EXIST %commit_filename% (
	ECHO Invalid filename...
	GOTO start
) ELSE (
	git.exe commit -F %commit_filename%
)
GOTO start


:push
SET /p push_confirm=Are you sure you want to push [y/n]? 
IF %push_confirm%==y (
	git.exe push
) ELSE IF %push_confirm%==Y (
	git.exe push
) ELSE (
	ECHO Okay... not pushing!
)
SET "push_confirm="
GOTO start


:reset
ECHO Clearing staged commit!
ECHO.
git.exe reset
GOTO start


:default
SET /p commit_message=What is the commit message? 
git.exe add -A
git.exe commit -m "%commit_message%"
git.exe push
ECHO Done!
SET "commit_message="
GOTO start


:clear
CLS
GOTO start


:ask_for_rerun
SET /p runagain=Would you like to rerun this script? 
IF %runagain%==y (
	ECHO Restarting...
	timeout 3 > NUL
	cls
	GOTO start
) ELSE (
	GOTO exit
)
SET "run_again="

PAUSE
:exit