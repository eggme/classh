const promise_ajax = function (text) {
    return new Promise(function (resolve, reject) {
        $.ajax({
            url: '/course/search',
            method: 'post',
            async: false,
            dataType: "json",
            data: {'search_value': text},
            success: function (result) {
                console.log("resolve");
                resolve(result);
            },
            error: function (e) {
                reject(e);
            },
            complete: function () {
                $('.search_loading').css('display', 'none');
                $('.search_result').css('display', 'block');
            }
        });
    });
};
var timeout = null;
$(function () {
    $('.search_input').on('keyup', function () {
        clearTimeout(timeout);
        let wrap_box = $('.search_area');
        let loading = $('.search_loading');
        let result = $('.search_result');
        let wrap = $('.search_iuput_border');
        $(wrap_box).show();
        $(loading).show();
        $(result).hide();
        $(wrap).addClass("search_input_radius");
        timeout = setTimeout(function () {
            let text = $('.search_input').val();
            if (text.length > 0) {
                $(loading).hide();
                $(result).show();
                searchDatabase(text);
            } else {
                $('.search_area').hide();
                $('.search_loading').hide();
                $('.search_result').hide();
                $('.search_iuput_border').removeClass('search_input_radius');
            }
        }, 500);
    });
    $('html').click(function (e) {
        if (!$(e.target).hasClass('search_iuput_border')) {
            if (!$(e.target).hasClass('search_input')) {
                if (!$(e.target).hasClass('search_area')) {
                    if (!$(e.target).hasClass('course_search_wrap')) {
                        $('.search_area').hide();
                        $('.search_result').hide();
                        $('.search_iuput_border').removeClass('search_input_radius');
                    }
                }
            }
        }
    });

    $('.search_input').click(function () {
        let text = $('.search_input').val();
        let size = $('.course_search_content_wrap').children().length;
        if (text.length > 0) {
            if (size > 0) {
                console.log("show");
                $('.search_area').show();
                $('.search_result').show();
                $('.search_iuput_border').addClass('search_input_radius');
            }
        }
    });

    // $('.search_iuput_border').hover(function(){
    //     let text = $('.search_input').val();
    //     let size = $('.course_search_content_wrap').children().length;
    //     console.log(size);
    //     if(text.length > 0){
    //         if(size > 0){
    //             $('.search_area').show();
    //             $('.search_result').show();
    //         }
    //     }
    // }, function(){
    //     $('.search_area').hide();
    //     $('.search_result').hide();
    //     $('.search_iuput_border').removeClass('search_input_radius');
    // });
});

function setSearchDataForm(course) {
    var template = "<div class='course_parent'>" +
        "<a href='/course/" + course.id + "' class='course_click'>" +
        "<div class='course_search_content'>" +
        "<div class='search_image_wrap'>" +
        "<img class='search_img' src='" + course.courseImg + "'>" +
        "</div>" +
        "<div class='search_result_content'>" +
        "<div class='search_content_title'>" +
        "<span class='search_title'>" + course.name + "</span>" +
        "</div>" +
        "<div class='search_content_name'>" +
        "<span class='search_name'>" + course.nickName + "</span>" +
        "</div>" +
        "</div>" +
        "</div>" +
        "</a>" +
        "</div>";
    $('.course_search_content_wrap').append(template);
}


function searchDatabase(text) {
    promise_ajax(text).then(function (result) {
        let size = result.length;
        $('.search_area').css('height', (size * 40) + (size * 16) + 60);
        $('.course_search_content_wrap').html('');
        if (size > 0) {
            for (var i = 0; i < size; i++) {
                setSearchDataForm(result[i]);
            }
        } else {
            $('.search_area').hide();
            $('.search_loading').hide();
            $('.search_result').hide();
            $('.search_input_border').removeClass('search_input_radius');
        }
    }, function (err) {
        $('.search_area').hide();
        $('.search_loading').hide();
        $('.search_result').hide();
        $('.search_input_border').removeClass('search_input_radius');
    });
}