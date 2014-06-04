(function ($) {
/*
 BackBone.js Based Application - Dync
*/
var parseURL = 'http://localhost:8080/Dync/usercontrol';
// Models
Issue = Backbone.Model.extend();
Tag = Backbone.Model.extend();
Login = Backbone.Model.extend({
    url: parseURL,
	defaults: {
        logged : 'no',
        naver_hash : null,
        kakao_hash : null
	},
	initialize: function(){
        $.ajax({
            type:"GET",
            url: parseURL,
            datatype: 'application/json',
            data: { action: 'check' },
            success:function(args){

            },
            error:function(e){

            }
        });
	}
});
// Collections
IssueCollection = Backbone.Collection.extend({
  model: Issue,
  url: 'http://localhost:8080/Dync/issuecontrol?action=list',
  parse: function(response) {
	  return response.results;
  },
  sync: function(method, model, options) {
        var that = this;
        var params = _.extend({
                    type: 'GET',
                    dataType: 'json',
                    url: that.url,
                    processData: false
                }, options);
  		return $.ajax(params);
  }
});
	
	
// View
IssuelistView = Backbone.View.extend({
  el: $('div#Issuelist'),
	
  initialize: function () {
	var that = this;
    _.bindAll(this, 'render');
	this.collection = new IssueCollection();
	this.collection.fetch({
        success: function (data, res) {
            that.render(res);
        }
      });
  },
  template : _.template($('#listIssueTemplate').html()),
  render: function (res) {
    $(this.el).html(this.template({ issues: res }));

  }
});
LoginView = Backbone.View.extend({
  el: $(".user"),
  events : {
      "click #login-btn": "loginpopup"
  },
  initialize: function () {
      _.bindAll(this,"render");
      this.model.bind('change', this.render);
  },
  render: function () {
      var status = this.model.get("logged");
      if (status == "ok") {
            $(".lightbox_container").hide(); // 로그인 성공시
            $("#login-btn").html("로그아웃");
      } else {
            console.log("Do login");
            $("#login-btn").html("로그인");
      }
  },
  loginpopup : function(){
      var status = this.model.get("logged");
      if (status == "ok") {
          alert("로그아웃");
          $.ajax({
              type:"POST",
              url: parseURL,
              data: { action : 'logout'},
              success:function(args){
                  var auth = args.toJSON;
                  KakaoLogin.set({logged : args.logged});
              },
              error:function(e){
                  alert(e.responseText);
              }
          });
      } else {
          $(".lightbox_container").show();
          $("#lightbox .close").bind("click", function(){
              $("#underlayer").hide();
          });
      }
  }
});	
	
// External Operation
var APIlogin = function() {
	Kakao.init('07f2d3ff4958ad3553bc8830de72133b');
	Kakao.Auth.createLoginButton({
			container: '#kakao-login-btn',
			success: function(authObj) {
              var token = JSON.stringify(authObj);
              var kakao= { action : 'login', logged: 'ok', kakao_hash: authObj.access_token, naver_hash: null };
                $.ajax({
                    type:"POST",
                    url: parseURL,
                    data: kakao,
                    callback: '?',
                    success:function(args){
                        var auth = args.toJSON;
                        console.log(JSON.stringify(args));
                        KakaoLogin.set({logged : 'ok'});
                    },
                    error:function(e){
                        alert(e.responseText);
                    }
                });
            }
	});	
}
new APIlogin;
var KakaoLogin = new Login();
var LoginSync = new LoginView({model: KakaoLogin});
// Operation
new IssuelistView();

var Coder = null;
$( "#left-menu-code, #add-code").click(function() {
  $( "#repository" ).toggle( "slow");
  if(!Coder) {
	  Coder = CodeMirror.fromTextArea(document.getElementById("texteditor"), {
		lineNumbers: true,
		mode: "javascript",
		keyMap: "sublime",
		autoCloseBrackets: true,
		matchBrackets: true,
		showCursorWhenSelecting: true,
		theme: "monokai"
	  });
  }
});

$( "#left-menu-timeline").click(function() {
	$("#left-menu-tag" ).toggle("slow");
});
$(".winclose").click(function(){ $("#repository").hide();$("#newissue").hide();  });
$(".winclose").click(function(){ $("#repository").hide();$("#newissue").hide();  });

//IssueView();
	
})(jQuery);