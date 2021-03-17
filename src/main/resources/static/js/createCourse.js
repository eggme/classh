var currentStep = 1;

$(document).ready(function () {
    $('.previous_button').css('display', 'none');

    $(document).on("click", ".submit_button", function (e)    {
        let query = $('.course_name').val();
        if(query != ""){
            console.log("submit");
            $('.createCourseForm').submit();
        }
    });

    /* 다음 단계 클릭 */
    $('.next_button').click(function () {
        currentStep++;
        console.log($(this).attr("class"));
        console.log(currentStep);
        checkButtonStatus();
        setLayout();
        setIndicator();
    });
    /* 마지막 단계에서 이전 단계 클릭 */
    $('.hidden_previous_content').click(function () {
        console.log(currentStep + " : ,,");
        currentStep--;
        removeSubmitButton();
        setLayout();
        setIndicator();
    });
    /* 이전 단계 클릭 */
    $('.previous_button').click(function () {
        currentStep--;
        checkButtonStatus();
        setLayout();
        setIndicator();
    });
    /* 모두 이해했고 바로 강의 만들기 클릭 */
    $('.direct_creation').click(function () {
        currentStep = 5;
        checkButtonStatus();
        setLayout();
        setIndicator();
    });
});


function removeSubmitButton() {
    console.log(currentStep);
    var obj = $('.submit_button');
    obj.removeClass("submit_button");
    obj.addClass("next_button");
    obj.addClass("inline");
    obj.text("다음");
    $('.previous_button').removeClass('nonDisplay');
    $('.hidden_previous').addClass('nonDisplay');
    $('.previous_button').attr('data-step', getPreviousStep());
    $('.next_button').attr('data-step', getNextStep());
}

function checkButtonStatus() {
    if (currentStep == 5) {
        let obj = $('.next_button');
        $(obj).removeClass('next_button');
        $(obj).addClass('submit_button');
        $(obj).removeClass('inline');
        $(obj).text('강의 만들기');
        console.log($(obj).attr("class"));
        $('.previous_button').addClass('nonDisplay');
        $('.hidden_previous').removeClass('nonDisplay');
        $('.previous_button').attr('data-step', getPreviousStep());
        $('.next_button').attr('data-step', getNextStep());
        console.log($('.previous_button').attr("data-step") + " : " + $('.next_button').attr("data-step"));
        console.log(currentStep + " ->");
        return;
    }
    if (currentStep > 1) {
        $('.previous_button').css('display', 'inline-block');
    } else if (currentStep == 1) {
        $('.previous_button').css('display', 'none');
    }
    $('.previous_button').attr('data-step', getPreviousStep());
    $('.next_button').attr('data-step', getNextStep());
}

function setIndicator() {
    let indicators = $('.indicator_layout').children(".indicator");
    $('.indicator').removeClass('active');
    $(indicators[(currentStep - 1)]).addClass("active");
}

function setLayout() {
    let layouts = $('.column_wrap');
    $('.column_wrap').removeClass('active_layout');
    $(layouts[(currentStep - 1)]).addClass("active_layout");
}

function getPreviousStep() {
    return (currentStep - 1);
}

function getNextStep() {
    return (currentStep + 1);
}