-- phpMyAdmin SQL Dump
-- version 3.2.0.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generato il: 23 nov, 2009 at 01:34 
-- Versione MySQL: 5.1.37
-- Versione PHP: 5.3.0

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
-- Struttura della tabella `Accept`
--

CREATE TABLE IF NOT EXISTS `Accept` (
  `Username` varchar(40) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `Protocol` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`Username`,`Protocol`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `Log`
--

CREATE TABLE IF NOT EXISTS `Log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Sender` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `date` datetime NOT NULL,
  `log` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dump dei dati per la tabella `Log`
--


-- --------------------------------------------------------

--
-- Struttura della tabella `MP`
--

CREATE TABLE IF NOT EXISTS `MP` (
  `id` int(11) NOT NULL,
  `nome` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `integrità` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `riservatezza` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `MP`
--

INSERT INTO `MP` (`id`, `nome`, `integrità`, `riservatezza`) VALUES
(0, 'irc', '1', '1'),
(1, 'msnp', '1', '1'),
(2, 'skype', '1', '1'),
(3, 'xmpp', '1', '1'),
(4, 'oscar', '1', '1'),
(5, 'yahoo', '1', '1');

-- --------------------------------------------------------

--
-- Struttura della tabella `Users`
--

CREATE TABLE IF NOT EXISTS `Users` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `user` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `password` varchar(40) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;
