$(function () {
    // 미리보기
    $('.show_preview').click(function () {
        var course_id = $('.course_id').val();
        location.href = "/courseInfo.do?course_id=" + course_id + "&instructor=true";
    });
    // 수업 추가하기
    $(document).on("click", ".add_class", function () {
        var section_code = $(this).parents('.section_init').data('value');
        var section_id = $(this).parents('.section_init').data('code');
        $('.class_form').attr('data-value', section_code);
        $('.class_form').attr('data-code', section_id);
        $('.class_form').css('display', 'block');
    });
    // 수업 삭제
    $(document).on('click', '.remove_class', function(){
        let result = confirm("정말로 삭제하시겠습니까?");
        if(result) {
            var class_id = $(this).parents('.class_box_line').data('class');
            console.log(class_id);
            deleteClass(class_id);
        }
    });
    // 섹션 편집
    $(document).on('click', ".edit_section", function(){
        let section_id = $(this).parents('.section_init').data('code');
        $('.edit_section_form').attr('data-id', section_id);
        $('.edit_section_form').css('display', 'block');
    });
    // 섹션 삭제
    $(document).on('click', '.remove_section', function(){
        let result = confirm("정말로 삭제하시겠습니까?");
        if(result){
            let section_id = $(this).parents('.section_init').data('code');
            deleteSection(section_id);
        }
    });
    // 수업 편집
    $(document).on("click", ".edit_class", function () {
        clearForm();
        findClassData($(this));
        $('.class_description_form').css('display', 'block');
    });
    $('.class_cancel').click(function () {
        $('.class_form').css('display', 'none');
    });
    $('.class_submit').click(function () {
        let id = $('.section_form').attr("data-id");
        let section_code = $('.class_form').attr('data-value');
        let section_id = $('.class_form').attr('data-code');
        let class_code = $('.section_' + section_code).find(".class_box").children("div").length;
        let name = $('.title_class').val();

        $.ajax({
            url: '/course/' + id + '/save/class',
            dataType: 'json',
            method: 'post',
            data: {
                name: name,
                course_section_id: section_id
            },
            success: function (result) {
                $('.class_form').css('display', 'none');
                createClassBox(name, section_id, section_code, class_code, result.id, false, false, false);
            }, error: function (e) {
                console.log(e);
            }
        });
    });
    $('.add_section').click(function () {
        $('.section_form').css('display', 'block');
    });
    $('.section_cancel').click(function () {
        $('.section_form').css('display', 'none');
    });
    $('.section_submit').click(function () {
        saveSection();
    });
    $('.edit_section_submit').click(function(){
        editSection();
    });
    $('.edit_section_cancel').click(function(){
       $('.edit_section_form').css('display','none');
    });
    $('.class_description_cancel').click(function () {
        $('#ex_filename').val();
        $('.class_description_form').css('display', 'none');
    });
    $('.class_description_submit').click(function () {
        // ajax 통신을 사용하여 db에 전송해야 할듯??
        $('.editClassForm').submit();
        $('.class_description_form').css('display', 'none');

    });
    $('.isPublic_toggle_button').on('click', function () {
        if (!$('.isPublic_toggle_input').is(":checked")) {
            checkboxOn();
        } else {
            checkboxOff();
        }
    });
});
/* 수업 삭제하기 */
function deleteClass(class_id){
    $.ajax({
        url: "/course/"+class_id+"/delete/class",
        method : "post",
        success : function(result){
            console.log(result);
            let section_box = $('.class_box_line[data-class='+class_id+']');
            console.log($(section_box).attr('class'));
            $(section_box).remove();
        },
        error : function(e){
            console.log(e);
        }
    });
}

/* 섹션 삭제하기 */
function deleteSection(section_id){
    $.ajax({
        url: "/course/"+section_id+"/delete/section",
        method : "post",
        success : function(result){
            console.log(result);
            let section_box = $('.section_init[data-code='+section_id+']');
            console.log($(section_box).attr('class'));
            $(section_box).remove();
        },
        error : function(e){
            console.log(e);
        }
    });
}

/* 섹션 추가하기 */
function saveSection(){
    let id = $('.section_form').attr("data-id");
    let name = $('.title_section').val();
    let goal = $('.goal_section').val();
    let section_code = $('.section_box').children('div').length;
    $.ajax({
        url: "/course/" + id + "/save/section",
        dataType: "json",
        method: "post",
        data: {
            "name": name,
            "goal": goal
        },
        success: function (result) {
            createSectionBox(name, section_code, result.id);
        },
        error: function (e) {
            console.log(e);
        }
    });
}

/* 섹션 수정하기 */
function editSection(){
    let id = $('.edit_section_form').attr("data-id");
    let name = $('.title_edit_section').val();
    let goal = $('.goal_edit_section').val();
    let section_code = $('.section_box').children('div').length;
    $.ajax({
        url: "/course/edit/section",
        dataType: "json",
        method: "post",
        data: {
            "id": id,
            "name": name,
            "goal": goal
        },
        success: function (result) {
            let section_box = $('.section_init[data-code='+result.id+']');
            let section_title = $(section_box).find('.section_title');
            $(section_title).text(" : "+result.name);
            $('.title_edit_section').val('');
            $('.goal_edit_section').val('');
            $('.edit_section_form').css('display','none');
        },
        error: function (e) {
            console.log(e);
        }
    });
}

