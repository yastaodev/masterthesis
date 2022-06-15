const converter = require("./converter")

module.exports.plotStats = function (ly) {
    let km = converter.fromLyToKm(ly);
    //TODO Use relative path
    Polyglot.eval('R',
        `# Km/h & Km
        calc <- function(speed, distance) {
            return((distance / speed) / 8760)
        }
        plot <- function(km) {
            library(ggplot2)
            num <- as.double(km)
            data <- data.frame(
              Objects=c("Human","Cheetah","Train","Airplane","Rocket") ,  
              Years=c(calc(9.4, num),calc(130, num),calc(300, num),calc(900, num),calc(27000, num))
            )
            
            ggplot(data, aes(x=Objects, y=Years)) + 
              geom_bar(stat = "identity") +
              theme(text = element_text(size = 50), plot.margin=unit(c(5,5,0,0), "cm"), panel.spacing=unit(c(0,0,0,0), "cm")) +
              coord_flip()
             
            ggsave("/opt/work/workspaces/idea/master-thesis/usecase-02/tmp/r-data.png")
        }
        export('plot', plot)`);
    plotFunction = Polyglot.import('plot');
    plotFunction(km);

    var desktop = Java.type('java.awt.Desktop')
    var FileClass = Java.type('java.io.File');
    var file = new FileClass("/opt/work/workspaces/idea/master-thesis/usecase-02/tmp/r-data.png");
    desktop.getDesktop().open(file);
}