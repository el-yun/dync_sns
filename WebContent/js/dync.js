(function ($) {
	
/*
 BackBone.js Based Application - Dync
*/
	
// Models
Issue = Backbone.Model.extend();
Tag = Backbone.Model.extend();
	
// Collections
IssueCollection = Backbone.Collection.extend({
  model: Issue,
  url: './issuecontrol?action=list',
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
	
IssueView = Backbone.View.extend({
  el: $('div#Issue'),
  events : {},
  initialize: function () {
	_.extend(this.options);
	this.template = _.template($("IssueViewTemplate").html());
	this.model.view= this;
	  this.model.on({
		  "destroy" : this.destoryHandler
	  });
	  this.render(0);
  },
  render: function () {
    //$(this.el).html(this.template({ issues: res }));
  }
});	
	
// Operation
new IssuelistView();
// IssueView();
	
})(jQuery);