controllers.controller('RoleAdminCtrl', function($scope, $routeParams, RoleAdminService) {


	if ($routeParams.guid) {
		RoleAdminService.get({guid: $routeParams.guid}, function(response) {
			$scope.data = response;
		});
	} else {
		RoleAdminService.create({}, function(response) {
			$scope.data = response;
			$routeParams.guid = $scope.data.guid;
		});
	}

	$scope.close = function() {
		history.back();
	};

	$scope.save = function() {
		RoleAdminService.save({}, $scope.data, function(response) {
			$scope.data = response;
		});
	};

	$scope.delete = function() {
		RoleAdminService.delete({guid: $routeParams.guid}, function(response) {
			history.back();
		});
	};

});

controllers.controller('RoleSearchAdminCtrl', function($scope, RoleAdminService) {

	function _startup() {
		RoleAdminService.all({}, function(response) {
			$scope.all = response;
		});
	}

	_startup();

	$scope.reload = function() {
		$scope.filter = null;
		_startup();
	};

	$scope.clear = function() {
		$scope.filter = null;
	};

	$scope.search = function(row) {
		if ($scope.filter) {
			var tmp = $scope.filter || '';
			return !!(((row.name !== null && row.name.indexOf(tmp) !== -1)));
		}
		return true;
	};
});