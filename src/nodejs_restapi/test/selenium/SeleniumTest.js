const { Builder, By, Key, until } = require('selenium-webdriver');
const { expect } = require('chai');

describe('Login Tests', () => {

    beforeEach(async () => driver = new Builder().forBrowser('chrome').build());


    it('Test title', async () => {
        await driver.get('http://localhost:3003/login');
        await driver.sleep(1000);
        const title = await driver.getTitle();

        expect(title).to.equal('AR-T');
    });

    it('Test no email', async () => {
        await driver.get('http://localhost:3003/login');
        await driver.sleep(1000);

        await driver.findElement(By.name('email')).sendKeys('');
        await driver.findElement(By.name('password')).sendKeys('password');

        await driver.findElement(By.name('loginButton')).click();
        await driver.sleep(2000);

        await driver.findElement(By.className('alert-validate')).exist;
    });

    it('Test invalid email format', async () => {
        await driver.get('http://localhost:3003/login');
        await driver.sleep(1000);

        await driver.findElement(By.name('email')).sendKeys('test');
        await driver.findElement(By.name('password')).sendKeys('password');

        await driver.findElement(By.name('loginButton')).click();
        await driver.sleep(2000);

        await driver.findElement(By.className('alert-validate')).click();

    });

    it('Test no password', async () => {
        await driver.get('http://localhost:3003/login');
        await driver.sleep(1000);

        await driver.findElement(By.name('email')).sendKeys('testing@mail.com');
        await driver.findElement(By.name('password')).sendKeys('');

        await driver.findElement(By.name('loginButton'));
        await driver.sleep(2000);

        await driver.findElement(By.className('alert-validate')).click();

    });

    it('Test Valid Login', async () => {
        await driver.get('http://localhost:3003/login');
        await driver.sleep(1000);

        await driver.findElement(By.name('email')).sendKeys('test1@mail.com');
        await driver.findElement(By.name('password')).sendKeys('pword');

        await driver.findElement(By.name('loginButton')).click();
        await driver.sleep(5000);

        const dashboardTitle = await driver.findElement(By.name('dashboardTitle'))
        expect(dashboardTitle).to.exist;

    });

    afterEach(async () => driver.quit());
});

describe('Dashboard Tests', () => {

    beforeEach(async () => driver = new Builder().forBrowser('chrome').build());

    it('Test Dashboard Redirects to Login', async () => {
        await driver.get('http://localhost:3003/dashboard');
        await driver.sleep(1000);
        const title = await driver.getTitle();
        const loginButton = await driver.findElement(By.name('loginButton'))

        expect(title).to.equal('AR-T');
        expect(loginButton).to.exist;

    });

    it('Test No Model Name', async () => {
        await driver.get('http://localhost:3003/login');
        await driver.sleep(1000);

        await driver.findElement(By.name('email')).sendKeys('test1@mail.com');
        await driver.findElement(By.name('password')).sendKeys('pword');

        await driver.findElement(By.name('loginButton')).click();

        const addModelButton = await driver.findElement(By.name('addModel'))
        expect(addModelButton).to.exist;

        addModelButton.click()
        await driver.sleep(1000);

    });

    it('Test No Model Description', async () => {
        await driver.get('http://localhost:3003/login');
        await driver.sleep(1000);

        await driver.findElement(By.name('email')).sendKeys('test1@mail.com');
        await driver.findElement(By.name('password')).sendKeys('pword');

        await driver.findElement(By.name('loginButton')).click();

        await driver.findElement(By.name('modelName')).sendKeys('Test');

        const addModelButton = await driver.findElement(By.name('addModel'))
        expect(addModelButton).to.exist;

        addModelButton.click()
        await driver.sleep(1000);

    });

    it('Test No 3D Model', async () => {
        await driver.get('http://localhost:3003/login');
        await driver.sleep(1000);

        await driver.findElement(By.name('email')).sendKeys('test1@mail.com');
        await driver.findElement(By.name('password')).sendKeys('pword');

        await driver.findElement(By.name('loginButton')).click();

        await driver.findElement(By.name('modelName')).sendKeys('Test');
        await driver.findElement(By.name('modelDesc')).sendKeys('Test Desc');

        const addModelButton = await driver.findElement(By.name('addModel'))
        expect(addModelButton).to.exist;

        addModelButton.click()
        await driver.sleep(1000);

    });

    it('Test Logout', async () => {
        await driver.get('http://localhost:3003/login');
        await driver.sleep(1000);

        await driver.findElement(By.name('email')).sendKeys('test1@mail.com');
        await driver.findElement(By.name('password')).sendKeys('pword');

        await driver.findElement(By.name('loginButton')).click();

        const addModelButton = await driver.findElement(By.name('addModel'))
        expect(addModelButton).to.exist;

        await driver.sleep(1000);

        await driver.findElement(By.name('logoutButton')).click();

        const loginButton = await driver.findElement(By.name('loginButton'))
        expect(loginButton).to.exist;

    });

    afterEach(async () => driver.quit());
});