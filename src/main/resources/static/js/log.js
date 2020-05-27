function getrecentin(is10) {
    var things = [];
    if (is10){
        ajaxUrl = "/adminapi/getrecentin10";
    }else {
        ajaxUrl = "/adminapi/getrecentin";
    }
    $.ajax(ajaxUrl, {
            type: "get",
            contentType: false,
            processData: false,
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Access-Token", sessionStorage.getItem("Access-Token"));
        },
            success: function (data) {
                if (data.code === 200) {
                    things = data["content"];
                    var str = "";
                    for (i in things) {
                        str = str + "<tr>" + "<td>" + things[i]["transactionId"] + "</td>" + "<td>" + things[i]["date"] + "</td>" + "<td>" + things[i]["itemId"] + "</td>" + "<td>" + things[i]["inCount"] + "</td>" + "<td>" + things[i]["viaId"] + "</td>" + "</tr>";
                    }
                    document.getElementById("intable").innerHTML = str;
                } else if (data.code === 302) {
                    alert('你无权访问');
                }
            },
            error: function () {
                alert('Error ajax to server.');
            }
        }
    );
}

function getrecentout(is10) {
    var things = [];
    if (is10){
        ajaxUrl = "/adminapi/getrecentout10";
    }else {
        ajaxUrl = "/adminapi/getrecentout";
    }
    $.ajax(ajaxUrl, {
            type: "get",
            contentType: false,
            processData: false,
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Access-Token", sessionStorage.getItem("Access-Token"));
        },
            success: function (data) {
                if (data.code === 200) {
                    things = data["content"];
                    var str = "";
                    for (i in things) {
                        str = str + "<tr>" + "<td>" + things[i]["transactionId"] + "</td>" + "<td>" + things[i]["date"] + "</td>" + "<td>" + things[i]["itemId"] + "</td>" + "<td>" + things[i]["outCount"] + "</td>" + "<td>" + things[i]["viaId"] + "</td>" + "</tr>";
                    }
                    document.getElementById("outtable").innerHTML = str;
                } else if (data.code === 302) {
                    alert('你无权访问');
                }

            },
            error: function () {
                alert('Error ajax to server.');
            }
        }
    );

}

function change10(obj) {
    is10 = obj.getAttribute("status");
    if (is10 === "is10"){
        obj.setAttribute("status", "all");
        obj.innerHTML = "全部";
        getrecentin(false);
        getrecentout(false);
    }else {
        obj.setAttribute("status", "is10");
        obj.innerHTML = "近10条";
        getrecentout(true);
        getrecentin(true);
    }
}

getrecentin(true);
getrecentout(true);