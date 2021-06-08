<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-06-08
  Time: 오후 4:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<link rel="stylesheet" href="/css/views/user/communityBoard.css">
<script src="/js/views/user/communityBoard.js"></script>

<div class="main_wrap">
    <div class="main_flex_wrap">
        <div class="community_board_flex_wrap">
            <div class="community_title_value">
                <c:out value="${notification.title}"/>
            </div>
        </div>
        <div class="community_content_wrap">
            <div class="writer_description_wrap">
                <div class="writer_profile_left">
                    <div class="writer_profile_wrap">
                        <div class="writer_profile_image_wrap">
                            <img src="${notification.writer.profile}" class="writer_profile_image_value">
                        </div>
                    </div>
                    <div class="writer_profile_content_wrap">
                        <div class="writer_profile_content_header">
                            <div class="writer_profile_nickName">
                                작성자 : <c:out value="${notification.writer.nickName}"/>
                            </div>
                            <div class="writer_profile_createAt writer_board_createAt_${noti.id}">
                                <script>
                                    timeForTodayObjectWithJSTL('${notification.create_at}', '.writer_board_createAt_${noti.id}');
                                </script>
                            </div>
                        </div>
                        <div class="writer_profile_content_footer">
                            <div class="writer_profile_content_value">
                                ${notification.content}
                            </div>
                            <div class="writer_profile_content_like_wrap">
                                <div class="writer_profile_like_wrap">이 내용이 도움이 되었나요?</div>
                                <div class="writer_profile_like">
                                    <c:choose>
                                        <c:when test="">
                                            <i class="fas fa-heart liked"></i>
                                        </c:when>
                                        <c:otherwise>
                                            <i class="far fa-heart "></i>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="writer_profile_like_count">(0)</div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="writer_profile_right">
                    <jsp:include page="/WEB-INF/aside/communityRightSideMenu.jsp"></jsp:include>
                </div>
            </div>
        </div>
    </div>
</div>