
function getLoginUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/session/login";
}

function getSignupUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/session/signup";
}

//BUTTON ACTIONS
function login(event){
	//Set the values to update
	var $form = $("#login-form");
	var formData = $form.serializeArray();
	var url = getLoginUrl();
    event.preventDefault(); // Prevent form submission

    // Get form data
    var username = $('#email').val();
    var password = $('#password').val();
    if(!ValidateEmail(formData[0].value)){
        return true;
    }
	$.ajax({
	   url: url,
	   type: 'POST',
	   data:{
           email: username,
           password: password
       },
	   success: function(response) {
            var redirectUrl = $("meta[name=baseUrl]").attr("content") + "/ui/home";
            window.location = redirectUrl;
	   },
	   error: function(response){
	    location.reload(true);
	   }
	});

	return false;
}

function signup(event){
	//Set the values to update
	var $form = $("#signup-form");
	var formData = $form.serializeArray();
	var url = getSignupUrl();
    event.preventDefault(); // Prevent form submission

    // Get form data
    var username = $('#email').val();
    var password = $('#password').val();
    if(!ValidateEmail(formData[0].value)){
        return true;
    }
	$.ajax({
	   url: url,
	   type: 'POST',
	   data:{
           email: username,
           password: password
       },
	   success: function(response) {
            var redirectUrl = $("meta[name=baseUrl]").attr("content") + "/ui/home";
            window.location = redirectUrl;
	   },
	   error: function(response){
	    location.reload(true);
	   }
	});

	return false;
}

function ValidateEmail(email) {

    // Regular expression pattern for email validation
    var pattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

  if (!pattern.test(email)) {
    alert("Invalid email address format!");
    return false
  }
  return true;
}
//INITIALIZATION CODE
function init(){
	$('#login').click(login);
	$('#signup').click(signup);
}

$(document).ready(init);

