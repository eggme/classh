$(function(){
    $('.open_close_toggle').click(function(){
        var arr = $('.section_head');

        if($(this).hasClass("closed")){
            $(this).text('모두 펼치기');
            $(this).removeClass('closed');
        }else{
            $(this).text('모두 닫기');
            $(this).addClass('closed');
        }
        console.log(arr.length);
        for(var i =0 ; i<arr.length; i++){
            $(arr[i]).trigger('click');
        }
    });

    $(document).on('click', '.section_head ', function () {
        console.log($(this).attr('class'));
        console.log($(this).next().css('max-height'));
        var class_box = this.nextElementSibling;
        if (class_box.style.maxHeight) {
            console.log("minus");
            /* minus */
            $(this).children().children("div:eq(0)").removeClass("button_hide");
            $(this).children().children("div:eq(1)").addClass("button_hide");
            class_box.style.maxHeight = null;
            $(class_box).addClass('hide_class');
        } else {
            /* plus */
            $(this).children().children("div:eq(0)").addClass("button_hide");
            $(this).children().children("div:eq(1)").removeClass("button_hide");
            console.log("plus");
            class_box.style.maxHeight = class_box.scrollHeight + "px";
        }
    });
});

function setHours(time, target){
    console.log(time);
    var hours = Math.floor(time / 3600);
    $(target).text(hours);
}

function setMinutes(time, target){
    var hours = Math.floor(time / 3600);
    var minutes = Math.floor((time - (hours * 3600)) / 60);
    $(target).text(minutes);
}

function timeFormat(second) {
    var hours = Math.floor(second / 3600);
    var minutes = Math.floor((second - (hours * 3600)) / 60);
    var seconds = second - (hours * 3600) - (minutes * 60);
    var result = "";
    if (hours > 0) {
        result = (hours*60) + minutes + " : " + seconds;
    } else if (minutes < 10) {
        if(seconds < 10){
            result = "0" + minutes + " : 0" + seconds;
        }else{
            result = "0" + minutes + " : " + seconds;
        }
    } else if(seconds < 10){
        result = minutes + " : 0" + seconds;
    }else{
        result = minutes + " : " + seconds;
    }
    return result;
}

function classSetting(class_code, title, study_time, parent, status, class_id, course_id) {
    var status_template = "";
    if(status == true){
        status_template = "<span class='course_preview right_margin' data-id='"+class_id+"' data-course='"+course_id+"'>미리보기</span>";
    }else {
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
        "<div class='section_classes section_class_" + section_code + "'>" +
        "</div>" +
        "</div>";
    $(template).appendTo($('.curriculum_content'));
}