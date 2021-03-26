$(function () {
    $('.course_preview').on('click', function(){
       const class_id = $(this).attr('data-id');
       const course_id = $(this).attr('data-course');
       console.log(class_id + " : " + course_id);
       location.href="/study/"+course_id+"/preview/"+class_id;
    });
    /* 별점 기능 구현 */
    $('.star_rate_ul li').click(function(){
        var list = $(this).parent().children();
        $(this).parent().children().removeClass('star_fill');
        var index = $(this).index();
        console.log(index);
        for(var i = 0; i < (index+1); i++){
            $(list[i]).addClass('star_fill');
        }
    });
    /* 모두 펼치기 / 모두 닫기 버튼 이벤트 */
    $('.curriculum_close_button').click(function(){
        var arr = $('.section_head');

        if($(this).hasClass("closed")){
            $(this).text('모두 펼치기');
            $(this).removeClass('closed');
        }else{
            $(this).text('모두 닫기');
            $(this).addClass('closed');
        }
        console.log(arr.length);
        for(var i =0 ; i<arr.length; i++){
            $(arr[i]).trigger('click');
        }
    });
    $(window).scroll(function () {
        var scrollTop = $(document).scrollTop();
        if (scrollTop > 230) {
            scrollTop = scrollTop + 150;
            $(".course_box").stop();
            $(".course_box").animate({"top": scrollTop});
        } else if (scrollTop < 230) {
            $(".course_box").stop();
            $(".course_box").animate({"top": 80});
        }
    });
    $(document).on('click', '.section_head ', function () {
        console.log($(this).attr('class'));
        console.log($(this).next().css('max-height'));
        var class_box = this.nextElementSibling;
        if (class_box.style.maxHeight) {
            console.log("minus");
            /*minus*/
            $(this).children().children("div:eq(0)").removeClass("button_hide");
            $(this).children().children("div:eq(1)").addClass("button_hide");
            class_box.style.maxHeight = null;
        } else {
            /* plus */
            $(this).children().children("div:eq(0)").addClass("button_hide");
            $(this).children().children("div:eq(1)").removeClass("button_hide");
            console.log("plus");
            class_box.style.maxHeight = class_box.scrollHeight + "px";
        }
    });
    $('.cart_submit').click(function(){
       location.href="/userCart.do";
    });
    $(document).on('click', '.learning_box', function () {
        var data_added = $('.course_id').attr('data-add');
        var data_state = $('.course_id').attr('data-state');
        let isRegistered = $('.course_id').attr('data-registered');
        console.log("data_state - > " +data_state + " : ["+data_added+"]");
        console.log("data_added - > " + data_added);
        if(isRegistered =='true'){
            location.href="/learningRoom.do?course_id="+$('.course_id').val();
            return;
        }
        if(data_added != 0 && data_state != null){
            if(data_added){
                console.log("course_cart_form - > " + data_added);
                location.href = "/userCart.do";
                return;
            }
        }
        if(data_added == 0 && data_state != undefined && data_state != 'null'){
            applyCourse();
            console.log("course_cart_form - > " + data_added);
            $('.course_cart_form').css('display', 'block');
            return;
        }
        alert("로그인이 필요한 서비스입니다.");

    });
    $('.cart_cancle').click(function () {
        $('.course_cart_form').css('display', 'none');
    });
    $('.question_button').click(function(){
        var isLogin = $('.course_id').attr('data-state');
        if(isLogin != 'null'){
            $('.question_form').css('display','block');
        }else{
            alert("로그인이 필요한 서비스입니다.");
        }
    });
    $('.question_cancel').click(function(){
        $('.question_form').css('display','none');
    });
    $('.question_submit').click(function(){
        $('.question_form').css('display','none');
        addQuestion();
    });
});

