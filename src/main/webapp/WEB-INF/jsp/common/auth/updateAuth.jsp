<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>

<%-- JS --%>
<script src="<c:url value='/resources/js/zTree_v3/js/jquery.ztree.all.js'/>"></script>
<script src="<c:url value='/resources/js/jquery.cookie.js'/>"></script>
<%-- CSS --%>
<link rel="stylesheet" href="<c:url value='/resources/js/zTree_v3/css/zTreeStyle/zTreeStyle.css'/>" type="text/css">

<div class="row">
    <div class="col-md-12">
        <div class="card">
            <div class="card-body">
                <form:form class="mt-3" id="updateForm" commandName="data">
                	<form:input type="hidden" class="form-control" path="authGroupSeq" />
                	<div class="row">
                    	<div class="col-md-6">
                    		<h4 class="card-title">권한그룹 정보</h4>
			                <h6 class="card-subtitle">추가 설명란입니다.</h6>
                    		<div class="row">
		                    	<div class="col-md-12">
		                        	<div class="form-group">
				                        <label class="form-control-label required" for="authGroupNm">권한그룹명</label>
				                        <form:input type="text" class="form-control" path="authGroupNm" />
		                        	</div>
		                        </div>
		                    	<div class="col-md-12">
		                        	<div class="form-group">
				                        <label class="form-control-label required" for="authGroupDc">권한그룹설명</label>
				                        <form:input type="text" class="form-control" path="authGroupDc" />
		                        	</div>
		                        </div>
		                    </div>
		                	<div class="row">
		                    	<div class="col-md-12">
		                        	<div class="form-group">
				                        <label class="form-control-label required" for="authClassCd">권한그룹</label>
				                        <form:input type="text" class="form-control" path="authClassCd" />
		                        	</div>
		                        </div>
		                    	<div class="col-md-12">
		                        	<div class="form-group">
				                        <label class="form-control-label required" for="useAt">사용여부</label>
				                        <form:input type="text" class="form-control" path="useAt" />
		                        	</div>
		                        </div>
		                    </div>
                    	</div>
						<div class="col-md-6">
	                   		<h4 class="card-title">권한그룹 세부 정보</h4>
			                <h6 class="card-subtitle">On a per-column basis (i.e. order by a specific column and then a secondary column if the data in the first column is identical), through the <code> columns.orderData</code> option.</h6>
							<div class="row">
		                    	<div class="col-md-12" style="max-height: 400px; overflow: auto;">
		                    		<ul id="treeDemo" class="ztree"></ul>
		                    	</div>
		                    </div>
	                   	</div>
                   	</div>
					<br/>
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
		var url = '<c:url value="/common/auth/updateAuth.json"/>';
		var options = common.getFormData($("#updateForm"));
			options.menuList = zTreeObj.getCheckedNodes().map(function(obj){
				var newObj = {
					menuSeq : obj.menuSeq,
					authGroupSeq : options.authGroupSeq
				}
				return newObj;
			});
		common.ajax({
            url: url,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(options),
            success:function(data){
                toastr.success('성공적으로 저장하였습니다!');
                common.closeSpa();
                table.ajax.reload();
            }
        });
	}

	var zTreeObj;
	// zTree configuration information, refer to API documentation (setting details)
	var setting = {
			view: {
				expandSpeed:"",
				selectedMulti: false,
				dblClickExpand: true
			},
			edit: {
				enable: true,
			},
			check: {
				enable: true,
				autoCheckTrigger: true
			},
			data: {
				key: {
					checked: "menuAuthCnt",
					name : "menuNm"
				},
				simpleData: {
					enable: true,
					idKey : "menuSeq",
					pIdKey : "upperMenuSeq"
				}
			},
			async: {
                enable: true,
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                type: "post",
                dataType: "json",
                url: "<c:url value='/common/menu/getMenuList.json'/>",
                autoParam: [],
                otherParam: ["authGroupSeq", $("#authGroupSeq").val()],
                dataFilter: null
            },
			callback: {
				onAsyncSuccess: onAsyncSuccess,
				onAsyncError: onAsyncError
			}
	};

	function onAsyncSuccess(event, treeId, treeNode, msg) {
		zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, msg.data);
		zTreeObj.expandAll(true);
	}

	function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
		toastr.error('서버 오류 입니다. 관리자에게 문의하세요.');
	}

	common.init(function(){
		validate.init("updateForm",
	    {
	        rules: {
	            authGroupNm: {required: true, maxlength: 30},
	            authGroupDc: {required: true, maxlength: 255},
	            authClassCd: {required: true, maxlength: 2},
	            useAt: {required: true, maxlength: 1}
	        },
	        submitHandler: function(form) {
	            common.confirm("저장하시겠습니까?").then(function(){
	                fnSave();
	            });
	        }
	    });
		zTreeObj = $.fn.zTree.init($("#treeDemo"), setting);
	});
</script>
