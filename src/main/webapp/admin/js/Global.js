/**
 * @Author  apple on 2017/7/10.
 */
var HOST=Globle_Host;

/* String Protorype Extension */
String.prototype.format = function() {
    var newStr = this, i = 0;
    while (/%s/.test(newStr)) {
        newStr = newStr.replace("%s", arguments[i++])
    }
    return newStr;
};

//Cookie存储   onLoad="checkCookie()"
function getCookie(c_name)
{
    if (document.cookie.length>0)
    {
        c_start=document.cookie.indexOf(c_name + "=")
        if (c_start!=-1)
        {
            c_start=c_start + c_name.length+1
            c_end=document.cookie.indexOf(";",c_start)
            if (c_end==-1) c_end=document.cookie.length
            return unescape(document.cookie.substring(c_start,c_end))
        }
    }
    return ""
}
function setCookie(c_name,value,expiredays)
{
    var exdate=new Date()
    exdate.setDate(exdate.getDate()+expiredays)
    document.cookie=c_name+ "=" +escape(value)+
        ((expiredays==null) ? "" : ";expires="+exdate.toGMTString())
}
function setPathCookie(c_name,value,expiredays,path)
{
    var exdate=new Date()
    exdate.setDate(exdate.getDate()+expiredays)
    document.cookie=c_name+ "=" +escape(value)+
        ((expiredays==null) ? "" : ";expires="+exdate.toGMTString())+
        ";path=/"+path+";";
}
function setCookieMy(c_name,value,expiredays)
{
    var exdate=new Date()
    exdate.setDate(exdate.getDate()+expiredays)
    document.cookie=c_name+ "=" +escape(value)+
        ((expiredays==null) ? "" : ";expires="+exdate.toGMTString())+
        ";path=/my;"
}
function checkCookie()
{
    username=getCookie('username')
    if (username!=null && username!="")
    {alert('Welcome again '+username+'!')}
    else
    {
        username=prompt('Please enter your name:',"")
        if (username!=null && username!="")
        {
            setCookie('username',username,365)
        }
    }
}
function delCookie(c_name) {
    if(getCookie(c_name)){
        setCookie(c_name,"");
        // document.cookie = c_name +"=; path=/; expires=Thu, 01-Jan-70 00:00:01 GMT";
    }
}

layui.use(['layer'], function() {
    var layer = layui.layer,
        $ = layui.jquery;


// //封装ajax
// function request(url,data){
//    return $.ajax({
//        url: url,
//        data: param || {},
//        type: type || 'GET'
//    });
// }
// function handleAjax(url, param, type) {
//     return request(url, param, type).then(function(resp){
// // 成功回调
//         if(resp.code==11006){
//             return resp.data; // 直接返回要处理的数据，作为默认参数传入之后done()方法的回调
//         }
//         else{
//             return $.Deferred().reject(resp.msg); // 返回一个失败状态的deferred对象，把错误代码作为默认参数传入之后fail()方法的回调
//         }
//     }, function(err){
// // 失败回调
//         console.log(err.status); // 打印状态码
//     });
// }
// ***************登录****************
function login(userID,userPs){
    //alert(userID+userPs);
    var data={
        loginName:userID,
        password:userPs
    };
    var url="/system.user.login.hsr";
    $.ajax({
        url: HOST+url,
        type: 'POST',
        data: JSON.stringify(data),
        dataType: "json",
        success: function(result){
            if(result.code==11000){
                layer.open({
                    type: 1,
                    shade: false,
                    title: false, //不显示标题
                    content: $('#layer_error'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
                    cancel: function(){
                        // window.location.href="login.html";
                        $('#passWord').val("");
                    }
                });
            }else{
                var loginResult = JSON.stringify(result.value);
                // 将字符串存入cookie
                setCookie('user',loginResult,24*60*60*1000);
                setCookieMy('loginResult',loginResult,24*60*60*1000);
                // alert(loginResult);
                 window.location.href = "index.html";
                // var token = JSON.parse(getCookie('user')).token;
                // alert(token);
            }

        },
        error:function(xhr){
            alert("错误提示： " + xhr.status + " " + xhr.statusText);
        }
    });
}
    $('#login').click(function(){
        // alert(0);
        login($('#userName').val(),$('#passWord').val());
    });

});

// base64加密开始
var keyStr = "ABCDEFGHIJKLMNOP" + "QRSTUVWXYZabcdef" + "ghijklmnopqrstuv"
    + "wxyz0123456789+/"+"=" ;

function encode64(input) {

    var output = "";
    var chr1, chr2, chr3 = "";
    var enc1, enc2, enc3, enc4 = "";
    var i = 0;
    do {
        chr1 = input.charCodeAt(i++);
        chr2 = input.charCodeAt(i++);
        chr3 = input.charCodeAt(i++);
        enc1 = chr1 >> 2;
        enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
        enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
        enc4 = chr3 & 63;
        if (isNaN(chr2)) {
            enc3 = enc4 = 64;
        } else if (isNaN(chr3)) {
            enc4 = 64;
        }
        output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2)
            + keyStr.charAt(enc3) + keyStr.charAt(enc4);
        chr1 = chr2 = chr3 = "";
        enc1 = enc2 = enc3 = enc4 = "";
    } while (i < input.length);

    return output;
}
// ***************退出****************
function logOutSys() {
    $.ajax({
        url: HOST + 'system.user.logout' + '.hsr',
        type: 'POST',
        data: JSON.stringify({}),
        async: false,
        headers: {
            token: this.getCookie('user') === "" ? "" : JSON.parse(this.getCookie('user')).token
        },
        dataType: "json",
        success: function (result) {
            setPathCookie('loginResult',"",24*60*60*1000,'my');
            setPathCookie('user',"",24*60*60*1000,'admin');
            window.location.href = "login.html";

        },
        error: function (xhr) {
            alert("错误提示： " + xhr.status + " " + xhr.statusText);
        }
    });
}