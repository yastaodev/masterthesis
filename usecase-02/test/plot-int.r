# Imports
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