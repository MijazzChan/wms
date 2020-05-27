var things = [];
window.lableSet = [];
window.dataSet = [];

function renderemployee(){
    $.ajax("/adminapi/getemployee", {
        type: "get",
        contentType: false,
        processData: false,
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Access-Token", sessionStorage.getItem("Access-Token"));
        },
        success: function (data) {
            if (data.code === 200) {
                things = data["content"];
                var str = "";
                for (i in things) {
                    str = str + "<tr>" + "<td>" + i + "</td>" + "<td>" + things[i]["emId"] + "</td>" + "<td>" + things[i]["emName"] + "</td>" + "<td>" + ((things[i]["emSex"] === 1) ? "男" : "女") + "</td>" + "<td>" + ((things[i]["admin"] === true) ? "是" : "否") + "</td>" + "<td><button class='btn btn-sm btn-primary' onclick='resetemployee(this)' emid='" + things[i]["emId"] + "'>重置</button>&nbsp;&nbsp;" + "<button class='btn btn-sm btn-danger' onclick='delemployee(this)' emid='" + things[i]["emId"] + "'>删除</button></td>" + "</tr>";
                }
                document.getElementById("mantable").innerHTML = str;
            } else if (data.code === 302) {
                alert("你无权访问");
            }
        },
            error: function () {
                alert('Error ajax to server.');
            }
        }
    );
}


function delemployee(obj) {
    var emId = obj.getAttribute("emid");
    var formData = new FormData();
    formData.append("emid", emId);
    $.ajax("/adminapi/delemployee", {
        type: "post",
        contentType: false,
        processData: false,
        data: formData,
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Access-Token", sessionStorage.getItem("Access-Token"));
        },
        success: function (data) {
            if (data.code === 200) {
                obj.className = "btn btn-sm btn-success";
                obj.innerHTML = "成功";
            } else if (data.code === 300) {
                obj.className = "btn btn-sm btn-danger";
                obj.innerHTML = "失败";
            } else {
                alert("你无权访问");
            }

        },
        error: function () {
            alert('Err ajax');
        }
    })
}

function resetemployee(obj) {
    var emId = obj.getAttribute("emid");
    var formData = new FormData();
    formData.append("emid", emId);
    $.ajax("/adminapi/resetemployee", {
        type: "post",
        contentType: false,
        processData: false,
        data: formData,
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Access-Token", sessionStorage.getItem("Access-Token"));
        },
        success: function (data) {
            if (data.code === 200) {
                obj.className = "btn btn-sm btn-success";
                obj.innerHTML = "成功";
            } else if (data.code === 300) {
                obj.className = "btn btn-sm btn-danger";
                obj.innerHTML = "错误";
            } else {
                alert("你无权访问");
            }
        },
        error: function () {
            alert('Err ajax');
        }
    });
}

renderemployee();