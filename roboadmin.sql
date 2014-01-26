-- phpMyAdmin SQL Dump
-- version 3.2.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 26, 2010 at 07:36 
-- Server version: 5.1.41
-- PHP Version: 5.3.1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `RoboAdminDB`
--
CREATE DATABASE `RoboAdminDB` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `RoboAdminDB`;

-- --------------------------------------------------------

--
-- Table structure for table `Accept`
--

CREATE TABLE IF NOT EXISTS `Accept` (
  `Username` varchar(40) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `Protocol` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`Username`,`Protocol`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Accept`
--

INSERT INTO `Accept` (`Username`, `Protocol`) VALUES
('625104871', 'Oscar'),
('giannib1986', 'Skype'),
('giannibony1986', 'Yahoo'),
('nanny07@gmail.com', 'MSNP'),
('nanny07@gmail.com', 'XMPP');

-- --------------------------------------------------------

--
-- Table structure for table `Log`
--

CREATE TABLE IF NOT EXISTS `Log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Sender` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `date` datetime NOT NULL,
  `log` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `Log`
--

INSERT INTO `Log` (`id`, `Sender`, `date`, `log`) VALUES
(1, '', '2010-04-26 14:30:28', 'avvio servizio log'),
(2, '', '2010-04-26 14:52:49', 'avvio servizio log'),
(3, '', '2010-04-26 17:12:22', 'avvio servizio log'),
(4, '', '2010-04-26 17:19:18', 'avvio servizio log'),
(5, '', '2010-04-26 17:26:13', 'avvio servizio log'),
(6, '', '2010-04-26 19:16:44', 'avvio servizio log');

-- --------------------------------------------------------

--
-- Table structure for table `MP`
--

CREATE TABLE IF NOT EXISTS `MP` (
  `id` int(11) NOT NULL,
  `nome` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `integrità` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `riservatezza` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `MP`
--

INSERT INTO `MP` (`id`, `nome`, `integrità`, `riservatezza`) VALUES
(0, 'irc', '0', '1'),
(1, 'msnp', '0', '1'),
(2, 'skype', '1', '1'),
(3, 'xmpp', '1', '1'),
(4, 'oscar', '0', '1'),
(5, 'yahoo', '1', '1');

-- --------------------------------------------------------

--
-- Table structure for table `Users`
--

CREATE TABLE IF NOT EXISTS `Users` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `user` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `password` varchar(40) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `Users`
--

INSERT INTO `Users` (`Id`, `user`, `password`) VALUES
(0, 'gianni', 'gianni');

-- --------------------------------------------------------

--
-- Table structure for table `invalidstring`
--

CREATE TABLE IF NOT EXISTS `invalidstring` (
  `InvalidString` varchar(100) NOT NULL,
  PRIMARY KEY (`InvalidString`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `invalidstring`
--

INSERT INTO `invalidstring` (`InvalidString`) VALUES
('invalid String');

-- --------------------------------------------------------

--
-- Table structure for table `ircchannellist`
--

CREATE TABLE IF NOT EXISTS `ircchannellist` (
  `Channel` varchar(50) NOT NULL,
  PRIMARY KEY (`Channel`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ircchannellist`
--

INSERT INTO `ircchannellist` (`Channel`) VALUES
('ciao'),
('freenode');

-- --------------------------------------------------------

--
-- Table structure for table `ircproperties`
--

CREATE TABLE IF NOT EXISTS `ircproperties` (
  `Property` varchar(20) NOT NULL,
  `Value` varchar(20) NOT NULL,
  PRIMARY KEY (`Property`,`Value`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ircproperties`
--

INSERT INTO `ircproperties` (`Property`, `Value`) VALUES
('IRC.login', 'lbett'),
('IRC.nickname', 'RAdmin'),
('IRC.password', ''),
('IRC.serverPort', '6667'),
('IRC.serverToConnect', 'irc.freenode.net');

-- --------------------------------------------------------

--
-- Table structure for table `msnpproperties`
--

CREATE TABLE IF NOT EXISTS `msnpproperties` (
  `Property` varchar(20) NOT NULL,
  `Value` varchar(20) NOT NULL,
  PRIMARY KEY (`Property`,`Value`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `msnpproperties`
--

INSERT INTO `msnpproperties` (`Property`, `Value`) VALUES
('MSNP.eMail', 'RA.msn@live.it'),
('MSNP.password', 'RoboAdminMsn');

-- --------------------------------------------------------

--
-- Table structure for table `mysqldbproperties`
--

CREATE TABLE IF NOT EXISTS `mysqldbproperties` (
  `Property` varchar(50) NOT NULL,
  `Value` varchar(20) NOT NULL,
  PRIMARY KEY (`Property`,`Value`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `mysqldbproperties`
--

INSERT INTO `mysqldbproperties` (`Property`, `Value`) VALUES
('MySqlDB.DBName', 'RoboAdminDB'),
('MySqlDB.DBPassword', 'visual'),
('MySqlDB.DBServerAddress', 'localhost'),
('MySqlDB.DBServerPort', '3306'),
('MySqlDB.DBUser', 'root');

-- --------------------------------------------------------

--
-- Table structure for table `oscarproperties`
--

CREATE TABLE IF NOT EXISTS `oscarproperties` (
  `Property` varchar(20) NOT NULL,
  `Value` varchar(20) NOT NULL,
  PRIMARY KEY (`Property`,`Value`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `oscarproperties`
--

INSERT INTO `oscarproperties` (`Property`, `Value`) VALUES
('OSCAR.eMail', 'raicqaim@aol.com'),
('OSCAR.password', 'RAdminOscar'),
('OSCAR.screenName', 'raicqaim');

-- --------------------------------------------------------

--
-- Table structure for table `roboadminproperties`
--

CREATE TABLE IF NOT EXISTS `roboadminproperties` (
  `Property` varchar(50) NOT NULL,
  `Value` varchar(20) NOT NULL,
  PRIMARY KEY (`Property`,`Value`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `roboadminproperties`
--

INSERT INTO `roboadminproperties` (`Property`, `Value`) VALUES
('Roboadmin.autore', '!AUTORE!'),
('RoboAdmin.cc', ''),
('RoboAdmin.changeServer', '!SERVER&CHANNEL!'),
('RoboAdmin.esegui', '!EXECUTE!'),
('RoboAdmin.from', ''),
('RoboAdmin.identificazione', '!ID!'),
('RoboAdmin.join', '!JOIN!'),
('RoboAdmin.LogFile', 'RAlog.txt'),
('RoboAdmin.login', '!LOGIN!'),
('RoboAdmin.logout', '!LOGOUT!'),
('RoboAdmin.off', '!DIE!'),
('Roboadmin.remotedesktop', '!DESKTOP!'),
('RoboAdmin.ripeti', '!REPEAT!'),
('RoboAdmin.saluto', '!HI!'),
('RoboAdmin.smtpServer', 'localhost'),
('RoboAdmin.subject', '!!RoboAdmim-Attack-D'),
('RoboAdmin.to', '');

-- --------------------------------------------------------

--
-- Table structure for table `skypeproperties`
--

CREATE TABLE IF NOT EXISTS `skypeproperties` (
  `Property` varchar(20) NOT NULL,
  `Value` varchar(20) NOT NULL,
  PRIMARY KEY (`Property`,`Value`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `skypeproperties`
--

INSERT INTO `skypeproperties` (`Property`, `Value`) VALUES
('Skype.password', 'RoboAdminSkp2009'),
('Skype.skypeId', 'robo.admin');

-- --------------------------------------------------------

--
-- Table structure for table `xmppproperties`
--

CREATE TABLE IF NOT EXISTS `xmppproperties` (
  `Property` varchar(20) NOT NULL,
  `Value` varchar(20) NOT NULL,
  PRIMARY KEY (`Property`,`Value`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `xmppproperties`
--

INSERT INTO `xmppproperties` (`Property`, `Value`) VALUES
('XMPP.eMail', 'ra.xmpp@gmail.com'),
('XMPP.host', 'talk.google.com'),
('XMPP.password', 'RoboAdminXmpp'),
('XMPP.port', '5222');

-- --------------------------------------------------------

--
-- Table structure for table `yahooproperties`
--

CREATE TABLE IF NOT EXISTS `yahooproperties` (
  `Property` varchar(20) NOT NULL,
  `Value` varchar(20) NOT NULL,
  PRIMARY KEY (`Property`,`Value`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `yahooproperties`
--

INSERT INTO `yahooproperties` (`Property`, `Value`) VALUES
('Yahoo.localPort', '-1'),
('Yahoo.password', 'RAYahoo'),
('Yahoo.userName', 'roboa@ymail.com');
