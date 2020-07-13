

var common = commonUtil = {
		  init: function (callBack) {
			//Toastr Default
			toastr.options = {
	    		closeButton: true,
	    		progressBar: true,
	    		positionClass: "toast-top-right",
	    		preventDuplicates: true,
	    		showMethod: "slideDown",
	    		hideMethod: "slideUp",
	    		hideDuration: 500
	    	};

		    callBack && callBack();
		  },
		  //uuid 생성
		  getUUID: function () { // UUID v4 generator in JavaScript (RFC4122 compliant)
			    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
			      var r = Math.random() * 16 | 0,
			        v = c == 'x' ? r : (r & 3 | 8);
			      return v.toString(16);
			    });
		  },
		  //로깅
		  console : function(obj, prefix) {
		    try {
		      var str = JSON.stringify(obj, null, 2); // spacing level = 2
		      console.log((prefix || '') + str);
		    }
		    catch(e) {
		      /*  ignore */
		    }
		  },
		  replaceAll : function (val, deli){//공백제거
			  if(!val){
				  return "";
			  }
			  val = val+"";
			  return val.split(deli).join("");
		  },
		  dataTableExportExcel : function(url , params){
			  location.href = url + "?" + $.param(params);
		  },
		  excelDown : function(url , paramObj){
				common.submitDynamicForm(url, "", "post", paramObj);
		  },
		  //Form을 동적으로 생성하여 제출한다.
		  submitDynamicForm : function(action, target, method, paramObj) {
		  	// #. body 객체 조회
		  	var bodyObj = document.getElementsByTagName("body")[0];

		  	// #. 폼 생성 후 body 추가
		  	var formObj = document.createElement("FORM");
		  	bodyObj.appendChild(formObj);

		  	// #. element 추가
		  	if (paramObj != null) {
		  		if (Object.prototype.toString.apply(paramObj) == "[object String]") {
		  			// #. paramObj가 문자열(JSON)인경우
		  			var inputObj = document.createElement("INPUT");
		  			inputObj.type = "hidden";
		  			inputObj.name = "json";
		  			inputObj.value = paramObj;
		  			formObj.appendChild(inputObj);
		  		} else {
		  			// #. paramObj가 객체인 경우
		  			for (var paramName in paramObj) {
		  				var paramValue = paramObj[paramName];

		  				// #. 배열이면 멀티 input 처리 (체크박스처럼)
		  				if (Object.prototype.toString.apply(paramValue) === '[object Array]') {
		  					for (var j = 0; j < paramValue.length; j++) {
		  						var inputObj = document.createElement("INPUT");
		  						inputObj.type = "hidden";
		  						inputObj.name = paramName;
		  						inputObj.value = paramValue[j];
		  						formObj.appendChild(inputObj);
		  					}
		  				} else {
		  					var inputObj = document.createElement("INPUT");
		  					inputObj.type = "hidden";
		  					inputObj.name = paramName;
		  					inputObj.value = paramValue;
		  					formObj.appendChild(inputObj);
		  				}
		  			}
		  		}
		  	}

		  	// #. request 정보 셋팅
		  	formObj.method = (method != null) ? method : "post";
		  	formObj.target = (target != null) ? target : "_self";
		  	formObj.action = (action != null) ? action : "";

		  	// #. submit
		  	formObj.submit();

		  	// #. 폼 삭제
		  	bodyObj.removeChild(formObj);
		  	delete(formObj);
		  },
          ajax : function(param){
        	var contentType =  "application/x-www-form-urlencoded; charset=UTF-8";
        	var processData =  true;
        	if(param.contentType != undefined ){
        		contentType = param.contentType ;
        	}
        	if(param.processData != undefined ){
        		processData = param.processData ;
        	}

        	return $.ajax({
    			url:param.url,
    			type:param.type||"POST",
    			data: param.data,
    			//timeout:3000,
    			async: param.async || true,
    			datatype:param.datatype || "json",
    		    contentType : contentType,
 	            processData : processData,
    			beforeSend: function (xhr) {
    		        xhr.setRequestHeader("AJAX", "TRUE"); //server에서 ajax에 대한 요청을 해당 header로 처리된다.
    		        common.loadingStart();
    		    },
    		    success:function(result){
    		    	if(result.data == "SUCCESS" || result.code == "200"){
    		    		param.success(result);
    		    	}else{
    		    		//common.alert(result.message||"실패하였습니다.");
    		    		toastr.error(result.message||'오류가 발생하였습니다.');
    		    	}
    				common.loadingEnd();
    			},
    			error: function(xhr, status, err) {
    				if(param.error){
    					param.error();
    				}else{
    					if (xhr.status == 401) {
    						common.alert("로그인후 이용해주세요.");
    					} else if (xhr.status == 403) {
    						common.alert("권한이 없습니다.\n로그인후 이용해주세요.");
    					} else {
    						var result = xhr.responseJSON||{};
    						common.alert(result.message||'예외가 발생했습니다.');
    					}
    				}
    				common.loadingEnd();
    			}
    		});
        }
        ,loadingStart :function(){
        	$(".preloader").show();
        }
        ,loadingEnd :function(){
        	$(".preloader").hide();
        }
        ,alert: function(title , options){
        	var title = title || 'Alert창 입니다.';
        	var elseCallBack = (options && options.elseCallBack) || function(){};
        	var icon = (options && options.icon) || 'info';
        	var text = (options && options.text) || '';

        	return new Promise(function(resolve, reject) {
        		resolve(
        			Swal.fire({
        				title: title,
        				text: text,
        				icon: icon,
        				allowOutsideClick:false,
        				confirmButtonColor: '#3085d6',
        				cancelButtonColor: '#d33',
        				confirmButtonText: '<i class="far fa-check-circle"></i> 확인',
        				buttonsStyling: false,
        				background: 'linear-gradient(to right, #8971ea, #7f72ea, #7574ea, #6a75e9, #5f76e8)',
        				customClass: {
        					confirmButton: 'btn waves-effect waves-light btn-outline-primary btn-white font-weight-bold mr-2',
        				}
        			})
        		)
        	});
        }
        ,confirm : function(title , options ){
        	var title = title || '진행하시겠습니까?';
        	var elseCallBack = (options && options.elseCallBack) || function(){};
        	var icon = (options && options.icon) || 'question';
        	var text = (options && options.text) || '';

        	return new Promise(function(resolve, reject) {
        		Swal.fire({
        			title: title,
        			text: text,
        			icon: icon,
        			allowOutsideClick:false,
        			showCancelButton: true,
        			confirmButtonColor: '#3085d6',
        			cancelButtonColor: '#d33',
        			confirmButtonText: '<i class="far fa-check-circle"></i> 확인',
        			cancelButtonText: '<i class="far fa-times-circle"></i> 취소',
        			buttonsStyling: false,
        			background: 'linear-gradient(to right, #8971ea, #7f72ea, #7574ea, #6a75e9, #5f76e8)',
        			customClass: {
        				confirmButton: 'btn waves-effect waves-light btn-outline-primary btn-white font-weight-bold mr-2',
        				cancelButton: 'btn waves-effect waves-light btn-outline-primary btn-white font-weight-bold'
        			}
        		}).then(function(result) {
        			if (result.value) {
        				resolve();
        			}else{
        				toastr.info('취소하였습니다.');
        				//reject();
        				elseCallBack();

        			}
        		});
        	});
        }, openSpa : function(url, json, callBack, options){
        	var json = json || {};
        	var callBack = callBack || function(){};
        	var container = (options && options.container) || 'container-fluid';
        	var target = (options && options.target) || 'spaPage';
        	var position = (options && options.position) || 'prepend';

        	var containerClass = "." + container;
        	var targetId = "#" + target;

        	var isSpa = $(containerClass).children(targetId).length;
        	if(isSpa == 0){
        		if(position=='append'){
        			$(containerClass).eq(0).append("<div id="+target+"></div>");
        		}else{
        			$(containerClass).eq(0).prepend("<div id="+target+"></div>");
        		}
        	}
        	$.post( url, json, function(data){
        		$(containerClass+" "+targetId).replaceWith(function() {
        		    return $(data).hide();
        		});
        		$(containerClass+" "+targetId).slideDown(200, 'linear');

        		callBack(data);
        	});
        }, closeSpa : function(options){
        	var container = (options && options.container) || 'container-fluid';
        	var target = (options && options.target) || 'spaPage';

        	var containerClass = "." + container;
        	var targetId = "#" + target;

        	$(containerClass+" "+targetId).slideUp(200, function() { $(this).remove(); } );
        }, getFormData : function($form){
            var unindexed_array = $form.serializeArray();
            var indexed_array = {};

            $.map(unindexed_array, function(n, i){
                indexed_array[n['name']] = n['value'];
            });

            return indexed_array;
        }

}
common.init(function(){
	dataTableConfig.init();
	dataTableConfig.set();
});
$(document).ready(function () {
  common && common.init();
});
