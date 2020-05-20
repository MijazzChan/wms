var things = [];
window.lableSet = [];
window.dataSet = [];

function renderemployee(){
    $.ajax("/api/getemployee", {
            type: "get",
            contentType: false,
            processData: false,
            success: function (data) {
                things = data["content"];
                var str = "";
                for (i in things) {
                    str = str + "<tr>" + "<td>" + i + "</td>" + "<td>" + things[i]["emId"] + "</td>" + "<td>" + things[i]["emName"] + "</td>" + "<td>" + ((things[i]["emSex"] === 1) ? "男" : "女") + "</td>" + "<td>" + ((things[i]["admin"] === true) ? "是" : "否") + "</td>" + "<td><button class='btn btn-sm btn-danger' onclick='delemployee(this)' id='" + things[i]["emId"] + "'>删除</button></td>" + "</tr>";
                }
                document.getElementById("mantable").innerHTML = str;
            },
            error: function () {
                alert('Error ajax to server.');
            }
        }
    );
}


function delemployee(obj) {
    var emId = obj.id;
    var formData = new FormData();
    formData.append("emid", emId);
    $.ajax("/api/delemployee", {
        type: "post",
        contentType: false,
        processData: false,
        data: formData,
        success: function (data) {
            obj.className = "btn btn-sm btn-success";
            obj.innerHTML = "成功";
        },
        error: function () {
            alert('Err ajax');
        }
    })
}

renderemployee();