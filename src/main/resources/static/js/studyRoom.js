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
        let question_id = $(this).attr('data-id');
        openQuestion(question_id);
    });

    $('.question_comment_write_button').click(function(){
        if($(this).hasClass('access')){
            let currentUserId = $(this).attr('data-id');
            let content = tinymce.get('myComment').getContent();
            let q_id = $('.question_obj_content').attr('data-id');
            console.log(currentUserId + " : " + content + " : "+ q_id);

            $.ajax({
                url : "/question/add/comment/json",
                method : "post",
                dataType : "json",
                data : {
                    "user_id" : currentUserId,
                    "content" : content,
                    "q_id" : q_id
                },success : function (result){
                    if(result == "success"){
                        openQuestion(q_id);
                        tinymce.get('myComment').setContent("");
                    }
                },error : function (e){
                    console.log(e);
                }
            })
        }
    });

    $('.course_community').click(function (){
        toggleTarget('.question_wrap', $(this));
        var url = location.href;
        url = url.replace(/\?tab=([a-zA-Z]+)/ig, "");
        url += "?tab=community";
        history.pushState(null, null, url);
    });

    $('.course_note').click(function(){
        toggleTarget('.note_wrap', $(this));
        var url = location.href;
        url = url.replace(/\?tab=([a-zA-Z]+)/ig, "");
        url += "?tab=note";
        history.pushState(null, null, url);
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
    /* 커뮤니티 게시판의 질문 상세 페이지에서 수정 버튼을 눌렀을 때 */
    $('.question_edit_button').click(function (){
        let question_id = $('.question_obj_content').attr('data-id');
        console.log(question_id);
        $.ajax({
            url : "/question/select/json",
            method: "post",
            dataType : "json",
            data : {"id" : question_id},
            success : function (result){
                console.log(result);
                $('.question_name').val(result.title);
                tinymce.get('myQuestion').setContent(result.content);

                let length = result.courseTags.length;
                $('.hashtag_value').html('');
                for(var i = 0; i < length; i++){
                    let value = result.courseTags[i].tag;
                    var template = "<div class='hash_tag_template'>"+
                        "<input type='hidden' name='courseTags["+length+"].tag' value="+ value +" />"+
                        "<span class='tag_value'>"+ value +"</span>" +
                        "<span><i class='fas fa-times'></i></span>" +
                        "</div>";
                    $(template).appendTo($('.hashtag_value'));
                }
            },error : function (e){
                console.log(e);
            }
        });
        $('.question_obj').css('display', 'none');
        $('.question_write_form').css('display', 'flex');

        $('.question_write_form').addClass("editable");
        $('.question_write_form').attr('data-qid', question_id);
        $('.question_submit').text("수정하기");
    });
    /* 커뮤니티 게시판의 질문 상세 페이지에서 삭제 버튼을 눌렀을 때 */
    $('.question_delete_button').click(function(){
        let id = $('.question_write_form').attr('data-id');
        let cid = $('.question_write_form').attr('data-cid');
        $('.delete_question_form_wrap').attr("data-id", id);
        $('.delete_question_form_wrap').attr('data-cid', cid);
        $('.delete_question_form_wrap').css("display", "block");
    });

    /* 커뮤니티 게시판의 질문 상세 페이지에서 삭제버튼을 누른 후 모달에서 확인을 눌렀을 때 */
    $('.delete_question_submit').click(function(){

        let question_id = $('.question_obj_content').attr('data-id');
        $.ajax({
            url : "/question/delete/json",
            method : 'post',
            data : {"id" : question_id},
            dataType : "json",
            success: function (result) {
                console.log(result);
                if(result.msg == "success"){
                    let id = $('.delete_question_form_wrap').attr('data-id');
                    let cId = $('.delete_question_form_wrap').attr('data-cId');
                    console.log("result = success");
                    location.href = "/study/"+id+"/lecture/"+cId+"?tab=community";
                }
            },error : function (e){
               console.log(e);
            }
        });
        $('.question_obj').css('display', 'none');
        $('.question_list').css('display', 'block');
        $('.delete_question_form_wrap').css("display", "none");
    });

    /* 커뮤니티 게시판의 질문 상세 페이지에서 삭제버튼을 누른 후 모달에서 취소를 눌렀을 때 */
    $('.delete_question_cancel').click(function (){
        $('.delete_question_form_wrap').css("display", "none");
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
    /* 질문 등록 버튼을 눌렀을 때 */
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
        var formData = {};
        /* 실제로 등록할 때 */
        if(!$('.question_write_form').hasClass('editable')){
            formData = {
                'id' : id,
                "class_id" : cid,
                "title" : title,
                "tags" : tags,
                "content" : content,
                "editable" : false
            };
        }else{
            /* 수정할 때 */
            let qid = $('.question_write_form').attr('data-qid');
            formData = {
                'id' : id,
                "class_id" : cid,
                "title" : title,
                "tags" : tags,
                "content" : content,
                "editable" : true,
                "q_id" : qid
            };
        }

        $.ajax({
            url : '/question/add/json',
            method : 'post',
            dataType : 'json',
            data : formData,
            success : function (result){
                console.log(result);
                location.href="/study/"+id+"/lecture/"+cid+"?tab=community";
            },error : function (e){
                console.log(e);
            }
        });
    });

    /* 노트에서 노트 클릭 시 포커스 주는 함수 */
    $(document).on('click', '.note_content_template', function(){
        /* 포커스 제거 */
        if($('.note_content_wrap').children(".note_content_template").hasClass("clicked")){
            $('.note_content_wrap').children(".note_content_template").removeClass("clicked");
        }
        /* 포커스 추가 */
        $(this).addClass("clicked");
    });

    /* 노트에서 노트 입력 클릭 시 ajax로 노트 정보 전송 */
    $('.note_write_button').click(function (){
        var formData = {
            "course" : $(".note_write_form").attr('data-id'),
            "courseClass" : $(".note_write_form").attr('data-cId'),
            "content" : tinymce.get("myNote").getContent(),
            "seconds" : parseInt(player.currentTime())
        };
        $.ajax({
            url : "/note/add/json",
            method : "post",
            dataType : "json",
            data : formData,
            success : function (result){
                console.log(result);

                var template = "<div class='note_content_template' data-id='"+ result.course.id +"' data-cId='"+ result.courseClass.id +"'>" +
                                    "<div class='note_content_form'>" +
                                        result.content +
                                    "</div>" +
                                    "<div class='note_toolbox_form'>" +
                                        "<div class='note_edit_wrap'>" +
                                            "<div class='note_edit_icon'>" +
                                                "<i class='far fa-edit'></i>" +
                                            "</div>" +
                                            "<div class='note_edit_text'>수정</div>" +
                                        "</div>" +
                                        "<div class='note_delete_wrap'>" +
                                            "<div class='note_delete_icon'>" +
                                                "<i class='far fa-trash-alt'></i>" +
                                            "</div>" +
                                            "<div class='note_delete_text'>삭제</div>" +
                                        "</div>" +
                                    "</div>" +
                                "</div>";
                console.log(template);
                $(template).appendTo(".note_content_wrap");
            },error : function (e){
                console.log(e);
            }
        })
    });
    /* 노트에서 수정 클릭 시 해당 데이터를 에디터에 세팅해줌 */
    $(document).on('click', '.note_edit_wrap', function (){

        /* 먼저 클릭된 에디터들을 지움 */
        if($('.note_content_wrap').children(".note_content_template").children().hasClass("edit_mode")){
            $('.note_content_wrap').children(".note_content_template").children().css('display', 'flex');
            $('.note_content_wrap').children(".note_content_template").children().css('display', 'flex');
            $('.note_content_wrap').children(".note_content_template").children(".edit_mode").remove();
            tinymce.EditorManager.execCommand('mceRemoveEditor', true, 'editNote');
        }

        /* 에디터 추가 */
        let obj = $(this).closest('.note_content_template');
        let id = $(obj).attr('data-id');
        let cId = $(obj).attr('data-cId');
        let nId = $(obj).attr('data-nId');
        let content = $(obj).children('.note_content_form').text();
        $(obj).children(".note_content_form").css('display', 'none');
        $(obj).children(".note_toolbox_form").css('display', 'none');

        var template = "<div class='edit_mode' data-id='"+id+"' data-cId='"+cId+"' data-nId='"+nId+"'>"+
                            "<div class='edit_textarea'>" +
                                "<textarea id='editNote' class='noteForm'></textarea>" +
                            "</div>" +
                            "<div class='edit_toolbar'>" +
                                "<div class='edit_mode_cancel_button'>취소</div>" +
                                "<div class='edit_mode_submit_button'>업데이트</div>" +
                            "</div>" +
                        "</div>";
        $(template).appendTo($(obj));
        tinymce.EditorManager.execCommand('mceAddEditor', true, 'editNote');
        tinymce.get("editNote").setContent(content);

    });
    /* 노트에서 삭제 클릭 시 삭제 모달을 킴 */
    $(document).on('click', '.note_delete_wrap', function (){
        $('.delete_note_form_wrap').css('display', 'block');
        let note_id = $(this).closest(".note_content_template").attr('data-nid');
        $('.delete_note_form_wrap').attr('data-nid', note_id);
    });
    /* 노트 삭제 모달에서 취소를 눌렀을 때 */
    $('.delete_note_cancel').click(function (){
        $('.delete_note_form_wrap').css('display', 'none');
    });
    /* 노트 삭제 모달에서 확인을 눌렀을 때 */
    $('.delete_note_submit').click(function (){
        let id = $('.delete_note_form_wrap').attr('data-nid');

        $.ajax({
            url : "/note/delete/json",
            method : "post",
            dataType : "json",
            data : {"note_id" : id},
            success : function (result){

            },error : function (e){
                console.log(e);
            }
        })
    });

    /* 수정 클릭 후 에디터가 나와서 노트 수정을 할 때, 수정 취소를 눌렀을 때 */
    $(document).on('click', '.edit_mode_cancel_button', function (){
        tinymce.EditorManager.execCommand('mceRemoveEditor', true, 'editNote');
        let obj = $(this).closest('.note_content_template');
        $(obj).children(".note_content_form").css('display', 'flex');
        $(obj).children(".note_toolbox_form").css('display', 'flex');
        $(obj).children(".edit_mode").remove();

    });

    /* 수정 클릭 후 에디터가 나와서 노트 수정을 할 때, 업데이트를 눌렀을 때 */
    $(document).on('click', '.edit_mode_submit_button', function (){
        let obj = $(this).closest('.note_content_template');
        let note_id = $(this).closest(".note_content_template").attr('data-nId');
        let content = tinymce.get("editNote").getContent();
        let second = parseInt(player.currentTime());

        let formData = {
            "note_id" : note_id,
            "content" : content,
            "seconds" : second
        };

        $.ajax({
            url : "/note/edit/json",
            method : 'post',
            dataType : "json",
            data : formData,
            success : function (result){
                console.log(result);
                console.log($(obj).html());
                $(obj).children(".note_content_form").html(result.content);
                $(obj).children(".note_content_form").css('display', 'flex');
                $(obj).children(".note_toolbox_form").css('display', 'flex');
                tinymce.EditorManager.execCommand('mceRemoveEditor', true, 'editNote');
                $(obj).children(".edit_mode").remove();
            },error : function (e){
                console.log(e);
            }
        })
    });
});

