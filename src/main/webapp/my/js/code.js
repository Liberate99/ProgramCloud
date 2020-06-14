/**
 * @Author  ADMIN on 17/10/12.
 */
var language = "";//语言 execjava execCplus execPy
var chapterId = "";//章节id
var courseId = "";//课程id
var collegeId = "";//学校id
var major = "";//专业
var classId = "";//班级id
var codeId = "";
var videoUrl = "";//视频url
var types = "";//类型: 0 写代码 1 播放视频 2 scratch 3 FPGA实验 4 机械实习
var lan_types = "";//语言类型 0 java 1 c++ 2 python
var al_name = "";//给codemirror选择语言类型用的
var editor = null;
var resetContent = "";
var content = "";//编辑器里面的内容
var inputvalue = "";//输入框里的值
var taskId = "";//项目任务projectId
var personProjectId = "";
var userMain = "";
// var userId

$(document).ready(function () {
    userMain = JSON.parse(getCookie('loginResult'));
    var urls = window.location.href.split(/\?|\&/);
    chapterId = urls[1].split("=")[1];
    courseId = urls[2].split("=")[1];
    //alert(JSON.parse(getCookie('loginResult')).token);
    getChapter(chapterId);//获取章节信息
    // getCollege();//获取学校专业信息
    // getClass();//获取班级信息
    setLastestChapter(chapterId);
    var codeFrame = document.getElementById("codeFrame");
    var obj = codeFrame.contentWindow;
    var codeType = lan_types;
    //判断任务类型(type 在 getChapterByChapterId() 获取)
    switch (types) {
        //code
        case '0':
            $("#a").css("display","block");
            $(codeFrame).attr("src","codeEdit.html?chapterId="+chapterId + "&courseId=" + courseId);
            iframeIsLoad(codeFrame,function () {
                console.log(codeType);
                obj.lan_types = codeType;
                if (codeType == "0") {
                    obj.language = 'execJava';
                } else if (codeType == "1") {
                    obj.language = 'execCplus';
                } else if (codeType == "2") {
                    obj.language = 'execPy';
                } else if (codeType == "4") {
                    obj.language = 'execHtml';
                }
                obj.resetContent = "";
                obj.content = "";
                getExample(chapterId);
                obj.readyNow();
            });
            break;

        //video
        case '1':
            // alert("test for video!");
            $("#sliderMenuBox").css("display","block");
            $(codeFrame).attr({"width":"100%","src":"video.html?chapterId="+chapterId});
            //设置视频页面高度
            iframeIsLoad(codeFrame,function () {
                obj.lan_height = document.documentElement.clientHeight-96;
                obj.setHeight();
                $("#sliderControl").on('click',sliderToggle);
                $(".menuLink").each(function (e) {
                    if($(this).attr("data-id") == chapterId){
                        $(this).css({"color":"#337ab7","background":"#f9f9f9"});
                    }
                })
            });
            break;

        //scrach
        case '2':
            // alert("test for scratch!");
            $("#a").css("display","block");
            if ($(window).width() < 1280 ) {
                back();
            }
            // $("#codeFrame").attr("src","codeDoc.html?chapterId="+chapterId);
            // $(codeFrame).attr("src","http://scratch.imayuan.com/");
            $(codeFrame).attr("src","scratch/ScratchIDE_2.html");
            // $("#codeFrame").attr("src","scratch/frame.html?projectId=eae211f2654546eda5679992c7c6eeba");
            // $("#codeFrame").attr("src","scratch/frame.html?chapterId="+chapterId);
            $(codeFrame).attr("width","80%");
            $("#a").attr("style","width:20%;height:100%;");
            getExample(chapterId);
            break;

        //cb
        case '3':
            //电路实习
            $("#a").css("display","block");
            $("#a").attr("width","30%")
            if(collegeId == ""){
                getCollege()
            }
            if(classId == ""){
                getClass()
            }
            $(codeFrame).attr("src","http://10.1.21.153/hdu?" + "chapterId=" + chapterId + "&courseId=" + courseId + "&userName=" + userMain.loginName + "&collegeId=" + collegeId + "&classId=" + classId);
            $(codeFrame).attr("width","70%");
            // // $("#codeFrame").attr("src","codeDoc.html?chapterId="+chapterId);
            // $(codeFrame).attr("src","http://127.0.0.1:8601/frame.html");
            // // $("#codeFrame").attr("src","scratch/frame.html?projectId=eae211f2654546eda5679992c7c6eeba");
            // // $("#codeFrame").attr("src","scratch/frame.html?chapterId="+chapterId);
            // $(codeFrame).attr("width","80%");
            // $("#a").attr("style","width:20%");
            // getExample(chapterId);
            break;

        //机械实习
        case '4':
            $("#a").css("display","block");
            if ($(window).width() < 1280 ) {
                back();
            }
            $(codeFrame).attr("src","http://10.1.21.153/virtual_lab/");
            $(codeFrame).attr("width","70%");
            break;

        default:
            alert("nothing to do!");
            break;
    }
});

