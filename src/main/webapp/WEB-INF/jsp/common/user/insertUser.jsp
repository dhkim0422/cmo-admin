<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>

<div class="row">
    <div class="col-md-12">
        <div class="card">
            <div class="card-body">
                <form:form class="mt-3" id="insertForm" commandName="data">
                	<h4 class="card-title">기본 정보</h4>
	                <h6 class="card-subtitle">추가 설명란입니다.</h6>
	                <hr />
                	<div class="row">
                		<div class="col-md-12">
                			<div class="row">
                				<div class="col-md-6">
                					<div class="form-group">
				                        <label class="form-control-label required" for="userId">사용자ID</label>
				                        <form:input type="text" class="form-control" path="userId" />
		                        	</div>
                				</div>
                				<div class="col-md-6">
                					<div class="form-group">
				                        <label class="form-control-label required" for="userNm">사용자 이름</label>
				                        <form:input type="text" class="form-control" path="userNm" />
		                        	</div>
                				</div>
                			</div>
                			<div class="row">
                				<div class="col-md-6">
                					<div class="form-group">
				                        <label class="form-control-label required" for="passwd">비밀번호</label>
				                        <form:input type="password" class="form-control" path="passwd" />
		                        	</div>
                				</div>
                				<div class="col-md-6">
                					<div class="form-group">
				                        <label class="form-control-label required" for="passwdCheck">비밀번호 확인</label>
				                        <input type="password" class="form-control" id="passwdCheck" name="passwdCheck" />
		                        	</div>
                				</div>
                			</div>
                		</div>
                	</div>
                	<br />
                	<h4 class="card-title">상세 정보</h4>
	                <h6 class="card-subtitle">추가 설명란입니다.</h6>
	                <hr />
                	<div class="row">
                		<div class="col-md-12">
                			<div class="row">
                				<div class="col-md-12">
                					<div class="form-group">
				                        <label class="form-control-label required" for="emailAddr">이메일</label>
				                        <form:input type="text" class="form-control" path="emailAddr" />
		                        	</div>
                				</div>
                			</div>
		                	<div class="row">
		                    	<div class="col-md-12">
		                        	<div class="form-group">
				                        <label class="form-control-label" for="adres">주소</label>
				                        <form:input type="text" class="form-control" path="adres" />
		                        	</div>
		                        </div>
		                    </div>
		                    <div class="row">
		                    	<div class="col-md-8">
		                        	<div class="form-group">
				                        <label class="form-control-label" for="detailAdres">상세주소</label>
				                        <form:input type="text" class="form-control" path="detailAdres" />
		                        	</div>
		                        </div>
		                    	<div class="col-md-4">
		                        	<div class="form-group">
				                        <label class="form-control-label" for="postNo">우편번호</label>
				                        <form:input type="text" class="form-control" path="postNo" />
		                        	</div>
		                        </div>
		                    </div>
		                    <div class="row">
		                    	<div class="col-md-6">
		                        	<div class="form-group">
				                        <label class="form-control-label required" for="userStatus">상태</label>
				                        <form:select class="custom-select mr-sm-2" path="userStatus">
				                            <form:option value="0" label="선택" />
											<c:forEach var="c" items="${userStatusList}">
												<form:option value="${c.code}" label="${c.codeNm}" />
											</c:forEach>
										</form:select>
		                        	</div>
		                        </div>
		                    </div>
		                    <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="form-control-label required" for="authGroupSeq">권한</label>
                                        <form:select class="custom-select mr-sm-2" path="authGroupSeq">
                                            <c:forEach var="c" items="${authList}">
                                                <form:option value="${c.authGroupSeq}" label="${c.authGroupNm}" />
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                		</div>
                	</div>
                	<br />
                	<h4 class="card-title">기관 정보</h4>
	                <h6 class="card-subtitle">추가 설명란입니다.</h6>
	                <hr />
                	<div class="row">
                		<div class="col-md-12">
                			<div class="row">
		                    	<div class="col-md-6">
		                        	<div class="form-group">
				                        <label class="form-control-label" for="instId">기관명</label>
				                        <form:select class="custom-select mr-sm-2" path="instId">
				                        	<form:option value="0" label="선택" />
											<c:forEach var="c" items="${instList}">
												<form:option value="${c.instId}" label="${c.instNm}" />
											</c:forEach>
										</form:select>
		                        	</div>
		                        </div>
		                        <div class="col-md-6">
		                        	<div class="form-group">
				                        <label class="form-control-label" for="instNmEn">영문명</label>
				                        <input type="text" class="form-control" id="instNmEn" readonly="readonly"/>
				                    </div>
				                </div>
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
	    validate.init("insertForm",
	        {
		    	rules: {
		            userId: {required: true, maxlength: 20},
		            userNm: {required: true, maxlength: 20},
		            passwd: {required: true , passwordRule : true , minlength : 8, maxlength : 16},
		            passwdCheck: {required: true , equalTo : '#passwd'},
		            emailAddr: {required: true , maxlength: 30 , email : true},
		            
		            instId: {required: true},
		            authGroupSeq: {required: true},
		            adres: {maxlength: 100},
		            detailAdres: {maxlength: 100},
		            postNo: { maxlength: 5 ,number :true },
		            userStatus: {required: true},
		            
		        },
		        submitHandler: function(form) {
		            common.confirm("저장하시겠습니까?").then(function(){
		                fnSave();
		            });
		        }
	        }
	    );
	});

	var fnSave = function(){
		var url = '<c:url value="/common/user/insertUser.json"/>';
		var options = $("#insertForm").serialize();
		common.ajax({
            url: url,
            data: options,
            success:function(data){
                toastr.success('성공적으로 저장하였습니다!');
                common.closeSpa();
                table.ajax.reload();
            }
        });
	}

</script>
