<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>

<div class="row">
    <div class="col-md-12">
        <div class="card">
            <div class="card-body">
                <h4 class="card-title">페이지 정보</h4>
                <h6 class="card-subtitle">추가 설명란입니다.</h6>
                <form:form class="mt-3" id="updateForm" commandName="data">
                	<form:input type="hidden" class="form-control" path="pageSeq" />
                	<div class="row">
                    	<div class="col-md-12">
                        	<div class="form-group">
		                        <label class="form-control-label required" for="pageNm">페이지명</label>
		                        <form:input type="text" class="form-control" path="pageNm" />
                        	</div>
                        </div>
                    </div>
                	<div class="row">
                    	<div class="col-md-12">
                        	<div class="form-group">
		                        <label class="form-control-label required" for="url">페이지URL</label>
		                        <form:input type="text" class="form-control" path="url" />
                        	</div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label class="form-control-label  for="url">Button ID</label>
                                <form:input type="text" class="form-control" path="btnId" maxlength="20" />
                            </div>
                        </div>
                    </div>
                    <div class="btn-list">
                    	<button type="submit" class="btn waves-effect waves-light btn-outline-primary font-weight-bold" ><i class="far fa-save"></i> 저장</button>
                    	<button type="button" class="btn waves-effect waves-light btn-outline-primary font-weight-bold" onclick="common.closeSpa();"><i class="far fa-times-circle"></i> 닫기</button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>

<script type="text/javaScript" language="javascript">
common.init(function(){
    validate.init("updateForm",
	{
		rules: {
			pageNm: {required: true, maxlength: 30},
			url: {required: true, maxlength: 255}
		},
	 	submitHandler: function(form) {
	 		common.confirm("저장하시겠습니까?").then(function(){
	 			fnSave();
	 		});
	    }
	});
});
	function fnSave(){
		var url = '<c:url value="/common/page/updatePage.json"/>';
		var param = $("#updateForm").serialize();
		common.ajax({
            url: url,
            data: param,
            success:function(data){
                toastr.success('성공적으로 저장하였습니다!');
                common.closeSpa();
                table.ajax.reload();
            }
        });
	}

</script>