//若为视频,为slider绑定点击事件
function sliderToggle() {
    var $slider = $("#sliderMenuBox");
    if($slider.offset().left < 0){
        $slider.animate({"left":"0"});
    }else{
        $slider.animate({"left":"-360px"});
    }
}

//获取案例
function getExample(chapterId) {
    var data = {};
    var url = "";
    // lan_types=4是html
    if(types == "0" && lan_types != "4"){
        data = {
            chapterId: chapterId
        };
        url = "business.busProgrammingTask.getBusProgrammingTaskListByChapterId";
    }else if(types == "2" || lan_types == "4"){
        data = {
            chapter: {
                id: chapterId
            }
        };
        url = "business.busCode.getCodeListToFront";
    }
    request(url,data,function callback(result) {
        console.log(result);
        if(result.value!=null&&result.value.length>0){
            var data = {
                value : result.value
            };
            if(types == "2" || lan_types == "4"){
                for(var i = 0;i<data.value.length;i++){
                    data.value[i].name = data.value[i].title;   //调用的url返回命名不统一
                }   
            }
            template.config("escape", false);
            var html = template('exampleList', data);
            //console.log(html);
            $('#exampleBox').html(html);
            if(result.value[0].name){
                $("#exampleTitle").text(result.value[0].name);
            }
            getExampleCode(result.value[0].id);
        }else{
            document.getElementById("codeFrame").contentWindow.hasExample = false;
            $('#exampleBox').html("<li role='presentation'><a role='menuitem' tabindex='-1' href='javascript:;'>暂无课程示例</a></li>");
        }
    });
}

//获取案例详情和代码
function getExampleCode(buscodeId) {
    codeId=buscodeId;
    var code;
    var codeFrame = document.getElementById("codeFrame");
    var obj = codeFrame.contentWindow;
    if(types == '0' && lan_types != "4"){   //代码编辑
        code = {
            busProgrammingTask:{
                id:buscodeId
            }
        };
        request('business.busProgrammingTask.getBusProgrammingTaskWithProblem',code,function callback(result) {
            if(result.code == 0){
                $("#exampleMain").html(result.value.busProgrammingTask.content);
                $("#exampleTitle").text(result.value.busProgrammingTask.name?result.value.busProgrammingTask.name:"课程案例");
                // iframeIsLoad(codeFrame,function () {
                    obj.codeId = buscodeId;
                    if(result.value.ojProblem.hasHint == 1){
                        obj.resetContent = result.value.ojProblem.hint;
                        obj.content = result.value.ojProblem.hint;
                    }
                    obj.problemId = result.value.ojProblem.id;
                    //pgTask存储试题详情
                    obj.pgTask = result.value;
                    // obj.readyNow(function callback() {
                        //回调保证editor不为空
                        // iCodeHistory();
                    // });
                // });
            }
        });
    }else if(types == '2' || lan_types == "4"){ //scratch
        code = {
            buscode:{
                id:buscodeId
            }
        };
        request('business.busCode.get',code,function callback(result) {
            console.log(result);
            if(result.code == 0 && result.value){
                $("#exampleMain").html(result.value.message);
                if(result.value.title){
                    $("#exampleTitle").text(result.value.title);
                }
                if(types == '2'){
                    taskId=result.value.content;
                    var codeHistory = {
                        busCodeRecord:{
                            codeId:buscodeId,
                            chapterId:chapterId,
                            userId:JSON.parse(getCookie('loginResult')).id
                        }
                    };
                    request('business.busCodeRecord.getRecordByCIdAndUIdForScratch',codeHistory,function callback(res) {
                        if(res.code==0){
                            reloadIframe(res.value.content);
                        }else{
                            $("#codeFrame").attr("src","scratch/ScratchIDE_2.html");
                            // $("#codeFrame").attr("src","scratch/frame.html?projectId="+
                            //     taskId+
                            //     "&codeId="+result.value.id
                            // );
                        }
                    });
                }
                if(lan_types == '4'){
                    obj.codeId = buscodeId;
                    if(result.value.content){
                        obj.resetContent = result.value.content;
                        obj.content = result.value.content;
                    }
                }
            }
        });
    }
}

