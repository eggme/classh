var pattern = /([^가-힣a-zA-Z0-9\x20])/i;

function trim(str) {
    return str.replace(/(^\s+)|(\s+)|(^ㄱ-ㅎㅏ-ㅣ\x20)$/, "");
}

$(function () {
    /* 답변 등록 시 호출 */
    $('.user_answer_submit').click(function () {
        $('.q_id').val($('.user_answer_form').attr('data-qid'));
        $('.user_answer_form').submit();
    });
    /* 왼쪽 사이드 뒤로가기 버튼 */
    $('.back_button').click(function () {
        let id = $(this).attr('data-id');
        location.href = "/question/select/" + id;
    });
    /* 연관 검색에 강의 링크 클릭 시 해당 강의 대시보드로 감 */
    $('.course_link').on('click', function () {
        let url = $(this).attr('data-id');
        location.href = "/course/" + url;
    });
    /* 강의 수정 누를 시 데이터 가져오는 콜백 */
    $('.question_edit_box').click(function () {
        let id = $(this).attr('data-id');
        $.ajax({
            url: '/question/select',
            method: 'post',
            dataType: 'json',
            data: {'id': id},
            success: function (result) {
                $('.question_name').val(result.title);
                tinymce.get('myQuestion').setContent(result.content);
                $('.hashtag_value').html('');
                for (var i = 0; i < result.courseTags.length; i++) {
                    console.log("hihi" + i);
                    var template = "<div class='hash_tag_template'>" +
                        "<input type='hidden' name='courseTags[" + i + "].tag' value=" + result.courseTags[i].tag + " />" +
                        "<span class='tag_value'>" + result.courseTags[i].tag + "</span>" +
                        "<span><i class='fas fa-times'></i></span>" +
                        "</div>";
                    $(template).appendTo($('.hashtag_value'));
                    $(this).val('');
                }
                $('.question_write_form_wrapper').attr('data-id', result.id);
                $('.question_write_form_wrapper').css('display', 'block');
            }, error: function (e) {
                console.log(e);
            }
        });
    });
    /* 강의 수정 폼에서 수정하기 저장하기 누를시 호출 */
    $('.question_submit').click(function () {
        $('.q_id').val($('.question_write_form_wrapper').attr('data-id'));
        $('.question_edit_form').submit();
    });
    /* 질문 삭제 버튼 클릭 */
    $('.question_delete_box').click(function(){
        $('.question_delete_form_wrapper').attr('data-id', $(this).attr('data-id'));
        $('.question_delete_form_wrapper').css("display", "block");
    });
    /* 질문삭제 폼 삭제 클릭 */
    $('.question_delete_submit').click(function(){
        let id = $('.question_delete_form_wrapper').attr('data-id');
        $.ajax({
            url: "/question/delete",
            method: "post",
            data : {"id" : id},
            dataType: "json",
            success: function(result){
                console.log(result);
                location.href="/course/"+result.url;
            },error: function (e){
                console.log(e);
            }
        })
    });
    /* 질문삭제 폼 취소 클릭 */
    $('.question_delete_cancel').click(function(){
        $('.question_delete_form_wrapper').css("display", "none");
    });
    /* 클릭 시 해시태그 삭제 */
    $(document).on('click', '.hash_tag_template', function () {
        $(this).remove();
    });
    /* 질문 수정창 닫기 */
    $('.question_cancel').click(function () {
        $('.question_write_form_wrapper').css('display', 'none');
    });
    /* 해시태그 입력 */
    $('.question_tag').keydown(function (key) {
        let value = trim($(this).val().trim());
        if (key.keyCode == 13) {
            if (!pattern.test(value) && value != "") {
                let length = $('.hashtag_value').children().length;
                var template = "<div class='hash_tag_template'>" +
                    "<input type='hidden' name='courseTags[" + length + "].tag' value=" + value + " />" +
                    "<span class='tag_value'>" + value + "</span>" +
                    "<span><i class='fas fa-times'></i></span>" +
                    "</div>";
                $(template).appendTo($('.hashtag_value'));
                $(this).val('');
            }
        } else if (key.keyCode == 8 && value == "") {
            $('.hashtag_value').children().last().remove();
        }
    });
    /* 강의 상태 변경 */
    $('.question_status').click(function(){
        let isMeWrote = $('.isMeWrote').attr('data-id');
        let status = $('.question_status').text().trim();
        if(isMeWrote == 1){
            let id = $('.isMeWrote').attr('data-qid');
            $.ajax({
                url : "/question/changeStatus",
                method: "post",
                data : {"id" : id, "status" : status},
                success : function(result){
                    location.href="/question/"+id;
                },error : function(e){
                    console.log(e);
                }
            });
        }
    });
});


tinymce.init({
    mode: 'textareas',
    selector: '.user_answer',
    height: 400,
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
    selector: '#myQuestion',
    height: 400,
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


function resolvedClass(text, obj){
    if(text == "해결"){
        $(obj).addClass('resolved');
    }
    $(obj).text(text);
}