controllers.controller('SCMAdminCtrl', function($scope, $routeParams, SCMAdminService, CommonService) {

	SCMAdminService.types({}, function(response) {
		$scope.types = response;
	});

	function _startup() {
		if ($routeParams.guid) {
			SCMAdminService.get({guid: $routeParams.guid}, function(response) {
				$scope.data = response;
			});
		} else {
			SCMAdminService.create({}, function(response) {
				$scope.data = response;
				$routeParams.guid = $scope.data.guid;
			});
		}
	}

	_startup();

	$scope.close = function() {
		history.back();
		scope.$apply();
	};

	$scope.save = function() {
		SCMAdminService.save({}, $scope.data, function(response) {
			$scope.data = response;
			CommonService.updateMsg();
		});
	};

	$scope.delete = function() {
		SCMAdminService.delete({guid: $routeParams.guid}, function(response) {
			history.back();
			scope.$apply();
		});
	};

	$scope.changepswd = function() {
		$scope.validation = false;

		if ($scope.pswd.n && $scope.pswd.c && ($scope.pswd.n === $scope.pswd.c)) {
			SCMAdminService.pswd({guid: $scope.user.guid}, {old: $scope.pswd.o, p1: $scope.pswd.n}, function(response) {

			});
		} else {
			$scope.validation = true;
		}
	};
});

controllers.controller('SCMSearchAdminCtrl', function($scope, SCMAdminService) {
	SCMAdminService.types({}, function(response) {
		$scope.types = response;
	});

	function _startup() {
		SCMAdminService.all({}, function(response) {
			$scope.all = response;
		});
	}

	_startup();

	$scope.reload = function() {
		_startup();
	};

	$scope.clear = function() {
		$scope.filter = null;
	};

	$scope.search = function(row) {
		if ($scope.filter) {
			var tmp = $scope.filter || '';
			return !!((row.user.indexOf(tmp) !== -1 || row.server.indexOf(tmp) !== -1));
		}
		return true;
	};
});