<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<% String fileId = request.getParameter("fileId"); %>

<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/js/kartik-bootstrap-fileinput/css/fileinput.min.css"/>" />
<script type="text/javascript" src="<c:url value='/resources/js/kartik-bootstrap-fileinput/js/fileinput.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/kartik-bootstrap-fileinput/js/fileinput.default.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/kartik-bootstrap-fileinput/themes/fas/theme.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/kartik-bootstrap-fileinput/js/locales/kr.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/kartik-bootstrap-fileinput/js/plugins/purify.min.js'/>"></script>

<input type="hidden" id="fileId" value= "<%= fileId %>"/>
<input type="file" class="custom-file-input" id="fileList" name="files" multiple >

<script type="text/javaScript" language="javascript">
var fileinput;
var fileInputOptions = {
	theme: 'fas',
	language: "kr",
	uploadUrl: '<c:url value="/jfile/upload.do"/>',
	uploadExtraData: function(){ return {fileId : $("#fileId").val()} },
	deleteUrl: '<c:url value="/jfile/deleteFile.do"/>',
	deleteExtraData: function(){ return {fileId : $("#fileId").val()} },
	initialPreviewDownloadUrl : '<c:url value="/jfile/readDownloadFile.do"/>'
};
function fnKvReadFiles(){
	$.ajax({
        async: false,
        url: '<c:url value="/jfile/readFiles.do"/>',
        data: { fileId: $("#fileId").val() },
        success:function(data){
        	fileInputOptions.initialPreview = data.data.map(function(obj){
            	return '<c:url value="/jfile/preview.do"/>'+'?fileId='+obj.fileId+"&fileSeq="+obj.fileSeq;
        	});
        	fileInputOptions.initialPreviewConfig = data.data;
        }
    });
}

function fnKvUpload(fileId, callback){
	callback = callback || function(){};
	if($("#fileList")[0].files.length>0){
		$("#fileId").val(fileId);
		$("#fileList").fileinput('upload');
	}else{
		callback();
	}
}

$(document).ready(function(){
	fnKvReadFiles();
	$("#fileList").fileinput(fileInputOptions).on('filebeforedelete', function() {
        return new Promise(function(resolve, reject) {
        	common.confirm("삭제하시겠습니까?").then(function(){
        		resolve();
     		});
        });
    }).on('filedeleted', function() {
    	toastr.success('성공적으로 삭제하였습니다!');
    }).on('filebatchuploadsuccess', function(event, data) {
    	common.closeSpa();
        table.ajax.reload();
	});
});
</script>
