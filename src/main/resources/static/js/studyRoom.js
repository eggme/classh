var player = null;
$(function(){
   $('.dashboard').click(function(){
       let redirectUrl = $(this).attr('data-url');
       location.href="/course/"+redirectUrl;
   });
   $('.close_button').click(function(){
       $('.curriculum_wrap').addClass('closed');
    });
   $('.course_data_list').click(function(){
       let target = $('.curriculum_wrap');
       if($(target).hasClass('closed')){
           $(target).addClass('opened');
           $(target).removeClass('closed');
       }else{
           $(target).removeClass('opened');
           $(target).addClass('closed');
       }
   });
});

function createClassContent(name, class_id, sectionCode, classCode, study_time){
    let tag = "";
    let temp = "played";
    let parentObj = $('.class_wrap[data-sid=' + sectionCode + ']');
    let template = "<div class='class_box "+tag+"' data-cid=" + class_id + " data-sid=" + sectionCode + " data-ccode=" + classCode + ">" +
        "<div class='class_icon "+temp+"'><span class='play_icon'><i class='fas fa-check-circle'></i></span></div>" +
        "<div class='class_content'>" +
        "<div class='class_title'>" + name + "</div>" +
        "<div class='class_time'>" +
        "<span class='time_icon'><i class='fas fa-play-circle'></i></span>" +
        "<span>" + timeFormat(study_time) + "분</span></div></div></div>";
    $(template).appendTo(parentObj);
}

function createSectionContent(name, course_id, section_id, sectionCode) {
    let parentObj = $('.course_content');
    let template = "<div class='section_box' data-course=" + course_id + " data-sid=" + section_id + " data-scode=" + sectionCode + ">" +
        "<div class='section_title'>" + name + "</div>" +
        "<div class='class_wrap' data-sid=" + sectionCode + "></div></div>";
    $(template).appendTo(parentObj);
}

function timeFormatWrapper(second, obj){
    console.log(second + " : " + timeFormat(second));
    $(obj).text(timeFormat(second));
}

function timeFormat(second) {
    var minutes = Math.floor((second / 60));
    return minutes;
}

function loadVideoUrl(url){
    $('#myPlayer').attr('data-href', url);
}
function loadVideoJS() {
    let url = $('#myPlayer').attr('data-href');
    console.log(url);
    // let start_time = $('.course_id').attr('data-start');
    player = videojs("myPlayer", {
        sources: [{
            src: url,
            type: 'video/mp4'
        }],
        controls: true,
        playsinline: true,
        autoplay: true,
        preload: 'metadata'
    });
    // player.currentTime(start_time);
}