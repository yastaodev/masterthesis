const BigDecimalClass = Java.type('java.math.BigDecimal');
const MathContext = Java.type('java.math.MathContext');
const lyKmFactorAsString = "0.00000000000010570";

module.exports.fromLyToKm = function (km) {
    var result = new BigDecimalClass(km).divide(new BigDecimalClass(lyKmFactorAsString), MathContext.DECIMAL128);
    return result.toString();
};

module.exports.fromKmToLy = function (ly) {
    var result = new BigDecimalClass(ly).multiply(new BigDecimalClass(lyKmFactorAsString));
    return result.toString();
}

//module.exports = {fromLyToKm, fromKmToLy};