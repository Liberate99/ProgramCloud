/**
 * @Author  apple on 2017/7/14.
 */
layui.define('layer', function (exports) {
    "use strict";
    var $ = layui.jquery,
        layerTips = parent.layer === undefined ? layui.layer : parent.layer,
        layer= layui.layer;

    var HOST = Globle_Host;
    var busajax = {
        getCookie: function (c_name) {
            if (document.cookie.length > 0) {
                var c_start = document.cookie.indexOf(c_name + "=")
                if (c_start != -1) {
                    c_start = c_start + c_name.length + 1
                    var c_end = document.cookie.indexOf(";", c_start)
                    if (c_end == -1) c_end = document.cookie.length
                    return unescape(document.cookie.substring(c_start, c_end))
                }
            }
            return "";
        },
        busajax: function (url, value,index, callback,noTips,isAsync) {
            //noTips:是否需要重新加载页面,默认或不填为true
            if(noTips==null) noTips=true;
            //isAsync:是否异步请求,默认或不填为false,即默认请求是同步的
            if(isAsync==null) isAsync=false;
            $.ajax({
                url: HOST + url + '.hsr',
                type: 'POST',
                data: JSON.stringify(value),
                async: isAsync,
                headers: {
                    token: this.getCookie('user') === "" ? "" : JSON.parse(this.getCookie('user')).token
                },
                dataType: "json",
                success: function (result) {
                    // console.log(noTips+"busajax"+JSON.stringify(result));
                    if (result.code == 11006) {
                        layerTips.msg("登录过期！")
                        setTimeout(function () {
                            parent.layer === undefined ? window.location.href="login.html" : window.parent.location.href="login.html";
                        }, 2000);

                        console.log(result.msg);
                    } else if(result.code==0){
                        if(noTips){
                            result.msg!=null ?layerTips.msg(result.msg):"";
                        setTimeout(function () {
                            if(index!=null){
                                layerTips.close(index);
                            }
                            location.reload(); //刷新
                        }, 2000);
                        }
                    }else {
                        layerTips.msg(result.msg);
                    }
                        callback==null?"":callback(result);

                },
                error: function (xhr) {
                    alert("错误提示： " + xhr.status + " " + xhr.statusText);
                }
            });
        },

        v: '1.0.1'
    };


    exports('busajax', busajax);
});