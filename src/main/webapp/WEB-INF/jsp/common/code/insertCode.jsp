<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>

<div class="row">
    <div class="col-md-12">
        <div class="card">
            <form:form class="mt-3" id="insertForm" commandName="data">
	            <div class="card-body">
	            	<div class="row">
	                   	<div class="col-md-12">
			                <h4 class="card-title">그룹코드 정보</h4>
			                <h6 class="card-subtitle">추가 설명란입니다.</h6>
			                <div class="row">
			                	<div class="col-md-12">
			                		<div class="table-responsive">
					                	<table id="groupCodeTable" class="table table-hover display no-wrap custom-table">
					                		<thead>
		                                        <tr>
		                                            <th scope="col"><label class="form-control-label required" for="groupCd">그룹코드</label></th>
		                                            <th scope="col"><label class="form-control-label required" for="groupNm">그룹코드명</label></th>
		                                            <th scope="col"><label class="form-control-label required" for="groupNmEn">영문명</label></th>
		                                            <th scope="col"><label class="form-control-label required" for="groupAbr">약어</label></th>
		                                            <th scope="col"><label class="form-control-label required" for="useAt">사용여부</label></th>
		                                        </tr>
		                                    </thead>
		                                    <tbody>
		                                    	<tr>
		                                    		<td><div class="form-group"><form:input type="text" class="form-control" path="groupCd" /></div></td>
		                                    		<td><div class="form-group"><form:input type="text" class="form-control" path="groupNm" /></div></td>
		                                    		<td><div class="form-group"><form:input type="text" class="form-control" path="groupNmEn" /></div></td>
		                                    		<td><div class="form-group"><form:input type="text" class="form-control" path="groupAbr" /></div></td>
		                                    		<td>
		                                    			<div class="form-group">
				                                    		<form:select class="custom-select mr-sm-2" path="useAt">
															<c:forEach var="c" items="${USE_AT}">
																<form:option value="${c.code}" label="${c.codeNm}" />
															</c:forEach>
															</form:select>
		                                    			</div>
		                                    		</td>
		                                    	</tr>
		                                    </tbody>
					                	</table>
					                </div>
			                	</div>
			                </div>
	                   	</div>
	            	</div>
	            	<br/>
	            	<div class="row">
	                   	<div class="col-md-12">
	                   		<div class="row">
	                   			<div class="col-md-10">
					                <h4 class="card-title">공통코드 정보</h4>
					                <h6 class="card-subtitle">On a per-column basis (i.e. order by a specific column and then a secondary column if the data in the first column is identical), through the <code> columns.orderData</code> option.</h6>
	                   			</div>
	                   			<div class="col-md-2">
	                   				<div class="btn-list text-right">
										<button type="button" class="btn waves-effect waves-light btn-outline-primary font-weight-bold" onclick="fnAddRow()"><i class="far fa-plus-square"></i> 추가</button>
										<button type="button" class="btn waves-effect waves-light btn-outline-primary font-weight-bold" onclick="fnRemoveRow()"><i class="far fa-minus-square"></i> 삭제</button>
									</div>
	                   			</div>
	                   		</div>

			                <div class="table-responsive">
			                	<table id="codeTable" class="table table-hover display no-wrap custom-table"></table>
			                </div>
	                   	</div>
	            	</div>

					<div class="btn-list">
						<button type="submit" class="btn waves-effect waves-light btn-outline-primary font-weight-bold" ><i class="far fa-save"></i> 저장</button>
						<button type="button" class="btn waves-effect waves-light btn-outline-primary font-weight-bold" onclick="common.closeSpa();"><i class="far fa-times-circle"></i> 닫기</button>
					</div>
            	</div>
            </form:form>
        </div>
    </div>
</div>

