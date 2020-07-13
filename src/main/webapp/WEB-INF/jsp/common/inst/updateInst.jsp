<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>

<div class="row">
    <div class="col-md-12">
        <div class="card">
            <div class="card-body">
                <h4 class="card-title">기관 정보</h4>
                <h6 class="card-subtitle">추가 설명란입니다.</h6>
                <form:form class="mt-3" id="updateForm" commandName="data">
                	<form:input type="hidden" class="form-control" path="instId" />
                	<div class="row">
                    	<div class="col-md-6">
                        	<div class="form-group">
		                        <label class="form-control-label required" for="instNm">기관명</label>
		                        <form:input type="text" class="form-control" path="instNm" />
                        	</div>
                        </div>
                    	<div class="col-md-6">
                        	<div class="form-group">
		                        <label class="form-control-label" for="instNmEn">영문명</label>
		                        <form:input type="text" class="form-control" path="instNmEn" />
                        	</div>
                        </div>
                    </div>
                	<div class="row">
                    	<div class="col-md-6">
                        	<div class="form-group">
		                        <label class="form-control-label" for="instAbrvNm">약어명</label>
		                        <form:input type="text" class="form-control" path="instAbrvNm" />
                        	</div>
                        </div>
                    	<div class="col-md-6">
                        	<div class="form-group">
		                        <label class="form-control-label" for="instAbrvNmEn">임상ID</label>
		                        <form:input type="text" class="form-control" path="instAbrvNmEn" />
                        	</div>
                        </div>
                    </div>
                	<div class="row">
                    	<div class="col-md-6">
                        	<div class="form-group">
		                        <label class="form-control-label" for="instCd">조사기관코드</label>
		                        <form:input type="text" class="form-control" path="instCd" />
                        	</div>
                        </div>
                    	<div class="col-md-6">
                        	<div class="form-group">
		                        <label class="form-control-label required" for="instClCd">기관분류</label>
		                        <form:select class="custom-select mr-sm-2" path="instClCd">
									<c:forEach var="c" items="${INST_CL_CD}">
										<form:option value="${c.code}" label="${c.codeNm}" />
									</c:forEach>
								</form:select>
                        	</div>
                        </div>
                    </div>
                	<div class="row">
                    	<div class="col-md-12">
                        	<div class="form-group">
		                        <label class="form-control-label" for="instLink">링크</label>
		                        <form:input type="text" class="form-control" path="instLink" />
                        	</div>
                        </div>
                    </div>
                	<div class="row">
                    	<div class="col-md-6">
                        	<div class="form-group">
		                        <label class="form-control-label" for="instDc">기관설명</label>
		                        <form:input type="text" class="form-control" path="instDc" />
                        	</div>
                        </div>
                    	<div class="col-md-6">
                        	<div class="form-group">
		                        <label class="form-control-label required" for="sortOrdr">정렬순서</label>
		                        <form:input type="text" class="form-control" path="sortOrdr" />
                        	</div>
                        </div>
                    </div>
                	<div class="row">
                    	<div class="col-md-6">
                        	<div class="form-group">
		                        <label class="form-control-label required" for="useAt">사용여부</label>
		                        <form:select class="custom-select mr-sm-2" path="useAt">
									<c:forEach var="c" items="${USE_AT}">
										<form:option value="${c.code}" label="${c.codeNm}" />
									</c:forEach>
								</form:select>
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
			instNm: {required: true, maxlength: 30},
			instNmEn: {maxlength: 30},
			instAbrvNm: {maxlength: 30},
			instAbrvNmEn: {maxlength: 30},
			instCd: {maxlength: 30},
			instClCd: {required: true, maxlength: 30},
			instIcon: {maxlength: 255},
			instLink: {url: true, maxlength: 255},
			instDc: {maxlength: 255},
			sortOrdr: {required: true, number: true},
			useAt: {required: true, maxlength: 1}
		},
	 	submitHandler: function(form) {
	 		common.confirm("저장하시겠습니까?").then(function(){
	 			fnSave();
	 		});
	    }
	});
});
	function fnSave(){
		var url = '<c:url value="/common/inst/updateInst.json"/>';
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
