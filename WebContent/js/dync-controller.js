(function ($) {
    /*
     BackBone.js Based Application - Dync
     */
    var parseURL = 'http://localhost:8080/Dync/usercontrol';
    Kakao.init('07f2d3ff4958ad3553bc8830de72133b');
// Models
    Issue = Backbone.Model.extend();
    Tag = Backbone.Model.extend();
    Code = Backbone.Model.extend({
        url: "http://localhost:8080/Dync/codecontrol?action=list"
    });
    Login = Backbone.Model.extend({
        url: parseURL + "?action=check"
    });
// Collections
    IssueCollection = Backbone.Collection.extend({
        model: Issue,
        url: 'http://localhost:8080/Dync/issuecontrol?action=list',
        parse: function (response) {
            return response.results;
        },
        save: function (options) {
            this.sync("update", this, options);
        },
        sync: function (method, model, options) {
            var that = this;
            var params = _.extend({
                type: 'GET',
                dataType: 'json',
                url: that.url,
                cache: false,
                processData: false
            }, options);
            return $.ajax(params);
        }
    });

    CodeCollection = Backbone.Collection.extend({
        model: Code,
        url: 'http://localhost:8080/Dync/codecontrol?action=list',
        parse: function (response) {
            return response.results;
        },
        sync: function (method, model, options) {
            var that = this;
            var params = _.extend({
                type: 'GET',
                dataType: 'json',
                url: that.url,
                cache: false,
                processData: false,
            }, options);
            console.log("fetch");
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
        template: _.template($('#listIssueTemplate').html()),
        render: function (res) {
            $(this.el).html(this.template({ issues: res }));

        },
        refresh: function(){
            this.collection.sync();
            var that = this;
            _.bindAll(this, 'render');
            this.collection = new IssueCollection();
            this.collection.fetch({
                success: function (data, res) {
                    that.render(res);
                }
            });
        }

    });
    CodelistView = Backbone.View.extend({
        el: $('#code_list'),
        events: {
            click: 'handleClick'
        },
        initialize: function () {
            var that = this;
            _.bindAll(this, 'render');
            this.collection = new CodeCollection();
            this.collection.fetch({
                success: function (data, res) {
                    that.render(res);
                }
            });
        },
        template: _.template($('#listCodeTemplate').html()),
        render: function (res) {
            $(this.el).html(this.template({ codes: res }));

        },
        refresh: function(){
            var that = this;
            this.collection.fetch({
                success: function (data, res) {
                    that.render(res);
                }
            });
        },
        handleClick:function (e) {
            e.preventDefault();
            var target_id = $(e.target).attr("data-codeid");
            $.ajax({
                type: "POST",
                dataType: "json",
                url: "http://localhost:8080/Dync/codecontrol",
                data: { action: 'code', 'CODE_ID': target_id },
                success: function (args) {
                    var result = unescape(decodeURIComponent(args[0].code_contents));
                    console.log(args[0].code_contents);
                    Coder.doc.setValue(result);
                },
                error: function (e) {
                    console.log(e.responseText);
                }
            });
        }
    });
    function Base64DecodeUrl(str){
        str = (str + '===').slice(0, str.length + (str.length % 4));
        return Base64.decode(str.replace(/-/g, '+').replace(/_/g, '/'));
    }
    LoginView = Backbone.View.extend({
        el: $(".user"),
        events: {
            "click #login-btn": "loginpopup"
        },
        initialize: function () {
            //this.render();

            _.bindAll(this, "render");
            this.model.bind('change', this.render);
            this.model.fetch();
        },
        render: function () {
            var status = this.model.get("logged");
            console.log(status);
            if (status == "ok") {
                $(".lightbox_container").hide(); // 로그인 성공시
                $("#login-btn").html("로그아웃");
            } else {
                console.log("Do login");
                $("#login-btn").html("로그인");
            }
        },
        loginpopup: function () {
            var status = this.model.get("logged");
            if (status == "ok") {
                alert("로그아웃");
                Kakao.Auth.logout();
                $.ajax({
                    type: "POST",
                    url: parseURL,
                    data: { action: 'logout'},
                    success: function (args) {
                        var auth = args.toJSON;
                        KakaoLogin.url = parseURL + "?action=check";
                        KakaoLogin.set({logged: args.logged, kakao_hash: null, naver_hash: null});
                    },
                    error: function (e) {
                        alert(e.responseText);
                    }
                });
            } else {
                $(".lightbox_container").show();
                $("#lightbox .close").bind("click", function () {
                    $("#underlayer").hide();
                });
            }
        }
    });

// External Operation
    var KakaoLogin = new Login();
    var LoginSync = new LoginView({model: KakaoLogin});
    var APIlogin = function () {
        Kakao.Auth.createLoginButton({
            container: '#kakao-login-btn',
            success: function (authObj) {
                console.log(authObj);
                Kakao.API.request({
                    url: '/v1/user/me',
                    success: function (res) {
                        var kakao = { action: 'login', logged: 'ok', kakao_hash: res.id, user_name: res.properties.nickname };
                        $.ajax({
                            type: "POST",
                            url: parseURL,
                            data: kakao,
                            callback: '?',
                            success: function (args) {
                                KakaoLogin.set({logged: 'ok'});
                                location.reload();
                            },
                            error: function (e) {
                                alert(e.responseText);
                            }
                        });
                    }
                });
            }
        });
    }
    new APIlogin;
// Operation
    var viewIssueList = new IssuelistView();

    var Coder = null;
    var Codelist = new CodelistView();
    $("#left-menu-code, #add-code").click(function () {
        KakaoLogin.save(null, {
            success: function (model, response) {
                console.log(response);
                if(response.logged == "ok"){
                    $("#repository").toggle({
                        duration: "slow",
                        complete: function () {
                            if (!Coder) {
                                Coder = CodeMirror.fromTextArea(document.getElementById("texteditor"), {
                                    lineNumbers: true,
                                    mode: "javascript",
                                    keyMap: "sublime",
                                    styleActiveLine: true,
                                    autoCloseBrackets: true,
                                    matchBrackets: true,
                                    showCursorWhenSelecting: true,
                                    theme: "mbo"
                                });
                                Coder.doc.setValue("//코드를 미리 입력하세요!");
                            }
                            Coder.on("change", function (cm, n) {
                                $("#editor_content").val(cm.getValue());
                            });
                            //Codelist = new CodelistView();
                        }
                    });
                } else {
                    alert("로그인 후 사용 가능합니다.");
                    return false;
                }
            },
            error: function (model, response) {
                console.log("error");
            }
        });
    });
    $("#left-menu-timeline").click(function () {
        $("#left-menu-tag").toggle("slow");
    });
    $(".winclose").click(function () {
        $("#repository").hide();
    });
    $("#send-btn").click(function () {
        if (KakaoLogin.get("logged") == "ok") {
            var Send = confirm("새로운 이슈를 등록하시겠습니까?");
            if (Send == true) {
                var options = {
                    url: 'http://localhost:8080/Dync/issuecontrol',
                    resetForm: true,
                    success: function(){
                        alert("이슈를 등록하였습니다!");
                    }
                };
                $("#insertIssueForm").ajaxSubmit(options);
            } else {
                return false;
            }
        } else {
            alert("로그인 후 등록할 수 있습니다.");
            return false;
        }
    });

    $("#save-btn").click(function (e) {
        if (KakaoLogin.get("logged") == "ok") {
            if ($("#input-subject").val() != ""){
                var options = {
                    success: function(){
                        alert("코드를 저장하였습니다.");
                        Codelist.refresh();
                    }
                };
                $("#insertCodeForm").ajaxSubmit(options);
            } else {
                return false;
            }
        } else {
            alert("로그인 후 등록할 수 있습니다.");
            return false;
        }
        return false;
    });

    $("#put-btn").click(function (e) {
        var sel = $('#issue_contents').getSelection();
        $('#issue_contents').insertText("[CODE:TEST001]", sel.end).setSelection(sel.start, sel.end);
        return false;
    });
    function nl2br(value) {s
        return value.replace(/\n/g, "<br />");
    }
//IssueView();

})(jQuery);