function getExampleCodewithoutIframeLoad(buscodeId) {
    codeId = buscodeId;
    var code;
    var codeFrame = document.getElementById("codeFrame");
    var obj = codeFrame.contentWindow;
    obj.codeId = buscodeId;
    if(types == '0' && lan_types != "4"){
        code = {
            busProgrammingTask:{
                id:buscodeId
            }
        };
        request('business.busProgrammingTask.getBusProgrammingTaskWithProblem',code,function callback(result) {
            if(result.code == 0){
                $("#exampleMain").html(result.value.busProgrammingTask.content);
                $("#exampleTitle").text(result.value.busProgrammingTask.name?result.value.busProgrammingTask.name:"课程案例");
                if(result.value.ojProblem.hasHint == 1){
                    obj.resetContent = result.value.ojProblem.hint;
                    obj.content = result.value.ojProblem.hint;
                }
                $(codeFrame).contents().find("#testBtn").css("display",result.value.ojProblem.hasCheckpoint == 1 ? "block" : "none");
                obj.editor.setValue(result.value.ojProblem.hint);
                obj.problemId = result.value.ojProblem.id;
                //pgTask存储试题详情
                obj.pgTask = result.value;
            }
        });
    }else if(types == '2' || lan_types == "4"){
        code = {
            buscode:{
                id:buscodeId
            }
        };
        request('business.busCode.get',code,function callback(result) {
            console.log(result);
            if(result.code == 0 && result.value){
                $("#exampleMain").html(result.value.message);
                if(result.value.title){
                    $("#exampleTitle").text(result.value.title);
                }
                if(types == '2'){
                    taskId=result.value.content;
                    var codeHistory = {
                        busCodeRecord:{
                            codeId:codeId,
                            chapterId:chapterId,
                            userId:JSON.parse(getCookie('loginResult')).id
                        }
                    };
                    request('business.busCodeRecord.getRecordByCIdAndUIdForScratch',codeHistory,function callback(res) {
                        if(res.code==0){
                            reloadIframe(res.value.content)
                        }else{
                            $("#codeFrame").attr("src","scratch/ScratchIDE_2.html");
                            // $("#codeFrame").attr("src","scratch/frame.html?projectId="+
                            //     taskId+
                            //     "&codeId="+result.value.id
                            // );
                        }
                    });
                }
                if(lan_types == '4'){
                    if(result.value.content){
                        obj.resetContent = result.value.content;
                        obj.content = result.value.content;
                    }
                }
            }
        });
    }
}

//判断该用户在该案例中是否有历史代码
function iCodeHistory() {
    var codeHistory = {
        busCodeRecord:{
            codeId:codeId,
            chapterId:chapterId,
            userId:JSON.parse(getCookie('loginResult')).id
            // codeId:buscodeId
        }
    };
    console.log(codeHistory);
    request('business.busCodeRecord.getRecordByCIdAndUId',codeHistory,function callback(result) {
        if(result.code==0){
            var obj = document.getElementById("codeFrame").contentWindow;
                obj.content = result.value.content;
                obj.exampleCode(obj.content);
        }
    });
}

