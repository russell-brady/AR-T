process.env.ENV = 'Test'

var expect  = require('chai').expect;
const app = require('../../app')
const request = require('supertest');

describe('Rest Api Tests', function() {
    describe ('Home Route', function() {
        it('Test home route code', function(done) {
            request(app)
            .get('/')
            .set('Accept', 'application/json')
            .expect(200)
            .end(done);
        });

        it('Test home route response', function(done) {
            request(app)
            .get('/')
            .set('Accept', 'application/json')
            .expect("This is the root route...")
            .end(done);
        });
    });

    describe ('Web App Routes', function() {
        it('Test Login GET Route', function(done) {
            request(app)
            .get('/login')
            .set('Accept', 'application/json')
            .expect(200)
            .end(done);
        });

        it('Test Web Login Route', function(done) {
            request(app)
            .post('/weblogin')
            .send({email: 'test1@mail.com', password: 'pword'})
            .set('Accept', 'application/json')
            .expect(200)
            .end(function(err, res) {
                if (err) return done(err);
                done();
            });
        });

        it('Test Logout Route', function(done) {
            request(app)
            .get('/logout')
            .set('Accept', 'application/json')
            .expect(302)
            .end(done);
        });

    });

    describe ('Android Authentication Rest Api Routes', function() {
        
        it('Test Login GET Route Valid Credentials', function(done) {
            request(app)
            .post('/login')
            .send("email=russell@mail.com")
            .send("password=pword")
            .set('Accept', 'application/x-www-form-urlencoded')
            .expect(200)
            .end(function(err, res) {
                expect(err).to.not.exist;
                expect(res.body.code).to.equal(200);
                expect(res.body.success).to.equal("login successful");
                done();
            });
        });

        it('Test Login GET Route Invalid Credentials', function(done) {
            request(app)
            .post('/login')
            .send("email=russell@mail.com")
            .send("password=")
            .set('Accept', 'application/x-www-form-urlencoded')
            .expect(200)
            .end(function(err, res) {
                expect(err).to.not.exist;
                expect(res.body.code).to.equal(204);
                expect(res.body.success).to.equal("incorrect credentials");
                done();
            });
        });

        it('Test Teacher Sign Up Route Missing Credentials', function(done) {
            request(app)
            .post('/registerTeacher')
            .set('Accept', 'application/json')
            .expect(200)
            .end(function(err, res) {
                expect(err).to.exist;
                done();
            });
        });        

        // it('Test Teacher Sign Up Route Valid Credentials', function(done) {
        //     request(app)
        //     .post('/registerTeacher')
        //     .send({email: 'teacher2@mail.com', password: 'pword', type: 'Teacher', name: 'teacher1'})
        //     .set('Accept', 'application/json')
        //     .expect(200)
        //     .end(function(err, res) {
        //         expect(err).to.not.exist;
        //         expect(res.body.code).to.equal(200)
        //         done();
        //     });
        // });

        it('Test Student Sign Up Route Missing Credentials', function(done) {
            request(app)
            .post('/registerStudent')
            .set('Accept', 'application/json')
            .expect(200)
            .end(function(err, res) {
                expect(err).to.exist;
                done();
            });
        });        

        // it('Test Student Sign Up Route Valid Credentials', function(done) {
        //     request(app)
        //     .post('/registerStudent')
        //     .send({email: 'student1@mail.com', password: 'pword', type: 'Student', name: 'student1', classId: 9999})
        //     .set('Accept', 'application/json')
        //     .expect(200)
        //     .end(function(err, res) {
        //         expect(err).to.not.exist;
        //         expect(res.body.code).to.equal(200)
        //         done();
        //     });
        // });

    });

    describe ('Assignments Routes', function() {

        it('Test GET assignments route', function(done) {
            request(app)
            .post('/getAssignments')
            .send("classId=1234")
            .set('Accept', 'application/x-www-form-urlencoded')
            .expect(200)
            .end(function(err, res) {
                expect(err).to.not.exist;
                expect(res.body.code).to.equal(200);
                expect(res.body.success).to.equal("Getting assignments sucessfull");
                done();
            });
        });

        it('Test GET student assignments route', function(done) {
            request(app)
            .post('/getAssignments')
            .send("id=1")
            .set('Accept', 'application/x-www-form-urlencoded')
            .expect(200)
            .end(function(err, res) {
                expect(err).to.not.exist;
                expect(res.body.code).to.equal(200);
                expect(res.body.success).to.equal("Getting assignments sucessfull");
                done();
            });
        });
    });

    describe ('Models Routes', function() {

        it('Test GET models route', function(done) {
            request(app)
            .get('/getModels/2')
            .set('Accept', 'application/x-www-form-urlencoded')
            .expect(200)
            .end(function(err, res) {
                expect(err).to.not.exist;
                expect(res.body.code).to.equal(200);
                expect(res.body.success).to.equal("Models retrieved successfully");
                done();
            });
        });

    });
    
    describe ('Cloud Anchor Routes', function() {

        it('Test GET cloud anchor route', function(done) {
            request(app)
            .post('/getCloudAnchorId')
            .send("anchor_key=413")
            .set('Accept', 'application/x-www-form-urlencoded')
            .expect(200)
            .end(function(err, res) {
                expect(err).to.not.exist;
                expect(res.body.code).to.equal(200);
                expect(res.body.success).to.equal("Sucessfull");
                done();
            });
        });

        it('Test GET cloud anchor route', function(done) {
            request(app)
            .post('/getCloudAnchorId')
            .set('Accept', 'application/x-www-form-urlencoded')
            .expect(200)
            .end(function(err, res) {
                expect(err).to.not.exist;
                expect(res.body.code).to.equal(204);
                expect(res.body.success).to.equal("Anchor does not exist");
                done();
            });
        });

    });

    describe ('Assignment Submission Routes', function() {

        it('Test GET assignment submissions route', function(done) {
            request(app)
            .post('/getAssignmentSubmissions')
            .send("assignmentId=1")
            .set('Accept', 'application/x-www-form-urlencoded')
            .expect(200)
            .end(function(err, res) {
                expect(err).to.not.exist;
                expect(res.body.code).to.equal(200);
                expect(res.body.success).to.equal("Getting assignments sucessfull");
                done();
            });
        });

    });

    describe ('Class Routes', function() {

        it('Test GET classes route', function(done) {
            request(app)
            .post('/getClasses')
            .send("id=2")
            .set('Accept', 'application/x-www-form-urlencoded')
            .expect(200)
            .end(function(err, res) {
                expect(err).to.not.exist;
                expect(res.body.code).to.equal(200);
                expect(res.body.success).to.equal("Getting Classes sucessfull");
                done();
            });
        });

        it('Test GET class students route', function(done) {
            request(app)
            .post('/getClassStudents')
            .send("classId=1234")
            .set('Accept', 'application/x-www-form-urlencoded')
            .expect(200)
            .end(function(err, res) {
                expect(err).to.not.exist;
                expect(res.body.code).to.equal(200);
                expect(res.body.success).to.equal("Getting students sucessfull");
                done();
            });
        });

        it('Test GET classmates route', function(done) {
            request(app)
            .post('/getClassmates')
            .send("id=1")
            .set('Accept', 'application/x-www-form-urlencoded')
            .expect(200)
            .end(function(err, res) {
                expect(err).to.not.exist;
                expect(res.body.code).to.equal(200);
                expect(res.body.success).to.equal("Getting students sucessfull");
                done();
            });
        });

    });
});