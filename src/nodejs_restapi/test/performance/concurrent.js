var siege = require('siege');

siege()
    .on(3003)
    .concurrent(20)
    .get('/')
    .attack()