/*******************************************
[Table of Content]

1. Button Fuction 		: 버튼 기능부
2. Validation Function 	: 유효성검사 기능부
3. Mypage Navigation	: 마이페이지 네비게이션 기능부
4. Run Script 			: 스크립트 실행부
*******************************************/


/*=== [ 1. Button Fuction ] ===*/

// 비밀번호 보이기/숨기기 토글 버튼
function pwd_toggle() {
	$('.eye').on('click', function() {
		$('input').toggleClass('active-eye');
		if ($('input').hasClass('active-eye')) {
			$(this).attr('class', "glyphicon glyphicon-eye-close form-control-feedback eye").prev('input').attr('type', "text");
		} else {
			$(this).attr('class', "glyphicon glyphicon-eye-open form-control-feedback eye").prev('input').attr('type', "password");
		}
	});
};

// 이메일 셀렉트
function select_email() {
	$('#slt_email').change(function() {
		if ($(this).val() == '') {
			$('#email').val('');
		} else if ($(this).val() == 'naver') {
			$('#email').val('@naver.com');
		} else if ($(this).val() == 'hanmail') {
			$('#email').val('@hanmail.net');
		} else if ($(this).val() == 'daum') {
			$('#email').val('@daum.net');
		} else if ($(this).val() == 'nate') {
			$('#email').val('@nate.com');
		} else if ($(this).val() == 'hotmail') {
			$('#email').val('@hotmail.com');
		} else if ($(this).val() == 'gmail') {
			$('#email').val('@gmail.com');
		}
	});
};

// 우편번호검색
function search_postcode() {
	$('#form_postcode').removeClass('has-error');
	$('#error_postcode').text('');
	$('#btn_postcode').removeClass('btn btn-lg btn-danger');
	$('#btn_postcode').addClass('btn btn-lg');
	$('#form_addr').removeClass('has-error');
	$('#error_addr').text('');

	new daum.Postcode({
		oncomplete: function(data) {
			// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

			// 각 주소의 노출 규칙에 따라 주소를 조합한다.
			// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
			var addr = ''; // 주소 변수
			var extraAddr = ''; // 참고항목 변수

			//사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
			if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
				addr = data.roadAddress;
			} else { // 사용자가 지번 주소를 선택했을 경우(J)
				addr = data.jibunAddress;
			}

			// 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
			if (data.userSelectedType === 'R') {
				// 법정동명이 있을 경우 추가한다. (법정리는 제외)
				// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
				if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
					extraAddr += data.bname;
				}
				// 건물명이 있고, 공동주택일 경우 추가한다.
				if (data.buildingName !== '' && data.apartment === 'Y') {
					extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
				}
				// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
				if (extraAddr !== '') {
					extraAddr = ' (' + extraAddr + ')';
				}

			}

			// 우편번호와 주소 정보를 해당 필드에 넣는다.
			document.getElementById('postcode').value = data.zonecode;
			document.getElementById("addr").value = addr;
			// 커서를 상세주소 필드로 이동한다.
			document.getElementById("de_addr").focus();
		}
	}).open();
};

// 회원가입 확인 
function check_member_account() {
	var c = confirm('가입 하시겠습니까?');
	if (c == true) {
		if ((check_userid() & check_pwd() & check_chkpwd()
			& check_name() & check_email() & check_tel()
			& check_postcode() & check_addr() & check_detailaddr()) == 0) {

			return false;
		}
	}
};

// 관리자 회원가입 확인 
function check_admin_account() {
	var c = confirm('가입 하시겠습니까?');
	if (c == true) {
		if ((check_adminid() & check_pwd() & check_chkpwd()
			& check_name()) == 0) {

			return false;
		}
	}
};

// 사용자 아이디 찾기
function find_id() {
	var member = $('#form_findid').serialize();
	if ((check_name() & check_email()) == 0) {
		$('#error_email').text('이름 혹은 이메일을 입력해주세요.');
		return false;
	}
	var option = {
		type: 'POST',
		url: '/member/found_id',
		cache: false,
		data: member
	};
	$.ajax(option).done(function(fragment) {
		$('#form_findid').replaceWith(fragment);
	});
};

