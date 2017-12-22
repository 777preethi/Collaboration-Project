/**
 * Blog Service
 */
app.factory('BlogService', function($http) {
	var BASE_URL = "http://localhost:8081/GetItMiddleware";
	var blogService = {};
	
	blogService.addBlog = function(blog) {
		return $http.post(BASE_URL+"/saveblog",blog);
	}
	
	blogService.getBlogsApproved = function() {
		return $http.get(BASE_URL+"/getblogs/"+1);
	}
	
	blogService.getBlogsWaitingApproval = function() {
		return $http.get(BASE_URL+"/getblogs/"+0);
	}
	
	return blogService;
});