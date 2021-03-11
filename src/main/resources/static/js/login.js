$(function () {
    $('.login_button').click(function () {
        login();
    });
});

function login() {
    var username = $('.email').val();
    var password = $('.password').val();
    $.ajax({
        url: "/login",
        type: "POST",
        dataType : "json",
        data : {
            username : username,
            password : password
        },
        success: function (res) {
            if(res.code == 200){
                window.location = res.item.url;
            }else{
                alert(res.message);
            }
        }
    });
}