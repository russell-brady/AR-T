var nodemailer = require('nodemailer');

var transporter = nodemailer.createTransport({
  service: 'gmail',
  auth: {
    user: 'russellbrady456@gmail.com',
    pass: 'samsung_97'
  }
});

var mailOptions = {
  from: 'russellbrady456@gmail.com',
};

function sendMail(subject, text, mailList) {

    mailOptions.to = mailList
    mailOptions.subject = subject
    mailOptions.text = text
    
    transporter.sendMail(mailOptions, function(error, info){
        if (error) {
          console.log(error);
        } else {
          console.log('Email sent: ' + info.response);
        }
      });
}

module.exports = {
    sendMail: sendMail
}