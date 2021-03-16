<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-03-12
  Time: 오후 3:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="/css/addCourseSideMenu.css" >
<aside class="main_aside">
    <h2 class="item_title">강의 제작</h2>
    <ul class="items">
        <li class="active"><a href="/addCourse.do?course_id="><i class="fas fa-check-circle"></i>&nbsp;&nbsp;강의정보</a>
        </li>
        <li><a href="/description.do?course_id="><i class="fas fa-check-circle"></i>&nbsp;&nbsp;상세소개</a></li>
        <li><a href="/curriculum.do?course_id="><i class="fas fa-check-circle"></i>&nbsp;&nbsp;커리큘럼</a></li>
        <li><a href="/coverImage.do?course_id="><i class="fas fa-check-circle"></i>&nbsp;&nbsp;커버 이미지</a></li>
    </ul>
    <br/>
    <br/>
    <h2 class="item_title">설정</h2>
    <ul class="items">
        <li><i class="fas fa-check-circle"></i>&nbsp;&nbsp;강의설정</li>
        <li><i class="fas fa-check-circle"></i>&nbsp;&nbsp;지식공유자 설정</li>
    </ul>
    <br/>
    <br/>
    <br/>
    <div>
        <span class="label label-danger submit">제출하기</span>
    </div>
</aside>