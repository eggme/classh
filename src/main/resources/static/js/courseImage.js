function changeImage(file, id){
    var form = new FormData();
    var parameter = $("meta[name='_csrf_parameter']").attr('content');
    var token = $("meta[name='_csrf']").attr("content");
    form.append("file", $(file)[0].files[0]);
    console.log("gd");
    $.ajax({
        url : '/course/'+id+'/upload/thumbnail?'+parameter+"="+token,
        method : 'post',
        processData : false,
        contentType: false,
        cache : false,
        data : form,
        beforeSend: function(){
            $('.upload-name').val('업로드 중입니다...');
        },
        success : function(result){
            $('.course_cover_image').attr('src', result.courseImg);
            location.href="/course/"+id+"/thumbnail";
        }
    })
}