// 본인인증 이메일 보내기
function send_authemail(name, email) {
		console.log(name);
		console.log(email);
	if (name != null && email != null) {
		var member = {
			'name': name,
			'email': email
		}
	} else {
		var member = $('#form_auth').serialize();
		console.log(member);
		var option = {
			type: 'POST',
			url: '/member/send_authemail',
			data: member,
			success: function(data) {
				console.log(data);
				if (data != null) {
					alert('인증번호가 전송되었습니다.');
					location.href = '/member/user_authok';
				} else {
					alert('인증번호 전송이 실패하였습니다.');
				}
			}
		};
	}
	$.ajax(option);
	return false;
}


// 아이디 기억하기
function show_id() {
	var user_id = localStorage.getItem('user_id');
	var admin_id = localStorage.getItem('admin_id');

	if (user_id != null) {
		$('#member_login_id').val(user_id);
		$('#save_id1').prop('checked', true);
	} else {
		$('#member_login_id').val('');
		$('#save_id1').prop('checked', false);
	}

	if (admin_id != null) {
		$('#admin_login_id').val(admin_id);
		$('#save_id1').prop('checked', true);
	} else {
		$('#admin_login_id').val('');
		$('#save_id1').prop('checked', false);
	}
};

// 회원 로그인
function save_member_id() {
	var member = $('#form_member_login').serializeArray();
	console.log(member);
	var user_id = member[0].value;
	var save_id = member[2].name == 'save_id' ? member[2].value : false;

	if (save_id) {
		localStorage.setItem('user_id', user_id);
	} else {
		localStorage.removeItem('user_id');
	}

	var option = {
		type: 'post',
		url: '/member/loginok',
		data: member
	};

	$.ajax(option).done(function(data) {
		console.log(data);
		if (data == '-1')		$('#error').text('탈퇴 등의 사유로 활동이 정지된 계정입니다.');
		else if (data == '0')	$('#error').text('아이디 또는 비밀번호가 일치하지 않습니다.');
		else location.href = '/'
	});

	return false;
};

// 관리자 로그인
function save_admin_id() {
	var member = $('#form_admin_login').serializeArray();
	console.log(member);
	var admin_id = member[0].value;
	var save_id = member[2].name == 'save_id' ? member[2].value : false;

	if (save_id) {
		localStorage.setItem('admin_id', admin_id);
	} else {
		localStorage.removeItem('admin_id');
	}

	var option = {
		type: 'post',
		url: '/admin/loginok',
		data: member
	};

	$.ajax(option).done(function(data) {
		console.log(data);
		if (data == '-1') $('#error').text('탈퇴 등의 사유로 활동이 정지된 계정입니다.');
		else if (data == '0') $('#error').text('아이디 또는 비밀번호가 일치하지 않습니다.');
		else location.href='/';
	});

	return false;
};

// 회원정보수정
function check_edit() {
	var c = confirm('수정 하시겠습니까?');
	if (c == true) {

		if (check_nowpwd() == 1)
			if (check_pwd() & check_chkpwd() == 0)
				return false;

		if ((check_name() & check_email() & check_tel() & check_postcode() & check_addr() & check_detailaddr()) == 0)
			return false;
	}
};

// 회원탈퇴
function member_cancel() {
	var result = confirm('탈퇴하시겠습니까?')
	if (result == true) {
		var member = $('#form_cancel').serialize();

		var option = {
			type: 'post',
			url: '/mypage/cancelok',
			data: member
		};
		$.ajax(option).done(function(data) {
			if (data == true) {
				alert('SOF8를 탈퇴하셨습니다.');
				location.href = '/';
			}
			else $('#error').text('비밀번호가 일치하지 않습니다.')
		});
	}
	return false;
}



/*=== [ 2. Validation Function ] ===*/

// 비밀번호 일치 확인
function getMatchedPwd(pwd) {
	var result = 0;
	var option = {
		url: '/member/checkpwd',
		data: {
			'pwd': pwd
		},
		async: false	// ajax 동기식 속성 부여해야 ajax 성공시 result 값이 1로 반환이 됨.
	}
	$.ajax(option).done(function(data) {
		if (data == 1) result = 1;
	});
	return result;
}

