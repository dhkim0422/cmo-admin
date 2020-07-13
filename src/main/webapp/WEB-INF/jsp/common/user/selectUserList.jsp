<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../../fragment/pageNavi.jsp"  flush="false"></jsp:include>

<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="card">
                <div class="card-body">
                    <h4 class="card-title">사용자 목록</h4>
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
var table;
    common.init(function(){

    	var userStatusList = JSON.parse('${userStatusList}');
        var authList = JSON.parse('${authList}');
        //searchable 전체검색시 검색여부 , cFootSearch 개별검색사용여부 , cInputType input 종류 , cCodeList selectbox 값

    	var datTableColumns = [
    		dtc.getOptionFiled(),
            {title: "사용자ID"   ,mData: "userId"      ,"cFootSearch" : true },
            {title: "이름"       ,mData: "userNm"      ,"cFootSearch" : true, render: function (data, type, row, meta) { return dataTableUtil.selectItemRender(data, meta)}},
            {title: "이메일"     ,mData: "emailAddr"   ,"cFootSearch" : true},
            {title: "주소"       ,mData: "adres"       ,"cFootSearch" : true},
            {title: "상세주소"   ,mData: "detailAdres" ,"cFootSearch" : true},
            {title: "우편번호"   ,mData: "postNo"      ,"searchable": false,"cFootSearch" : true},
            {title: "상태"       ,mData: "userStatusNm","searchable": false,"cFootSearch" : true , "cInputType" : "select" ,"cCodeList" : userStatusList},
            {title: "로그인시도" ,mData: "loginTryCnt" ,"searchable": false,"cFootSearch" : true , "cInputType" : "number"},
            {title: "권한"       ,mData: "authGroupNm" ,"searchable": false,"cFootSearch" : true , "cInputType" : "select" ,"cCodeList" : authList},
            {title: "등록일"     ,mData: "registDt"    ,"searchable": false,"cFootSearch" : false }
    	];

    	table = dataTableUtil.initAndSearchBox('dataTable' ,{
            ajax : {
                   method:"post"
                   ,url: "<c:url value='/common/user/getUserList.json'/>"
                   /*,"data": function ( d ) {
                      console.log(d)

                    }*/
            },
            "processing": true,
            "serverSide": true,
            //"bSort" : false,
            "order": [[ 10, "desc" ]],
            columns : datTableColumns,
            buttons: {
                dom: dtc.getButtonDom(),
                buttons: [
                    {
                        extend: 'excel',
                        className: dtc.getButtonOutlinePrimary() ,
                        titleAttr: '테이블 데이터를 엑셀로 내보냅니다.',
                        text: '<i class="far fa-file-excel"></i>'+' 엑셀',
                        action: function (e, dt, node, config)
                        {
                            var data = table.ajax.params();
                            common.dataTableExportExcel( "<c:url value='/common/user/selectUserListExcel.do'/>" , data)
                        }
                    }
                    ,{
                      text: '<i class="fas fa-wrench"></i>'+' 비밀번호 초기화',
                      className: dtc.getButtonOutlinePrimary() ,
                      action: function ( e, dt, node, config ) {
                            var selectedItems = dataTableUtil.getSelectedItems(table);
                            if(selectedItems.length == 0){
                                toastr.error('초기화 데이터를 선택해주세요.');
                            }else{
                                common.confirm("초기화하시겠습니까?").then(function(){
                                    common.ajax({
                                        url: "<c:url value='/common/user/updatePasswordReset.json'/>",
                                        contentType: "application/json; charset=utf-8",
                                        dataType: "json",
                                        data: JSON.stringify(selectedItems),
                                        success:function(data){
                                            toastr.success('초기화하였습니다!');
                                            table.ajax.reload();
                                        }
                                    });
                                });
                            }
                      }
                    }
                ],
            },
            selectUrl: "<c:url value='/common/user/updateUser.do'/>",
            insertUrl: "<c:url value='/common/user/insertUser.do'/>",
            deleteUrl: "<c:url value='/common/user/deleteUser.json'/>"
        });
    });

</script>
