-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 12 Cze 2018, 00:55
-- Wersja serwera: 10.1.30-MariaDB
-- Wersja PHP: 7.2.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `iaid_project`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `dataset`
--

CREATE TABLE `dataset` (
  `id` int(11) NOT NULL,
  `l_core` varchar(255) NOT NULL,
  `l_surf` varchar(255) NOT NULL,
  `l_o2` varchar(255) NOT NULL,
  `l_bp` varchar(255) NOT NULL,
  `surf_stbl` varchar(255) NOT NULL,
  `core_stbl` varchar(255) NOT NULL,
  `bp_stbl` varchar(255) NOT NULL,
  `comfort` varchar(255) NOT NULL,
  `decistion` varchar(255) NOT NULL,
  `dataset_type` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `dataset`
--
ALTER TABLE `dataset`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