//判断iframe是否加载完成
function iframeIsLoad(iframe,callback){
    if(iframe.attachEvent) {
        iframe.attachEvent('onload',function(){
            callback && callback();
        });
    }else {
        iframe.onload = function(){
            callback && callback();
        }
    }
}

// 根据章节id获取章节具体信息
function getChapter(chapterId) {
    var data={
        busChapter:{
            id:chapterId
        }
    };
    request('business.busChapter.getBusChapterWithCourseClassify',data,function callback(result) {
        //console.log("chapterinfo:"+JSON.stringify(result));
        if(result.code == 0){
            // courseId =result.value.busChapter.courseId;
            videoUrl = result.value.busChapter.resourceUrl;
            lan_types = result.value.busCourse.classify;
            $('#courseTit').html(result.value.busChapter.title);
//            $('#case1').html(result.value.des);
            $('#caseContent').html(result.value.busChapter.des);
            types = result.value.busChapter.type;
            if(types == '1'){
                courseDetail(courseId, 3);
                $(".hov").eq(0).html("<a href='javascript:;'>"+result.value.busChapter.title+"</a>");
            }else{
                courseDetail(courseId, 2);
            }
            var menuLinks = $("a.menuLink");
            for(var i = 0;i < menuLinks.length;i++){
                var hrefId = menuLinks[i].href.split("=")[1];
                if(hrefId == chapterId){
                    if(i>0){
                        $("#lastCourse").on("click",function () {
                            window.location.href = menuLinks[i-1].href;
                        })
                    }
                    if(i<menuLinks.length-1){
                        $("#nextCourse").on("click",function () {
                            window.location.href = menuLinks[i+1].href;
                        })
                    }
                    $("#courseTit").text($.trim(menuLinks[i].innerText));
                    break;
                }
            }
            console.log(result.msg);
        }else{
            console.log(result.msg);
        }
    },false)
}

var iflag=0;
function showMenu() {
    if(iflag == 0){
        $("#courseMenu").css("display","block");
        $(".hornBox").eq(0).css("display","block");
        iflag = 1;
    }else{
        $("#courseMenu").css("display","none");
        $(".hornBox").eq(0).css("display","none");
        iflag = 0;
    }
}

// 阻止事件冒泡
function stopPropagation(e) {
    if (e.stopPropagation)
        e.stopPropagation();
    else
        e.cancelBubble = true;
}

// 获取学校id
// function getCollege(){
//     console.log("college information");
//     var data = {
//         busStudentInfo : {
//             userId : JSON.parse(getCookie('loginResult')).id
//         }
//     };
//     console.log(JSON.parse(getCookie('loginResult')).id);
//     request('business.busStudentInfo.getStudentCollegeIDInfoFromUserID', data, function callback(result) {
//         console.log(result);
//         if (result.code == 0) {
//             if(result.value == null){
//                 console.log("college information is null!")
//                 collegeId = 0
//                 major = ""
//             }else{
//                 console.log("college information get&set!")
//                 console.log(result.value[0].collegeId)
//                 // collegeId = result.value.collegeId
//                 // major = result.value.major
//             }
//         }else {
//             // layer.msg(result.msg,{time: 2000, icon:5});
//         }
//
//     });
// }

// 获取班级id
// function getClass(){
//     console.log("class information");
//     var data = {
//         user: {
//             id:JSON.parse(getCookie('loginResult')).id
//         },
//         busCourse: {
//             id: courseId
//         }
//     }
//     // 获取是否加入班级
//     request('business.busClass.isJoinClass',data,function callback(result){
//         console.log(result);
//         if (result.code == 0) {
//             if(result.value == null){
//                 // 没有班级信息
//                 console.log("class information is null!")
//                 collegeId = 0// no collage
//                 major = ""
//             }else{
//                 console.log("class information get&set!")
//                 collegeId = result.value.collegeId
//                 major = result.value.major
//             }
//         } else {
//             // layer.msg(result.msg, {time:3000});//弹出提示框
//         }
//     });
// }

