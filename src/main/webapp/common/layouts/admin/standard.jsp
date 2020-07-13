<!DOCTYPE html>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html xml:lang="ko" lang="ko" xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="commonInclude.jsp"%>
	</head>
	<body>
		<!-- Preloader - style you can find in spinners.css -->
		<div class="preloader">
	        <div class="lds-ripple">
	            <div class="lds-pos"></div>
	            <div class="lds-pos"></div>
	        </div>
	    </div>

	    <!-- Main wrapper - style you can find in pages.scss -->
		<div id="main-wrapper" data-theme="light" data-layout="vertical" data-navbarbg="skin6" data-sidebartype="full" data-sidebar-position="fixed" data-header-position="fixed" data-boxed-layout="full">
			<tiles:insertAttribute name="top"/>
			<tiles:insertAttribute name="left"/>
			<div class="page-wrapper">
				<tiles:insertAttribute name="content"/>
				<tiles:insertAttribute name="footer"/>
			</div>
		</div>
	</body>
</html>

