/**
 * @Author  ADMIN on 17/10/13.
 */
var chapterId = "";
var lan_types = "";//语言类型 0 java 1 c++ 2 python
var al_name = "";//给codemirror选择语言类型用的
var editor = null;
var resetContent = "";
var content = "";//编辑器里面的内容
var inputvalue = "";//输入框里的值
var language = "",codeId="",problemId="";
var pgTask = {}; //存储试题信息
var courseId = "";
var userId = "";
var hasExample = true; // 是否有课程案例
function readyNow(callback) {
    // chapterId = window.location.href.split('=')[1];
    userId = JSON.parse(getCookie('loginResult')).id;
    console.log("~~~~~~~~~~~~~~~~~~~~~~~~userId: "+userId);
    var urls = window.location.href.split(/\?|\&/);
    chapterId = urls[1].split("=")[1];
    console.log(urls);
    courseId = urls[2].split("=")[1];
    // getCode(chapterId);
    $.getScript('CodeMirror/lib/codemirror.js', function () {
        $.when(
            $.getScript('CodeMirror/mode/clike/clike.js'),
            $.getScript('CodeMirror/mode/python/python.js'),
            $.getScript('CodeMirror/mode/javascript/javascript.js'),
            $.getScript('CodeMirror/mode/css/css.js'),
            $.getScript('CodeMirror/addon/edit/matchbrackets.js'),
            $.getScript('CodeMirror/mode/htmlmixed/htmlmixed.js'),
            $.getScript('CodeMirror/mode/xml/xml.js'),
            $.getScript('CodeMirror/addon/hint/show-hint.js'),
//                    $.getScript('CodeMirror/addon/hint/anyword-hint.js'),
            $.Deferred(function (deferred) {
                $(deferred.resolve);
            })
        ).done(function () {
            switch (lan_types) {
                case '0':
                    al_name = "text/x-java";
                    $('#tab_nav').html('<li role="presentation" class="active"><a href="#">HelloWorld.java</a></li>');
                    break;
                case '1':
                    al_name = "text/x-c++src";
                    $('#tab_nav').html('<li role="presentation" class="active"><a href="#">hello.cpp</a></li>');
                    break;
                case '2':
                    al_name = "text/x-python";
                    $('#tab_nav').html('<li role="presentation" class="active"><a href="#">hello.py</a></li>');
                    break;
                case '4':
                    al_name = "htmlmixed";
                    $('#tab_nav').html('<li role="presentation" class="active"><a href="#">index.html</a></li>');
                    break;
                default:
                    al_name = "text/x-java";
                    break;
            }
            if(editor==null){

            editor = CodeMirror.fromTextArea(document.getElementById('code'), {
                mode: al_name,
                tabMode: 'indent',
                extraKeys: {"Ctrl-M": "autocomplete"},//输入s然后ctrl就可以弹出选择项
                theme: 'seti',
                indentUnit: 4,
                lineNumbers: true,
                lineWrapping: true,
                matchBrackets: true,
                tabSize: 2,
                cursorBlinkRate: 530
            });
            }
            editor.setValue(content);
            if(pgTask.ojProblem && pgTask.ojProblem.hasCheckpoint == 1){
                $("#testBtn").css("display","block");
            }
            if(callback){
                callback();
            }
        });
    });

    $.getScript('js/markdown.min.js', function() {
        converter = Markdown.getSanitizingConverter();

        $('.markdown').each(function() {
            var plainContent    = $(this).text(),
                markdownContent = converter.makeHtml(plainContent.replace(/\\\n/g, '\\n'));

            $(this).html(markdownContent);
        });
    });
}

function resetCode() {
    editor.setValue(resetContent);
}

var exampleCode = function (content) {
    editor.setValue(content);
};

//打开测试运行弹窗
function codeTest() {
    $("#testModal").modal('show');
    $("#sampleInput").val("");
}

//填入默认样例
function sampleIn() {
    if(pgTask && pgTask.ojProblem.sampleInput){
        $("#sampleInput").val(pgTask.ojProblem.sampleInput);
    }
}

//测试运行提交
function testRun() {
    if($("#sampleInput").val().trim() == ""){
        alert("运行数据不能为空!!!");
        return;
    }
    codeSubmission(1);
    $('#testModal').modal('hide');
}

function runCode() {
    var codeValue = editor.getValue();
    // var languageId = getLanId(lan_types);
    if (codeValue.trim() == "" || codeValue == null) {
        alert("代码提交不能为空!!!");
        return;
    }
    if( lan_types == "0" || lan_types == "1" || lan_types == "2"){
        if(hasExample){//有课程案例
            if(pgTask && pgTask.ojProblem.hasInput == 1&&pgTask.ojProblem.hasCheckpoint != 1){
                //如果需要输入,弹出输入框
                console.log("??????");
                $("#codeRunModal").modal('show');
                $("#subInput").val("");
                console.log("!!!!!!");
            }else{
                //如果不需要输入,直接提交
                codeSubmission(0);
            }
        }else{
            codeSubmission(1, 1);
        }
    }else if(lan_types == "4"){
        submitCode(codeValue, null, language, chapterId, codeId);
    }
}

