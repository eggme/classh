function findSectionObject(obj) {
    console.log("findSectionObject -> " + id);
    for (var i = 0; i < resultObj.length; i++) {
        if (resultObj[i].section_number == id) {
            return resultObj[i];
        }
    }
}

$(function () {
    // 미리보기
    $('.show_preview').click(function () {
        var course_id = $('.course_id').val();
        location.href = "/courseInfo.do?course_id=" + course_id + "&instructor=true";
    });
    // 저장 후 다음 페이지로
    $('.save_next_page').click(function () {
        var course_id = $('.course_id').val();
        location.href = "/coverImage.do?course_id=" + course_id;
    });
    // 수업 추가하기
    $(document).on("click", ".add_class", function () {
        var section_code = $(this).parents('.section_init').data('value');
        var section_id = $(this).parents('.section_init').data('code');
        console.log(section_code + " : " + section_id);
        $('.class_form').attr('data-value', section_code);
        $('.class_form').attr('data-code', section_id);
        $('.class_form').css('display', 'block');
    });
    // 수업 편집
    $(document).on("click", ".edit_class", function () {
        findClassData($(this));
        //loadCourseResource($(this));
        $('.class_description_form').css('display', 'block');
    });
    $('.class_cancle').click(function () {
        $('.class_form').css('display', 'none');
    });
    $('.class_submit').click(function () {
        let id = $('.section_form').attr("data-id");
        let section_code = $('.class_form').attr('data-value');
        let section_id = $('.class_form').attr('data-code');
        let class_code = $('.section_' + section_code).find(".class_box").children("div").length;
        let name = $('.title_class').val();

        $.ajax({
            url: '/course/'+id+'/save/class',
            dataType: 'json',
            method: 'post',
            data: {
                name : name,
                course_section_id : section_id
            },
            success: function (result) {
                $('.class_form').css('display', 'none');
                createClassBox(name, section_id, section_code, class_code, result.id);
            }, error: function (e) {
                console.log(e);
            }
        });
    });
    $('.add_section').click(function () {
        $('.section_form').css('display', 'block');
    });
    $('.section_cancle').click(function () {
        $('.section_form').css('display', 'none');
    });
    $('.section_submit').click(function () {
        let id = $('.section_form').attr("data-id");
        let name = $('.title_section').val();
        let goal = $('.goal_section').val();
        let section_code = $('.section_box').children('div').length;
        $.ajax({
            url: "/course/"+id+"/save/section",
            dataType: "json",
            method: "post",
            data: {
                "name" : name,
                "goal" : goal
            },
            success: function (result) {
                createSectionBox(name, section_code, result.id);
            },
            error: function (e) {
                console.log(e);
            }
        });
    });
    $('.class_description_cancle').click(function () {
        $('#ex_filename').val();
        $('.class_description_form').css('display', 'none');
    });
    $('.class_description_submit').click(function () {
        // ajax 통신을 사용하여 db에 전송해야 할듯??
        $('.class_description_form').css('display', 'none');
    });
    $('.remove_class').on('click', function(){
       // 섹션 삭제 클릭 시 실행
    });
});


// 수업 박스 생성
function createClassBox(title, course_section_id, section_index, class_index, class_id) {
    console.log(class_index + " : " + title);
    let template = "<div class='class_" +class_id+ " class_box_line' data-value='" + class_index + "' data-code='" + course_section_id + "' data-class='" + class_id + "'>" +
        "<div class='class_text classes'>" +
        "<p>수업 " + class_index + " : " + title + "</p>" +
        "</div>" +
        "<div class='class_toolbox'>" +
        "<div class='edit_class'>" +
        "<i class='fas fa-pencil-alt'></i>" +
        "</div>" +
        "<div class='remove_class'>" +
        "<i class='fas fa-trash-alt'></i>" +
        "</div>" +
        "</div>" +
        "</div>";
    $(template).appendTo($('.section_' + section_index).find(".class_box"));
    $('.title_class').val('');
    $('.class_form').css('display', 'none');
}

// 섹션 박스 생성
function createSectionBox(title, section_code, section_id) {
    let template = "<div class='section_" + section_code + " section_init section_margin' data-value=" + section_code + " data-code=" + section_id + ">" +
        "<div class='section_data'>" +
        "<div class='section_info sections'>" +
        "<span class='section_number'>섹션 " + section_code + "</span>" +
        "<span class='section_title'>&nbsp;: " + title + "</span>" +
        "</div>" +
        "<div class='section_menu'>" +
        "<div class='remove_class'>" +
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
    $(template).appendTo('.section_box');
    $('.title_section').val('');
    $('.section_form').css('display', 'none');
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
    reader.onload = function(){
        var aud = new Audio(reader.result);
        aud.onloadedmetadata = function(){
            duration = aud.duration;
            console.log("/course/"+id+"/upload/video/" + class_id + "/" + duration+"?"+parameter+"="+token);
            /* ajax에서 form 데이터를 쓸려면 csrf parameterName과 token이 필요함, 요청 URL 경로에 붙이면 됌 */
            $.ajax({
                url: "/course/"+id+"/upload/video/" + class_id + "/" + duration+"?"+parameter+"="+token,
                method: "POST",
                data: data,
                processData: false,
                contentType: false,
                cache: false,
                success: function (result) {
                    $('.upload-name').val($('input[type=file]')[0].files[0].name);
                    console.log(result.time);
                    console.log(result.url);
                    let class_obj = $('.section_' + section_number).find('.class_box').children('.class_' + class_number);
                    $(class_obj).attr('data-url', result.url);
                    $(class_obj).attr('data-time', result.time);
                },
                beforeSend : function(){
                    $('.upload-name').val('업로드 중입니다...');
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
    // 클래스 데이터 저장
    $('.title_class_description').val(title);
    // 클래스 번호 저장
    $('.section_id').val(section_id);
    $('.class_number').val(class_code);
    $('.section_number').val(section_code);
    $('.class_id').val(class_id);
}