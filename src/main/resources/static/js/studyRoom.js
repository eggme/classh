window.onbeforeunload = function () {
    saveCourseStudyData();
    clearInterval(studySave);
    return null;
}

var pattern = /([^가-힣a-zA-Z0-9\x20])/i;

function trim(str){
    return str.replace(/(^\s+)|(\s+)|(^ㄱ-ㅎㅏ-ㅣ\x20)$/, "");
}

var player = null;
var studySave = setInterval(function (){
    saveCourseStudyData();
}, 20000);

$(function () {
    $('.dashboard').click(function () {
        let id = $(this).attr('data-id');
        location.href = "/course/" + id;
    });
    $('.close_button').click(function () {
        $(this).closest('.study_tab').removeClass('opened');
        $(this).closest('.study_tab').addClass('closed');
    });
    $('.course_data_list').click(function () {
        toggleTarget('.curriculum_wrap', $(this));
    });

    $('.back_button').click(function (){
        $('.question_list').css('display', 'block');
        $('.question_obj').css('display', 'none');
    });

    $(document).on('click', '.question_content_template', function (){
        $('.question_list').css('display', 'none');
        $('.question_obj').css('display', 'flex');
    });

    $('.course_community').click(function (){
        toggleTarget('.question_wrap', $(this));
    });

    $('.course_note').click(function(){
        toggleTarget('.note_wrap', $(this));
    });

    /* 수강권한 모달에서 강의 상세보기 클릭 */
    $('.go_course_info_button').click(function(){
        let id = $(this).attr('data-id');
        location.href="/course/"+ id
    });
    /* 수강권한 모달에서 바로 결제하기 클릭 */
    $('.do_payment_button').click(function(){
        let id = $(this).attr('data-id');
        location.href="/course/carts/"+id;
    })
    /* 수강권한 모달에서 수강 바구니 담기 클릭 */
    $('.add_course_button').click(function(){
        $('.course_cart_form').submit();
    });

    $('.add_cart_cancel').click(function(){
        $('.add_cart_form_wrap').css('display', "none");
    });

    $('.add_cart_submit').click(function(){
        location.href="/course/carts";
    });

    $('.add_cart_submit').click(function(){
        $('.clear_course_form_wrap').css('display', 'none');
    })

    $('.replay').click(function () {
        $('.video_ended').addClass('hidden');
        player.currentTime(0);
    });

    $('.next').click(function(){
       let id = $(this).attr('data-id');
       let cId = $(this).attr('data-cId');
       location.href="/study/"+id+"/lecture/"+cId;
    });

    $('.clear_course_submit').click(function(){
        /* 완강 확인버튼 */
        $('.clear_course_form_wrap').css('display', 'none');
    });

    $(document).on('click', '.class_box', function (){
        let id = $(this).attr('data-id');
        let cId = $(this).attr('data-cid');
        let preview = $(this).attr('data-p');
        let authentication =  $(this).hasClass('authentication');
        if(authentication == true){ /* 수강신청이 됐을 때 */
            location.href="/study/"+id+"/lecture/"+cId;
        }else{
            if(preview == 'true'){ /* 수강신청이 안됐을 떄, 미리보기 강의라면 */
                location.href="/study/"+id+"/preview/"+cId;
            }else{ /* 수강신청이 안됐을 때, 미리보기 강의가 아니라면 */
                if($(this).hasClass('lock')){ // 익명 사용자의 미리보기 강의에서 미리보기 설정이 되어있지 않는 강의를 눌렀을 때
                    location.href="/study/"+id+"/preview/"+cId;
                }
            }
        }
    });
    /* 커뮤니티 게시판 */
    $('.write_question').click(function(){
        $('.question_list').css('display', 'none');
        $('.question_write_form').css('display', 'flex');
    });

    $('.newQuestion').click(function (){
        $('.question_list').css('display', 'none');
        $('.question_write_form').css('display', 'flex');
    });

    $('.question_cancel').click(function(){
        $('.question_list').css('display', 'block');
        $('.question_write_form').css('display', 'none');
    });

    $('.question_tag').keydown(function (key) {
        let value = trim($(this).val().trim());
        if (key.keyCode == 13) {
            if (!pattern.test(value) && value != "") {
                let length = $('.hashtag_value').children().length;
                var template = "<div class='hash_tag_template'>"+
                    "<input type='hidden' name='courseTags["+length+"].tag' value="+ value +" />"+
                    "<span class='tag_value'>"+ value +"</span>" +
                    "<span><i class='fas fa-times'></i></span>" +
                    "</div>";
                $(template).appendTo($('.hashtag_value'));
                $(this).val('');
            }
        }else if(key.keyCode == 8 && value == ""){
            $('.hashtag_value').children().last().remove();
        }
    });
    $(document).on('click', '.hash_tag_template', function(){
        $(this).remove();
    });

    $('.question_submit').click(function (){
       let id = $('.question_write_form').attr('data-id');
       let cid = $('.question_write_form').attr('data-cid');
       let title = $('.question_name').val();
       let tagObj = $('.hashtag_value').children('.hash_tag_template');
       var tags = new Array();
       var i = 0;
       $(tagObj).each(function (){
           tags[i] = $(this).children('input').attr('value');
           i++;
       })
       let content = tinymce.get('myQuestion').getContent();

       let formData = {
           'id' : id,
           "class_id" : cid,
           "title" : title,
           "tags" : tags,
           "content" : content
       };
       $.ajax({
           url : '/question/add/json',
           method : 'post',
           dataType : 'json',
           data : formData,
           success : function (result){
                console.log(result);
                location.href="/study/"+id+"/lecture/"+cid+"/community";
               // $('.question_list').css('display', 'block');
               // $('.question_write_form').css('display', 'none');
               //  $('.question_list').load(window.location.href + ".question_list");
               // var template = "<div class='question_content_template' data-id='"+result.id+"'>"+
               //     "<div class='question_content_border_template'>" +
               //     "<div class='question_content_title_template'>질문</div>" +
               //     "<div class='question_content_title_value'>" + result.title + "</div>" +
               //     "<div class='question_content_content_value'>" + result.content + "</div>" +
               //     "</div>" +
               //     "<div class='question_content_toolbar_template'>" +
               //     "<div class='question_content_user_wrap'>" +
               //     "<div class='question_content_user_profile'>" +
               //     "<img src='"+ result.member.profile +"' class='question_content_user_profile_value'>" +
               //     "</div>" +
               //     "<div class='question_content_user_name'>" + result.member.nickName + "</div>" +
               //     "</div>" +
               //     "<div class='question_content_toolbar_wrap'></div>" +
               //     "</div>" +
               //     "</div>";
               // $(template).appendTo($('.question_content_wrap'));
           },error : function (e){
               console.log(e);
           }
       });
    });
});