//带有输入弹窗的提交
function runCodeWithInput() {
    if($("#subInput").val().trim() == ""){
        alert("运行数据不能为空!!!");
        return;
    }
    codeSubmission(0);
    $('#codeRunModal').modal('hide');
}

//代码提交接口
function codeSubmission(iTest,iProblem) {
    // iTest如果等于1,则表示是测试运行
    // iProblem如果等于1,则表示没有任务代码
    var codeValue = editor.getValue();
    console.log("codeValue: "+codeValue);
    var languageId = getLanId(lan_types);
    console.log("languageId: "+languageId);
    console.log("pgTask: "+pgTask);
    //var userId = JSON.parse(getCookie('loginResult')).id;
    console.log("userId: "+userId);
    var submissionData = {
        ojSubmission :{
            taskId : iProblem ? 0 : pgTask.busProgrammingTask.id,
            problemId : iTest ? 0 : problemId,
            userId : userId,
            submitCode : codeValue,
            submitInput: iTest ? $("#sampleInput").val() : $("#subInput").val(),
            languageId : languageId
        }
    };
    console.log("submissionData: "+submissionData);
    $("#runBtn").removeAttr("onclick").css("cursor","wait");
    // console.log(submissionData);
    request('business.ojSubmission.createSubmission',submissionData,function callback(result) {
        if(result.code == 0){
            for (var _key in result) {
                console.log(_key+" : "+result[_key]);
            }
            $('#judge-result').html("PD");
            console.log("result.value: "+result.value);
            var currentJudgeResult = 'PD',
                getterInterval     = setInterval(function() {
                    getCodeResult(result.value);
                    console.log("codeResult: "+getCodeResult(result.value));
                    currentJudgeResult = $('#judge-result').html();
                    console.log("currentJudgeResult: "+currentJudgeResult);
                    if ( currentJudgeResult != 'PD' ) {
                        $("#runBtn").attr("onclick","runCode()").css("cursor","pointer");
                        clearInterval(getterInterval);
                    }
                }, 1000);
        }else{
            $("#runBtn").attr("onclick","runCode()").css("cursor","pointer");
            layer.msg(result.msg, {shade:false,time: 1000, icon:5});
        }
    });
}

function getCodeResult(codeResultId) {
    var data = {
        ojSubmission:{
            id: codeResultId
        }
    };
    request('business.ojSubmission.get',data,function callback(result) {
        if(result.code == 0 && result.value.judgeResult != 'PD'){
            $('#judge-result').html(result.value.judgeResult);
            $('#judge-log').html(converter.makeHtml(result.value.judgeLog.replace(/\\\n/g, '\\n')));
            if (result.value.judgeResult == "AC") {
                var fdata = {
                  user: {
                      id: JSON.parse(getCookie('loginResult')).id
                  },
                  busProgrammingTask: {
                      id: pgTask.busProgrammingTask.id,
                      problemId: pgTask.ojProblem.id
                  },
                  busChapter: {
                      id: chapterId,
                      courseId: courseId
                  }
                };
                request('business.busProgrammingTask.hasFinishProgrammingTask',fdata,function callback(result) {

                }, true, false)
            }
        }
    }, true, false);
}

function getLanId(lan_types) {
    var languageId = "";
    switch (lan_types) {
        case '0':
            // al_name = "text/x-java";
            languageId = 3;
            break;
        case '1':
            // al_name = "text/x-c++src";
            languageId = 2;
            break;
        case '2':
            // al_name = "text/x-python";
            languageId = 5;
            break;
        case '4':
            // al_name = "htmlmixed";
            break;
        default:
            // al_name = "text/x-java";
            break;
    }
    return languageId;
}

//代码执行的真正函数
// function runCode() {
//     inputvalue = $("#inputText").val();
//     code = editor.getValue();
//     if (code.trim() == "" || code == null) {
//         alert("代码提交不能为空!!!");
//         return;
//     }
//     submitCode(code, inputvalue, language);
//     // historyCourse();
//
// //    var data = "code="+encodeURIComponent(code);
// }

function back() {
    document.getElementById("back").style.display = "none";
    document.getElementById("run").style.display = "block";
    $("#a").toggle(500);
    $("#b").toggle(500);
}

function selectCode() {
    var obj = getElementsClass("CodeMirror-code");
    selectText(obj[0]);
    alert(obj[0]);

}

function chooseChapter(chapterId) {
    window.location.href = "code.html?chapterId=" + chapterId;
}