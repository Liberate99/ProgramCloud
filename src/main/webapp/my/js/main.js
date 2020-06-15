var HOST=Globle_Host;

$(function(){
    if(getCookie('loginResult') == "" || getCookie('loginResult') == null){
    	//到此页面时，当cookie为 ""/空 时说明已登陆 直接展示课程库页面
        $(".isDisplay").css("display","none");
        $(".isDisplayMb").css("display","none");
		$("nav li").eq(1).html('<a href="courseLibrary.html">课程库</a>');
    }else{
    	//到此页面时，当cookie不为空时，展示登陆框，
		// request("gettoken","adasd",null,false);
		if($(window).width() < 700){
            $(".isDisplayMb").css("display","block");
            $(".isDisplay").css("display","none");
        }else {
            $(".isDisplay").css("display","block");
            $(".isDisplayMb").css("display","none");
			var userMain = JSON.parse(getCookie('loginResult'));
			if(userMain==null) {
                $(".isDisplayMb").css("display","none");
                $(".isDisplay").css("display","none");
			}else{
                if(userMain.photo != "" && userMain.photo != null){
                    $("#userImg").attr("src",userMain.photo)
                }
                $("#userName").text(userMain.loginName);
			}
        }
        $(".loginBox").eq(0).css("display","none");
		if(window.location.href.indexOf("index.html")>0){
			window.location.href = "myCourse.html";
		}
    }
	// var ochange = 1;
	$(".change-box").css("display","none");
	$(".change").each(function(i){
		$(this).attr("ochange",1);
		$(this).click(function(){
			$(".change-box").eq(i).toggle();
			if($(this).attr("ochange") == 1){
				$(this).text("取消");
				$(this).attr("ochange",0);
			}else{
				// if(i == 2){
				// 	$(this).text("充值");
				// }else if(i == 3 || i == 4){
				// 	$(this).text("绑定");
				// }else{
				// 	$(this).text("修改");
				// }
                $(this).text("修改");
				$(this).attr("ochange",1);
			}
		});
	});
    var identityRole = JSON.parse(getCookie('loginResult')).identityRole;
	// var userName = JSON.parse(getCookie('loginResult')).loginName;
    if(identityRole == "1" || identityRole == "2"){
        // 老师 或 管理员
        //layer.msg("老师或管理员", {time:3000});
        //console.log("老师 或 管理员");
        // var a = '<li><a href="dataStatistics.html?user_name='+ userName + ">数据统计</a></li>';
        var a = '<li><a href="dataStatistics.html">数据统计</a></li>';
        $("#cloud-navbar-collapse ul ul").prepend(a);

    }else{
        console.log("学生");

	}
});

//登陆验证
function check() {
	var userID = $("#userID").val();
	var userPs = $("#userPs").val();
	if(userID == ""){
		layer.msg('请输入用户名！', {shade:false,time: 1000, icon:5});
		return false;
	}
	if(userPs == ""){
		layer.msg('请输入密码！', {shade:false,time: 1000, icon:5});
		return false;
	}
	login(userID,userPs);
	return false;
}

//回车监听
function BindEnter(obj) {     
	// alert("捕捉回车");
	//使用document.getElementById获取到按钮对象  
	if(obj.keyCode == 13){
		check();
	}
}

//注册验证
function regCheck(){
	var identityRole = $("#identityRole").val();
	var userName = $("#userName").val();
	var userPhone = $("#userPhone").val();
	var userPassword = $("#userPassword").val();
	var userPasswordRe = $("#userPasswordRe").val();
	var userIdentify = $("#userIdentify").val();
	var oflag = 0;
	if (identityRole == "" || identityRole == null) {
		$("#identityRole").next().text("请选择用户类型");
		oflag = 1;
	}else {
		$("#identityRole").next().text("");
	}
	if(userName == ""){
		$("#userName").next().text("请输入用户名");
		oflag = 1;
	} else if (!/^[a-zA-Z0-9_-]{4,16}$/.test(userName)) {
		$("#userName").next().text("请输入正确的用户名:4到16位(字母,数字,下划线,减号)");
		oflag = 1;
	} else {
		$("#userName").next().text("");
	}
	if(userPhone == ""){
		$("#userPhone").next().text("请输入手机号");
		oflag = 1;
	}else if(!/^1[3|5|8|7][0-9]\d{4,8}$/.test(userPhone)){
		$("#userPhone").next().text("请输入正确的手机号");
		oflag = 1;
	}else {
		$("#userPhone").next().text("");
	}
	if(userPassword == ""){
		$("#userPassword").next().text("请输入密码");
		oflag = 1;
	}else if(!/^([\w\.\_]|[\u4e00-\u9fa5]){6,15}$/.test(userPassword)){
		$("#userPassword").next().text("请输入6~15位，支持中文、字母、数字、下划线和小数点");
		oflag = 1;
	}else {
		$("#userPassword").next().text("");
	}
	if(userPasswordRe == ""){
		$("#userPasswordRe").next().text("请确认密码");
		oflag = 1;
	}else if(userPassword != userPasswordRe){
		$("#userPasswordRe").next().text("密码不一致，请重新输入");
		oflag = 1;
	}else {
		$("#userPasswordRe").next().text("");
	}
	if(userIdentify == ""){
		$("#userIdentify").next().next().next().text("请输入验证码");
		oflag = 1;
	}else {
		$("#userIdentify").next().next().next().text("");
	}
	if(oflag == 1){
		return false;
	}
	if(!$("#agreement").is(':checked')){
		layer.msg("请先确认版权声明和隐私保护条款", {time: 2000, icon:6});
		return false;
	}
	register(identityRole,userName,userPhone,userPassword,userIdentify);
}
//统一图片展示大小
function courseImgs() {
	$(".courseImg").each(function () {
		var realWidth = $(this).width();
		// var realHeight = parseInt(realWidth/3*2);
		var realHeight = parseInt(realWidth*0.58);
		$(this).css("height",realHeight);
	});
}

//func 为类名.方法名 data 为参数
//如果loading为false则不显示加载层
function request(func,data,callback,type,loading){
	if(this.getCookie('loginResult') === "")window.location.href=HOST+"index.html?message=1";
	if(loading !== false){
		var index = layer.load(2, {shade: [0.01,'#ababab']});	
	}
	var callbackReturn;
	$.ajax({
		url: HOST+func+'.hsr',
        type: 'POST',
		data: JSON.stringify(data),
		async:type==null?false:type,
		headers: {
			token: this.getCookie('loginResult') === "" ? "" : JSON.parse(this.getCookie('loginResult')).token
		},
		dataType: "json",
		success: function(result){
			if(loading !== 0){
				layer.close(index);
			}
			// console.log(func+"request"+JSON.stringify(result));
			if (result.code == 11006) {
				delCookie('loginResult');
				// layer.msg('登录过期！', {time: 1000, icon:6});
				window.location.reload();
				// setTimeout(function () {window.location.href=HOST+"index.html"; }, 1000);
				console.log(result.msg);
			} else if(result.code==0){
				console.log(result.msg);
			}
			callbackReturn = callback==null?"":callback(result);

		},
		error:function(xhr){
			alert("错误提示： " + xhr.status + " " + xhr.statusText);
		}
	});
	if(callbackReturn){
		return callbackReturn;
	}
}


