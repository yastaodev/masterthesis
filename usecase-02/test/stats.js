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

describe("Statistics", function () {
    describe("How long does one need to cover the distance?", function () {
        it("plots a comparison graph for a given distance (No R-Tests)", function () {
            stats.plotStats(1, false);

            function calcHash(img) {
                const fileBuffer = fs.readFileSync(img);
                const hashSum = crypto.createHash('sha256');
                hashSum.update(fileBuffer);
                const hex = hashSum.digest('hex');
            }

            expect(calcHash('test/stats.png')).to.equal(calcHash('tmp/stats.png'));
        }).timeout(100000);

        it("plots a comparison graph for a given distance (Embedded R-Tests)", function () {
            Polyglot.eval('R',
                `# Imports
                library(ggplot2)
                library(testthat)
                library(crayon)
                # First, 'correct' data frame
                df <- data.frame(
                    Response   = LETTERS[1:5],
                    Proportion = c(0.1,0.2,0.1,0.2,0.4)
                )
                
                # Second data frame where column has 'wrong' name that does not match aes()
                df2 <- data.frame(
                    x          = LETTERS[1:5],
                    Proportion = c(0.1,0.2,0.1,0.2,0.4)
                )
                
                plot_fun <- function(df) {
                    p1 <- ggplot(df, aes(Response, Proportion)) +
                        geom_bar(stat='identity')
                        return(p1)
                }
                
                # All tests succeed
                #z <- capture.output(
                z <- function() {
                    x <- capture.output(
                    y <- test_that("Scale is labelled 'Proportion'",{
                        p <- plot_fun(d)
                        expect_true(is.ggplot(p))
                        expect_identical(p$labels$y, "Proportion")
                    
                        p <- plot_fun(df2)
                        expect_true(is.ggplot(p))
                        expect_identical(p$labels$y, "Proportion")
                    })
                    )
                    return(c(y, strip_style(x)))
                }
                #)
                
                # Second test with data frame df2 fails
                test_that("Printing ggplot object actually works",{
                    p <- plot_fun(df)
                    expect_error(print(p), NA)
                
                    p <- plot_fun(df2)
                    expect_error(print(p), NA)
                })
                
                        export('z', z)
            `);

            z = Polyglot.import('z');
            ss = z();
            const util = require('util');
            console.log(util.inspect(ss, false, null, true));

        }).timeout(100000);
        });

});