function addQuestion(){
    let course_id = $('.question_course_id').val();
    let question_title = $('.question_title_value').val();
    let question_description = tinymce.activeEditor.getContent();
    $.ajax({
       url : '/addQuestion.do',
       type : 'post',
       data : {
           'course_id' : course_id,
           'question_title' : question_title,
           'question_description' : question_description
       },
       success : function(result){
           let default_template = '<body id="tinymce" class="mce-content-body " data-id="mce_0" contenteditable="true" spellcheck="false" style="overflow-y: hidden; padding-left: 1px; padding-right: 1px; min-height: 0px;" data-mce-style="overflow-y: hidden; padding-left: 1px; padding-right: 1px; min-height: 0px;"><p><strong><span style="box-shadow: inset 0 -10px #DDFCFD;" data-mce-style="box-shadow: inset 0 -10px #DDFCFD;">강의와 관련있는 질문</span>을 남겨주세요.</strong><br>• 강의와 관련이 없는 질문은 지식공유자가 답변하지 않을 수 있습니다. <span style="color: #7e8c8d;" data-mce-style="color: #7e8c8d;"><em style="font-size: 13px;" data-mce-style="font-size: 13px;">(사적 상담, 컨설팅, 과제 풀이 등)</em></span><br>• 질문을 남기기 전, 비슷한 내용을 질문한 수강생이 있는지 먼저 <span style="text-decoration: underline;" data-mce-style="text-decoration: underline;">검색</span>을 해주세요. <span style="color: #7e8c8d;" data-mce-style="color: #7e8c8d;"><em style="font-size: 13px;" data-mce-style="font-size: 13px;">(중복 질문을 자제해주세요.)</em></span><br>• <span style="text-decoration: underline;" data-mce-style="text-decoration: underline;">서비스 운영 관련 질문은 인프런 우측 하단 ‘문의하기’</span>를 이용해주세요.&nbsp;<span style="color: #7e8c8d;" data-mce-style="color: #7e8c8d;"><em style="font-size: 13px;" data-mce-style="font-size: 13px;">(영상 재생 문제, 사이트 버그, 강의 환불 등)</em></span></p><p><strong><span style="box-shadow: inset 0 -10px #DDFCFD;" data-mce-style="box-shadow: inset 0 -10px #DDFCFD;">질문 전달</span>에도 요령이 필요합니다.</strong><br>• 지식공유자가 질문을 좀 더 쉽게 확인할 수 있게 도와주세요.<br>• 강의실 페이지<em style="color: #7e8c8d; font-size: 13px;" data-mce-style="color: #7e8c8d; font-size: 13px;">(/lecture) </em>에서 \'질문하기\'를 이용해주시면 질문과 연관된 수업 영상 제목이 함께 등록됩니다.<br>• 강의 대시보드에서 질문을 남길 경우, <span style="text-decoration: underline;" data-mce-style="text-decoration: underline;">관련 섹션 및 수업 제목을 기재</span>해주세요.&nbsp;<br>• 수업 특정 구간에 대한 질문은 꼭 <span style="text-decoration: underline;" data-mce-style="text-decoration: underline;">영상 타임코드</span>를 남겨주세요!</p><p><strong><span style="box-shadow: inset 0 -10px #DDFCFD;" data-mce-style="box-shadow: inset 0 -10px #DDFCFD;">구체적인 질문</span>일수록 명확한 답을 받을 수 있어요.</strong><br>• 질문 제목은 핵심 키워드를 포함해 간결하게 적어주세요.<br>• 질문 내용은 자세하게 적어주시되, 지식공유자가 답변할 수 있도록 <span style="text-decoration: underline;" data-mce-style="text-decoration: underline;">구체적으로 남겨주세요</span>.<br>• 정확한 질문 내용과 함께 <span style="text-decoration: underline;" data-mce-style="text-decoration: underline;">코드</span>를 적어주시거나, <span style="text-decoration: underline;" data-mce-style="text-decoration: underline;">캡쳐 이미지</span>를 첨부하면 더욱 좋습니다.</p><p><strong><span style="box-shadow: inset 0 -10px #DDFCFD;" data-mce-style="box-shadow: inset 0 -10px #DDFCFD;">기본적인 예의</span>를 지켜주세요.</strong><br>• 정중한 의견 및 문의 제시, 감사 인사 등의 커뮤니케이션은 더 나은 강의를 위한 기틀이 됩니다.&nbsp;<br>• 질문이 있을 때에는 강의를 만든 지식공유자에 대한 기본적인 예의를 꼭 지켜주세요.&nbsp;<br>• <span style="text-decoration: underline;" data-mce-style="text-decoration: underline;">반말, 욕설, 과격한 표현 등 지식공유자를 불쾌하게 할 수 있는 내용</span>은 스팸 처리 등 제재를 가할 수 있습니다.&nbsp;</p></body>';
           tinymce.activeEditor.setContent(default_template);
           $('.question_title_value').val('');
           loadQuestions(course_id);
       }
    });
}