// 사용자 아이디 중복 확인 
function getMatchedId(user_id) {
	var result = 0;
	var option = {
		url: '/member/checkid',
		data: {
			'user_id': user_id
		},
		async: false	// ajax 동기식 속성 부여해야 ajax 성공시 result 값이 1로 반환이 됨.
	};
	$.ajax(option).done(function(data) {
		if (data == 0) {
			$('#form_id').removeClass('has-error');
			$('#form_id').addClass('has-success');
			$('#error_id').text('사용가능한 아이디입니다.');
			result = 1;
		} else {
			$('#form_id').removeClass('has-success');
			$('#form_id').addClass('has-error');
			$('#error_id').text('중복된 아이디입니다.');
		}
	});
	return result;
};

// 관리자 아이디 중복 확인 
function getMatchedAdminId(admin_id) {
	var result = 0;
	var option = {
		url: '/admin/check_adminid',
		data: {
			'admin_id': admin_id
		},
		async: false	// ajax 동기식 속성 부여해야 ajax 성공시 result 값이 1로 반환이 됨.
	}
	$.ajax(option).done(function(data) {
		if (data == 0) {
			$('#form_id').removeClass('has-error');
			$('#form_id').addClass('has-success');
			$('#error_id').text('사용가능한 아이디입니다.');
			result = 1;
		} else {
			$('#form_id').removeClass('has-success');
			$('#form_id').addClass('has-error');
			$('#error_id').text('중복된 아이디입니다.');
		}
	});
	return result;
};

// 아이디 유효성 검사
function check_userid() {
	var result = 0;
	// input 값 초기화
	var user_id = $('#user_id').val();
	// 7-15자의 하나 이상의 대소문자 영문과 숫자 및 일부 특수문자(_)만 입력 가능한 정규식
	var reg = /^(?=.*[a-zA-Z])[-a-zA-Z0-9_]{7,15}$/;

	if (user_id == "") {
		$('#form_id').removeClass('has-success');
		$('#form_id').addClass('has-error');
		$('#error_id').text('아이디를 입력해 주세요.');
	} else if (!reg.test(user_id)) {
		$('#form_id').removeClass('has-success');
		$('#form_id').addClass('has-error');
		$('#error_id').text('잘못된 형식의 아이디입니다.');
	} else {
		// 사용자 아이디 중복 확인
		result = getMatchedId(user_id);
	}
	return result;
};

// 관리자 아이디 유효성 검사
function check_adminid() {
	var result = 0;
	// input 값 초기화
	var admin_id = $('#admin_id').val();
	// 7-15자의 하나 이상의 대소문자 영문과 숫자 및 일부 특수문자(_)만 입력 가능한 정규식
	var reg = /^(?=.*[a-zA-Z])[-a-zA-Z0-9_]{7,15}$/;

	if (admin_id == "") {
		$('#form_id').removeClass('has-success');
		$('#form_id').addClass('has-error');
		$('#error_id').text('아이디를 입력해 주세요.');
	} else if (!reg.test(admin_id)) {
		$('#form_id').removeClass('has-success');
		$('#form_id').addClass('has-error');
		$('#error_id').text('잘못된 형식의 아이디입니다.');
	} else {
		// 사용자 아이디 중복 확인
		result = getMatchedAdminId(admin_id);
	}
	return result;
};

// 현재 비밀번호 유효성 검사
function check_nowpwd() {
	var result = 0;

	var now_pwd = $('#now_pwd').val();
	var edit_pwd = $('#edit_pwd').val();

	// 현재 비밀번호가 입력됨
	if (now_pwd != '') {

		// 입력한 비밀번호와 현재 비밀번호와 일치
		if (now_pwd == edit_pwd) {
			$('#form_nowpwd').removeClass('has-error');
			$('#error_nowpwd').text('');

			result = 1;
		} else {
			$('#form_nowpwd').removeClass('has-success');
			$('#form_nowpwd').addClass('has-error');
			$('#error_nowpwd').text('현재 비밀번호와 일치하지 않습니다.');
		}
	}

	return result;
};

