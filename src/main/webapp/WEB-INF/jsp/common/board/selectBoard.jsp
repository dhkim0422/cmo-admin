<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="<c:url value='/resources/js/kartik-bootstrap-fileinput/js/fileinput.default.js'/>"></script>

<div class="row">
    <div class="col-md-12">
        <div class="card">
            <div class="card-body">
                <h4 class="card-title">
                	<div class="row">
                		<div class="col-md-8">
		                	<c:out value="${data.bbsSj}" />
                		</div>
                		<div class="col-md-4 text-muted text-right">
                			<small>Regist By <c:out value="${data.registId}" />/<c:out value="${data.registDt}" /></small>,
               				<small>Update By <c:out value="${data.updateId}" />/<c:out value="${data.updateDt}" /></small>
                		</div>
                	</div>
                </h4>
                <hr/>
                <form:form class="mt-3" id="selectForm" commandName="data">
                	<form:input type="hidden" class="form-control" path="bbsId" />
                	<div class="row">
                    	<div class="col-md-12">
                        	<div class="scrollbox" style="min-height: 300px;">
								<c:out value="${data.bbsCn}" escapeXml="false" />
							</div>
                        </div>
                    </div>
                    <div class="row">
                    	<div class="col-md-12">
                    		<div class="form-group">
                    			<label class="form-control-label" for="fileList">첨부파일</label>
                    			<div class="table-responsive">
				                	<table id="fileTable" class="table table-hover display no-wrap custom-table"></table>
				                </div>
                    		</div>
                    	</div>
                    </div>
                    <div class="btn-list">
                    	<button type="button" class="btn waves-effect waves-light btn-outline-primary font-weight-bold" onclick="fnUpdate();"><i class="far fa-edit"></i> 수정</button>
                    	<button type="button" class="btn waves-effect waves-light btn-outline-primary font-weight-bold" onclick="common.closeSpa();"><i class="far fa-times-circle"></i> 닫기</button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>

<script type="text/javaScript" language="javascript">
var fileTable;
common.init(function(){
	fileTable = dataTableUtil.init('fileTable' ,{
		dom : dtc.getDomBasic(),
		ajax : {
            method:"post",
            url: '<c:url value="/jfile/readFiles.do"/>'+'?fileId='+'${data.bbsId}',
            data:{
            }
        },
		columns : [
			{title: "파일명",		mData: "fileNm", render: function (data, type, row, meta) { return fnGetExtIcon(row.extension)+' '+data; }},
			{title: "사이즈",		mData: "fileSize", render: function (data, type, row, meta) { return fnGetFileSize(data); }},
			{title: "다운로드",	mData: "fileSeq", render: function (data, type, row, meta) { return fnGetDownloadBtn(row, '<c:url value="/jfile/readDownloadFile.do"/>'); }}
		],
	    columnDefs : [
			{ targets: '_all', orderable: false }
		],
		order : false
	});
});

function fnUpdate(){
	common.openSpa('<c:url value="/common/${codeTy}/${codeId}/updateBoard.do"/>', {bbsId : $("#bbsId").val()});
}

</script>
