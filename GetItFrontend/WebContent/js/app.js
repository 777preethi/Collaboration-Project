/**
 * Angular JS Module
 */
var app = angular.module("app",['ngRoute', 'ngCookies'])
app.config(function($routeProvider) {
	$routeProvider
	.when('/register', {
		templateUrl: 'views/registerForm.html',
		controller: 'UserController'
	})
	.when('/login', {
		templateUrl: 'views/login.html',
		controller: 'UserController'
	})
	.when('/editprofile', {
		templateUrl: 'views/editProfile.html',
		controller: 'UserController'
	})
	.when('/addjob', {   //Data is from View[jobForm.html] to Controller
		templateUrl: 'views/jobForm.html',
		controller: 'JobController'
	})
	.when('/alljobs', {   //Data is from Controller to View [$scope.jobs=[]]
		templateUrl: 'views/jobList.html',
		controller: 'JobController'
	})
	.when('/editJob', {
		templateUrl: 'views/editJob.html',
		controller: 'JobController'
	})
	.when('/addblog', {   //Data is from View[blogForm.html] to Controller
		templateUrl: 'views/blogForm.html',
		controller: 'BlogController'
	})
	.when('/getBlogs', {   //Data is from Controller to View [$scope.blogs=[]]
		templateUrl: 'views/blogList.html',
		controller: 'BlogController'
	})
	.otherwise({
		templateUrl: 'views/home.html'
	})
})

app.run(function($rootScope, $cookieStore, UserService, $location) {
	//alert("app.run got loaded.")
	if($rootScope.currentUser == undefined)
	{
		$rootScope.currentUser = $cookieStore.get('currentUser');
		console.log("Cookie: ");
		console.log($cookieStore.get('currentUser'));
	}
	else
	{
		console.log("Unable to set cookie");
	}
	
	$rootScope.logout = function() {
		//Call middlleware logout url -> In middleware, remove HttpSession attribute, update online status to false
		//On success -> In frontend, remove the cookiestore attribute currentUser, delete $rootScope
		UserService.logout().then(function(response) {
			delete $rootScope.currentUser;
			$cookieStore.remove('currentUser');
			$location.path("/login");
		}, function(response) {
			console.log(response.status);
			$location.path("/login");
		})
	}
})