//Cookie存储   onLoad="checkCookie()"
function getCookie(c_name) {
	// console.log(document.cookie);
	if (document.cookie.length>0) {
		c_start = document.cookie.indexOf(c_name + "=")
		if (c_start!=-1) {
			c_start = c_start + c_name.length + 1
			c_end = document.cookie.indexOf(";",c_start)
			if (c_end==-1) c_end=document.cookie.length
			return unescape(document.cookie.substring(c_start,c_end))
		}
	}
	return ""
}
function setPathCookie(c_name,value,expiredays,path) {
    var exdate=new Date()
    exdate.setDate(exdate.getDate() + expiredays)
    document.cookie = c_name + "=" +
						escape(value) +
						((expiredays==null) ? "" : ";expires=" + exdate.toGMTString()) +
        				";path=/"+path+";";
}
function setCookie(c_name,value,expiredays) {
	console.log("设置cookie "+c_name+"："+value);
	// delCookie(c_name) ;
	var exdate=new Date()
	exdate.setDate(exdate.getDate()+expiredays)
	document.cookie=c_name+ "=" +escape(value)+
		((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
}
function checkCookie() {
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

//加密 需要引入相应的js
function base64(){
	var b = new Base64();
	var str = b.encode("admin:admin");
	alert("base64 encode:" + str);
	//解密
	str = b.decode(str);
	alert("base64 decode:" + str);
}
function md5(){
	var hash = hex_md5("123dafd");
	alert(hash)
}
function sha1(){
	var sha = hex_sha1('mima123465')
	alert(sha)
}

// ***************登录****************
function login(userID,userPs){
	var index = layer.load(2, {shade: [0.5,'#ababab']});
    var data = {
        loginName:userID,
        password:userPs
    };
    var url="system.user.login.hsr";
    $.ajax({
        url: HOST+url,
        type: 'POST',
        data: JSON.stringify(data),
        dataType: "json",
        success: function(result){
			layer.close(index);
			if(result.code == 0){
				var loginResult = JSON.stringify(result.value);
				//alert(JSON.stringify(result.value))
				// 将字符串存入cookie
                setPathCookie('user',"",24*60*60*1000,'admin');
                setCookie('loginResult',loginResult,24*60*60*1000);
				//alert(loginResult);
				window.location.href = "myCourse.html";
				// var token = JSON.parse(getCookie('loginResult')).token;
				// alert(token);
			}else {
				layer.msg(result.error, {shade:false,time: 1000, icon:5});
			}
        },
        error:function(xhr){
            alert("错误提示： " + xhr.status + " " + xhr.statusText);
        }
    });
}

// ***************检查用户名是否重复****************
function checkUser(type){
	if(type=0){
		var name=$("#newName").val()
	}else if(type=1){
		var name=$("#userName").val()
	}
	var data={
		loginName:name
	};
	var url="/system.user.loginNameCheck.hsr";
	$.ajax({
		url: HOST+url,
		type: 'POST',
		data: JSON.stringify(data),
		dataType: "json",
		success: function(result){
			console.log(JSON.stringify(result));
			//var text = '<div class="alertBg">'+
			//	'<div class="alertBox">'+
			//	'<img src="img/success.png"/>'+
			//	'<p>'+result.msg+'</p>'+
			//	'</div>'+
			//	'</div>';
			//$('body').append(text);
			if(result.code==11004){
				layer.msg(result.msg, {time: 3000, icon:5});
				$('#userName').val("");
			}
			if(type=1){
				// ***************获取验证码****************
				if(result.code==0){
					$("#codpic").attr('src',HOST+'servlet/validateCodeServlet?loginName='+$("#userName").val());
				}
			}
		},
		error:function(xhr){
			alert("错误提示： " + xhr.status + " " + xhr.statusText);
		}
	});
}
// ***************换一张验证码****************
function changePic(){
	$("#codpic").attr('src',HOST+'servlet/validateCodeServlet?loginName='+$("#userName").val()+'&date='+Date.parse(new Date()));
}
// ***************注册****************
function register(identityRole,userName,userPhone,userPassword,userIdentify) {
	var index = layer.load(2, {shade: [0.5,'#ababab']});
	var data = {
		user:{
            identityRole:identityRole,
            loginName:userName,
            password:userPassword,
            phone:userPhone,
			validateCode:userIdentify
		}
	};
	var url = "/system.user.register.hsr";
    $.ajax({
        url: HOST+url,
        type: 'POST',
        data: JSON.stringify(data),
        dataType: "json",
        success: function(result){
			layer.close(index);
			//alert(JSON.stringify(result))
			if(result.code == 0){
				var text = '<div class="alertBg">'+
					'<div class="alertBox">'+
					'<img src="img/success.png"/>'+
					'<p>注册成功，页面将于3秒后为您跳转</p>'+
					'</div>'+
					'</div>';
				$('body').append(text);
				setTimeout(function () {
					window.location.href = "index.html";
				},3000);
				console.log(result);
			}else{
				console.log(result);
				layer.msg(result.msg, {shade:false,time: 1000, icon:5});
			}
        },
        error:function(xhr){
            alert("错误提示： " + xhr.status + " " + xhr.statusText);
        }
    });
}
// ***************退出****************
function logOut() {
    request('system.user.logout',{},function callback(result) {
        delCookie('loginResult');
        setPathCookie('user',"",24*60*60*1000,'admin');
        window.location.href = "index.html";
    });
}
// ***************修改个人信息****************
function change(data){
	request('system.user.updateUserInfo',data,function callback(result) {
		if(result.code==0){
			var loginResult = JSON.stringify(result.value);
			setCookie('loginResult', loginResult, 24*60*60*1000);
			layer.msg("修改成功", {shade:false,time: 1000, icon:1});
		}
	})
}
// ***************检查修改密码****************
function passwordCheck(){
	var pwd=$("#oldPwd").val();
	var data={
		"password":pwd,
		"user":{
			"loginName":JSON.parse(getCookie('loginResult')).loginName,
			"password":JSON.parse(getCookie('loginResult')).password
		}
	}
	request('system.user.passwordCheck',data,function callback(result) {
		layer.msg(result.msg, {shade:false,time: 1000, icon:5});
	})
}

// 获取学校id
function getCollege(){
    var data = {
        busStudentInfo : {
            userId : JSON.parse(getCookie('loginResult')).id
        }
    };
    console.log(JSON.parse(getCookie('loginResult')).id);
    request('business.busStudentInfo.getStudentCollegeIDInfoFromUserID', data, function callback(result) {
        console.log(result);
        if (result.code == 0) {
            if(result.value == null){
                console.log("college information is null!")
				setCookie('collegeId', "0", 0);
                setCookie('major', "0");
            }else{
                console.log("college information get&set!")
				if(result.value[0].collegeId != null){
                    console.log(result.value[0].collegeId)
                    collegeId = result.value[0].collegeId
                    // setCookie('collegeId', result.value[0].collegeId+"", 24*60*60*1000);
                    // setCookie('major', result.value[0].major, 24*60*60*1000);
				}else {
                    // setCookie('collegeId', "0", 24*60*60*1000);
				}
            }
        } else {
            // layer.msg(result.msg,{time: 2000, icon:5});
        }
    });
}

// 获取班级id
function getClass(){
    console.log("class information");
    var data = {
        user: {
            id:JSON.parse(getCookie('loginResult')).id
        },
        busCourse: {
            id: courseId
        }
    }
    // 获取是否加入班级
    request('business.busClass.isJoinClass',data,function callback(result){
        // console.log(result);
        if (result.code == 1) {
            if(result.value == null){
                // 没有班级信息
                console.log("class information is null!")
                classId = "0"// no collage
            }else{
                console.log("class information get&set!")
                classId = result.value.id
            }
        } else {
            // layer.msg(result.msg, {time:3000});//弹出提示框
        }
    });
}

// ***************课程库****************

// ***************课程分类（字典）****************
function getDict(){
	var data={
			dict:{
				type:'course_tag'
			}
		};
	request('system.dict.get',data,function callback(result) {
		var data = {
			value : result.value
		};
		template.config("escape", false);

		if($("script#classify").length){
			var html = template('classify', data);
			$('#dict').append(html);
		}
		if($("script#classifyTag").length){
			var searchTag = template('classifyTag', data);
			$('#searchTag').empty().append(searchTag);	
		}
	})
}

// ***************课程列表 + 实验列表****************
function courseList(classify,title,page,isNewPage){
	var data={
		buscourse:{
			classify:classify,
			title:title
		},
		page:{
            current:page,
            size:8
        }
	};

	
	request('business.busCourse.getCourseByPage',data,function callback(result){
		// if(result.value.length>0){
		console.log(request.headers);
		var pages=result.value.pages;
		var current=result.value.current;
		console.log(result.value);
		if(isNewPage){
			console.log("请求重新初始化");
			 setP(classify,title,pages,current);
		}else{
			console.log("不重新初始化");
		}
			var data = {
				value : result.value.records
			};
			template.config("escape", false);
			var html_courses = template('content_new', data);
			//console.log(html);
		  //  var html1=template('content1',data);
			$('#show').html(html_courses);
			// $('#train_content').html(html_training);
			courseImgs();
		// }
	});
}


// 获取全站累计实验次数、实验时长、文件上传次数
function getFPGATotalStatistics(){
    $.ajax({
        type: 'GET',
        url: 'http://10.1.21.153/hdu/cb/getTotalStatistics',
        dataType: 'json',
        success: function(data){
            if(data.code == 0){
                document.getElementById("fpag_total_exp_num").innerText = data.result.expNum;
                document.getElementById("fpag_total_duration").innerText = data.result.duration;
                document.getElementById("fpag_total_file_upload_num").innerText = data.result.fileUploadNum;
            }else{
                console.log(data);
            }
        },
        error: function(xhr){
            console.log(xhr);
        }
    });
}

// 获取近一月 实验次数、实验时长
function getLastMonthStatistics(){
    $.ajax({
        type: 'GET',
        url: 'http://10.1.21.153/hdu/cb/getLastMonthDailyStatistics',
        dataType: 'json',
        success: function(data){
            console.log(data)
            if(data.code == 0){
                var month_exp_num = [
                						data.result.expNum[0], data.result.expNum[1], data.result.expNum[2], data.result.expNum[3], data.result.expNum[4], data.result.expNum[5], data.result.expNum[6],
									 	data.result.expNum[7], data.result.expNum[8], data.result.expNum[9], data.result.expNum[10], data.result.expNum[11], data.result.expNum[12], data.result.expNum[13],
                    					data.result.expNum[14],data.result.expNum[15],data.result.expNum[16],data.result.expNum[17], data.result.expNum[18], data.result.expNum[19], data.result.expNum[20],
										data.result.expNum[21],data.result.expNum[22],data.result.expNum[23],data.result.expNum[24], data.result.expNum[25], data.result.expNum[26], data.result.expNum[27],
                    					data.result.expNum[28],data.result.expNum[29],data.result.expNum[30]
                ];
                var month_duration = [
                    					data.result.duration[0], data.result.duration[1], data.result.duration[2], data.result.duration[3],  data.result.duration[4],  data.result.duration[5],  data.result.duration[6],
                    					data.result.duration[7], data.result.duration[8], data.result.duration[9], data.result.duration[10], data.result.duration[11], data.result.duration[12], data.result.duration[13],
                    					data.result.duration[14],data.result.duration[15],data.result.duration[16],data.result.duration[17], data.result.duration[18], data.result.duration[19], data.result.duration[20],
                    					data.result.duration[21],data.result.duration[22],data.result.duration[23],data.result.duration[24], data.result.duration[25], data.result.duration[26], data.result.duration[27],
                    					data.result.duration[28],data.result.duration[29],data.result.duration[30]
				];
                var month_file_upload_num = [
                    					data.result.fileUploadNum[0], data.result.fileUploadNum[1], data.result.fileUploadNum[2], data.result.fileUploadNum[3],  data.result.fileUploadNum[4],  data.result.fileUploadNum[5],  data.result.fileUploadNum[6],
                    					data.result.fileUploadNum[7], data.result.fileUploadNum[8], data.result.fileUploadNum[9], data.result.fileUploadNum[10], data.result.fileUploadNum[11], data.result.fileUploadNum[12], data.result.fileUploadNum[13],
                    					data.result.fileUploadNum[14],data.result.fileUploadNum[15],data.result.fileUploadNum[16],data.result.fileUploadNum[17], data.result.fileUploadNum[18], data.result.fileUploadNum[19], data.result.fileUploadNum[20],
                    					data.result.fileUploadNum[21],data.result.fileUploadNum[22],data.result.fileUploadNum[23],data.result.fileUploadNum[24], data.result.fileUploadNum[25], data.result.fileUploadNum[26], data.result.fileUploadNum[27],
                    					data.result.fileUploadNum[28],data.result.fileUploadNum[29],data.result.fileUploadNum[30]
				];
                setTotalMonthEchartsOption(month_exp_num, month_duration);
                setTotalMonthEchartsOption1(month_file_upload_num);
            }else{
                console.log(data);
                var nodata = new Array(30).fill(0);
                setTotalMonthEchartsOption(nodata,nodata);
                setTotalMonthEchartsOption1(nodata)
            }
        },
        error: function(xhr){
            console.log(xhr);
            var nodata = new Array(30).fill(0);
            setTotalMonthEchartsOption(nodata,nodata);
            setTotalMonthEchartsOption1(nodata)
        }
    });
}
function setTotalMonthEchartsOption(month_exp_num, month_duration){
    var chart_total = echarts.init(document.getElementById('Echarts_last_month'));
    var total_option = {
        title:{
            left:'center',
            text:'近一月实验次数/实验时长',
            textStyle:{
                //文字颜色
                color:'#000000',
                //字体风格,'normal','italic','oblique'
                fontStyle:'normal',
                //字体粗细 'normal','bold','bolder','lighter',100 | 200 | 300 | 400...
                fontWeight:'bold',
                //字体系列
                fontFamily:'sans-serif',
                //字体大小
                fontSize:18,
            }
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                crossStyle: {
                    color: '#999'
                }
            }
        },
        legend: {
            data: [
                {name: '实验次数'},
                {name: '实验时长'}
            ],
            textStyle: {
                color: '#000000',
                fontSize: 11
            },
            y: 'bottom',
            x: 'center',
        },
        xAxis: {
            axisLabel: {
                interval:0,
                rotate:40
            },
            type: 'category',
            data: [GetDateStr(-30), GetDateStr(-29), GetDateStr(-28), GetDateStr(-27), GetDateStr(-26), GetDateStr(-25), GetDateStr(-24), GetDateStr(-23), GetDateStr(-22),
                GetDateStr(-21), GetDateStr(-20), GetDateStr(-19), GetDateStr(-18), GetDateStr(-17), GetDateStr(-16), GetDateStr(-15),
                GetDateStr(-14), GetDateStr(-13), GetDateStr(-12), GetDateStr(-11), GetDateStr(-10), GetDateStr(-9), GetDateStr(-8),
                GetDateStr(-7), GetDateStr(-6), GetDateStr(-5), GetDateStr(-4), GetDateStr(-3), GetDateStr(-2), GetDateStr(-1)],
        },
        yAxis: [
            {
                type : 'value',
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: '#000000'
                    },
                    fontSize: 11,
                    interval: 'auto',
                    formatter: '{value}'
                },
                name : '单位（次）',
            },
            {
                type : 'value',
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: '#000'
                    },
                    fontSize: 11,
                    interval: 'auto',
                    formatter: '{value}'
                },
                name : '单位（分钟）',
            },
        ],
        series: [
            //exp num
            {
                name: '实验次数',
                data: month_exp_num,
                type: 'line',
                yAxisIndex: 0,
            },

            //duration
            {
                name: '实验时长',
                data: month_duration,
                type: 'line',
                yAxisIndex: 1,
            }
        ]
    };
    chart_total.setOption(total_option);
}
function setTotalMonthEchartsOption1(month_file_upload_num) {
    var dataShadow = [];
    var yMax = Math.max.apply(null, month_file_upload_num);
    for (var i = 0; i < month_file_upload_num.length; i++) {
        dataShadow.push(yMax);
    }
    var dataAxis = [GetDateStr(-30), GetDateStr(-29), GetDateStr(-28), GetDateStr(-27), GetDateStr(-26), GetDateStr(-25), GetDateStr(-24), GetDateStr(-23), GetDateStr(-22),
        GetDateStr(-21), GetDateStr(-20), GetDateStr(-19), GetDateStr(-18), GetDateStr(-17), GetDateStr(-16), GetDateStr(-15),
        GetDateStr(-14), GetDateStr(-13), GetDateStr(-12), GetDateStr(-11), GetDateStr(-10), GetDateStr(-9), GetDateStr(-8),
        GetDateStr(-7), GetDateStr(-6), GetDateStr(-5), GetDateStr(-4), GetDateStr(-3), GetDateStr(-2), GetDateStr(-1)];

    var chart_total = echarts.init(document.getElementById('Echarts_last_month1'));
    var total_option = {
        title: {
            left:'center',
            text: '近期实验平台文件上传量',
            subtext: '近三十天数据'
        },
        xAxis: {
            data: dataAxis,
            axisLabel: {
                inside: true,
                textStyle: {
                    color: '#82bac7'
                },
                interval:0,
                rotate:90
            },
            axisTick: {
                show: false
            },
            axisLine: {
                show: false
            },
            z: 10
        },
        yAxis: {
            axisLine: {
                show: false
            },
            axisTick: {
                show: false
            },
            axisLabel: {
                textStyle: {
                    color: '#999'
                }
            }
        },
        dataZoom: [
            {
                type: 'inside'
            }
        ],
        series: [
            { // For shadow
                type: 'bar',
                itemStyle: {
                    color: 'rgba(0,0,0,0.05)'
                },
                barGap: '-100%',
                barCategoryGap: '40%',
                data: dataShadow,
                animation: false
            },
            {
                type: 'bar',
                itemStyle: {
                    color: new echarts.graphic.LinearGradient(
                        0, 0, 0, 1,
                        [
                            {offset: 0, color: '#83bff6'},
                            {offset: 0.5, color: '#188df0'},
                            {offset: 1, color: '#188df0'}
                        ]
                    )
                },
                emphasis: {
                    itemStyle: {
                        color: new echarts.graphic.LinearGradient(
                            0, 0, 0, 1,
                            [
                                {offset: 0, color: '#2378f7'},
                                {offset: 0.7, color: '#2378f7'},
                                {offset: 1, color: '#83bff6'}
                            ]
                        )
                    }
                },
                data: month_file_upload_num
            }
        ]
	}
    chart_total.setOption(total_option);
}


