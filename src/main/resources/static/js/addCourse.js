$(function () {
    // 강의 카테고리
    $('.category').children().click(function () {
        $('.category_actived').removeClass('category_actived');
        $(this).addClass('category_actived');
        $('.courseCategory').val($(this).text());
    });
    // 강의 레벨
    $('.level').children().click(function () {
        $('.level_actived').removeClass('level_actived');
        $(this).addClass('level_actived');
        $('.courseLevel').val($(this).text());
    });
    // 스킬 키워드 태그 추가
    $('.course_tag_add').click(function () {
        var value = $('.course_tag_input').val();
        if (value != '') {
            var sample = '<div class="course_tag_hidden_form">'
                + '<div class="course_tag_text">' + value + '</div>'
                + '<div class="course_tag_remove">'
                + '<i class="far fa-trash-alt"></i>'
                + '</div>'
                + '<input type="hidden" name="tags" value="' + value + '" /></div>';
            $(sample).appendTo('.course_tag_hidden');
            $('.course_tag_input').val('');
        }
    });
    // 스킬 키워드 태그 삭제
    $(document).on('click', '.course_tag_remove', function () {
        console.log('hi');
        console.log($(this).closest('div').attr('class'));
        console.log($(this).closest('div').parent().closest('div').attr('class'));
        $(this).closest('div').parent().closest('div').remove();
    });
    // 스킬 키워드 태그 끝
    // 추천 태그 추가
    $('.course_recommend_add').click(function () {
        var value = $('.course_recommend_input').val();
        if (value != '') {
            var sample = '<div class="course_recommend_hidden_form">'
                + '<div class="course_recommend_text">' + value + '</div>'
                + '<div class="course_recommend_remove">'
                + '<i class="far fa-trash-alt"></i>'
                + '</div>'
                + '<input type="hidden" name="recommendations" value="' + value + '" /></div>';
            $(sample).appendTo('.course_recommend_hidden');
            $('.course_recommend_input').val('');
        }
    });
    // 추천 태그 삭제
    $(document).on('click', '.course_recommend_remove', function () {
        console.log('hi');
        console.log($(this).closest('div').attr('class'));
        console.log($(this).closest('div').parent().closest('div').attr('class'));
        $(this).closest('div').parent().closest('div').remove();
    });
    // 추천 태그 끝
    $('.save_next_page').click(function () {
        // 전처리

        // 최종 submit
        $('.course_form').submit();
    });
});

function setSkillTag(msg, isReleased) {
    var sample = '<div class="course_tag_hidden_form">'
        + '<div class="course_tag_text">' + msg + '</div>'
        + '<div class="course_tag_remove">'
        + '<i class="far fa-trash-alt"></i>'
        + '</div>'
        + '<input type="hidden" name="tags" value="' + msg + '" /></div>';
    console.log(isReleased);
    if(isReleased){
        sample = '<div class="course_tag_hidden_form">'
            + '<div class="course_tag_text">' + msg + '</div>'
            + '<input type="hidden" name="tags" value="' + msg + '" /></div>';
    }
    $(sample).appendTo('.course_tag_hidden');
}

function setRecommends(msg, isReleased) {
    var sample = '<div class="course_recommend_hidden_form">'
        + '<div class="course_recommend_text">' + msg + '</div>'
        + '<div class="course_recommend_remove">'
        + '<i class="far fa-trash-alt"></i>'
        + '</div>'
        + '<input type="hidden" name="recommendations" value="' + msg + '" /></div>';
    if(isReleased){
        sample = '<div class="course_recommend_hidden_form">'
            + '<div class="course_recommend_text">' + msg + '</div>'
            + '<input type="hidden" name="recommendations" value="' + msg + '" /></div>';
    }
    $(sample).appendTo('.course_recommend_hidden');
}

function setCategory(msg) {
    $('.courseCategory').val(msg);
}

function setLevel(msg) {
    $('.courseLevel').val(msg);
}