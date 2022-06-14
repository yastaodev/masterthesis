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

stats.getStatsKm(50);

app.listen(3000);