function loadQuestions(course_id){
    $('.question_content_wrap_box').html('');
    $.ajax({
        url : '/loadQuestions.do',
        type : 'post',
        data : {'course_id' : course_id},
        success : function(result){
            console.log(result);
            for(let i = 0; i < result.length; i++){
                createQuestionBox(result[i]);
            }
        }
    });
}

function createQuestionBox(q){
    let template = "<div class='question_data' data-qid='"+q.course_question_id+"' data-cid='"+q.course_id+"' data-rid='"+q.relation_id+"' data-uid='"+q.user_id+"' data-class='"+q.course_class+"'>" +
                        "<div class='question_flex_wrap'>" +
                            "<div class='question_user_img'>" +
                                "<img class='question_img_content' src='/imgs/mini_icon_1.png' />" +
                            "</div>" +
                            "<div class='question_content_wrap'>"+
                                "<div class='question_user_content'>"+
                                    "<div class='question_title'>"+ q.title +"</div>"+
                                    "<div class='question_user_name'>" + q.name +"</div>"+
                                "</div>" +
                                "<div class='question_content'>"+ q.description +"</div>" +
                            "</div>" +
                        "</div>" +
                        "<div class='question_reaction_wrap'>" +
                            "<div class='like'>" +
                                "<div class='like_reaction_logo'><i class='fas fa-heart'></i></div>" +
                                "<div class='like_reaction_content'>0</div>" +
                            "</div>" +
                            "<div class='comment'>" +
                                "<div class='comment_reaction_logo'><i class='fas fa-comment'></i></div>" +
                            "<div class='comment_reaction_content'>0</div></div>" +
                        "</div>" +
                    "</div>";
    $('.question_content_wrap_box').append(template);
}

function tab(class_obj){
    let parents = $('.real_course_content').children().length;
    $('.real_course_content').children().each(function(index, item){
        $(this).removeClass('course_active');
    });
    $(class_obj).addClass('course_active');
    console.log(parents);
}

// 수강신청을 눌렀을 때 수강신청 테이블에 데이터가 들어가고 유저 수강바구니에 데이터가 추가됨
function applyCourse() {
    var course_id = $('.course_id').val();
    var isLogin = $('.course_id').attr('data-state');
    console.log(course_id + " : " + isLogin);
    if (isLogin != "null") {
        $.ajax({
            url: "/applyCourse.do?course_id=" + course_id,
            method: "post",
            success: function (result) {
                console.log(result);
                $('.learning_box').text('결제하기');
                $('.course_id').attr('data-add', true);
            },
            error: function (e) {
                console.log(e);
            }
        });
        return true;
    } else {
        alert("로그인이 필요한 서비스입니다.");
        return false;
    }
}

function timeFormatKorWrapper(second, obj){
    $(obj).text(timeFormatKor(second));
}

