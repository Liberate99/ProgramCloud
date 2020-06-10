/**
 * @Author  ADMIN on 17/10/12.
 */
var editor;
var language = "";
var codeId = "";
var info = {};
var lan_types = "";
$(function () {
    var hrefs = window.location.href.replace(/\?/,"&").split("&");
    lan_types = hrefs[1].split("=")[1];
    info.codeId = hrefs[3].split("=")[1];
    info.chapterId = hrefs[2].split("=")[1];
    var al_name = "";
    $.getScript('CodeMirror/lib/codemirror.js', function() {
        $.when(
            $.getScript('CodeMirror/mode/clike/clike.js'),
            $.getScript('CodeMirror/mode/python/python.js'),
            $.getScript('CodeMirror/mode/javascript/javascript.js'),
            $.getScript('CodeMirror/mode/css/css.js'),
            $.getScript('CodeMirror/mode/htmlmixed/htmlmixed.js'),
            $.getScript('CodeMirror/mode/xml/xml.js'),
            $.getScript('CodeMirror/addon/hint/show-hint.js'),
            $.Deferred(function(deferred) {
                $(deferred.resolve);
            })
        ).done(function() {
            switch (lan_types) {
                case '0':
                    al_name = "text/x-java";
                    language = "execJava";
                    $('#tab_nav').append('<li role="presentation" class="active"><a href="#">HelloWorld.java</a></li>');
                    break;
                case '1':
                    al_name = "text/x-c++src";
                    language = "execCplus";
                    $('#tab_nav').append('<li role="presentation" class="active"><a href="#">hello.cpp</a></li>');
                    break;
                case '2':
                    al_name = "text/x-python";
                    language = "execPy";
                    $('#tab_nav').append('<li role="presentation" class="active"><a href="#">hello.py</a></li>');
                    break;
                case '4':
                    al_name = "htmlmixed";
                    language = "execHtml";
                    $('#tab_nav').append('<li role="presentation" class="active"><a href="#">index.html</a></li>');
                    break;
                default:
                    al_name = "text/x-java";
                    break;
            }
            editor = CodeMirror.fromTextArea(document.getElementById('code'), {
                mode: al_name,
                tabMode: 'indent',
                extraKeys: {"Ctrl-M": "autocomplete"},//输入s然后ctrl就可以弹出选择项
                theme: 'seti',
                indentUnit: 4,
                lineNumbers: true,
                lineWrapping: true,
                matchBrackets: true,
                cursorBlinkRate: 530
            });
            // document.getElementsByClassName('CodeMirror')[0].style.height = document.documentElement.clientHeight - 30 + "px";
            // document.getElementById('result').style.height = document.documentElement.clientHeight - 30 + "px";
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
});

//代码运行
// function runCode() {
//     var code = editor.getValue();
//     var inputvalue = $("#inputText").val();
//     if (code.trim() == "" || code == null) {
//         alert("代码提交不能为空!!!");
//         return;
//     }
//     answerCode(code,inputvalue,language,info.chapterId,info.codeId);
//     // console.log(code);
//
// }


// 代码运行
function runCode() {
    var codeValue = editor.getValue();
    // var languageId = getLanId(lan_types);
    if (codeValue.trim() == "" || codeValue == null) {
        alert("代码提交不能为空!!!");
        return;
    }
    if(lan_types == "0" || lan_types == "1" || lan_types == "2"){
        $("#codeRunModal").modal('show');
        $("#subInput").val("");
    }else if(lan_types == "4"){
        submitCode(codeValue, "", language,info.chapterId,info.codeId);
    }
}

//带有输入弹窗的提交
function runCodeWithInput() {
    codeSubmission();
    $('#codeRunModal').modal('hide');
}

// 代码提交
function codeSubmission() {
    var codeValue = editor.getValue();
    var languageId = getLanId(lan_types);
    var submissionData = {
        ojSubmission :{
            taskId : info.codeId,
            problemId : 0,
            userId : JSON.parse(getCookie('loginResult')).id,
            submitCode : codeValue,
            submitInput: $("#subInput").val(),
            languageId : languageId
        }
    };
    $("#runBtn").removeAttr("onclick").css("cursor","wait");
    // console.log(submissionData);
    request('business.ojSubmission.createSubmission',submissionData,function callback(result) {
        if(result.code == 0){
            $('#judge-result').html("PD");
            // console.log(result.value);
            var currentJudgeResult = 'PD',
                getterInterval     = setInterval(function() {
                    getCodeResult(result.value);
                    currentJudgeResult = $('#judge-result').html();

                    if ( currentJudgeResult != 'PD' ) {
                        $("#runBtn").attr("onclick","runCode()").css("cursor","pointer");


                        clearInterval(getterInterval);
                        // alert(111);

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
            // $("#result").html(result.value.judgeLog.replace("\r\n", "</br>").replace("\n", "</br>"));
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
