$(function(){
    $('.save_next_page').click(function (){
       $('.courseForm').submit();
    });
});

function image_upload_handler (blobInfo, success){
    var formData = new FormData();
    formData.append("file", blobInfo.blob());
    // console.log(blobInfo.filename());
    // console.log(blobInfo.blob());
    // console.log(formData);
    $.ajax({
        url : "/course/upload/img",
        method : "POST",
        data : formData,
        processData : false,  // ajax는 기본적으로 Query String 으로 데이털르 보내는데 file 전송시에는 Query String 으로 보낼 필요가 없음. 이 값이 Query String의 유무를 체크하는 것
        contentType: false, // content-type의 기본값은 application/x-www-form-urlencoded; charset=UTF-8이다. 파일 전송시 Content Type은 Multipart/form-data 이므로
        // content-Type을 false해주게 되면 전송 헤더 값이 Multipart/form-data로 변경 됨
        cache: false,
        timeout: 600000,
        dataType : "json",
        success: function (result) {
            success(result.data_url);
        },
        error: function (e) {
            console.log("error by" + e);
        }
    });
}
tinymce.init({
    mode : 'textareas',
    selector:'#editor',
    height : 500,
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