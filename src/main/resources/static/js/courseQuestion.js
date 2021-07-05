var pattern = /([^가-힣a-zA-Z0-9\x20])/i;

function trim(str){
    return str.replace(/(^\s+)|(\s+)|(^ㄱ-ㅎㅏ-ㅣ\x20)$/, "");
}

$(function () {
    $('.button_area').click(function () {
        $('.question_write_form_wrapper').css('display', 'block');
    });
    $('.question_cancel').click(function () {
        $('.question_write_form_wrapper').css('display', 'none');
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

    /* 질문답변 게시판 질문 답변 작성 시 */
    $('.question_submit').click(function(){
        $('.course_id').val($('.question_write_form_wrapper').attr('data-id'));
        $('.question_form').submit();
    });
    $('.question_content').on('click',function(){
        let id = $(this).attr('data-id');
       location.href="/question/"+id;
    });
});

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