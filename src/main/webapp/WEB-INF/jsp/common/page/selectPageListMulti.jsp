<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row">
    <div class="col-md-12">
        <div class="card">
         <div class="card-body">
             <h4 class="card-title">페이지 추가</h4>
             <h6 class="card-subtitle">추가 설명란입니다.</h6>
             <div class="table-responsive">
                 <table id="dataTable" class="table table-hover display no-wrap"></table>
             </div>
             <div class="btn-list">
             	<button type="button" class="btn waves-effect waves-light btn-outline-primary font-weight-bold" onclick="fnChoosePage()"><i class="far fa-save"></i> 저장</button>
             	<button type="button" class="btn waves-effect waves-light btn-outline-primary font-weight-bold" onclick="fnClosePage();"><i class="far fa-times-circle"></i> 닫기</button>
             </div>
         </div>
        </div>
    </div>
</div>

<script type="text/javaScript" language="javascript">

	function fnChoosePage(){
		var selectedItems = dataTableUtil.getSelectedItems(pageTable);
		if(selectedItems.length == 0){
			toastr.error('추가할 데이터를 선택해주세요.');
		}else{
			selectedItems = selectedItems.map(function(obj){
				obj.menuSeq = "<c:out value='${menuSeq}'/>";
				return obj;
			});
			common.ajax({
				contentType: "application/json; charset=utf-8",
                dataType: "json",
                data: JSON.stringify(selectedItems),
                url: "<c:url value='/common/menu/insertMenuPage.json'/>",
	            success:function(data){
	            	toastr.success('성공적으로 저장하였습니다!');
                    table.ajax.reload();
                    fnClosePage();
	            }
	        });

		}
	}

	function fnClosePage(){
		var options = {
			container: 'targetPage'
		}
		common.closeSpa(options);
	}
	var pageTable;
	common.init(function(){
	    pageTable = dataTableUtil.init('dataTable' ,{
	        ajax : {
	            method:"post",
	            url: "<c:url value='/common/page/getPageList.json'/>",
	            data:{
	            	"pageUse" : "N"
	            }
	        },
	        columns : [
	        	dtc.getOptionFiled(),
	            {title: "페이지명", mData: "pageNm"},
	            {title: "URL",      mData: "url"},
	            {title: "등록일",      mData: "registDt"}
	        ],
	        buttons: {
	            dom: dtc.getButtonDom(),
	            buttons: [
	            ]
	        }
	    });
	});


</script>
