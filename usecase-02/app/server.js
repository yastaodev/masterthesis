var express = require("express");
var converter = require("./converter");
var stats = require("./stats");

var app = express();

app.get("/fromLyToKm", function (req, res) {
    var km = converter.fromLyToKm(String(req.query.ly));
    res.send(km);
});

app.get("/fromKmToLy", function (req, res) {
    var ly = converter.fromKmToLy(String(req.query.km));
    res.send(ly);
});
//curl localhost:3000/stats?ly=1&open=true
app.get("/stats", function(req, res) {
    stats.plotStats(String(req.query.ly), req.query.popup);
    res.send("SUCCESS");
});

app.listen(3000);