// 비밀번호 유효성 검사
function check_pwd() {
	var result = 0;
	// input 값 초기화
	var pwd = $('#pwd').val();
	// 8-20자의 하나 이상의 문자, 숫자 및 특수 문자만 입력 가능한 정규식
	var reg = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*?])[A-Za-z\d!@#$%^&*?]{8,20}$/;

	if (pwd == null || pwd == "") {
		$('#form_pwd').removeClass('has-success');
		$('#form_pwd').addClass('has-error');
		$('#error_pwd').text('비밀번호를 입력해 주세요.');
	} else if (!reg.test(pwd)) {
		$('#form_pwd').removeClass('has-success');
		$('#form_pwd').addClass('has-error');
		$('#error_pwd').text('잘못된 형식의 비밀번호입니다.');
	} else {
		$('#form_pwd').removeClass('has-error');
		$('#form_pwd').addClass('has-success');
		$('#error_pwd').text('사용가능한 비밀번호입니다.');
		result = 1;
	}
	return result;
};

// 비밀번호 확인 유효성 검사
function check_chkpwd() {
	var result = 0;
	// input 값 초기화
	var pwd = $('#pwd').val();
	var chk_pwd = $('#chk_pwd').val();

	if (chk_pwd == null || chk_pwd == "") {
		$('#form_chk_pwd').removeClass('has-success');
		$('#form_chk_pwd').addClass('has-error');
		$('#error_chk_pwd').text('비밀번호 확인을 입력해주세요.');
	} else if (chk_pwd != pwd) {
		$('#form_chk_pwd').removeClass('has-success');
		$('#form_chk_pwd').addClass('has-error');
		$('#error_chk_pwd').text('비밀번호와 일치하지 않습니다.');
	} else {
		$('#form_chk_pwd').removeClass('has-error');
		$('#form_chk_pwd').addClass('has-success');
		$('#error_chk_pwd').text('비밀번호와 일치합니다.');
		result = 1;
	}
	return result;
};

// 이름 유효성 검사
function check_name() {
	var result = 0;
	// input 값 초기화 
	var name = $('#name').val();
	// 2-6자의 한글만 입력 가능한 정규식
	var reg = /^[가-힣]{2,6}$/;

	if (name == null || name == "") {
		$('#form_name').addClass('has-error');
		$('#error_name').text('이름을 입력해 주세요.');
	} else if (!reg.test(name)) {
		$('#form_name').addClass('has-error');
		$('#error_name').text('잘못된 형식의 이름입니다.');
	} else {
		$('#form_name').removeClass('has-error');
		$('#error_name').text('');
		result = 1;
	}
	return result;
};

// 이메일 유효성 검사
function check_email() {
	var result = 0;
	// input 값 초기화 
	var email = $('#email').val();
	// 이메일 글자 앞 중간 뒤에 영문+숫자 포함하여 
	// 특수문자 중 점( . ) 하이픈( - ) 언더바( _ ) 만 사용 가능하도록 하는 정규식
	var reg = /^([\w\.\_\-])*[a-zA-Z0-9]+([\w\.\_\-])*([a-zA-Z0-9])+([\w\.\_\-])+@([a-zA-Z0-9]+\.)+[a-zA-Z0-9]{2,8}$/;

	if (email == null || email == "") {
		$('#form_email').addClass('has-error');
		$('#error_email').text('이메일을 입력해 주세요.');
	} else if (!reg.test(email)) {
		$('#form_email').addClass('has-error');
		$('#error_email').text('잘못된 형식의 이메일입니다.');
	} else {
		$('#form_email').removeClass('has-error');
		$('#error_email').text('');
		result = 1;
	}
	return result;
}

// 휴대전화 유효성 검사
function check_tel() {
	var result = 0;
	// input 값 초기화 
	var tel = $('#tel').val();
	// 3, 3-4, 4자의 숫자와 특수기호(-)만 입력 가능한 정규식
	var reg = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;

	if (tel == null || tel == "") {
		$('#form_tel').addClass('has-error');
		$('#error_tel').text('휴대전화를 입력해 주세요.');
	} else if (!reg.test(tel)) {
		$('#form_tel').addClass('has-error');
		$('#error_tel').text('잘못된 형식의 휴대전화입니다.');
	} else {
		$('#form_tel').removeClass('has-error');
		$('#error_tel').text('');
		result = 1;
	}
	return result;
};

