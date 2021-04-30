$(function () {
    $.ajax({
        url: "/userData",
        type : "POST",
        success: function (result) {
            $('.user_id').text(result.name +" >");
            $('.user_profile_img').attr('src', result.profile);
            $('.mini_icon').attr('src', result.profile);
        }
    })
});