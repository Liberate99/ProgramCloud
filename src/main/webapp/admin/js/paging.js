/**
 * Paging 组件
 * @description 基于laytpl 、laypage、layer 封装的组件
 * @author Van zheng_jinfan@126.com
 * @link http://m.zhengjinfan.cn
 * @license MIT
 * @version 1.0.1
 */
layui.define(['layer', 'laypage', 'laytpl'], function (exports) {
    "use strict";
    var $ = layui.jquery,
        layer = parent.layui.layer === undefined ? layui.layer : parent.layui.layer,
        laytpl = layui.laytpl;

    var Paging = function () {
        this.config = {
            url: undefined, //数据远程地址
            type: 'POST', //数据的获取方式  get or post
            elem: undefined, //内容容器
            params: {}, //获取数据时传递的额外参数
            openWait: false, //加载数据时是否显示等待框 
            tempElem: undefined, //模板容器
            tempType: 0, //如果等于0则需要设置模板容器，1为提供模板内容
            paged: true,//是否显示分页组件
            pageConfig: { //参数应该为object类型
                elem: undefined,
                pageSize: 15 //分页大小
            },
            success: undefined, //type:function
            fail: function (res) {
                console.log(res.msg);
                //layer.msg(res.msg, { icon: 2 });
            }, //type:function
            complate: undefined, //type:function
            serverError: function (xhr, status, error) { //ajax的服务错误
                throwError("错误提示： " + xhr.status + " " + xhr.statusText);
            }
        };
    };
	/**
	 * 版本号
	 */
    Paging.prototype.v = '1.0.3';

	/**
	 * 设置
	 * @param {Object} options
	 */
    Paging.prototype.set = function (options) {
        var that = this;
        $.extend(true, that.config, options);
        return that;
    };
	/**
	 * 初始化
	 * @param {Object} options
	 */
    Paging.prototype.init = function (options) {
        var that = this;
        $.extend(true, that.config, options);
        var _config = that.config;
        if (_config.url === undefined) {
            throwError('Paging Error:请配置远程URL!');
        }
        if (_config.elem === undefined) {
            throwError('Paging Error:请配置参数elem!');
        }
        if ($(_config.elem).length === 0) {
            throwError('Paging Error:找不到配置的容器elem!');
        }
        if (_config.tempType === 0) {
            if (_config.tempElem === undefined) {
                throwError('Paging Error:请配置参数tempElem!');
            }
            if ($(_config.tempElem).length === 0) {
                throwError('Paging Error:找不到配置的容器tempElem!');
            }
        }
        if (_config.paged) {
            var _pageConfig = _config.pageConfig;
            if (_pageConfig.elem === undefined) {
                throwError('Paging Error:请配置参数pageConfig.elem!');
            }
        }
        if (_config.type.toUpperCase() !== 'GET' && _config.type.toUpperCase() !== 'POST') {
            throwError('Paging Error:type参数配置出错，只支持GET或都POST');
        }
        var data_init=_config.params;
        data_init.page.size=_config.pageConfig.pageSize;
       /* {
            page:{
                current:1,
                size:_config.pageConfig.pageSize
            }
        }*/
        //alert('Data='+JSON.stringify(_config.params));
        that.get(JSON.stringify(data_init));

        return that;
    };
	/**
	 * 获取数据
	 * @param {Object} options
	 */
    Paging.prototype.get = function (options) {
        var that = this;
        var _config = that.config;

        //此处有修改:2018-02-27
        _config.params = JSON.parse(options);

        var loadIndex = undefined;
        if (_config.openWait) {
            loadIndex = layer.load(2);
        }
        //默认参数
        var df = {
            pageIndex: 1,
            pageSize: _config.pageConfig.pageSize
        };
        // $.extend(true, _config.params, df, options);
        $.ajax({
            type: _config.type,
            url: _config.url,
            data: options,
            dataType: 'json',
            headers:{
                token:getCookie('user')==="" ?"":JSON.parse(getCookie('user')).token
            },
            success: function (result, status, xhr) {
                if (loadIndex !== undefined)
                    layer.close(loadIndex); //关闭等待；
                // alert("获取数据"+result.code+getCookie('user')==="" ?"":JSON.parse(getCookie('user')).token);
                if(result.code==11006){
                    layer.open({
                        type: 1,
                        shade: false,
                        title: false, //不显示标题
                        content:$('#layer_notice', parent.document), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
                        cancel: function(){
                            window.parent.location.href="login.html";
                        }
                    });
                    // layer.msg("登录过期！")
                }
                if (result.code==0) {
                  //  alert("获取数据成功");
                    //获取模板
                    var tpl = _config.tempType === 0 ? $(_config.tempElem).html() : _config.tempElem;
                    //渲染数据
                    laytpl(tpl).render(result, function (html) {
                        if (_config.renderBefore) {
                            _config.renderBefore(html, function (formatHtml) {
                                $(_config.elem).html(formatHtml);
                            }, result.value.records);
                        }
                        else {
                            $(_config.elem).html(html);
                        }
                    });
                    if (_config.paged) {
                        if (result.value.total === null || result.value.total === undefined) {
                            throwError('Paging Error:请返回数据总数！');
                            return;
                        }
                        var _pageConfig = _config.pageConfig;
                        var pageSize = result.value.size;
                        var pages = result.value.pages ;
                        var defaults = {
                            cont: $(_pageConfig.elem),
                            curr: result.value.current,
                            pages: pages,
                            jump: function (obj, first) {
                                //得到了当前页，用于向服务端请求对应数据
                                var curr = obj.curr;
                                if (!first) {
                                    var nextPage = _config.params;
                                   nextPage.page.current=curr;
                                    nextPage.page.size=pageSize;
                                    that.get(JSON.stringify(nextPage));
                                }
                            }
                        };
                        // $.extend(defaults, _pageConfig); //参数合并
                        layui.laypage(defaults); //调用laypage组件渲染分页
                    }
                    if (_config.success) {
                        _config.success(); //渲染成功
                    }
                } else {
                    var thLength = $(_config.elem).siblings('thead').find('th').length;
                    $(_config.elem).html('<tr><td colspan="' + thLength + '" style="text-align:left;">' + result.msg + '</td></tr>');
                    if (_config.fail) {
                        _config.fail(result); //获取数据失败
                    }
                }
                if (_config.complate) {
                    _config.complate(); //渲染完成
                }
            },
            error: function (xhr, status, error) {
                if (loadIndex !== undefined)
                    layer.close(loadIndex); //关闭等待层
                _config.serverError(xhr, status, error); //服务器错误
            }
        });
    };
	/**
	 * 抛出一个异常错误信息
	 * @param {String} msg
	 */
    function throwError(msg) {
        throw new Error(msg);
    };

    var paging = new Paging();
    exports('paging', function (options) {
        return paging.set(options);
    });
});
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