// 获取近一周 实验次数、实验时长
function getLastWeekStatistics(){
    $.ajax({
        type: 'GET',
        url: 'http://10.1.21.153/hdu/cb/getLastWeekDailyStatistics',
        dataType: 'json',
        success: function(data){
            console.log(data)
            if(data.code == 0){
            	console.log(data.result.expNum)
				console.log(data.result.expNum[0])
                console.log((data.result.expNum)[0])
                var week_exp_num = [data.result.expNum[0], data.result.expNum[1], data.result.expNum[2], data.result.expNum[3],
                    			data.result.expNum[4], data.result.expNum[5], data.result.expNum[6]];
                var week_duration = [data.result.duration[0], data.result.duration[1], data.result.duration[2], data.result.duration[3],
								data.result.duration[4], data.result.duration[5], data.result.duration[6]];
                var week_file_upload_num = [data.result.fileUploadNum[0], data.result.fileUploadNum[1], data.result.fileUploadNum[2], data.result.fileUploadNum[3],
                    					data.result.fileUploadNum[4], data.result.fileUploadNum[5], data.result.fileUploadNum[6]];
                setTotalWeekEchartsOption(week_exp_num, week_duration, week_file_upload_num);
            }else{
                console.log(data);
                var nodata = [0, 0, 0, 0, 0, 0, 0];
                setTotalWeekEchartsOption(nodata,nodata,nodata);
            }
        },
        error: function(xhr){
            console.log(xhr);
            var nodata = [0, 0, 0, 0, 0, 0, 0];
            setTotalWeekEchartsOption(nodata,nodata,nodata);
        }
    });
}
function setTotalWeekEchartsOption(week_exp_num, week_duration, week_file_upload_num){
    var chart_total = echarts.init(document.getElementById('Echarts_last_week'));
    var total_option = {
        title:{
            left:'center',
            text:'近一周实验次数/实验时长',
            textStyle:{
                //文字颜色
                color:'#000000',
                //字体风格,'normal','italic','oblique'
                fontStyle:'normal',
                //字体粗细 'normal','bold','bolder','lighter',100 | 200 | 300 | 400...
                fontWeight:'bold',
                //字体系列
                fontFamily:'sans-serif',
                //字体大小
                fontSize:18,
            }
		},
    	tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                crossStyle: {
                    color: '#999'
                }
            }
        },
        legend: {
            data: [
                {name: '实验次数'},
                {name: '实验时长'}
            ],
            textStyle: {
                color: '#000000',
                fontSize: 11
            },
            y: 'bottom',
            x: 'center',
        },
        xAxis: {
            type: 'category',
            data: [GetDateStr(-7), GetDateStr(-6), GetDateStr(-5), GetDateStr(-4), GetDateStr(-3), GetDateStr(-2), GetDateStr(-1)],
        },
        yAxis: [
            {
                type : 'value',
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: '#000000'
                    },
                    fontSize: 11,
                    interval: 'auto',
                    formatter: '{value}'
                },
                name : '单位（次）',
            },
            {
                type : 'value',
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: '#000'
                    },
                    fontSize: 11,
                    interval: 'auto',
                    formatter: '{value}'
                },
                name : '单位（分钟）',
            },
        ],
        series: [
            //exp num
            {
            	name: '实验次数',
                data: week_exp_num,
                type: 'line',
                yAxisIndex: 0,
            },

            //duration
            {
                name: '实验时长',
                data: week_duration,
                type: 'line',
                yAxisIndex: 1,
            }
        ]
    };
    chart_total.setOption(total_option);
}


