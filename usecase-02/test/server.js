const fs = require('fs');
var expect = require("chai").expect;
var request = require("request");

describe("API Server", function () {
    before(function() {
        let folder = 'tmp/';
        fs.readdir(folder, (err, files) => {
            if (err) throw err;
            for (const file of files) {
                fs.unlinkSync(folder + file);
            }
        });
    });

    describe("Light Years to Kilometers", function (done) {
        var url = "http://localhost:3000/fromLyToKm?ly=14000000000";
        it("returns status 200", function (done) {
            request(url, function(error, response, body) {
                expect(response.statusCode).to.equal(200);
                done();
            });
        });
        it("returns the distance in KM", function (done) {
            request(url, function(error, response, body) {
                expect(body).to.equal("132450331125827814569536.4238410596");
                done();
            });
        });
    });

    describe("Kilometers to light years", function (done) {
        var url = "http://localhost:3000/fromKmToLy?km=9460800000000";
        it("returns status 200", function (done) {
            request(url, function(error, response, body) {
                expect(response.statusCode).to.equal(200);
                done();
            });
        });
        it("returns the distance in LY", function (done) {
            request(url, function(error, response, body) {
                expect(body).to.equal("1.00000656000000000");
                done();
            });
        });
    })

    describe("How long does it need to cover the distance?", function (done) {
        var url = "http://localhost:3000/stats?ly=1&open=true";
        it("returns status 200", function (done) {
            request(url, function(error, response, body) {
                expect(response.statusCode).to.equal(200);
                done();
            });
        }).timeout(5000);;
        it("plots the comparison graph", function (done) {
            request(url, function(error, response, body) {
                expect(body).to.equal("SUCCESS");
                done();
            });
        }).timeout(10000);
    })
})