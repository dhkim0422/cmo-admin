/**
 * validate utils
 */
$.validator.setDefaults({
	lang: 'ko',
	onkeyup: function( element, event ) {
		if (event.which === 9 && this.elementValue(element) === "") {
	        return;
	    } else {
	        this.element(element);
	    }
	},
	errorClass: 'is-invalid',
	validClass: 'is-valid',
	errorElement: 'div',
	errorPlacement: function (error, element) {
        error.addClass('invalid-feedback');
        element.closest('.form-group').append(error);
    }
});
$.validator.addMethod("passwordRule",  function( value, element ) {
	return this.optional(element) ||  /^.*(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&amp;+=]).*$/.test(value);
});

$.extend( $.validator.messages, {
	passwordRule: "비밀번호는 영문자, 숫자, 특수문자 조합을 입력해야 합니다."
});


var validate = validateUtil = {
		  init: function (formId , option) {
			  $("#"+formId).validate({
		            rules: option.rules,
		            submitHandler: option.submitHandler,
		            messages : option.messages||{}
		      });
		  }
}

