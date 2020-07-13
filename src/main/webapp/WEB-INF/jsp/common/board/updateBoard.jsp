<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="<c:url value='/resources/js/tinymce/5.0/tinymce.min.js'/>"></script>

<div class="row">
    <div class="col-md-12">
        <div class="card">
            <div class="card-body">
                <h4 class="card-title">공지사항 정보</h4>
                <h6 class="card-subtitle">추가 설명란입니다.</h6>
                <form:form class="mt-3" id="updateForm" commandName="data">
                	<form:input type="hidden" class="form-control" path="bbsId" />
                	<div class="row">
                    	<div class="col-md-12">
                        	<div class="form-group">
		                        <label class="form-control-label required" for="bbsSj">제목</label>
		                        <form:input type="text" class="form-control" path="bbsSj" />
                        	</div>
                        </div>
                    </div>
                	<div class="row">
                    	<div class="col-md-12">
                        	<div class="form-group">
		                        <label class="form-control-label required" for="bbsCn">내용</label>
		                        <form:textarea path="bbsCn"></form:textarea>
                        	</div>
                        </div>
                    </div>
                    <div class="row">
                    	<div class="col-md-6">
                        	<div class="form-group">
		                        <label class="form-control-label" for="frtAt">우선게시여부</label>
		                        <form:select class="custom-select mr-sm-2" path="frtAt">
									<c:forEach var="c" items="${USE_AT}">
										<form:option value="${c.code}" label="${c.codeNm}" />
									</c:forEach>
								</form:select>
                        	</div>
                        </div>
                    	<div class="col-md-6">
                        	<div class="form-group">
		                        <label class="form-control-label" for="othbcAt">공개여부</label>
		                        <form:select class="custom-select mr-sm-2" path="othbcAt">
									<c:forEach var="c" items="${USE_AT}">
										<form:option value="${c.code}" label="${c.codeNm}" />
									</c:forEach>
								</form:select>
                        	</div>
                        </div>
                    </div>
                    <div class="row">
                    	<div class="col-md-12">
                    		<div class="form-group">
                    			<label class="form-control-label" for="fileList">첨부파일</label>
                    			<jsp:include page="/WEB-INF/jsp/common/board/uploadView.jsp">
						            <jsp:param name="fileId" value="${data.bbsId}"></jsp:param>
							    </jsp:include>
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

	function fnSave(){
		var url = '<c:url value="/common/${codeTy}/${codeId}/updateBoard.json"/>';
		var param = $("#updateForm").serialize();
		common.ajax({
            url: url,
            data: param,
            success:function(data){
                toastr.success('성공적으로 저장하였습니다!');
                //파일 업로드
                fnKvUpload(data.data.bbsId, function(){
                	common.closeSpa();
                    table.ajax.reload();
            	});
            }
        });
	}



	common.init(function(){
		validate.init("updateForm",
			    {
			        rules: {
			            bbsSj: {required: true, maxlength: 160}
			        },
			        submitHandler: function(form) {
			            common.confirm("저장하시겠습니까?").then(function(){
			                fnSave();
			            });
			        }
			    });

		tinymce.init({
	        selector : 'textarea#bbsCn',
	        language : 'ko_KR',
	        height: 400
	    });
	});
</script>
