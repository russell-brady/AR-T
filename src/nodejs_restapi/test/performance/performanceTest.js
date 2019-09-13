var siege = require('siege');

siege()
  .on(3003)
  .for(10000).times
  .get('/')    
  .post('/login', {email: 'russell@mail.com', password: 'pword'})
  .attack()
