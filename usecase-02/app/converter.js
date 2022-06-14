const BigDecimalClass = Java.type('java.math.BigDecimal');
const MathContext = Java.type('java.math.MathContext');
const lyKmFactorAsString = "0.00000000000010570";

module.exports.fromLyToKm = function (ly) {
    var result = new BigDecimalClass(ly).divide(new BigDecimalClass(lyKmFactorAsString), MathContext.DECIMAL128);
    return result.toString();
};

module.exports.fromKmToLy = function (km) {
    var result = new BigDecimalClass(km).multiply(new BigDecimalClass(lyKmFactorAsString));
    return result.toString();
}