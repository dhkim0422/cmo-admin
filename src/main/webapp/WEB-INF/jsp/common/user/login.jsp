<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

        <!-- ============================================================== -->
        <!-- Login box.scss -->
        <!-- ============================================================== -->
        <div class="auth-wrapper d-flex no-block justify-content-center align-items-center position-relative"
            style="background:url(<c:url value='/resources/images/big/auth-bg.jpg'/>) no-repeat center center;">
            <div class="auth-box row">
                <div class="col-lg-7 col-md-5 modal-bg-img" style="background-image: url(<c:url value='/resources/images/big/d2.jpg'/>);">
                </div>
                <div class="col-lg-5 col-md-7 bg-white">
                    <div class="p-3">
                        <div class="text-center">
                            <img src="<c:url value='/resources/images/big/icon.png'/>" alt="wrapkit">
                        </div>
                        <h2 class="mt-3 text-center">Sign In</h2>
                        <p class="text-center">Enter your email address and password to access admin panel.</p>
                        <form class="mt-4" id="frm" name="frm">
                            <div class="row">
                                <div class="col-lg-12">
                                    <div class="form-group">
                                        <label class="text-dark" for="userId">Username</label>
                                        <input class="form-control" id="userId" name="userId" type="text" value="admin"
                                            placeholder="enter your username">
                                    </div>
                                </div>
                                <div class="col-lg-12">
                                    <div class="form-group">
                                        <label class="text-dark" for="passwd">Password</label>
                                        <input class="form-control" id="passwd" name="passwd" type="password" value="1234"
                                            placeholder="enter your password">
                                    </div>
                                </div>
                                <div class="col-lg-12 text-center">
                                    <button type="submit" class="btn btn-block btn-dark">Sign In</button>
                                </div>
                                <div class="col-lg-12 text-center mt-5">
                                    Don't have an account? <a href="#" class="text-danger">Sign Up</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- ============================================================== -->
        <!-- Login box.scss -->
        <!-- ============================================================== -->

<script type="text/javaScript" language="javascript">
    common.init(function(){
    	validate.init("frm",
    		{
              rules: {
                  userId: {required: true, maxlength: 20},
                  passwd: {required: true, maxlength: 20},
              },
              submitHandler: function(form) {
            	  fnLogin();
              }
            }
    	);
    });
    var fnLogin = function(){
    	var url = '<c:url value="/common/user/login.json"/>';
        var params = $("#frm").serialize();
        common.ajax({
            url: url,
            data: params,
            success:function(data){
                location.href = "<c:url value='/frt/index.do'/>" ;
            }
        });
    }
</script>