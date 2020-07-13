<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../../fragment/pageNavi.jsp"  flush="false"></jsp:include>

<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="card">
	            <div class="card-body">
	                <h4 class="card-title">기관 목록</h4>
	                <h6 class="card-subtitle">추가 설명란입니다.</h6>
	                <div class="table-responsive">
	                    <table id="dataTable" class="table table-hover display no-wrap"></table>
	                </div>
	            </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javaScript" language="javascript">
var table;
common.init(function(){
    table = dataTableUtil.init('dataTable' ,{
		ajax : {
            method:"post",
            url: "<c:url value='/common/inst/getInstList.json'/>",
            data:{
            }
        },
		columns : [
			dtc.getOptionFiled(),
			{title: "기관ID",	mData: "instId"},
			{title: "기관명",		mData: "instNm", render: function (data, type, row, meta) { return dataTableUtil.selectItemRender(data, meta)}},
			{title: "영문명",		mData: "instNmEn"},
			{title: "약어명",		mData: "instAbrvNm"},
			{title: "약어영문명",	mData: "instAbrvNmEn"},
			{title: "기관코드",	mData: "instCd"},
			{title: "기관분류코드",mData: "instClCd"},
			{title: "아이콘",		mData: "instIcon"},
			{title: "링크",		mData: "instLink"},
			{title: "설명",		mData: "instDc"},
			{title: "정렬순서",	mData: "sortOrdr"},
			{title: "사용여부",	mData: "useAt"},
			{title: "등록일",		mData: "registDt"},
			{title: "등록자",		mData: "registId"},
			{title: "수정일",		mData: "updateDt"},
			{title: "수정자",		mData: "updateId"}
		],
	    buttons: {
	    	dom: dtc.getButtonDom(),
	    	buttons: [
	    		dtc.getExportButton()
    		]
	    },
	    selectUrl: "<c:url value='/common/inst/updateInst.do'/>",
	    insertUrl: "<c:url value='/common/inst/insertInst.do'/>",
    	deleteUrl: "<c:url value='/common/inst/deleteInst.json'/>"
	});
});



</script>
