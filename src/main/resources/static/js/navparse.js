let nav_dashboard = document.getElementById("nav-dashboard");
let nav_instock = document.getElementById("nav-instock");
let nav_outstock = document.getElementById("nav-outstock");
let nav_manage = document.getElementById("nav-manage");
let nav_log = document.getElementById("nav-log");
let nav_logout = document.getElementById("nav-logout");
let nav_brand = document.getElementById("nav-brand");
let token = sessionStorage.getItem("Access-Token");
let emid = sessionStorage.getItem("emId");


if (token.length > 0) {
    nav_dashboard.setAttribute("href", nav_dashboard.getAttribute("href")
        + "?Access-Token=" + token);
    nav_instock.setAttribute("href", nav_instock.getAttribute("href")
        + "?Access-Token=" + token);
    nav_outstock.setAttribute("href", nav_outstock.getAttribute("href")
        + "?Access-Token=" + token);
    nav_manage.setAttribute("href", nav_manage.getAttribute("href")
        + "?Access-Token=" + token);
    nav_log.setAttribute("href", nav_log.getAttribute("href")
        + "?Access-Token=" + token);
    nav_brand.setAttribute("href", nav_brand.getAttribute("href")
        + "Access-Token=" + token);
    nav_logout.setAttribute("href", nav_logout.getAttribute("href")
        + "?emid=" + emid);
}