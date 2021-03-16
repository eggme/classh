function selectMenu(data){
    $('.aside_menu a').removeClass('select_menu');
    $('.aside_menu a[data-menu="'+data+'"]').addClass('select_menu');
}
var map = {};
map['dashboard'] = "학습 대시보드";
map['profile'] = "프로필 설정";
map['myLecture'] = "내 강의";
map['instructor'] = "대시보드";
map['addLecture'] = "강의 만들기";
map['courseList'] = "강의 관리";

function mappingMenu(data){
    return map[data];
}