// 获取近一周 每天 每两小时 数据
function getLastWeekHourlyStatistics() {
    $.ajax({
        type: 'GET',
        url: 'http://10.1.21.153/hdu/cb/getLastWeekDailyHourlyStatistics',
        dataType: 'json',
        success: function(data){
            console.log(data)
            if(data.code == 0){
                // 获取实验次数
                var expNum = new Array();
                var maxValue = 0;
                var index = 0;
                for(var i = 0; i < 7; i++){
                    var expNumData = data.result.expNum[i].split('/');
                    expNum[i] = new Array();
                    for(var j = 0; j < 12; j++){
                    	var tmp = parseInt(expNumData[j]);
                    	if(tmp > maxValue){
                    		maxValue = tmp;
						}
                        expNum[index++] = [i, j, tmp];
                    }
                }
                console.log(expNum);
                setLastWeekHourlyEchartsOption(expNum,maxValue);
            }else{
                console.log(data);
                var nodata = [
                    [0,0,0],[0,1,0],[0,2,0],[0,3,0],[0,4,0],[0,5,0],[0,6,0],[0,7,0],[0,8,0],[0,9,0],[0,10,0],[0,11,0],
                    [1,0,0],[1,1,0],[1,2,0],[1,3,0],[1,4,0],[1,5,0],[1,6,0],[1,7,0],[1,8,0],[1,9,0],[1,10,0],[1,11,0],
                    [2,0,0],[2,1,0],[2,2,0],[2,3,0],[2,4,0],[2,5,0],[2,6,0],[2,7,0],[2,8,0],[2,9,0],[2,10,0],[2,11,0],
                    [3,0,0],[3,1,0],[3,2,0],[3,3,0],[3,4,0],[3,5,0],[3,6,0],[3,7,0],[3,8,0],[3,9,0],[3,10,0],[3,11,0],
                    [4,0,0],[4,1,0],[4,2,0],[4,3,0],[4,4,0],[4,5,0],[4,6,0],[4,7,0],[4,8,0],[4,9,0],[4,10,0],[4,11,0],
                    [5,0,0],[5,1,0],[5,2,0],[5,3,0],[5,4,0],[5,5,0],[5,6,0],[5,7,0],[5,8,0],[5,9,0],[5,10,0],[5,11,0],
                    [6,0,0],[6,1,0],[6,2,0],[6,3,0],[6,4,0],[6,5,0],[6,6,0],[6,7,0],[6,8,0],[6,9,0],[6,10,0],[6,11,0]
                ];
                setLastWeekHourlyEchartsOption(nodata,1);
            }
        },
        error: function(xhr){
            console.log(xhr);
            var nodata = [
                [0,0,0],[0,1,0],[0,2,0],[0,3,0],[0,4,0],[0,5,0],[0,6,0],[0,7,0],[0,8,0],[0,9,0],[0,10,0],[0,11,0],
                [1,0,0],[1,1,0],[1,2,0],[1,3,0],[1,4,0],[1,5,0],[1,6,0],[1,7,0],[1,8,0],[1,9,0],[1,10,0],[1,11,0],
                [2,0,0],[2,1,0],[2,2,0],[2,3,0],[2,4,0],[2,5,0],[2,6,0],[2,7,0],[2,8,0],[2,9,0],[2,10,0],[2,11,0],
                [3,0,0],[3,1,0],[3,2,0],[3,3,0],[3,4,0],[3,5,0],[3,6,0],[3,7,0],[3,8,0],[3,9,0],[3,10,0],[3,11,0],
                [4,0,0],[4,1,0],[4,2,0],[4,3,0],[4,4,0],[4,5,0],[4,6,0],[4,7,0],[4,8,0],[4,9,0],[4,10,0],[4,11,0],
                [5,0,0],[5,1,0],[5,2,0],[5,3,0],[5,4,0],[5,5,0],[5,6,0],[5,7,0],[5,8,0],[5,9,0],[5,10,0],[5,11,0],
                [6,0,0],[6,1,0],[6,2,0],[6,3,0],[6,4,0],[6,5,0],[6,6,0],[6,7,0],[6,8,0],[6,9,0],[6,10,0],[6,11,0]
            ];
            setLastWeekHourlyEchartsOption(nodata,1);
        }
    });
}
function setLastWeekHourlyEchartsOption(statistics, maxValue){
    var hours = ['0a-2a', '2a-4a', '4a-6a', '6a-8a', '8a-10a', '10a-12a', '12a-14p', '14p-16p', '16p-18p', '18p-20p','20p-22p','22p-24p'];
    var days = [GetDateStr(-7), GetDateStr(-6), GetDateStr(-5), GetDateStr(-4), GetDateStr(-3), GetDateStr(-2), GetDateStr(-1)];
    var data = statistics;
    data = data.map(function (item) {
        return [item[1], item[0], item[2] || '-'];
    });

    var chart_total = echarts.init(document.getElementById('Echarts_last_week_hourly'));
    var total_option = {
        title:{
            left:'center',
            text:'近一周实验时段/次数热点图',
            textStyle:{
                //文字颜色
                color:'#000000',
                //字体风格,'normal','italic','oblique'
                fontStyle:'normal',
                //字体粗细 'normal','bold','bolder','lighter',100 | 200 | 300 | 400...
                fontWeight:'bold',
                //字体系列
                fontFamily:'sans-serif',
                //字体大小
                fontSize:18,
            }
        },
        tooltip: {
            position: 'top'
        },
        animation: true,
        grid: {
            height: '50%',
            top: '10%'
        },
        xAxis: {
            type: 'category',
            data: hours,
            axisLabel: {
                interval:0,
                rotate:45
            },
            splitArea: {
                show: true
            }
        },
        yAxis: {
            type: 'category',
            data: days,
            splitArea: {
                show: true
            }
        },
        visualMap: {
            min: 0,
            max: maxValue,
            calculable: true,
            orient: 'horizontal',
            left: 'center',
            bottom: '15%'
        },
        series: [{
            name: '实验次数',
            type: 'heatmap',
            data: data,
            label: {
                show: true
            },
            emphasis: {
                itemStyle: {
                    shadowBlur: 10,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }]
    };
    chart_total.setOption(total_option);
}


// ***************通过search获取相关列表****************
// function getSearchCourse(classify,title,page){
// 	var data={
// 		buscourse:{
// 			classify:classify,
// 			title:title
// 		},
// 		page:{
// 			current:page,
// 			size:8
// 		}
// 	};
// 	request('business.busCourse.getCourseByPage',data,function callback(result){
// 		// if(result.value.length>0){
// 		var pages=result.value.pages;
// 		var current=result.value.current;
// 		setP(pages,current);
// 		var data = {
// 			value : result.value.records
// 		};
// 		template.config("escape", false);
//
// 		var html = template('content', data);
// 		//console.log(html);
// 		$('#show').html(html);
// 		courseImgs();
// 		// }
// 	});
//
// }
var idToName;
// ***************通过课程id获取问答列表****************
function answer(courseId,page,orderBy,isasc,isNewPage){
	var key = $("#searchKey").val();
	var url;
	var data={
		busQuestion:{
			courseId:courseId
		},
		page:{
			current:page,
			size:8,
			orderByField:orderBy,
			asc:isasc
		}
	};
	if(key){
		data.key = key;
		url = "business.busQuestion.searchQuestion";
	}else{
		url = "business.busQuestion.getCourseQuestionByPage";
	}
    request(url,data,function callback(result){
        var pages=result.value.pages;
        var current=result.value.current;
        if(isNewPage){
            console.log("请求重新初始化");
            setPage(courseId,pages,current);
        }else{
            console.log("不重新初始化");
        }
        // $('#overview')!=null?$('#overview').html(result.value[0].overview):"";
        var data = {
            value : result.value.records
        };
        for(var i = 0; i < data.value.length; i ++){
            data.value[i].createDate= new Date(parseInt(data.value[i].createDate) ).Format("yyyy-MM-dd");
            data.value[i].updateDate= new Date(parseInt(data.value[i].updateDate) ).Format("yyyy-MM-dd");
            // getUserName(data.value[i].createBy);
			userInfo = getUser(data.value[i].createBy);
			data.value[i].createByName = userInfo.loginName;
			data.value[i].photo = userInfo.photo? userInfo.photo: "img/head_pic.png";
            // data.value[i].createBy =idToName ;
            //data.value[i].createBy=getname(data.value[i].id);
        }
        template.config("escape", false);
        var html = template('answers', data);
        // console.log(data);
        // $('#list').html(html);
        $("#questList").html(html);

    });
}

// ***************通过问题id获取问题信息****************
function getQuestionInfo(questionId) {
	var data={
		busQuestion:{
			id:questionId
		}
	};
	return request('business.busQuestion.get',data,function callback(result) {
		if(result.code == 0){
			$("#ques-title").text(result.value.title).attr("data-status",result.value.status).attr("data-recommend",result.value.recommended);
			$("#bonur").text(result.value.bonus);
			$("#ques-time").text(new Date(result.value.createDate).Format("yyyy-MM-dd"));
			$("#ques-asker").text(getUserName(result.value.createBy)).attr("data-id",result.value.id).attr("data-createBy",result.value.createBy);
			$("#ques-desc").empty().append(result.value.description.replace("\r\n", "</br>").replace("\n","</br>"));
			if(result.value.recommended == '1'){
				$(".recommend-tag").eq(0).css("display","inline-block");
			}
			if(result.value.status == '0'){
				$("#ques-status").text("[已解决]");
			}
		}else{
			layer.msg(result.msg, {time:3000,icon:5});
		}
		return result.value;
		
	});

}

// ***************获取课程详情****************
function getCourseDetail(courseId) {
	var busCourse = {
		busCourse:{
			id: courseId
		}
	};
	return request('business.busCourse.getCourseDetail',busCourse,function callback(result) {
		if(result.code == 0){
			return result.value;
		}
	});
}

// ***************通过问题id获取回答列表****************
function reply(questionId,page,orderBy,isNewPage,courseId,askerId) {
	var busCourse = {
		busCourse:{
			id: courseId
		}
	};
	request('business.busCourse.getCourseDetail',busCourse,function callback(result) {
		if(result.code == 0){
			var teacherId = result.value.teacherId;
			var isTeacher = teacherId == JSON.parse(getCookie('loginResult')).id;
			var isAsker = askerId == JSON.parse(getCookie('loginResult')).id;
			var isSolve = $("#ques-title").attr("data-status");
			var isRecommend = $("#ques-title").attr("data-recommend");
			if(isTeacher && isRecommend!= '1'){
				$('.ques-recommend').eq(0).css('display','block');
			}
			if(isAsker){
				$('.del-link').eq(0).css('display','block');
			}
			var data={
				busAnswer:{
					questionId:questionId
				},
				busCourse:{
					createBy:result.value.teacherId
				},
				page:{
					current:page,
					size:8,
					OrderByField:orderBy,
					asc: true
				}
			};
			request('business.busAnswer.getQuestionAnswerByPage',data,function callback(result){
				var pages=result.value.answerPage.pages;
				var current=result.value.answerPage.current;
				if(isNewPage){
					console.log("请求重新初始化");
					setPage(questionId,courseId,askerId,pages,current);
				}else {
					console.log("不重新初始化");
				}
				if(result.value.recommendedAnswer.length > 0){
					var dataRecom={
						value : result.value.recommendedAnswer,
						isTeacher: isTeacher,
						isAsker: isAsker,
						isSolve: isSolve
					};
					dataRecom = answerFor(dataRecom);
					template.config("escape",false);

					var htmlRecom = template('reply',dataRecom);
					$("#recommendList").html(htmlRecom);
				}
				if(result.value.answerPage.records.length > 0){
					var data={
						value : result.value.answerPage.records,
						isTeacher: isTeacher,
						isAsker: isAsker,
						isSolve: isSolve
					};
					data = answerFor(data);
					template.config("escape",false);

					var html = template('reply',data);
					$("#answerList").html(html);
				}
				if(result.value.teacherAnswer.length > 0){
					//如果存在老师回答
					var dataT={
						value : result.value.teacherAnswer,
						isTeacher: isTeacher,
						isAsker: isAsker,
						isSolve: isSolve
					};
					dataT = answerFor(dataT);
					template.config("escape",false);

					var htmlT = template('reply',dataT);
					$("#teacherAnswer").html(htmlT);
				}else{
					$("#teacherArea").css('display','none');
				}
				if(result.value.acceptedAnswer.length > 0){
					//如果存在老师回答
					var dataAccept={
						value : result.value.acceptedAnswer,
						isTeacher: isTeacher,
						isAsker: isAsker,
						isSolve: isSolve
					};
					dataAccept = answerFor(dataAccept);
					template.config("escape",false);

					var htmlAccept = template('reply',dataAccept);
					$("#acceptAnswer").html(htmlAccept).css('display','block');
				}
			});
		}
	});
}
//***************回答遍历****************
function answerFor(data) {
	var userInfo = null;
	for(var i = 0;i < data.value.length;i++){
		userInfo = getUser(data.value[i].createBy);
		data.value[i].createDate = new Date(parseInt(data.value[i].createDate) ).Format("yyyy-MM-dd");
		data.value[i].createByName = userInfo.loginName;
		data.value[i].photo = userInfo.photo? userInfo.photo: "img/head_pic.png";
		if(data.value[i].reason){
			data.value[i].reason = data.value[i].reason.replace("\r\n","</br>").replace("\n","</br>");
		}
		if(data.value[i].code){
			//转义双引号
			data.value[i].code = data.value[i].code.replace(/"/g,'&quot;');
		}
	}
	return data;
}

//***************获取用户积分****************
function getUserIntegral(userId) {
	if(!userId){
		userId = JSON.parse(getCookie("loginResult")).id;
	}
	var data = {
		rewardPoints:{
			userId:userId
		}
	};
	return request('business.rewardPoints.checkBalance',data,function callback(result) {
		if(result.code == 0){
			return result.value;
		}else{
			layer.msg(result.msg, {time:3000,icon:5});
		}
	});
}

// ***************积分变化(增加或扣除)****************
function changeIntegral(userId,rewardPoints,type,event) {
	var data = {
		rewardPoints:{
			"userId":userId?userId:JSON.parse(getCookie('loginResult')).id,
			"rewardPoints":rewardPoints,
			"type":type,
			"event":event,
			"eventTime":new Date().Format("yyyy-MM-dd hh:mm:ss")
		}
	};
	request('business.rewardPoints.insert',data,function callback(result) {
		if(result.code != 0){
			layer.msg(result.msg, {time:3000,icon:5});
		}
	});
}

// ***************赞同或取消点赞****************
function agreeAnswer(answerId,flag) {
	//点赞"1"取消赞"0"
	var data = {
		user:{
			token:JSON.parse(getCookie('loginResult')).token
		},
		busAnswer:{
			id:answerId
		},
		busAnswerLike:{
			"status":flag
		}
	};
	return request('business.busAnswer.approveAnswer',data,function callback(result) {
		return result.code+"";	//返回值:操作是否成功
	});
}

// ***************教师推荐问题****************
function recommendQ(questionId) {
	var data = {
		user:{
			token:JSON.parse(getCookie('loginResult')).token
		},
		busQuestion:{
			id:questionId
		}
	}
	return request('business.busQuestion.recommendQuestion',data,function callback(result) {
		if(result.code == 0){
			return result;
		}
	})
}

// ***************教师推荐回答****************
function recommendA(answerId) {
	var data = {
		user:{
			token:JSON.parse(getCookie('loginResult')).token
		},
		busAnswer:{
			id:answerId
		}
	}
	return request('business.busAnswer.recommendAnswer',data,function callback(result) {
		if(result.code == 0){
			return result;
		}
	})
}

// ***************提问者采纳回答****************
function acceptAnswer(questionId,questionCreateBy,answerId,answerCreatBy) {
	var data = {
		user:{
			token: JSON.parse(getCookie('loginResult')).token
		},
		busQuestion:{
			id: questionId,
			createBy: questionCreateBy
		},
		busAnswer:{
			id:answerId,
			createBy:answerCreatBy
		}
	};
	return request('business.busAnswer.acceptAnswer',data,function callback(result) {
		return result;
	})
}

// ***************查询被采纳回答****************
function getAcceptAnswer(questionId) {
	var data = {
		"busAnswer":{
			"questionId":questionId
		}
	};
	return request('business.busAnswer.getAcceptedAnswer.hsr',data,function (result) {
		return result;
	})
}

// ***************提问者删除问题****************
function deleteQuestion(questionId) {
	var data = {
		busQuestion:{
			id:questionId
		},
		user:{
			token:JSON.parse(getCookie('loginResult')).token
		}
	};
	return request('business.busQuestion.delete',data,function callback(result) {
		return result;
	})
}

// ***************通过用户ID获取姓名****************
// function getUserName(id){
// 	var data={
// 		user:{
// 			id:id
// 		}
// 	}
//     request('system.user.get',data,function callback(result){
//
//     	loginname=result.value.loginName;
//     });
// }
function getUserName(id){
    var data={
        user:{
            id:id
        }
    };
    return request('system.user.get',data,function callback(result){
		if(result.code == 0){
			idToName = result.value.loginName;	//兼容前代码
			return result.value.loginName;
		}
    });
}

function getUser(id){
	var data={
		user:{
			id:id
		}
	};
	return request('system.user.get',data,function callback(result){
		if(result.code == 0){
			return result.value;
		}
	});
}

// ***************提交回答****************
function submitAnswer() {
	var questionId = window.location.href.split("=")[1];
	if(questionId.indexOf("#")){
		questionId = questionId.split("#")[0];
	}
	if(!$.trim($("#myAnswer").val())){
		layer.msg("请先输入回答,才能提交哦!", {shade:false,time: 1500, icon:5});
		return false;
	}
    var data={
        busAnswer:{
            reason: $("#myAnswer").val(),
            createBy: JSON.parse(getCookie('loginResult')).id,
            questionId: questionId,
            supportNum: "0"
        }
    };
	if($("#attachCode") && $("#attachCode").is(":checked")){
		var a = document.getElementById("codeFrame").contentWindow.editor.getValue();
		data.busAnswer.code = encode64(encodeURIComponent(a));
	}
    request('business.busAnswer.answerQuestion',data,function callback(result){
        if(result.code == 0){
            layer.msg("回答成功!", {shade:false,time: 1500, icon:6});
			$("#myAnswer").val("");
			var questionInfo = getQuestionInfo(questionId);
			if(questionInfo){
				reply(questionId,1,"create_date",true,questionInfo.courseId,questionInfo.createBy);
			}
        }else{
            layer.msg("回答失败!", {shade:false,time: 1500, icon:5});
        }
    });
}

// ***************通过课程id获取章节列表****************
function courseDetail(courseid,type){
	var data={
		busCourse:{
			id:courseid
		},
		user:{
			token:JSON.parse(getCookie('loginResult')).token
		}
	};
	request('business.busChapter.getTreeChapterListToFront',data,function callback(result){
		console.log("=====================================================")
		if(type==1){
			// from courseDetail
			if(result.code==0){
				// $('#overview')!=null?$('#overview').html(result.value[0].overview):"";
				if(result.value){
					var data = {
						value : result.value,
						courseid :courseid
					};
					// console.log(data.value),
					template.config("escape", false);
					$("#parentCap").empty();
					$("#parentCap").append('<option value="">请选择</option>');
					for(var key in data.value){
						console.log("key:   ");console.log(key);
						console.log("value: ");console.log(data.value[key]);
						$("#parentCap").append('<option value="'+result.value[key][0].id+'">'+result.value[key][0].title+'</option>');
					}
					var html = template('content', data);
					$('#show').html(html);
					var html1 = template('train_content', data);
					$('#training').html(html1);
					//add content
					//$('#training').html(html);
					return result.value;
				}
			}
		}
		else if(type==2 || type == 3){
			// debugger;
			// from codeFrame
			// 如果type = 3用于sliderMenu
			if(result.code==0){
				// $('#overview')!=null?$('#overview').html(result.value[0].overview):"";
				var data = {
					value : result.value
				};
				template.config("escape", false);
				var html = template('content', data);
				// console.log(data);
				// $('#list').html(html);
				if(type==2){
					$("#menuMain").html(html);	
				}else{
					$("#sliderMenuMain").html(html);
				}
			}
		}
		else if(type==7){
			//FPGA 实验
            if(result.code==0){
                // $('#overview')!=null?$('#overview').html(result.value[0].overview):"";
                if(result.value){
                    var data = {
                        value : result.value,
                        courseid :courseid
                    };
                    // console.log(data.value),
                    template.config("escape", false);
                    $("#parentCap").empty();
                    $("#parentCap").append('<option value="">请选择</option>');
                    for(var key in data.value){
                        console.log("key:   ");console.log(key);
                        console.log("value: ");console.log(data.value[key]);
                        $("#parentCap").append('<option value="'+result.value[key][0].id+'">'+result.value[key][0].title+'</option>');
                    }
                    var html = template('content', data);
                    $('#show').html(html);
                    // var html1 = template('train_content', data);
                    // $('#training').html(html1);
                    //add content
                    //$('#training').html(html);
                    return result.value;
                }
            }
		}
	});
}

// ***************通过课程id获取练习题列表******************
function exerciseDetial(courseid, type) {
	console.log("exerciseDetial");
	console.log(courseid);
	var data={
		busCourse:{
			id:courseid
		},
		user:{
			token:JSON.parse(getCookie('loginResult')).token
		}
	};
	request('business.busChapter.getTreeChapterListToFront',data,function callback(result){
		console.log("=====================================================")
		if(type==1){
			// from courseDetail
			if(result.code==0){
				// $('#overview')!=null?$('#overview').html(result.value[0].overview):"";
				if(result.value){
					var data = {
						value : result.value,
						courseid :courseid
					};
					// console.log(data.value),
					template.config("escape", false);
					$("#parentCap").empty();
					$("#parentCap").append('<option value="">请选择</option>');
					for(var key in data.value){
						console.log("属性：" + key + ",值：" + data.value[key]);
						$("#parentCap").append('<option value="'+result.value[key][0].id+'">'+result.value[key][0].title+'</option>');
					}
					var html = template('content', data);
					$('#show').html(html);
					//add content
					//$('#training').html(html);
					return result.value;
				}
			}
		}
		else if(type==2 || type == 3){
			// debugger;
			// from codeFrame
			// 如果type = 3用于sliderMenu
			if(result.code==0){
				// $('#overview')!=null?$('#overview').html(result.value[0].overview):"";
				var data = {
					value : result.value
				};
				template.config("escape", false);
				var html = template('content', data);
				// console.log(data);
				// $('#list').html(html);
				if(type==2){
					$("#menuMain").html(html);
				}else{
					$("#sliderMenuMain").html(html);
				}
			}
		}
	});
}

// ***************通过课程id获取课程概览****************
function courseOverview(courseid){
    var data={
        busCourse:{
            id:courseid
        }
    };
    request('business.busCourse.getCourseDetail',data,function callback(result){
        console.log(JSON.stringify(result.value));
        if(result.code==0){
            var a=result.value.courseTeacher;
            var b=result.value.teacherRank;
            var c=result.value.teacherImage?result.value.teacherImage:"img/head_pic.png";
            if(result.value.pic!=null && result.value.pic!=""){
                $('#coursePic').attr("src",result.value.pic);
            }
            $('#title').html(result.value.title);
            $('#courseTitle').html(result.value.title);
            $('#classTime').html(result.value.classtime);
            $('#score').html(result.value.score);
            $('#parentCount').html(result.value.parentCount);
            $('#childCount').html(result.value.childCount);
            $('#codeCount').html(result.value.codeCount);
            $('#desc').html(result.value.desc);
            $('#overview')!=null?$('#overview').html(result.value.overview):"";
            $('#userNames').html(a);
            $('#teaname').html(a);
            $('#tead').html(a);
            $('#role').html(b);
            $('#teajob').html(b);
            $('#userImgs').attr('src', c);
            $('#teaimg').attr('src', c);
            $('#teacherID').html(result.value.teacherId);
            //$('#teaid').html(result.value.teacherId);
            // 考虑 teacherImage 为undefined时的情况
            // 将返回的 result.value.teacherId 保存到页面中，在
        }
    });
}
// ***************通过老师id获得老师简介****************
function getteacher(teacheruserid){
    var data = {
        busTeacherInfo:{
            userId:teacheruserid
        }
    };
    request('business.busCourse.getTeacherInfo',data,function callback(result){
        console.log(JSON.stringify(result.value));
        if(result.code==0){
            $('#teamajor').html(result.value.major);
            $('#teaschool').html(result.value.college);
            $('#teadepart').html(result.value.department);
            $('#teadec').html(result.value.teaDes);
        }

    })
}
//获取这门课程的所有班级
function getClassesByCourse(courseid) {
    var data = {
        busCourse:{
            id:courseid
        }
    }
    request('business.busClass.getClassesByCourse',data,function callback(result){
        // console.log(JSON.stringify(result.value));
        if(result.code==0){
            for (var i= 0;i < result.value.length;i++){
                $("#classes").append("<option value='"+result.value[i].id+"'>"+result.value[i].name+"</option>");
            }
        }

    });
}

function hasClass(courseid) {
    var data = {
        busCourse:{
            id:courseid
        }
    };
    request('business.busClass.hasClass',data,function callback(result){
        // console.log(JSON.stringify(result.value));
        if(result.code==1){
            $('#joinClass').hide();
        }
    });
}
//开始学习前判断用户是否为新用户
function checknewinf(){

}
//加入班级前判断用户是否以完善个人信息
function checkInf() {
	var data = {
		user:{
			id:JSON.parse(getCookie('loginResult')).id,
			identityRole:JSON.parse(getCookie('loginResult')).identityRole
		}
	};
	request('system.user.getCollegeInfo',data,function callback(result){
		if(result.code == 0){
			if(!JSON.parse(getCookie('loginResult')).name){
				// console.log(getCookie('loginResult'));
				layer.msg("请先完善个人信息", {time:3000});
				window.location.href = "personalCenter.html#users";
			}else{
				$("#myModal1").modal('show');
			}
		}else {
			layer.msg(result.msg, {time:3000});
			window.location.href = "personalCenter.html#schoolInf";
		}
	});
}

//判断用户是否加入班级
function isJoinClass(courseid){
    var data = {
        user:{
            id:JSON.parse(getCookie('loginResult')).id
        },
        busCourse:{
            id:courseid
        }
    }
    request('business.busClass.isJoinClass',data,function callback(result){
        // console.log(JSON.stringify(result.value));
        if(result.code==1){
			$('#joinClass').hide();
			$('#p_tag').html('您所在的班级是:'+result.value.className+',该班的开课老师是:'+result.value.createName);
            setCookie('classId', result.value.id);
        }
    });
};
//用户加入班级操作
function joinClass(courseid) {
	var classId = $('#classes').val();
	var invitationCode = $('#invitationCode').val();
    var data = {
        user:{
            id:JSON.parse(getCookie('loginResult')).id
        },
        busCourse:{
            id:courseid
        },
        busClass:{
        	id:classId,
            invitationCode:invitationCode
		}
    }
    request('business.busClass.joinClass',data,function callback(result){
        // console.log(JSON.stringify(result.value));
        if(result.code==0){
        	// alert(result.msg);
			layer.msg(result.msg, {time:1000,icon:1},function () {
				location.reload();
			});
			setCookie('classId',classId);
        } else {
			layer.msg(result.msg, {time:3000,icon:2});
		}
    });
}

// ***************通过课程id和用户id获课程是否是继续学习还是开始学习****************
function getLastestChapter(courseid){
    var data = {
        user:{
            token:JSON.parse(getCookie('loginResult')).token
        },
        busCourse:{
            id:courseid
        }
    };
    request('business.busCourseRecord.getLastestChapter',data,function callback(result){
        // console.log(JSON.stringify(result.value));
        if(result.code==0 && result.value !=null){
            $('#begin').html("继续学习");
            $('#begin').attr("href","codeFrame.html?chapterId="+result.value.lastest + "&courseId=" + courseid);
        }
        else if(result.code == 2){
            $('#begin').html("开始学习");
            $('#begin').attr("href","codeFrame.html?chapterId="+result.value.lastest + "&courseId=" + courseid);
			$('#begin').click(function(){
				beginStudy(courseid);
			});
        }
    });
}

function beginStudy(courseid) {
	var data = {
        user:{
            token:JSON.parse(getCookie('loginResult')).token
        },
        busCourse:{
            id:courseid
        }
	}

	request('business.busCourseRecord.beginStudy',data,function callback(result){
        // console.log(JSON.stringify(result.value));
        if(result.code==0){
            console.log(result.msg);
        }
        else{
        	console.log(result.msg);
		}
    });
}

function setLastestChapter(chapterId){
	var data = {
        user:{
            token:JSON.parse(getCookie('loginResult')).token
        },
        busChapter:{
            id:chapterId
        }
	};
    request('business.busCourseRecord.setLastestChapter',data,function callback(result){

    });
}

// ***************通过课程id和用户id获课程进度****************
function getFinishProgress(courseid){
	var data = {
        user:{
            token:JSON.parse(getCookie('loginResult')).token
        },
		busCourse:{
        	id:courseid
		}
	};
    request('business.busChapterRecord.finishProgress',data,function callback(result){
        // console.log(JSON.stringify(result.value));
        if(result.code==0){
            $('#progress').html(result.value.completion + "%");
            $('#progress').attr("aria-valuenow",result.value.completion);
            $('#progress').attr("style","min-width: 2em;width: "+ result.value.completion +"%;");
        }
    },true);
}

// **************** 获取我创建的FPGA类实验课程 ******************
function getFPGACourseICreated() {
    console.log("getFPGACourseICreated")
    var token = getCookie('loginResult');
    if(token!=null && token!=""){
        var data={
            page:{
                current: "1"
            },
            user:{
                token:JSON.parse(token).token
            }
        };
        request('business.busCourse.myFPGACourseCreatedByMyself',data,function callback(result) {
            console.log(result.value.records);
            if(result.value.records){
                var data = {
                    value : result.value.records
                };
                template.config("escape", false);
                var html = template('createdCourse', data);
                $('#show-created-courses').html(html);
            }else{
                $('#show-created-courses').html("<p style='line-height: 120px;'>您尚未创建任何课程</p>");
            }
        });
    }else {
        layer.msg('登录过期！', {shade:true,time: 1000, icon:6});
        setTimeout(function () {window.location.href=HOST; }, 1000);
    }
}

// ***************已完成课程列表****************
function myCourseHasFinished(){
    var token=getCookie('loginResult');
    if(token!=null && token!=""){
        var data={
            user:{
                token:JSON.parse(token).token
            }
        };
        request('business.busCourse.myCourseHasFinished',data,function callback(result) {
            // console.log(result.value);
            if(result.value){
                var data = {
                    value : result.value
                };
                template.config("escape", false);

                var html = template('finished', data);
                //console.log(html);
                $('#show2').html(html);
            }else{
                $('#show2').html("<p>您尚未完成任何课程~</p>");
            }
        });
    }else {
        layer.msg('登录过期！', {shade:true,time: 1000, icon:6});
        setTimeout(function () {window.location.href=HOST; }, 1000);
	}
	// var url="business.busCourse.myCourseHasFinished.hsr";
	// $.ajax({
	// 	url: HOST+url,
	// 	type: 'GET',
	// 	headers: {
	// 		token: JSON.parse(getCookie('loginResult')).token
	// 	},
	// 	data: "Data"+"="+JSON.stringify(data),
	// 	dataType: "json",
	// 	success: function(result){
	// 		//alert(JSON.stringify(result))
	// 		var data = {
	// 			value : result.value
	// 		};
	// 		template.config("escape", false);
    //
	// 		var html = template('finished', data);
	// 		//console.log(html);
	// 		$('#show1').html(html);
	// 	},
	// 	error:function(xhr){
	// 		alert("错误提示： " + xhr.status + " " + xhr.statusText);
	// 	}
	// });
}

// ***************未完成课程列表****************
function myCourseOnLearning(){
    var token=getCookie('loginResult');
    console.log(token);
	if(token!=null && token!=""){
        var data={
            user:{
                token:JSON.parse(token).token
            }
        };
        request('business.busCourse.myCourseOnLearning',data,function callback(result) {
            if(result.value){
                console.log(result.value);
                var data = {
                    value : result.value
                };
                template.config("escape", false);
                var html = template('learning', data);
                //console.log(html);
                $('#show1').html(html);
            }else{
                $('#show1').html("<p style='line-height: 120px;'>您尚未加入任何课程~前往<a href='courseLibrary.html'>课程库</a>学习</p>");
            }
        });
	}else {
        layer.msg('登录过期！', {shade:true,time: 1000, icon:6});
        setTimeout(function () {window.location.href=HOST; }, 1000);
	}

	// var url="business.busCourse.myCourseOnLearning.hsr";
	// $.ajax({
	// 	url: HOST+url,
	// 	type: 'GET',
	// 	headers: {
	// 		token: JSON.parse(getCookie('loginResult')).token
	// 	},
	// 	data: "Data"+"="+JSON.stringify(data),
	// 	dataType: "json",
	// 	success: function(result){
	// 		//alert(JSON.stringify(result))
    //
	// 		var data = {
	// 			value : result.value
	// 		};
	// 		template.config("escape", false);
    //
	// 		var html = template('learning', data);
	// 		//console.log(html);
	// 		$('#show2').html(html);
	// 	},
	// 	error:function(xhr){
	// 		alert("错误提示： " + xhr.status + " " + xhr.statusText);
	// 	}
	// });
}


// ***************推荐课程列表****************
function commendCourse(){
	var data={
		user:{
			token:JSON.parse(getCookie('loginResult')).token
		},
		num:4
	};
	request('business.busCourse.recommendedCourses',data,function callback(result) {
		console.log(JSON.stringify(result));
		if(result.value!=null&&result.value.length>0){
			var data = {
				value : result.value
			};
			template.config("escape", false);

			var html = template('commend', data);
			//console.log(html);
			$('#show').html(html);
		}else{
			$('#show').html("<p>暂时没有推荐课程~</p>");
		}
	})
}
// ***************历史课程列表****************
function historyCourse(){
	var data={
		user:{
			token:JSON.parse(getCookie('loginResult')).token
		},
		num:4
	};
	request('business.busCourseRecord.getLatestCourseRecordByUser',data,function callback(result) {
		//console.log(JSON.stringify(result));
		if(result.value!=null&&result.value.length>0){
			var data = {
				value : result.value
			};
			template.config("escape", false);

			var html = template('history', data);
			//console.log(html);
			$('#show1').html(html);
		}else{
			$('#show1').html("<p>暂时没有历史课程~</p>");
		}
	})
}

// ***************运行问答代码****************
function answerCode(code, inputvalue, func, chapterId, codeId) {
	var loadIndex = layer.load(2);
	var data;
	if (func == "execHtml") {
		data={
			busCodeRecord:{
				codeId: codeId,
				chapterId: chapterId,
				userId:JSON.parse(getCookie('loginResult')).id,
				content:encode64(encodeURIComponent(code))
			}
		};
	} else {
		data={
			busCodeRecord:{
				codeId: codeId,
				chapterId: chapterId,
				userId:JSON.parse(getCookie('loginResult')).id,
				content:encode64(encodeURIComponent(code))
			},
			inputValue: inputvalue
		};
	}
	$.ajax({
		url: HOST+'business.busCodeRecord.'+func+'.hsr',
		type: 'POST',
		data: JSON.stringify(data),
		headers: {
			token: this.getCookie('loginResult') === "" ? "" : JSON.parse(this.getCookie('loginResult')).token
		},
		dataType: "json",
		success: function(result){
			layer.close(loadIndex); //关闭等待；
			var sts="";
			if (func == "execHtml") {
				sts = result.value;
				$("#result_iframe").attr("src", sts);
			} else {
				for (var i=0;i<result.value.length;i++)
				{
					sts += (result.value[i] + '<br />');
				}
				$("#result").html(sts);
			}
		}
	});
}

/*
*提交执行代码，code为代码内容，func为执行的代码语言的方法
*/
function submitCode(code, inputvalue, func, chapterId, codeId){
    // $("#run").html("运行中...");
    // $("#run").attr("disabled", true);
    // console.log(encode64(encodeURIComponent(code)));
    var loadIndex = layer.load(2);
	var data;
	if (func == "execHtml") {
        data={
            busCodeRecord:{
                codeId:codeId,
                chapterId:chapterId,
                userId:JSON.parse(getCookie('loginResult')).id,
                content:encode64(encodeURIComponent(code))
            }
        };
	} else {
        data={
            busCodeRecord:{
                codeId:codeId,
                chapterId:chapterId,
                userId:JSON.parse(getCookie('loginResult')).id,
                content:encode64(encodeURIComponent(code))
            },
            inputValue: inputvalue
        };
	}
	$.ajax({
		url: HOST+'business.busCodeRecord.'+func+'.hsr',
		type: 'POST',
		data: JSON.stringify(data),
		headers: {
			token: this.getCookie('loginResult') === "" ? "" : JSON.parse(this.getCookie('loginResult')).token
		},
		dataType: "json",
		success: function(result){
			layer.close(loadIndex); //关闭等待；
			var sts="";
			if(result.code == 1){
				if (func == "execHtml") {
					var data = {
						value : result.value
					};
					sts = result.value;
					$("#result_iframe").attr("src", sts);
				} else {
					var data = {
						value : result.value
					};
					for (var i=0;i<result.value.length;i++)
					{
						sts += (result.value[i] + '<br />');
					}
					$("#result").html(sts);
				}
			}else{
				$("#result").html("执行失败");
			}
		}
	});
	// request('business.busCodeRecord.'+func,data,function callback(result) {
	// 	var sts="";
     //    if (func == "execHtml") {
	// 		var data = {
	// 			value : result.value
	// 		};
	// 		sts = result.value;
	// 		$("#result_iframe").attr("src", sts);
     //    } else {
     //        var data = {
     //            value : result.value
     //        };
     //        for (var i=0;i<result.value.length;i++)
     //        {
     //            sts += (result.value[i] + '<br />');
     //        }
     //        $("#result").html(sts);
	// 	}
	// 	// layer.close(loadIndex); //关闭等待；
	// })
	// alert(getCookie('loginResult')+" "+JSON.stringify(data)+"----"+JSON.parse(getCookie('loginResult')).token);
}
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

function getCode(chapterid){
	var data={
		buscode:{
			chapterId:chapterid
		}
	};
	request('business.busCode.getByChapterId',data,function callback(result) {
		console.log(JSON.stringify(result));
		lan_types=result.value.type;
		if (result.value.type == "0") {
			language = 'execJava';
		} else if (result.value.type == "1") {
			language = 'execCplus';
		} else if (result.value.type == "2") {
			language = 'execPy';
		} else if (result.value.type == "4") {
            language = 'execHtml';
		}
		codeId = result.value.id;
		// document.getElementById('code').innerHTML = result.value.content;
		resetContent= result.value.content;
		
	})

	var datas={
		busCodeRecord:{
			chapterId:chapterid,
			userId:JSON.parse(getCookie('loginResult')).id
		}
	};

	request('business.busCodeRecord.getRecordByCIdAndUId',datas,function callback(result){
		if (result.code == "0"){
			content = result.value.content;
		} else {
			content = resetContent;
		}
	});
	// var url="business.busCode.getByChapterId.hsr";
	// $.ajax({
	// 	url: HOST+url,
	// 	type: 'GET',
	// 	headers: {
	// 		token: JSON.parse(getCookie('loginResult')).token
	// 	},
	// 	data: "Data"+"="+JSON.stringify(data),
	// 	dataType: "json",
	// 	success: function(result){
	// 		//alert(JSON.stringify(result));
	// 		setCookie('codeId',result.value.id);
	// 		document.getElementById('code').innerHTML = result.value.content;
	// 		editor.setValue(result.value.content);
	// 	},
	// 	error:function(xhr){
	// 		alert("错误提示： " + xhr.status + " " + xhr.statusText);
	// 	}
	// });

}
//获取个人概览信息
function  getPersonalOverview() {
	var data={
		user:{
			token: this.getCookie('loginResult') === "" ? "" : JSON.parse(this.getCookie('loginResult')).token
		}
	};
	request('system.user.getPersonalOverview',data,function callback(result) {
		if(result){
			var data = {
				value:result.value
			};
			console.log(JSON.stringify(result));
			template.config("escape", false);
			var html = template('content', data);
			//console.log(html);
			$('#personalOverView').html(html);
			timeLine();
		}

	},false);
}
// ***************scratch作品列表****************
function proList(){
	var data={
		page:{
			current:1
		}
	};
	request('business.busScratchFile.getBusScratchProjectByPage',data,function callback(result){
		// if(result.value.length>0){
		if(result.code == 0){
			if(result.value){
				var pages=result.value.pages;
				var current=result.value.current;
				setP(pages,current);
			}
			var data = {
				value : result.value?result.value.records:""
			};
			template.config("escape", false);

			var html = template('content', data);
			//console.log(html);
			$('#pro_list').html(html);
		}
		// }
	});

}



// 获取日期
function GetDateStr(AddDayCount) {
    var dd = new Date();
    dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
    //var y = dd.getFullYear();
    var m = (dd.getMonth()+1)<10?"0"+(dd.getMonth()+1):(dd.getMonth()+1);//获取当前月份的日期，不足10补0
    var d = dd.getDate()<10?"0"+dd.getDate():dd.getDate();//获取当前几号，不足10补0
	//var result = y+"-"+m+"-"+d;
    var result = m+"-"+d;
    // console.log(result);
    return result;
}


// 日期格式转换
Date.prototype.Format = function (fmt) { //author: meizz 
	var o = {
		"M+": this.getMonth() + 1, //月份 
		"d+": this.getDate(), //日 
		"h+": this.getHours(), //小时 
		"m+": this.getMinutes(), //分 
		"s+": this.getSeconds(), //秒 
		"q+": Math.floor((this.getMonth() + 3) / 3), //季度 
		"S": this.getMilliseconds() //毫秒 
	};
	if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}