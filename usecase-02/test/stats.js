var expect = require("chai").expect;
var stats = require("../app/stats");
const crypto = require('crypto');
const fs = require('fs');


// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//      How to test? compare the files or just the hashes?
// Java opens the file with the system default viewer
//      test with JUnit, that the file can be opened
//      Integration test with Node Mocha
//

//1. Approach R-Test ignorieren: Nur das Bild oder die Hashes vergleichen
//2. Approach R-Test als Snippet einbetten und evaluieren: Den Rückgabewert muss man lesen können
describe("Statistics", function () {
    describe("How long does one need to cover the distance?", function () {
        it("plots a comparison graph for a given distance (in light years)", function () {
            stats.plotStats(1, false)
            /*const fileBuffer = fs.readFileSync('tmp/stats.png');
            const hashSum = crypto.createHash('sha256');
            hashSum.update(fileBuffer);

            const hex = hashSum.digest('hex');

            console.log('########################## ' + hex);*/

        });
    });
});