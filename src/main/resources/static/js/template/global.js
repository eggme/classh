/*
    protected CSRF for ajax Jquery
 */
(function ($) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajaxSetup({
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        }
    });
})(jQuery);

function image_upload_handler(blobInfo, success) {
    var formData = new FormData();
    formData.append("file", blobInfo.blob());
    $.ajax({
        url: "/course/upload/img",
        method: "POST",
        data: formData,
        processData: false,  // ajax는 기본적으로 Query String 으로 데이털르 보내는데 file 전송시에는 Query String 으로 보낼 필요가 없음. 이 값이 Query String의 유무를 체크하는 것
        contentType: false, // content-type의 기본값은 application/x-www-form-urlencoded; charset=UTF-8이다. 파일 전송시 Content Type은 Multipart/form-data 이므로
        // content-Type을 false해주게 되면 전송 헤더 값이 Multipart/form-data로 변경 됨
        cache: false,
        timeout: 600000,
        dataType: "json",
        success: function (result) {
            success(result.data_url);
        },
        error: function (e) {
            console.log("error by" + e);
        }
    });
}

function convertLocalDateTime(localDate, obj){
    let date = new Date(localDate);
    let year = date.getFullYear();
    let month = date.getMonth()+1;
    let day = date.getDate();
    console.log(localDate);
    $(obj).text(year+"년 "+month+"월 "+day+"일");
}

function showHtmlTagWithText(text, obj){
    $(obj).html(text);
}

function CostSeparator(cost, obj){
    $(obj).html(Number(cost).toLocaleString());
}

function CostSeparatorKR(cost, obj){
    $(obj).html(Number(cost).toLocaleString()+"원");
}

function CostSeparatorKRValue(cost){
    return Number(cost).toLocaleString();
}

function timeFormatKor(second, obj) {
    var hours = Math.floor(second / 3600);
    var minutes = Math.floor((second - (hours * 3600)) / 60);
    var result = "";
    console.log(hours + " : " + second);
    if (hours > 0) {
        result = hours + " 시간 " + minutes + " 분";
    } else {
        result = minutes + " 분";
    }
    console.log(result);
    $(obj).html(result);
}

function getPercent(current, total, obj){
    let percent = ((current / total) * 100).toFixed(2);
    console.log(percent);
    $(obj).html(percent +"%");
}

function getPercentProgress(current, total, obj){
    let percent = ((current / total) * 100).toFixed(0);
    console.log(percent);
    $(obj).val(percent);
}

function timeForToday(value) {
    const today = new Date();
    const timeValue = new Date(value);

    const betweenTime = Math.floor((today.getTime() - timeValue.getTime()) / 1000 / 60);
    console.log(betweenTime + " : " + today+ " : " +timeValue);
    if (betweenTime < 1) return '방금전';
    if (betweenTime < 60) {
        return `${betweenTime}분전`;
    }

    const betweenTimeHour = Math.floor(betweenTime / 60);
    if (betweenTimeHour < 24) {
        return `${betweenTimeHour}시간전`;
    }

    const betweenTimeDay = Math.floor(betweenTime / 60 / 24);
    if (betweenTimeDay < 365) {
        return `${betweenTimeDay}일전`;
    }

    return `${Math.floor(betweenTimeDay / 365)}년전`;
}

function timeForTodayObject(value, obj){
    $(obj).text(timeForToday(value));
}

function timeForTodayObjectWithJSTL(value, obj){
    timeForTodayObject(new Date(value), obj);
}

function removeHtmlTag(text, target){
    let replacedText = text.replace(/(<([^>]+)>)/ig, "");
    $(target).text(replacedText);
}

function setTextToObj(value, target){
    $(target).text(value);
}

function openModal(obj){
    $(obj).css("display", "block");
}