function clearForm(){
    $('.title_class_description').val('');
    $('.upload-name').val('');
    $('.upload-name').attr('disable', 'false');
    $('.mediaPath').val('');
    $('.isPublic_toggle_input').attr("checked", false);
    tinymce.get("instructorNote").setContent('');
    $('.fileUpload-name').val('');
    $('.fileUpload-name').attr('disable', 'false');
    $('.dataPath').val('');
    $('.id').val('');
}

function checkboxOn(){
    $('.on_off').text("ON")
    $('.on_off').removeClass('toggle_off');
    $('.on_off').addClass('toggle_on');
}
function checkboxOff(){
    $('.on_off').text("OFF")
    $('.on_off').removeClass('toggle_on');
    $('.on_off').addClass('toggle_off');
}

function loadCourseResource(obj) {
    let class_id = obj;
    $.ajax({
        url: "/course/edit/class",
        method: "post",
        dataType: "json",
        data : {"class_id" : class_id},
        success: function (result) {
            console.log(result);
            $('.title_class_description').val(result.name);
            console.log(result.mediaPath + " : " +result.dataPath);
            $('.upload-name').val(result.mediaPath);
            $('.upload-name').attr('disable', 'true');
            $('.mediaPath').val(result.mediaPath);
            if(result.preview == true){
                console.log("wow");
                $('.isPublic_toggle_input').attr("checked", true);
                checkboxOn();
            }else{
                $('.isPublic_toggle_input').attr("checked", false);
                checkboxOff();
            }
            console.log(result.instructorMemo);
            tinymce.get("instructorNote").setContent(result.instructorMemo);
            $('.fileUpload-name').val(result.dataPath);
            $('.fileUpload-name').attr('disable', 'true');
            $('.dataPath').val(result.dataPath);
            $('.id').val(class_id);
        }
    });
}


// 수업 박스 생성
function createClassBox(title, course_section_id, section_index, class_index, class_id, isPreview, inVideo, inFile, isReleased) {
    var video = "";
    var preview = "";
    var file_form = "";

    if(inFile == true){
        file_form = "<div class='file_icon_class'>"+
            "<i class='fas fa-cloud-download-alt'></i>"+
            "</div>";
    }else{
        file_form = "";
    }
    if(inVideo == true) {
        video = "<div class='video_class'>"+
            "<i class='far fa-play-circle'></i>"+
            "</div>";
    }else{
        video = "";
    }
    if(isPreview == true){
        preview = "<div class='preview_class'>"+
                "<i class='fas fa-eye'></i>"+
                "</div>";
    }else{
        preview = "";
    }
    var template = "<div class='class_" + class_id + " class_box_line' data-value='" + class_index + "' data-code='" + course_section_id + "' data-class='" + class_id + "'>" +
        "<div class='class_text classes'>" +
        "<p>수업 " + class_index + " : " + title + "</p>" +
        "</div>" +
        "<div class='class_toolbox'>" +
            video +
            preview +
            file_form +
            "<div class='edit_class'>" +
                "<i class='fas fa-pencil-alt'></i>" +
            "</div>" +
            "<div class='remove_class'>" +
                "<i class='fas fa-trash-alt'></i>" +
            "</div>" +
        "</div>" +
        "</div>";
    if(isReleased){
        template = "<div class='class_" + class_id + " class_box_line' data-value='" + class_index + "' data-code='" + course_section_id + "' data-class='" + class_id + "'>" +
            "<div class='class_text classes'>" +
            "<p>수업 " + class_index + " : " + title + "</p>" +
            "</div>" +
            "<div class='class_toolbox'>" +
            video +
            preview +
            file_form +
            "</div>" +
            "</div>";
    }
    $(template).appendTo($('.section_' + section_index).find(".class_box"));
    $('.title_class').val('');
    $('.class_form').css('display', 'none');
}

// 섹션 박스 생성
function createSectionBox(title, section_code, section_id, isReleased) {
    var template = "<div class='section_" + section_code + " section_init section_margin' data-value=" + section_code + " data-code=" + section_id + ">" +
        "<div class='section_data'>" +
        "<div class='section_info sections'>" +
        "<span class='section_number'>섹션 " + section_code + "</span>" +
        "<span class='section_title'>&nbsp;: " + title + "</span>" +
        "</div>" +
        "<div class='section_menu'>" +
        "<div class='remove_section'>" +
        "<i class='fas fa-trash-alt'></i>" +
        "</div>" +
        "<div class='edit_section'>" +
        "<i class='fas fa-pencil-alt'></i>" +
        "</div>" +
        "<div class='add_class'>" +
        "<i class='fas fa-plus-circle'></i>&nbsp;수업 추가하기" +
        "</div>" +
        "</div>" +
        "<div class='class_wrap'>" +
        "<div class='class_box'>" +
        "</div>" +
        "</div>" +
        "</div>" +
        "</div>";
    if(isReleased){
        template = "<div class='section_" + section_code + " section_init section_margin' data-value=" + section_code + " data-code=" + section_id + ">" +
            "<div class='section_data'>" +
            "<div class='section_info sections'>" +
            "<span class='section_number'>섹션 " + section_code + "</span>" +
            "<span class='section_title'>&nbsp;: " + title + "</span>" +
            "</div>" +
            "<div class='section_menu'>" +
            "</div>" +
            "<div class='class_wrap'>" +
            "<div class='class_box'>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "</div>";
    }
    $(template).appendTo('.section_box');
    $('.title_section').val('');
    $('.section_form').css('display', 'none');
}

