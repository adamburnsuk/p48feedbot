@echo off
set ip=%1
set port=%2
set udid=%3
set chromedriverport=%4
set reservationID=%5

appium -a %ip% -p %port% --chromedriver-executable "c:\\toolbox\\chromedriver\\chromedriver.exe" --chromedriver-port %chromedriverport% -dc "{\"udid\": \"%udid%\"}" --log "c:\\toolbox\\tests\\logs\\%reservationID%.log" 