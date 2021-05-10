$(function () {

    $('.nonValidated').mouseover(function(){
        $('.guide_wrap').removeClass('hidden');
    });

    $('.nonValidated').mouseout(function(){
        $('.guide_wrap').addClass('hidden');
    });

    $('.final_submit').click(function () {
        $('.course_submit_form').css('display', 'block');
    });
    $('.course_submit').click(function () {
        $('.course_submit_form').css('display', 'none');
    });
    $('.course_success_submit').click(function () {
        $('.course_submit_success_form').css('display', 'none');
        location.href="/course/list";
    });
    $('.course_cancel').click(function () {
        $('.course_submit_form').css('display', 'none');
    });
    $('.course_submit').click(function () {
        let course_id = $('.course_submit_form').attr('data-id');

        $.ajax({
            url: "/course/confirm",
            method: "post",
            data: {"id": course_id},
            success: function (result) {
                $('.course_submit_form').css('display', 'none');
                $('.course_submit_success_form').css('display', 'block');
            }, error: function (e) {
                console.log(e);
            }
        });
    });
});

/* 메뉴 활성화용 검증 */
function validationMenu(info, desc, curriculum, cover){
    if(info){
        $('.course_info').find(".check").addClass('validated');
    }
    if(desc){
        $('.description').find(".check").addClass('validated');
    }
    if(curriculum){
        $('.curriculum').find(".check").addClass('validated');
    }
    if(cover){
        $('.thumbnail_').find(".check").addClass('validated');
    }
}

/* 강의 최종 검수 */
function validation(check){
    console.log(check);
    if(!check){
        $('.final_submit').addClass('nonValidated');
    }else{
        $('.final_submit').removeClass('nonValidated');
    }
}