/**
 * data table default options
 */
var dataTableConfig = dtc = {
	init : function(){
		/*
		this.domExceptB= "<'row'<'col-sm-12 col-md-10'f><'col-sm-12 col-md-2 text-right'l>>" +
		"<'row'<'col-sm-12 w-100'tr>>" +
		"<'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7'p>>";
		*/
		this.domBasic= "<'row'<'col-sm-12 w-100'tr>>";

		this.buttonDom = { button: { tag: 'button', className: '' }};
		this.buttonOutlinePrimary = ' btn waves-effect waves-light btn-outline-primary font-weight-bold ';
		//this.buttonLight = ' btn waves-effect waves-light btn-light font-weight-bold ';
		this.optionField = {title: "", mData: "", searchable: false, orderable: false, defaultContent: "", className: 'select-checkbox'};
		this.exportButton = {
			extend: 'collection',
			text: '<i class="fas fa-download"></i>'+' 내보내기',
			titleAttr: '테이블 데이터를 내보냅니다.',
			className: this.buttonOutlinePrimary,
			buttons: [
			{
			    extend: 'copy',
			    className: this.buttonOutlinePrimary+'form-control mb-1',
			    titleAttr: '테이블 데이터를 복사합니다.',
			    text: '<i class="far fa-copy"></i>'+' 복사'
			}, {
				extend: 'excel',
				className: this.buttonOutlinePrimary+'form-control mb-1',
				titleAttr: '테이블 데이터를 엑셀로 내보냅니다.',
				text: '<i class="far fa-file-excel"></i>'+' 엑셀',
				filename: 'excel-export',
				extension: '.xlsx'
				}, {
					extend: 'pdf',
				className:this. buttonOutlinePrimary+'form-control mb-1',
				titleAttr: '테이블 데이터를 PDF로 내보냅니다.',
				text: '<i class="far fa-file-pdf"></i>'+' PDF',
				filename: 'pdf-export',
				extension: '.pdf'
			}, {
					extend: 'print',
				className: this.buttonOutlinePrimary+'form-control mb-1',
				titleAttr: '테이블 데이터를 인쇄합니다.',
				text: '<i class="fas fa-print"></i>'+' 인쇄',
			}
			]
		};
		this.insertButtonSet = {iconClass: "far fa-plus-square", text: "등록", textClass: this.buttonOutlinePrimary};
		this.deleteButtonSet = {iconClass: "far fa-minus-square", text: "삭제"};
	}
	,set : function(){
		var vThis = this;
		$.extend( true, $.fn.dataTable.defaults, {
			dom : "<'row'<'col-sm-12 col-md-6'f><'col-sm-12 col-md-4 text-left pl-5'B><'col-sm-12 col-md-2 text-right'l>>"+"<'row'<'col-sm-12 w-100'tr>>"+"<'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7'p>>",
			columnDefs : [{ defaultContent: "-", targets: "_all" }],
			select : { style: 'multi+shift', selector: 'td:first-child' },
			order : [[ 1, "desc" ]],
			language : {
				decimal:        "",
			    emptyTable:     "표에서 사용할 수있는 데이터가 없습니다.",
			    info:           "총 _TOTAL_개   _START_에서 _END_까지 표시",
			    infoEmpty:      "0 개 항목 중 0 ~ 0 개 표시",
			    infoFiltered:   "(_MAX_ 총 항목에서 필터링 됨)",
			    infoPostFix:    "",
			    thousands:      ",",
			    lengthMenu:     "_MENU_ 개씩 보기",
			    loadingRecords: "로드 중 ...",
			    processing:     "처리 중 ...",
			    search:         "검색:",
			    zeroRecords:    "검색 결과가 없습니다.",
			    paginate: {
			        first:      "처음",
			        last:       "마지막",
			        next:       "다음",
			        previous:   "이전"
			    },
			    aria: {
			        sortAscending:  ": 오름차순 정렬",
			        sortDescending: ": 내림차순 정렬"
			    }
			},
			fnInitComplete : function(settings, json){
				var api = new $.fn.dataTable.Api( settings );

				var selectUrl = (settings && settings.oInit && settings.oInit.selectUrl) || false;
				if(selectUrl){
					$.fn.DataTable.defaults.fnSelectItem = (settings && settings.oInit && settings.oInit.fnSelectItem) || function(row){
						var row = row || 0;
						var json = api.row(row).data();
						common.openSpa(selectUrl, json);
					}
				}

				var insertUrl = (settings && settings.oInit && settings.oInit.insertUrl) || false;

				if(insertUrl){
					var fnInsertItem = (settings && settings.oInit && settings.oInit.fnInsertItem) || $.fn.DataTable.defaults.fnInsertItem;
					api.button().add(0, dataTableUtil.getButtonSet(function(){dtc.fnInsertItem(insertUrl, api)}, vThis.insertButtonSet));
				}

				var deleteUrl = (settings && settings.oInit && settings.oInit.deleteUrl) || false;
				if(deleteUrl){
					var fnDeleteItem = (settings && settings.oInit && settings.oInit.fnDeleteItem) || $.fn.DataTable.defaults.fnDeleteItem;
					api.button().add(1, dataTableUtil.getButtonSet(function(){dtc.fnDeleteItem(deleteUrl, api)}, vThis.deleteButtonSet));
				}
				function selectItem(row){

				}
			}

		});

		$.extend( true, $.fn.DataTable.ext.classes, {
			sInfo : "dataTables_info font-weight-bold",
			sFilterInput : "form-control font-weight-bold",
			sLengthSelect : "form-control font-weight-bold",
			sHeaderTH : "font-weight-bold",
		});
	}
	,fnInsertItem : function(url, api){
		common.openSpa(url);
	},
	fnDeleteItem : function(url, api){
		var options = {};
		var selectedItems = dataTableUtil.getSelectedItems(api);
		if(selectedItems.length == 0){
			toastr.error('삭제할 데이터를 선택해주세요.');
		}else{
			common.confirm("삭제하시겠습니까?").then(function(){
	    		common.ajax({
	                url: url,
	                contentType: "application/json; charset=utf-8",
	    			dataType: "json",
	                data: JSON.stringify(selectedItems),
	                success:function(data){
	                	toastr.success('성공적으로 삭제하였습니다!');
						api.ajax.reload();
	                }
	            });
	 		});
		}
	}
	,getOptionFiled : function(){
		return this.optionField;
	}
	,getDomBasic  : function(){
		return this.domBasic;
	}
	,getButtonDom : function(){
		return this.buttonDom;
	}
	,getButtonOutlinePrimary : function(){
		return this.buttonOutlinePrimary
	}
	,getExportButton :function(){
		return this.exportButton;
	}
}


