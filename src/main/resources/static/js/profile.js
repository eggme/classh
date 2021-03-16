$(function () {
    $('.profile_img').click(function (e) {
        e.preventDefault();
        $('.uploadImage').click();
    });
});

function submitForm(form_name) {
    $(form_name).submit();
}

function changeImage(obj) {
    $('.image_form').submit();
}

