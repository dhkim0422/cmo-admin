<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ctag" uri="/WEB-INF/tld/function.tld"%>

<jsp:include page="../../fragment/pageNavi.jsp"  flush="false"></jsp:include>

<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="card">
	            <div class="card-body">
	                <h4 class="card-title">페이지 목록</h4>
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
	var selectUrl  = '<ctag:btn btnId="selectUrl" menuSeq="${menuSeq}"></ctag:btn>' ;
	var insertUrl  = '<ctag:btn btnId="insertUrl" menuSeq="${menuSeq}"></ctag:btn>' ;
	var deleteUrl  = '<ctag:btn btnId="deleteUrl" menuSeq="${menuSeq}"></ctag:btn>' ;

	table = dataTableUtil.init('dataTable' ,{
		ajax : {
            method:"post",
            url: "<c:url value='/common/page/getPageList.json'/>",
            data:{
            }
        },
		columns : [
			dtc.getOptionFiled() ,
			{title: "페이지순번",	mData: "pageSeq"},
			{title: "페이지명",	mData: "pageNm", render: function (data, type, row, meta) { return dataTableUtil.selectItemRender(data, meta)}},
			{title: "영문명",		mData: "pageNmEn"},
			{title: "URL",		mData: "url"},
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
	    selectUrl: selectUrl,//"<c:url value='/common/page/updatePage.do'/>",
	    insertUrl: insertUrl,//"<c:url value='/common/page/insertPage.do'/>",
    	deleteUrl: deleteUrl,//"<c:url value='/common/page/deletePage.json'/>"
	});
});

</script>