<script type="text/javaScript" language="javascript">

	function fnSave(){
		var url = '<c:url value="/common/code/insertCode.json"/>';
		var param = $("#insertForm").serialize();
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

	var groupCodeTable = $('#groupCodeTable').DataTable({
		dom : dtc.getDomBasic(),
		columnDefs : [
	        { targets: '_all', orderable: false }
		],
		select: false,
		order : false
	});

	var codeTable = $('#codeTable').DataTable({
		dom : dtc.getDomBasic(),
		columns: [
			{title: "", 		mData:""},
			{title: "코드",		mData:"code"},
			{title: "코드명",		mData:"codeNm"},
			{title: "영문명",		mData:"codeNmEn"},
			{title: "정렬순서",	mData:"colOrd"},
			{title: "사용여부",	mData:"useAt"},
			{title: "코드설명",	mData:"codeDc"},
			{title: "코드아이콘",	mData:"codeIcon"},
		],
		columnDefs : [
			{ targets: [0], searchable: false, orderable: false, defaultContent: "", className: 'select-checkbox', render: function (data, type, row, meta) { return ''}},
			{ targets: [4], orderable: false, render: function (data, type, row, meta) { return fnColumnRender(data, meta, 'number')}},
			{ targets: [5], orderable: false, render: function (data, type, row, meta) { return fnColumnRender(data, meta, 'select')}},
	        { targets: '_all', orderable: false, render: function (data, type, row, meta) { return fnColumnRender(data, meta)}}
		],
		order : false
	});

	function fnAddRow(){
		codeTable.row.add([{oriCode: "", code: "", codeNm: "", codeNmEn: "", colOrd: "", useAt: "Y", codeDc: "", codeIcon: ""}]).draw();
		fnAddRules();
	}

	function fnRemoveRow(){
		var selectedRows = codeTable.rows( { selected: true } ).data();
		if(selectedRows.length==0){
			toastr.error('삭제할 데이터를 선택해주세요.');
		}else{
			codeTable.rows( { selected: true } ).remove().draw();
		}
	}

	function fnColumnRender(data, meta, type){
		var data = data || '';
		var row = (meta && meta.row) || 0;
		var type = type || 'text';
		var colNm = meta.settings.aoColumns[meta.col].mData;

		if(type=='select'){
			return '<div class="form-group"><select class="custom-select mr-sm-2" name="codeList['+row+'].'+colNm+'" >'+
				   '<option value="Y" '+(data=='Y' ? 'selected="selected"':'')+'>사용</option>'+
				   '<option value="N" '+(data=='N' ? 'selected="selected"':'')+'>미사용</option>'+
				   '</select></div>';
		}else{
			return '<div class="form-group"><input type="'+type+'" class="form-control" name="codeList['+row+'].'+colNm+'" value="'+data+'" /></div>';
		}
	}

	function fnAddRules(){
		$('[name^="codeList"][name$="code"]').each(function() {
		    $(this).rules('add', { required: true, maxlength: 20 });
		});
		$('[name^="codeList"][name$="codeNm"], [name^="codeList"][name$="codeNmEn"]').each(function() {
		    $(this).rules('add', { required: true, maxlength: 30 });
		});
		$('[name^="codeList"][name$="colOrd"]').each(function() {
		    $(this).rules('add', { required: true, number: true });
		});
		$('[name^="codeList"][name$="useAt"]').each(function() {
		    $(this).rules('add', { required: true, maxlength: 1});
		});
		$('[name^="codeList"][name$="codeDc"], [name^="codeList"][name$="codeIcon"]').each(function() {
		    $(this).rules('add', { maxlength: 100});
		});
	}

	common.init(function(){
	    validate.init("insertForm",
	    {
	        rules: {
	            groupCd: {required: true, maxlength: 30},
	            groupNm: {required: true, maxlength: 30},
	            groupNmEn: {required: true, maxlength: 30},
	            groupAbr: {maxlength: 10},
	            useAt: {required: true, maxlength: 1}
	        },
	        submitHandler: function(form) {
	            common.confirm("저장하시겠습니까?").then(function(){
	                fnSave();
	            });
	        }
	    });
	    fnAddRules();
	});
</script>
