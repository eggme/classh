<%--
  Created by IntelliJ IDEA.
  User: kyyet
  Date: 2021-06-04
  Time: 오후 5:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" href="/css/views/user/notification.css">
<script src="/js/views/user/notification.js"></script>

<section class="main_section">
    <div class="main_flex_wrap">
        <div class="notification_title_wrap">
            <div class="notification_title">
                읽지 않은 알림<span class="notification_title_count_value">0</span>개
            </div>
            <div class="notification_toolbox">
                <div class="notification_all_read_icon"><i class="fas fa-check-circle"></i></div>
                <div class="notification_all_read_text">모두 읽음 표시</div>
            </div>
        </div>

        <div clas="notification_body_wrap">
            <c:choose>
                <c:when test="${fn:length(list) gt 0}">
                    <c:set var="nonReadCnt" value="0" />
                    <c:forEach var="noti" items="${list}" varStatus="index">
                        <div class="notification_template notification_${noti.id}" data-id="${noti.id}" data-type="${noti.notificationType}">
                            <c:choose>
                                <c:when test="${noti.isRead()}">
                                    <div class="notification_status_wrap">
                                        <div class="notification_circle_icon read_icon"></div>
                                    </div>
                                    <div class="notification_content_wrap read_content">
                                        <c:out value="${noti.notificationType.value}"/>
                                        <c:out value="${noti.title}" />
                                    </div>
                                    <div class="notification_timestamp_wrap timestamp_${noti.id}">
                                        <script>
                                            timeForTodayObject(new Date('${noti.create_at}'), '.timestamp_${noti.id}');
                                        </script>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="nonReadCnt" value="${nonReadCnt + 1}" />
                                    <div class="notification_status_wrap">
                                        <div class="notification_circle_icon"></div>
                                    </div>
                                    <div class="notification_content_wrap">
                                        <c:out value="${noti.notificationType.value}"/>
                                        <c:out value="${noti.title}" />
                                    </div>
                                    <div class="notification_timestamp_wrap timestamp_${noti.id}">
                                        <script>
                                            timeForTodayObject(new Date('${noti.create_at}'), '.timestamp_${noti.id}');
                                        </script>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:forEach>
                    <script>
                        setCount('${nonReadCnt}', '.notification_title_count_value')
                    </script>
                </c:when>
                <c:otherwise>
                    <div class="not_have_notification">받은 알림이 존재하지 않습니다.</div>
                </c:otherwise>
            </c:choose>

        </div>
    </div>
</section>