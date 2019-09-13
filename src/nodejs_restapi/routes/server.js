const mysql = require('mysql')

// const pool = mysql.createPool({
//     connectionLimit: 10,
//     host: 'us-cdbr-iron-east-03.cleardb.net',
//     user: 'b9280f39950c08',
//     password: '3cf248e2',
//     database: 'heroku_b835ade894df755',
//     multipleStatements: true,
// })

function getConnection() {
    return pool;
}

module.exports = {
    connection: getConnection
}

const pool = mysql.createPool({
    connectionLimit: 10,
    host: 'localhost',
    user: 'root',
    password: 'samsung97',
    database: 'art_database',
    port: 3306,
    multipleStatements: true,
})

// const testPool = mysql.createPool({
//     connectionLimit: 10,
//     host: 'localhost',
//     user: 'root',
//     password: 'samsung97',
//     database: 'art_database',
//     port: 3306,
//     multipleStatements: true,
// })