/**
 * data table utils
 */
var dataTableUtil = dtUtil  = {
		  //option copy
		   getOption: function(options){
			   var customOptions = $.extend(true,{} ,options);
			   return customOptions;
		   }
		   //grid 만 초기화
		   ,init : function(dataTableId , options){
			   var customOptions = dataTableUtil.getOption(options);
			   return $("#"+dataTableId).DataTable(customOptions);
		   }
		  ,initAndSearchBox : function(dataTableId , options){
			   //create searchbox
			   dataTableUtil.setColumnSearchBox(dataTableId , options.columns);
			   //grid init
			   var table = dataTableUtil.init(dataTableId , options);
			   //bind event
			   dataTableUtil.setColumnSearchBoxEvent(table);
			   return table;
		   }
		  //footer search columns 생성
		  ,setColumnSearchBox : function (dataTableId , datTableColumns) {
			  	var footerHtml = "<tr>";
		    	for(var cols in datTableColumns){
		    		var head = datTableColumns[cols];
		    		footerHtml +="<td>";
		    		if(head && head.cFootSearch){
		    			if(!head.cInputType ){
		    				footerHtml +="<input type='text' placeholder='"+head.title+"' />";
		    			}else if(head.cInputType == "select"){
		                    footerHtml +="<select>";
		                    footerHtml +="<option value=''>전체</option>";
		                    for(var i = 0 ; i < head.cCodeList.length ; i++){
		                    	var code = head.cCodeList[i];
		                    	footerHtml +="<option value='"+code.code+"'>"+code.codeNm+"</option>";
		                    }
		                    footerHtml +="</select>";
		                }else if(head.cInputType == "number"){
		                    footerHtml +="<input type='number' placeholder='"+head.title+"' />";
		                }else{
		                    footerHtml +="<input type='text' placeholder='"+head.title+"' />";
		                }

		    		}
		    		footerHtml +="</td>";
		    	}
		    	footerHtml += "</tr>";
		    	$("#"+dataTableId).append($('<tfoot/>').html( footerHtml));
		 }
         //footer seatch box event bind
		,setColumnSearchBoxEvent : function( table ){
			table.columns().eq( 0 ).each( function ( colIdx ) {
	            $( 'input , select', table.column( colIdx ).footer() ).on( 'keyup change', function ()
	            {
	                table.column( colIdx ).search( this.value ).draw();
	            });
	        });
		}
		//선택된 데이타 조회
		,getSelectedItems :function (table){
			var data = table.rows( { selected: true } ).data();
			var selectedItems = [];
			for(var i=0; i<data.length; i++){
				selectedItems[i] = data[i];
			}
			return selectedItems;
		}
		//link render
		,selectItemRender : function (data, meta){
			var data = data || '-';
			var row = (meta && meta.row) || '';

			return '<a href="javascript:void(0);" onclick="$.fn.DataTable.defaults.fnSelectItem(\''+row+'\');">'+data+'</a>';
		}
		,getButtonSet : function (callback, options){
			var iconClass = (options && options.iconClass) || 'far fa-plus-square';
			var text = (options && options.text) || '등록';
			var textClass = (options && options.textCalss) || dtc.getButtonOutlinePrimary();

			var buttonSet = {
		        text: '<i class="'+iconClass+'"></i> '+text,
		        className: textClass,
		        action: function ( e, dt, node, config ) {
		        	callback();
		        }
		    }
			return buttonSet
		}
}

