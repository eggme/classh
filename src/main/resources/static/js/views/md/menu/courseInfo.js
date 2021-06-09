$(function () {
    /* 강의 미리보기 클릭 */
    $('.course_preview_box').click(function () {
        let id = $(this).attr("data-id");
        window.open("/course/" + id);
    });
    /* 뒤로가기 클릭 */
    $('.course_tool_back_button').click(function () {
        history.go(-1);
    });
    /* 강의 승인완료 클릭 */
    $('.course_tool_submitted_button').click(function () {
        changeCourseState('승인완료');
    });
    /* 강의 승인거절 클릭 */
    $('.course_tool_reject_button').click(function () {
        changeCourseState('승인거절');
    });
});

function changeCourseState(state){
    let id = $('.marin_wrap').attr('data-id');
    $.ajax({
        url : '/md/course/change',
        method : 'post',
        dataType : 'json',
        data : {
            id : id,
            courseState: state
        },success: function(result){
            console.log(result);
            location.href="/md/course/list";
        },error : function (e){

        }
    });
}

function classSetting(class_code, title, study_time, parent, status, class_id, course_id) {
    var status_template = "";
    if (status == true) {
        status_template = "<span class='course_preview right_margin' data-id='" + class_id + "' data-course='" + course_id + "'>미리보기</span>";
    } else {
        status_template = "";
    }
    var template = "<div class='section_class_content_" + class_code + " section_class_box'>" +
        "<div class='class_title'>" +
        "<i class='far fa-play-circle right_margin'></i>" +
        "<span class='class_title_content horizontal_margin'>" + title + "</span>" +
        "</div>" +
        "<div class='class_box'>" +
        status_template +
        "<i class='far fa-clock right_margin'></i> <span class='class_time right_margin''>" + timeFormat(study_time) + "</span>" +
        "</div>" +
        "</div>";
    $(template).appendTo($(parent));
}

function sectionSetting(section_code, title, class_length, total_time) {
    var template = "<div class='section_wrap'>" +
        "<div class='section_head section_" + section_code + "'>" +
        "<div class='section_title section_title_" + section_code + "'>" +
        "<div class='button_plus right_margin toggle_button'><i class='fas fa-plus'></i></div>" +
        "<div class='button_minus right_margin toggle_button button_hide'><i class='fas fa-minus'></i></div>" +
        "<span class='section_title_content horizontal_margin'>" +
        "섹션 " + section_code + ". " + title +
        "</span>" +
        "</div>" +
        "<div class='section_box'>" +
        "<div class='section_class_length_box horizontal_margin'>" +
        "<span class='section_class_length'>" + class_length + "</span>" +
        "<span>&nbsp;&nbsp;강의</span>" +
        "</div>" +
        "<i class='far fa-clock right_margin'></i>" +
        "<span class='section_time right_margin'>" + timeFormat(total_time) + "</span>" +
        "</div>" +
        "</div>" +
        "<div class='section_classes hide_class section_class_" + section_code + "'>" +
        "</div>" +
        "</div>";
    $(template).appendTo($('.curriculum_content'));
}

function timeFormat(second) {
    var hours = Math.floor(second / 3600);
    var minutes = Math.floor((second - (hours * 3600)) / 60);
    var seconds = second - (hours * 3600) - (minutes * 60);
    var result = "";
    if (hours > 0) {
        result = (hours * 60) + minutes + " : " + seconds;
    } else if (minutes < 10) {
        if (seconds < 10) {
            result = "0" + minutes + " : 0" + seconds;
        } else {
            result = "0" + minutes + " : " + seconds;
        }
    } else if (seconds < 10) {
        result = minutes + " : 0" + seconds;
    } else {
        result = minutes + " : " + seconds;
    }
    return result;
}
