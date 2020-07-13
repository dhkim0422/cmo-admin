<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<aside class="left-sidebar" data-sidebarbg="skin6">
    <!-- Sidebar scroll-->
    <div class="scroll-sidebar" data-sidebarbg="skin6">
        <!-- Sidebar navigation-->
        <nav class="sidebar-nav">
            <ul id="sidebarnav">
                <!-- get top menu -->
                <c:set var="topmenu" value="0" />
                <c:set var="menuNm" value="-" scope="request"/>
                <c:set var="upMenuNm" value="-" scope="request"/>
                <c:set var="menuSeq" value="0" scope="request"/>
                <c:forEach var="menu" items="${sessionScope.sessionUserInfo.menuList}">
                    <c:if test="${topmenu eq '0' and fn:indexOf(requestScope['javax.servlet.forward.servlet_path'], menu.url) >= 0 and not empty menu.url }" >
                       <c:set var="topmenu" value="${menu.histMenuSeq[0]}" />
                       <c:set var="menuNm" value="${menu.menuNm}" scope="request"/>
                       <c:set var="upMenuNm" value="${menu.upMenuNm}" scope="request"/>
                       <c:set var="menuSeq" value="${menu.menuSeq}" scope="request"/>
                    </c:if>
                </c:forEach>
                <!-- get top menu -->

                <!-- draw menu -->
                <c:forEach var="menu" items="${sessionScope.sessionUserInfo.menuList}">
                    <c:set var="contains" value="false" />

                    <c:if test="${menu.level ne '0' and menu.useAt eq 'Y'}">
	                    <c:forEach var="item" items="${menu.histMenuSeq}">
	                      <c:if test="${item eq topmenu}">
	                        <c:set var="contains" value="true" />
	                      </c:if>
	                    </c:forEach>

	                    <c:if test="${contains eq 'true'}"><!-- top menu이하 -->
	                        <c:if test="${menu.level eq '1'}">
	                            <c:if test="${menu.colOrd ne '1'}"><li class="list-divider"></li></c:if>
	                            <li class="nav-small-cap"><span class="hide-menu">${menu.menuNm}</span></li>
	                        </c:if>

	                        <c:if test="${menu.level eq '2'}">
	                        <li class="sidebar-item">
			                    <a class="sidebar-link sidebar-link" href="<c:url value='${menu.url}'/>" aria-expanded="false">
			                        <i data-feather="${menu.menuIcon}" class="feather-icon"></i><span class="hide-menu">${menu.menuNm}</span>
			                    </a>
			                </li>

			                </c:if>
	                    </c:if>

                    </c:if>
                </c:forEach>
                <!-- drwa menu -->
            </ul>
        </nav>
        <!-- End Sidebar navigation -->
    </div>
    <!-- End Sidebar scroll-->
</aside>
<script type="text/javascript">
</script>