// 수업 첨부자료 업로드
function StudyFileUpload(file) {
    var uploadFile = $('#study_filename')[0].files[0];
    let section_number = $('.section_number').val();
    let class_number = $('.class_number').val();
    let class_id = $('.class_id').val();
    /* csrf 토큰 */
    var parameter = $("meta[name='_csrf_parameter']").attr('content');
    var token = $("meta[name='_csrf']").attr("content");

    let data = new FormData();
    console.log(uploadFile);
    data.append('file', uploadFile);

    $.ajax({
        url: "/course/upload/file/" + class_id + "/?" + parameter + "=" + token,
        method: "POST",
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        success: function (result) {
            $('.fileUpload-name').val(result.dataPath);
            $('.dataPath').val(result.dataPath);
        },
        beforeSend: function () {
            $('.fileUpload-name').val('업로드 중입니다...');
        },
        error: function (e) {
            console.log("error by" + e);
        }
    });
}

// 수업 영상 업로드
function fileUpload(file) {
    console.log($(this));
    console.log($(this).parent());
    var uploadFile = $('input[type=file]')[0].files[0];
    let section_number = $('.section_number').val();
    let class_number = $('.class_number').val();
    let class_id = $('.class_id').val();
    let id = $('.class_description_form').attr("data-id");
    /* csrf 토큰 */
    var parameter = $("meta[name='_csrf_parameter']").attr('content');
    var token = $("meta[name='_csrf']").attr("content");
    var duration = 0;

    let data = new FormData();
    data.append('file', uploadFile);
    /* 파일리더를 통해 동영상 시간 추출 */
    var reader = new FileReader();
    reader.onload = function () {
        var aud = new Audio(reader.result);
        aud.onloadedmetadata = function () {
            duration = aud.duration;
            console.log("/course/" + id + "/upload/video/" + class_id + "/" + duration + "?" + parameter + "=" + token);
            /* ajax에서 form 데이터를 쓸려면 csrf parameterName과 token이 필요함, 요청 URL 경로에 붙이면 됌 */
            $.ajax({
                url: "/course/" + id + "/upload/video/" + class_id + "/" + duration + "?" + parameter + "=" + token,
                method: "POST",
                data: data,
                processData: false,
                contentType: false,
                cache: false,
                xhr: function(){
                    var xhr = new window.XMLHttpRequest();
                    xhr.upload.addEventListener("progress", function(evt){
                        console.log(evt.lengthComputable);
                        if(evt.lengthComputable){
                            var percentComplete = (evt.loaded / evt.total) * 100;
                            console.log(percentComplete);
                            $('.upload-name').val("업로드 중입니다..."+percentComplete+"%");
                        }
                    }, false);
                    return xhr;
                },
                success: function (result) {
                    $('.upload-name').val(result.mediaPath);
                    $('.mediaPath').val(result.mediaPath);
                },
                error: function (e) {
                    console.log("error by" + e);
                }
            });
        };
    };
    reader.readAsDataURL(uploadFile);
}


// 수업과 섹션을 찾아서 숨겨놓은 수업 변경 시 데이터 들어가게 함
function findClassData(obj) {
    var div_wrap = obj.parents('.class_box_line');
    var class_code = $(div_wrap).attr('data-value');
    var class_id = $(div_wrap).attr('data-class')
    var temp = div_wrap.find('.class_text');
    var texts = temp.text();
    // 텍스트 추출
    var title = texts.substr(texts.indexOf(':') + 1, texts.length);
    // 섹션 번호 추출
    var section_ = obj.parents('.section_init');
    var section_id = $(section_).attr('data-code');
    var section_code = $(section_).attr('data-value');

    var video = $(div_wrap).attr('data-url');

    console.log("섹션 : " + section_code + ", 수업 : " + class_code);
    if (video != '') {
        $('.upload-name').val(video);
    }
    // 클래스 번호 저장
    $('.section_id').val(section_id);
    $('.class_number').val(class_code);
    $('.section_number').val(section_code);
    $('.class_id').val(class_id);
    $('.id').val(class_id);
    loadCourseResource(class_id);
}

tinymce.init({
    mode: 'textareas',
    selector: '#instructorNote',
    height: 350,
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

function redirectThumbnail(course_id){
    location.href = "/course/" + course_id +"/thumbnail";
}