function toggleTarget(target, obj){

    $('.study_menu').children().removeClass('checked');
    $(obj).addClass('checked');

    $('.study_tab_wrap').children('.study_tab').removeClass('opened');
    $('.study_tab_wrap').children('.study_tab').addClass('closed');

    if ($(target).hasClass('closed')) {
        $(target).addClass('opened');
        $(target).removeClass('closed');
    } else {
        $(target).removeClass('opened');
        $(target).addClass('closed');
    }
}

function createClassContent(name, course_id , class_id, sectionCode, classCode, study_time, preview, signup) {
    let tag = "class_box_"+class_id;
    var customTag = "<i class='fas fa-check-circle'></i>";
    var additionalClassLock = "";
    if(signup == true){
        additionalClassLock = "authentication";
    }else{
        if(preview == 'false'){
            customTag = "<i class='fas fa-lock'></i>";
            additionalClassLock = "lock";
        }
    }
    let parentObj = $('.class_wrap[data-sid=' + sectionCode + ']');
    let template = "<div class='class_box " + tag + " "+additionalClassLock+"' data-id="+course_id+" data-cid=" + class_id + " data-sid=" + sectionCode + " data-ccode=" + classCode + " data-p="+ preview +">" +
        "<div class='class_icon class_icon_"+class_id+"'><span class='play_icon'>" + customTag + "</span></div>" +
        "<div class='class_content'>" +
        "<div class='class_title'>" + name + "</div>" +
        "<div class='class_time'>" +
        "<span class='time_icon'><i class='fas fa-play-circle'></i></span>" +
        "<span>" + timeFormat(study_time) + "분</span></div></div></div>";
    $(parentObj).append(template);
}

