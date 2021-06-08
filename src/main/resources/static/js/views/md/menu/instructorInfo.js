$(function () {
    $(document).on('click', '.preview', function () {
        let url = $(this).attr('data-url');
        window.open("/course/"+url);
    });
    $(document).on('click', '.manage', function () {
        let id = $(this).attr('data-id');
        location.href = "/md/course/info/"+id;
    });

    $('.writeNotification').click(function(){
       $('.write_notification_wrapper').css('display', 'block');
    });

    $('.notification_cancel').click(function (){
       $('.write_notification_wrapper').css('display', 'none');
    });

    $(document).on('click', '.notification_template', function(){
       var arr = $('.notification_template_wrap').children();
       for(var i = 0; i < arr.length; i++){
           $(arr[i]).removeClass('checked');
       }
       $('.notificationType').val($(this).attr('data-value'));
       $(this).addClass('checked');
    });

    $('.notification_submit').click(function(){
        $('.notification_form').submit();
    });

    $('.write_notification_button').click(function(){
        $('.write_success_wrapper').css('display', 'none');
    });
});

tinymce.init({
    mode: 'textareas',
    selector: '#notificationContent',
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