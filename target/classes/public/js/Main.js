$(document).ready(function () {

    $("#wordcards").click(function () {
        $.ajax({
            url: "/wordcards",
            type: "GET",
            async: true,
            success: function (resp) {
                var n = JSON.parse(resp);
                var delButton = "<button type='button' class='del-button glyphicon glyphicon-remove-sign'></button>";
                $("#big-table").removeAttr("style");
                $("#big-table").html(
                    "<tr><th>ID</th>" +
                    "<th>File</th>" +
                    "<th>Theme</th>" +
                    "<th>HUN</th>" +
                    "<th>EN</th>" +
                    "<th>Remove</th>" +
                    "</tr>");
                for (var i = 0; i < n.length; i++) {
                    var obj = n[i];
                    var id = obj["id"]
                    var picLoc = obj["picLocation"];
                    var theme = obj["theme"];
                    var hun = obj["hun"];
                    var eng = obj["eng"];
                    $("#big-table").append(
                        "<tr><td>" + id + "</td>" +
                        "<td>" + picLoc + "</td>" +
                        "<td>" + theme + "</td>" +
                        "<td>" + hun + "</td>" +
                        "<td>" + eng + "</td>" +
                        "<td>" + delButton + "</td></tr>"
                    );
                }
            },
            error: function () {
                alert('Problem with function')
            }
        });
    });
    $("#exam-button").click(function () {
        $.ajax({
            url: "/exam_cards",
            type: "GET",
            async: true,
            success: function (resp) {
                alert(resp);
                $("#get-card").removeAttr("style");
            },
            error: function () {
                alert('Problem with function')
            }
        });
    });

    $("#get-card").click( function () {
        $.ajax({
            url: "/getcard",
            type: "GET",
            async: true,
            success: function (resp) {
                alert(resp);
                var n = JSON.parse(resp);
                $('#card-image').attr("src", n["picLocation"]);
                alert(n["picLocation"]);
            },
            error: function () {
                alert('Problem with function')
            }
        });
    });
});