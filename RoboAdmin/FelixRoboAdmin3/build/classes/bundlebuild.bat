@echo on
REM esegue i jar per i bundle presenti nel progetto RoboAdmin
set CURRENT_DIR=%cd%
cd C:\Users\NaNNy\Documents\RoboAdmin2.0\RoboAdmin1.4\FelixRoboAdmin3\build\classes

REM interfacce di servizio
jar cfm ../../bundle/interfaces/COM.jar ../../src/communicationLayer/service/manifest.mf communicationLayer/service
jar cfm ../../bundle/interfaces/CONFIGURATOR.jar ../../src/configurator/service/manifest.mf configurator/service
jar cfm ../../bundle/interfaces/DB.jar ../../src/db/service/manifest.mf db/service
jar cfm ../../bundle/interfaces/INTELLIGENCE.jar ../../src/intelligence/service/manifest.mf intelligence/service
jar cfm ../../bundle/interfaces/LOG.jar ../../src/log/service/manifest.mf log/service
jar cfm ../../bundle/interfaces/ROBOADMIN.jar ../../src/RA/service/manifest.mf RA/service
jar cfm ../../bundle/interfaces/SECURITY.jar ../../src/security/service/manifest.mf security/service

REM servizi RoboAdmin
jar cfm ../../bundle/CORE.jar ../../src/RA/V1/manifest.mf RA/V1

REM servizi DataBase
jar cfm ../../bundle/MySQLDataBase.jar ../../src/db/mySQL/manifest.mf db/mySQL

REM servizi logger
jar cfm ../../bundle/dbLogger.jar ../../src/log/dbLogger/manifest.mf log/dbLogger
jar cfm ../../bundle/fileLogger.jar ../../src/log/fileLogger/manifest.mf log/fileLogger

REM servizi intelligence
jar cfm ../../bundle/eliza.jar ../../src/intelligence/eliza/manifest.mf intelligence/eliza

REM servizi configurator
jar cfm ../../bundle/patchConfigurator.jar ../../src/configurator/patchConfigurator/manifest.mf configurator/patchConfigurator

REM servizi security
jar cfm ../../bundle/raSecurity.jar ../../src/security/raSecurity/manifest.mf security/raSecurity

REM servizi comunicazione
jar cfm ../../bundle/controller.jar ../../src/communicationLayer/controller/manifest.mf communicationLayer/controller
jar cfm ../../bundle/irc.jar ../../src/communicationLayer/irc/manifest.mf communicationLayer/irc
jar cfm ../../bundle/msnp.jar ../../src/communicationLayer/msnp/manifest.mf communicationLayer/msnp
jar cfm ../../bundle/skype.jar ../../src/communicationLayer/skype/manifest.mf communicationLayer/skype
jar cfm ../../bundle/xmpp.jar ../../src/communicationLayer/xmpp/manifest.mf communicationLayer/xmpp
jar cfm ../../bundle/oscar.jar ../../src/communicationLayer/oscar/manifest.mf communicationLayer/oscar
jar cfm ../../bundle/yahoo.jar ../../src/communicationLayer/yahoo/manifest.mf communicationLayer/yahoo


cd %CURRENT_DIR%