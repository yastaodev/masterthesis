var expect = require("chai").expect;
var stats = require("../app/stats");

// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// What needs to be done here is:
// R makes a plot and saves the image
//      How to test? in R or in JS?
// NodeJS converts the image to PDF and embed a text into it
//      How to test? compare the files or just the hashes?
// Java opens the file with the system default viewer
//      test with JUnit, that the file can be opened
//      Integration test with Node Mocha
//
describe("Statistics", function () {
    describe("How long does one need to cover the distance?", function () {
        it("plots a comparison graph for a given distance (in light years)", function () {
            // Cannot handle big digits, so the parameter should be a String
            var inKm1 = converter.fromLyToKm("0");
            var inKm2 = converter.fromLyToKm("14000000000");
            var inKm3 = converter.fromLyToKm("12976233.12");
            var inKm4 = converter.fromLyToKm("0.000000000001");
            // km = ly / 0.00000000000010570
            expect(inKm1).to.equal("0E+17");
            expect(inKm2).to.equal("132450331125827814569536.4238410596");
            expect(inKm3).to.equal("122764740964995269631.0312204351939");
            expect(inKm4).to.equal("9.460737937559129612109744560075686");
        });
    });

    describe("Kilometers to light years", function () {
        it("converts from kilometers to light years", function () {
            // Cannot handle big digits, so the parameter should be a String
            var inLy1 = converter.fromKmToLy("0");
            var inLy2 = converter.fromKmToLy("123456789101112131415161718192021");
            var inLy3 = converter.fromKmToLy("89101112.34");
            var inLy4 = converter.fromKmToLy("9460800000000");
            // ly = km * 0.00000000000010570
            expect(inLy1).to.equal("0E-17");
            expect(inLy2).to.equal("13049382607987552290.58259361289661970");
            expect(inLy3).to.equal("0.0000094179875743380");
            expect(inLy4).to.equal("1.00000656000000000");
        });
    })
});