function timeFormatKor(second) {
    var hours = Math.floor(second / 3600);
    var minutes = Math.floor((second - (hours * 3600)) / 60);
    var result = "";
    console.log(hours + " : " + second);
    if (hours > 0) {
        result = hours + " 시간 " + minutes + " 분";
    } else {
        result = minutes + " 분";
    }
    console.log(result);
    return result;
}

function timeFormat(second) {
    var hours = Math.floor(second / 3600);
    var minutes = Math.floor((second - (hours * 3600)) / 60);
    var seconds = second - (hours * 3600) - (minutes * 60);
    var result = "";
    if (hours > 0) {
        result = (hours*60) + minutes + " : " + seconds;
    } else if (minutes < 10) {
        if(seconds < 10){
            result = "0" + minutes + " : 0" + seconds;
        }else{
            result = "0" + minutes + " : " + seconds;
        }
    } else if(seconds < 10){
        result = minutes + " : 0" + seconds;
    }else{
        result = minutes + " : " + seconds;
    }
    return result;
}

function classSetting(class_code, title, study_time, parent, status, class_id, course_id) {
    var status_template = "";
    if(status == true){
        status_template = "<span class='course_preview right_margin' data-id='"+class_id+"' data-course='"+course_id+"'>미리보기</span>";
    }else {
        status_template = "";
    }
    var template = "<div class='section_class_content_" + class_code + " section_class_box'>" +
        "<div class='class_title'>" +
        "<i class='far fa-play-circle right_margin'></i>" +
        "<span class='class_title_content horizontal_margin'>" + title + "</span>" +
        "</div>" +
        "<div class='class_box'>" +
        status_template +
        "<i class='far fa-clock right_margin'></i> <span class='class_time right_margin''>" + timeFormat(study_time) + "</span>" +
        "</div>" +
        "</div>";
    $(template).appendTo($(parent));
}

function sectionSetting(section_code, title, class_length, total_time) {
    var template = "<div class='section_wrap'>" +
        "<div class='section_head section_" + section_code + "'>" +
        "<div class='section_title section_title_" + section_code + "'>" +
        "<div class='button_plus right_margin toggle_button'><i class='fas fa-plus'></i></div>" +
        "<div class='button_minus right_margin toggle_button button_hide'><i class='fas fa-minus'></i></div>" +
        "<span class='section_title_content horizontal_margin'>" +
        "섹션 " + section_code + ". " + title +
        "</span>" +
        "</div>" +
        "<div class='section_box'>" +
        "<div class='section_class_length_box horizontal_margin'>" +
        "<span class='section_class_length'>" + class_length + "</span>" +
        "<span>&nbsp;&nbsp;강의</span>" +
        "</div>" +
        "<i class='far fa-clock right_margin'></i>" +
        "<span class='section_time right_margin'>" + timeFormat(total_time) + "</span>" +
        "</div>" +
        "</div>" +
        "<div class='section_classes hide_class section_class_" + section_code + "'>" +
        "</div>" +
        "</div>";
    $(template).appendTo($('.curriculum_content'));
}

function settingCourseButton(isAdded) {
    $('.course_id').attr('data-add', isAdded);
    console.log(isAdded);
    if (isAdded != undefined) {
        if (isAdded != '' && isAdded == true) {
            var obj = $('.learning_box');
            $(obj).text("결제하기");
        }
    }
}
function finalSettingCourseButton(){
    let isRegistered = $('.course_id').attr('data-registered');
    console.log("dd"+ (isRegistered == true) + " : " + isRegistered);
    if(isRegistered == 'true'){
        let obj = $('.learning_box');
        $(obj).text("학습하기");
    }
}

function convertLocalDateTime(localDate, obj){
    console.log(localDate);
    var date = new Date(localDate);
    var year = date.getFullYear();
    var month = date.getMonth()+1;
    var day = date.getDate();
    console.log(date);
    console.log(year+"년 "+month+"월 "+day+"일");
    $(obj).text(year+"년 "+month+"월 "+day+"일");
}
