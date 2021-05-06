$(function () {
    $('.submit').click(function () {
        $('.course_submit_form').css('display', 'block');
    });
    $('.course_submit').click(function () {
        $('.course_submit_form').css('display', 'none');
    });
    $('.course_success_submit').click(function () {
        $('.course_submit_success_form').css('display', 'none');
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