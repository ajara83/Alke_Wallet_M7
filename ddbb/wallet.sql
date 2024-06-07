-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 07-06-2024 a las 03:41:55
-- Versión del servidor: 8.0.37
-- Versión de PHP: 7.4.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `wallet`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `app_user`
--

CREATE TABLE `app_user` (
  `id` bigint NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `app_user`
--

INSERT INTO `app_user` (`id`, `password`, `username`) VALUES
(1, '$2a$10$o99C31HbXR5C2MZl8XLPXOnIo42lPQ9KlQQop9dbGuJU0M98qGNJK', 'admin'),
(2, '$2a$10$/3GHdwQ/Sd5/XAg2Y/wUTO/pqSStzDVDp4K3jMpeqpRQ1R/3CUc4S', 'beto'),
(3, '$2a$10$YFYf2C0EKuYN43iPi9.FeuSZykqjW0MCStZs1XY6cMLRH4gs36f5S', 'tere');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `id` int NOT NULL,
  `cuenta_id` bigint DEFAULT NULL,
  `usuario_id` bigint DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `rut` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`id`, `cuenta_id`, `usuario_id`, `email`, `nombre`, `rut`) VALUES
(1, 1, 2, 'ajara@webmark.cl', 'Alberto', '15536288-K'),
(2, 2, 3, 'ajara@webmark.cl', 'Teresa Rojas', '8114322-6');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cuenta`
--

CREATE TABLE `cuenta` (
  `saldo` double NOT NULL,
  `usd` bit(1) NOT NULL,
  `id` bigint NOT NULL,
  `titular` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `cuenta`
--

INSERT INTO `cuenta` (`saldo`, `usd`, `id`, `titular`) VALUES
(350, b'0', 1, 'beto'),
(750, b'0', 2, 'tere');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `rol` (
  `id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `rol`
--

INSERT INTO `rol` (`id`, `name`) VALUES
(1, 'ADMIN'),
(2, 'USUARIO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `transaccion`
--

CREATE TABLE `transaccion` (
  `monto` double NOT NULL,
  `cuenta_destino` bigint DEFAULT NULL,
  `cuenta_origen` bigint DEFAULT NULL,
  `fecha` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `cuenta_destino_id` bigint DEFAULT NULL,
  `cuenta_origen_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `transaccion`
--

INSERT INTO `transaccion` (`monto`, `cuenta_destino`, `cuenta_origen`, `fecha`, `id`, `tipo`, `cuenta_destino_id`, `cuenta_origen_id`) VALUES
(500, NULL, 2, '2024-06-06 20:44:07.814560', 1, 'DEPOSITO', NULL, NULL),
(50, 1, 2, '2024-06-06 20:47:24.282209', 2, 'TRANSFERENCIA', NULL, NULL),
(600, NULL, 2, '2024-06-06 21:35:41.373422', 3, 'DEPOSITO', NULL, NULL),
(300, 1, 2, '2024-06-06 21:35:48.005435', 4, 'TRANSFERENCIA', NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user_roles`
--

CREATE TABLE `user_roles` (
  `role_id` int NOT NULL,
  `user_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `user_roles`
--

INSERT INTO `user_roles` (`role_id`, `user_id`) VALUES
(1, 1),
(2, 2),
(2, 3);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `app_user`
--
ALTER TABLE `app_user`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKj0k0rw6f56utyt96rnahif4kf` (`cuenta_id`),
  ADD UNIQUE KEY `UKid7jmosqg8hkqiqw4vf50xipm` (`usuario_id`);

--
-- Indices de la tabla `cuenta`
--
ALTER TABLE `cuenta`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `rol`
--
ALTER TABLE `rol`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `transaccion`
--
ALTER TABLE `transaccion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKhaaa8hd9jvoqmwdflt8c6kuqp` (`cuenta_destino_id`),
  ADD KEY `FKxvyfnyjy81i330lml8s0y706` (`cuenta_origen_id`);

--
-- Indices de la tabla `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`role_id`,`user_id`),
  ADD KEY `FK6fql8djp64yp4q9b3qeyhr82b` (`user_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `app_user`
--
ALTER TABLE `app_user`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `cuenta`
--
ALTER TABLE `cuenta`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `rol`
--
ALTER TABLE `rol`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `transaccion`
--
ALTER TABLE `transaccion`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `FKawnjeivhqi9af18vsh5ng8tqf` FOREIGN KEY (`cuenta_id`) REFERENCES `cuenta` (`id`),
  ADD CONSTRAINT `FKskkiku3k5if6v4ikfvipffau7` FOREIGN KEY (`usuario_id`) REFERENCES `app_user` (`id`);

--
-- Filtros para la tabla `transaccion`
--
ALTER TABLE `transaccion`
  ADD CONSTRAINT `FKhaaa8hd9jvoqmwdflt8c6kuqp` FOREIGN KEY (`cuenta_destino_id`) REFERENCES `cuenta` (`id`),
  ADD CONSTRAINT `FKxvyfnyjy81i330lml8s0y706` FOREIGN KEY (`cuenta_origen_id`) REFERENCES `cuenta` (`id`);

--
-- Filtros para la tabla `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `FK6fql8djp64yp4q9b3qeyhr82b` FOREIGN KEY (`user_id`) REFERENCES `app_user` (`id`),
  ADD CONSTRAINT `FK9lasceq82f1y6pltnys5ovctw` FOREIGN KEY (`role_id`) REFERENCES `rol` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
