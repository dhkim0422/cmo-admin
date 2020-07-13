<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%-- JS --%>
<script src="<c:url value='/resources/js/zTree_v3/js/jquery.ztree.all.js'/>"></script>
<script src="<c:url value='/resources/js/jquery.cookie.js'/>"></script>
<%-- CSS --%>
<link rel="stylesheet" href="<c:url value='/resources/js/zTree_v3/css/zTreeStyle/zTreeStyle.css'/>" type="text/css">

<jsp:include page="../../fragment/pageNavi.jsp"  flush="false"></jsp:include>

<div class="container-fluid">
	<div class="row">
		<div class="col-md-3">
			<div class="card">
				<div class="card-body">
					<h4 class="card-title">메뉴트리</h4>
					<h6 class="card-subtitle">추가 설명란입니다.</h6>
					<div class="row">
						<div class="col-md-12">
							<ul id="treeDemo" class="ztree"></ul>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-9">
			<div class="card">
				<div class="card-body">
					<h4 class="card-title">메뉴 정보</h4>
					<h6 class="card-subtitle">추가 설명란입니다.</h6>
					<form:form class="mt-3" id="updateForm" commandName="data">
						<div class="row">
							<div class="col-12">
								<div class="form-body">
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<label class="form-control-label required" for="menuNm">메뉴명</label>
												<form:input type="text" class="form-control" path="menuNm" placeholder="메뉴 목록 조회" />
												<form:input type="hidden" class="form-control" path="menuSeq" placeholder="123" />
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<label class="form-control-label required" for="useAt">메뉴여부</label>
												<form:select class="custom-select mr-sm-2" path="useAt">
													<c:forEach var="c" items="${USE_AT}">
														<form:option value="${c.code}" label="${c.codeNm}" />
													</c:forEach>
												</form:select>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<label class="form-control-label required" for="upperMenuSeq">상위메뉴</label>
												<form:select class="custom-select mr-sm-2"
													path="upperMenuSeq">
													<form:option value="0" label="root" />
													<c:forEach var="m" items="${MENU_LIST}">
														<form:option value="${m.menuSeq}" label="${m.menuNm}" />
													</c:forEach>
												</form:select>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<label class="form-control-label required" for="pageSeq">대표 URL</label>
												<form:select class="custom-select mr-sm-2" path="pageSeq">
													<form:option value="0" label="-" />
													<c:forEach var="p" items="${PAGE_LIST}">
														<form:option value="${p.pageSeq}" label="${p.url}" />
													</c:forEach>
												</form:select>

												<select namd="templatePageSeq" id="templatePageSeq" style="display:none;">
                                                    <option value="0" >-</option>
                                                    <c:forEach var="p" items="${PAGE_LIST}">
                                                        <option value="${p.pageSeq}">${p.url}</option>
                                                    </c:forEach>
                                                </select>

											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<label class="form-control-label required" for="colOrd">정렬순서</label>
												<form:input type="text" class="form-control" path="colOrd" placeholder="123" />
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<label class="form-control-label" for="menuIcon">아이콘</label>
												<form:input type="text" class="form-control" path="menuIcon" placeholder="home" />
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="btn-list">
							<button type="submit"
								class="btn waves-effect waves-light btn-outline-primary font-weight-bold">
								<i class="far fa-save"></i> 저장
							</button>
						</div>
					</form:form>
				</div>
			</div>
			<div class="targetPage"></div>
			<div class="card">
				<div class="card-body">
					<h4 class="card-title">페이지 정보</h4>
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
	var zTreeObj;
	// zTree configuration information, refer to API documentation (setting details)
	var setting = {
			view: {
				expandSpeed:"",
				addHoverDom: addHoverDom,
				removeHoverDom: removeHoverDom,
				selectedMulti: false,
				dblClickExpand: true
			},
			edit: {
				enable: true,
				autoCheckTrigger: true
			},
			data: {
				key: {
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
                otherParam: [],
                dataFilter: null
            },
			callback: {
				onAsyncSuccess: onAsyncSuccess,
				onAsyncError: onAsyncError,
				onExpand: onExpand,
				onCollapse: onCollapse,
				beforeRemove: beforeRemove,
				beforeRename: beforeRename,
				onRename: onRename,
				onDrop: onDrop,
				onClick : onClick
			}
	};

	function addHoverDom(treeId, treeNode) {
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='add node' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_"+treeNode.tId);
		if (btn) btn.bind("click", function(){
			var newMenuNm = "new node";
			var options = {
				menuNm : newMenuNm,
				upperMenuSeq : treeNode.menuSeq,
				newMenuNm : newMenuNm
			};
			fnSave(options);
			return false;
		});
	};

	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_" + treeNode.tId).unbind().remove();
	};

	function onAsyncSuccess(event, treeId, treeNode, msg) {
		zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, msg.data);
		var cookie = $.cookie("z_tree" + window.location);
		if (cookie) {
			var z_tree = JSON.parse(cookie);
			for (var i = 0; i < z_tree.length; i++) {
				var node = zTreeObj.getNodeByParam('menuSeq', z_tree[i]);
				zTreeObj.expandNode(node, true, false);
			}
		}
	}

	function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
		toastr.error('서버 오류 입니다. 관리자에게 문의하세요.');
	}

	function onExpand(event, treeId, treeNode) {
        var cookie = $.cookie("z_tree" + window.location);
        var z_tree = new Array();
        if (cookie) {
            z_tree = JSON.parse(cookie);
        }
        if ($.inArray(treeNode.menuSeq, z_tree) < 0) {
            z_tree.push(treeNode.menuSeq);
        }
        $.cookie("z_tree" + window.location, JSON.stringify(z_tree))
    }

    function onCollapse(event, treeId, treeNode) {
        var cookie = $.cookie("z_tree" + window.location);
        var z_tree = new Array();
        if (cookie) {
            z_tree = JSON.parse(cookie);
        }
        var index = $.inArray(treeNode.menuSeq, z_tree);
        z_tree.splice(index, 1);
        for (var i = 0; i < treeNode.children.length; i++) {
            index = $.inArray(treeNode.children[i].menuSeq, z_tree);
            if (index > -1) z_tree.splice(index, 1);
        }
        $.cookie("z_tree" + window.location, JSON.stringify(z_tree))
    }

	function beforeRemove(treeId, treeNode) {
		zTreeObj.selectNode(treeNode);
		var options = [{ menuSeq: treeNode.menuSeq }];
		var url = '<c:url value="/common/menu/deleteMenu.json"/>';

		common.confirm("삭제하시겠습니까?").then(function(){
			common.ajax({
	            url: url,
	            contentType: "application/json; charset=utf-8",
    			dataType: "json",
	            data: JSON.stringify(options),
	            success:function(data){
	            	toastr.success('성공적으로 삭제하였습니다!');
                    refreshAndSelect();
	            }
	        });
	        return true;
		},function(){
			toastr.info('취소하였습니다.');
			return false;
		});
	}

    function beforeRename(treeId, treeNode, newName) {
 	    $("#"+treeNode.tId + "_input").keyup(function(){
 			$("#menuNm").val(this.value);
 		});
 		if (newName.length == 0) {
 			setTimeout(function() {
 				zTreeObj.cancelEditName();
 				toastr.warning('메뉴명은 반드시 입력해야 합니다.');
 				$("#menuNm").val(treeNode.menuNm);
 			}, 0);
 			return false;
 		}
 		native_name = treeNode.menuNm;
 		return true;
 	}

    function onRename(event, treeId, treeNode, isCancel) {
		if (native_name == treeNode.menuNm) {
			return;
		}
		var options = {
			menuSeq : treeNode.menuSeq,
			upperMenuSeq : treeNode.upperMenuSeq || '0',
			menuNm : treeNode.menuNm
		};

		fnSave(options)
	}

    function onDrop(event, treeId, treeNodes, targetNode, moveType) {
		var rootFlag = false;
		if (targetNode) {
			if (targetNode.getParentNode() == null && moveType != "inner") {
				rootFlag = true;
			} else {
				// 				alert(treeNodes.length + "," + targetNode.tId + ", " + targetNode.menuNm );
			}
		} else {
			if (moveType = "inner") {
				rootFlag = true;
			} else {
				return false;
			}
		}
		$.post('<c:url value="/common/menu/updateMenuPage.json"/>', {
			menuSeq : treeNodes[0].menuSeq,
			upperMenuSeq : rootFlag ? 0 : targetNode.menuSeq
		}, function() {
			var parentNode = treeNodes[0].getParentNode();
			var siblings = rootFlag ? treeObj.getNodes() : parentNode.children;
			for (var i = 0; i < siblings.length; i++) {
				var sibling = siblings[i];
				sibling.colOrd = i;
				common.ajax({
		            url: '<c:url value="/common/code/updateMenuPage.json"/>',
		            data: {
	                    menuSeq : sibling.menuSeq,
	                    upperMenuSeq : rootFlag ? 0 : sibling.upperMenuSeq,
	                    colOrd : sibling.colOrd
	                },
		            success:function(data){
		            	fnTopMenuList();
		            }
		        });
			}
			$("#" + treeNodes[0].tId + "_span").click();
		});
	};

	function onClick(event, treeId, treeNode) {
		$('#menuNm').val(treeNode.menuNm);
		$('#menuSeq').val(treeNode.menuSeq);
		$('#colOrd').val(treeNode.colOrd);
		$('#upperMenuSeq').val(treeNode.upperMenuSeq == null ? 0 : treeNode.upperMenuSeq);
		$('#useAt').val(treeNode.useAt);
		var option = $("#templatePageSeq").html();
		if(treeNode.pageSeq){
			option = "<option value='"+treeNode.pageSeq+"'>"+treeNode.url+"</option>" + option;
		}
		$('#pageSeq').html(option);
		$('#pageSeq').val(treeNode.pageSeq);
		$('#menuIcon').val(treeNode.menuIcon);
	    table.ajax.reload();
	};

	function fnSave(options){
		var options = options || $("#updateForm").serialize();
		var url = '<c:url value="/common/menu/saveMenu.json"/>';
		common.ajax({
            url: url,
            data: options,
            success:function(data){
            	toastr.success('성공적으로 저장하였습니다!');
                refreshAndSelect();
            }
        });
	}

	function refreshAndSelect(){
		var node = zTreeObj.getSelectedNodes();
		if(node.length == 0){
			zTreeObj.reAsyncChildNodes();

		}else{
			zTreeObj.reAsyncChildNodes(node[0], "refresh", false, function(){
				zTreeObj.selectNode(node[0]);
			});
		}
	}

	var table = $('#dataTable').DataTable({
		ajax : {
            method:"post",
            url: "<c:url value='/common/menu/getSelectMenuPageList.json'/>",
            data: function ( d ) {
            	var menuSeq = $('#menuSeq').val() || '0';
	            return $.extend( {}, d, {
	            	menuSeq : menuSeq
	            });
            }
        },
		columns : [
			{title: "페이지명",	mData: "pageNm"},
			{title: "URL",		mData: "url"},
			{title: "삭제",		mData: "pageSeq", render: function (data, type, row, meta) {
				return '<button class="'+dtc.getButtonOutlinePrimary()+'" onclick="pageDelete('+row.menuSeq+','+row.pageSeq+');"><i class="far fa-trash-alt"></i> 삭제</button>';
			}}
		],
	    buttons: {
	    	dom: dtc.getButtonDom(),
	    	buttons: [
	    		dataTableUtil.getButtonSet(function(){pageAdd();}, {text: "페이지 추가"})
    		]
	    },
	});

	function pageAdd(){
		var menuSeq = $('#menuSeq').val() || 0;
		if(menuSeq==0){
			toastr.info('메뉴를 먼저 선택해주세요.');
			return;
		}
		var json = {
			menuSeq: menuSeq
		};
		var options = {
			container: 'targetPage',
			position: 'append'
		};
		common.openSpa("<c:url value='/common/page/selectPageListMulti.do'/>", json, null, options);
	}

	function pageDelete(menuSeq, pageSeq){
		var json = {
			menuSeq: menuSeq,
			pageSeq: pageSeq
		}
		common.confirm("삭제하시겠습니까?").then(function(){
			common.ajax({
	            url: "<c:url value='/common/menu/deleteMenuPage.json'/>",
	            contentType: "application/json; charset=utf-8",
    			dataType: "json",
	            data: JSON.stringify(json),
	            success:function(data){
	            	toastr.success('성공적으로 삭제하였습니다!');
                    table.ajax.reload();
	            }
	        });

 		});
	}

	common.init(function(){
		zTreeObj = $.fn.zTree.init($("#treeDemo"), setting);

		$("#menuNm").keyup(function() {
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			var node = treeObj.getSelectedNodes()[0];
			if (node != null) {
				$("#" + node.tId + "_span").text(this.value);
			}
		});
		validate.init("updateForm",
		{
			rules: {
				menuNm: {required: true, maxlength: 30},
				useAt: {required: true},
				upperMenuSeq: {required: true},
				menuSeq: {number: true},
				pageSeq: {number: true},
				colOrd: {required: true, number: true},
				menuIcon: {maxlength: 100}
			},
		 	submitHandler: function(form) {
		 		common.confirm("저장하시겠습니까?").then(function(){
		 			fnSave();
		 		});
		    }
		});
	});
</script>
