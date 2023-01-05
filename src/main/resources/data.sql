INSERT INTO `22-jrtp`.`country_master` (`countryId`, `countryName`) VALUES ('1', 'India');
INSERT INTO `22-jrtp`.`country_master` (`countryId`, `countryName`) VALUES ('2', 'USA');

INSERT INTO `22-jrtp`.`state_master` (`stateId`, `stateName`, `countryId`) VALUES ('1', 'Florida', '2');
INSERT INTO `22-jrtp`.`state_master` (`stateId`, `stateName`, `countryId`) VALUES ('2', 'Alaska', '2');
INSERT INTO `22-jrtp`.`state_master` (`stateId`, `stateName`, `countryId`) VALUES ('6', 'Uttar Pradesh', '1');

INSERT INTO `22-jrtp`.`cities_master` (`cityId`, `cityName`, `stateId`) VALUES ('1', 'Varanasi', '6');
INSERT INTO `22-jrtp`.`cities_master` (`cityId`, `cityName`, `stateId`) VALUES ('5', 'Hyderabad', '7');