// 우편번호 유효성 검사
function check_postcode() {
	var result = 0;
	// input 값 초기화 
	var postcode = $('#postcode').val();

	if (postcode == null || postcode == "") {
		$('#form_postcode').addClass('has-error');
		$('#error_postcode').text('우편번호를 입력해 주세요.');
		$('#btn_postcode').removeClass('btn btn-lg');
		$('#btn_postcode').addClass('btn btn-lg btn-danger');
	} else {
		$('#form_postcode').removeClass('has-error');
		$('#error_postcode').text('');
		$('#btn_postcode').removeClass('btn btn-lg btn-danger');
		$('#btn_postcode').addClass('btn btn-lg');
		result = 1;
	}
	return result;
};

// 주소 유효성 검사
function check_addr() {
	var result = 0;
	// input 값 초기화 
	var addr = $('#addr').val();
	// 모든 글자 100자 이하만 입력 가능한 정규식
	var reg = /^.{1,100}$/;

	if (addr == null || addr == "") {
		$('#form_addr').addClass('has-error');
		$('#error_addr').text('주소를 입력해 주세요.');
	} else if (!reg.test(addr)) {
		$('#form_addr').addClass('has-error');
		$('#error_addr').text('잘못된 형식의 주소입니다.');
	} else {
		$('#form_addr').removeClass('has-error');
		$('#error_addr').text('');
		result = 1;
	}
	return result;
};

// 상세주소 유효성 검사
function check_detailaddr() {
	var result = 0;
	// input 값 초기화 
	var de_addr = $('#de_addr').val();
	// 모든 글자 100자 이하만 입력 가능한 정규식
	var reg = /^.{1,100}$/;

	if (de_addr == null || de_addr == "") {
		$('#form_detailaddr').addClass('has-error');
		$('#error_detailaddr').text('상세주소를 입력해 주세요.');
	} else if (!reg.test(de_addr)) {
		$('#form_detailaddr').addClass('has-error');
		$('#error_detailaddr').text('잘못된 형식의 상세주소입니다.');
	} else {
		$('#form_detailaddr').removeClass('has-error');
		$('#error_detailaddr').text('');
		result = 1;
	}
	return result;
}

// 유효성검사 
function check_valid() {
	$('#user_id').focusin(function() {
		$('#error_id').html('<small class="text-primary"><b>7-15자, 하나 이상의 대소문자, 숫자 및 특수문자(_)</b></small>');
	}).focusout(function() {
		check_userid();
	});

	$('#admin_id').focusin(function() {
		$('#error_id').html('<small class="text-primary"><b>7-15자, 하나 이상의 대소문자, 숫자 및 특수문자(_)</b></small>');
	}).focusout(function() {
		check_adminid();
	});

	$('#now_pwd').focusout(function() {
		check_nowpwd()
	});

	$('#pwd').focusin(function() {
		$('#error_pwd').html('<small class="text-primary"><b>8-20자, 하나 이상의 문자, 숫자 및 특수문자(!@#$%^&*?)</b></small>');
	}).focusout(function() {
		check_pwd()
	});

	$('#chk_pwd').focusout(function() {
		check_chkpwd()
	});

	$('#name').focusin(function() {
		$('#error_name').html('<small class="text-primary"><b>2-6자, 한글</b></small>');
	}).focusout(function() {
		check_name()
	});

	$('#email').focusout(function() {
		check_email()
	});

	$('#tel').focusout(function() {
		check_tel()
	});

	$('#postcode').focusout(function() {
		check_postcode()
	});

	$('#addr').focusout(function() {
		check_addr()
	});

	$('#de_addr').focusout(function() {
		check_detailaddr()
	});
};


/*=== [ 3.Mypage Navigation ] ===*/


// 현재 페이지 네비게이션 메뉴 활성화 기능
function mypage_nav() {
	$('.sidenav li.active').removeClass('active').removeAttr('aria-current');
	$('a[href="' + location.pathname + '"]').closest('li').addClass('active').attr('aria-current', 'page');
};


/*=== [ 4. Run Script ] ===*/


window.onload = function() {
	pwd_toggle();
	select_email();
	check_valid();
	show_id();
	mypage_nav();
};