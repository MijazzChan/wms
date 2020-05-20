window.lableSet = [];
window.dataSet = [];
window.queryselection = 1;

function getdataandlable() {
    var things = [];

    $.ajax("/api/getstoragedesc", {
            type: "get",
            contentType: false,
            processData: false,
            success: function (data) {
                things = data["content"];
                for (i = 0; i < 7; i++) {
                    lableSet.push(things[i]["itemName"]);
                    dataSet.push(things[i]["itemAmount"]);
                }
            },
            error: function () {
                alert('Error ajax to server.');
            }
        }
    )
}

function renderchart() {
    var ctx = document.getElementById("myChart");
    var myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: lableSet,
            datasets: [{
                data: window.dataSet,
                backgroundColor: '#6610f2',
                borderColor: '#6f42c1',
                borderWidth: 2,
                pointBackgroundColor: '#6f42c1'
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: false
                    }
                }]
            },
            legend: {
                display: false,
            }
        }
    });
}


function rendertable() {
    var things = [];
    $.ajax("/api/getstorage", {
            type: "get",
            contentType: false,
            processData: false,
            success: function (data) {
                things = data["content"];
                var str = "";
                for (i in things) {
                    str = str + "<tr>" + "<td>" + i + "</td>" + "<td>" + things[i]["itemId"] + "</td>" + "<td>" + things[i]["itemName"] + "</td>" + "<td>" + things[i]["itemAmount"] + "</td>" + "</tr>";
                }
                document.getElementById("storagetable").innerHTML = str;
            },
            error: function () {
                alert('Error ajax to server.');
            }
        }
    )
}

function renderquerytable(data) {
    var things = [];
    things = data["content"];
    var str = "";
    for (i in things) {
        str = str + "<tr>" + "<td>" + i + "</td>" + "<td>" + things[i]["itemId"] + "</td>" + "<td>" + things[i]["itemName"] + "</td>" + "<td>" + things[i]["itemAmount"] + "</td>" + "</tr>";
    }
    document.getElementById("storagetable").innerHTML = str;
}

function queryselect(obj) {
    var id = obj.id;
    var inputbox = document.getElementById("queryinput")
    $("#queryinput").val("");
    if (id === "query-id") {
        inputbox.setAttribute("placeholder", "输入商品ID以查询");
        window.queryselection = 1;
    }else if (id === "query-name") {
        inputbox.setAttribute("placeholder", "输入商品名以查询, 接受SQL pattern");
        window.queryselection = 2;
    }else if (id === "query-storage") {
        inputbox.setAttribute("placeholder", "输入库存范围以查询, 以,为区间分隔");
        window.queryselection = 3;
    }
}

function searchnow() {
    if (window.queryselection === 1) {
        var formData = new FormData();
        formData.append("itemid", $("#queryinput").val());
        $.ajax("/api/findstoragebyid", {
            type: "post",
            contentType: false,
            processData: false,
            data: formData,
            success: function (data) {
                renderquerytable(data);
            },
            error: function () {
                alert('Err ajax');
            }
        })
    }else if (window.queryselection === 2) {
        var formData = new FormData();
        formData.append("itemname", $("#queryinput").val());
        $.ajax("/api/findstoragebyname", {
            type: "post",
            contentType: false,
            processData: false,
            data: formData,
            success: function (data) {
                renderquerytable(data);
            },
            error: function () {
                alert('Err ajax');
            }
        })
    }else if (window.queryselection === 3) {
        var formData = new FormData();
        formData.append("between", $("#queryinput").val());
        $.ajax("/api/findstoragebetween", {
            type: "post",
            contentType: false,
            processData: false,
            data: formData,
            success: function (data) {
                renderquerytable(data);
            },
            error: function () {
                alert('Err ajax');
            }
        })
    }
}



getdataandlable();
renderchart();
rendertable();

$("#queryinput").keypress(function (event) {
    if (event.which === 13) {
        searchnow();
    }
})