//
// function resetCode(){
//     editor.setValue(resetContent);
// }
// function reloadIframe(projectId){
//     personProjectId = projectId;
//     var src = "scratch/frame.html?projectId="+
//         projectId+
//         "&codeId="+codeId+
//         "&hasOwnProject="+"-1";
//     $("#codeFrame").attr("src",src);
// }
//
// function runCode() {
//     inputvalue = $("#inputText").val();
//     code = editor.getValue();
//     if (code.trim()== "" || code == null) {
//         alert("代码提交不能为空");
//         return ;
//     }
//     submitCode(code, inputvalue, language);
//     historyCourse();
//
// //    var data = "code="+encodeURIComponent(code);
//
// }


function back() {
    // document.getElementById("back").style.display = "none";
    // document.getElementById("run").style.display = "block";
    $("#codeFrame").attr("width","80%");//照顾动画效果，无实际业务意义

    $("#a").toggle(500,function () {
        if($("#a").css("display")=="none"){
            $("#codeFrame").attr("width","100%");
        }else{
            $("#codeFrame").attr("width","80%");

        }
    });
    //
}

function resetCurrentCode() {
    // 重新从当前任务章节获取初始化id
    //scratch/ScratchIDE_2.html
    // var src = "scratch/frame.html?projectId="+
    //     taskId+
    //     "&codeId="+codeId+
    //     "&hasOwnProject="+personProjectId;
    var src = "scratch/ScratchIDE_2.html";
    $("#codeFrame").attr("src",src);
}

function selectCode() {
    var obj = getElementsClass("CodeMirror-code");
    selectText(obj[0]);
    alert(obj[0]);

}

function chooseChapter(chapterId) {
    window.location.href="code.html?chapterId="+chapterId;
}

//  function switchView() {
//      $("#a").toggle(500);
//      $("#b").toggle(500);
//  }
//
// function cod() {
//     document.getElementById("vid").style.display = "none";
//     document.getElementById("doc").style.display = "none";
//     document.getElementById("code_area").style.display = "none";
//     document.getElementById("b").style.display = "none";
// }
// function vod(width, height) {
//     var myPlayer =
//         videojs("my-video", {
//             "controls": true,
//             "autoplay": false,
//             "preload": "auto",
//             "poster": "oceans.png",
//             "width": width,
//             "height": height,
//             sources: [
//                 {src: HOST + videoUrl, type: 'video/mp4'}
//             ],
//
//         });
// //        myPlayer.watermark({
// //            file: HOST+'/my/img/logo.png',
// //            clickable: true,
// //            url: "http://www.yunjuanyunshu.com",
// //
// //        });
//     myPlayer.videoJsResolutionSwitcher();
//     myPlayer.on("ended", function () {
//         console.log("end", this.currentTime());
//         var bcR = {
//             buschapterrecord: {
//                 userId: getCookie('loginResult') === "" ? "" : JSON.parse(getCookie('loginResult')).id,
//                 chapterId: getCookie('chapterid')
//             }
//         };
//         request('business.busChapterRecord.finishBusCha', bcR, function callback(result) {
//
//         });
//     });
//     document.getElementById("vid").style.display = "block";
//     document.getElementById("doc").style.display = "none";
//     document.getElementById("code_area").style.display = "none";
//     document.getElementById("b").style.display = "none";
//     document.getElementById("c").style.display = "none";
//     document.getElementById("c1").style.display = "none";
// }
// function dod() {
//     document.getElementById("doc").style.display = "block";
//     document.getElementById("vid").style.display = "none";
//     document.getElementById("code_area").style.display = "none";
//     document.getElementById("b").style.display = "none";
//     document.getElementById("c").style.display = "none";
//     document.getElementById("c1").style.display = "none";
//
// }
function goBack() {
    window.location.href = "courseDetail.html?courseId=" + courseId;
}