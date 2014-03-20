controllers.controller('AgentAdminCtrl', function($scope, $routeParams, AgentAdminService) {

	$scope.pswd = {n: null, c: null, o: null};

	AgentAdminService.types({}, function(response) {
		$scope.types = response;
	});
	
	if ($routeParams.guid) {
		AgentAdminService.get({guid: $routeParams.guid}, function(response) {
			$scope.data = response;
		});
	} else {
		AgentAdminService.create({}, function(response) {
			$scope.data = response;
			$routeParams.guid = $scope.data.guid;
		});
	}

	$scope.close = function() {
		history.back();
	};

	$scope.save = function() {
		AgentAdminService.save({}, $scope.data, function(response) {
			$scope.data = response;
		});
	};

	$scope.delete = function() {
		AgentAdminService.delete({guid: $routeParams.guid}, function(response) {
			history.back();
		});
	};
	
	$scope.changepswd = function() {
		if ($scope.pswd.n && $scope.pswd.c && ($scope.pswd.n === $scope.pswd.c)) {
			AgentAdminService.pswd({guid: $routeParams.guid}, {old: $scope.pswd.o, p1: $scope.pswd.n}, function(response) {
				$scope.pswd = {n: null, c: null, o: null};
			});
		}
	};	

});

controllers.controller('AgentSearchAdminCtrl', function($scope, AgentAdminService) {

	function _startup() {
		AgentAdminService.all({}, function(response) {
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
			return !!(((row.user !== null && row.user.indexOf(tmp) !== -1)
					|| (row.name !== null && row.name.indexOf(tmp) !== -1)
					|| (row.url !== null && row.url.indexOf(tmp) !== -1)
					));
		}
		return true;
	};
});