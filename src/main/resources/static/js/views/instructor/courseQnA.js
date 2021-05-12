$(function(){
   $('.user_answer_submit').click(function(){
       $('.q_id').val($('.user_answer_form').attr('data-qid'));
       $('.user_answer_form').submit();
   });
   $('.back_button').click(function() {
       let id = $(this).attr('data-id');
       location.href="/question/select/"+id;
   });
   $('.course_link').on('click', function(){
      let url = $(this).attr('data-id');
      location.href = "/course/"+url;
   });
});


tinymce.init({
    mode : 'textareas',
    selector:'.user_answer',
    height : 400,
    plugins: 'image code media image',
    language_url: '/js/ko_KR.js',
    toolbar: 'undo redo | link image | code | media ',
    media_live_embeds: true,
    image_title: true,
    automatic_uploads: true,
    file_picker_types: 'image',
    video_template_callback: function(data) {
        return '<video width="' + data.width + '" height="' + data.height + '"' + (data.poster ? ' poster="' + data.poster + '"' : '') + ' controls="controls">\n' + '<source src="' + data.source1 + '"' + (data.source1mime ? ' type="' + data.source1mime + '"' : '') + ' />\n' + (data.source2 ? '<source src="' + data.source2 + '"' + (data.source2mime ? ' type="' + data.source2mime + '"' : '') + ' />\n' : '') + '</video>';
    },
    images_upload_handler: image_upload_handler,
    content_style: '//www.tinymce.com/css/codepen.min.css'
});