/**
 * Created by ADMIN on 18/2/7.
 */
//获取其他问题
function getOtherQuestion(courseId) {
    var data = {
        busQuestion:{
            courseId:courseId
        },
        page:{
            current:1,
            size:8,
            orderByField:"createDate",
            asc:true
        }
    };
    request('business.busQuestion.getCourseQuestionByPage',data,function callback(result) {
        if(result.code == 0){
            var data = {
                value : result.value.records
            };
            for(var i = 0; i < data.value.length; i ++){
                data.value[i].createDate= new Date(parseInt(data.value[i].createDate) ).Format("yyyy-MM-dd");
            }
            template.config("escape", false);
            var html = template('question', data);
            $("#other-ques").html(html);
        }
    })
}

//赞同该回答
function answerAgree(e) {
    // console.log(e);
    var $obj = $(e);
    var agree = $obj.find('span');
    if($obj.attr("data-flag") == 0){
        var result = agreeAnswer($obj.attr("data-id"),"1");
        console.log(result+ ":"+ typeof result);
        if(result == "0"){
            $obj.find('img').attr('src','img/zanActive.png');
            agree.text(agree.text()-0+1);
            agree.css('color','#ea9518');
            $obj.attr("data-flag",1);   
        }else{
            layer.msg("操作失败", {time:3000,icon:5});
        }
    }else{
        result = agreeAnswer($obj.attr("data-id"),"0");
        if(result === "0"){
            $obj.find('img').attr('src','img/zan.png');
            agree.text(agree.text()-1);
            agree.css('color','#999');
            $obj.attr("data-flag",0);   
        }else{
            layer.msg("操作失败", {time:3000,icon:5});
        }
    }
}

//老师推荐回答
function answerRecommend(e) {
    var $obj = $(e);
    var agree = $obj.find('span');
    if($obj.attr("data-flag") == 0){
        //推荐回答
        var result = recommendA($obj.attr("data-id"));
        if(result.code == 0){
            $obj.find('img').attr('src','img/recommendAct.png');
            agree.css('color','#ea9518');
            $obj.attr("data-flag",1);
        }
    }else{
        //取消推荐回答
        // $obj.find('img').attr('src','img/recommend.png');
        // agree.css('color','#999');
        // $obj.attr("data-flag",0);
    }
}

//老师推荐问题
function questionRecommend(e) {
    var $obj = $(e);
    if($obj.hasClass('ques-recommend-act')){
        //取消推荐
        // $obj.removeClass('ques-recommend-act');
    }else{
        //推荐回答
        var questionId = window.location.href.split("=")[1];
        if(questionId.indexOf("#")){
            questionId = questionId.split("#")[0];
        }
        var result = recommendQ(questionId);
        if(result.code == 0){
            $obj.addClass('ques-recommend-act');
            $obj.text("已推荐");
        }
    }
}

//提问者采纳回答
function adoptAnswer(e) {
    var $obj = $(e);
    if($obj.hasClass('adopt-answer-act')){
        //取消
        // $obj.removeClass('adopt-answer-act');
        // $obj.text('采纳该回答');
    }else{
        var questionId = $("#ques-asker").attr("data-id");
        var questionCreateBy = $("#ques-asker").attr("data-createBy");
        var answerId = $obj.attr("data-id");
        var answerCreatBy = $obj.attr("data-create");
        var result = acceptAnswer(questionId,questionCreateBy,answerId,answerCreatBy);
        if(result.code == 0){
            $obj.addClass('adopt-answer-act');
            $obj.text('已采纳');
            $obj.css('opacity',1);
            layer.msg('采纳成功', {shade:false,time: 1500, icon:6});
            window.location.reload();
        }else{
            layer.msg('操作失败', {shade:false,time: 1500, icon:5});
        }
    }
}

//提问者删除问题
function delQuestion(e) {
    var questionId = $("#ques-asker").attr("data-id");
    var result = deleteQuestion(questionId);
    if(result.code == 0){
        layer.msg('删除成功', {shade:false,time: 1500, icon:6});
        window.location.href = "courseDetail.html?courseId="+$("#ques-title").attr("data-courseId");
    }else{
        layer.msg('删除失败', {shade:false,time: 1500, icon:5});
    }
}

//获取代码
function codeView(e) {
    var $obj = $(e);
    var code = $obj.attr("data-code");
    code = code.replace(/\&quot\;/g,'"');
    document.getElementById("codeFrame").contentWindow.editor.setValue(code);
}

