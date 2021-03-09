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

    $(document).on("click", ".add_class", function () {
        var section_code = $(this).parents('.section_init').data('value');
        var section_id = $(this).parents('.section_init').data('code');
        console.log(section_code + " : " + section_id);
        $('.class_form').attr('data-value', section_code);
        $('.class_form').attr('data-code', section_id);
        $('.class_form').css('display', 'block');
    });
    $(document).on("click", ".edit_class", function () {
        findClassData($(this));
        //loadCourseResource($(this));
        $('.class_description_form').css('display', 'block');
    });
    $('.class_cancle').click(function () {
        $('.class_form').css('display', 'none');
    });
    $('.class_submit').click(function () {
        let section_code = $('.class_form').attr('data-value');
        let section_id = $('.class_form').attr('data-code');
        let class_code = $('.section_' + section_code).find(".class_box").children("div").length + 1;
        let course_id = $('.course_id').val();
        let title = $('.title_class').val();
        let obj = new Object();
        obj.section_code = section_code;
        obj.title = title;
        obj.course_id = course_id;
        obj.class_code = class_code;
        obj.section_id = section_id;
        $.ajax({
            url: '/saveCourseClass.do',
            dataType: 'json',
            method: 'post',
            data: {'result': JSON.stringify(obj)},
            success: function (result) {
                $('.class_form').css('display', 'none');
                createClassBox(title, section_id, section_code, course_id, class_code, result.course_class_id);
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
        let title = $('.title_section').val();
        let section_code = $('.section_box').children('div').length;
        let obj = new Object();
        obj.section_code = section_code;
        obj.title = title;
        obj.course_id = $('.course_id').val();
        $.ajax({
            url: "/saveCourseSection.do",
            dataType: "json",
            method: "post",
            data: {"result": JSON.stringify(obj)},
            success: function (result) {
                createSectionBox(title, section_code, result.section_id);
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
});

// 클래스 수정 클릭시 해당 클래스의 리소스를 가져오는 함수
function loadCourseResource(elements) {
    let obj = new Object();
    obj.section_number = $('.section_number').val();
    obj.class_code = $('.class_number').val();
    obj.class_id = $('.class_id').val();
    obj.course_id = $('.course_id').val();
    obj.section_id = $('.section_id').val();
    $.ajax({
        url: '/loadCourseResource.do',
        method: "post",
        dataType: "json",
        data: {"result": JSON.stringify(obj)},
        success: function (result) {
            let section_number = $('.section_number').val();
            let class_number = $('.class_number').val();
            let class_obj = $('.section_' + section_number).find('.class_box').children('.class_' + class_number);
            $(class_obj).attr('data-url', result.path);
            $(class_obj).attr('data-time', result.time);
            findClassData(elements);
        },
        error: function (e) {
            console.log(e);
        }
    });
}

// 수업 박스 생성
function createClassBox(title, course_section_id, section_code, course_id, class_code, class_id) {
    let template = "<div class='class_" + class_code + " class_box_line' data-value=" + class_code + " data-code=" + course_section_id + " data-class=" + class_id + ">" +
        "<div class='class_text classes'>" +
        "<p>수업 " + class_code + " : " + title + "</p>" +
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
    $(template).appendTo($('.section_' + section_code).find(".class_box"));
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

// 수업 박스에서 사용되는 함수로 부모를 찾아서 current_section_number을 얻음
function findParents(obj) {
    if (obj.parents('.section_init').attr('class').substring(8, 9) != undefined) {
        current_section_number = obj.parents('.section_init').attr('class').substring(8, 9) * 1;
    }
    let section_code = $(obj).parents('.section_init').data('value');
    console.log("parent class -> " + current_section_number + " : " + section_code);
    let section_ = obj.parents('.section_init');
    current_parent = section_.find('.class_box');
    let size = current_parent.children().length;
    current_class_number = size + 1;
}

// 수업 영상 업로드
function fileUpload(file) {
    console.log($(this));
    console.log($(this).parent());
    let section_number = $('.section_number').val();
    let class_number = $('.class_number').val();
    let class_id = $('.class_id').val();
    let course_id = $('.course_id').val();
    let section_id = $('.section_id').val();
    let data = new FormData();
    console.log("/upload/fileUpload.do?class_code=" + class_number + "&class_id=" + class_id + "&course_id=" + course_id);
    data.append('file', $('input[type=file]')[0].files[0]);
    $.ajax({
        url: "/upload/fileUpload.do?class_code=" + class_number + "&class_id=" + class_id + "&course_id=" + course_id + "&section_id=" + section_id,
        method: "POST",
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
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
}

// ?
function addClassFileData(section_number, class_number, url, time) {
    $('.upload-name').val($('input[type=file]')[0].files[0].name);
    console.log(time);
    console.log(url);
    var section_obj = resultObj[section_number];
    console.log(resultObj[section_number]);
    var class_obj = section_obj.section_classes;
    for (var i = 0; i < class_obj.length; i++) {
        if (class_obj[i].class_number == class_number) {
            class_obj[i].url = url;
            class_obj[i].time = time;
        }
    }
    console.log(section_obj);
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

    console.log("섹션 : " + class_code + ", 수업 : " + section_code);
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

// page onLoad 함수
function loadCourseData() {
    var obj = new Object();
    obj.course_id = $('.course_id').val();
    var result = JSON.stringify(obj);
    $.ajax({
        url: "/loadCourseData.do",
        method: "post",
        dataType: "json",
        data: {"course_id": result},
        success: function (result) {
            console.log(result);
            CourseDataInitialized(result);
        },
        error: function (e) {
            console.log(e);
        }
    });
}

// 실제 데이터르 가지고 View 에 뿌려주는 함수
function CourseDataInitialized(result) {
    var section_size = result.length;
    for (var i = 0; i < section_size; i++) {
        var section_obj = result[i];
        var section_title = section_obj.title;
        var course_section_id = result[i].course_section_id;
        var section_code = result[i].section_code;
        var course_id = result[i].course_id;
        createSectionBox(section_title.replace(/"/gi, ""), section_obj.section_code, section_obj.course_section_id);
        if (section_obj.courseClassArrayList != undefined) {
            var class_size = section_obj.courseClassArrayList.length;
            for (var j = 0; j < class_size; j++) {
                var class_obj = section_obj.courseClassArrayList[j];
                var class_title = class_obj.title;
                var class_code = class_obj.class_code;
                var course_class_id = class_obj.course_class_id;
                createClassBox(class_title.replace(/"/gi, ""), course_section_id, section_code, course_id, class_code, course_class_id);
            }
        }
    }
    var result12 = JSON.stringify(resultObj);
    console.log("resultObj ->>>>>>" + result12);
}