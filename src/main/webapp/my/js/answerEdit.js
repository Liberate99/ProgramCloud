/**
 * @Author  ADMIN on 17/10/13.
 */
// var chapterId = "";
// var lan_types ="";//语言类型 0 java 1 c++ 2 python
// var al_name ="";//给codemirror选择语言类型用的
// var editor=null;
// var resetContent="";
// var content="";//编辑器里面的内容
// var inputvalue="";//输入框里的值
// var language = "";
// $(function () {
//     chapterId=window.location.href.split('=')[1];
//     getCode(chapterId);
//     //getCode("6f0903e8567e412ea341766b36a9572e");
//     $.getScript('CodeMirror/lib/codemirror.js', function() {
//         $.when(
//             $.getScript('CodeMirror/mode/clike/clike.js'),
//             $.getScript('CodeMirror/mode/python/python.js'),
//             $.getScript('CodeMirror/mode/javascript/javascript.js'),
//             $.getScript('CodeMirror/mode/css/css.js'),
//             $.getScript('CodeMirror/mode/htmlmixed/htmlmixed.js'),
//             $.getScript('CodeMirror/mode/xml/xml.js'),
//             $.getScript('CodeMirror/addon/hint/show-hint.js'),
// //                    $.getScript('CodeMirror/addon/hint/anyword-hint.js'),
//             $.Deferred(function(deferred) {
//                 $(deferred.resolve);
//             })
//         ).done(function() {
//             switch (lan_types) {
//                 case '0':
//                     al_name = "text/x-java";
//                     $('#tab_nav').append('<li role="presentation" class="active"><a href="#">HelloWorld.java</a></li>');
//                     break;
//                 case '1':
//                     al_name = "text/x-c++src";
//                     $('#tab_nav').append('<li role="presentation" class="active"><a href="#">hello.cpp</a></li>');
//                     break;
//                 case '2':
//                     al_name = "text/x-python";
//                     $('#tab_nav').append('<li role="presentation" class="active"><a href="#">hello.py</a></li>');
//                     break;
//                 case '4':
//                     al_name = "htmlmixed";
//                     $('#tab_nav').append('<li role="presentation" class="active"><a href="#">index.html</a></li>');
//                     break;
//                 default:
//                     al_name = "text/x-java";
//                     break;
//             }
//             editor = CodeMirror.fromTextArea(document.getElementById('code'), {
//                 mode: al_name,
//                 tabMode: 'indent',
//                 extraKeys: {"Ctrl-M": "autocomplete"},//输入s然后ctrl就可以弹出选择项
//                 theme: 'seti',
//                 indentUnit: 2,
//                 lineNumbers: true,
//                 lineWrapping: true,
//                 matchBrackets: true,
//                 cursorBlinkRate: 530
//             });
//             document.getElementsByClassName('CodeMirror')[0].style.height = document.documentElement.clientHeight - 130 + "px";
//             document.getElementById('result').style.height = document.documentElement.clientHeight - 30 + "px";
//             //editor.setValue(content);
//
//         });
//     });
// });
// function resetCode(){
//     editor.setValue(resetContent);
// }
//
// function runCode() {
// //    $("#a").toggle(500);
// //    $("#b").toggle(500);
// //    document.getElementById("run").style.display = "none";
// //    document.getElementById("back").style.display = "block";
//     inputvalue = $("#inputText").val();
//
//     code = editor.getValue();
//     submitCode(code, inputvalue, language);
//     // historyCourse();
//
// //    var data = "code="+encodeURIComponent(code);
//
// }
// function back() {
//     document.getElementById("back").style.display = "none";
//     document.getElementById("run").style.display = "block";
//     $("#a").toggle(500);
//     $("#b").toggle(500);
// }
// function selectCode() {
//     var obj = getElementsClass("CodeMirror-code");
//     selectText(obj[0]);
//     alert(obj[0]);
//
// }
//
// function chooseChapter(chapterId) {
//     window.location.href="code.html?chapterId="+chapterId;
// }