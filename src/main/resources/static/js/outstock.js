var tick = "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"32\" height=\"32\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\" class=\"feather feather-check\"><polyline points=\"20 6 9 17 4 12\"></polyline></svg>";
var cross = "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"24\" height=\"24\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\" class=\"feather feather-alert-triangle\"><path d=\"M10.29 3.86L1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0z\"></path><line x1=\"12\" y1=\"9\" x2=\"12\" y2=\"13\"></line><line x1=\"12\" y1=\"17\" x2=\"12.01\" y2=\"17\"></line></svg>";
var person = localStorage.getItem('id');
freshtable();

function freshtable() {
    var things = [];
    $.ajax("/api/getstorage",{
            type: "get",
            contentType: false,
            processData: false,
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Access-Token", sessionStorage.getItem("Access-Token"));
        },
            success: function (data) {
                things = data["content"];
                var str = "";
                for (i in things) {
                    str = str+"<tr>"+"<td>"+i+"</td>"+"<td>" + things[i]["itemId"] + "</td>" + "<td>" + things[i]["itemName"] + "</td>" + "<td>" + things[i]["itemAmount"] + "</td>" + "</tr>";
                }
                document.getElementById("storagetable").innerHTML = str;
            },
            error: function () {
                alert('Error ajax to server.');
            }
        }
    )
}

function outputItem() {
    var formData = new FormData();
    formData.append("itemid", $("#itemId1").val());
    formData.append("viaid", sessionStorage.getItem("emId"));
    formData.append("itemcount", $("#itemCount1").val());
    $.ajax("/api/outputitem", {
        type: "post",
        contentType: false,
        processData: false,
        data: formData,
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Access-Token", sessionStorage.getItem("Access-Token"));
        },
        success: function (data) {
            if (data.code == 200) {
                document.getElementById("status1").innerHTML = tick;
                freshtable();
            }else {
                document.getElementById("status1").innerHTML = cross + data.content;
            }
        },
        error: function () {
            alert('Err ajax');
        }
    })
}

function delItem() {
    var formData = new FormData();
    formData.append("itemid", $("#itemId2").val());
    $.ajax("/adminapi/delitem", {
        type: "post",
        contentType: false,
        processData: false,
        data: formData,
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Access-Token", sessionStorage.getItem("Access-Token"));
        },
        success: function (data) {
            if (data.code == 200) {
                document.getElementById("status2").innerHTML = tick;
                freshtable();
            }else {
                document.getElementById("status2").innerHTML = cross + data.content;
            }
        },
        error: function () {
            alert('Err ajax');
        }
    })
}

