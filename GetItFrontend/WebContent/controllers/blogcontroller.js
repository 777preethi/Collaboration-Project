/**
 * Blog Controller
 */
app.controller('BlogController', function($scope, BlogService, $location, $rootScope) 
{
	$scope.addBlog = function() {
		BlogService.addBlog($scope.blog).then(function(response) {
			console.log(response.data);
			alert("Blog added successfully.")
			$location.path("/home");
		}, function(response) {
			console.log(response.data);
			if(response.status == 401)
			{
				if(response.data.code == 7)
				{
					alert("Access denied.");
					$location.path("/home");
				}
				else
				{
					$scope.error = response.data;
					$location.path("/login");
				}
			}
			else
			{
				$scope.error = response.data;
				$location.path("/addblog");
			}			
		});
	}
	
	
	//Two Variables: blogsApproved & blogsWaitingApproval
	//Statement to initialize  variable blogsApproved
	BlogService.getBlogsApproved().then(function(response) {
		console.log(response.data);
		$scope.blogsApproved = response.data;
	}, function(response) {
		if(response.status == 401)
		{
			$scope.error = response.data;
			$location.path("/login");
		}
	});
	
	//Statement to initialize variable blogsWaitingApproval
	if($rootScope.currentUser.role == 'ADMIN')
	{
		BlogService.getBlogsWaitingApproval().then(function(response) {
			console.log(response.data);
			$scope.blogsWaitingApproval = response.data;
		}, function(response) {
			if(response.status == 401)
			{
				if(response.data.code == 7)
				{
					alert("Access denied.");
					$location.path("/home");
				}
				else
				{
					$scope.error = response.data;
					$location.path("/login");
				}
			}
		});
	}
});