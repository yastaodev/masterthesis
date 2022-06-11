var expect = require("chai").expect;
var request = require("request");

describe("API Server", function () {
    describe("Light Years to Kilometers", function () {
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
})