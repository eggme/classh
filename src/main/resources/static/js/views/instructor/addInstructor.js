$(function () {
    /* 모달 키기 */
    $(".getting_start_button_success").click(function () {
        $('.modal_form_wrapper').css('display', 'block');
    });
    $('.button_success').click(function () {
        $('.modal_form_wrapper').css('display', 'block');
    });
    /* 모달 종료 버튼 */
    $('.modal_exit_icon_wrap').click(function () {
        $('.message_description').removeClass('hidden');
        $('.message_form_value').addClass('hidden');

        $('.modal_subText_area').removeClass("hidden");
        $('.modal_form_value').addClass("hidden");

        $('.modal_form_wrapper').css('display', 'none');
    });

    /* 모달에서 지식공유 참여를 눌렀을 때 */
    $('.modal_getting_start').click(function () {
        $('.message_description').addClass('hidden');
        $('.message_form_value').removeClass('hidden');

        $('.modal_subText_area').addClass("hidden");
        $('.modal_form_value').removeClass("hidden");
        $('.modal_submit').removeClass("hidden");
        $(this).addClass("hidden");
    });

    $('.modal_submit').click(function(){
        $('.instructor_form').submit();
    });

    $(document).on("keyup", ".modal_tel", function () {
        $(this).val($(this).val().replace(/[^0-9]/g, "").replace(/(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/, "$1-$2-$3").replace("--", "-"));
    });

});