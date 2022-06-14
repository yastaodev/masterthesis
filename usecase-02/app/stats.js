const converter = require("./converter")

module.exports.getStatsLy = function (ly) {
    var km = converter.fromLyToKm(ly);
    getStatsKm(km);
}

module.exports.getStatsKm = function (km) {
    const speedMap = new Map();
    speedMap.set('human', 9.4);
    speedMap.set('cheetah', 130);
    speedMap.set('train', 300);
    speedMap.set('airplane', 900);
    speedMap.set('rocket', 27000);

    Polyglot.eval('R',
        `
        # Load ggplot2
        library(ggplot2)
        
        # Create data
        data <- data.frame(
          name=c("A","B","C","D","E") ,  
          value=c(3,12,5,18,45)
          )
        
        # Barplot
        ggplot(data, aes(x=name, y=value)) + 
          geom_bar(stat = "identity") +
          coord_flip()
          
        # ggsave("r-data.pdf")
        ggsave("/opt/work/workspaces/idea/master-thesis/usecase-02/tmp/r-data.png")
        `);
}