function createSectionContent(name, course_id, section_id, sectionCode) {
    console.log(name);
    let parentObj = $('.course_content');
    let template = "<div class='section_box' data-course=" + course_id + " data-sid=" + section_id + " data-scode=" + sectionCode + ">" +
        "<div class='section_title'>" + name + "</div>" +
        "<div class='class_wrap' data-sid=" + sectionCode + "></div></div>";
    $(parentObj).append(template);
}

function timeFormatWrapper(second, obj) {
    console.log(second + " : " + timeFormat(second));
    $(obj).text(timeFormat(second));
}

function timeFormat(second) {
    var minutes = Math.floor((second / 60));
    return minutes;
}

function loadVideoUrl(url) {
    $('#myPlayer').attr('data-href', url);
}

function loadVideoJS(startTime, endTime) {
    let url = $('#myPlayer').attr('data-href');
    player = videojs("myPlayer", {
        sources: [{
            src: url,
            type: 'video/mp4'
        }],
        controls: true,
        playsinline: true,
        autoplay: true,
        preload: 'metadata'
    });
    player.on("ended", function () {
        saveCourseStudyData();
        let id = $('#myPlayer').attr("data-id");
        let cId = $('#myPlayer').attr("data-cId");
        $.ajax({
            url: '/study/'+id+'/next/'+cId,
            method: 'post',
            dataType: 'json',
            success: function(r){
                console.log(r.result);
                if(r.result != 'end'){
                    $('.video_ended').removeClass('hidden');
                    $('.course_next_name').html(r.name);
                    $('.next').attr('data-cId', r.id);
                }else{
                    /* 강의를 다들었을 때 */
                    clearInterval(studySave);
                    $('.clear_course_form_wrap').css('display', 'block');
                }
            },error: function(e){
                console.log(e);
            }
        })
    });
    if(startTime != null){
        console.log(startTime);
        if(startTime >= (endTime-10)){
            player.currentTime(0);
        }else{
            player.currentTime(startTime);
        }
    }
}

/* 강의의 권한이 없을 때 myPlayer display none */
function noAuthorized() {
    $('#myPlayer').css('display', 'none');
    $('.min_height').css('display', 'none');
}

function Authorized() {
    $('#myPlayer').css('display', 'flex');
    $('.min_height').css('display', 'flex');
}

/* 수강정보 저장 */
function saveCourseStudyData(){
    let id = $('#myPlayer').attr("data-id");
    let cId = $('#myPlayer').attr("data-cId");
    let currentTime = player.currentTime();
    $.ajax({
       url: '/study/'+id+'/save/'+cId,
        method:'post',
        dataType:'json',
        data: {'currentTime' : currentTime},
        success: function(r){
            console.log(r);
        },error: function (e){}
    });
}

function changeCourseStatus(class_id, startTime, endTime){
    let customTag = "";
    if(startTime >= (endTime-10))
        customTag = "played";

    $('.class_icon_'+class_id).addClass(customTag);
}

function changeActiveCourse(class_id){
    let cId = $('.course_content_wrap').attr('data-cId');
    console.log(cId + " : " + class_id);
    if(cId == class_id){
        if($('.class_box_'+class_id).hasClass('lock')){
            $('.class_box_'+class_id).addClass("active_lock");
        }else{
            $('.class_box_'+class_id).addClass("active");
        }
    }
}

function openModal(obj){
    $(obj).css("display", "block");
}

function openTab(tab){
    console.log(tab);
    if(tab == 'community'){
        toggleTarget('.question_wrap', $('.course_community'));
    }
}

tinymce.init({
    mode: 'textareas',
    selector: '#myQuestion',
    height: 500,
    plugins: 'image code media image',
    language_url: '/js/ko_KR.js',
    toolbar: 'undo redo | link image | code | media ',
    media_live_embeds: true,
    image_title: true,
    automatic_uploads: true,
    file_picker_types: 'image',
    video_template_callback: function (data) {
        return '<video width="' + data.width + '" height="' + data.height + '"' + (data.poster ? ' poster="' + data.poster + '"' : '') + ' controls="controls">\n' + '<source src="' + data.source1 + '"' + (data.source1mime ? ' type="' + data.source1mime + '"' : '') + ' />\n' + (data.source2 ? '<source src="' + data.source2 + '"' + (data.source2mime ? ' type="' + data.source2mime + '"' : '') + ' />\n' : '') + '</video>';
    },
    images_upload_handler: image_upload_handler,
    content_style: '//www.tinymce.com/css/codepen.min.css'
});