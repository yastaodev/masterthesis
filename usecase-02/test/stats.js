var expect = require("chai").expect;
var stats = require("../app/stats");
const crypto = require('crypto');
const fs = require('fs');
const util = require('util');
const shell = require('shelljs')

describe("Statistics", function () {
    describe("How long does it need to cover the distance?", function () {
        it("plots a comparison graph for a given distance (No R-Tests)", function () {
            stats.plotStats(1, false);

            function calcHash(img) {
                const fileBuffer = fs.readFileSync(img);
                const hashSum = crypto.createHash('sha256');
                hashSum.update(fileBuffer);
                const hex = hashSum.digest('hex');
            }

            expect(calcHash('test/stats.png')).to.equal(calcHash('tmp/stats.png'));
        }).timeout(999999);

        it("plots a comparison graph for a given distance (embedded R-Snippet)", function () {
            polyglotEval(false);
        }).timeout(999999);

        it("plots a comparison graph for a given distance (loaded R-Script)", function () {
            polyglotEval(true);
        }).timeout(999999);

        it("plots a comparison graph for a given distance (using extern runtime)", function () {
            shell.exec('./test/plot-ext.r');
        }).timeout(999999);
    });

});

function polyglotEval(fromFile) {
    if (fromFile == true) {
        Polyglot.evalFile('R', 'test/plot-int.r');
    } else {
        Polyglot.eval('R',
            `# Imports
            library(ggplot2)
            library(testthat)
            library(crayon)
            
            # First data frame (Correct)
            df1 <- data.frame(
                Objects = c("Human","Cheetah","Train","Airplane","Rocket"),
                Years = c(0.1,0.2,0.1,0.2,0.4)
            )
            
            # Second data frame (Wrong)
            df2 <- data.frame(
                Items = c("Human","Cheetah","Train","Airplane","Rocket"),
                Years = c(10,20,30,40,50)
            )
            
            plot_fun <- function(df) {
                pt <- ggplot(df, aes(Objects, Years)) +
                    geom_bar(stat='identity')
                    return(pt)
            }
            
            # All tests succeed
            f1 <- function() {
                c1 <- capture.output(
                    t1 <- test_that("Check label 'Years'",{
                        p <- plot_fun(df1)
                        expect_true(is.ggplot(p))
                        expect_identical(p$labels$y, "Years")
                    
                        p <- plot_fun(df2)
                        expect_true(is.ggplot(p))
                        expect_identical(p$labels$y, "Years")
                    })
                )
                return(c(t1, strip_style(c1)))
            }
            # Second test with data frame df2 fails
            f2 <- function() {
                c2 <- capture.output(
                    t2 <- test_that("Printing plot object",{
                        p <- plot_fun(df1)
                        expect_error(print(p), NA)
                    
                        p <- plot_fun(df2)
                        expect_error(print(p), NA)
                    })
                )
                return(c(t2, strip_style(c2)))
            }
            
            export('f1', f1)
            export('f2', f2)
        `);
    }

    f1 = Polyglot.import('f1');
    res1 = f1();
    console.log(util.inspect(res1, false, null, true));
    expect(res1[0]).to.equal("TRUE");

    f2 = Polyglot.import('f2');
    res2 = f2();
    console.log(util.inspect(res2, false, null, true));
    expect(res2[0]).to.equal("TRUE");
}
