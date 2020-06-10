var user = "";
var identityRole = null;

$(document).ready(function () {
    user = JSON.parse(getCookie("user"));
    //判断用户身份
    if(!user.name && (user.identityRole == "1" || user.identityRole == "2")){
        // 老师 或 管理员
        //layer.msg("老师或管理员", {time:3000});
        console.log("老师 或 管理员");
    }
});