function openQuestion(question_id){
    $.ajax({
        url : "/question/"+question_id,
        dataType : "json",
        method : "post",
        data : {"id" : question_id},
        success : function (result) {
            console.log(result);
            $('.question_obj').attr("data-id", result.course.id);
            $('.question_obj').attr("data-cid", result.courseClass.id);
            $('.question_obj_title_value').text(result.courseQuestion.title);
            $('.question_obj_user_name').text(result.courseQuestion.member.nickName);
            formatAMPM(result.courseQuestion.modify_at,'.question_obj_create_at');
            $('.question_obj_content_value').html(result.courseQuestion.content);
            let currentUserId = $('.question_obj_toolbox').attr('data-id');
            $('.question_obj_content').attr('data-id', result.courseQuestion.id);
            if(currentUserId == result.courseQuestion.member.id){
                $('.question_obj_toolbox').addClass('question_flex');
            }
            $('.question_obj_tag_wrap').html("");
            for(var i = 0; i<result.courseQuestion.courseTags.length; i++){
                let tag = "<div class='question_tag_value'>" +
                    "#"+
                    result.courseQuestion.courseTags[i].tag +"</div>";
                $(tag).appendTo($('.question_obj_tag_wrap'));
            }
            $('.question_comment_wrap').html('');
            for(var i = 0; i<result.list.length; i++){

                let ownerToolbox = "";
                let currentUserId = $('.question_obj').attr('data-cuid');
                if(result.list[i].member.id == currentUserId){
                    ownerToolbox = "<div class='comment_profile_toolbox'>" +
                                        "<div class='comment_profile_edit'>수정</div>"+
                                        "<div class='comment_profile_delete'>삭제</div>"+
                                    "</div>";
                }

                var template = "<div class='comment_template_wrap' data-id='"+result.list[i].id+"'>" +
                                    "<div class='comment_profile_image_wrap'>" +
                                        "<div class='comment_profile_image_box'>" +
                                            "<img class='comment_profile_image_value' src='"+ result.list[i].member.profile +"'>"+
                                        "</div>"+
                                    "</div>" +
                                    "<div class='comment_profile_content_wrap'>" +
                                        "<div class='comment_profile'>" +
                                            "<div class='comment_profile_wrap'>" +
                                                "<div class='comment_profile_name'>"+ result.list[i].member.nickName +"</div>" +
                                                "<div class='comment_profile_create_at'>" + formatAMPMData(result.list[i].create_at) + "</div>" +
                                                "<div class='comment_profile_toolbox'></div>" +
                                            "</div>"+
                                            ownerToolbox +
                                        "</div>" +
                                        "<div class='comment_content_wrap'>" +
                                            result.list[i].commentContent +
                                        "</div>" +
                                    "</div>" +
                                "</div>";
                $(template).appendTo(".question_comment_wrap");
            }

        },error: function (e){
            console.log(e);
        }
    });

    $('.question_list').css('display', 'none');
    $('.question_obj').css('display', 'flex');
}

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

function setCurrentUserId(id){
    $('.question_obj').attr('data-cuid', id);
}

function openTab(tab){
    console.log(tab);
    if(tab == 'community'){
        toggleTarget('.question_wrap', $('.course_community'));
    }else if(tab == 'note'){
        toggleTarget('.note_wrap', $('.course_note'));
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

tinymce.init({
    mode: 'textareas',
    selector: '#myComment',
    height: 200,
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

tinymce.init({
    mode: 'textareas',
    selector: '#myNote',
    height: 200,
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

tinymce.init({
    mode: 'textareas',
    selector: '#editNote',
    height: 200,
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