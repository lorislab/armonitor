controllers.controller('BTSAdminCtrl', function($scope, $routeParams, BTSAdminService) {

	$scope.pswd = {n: null, c: null, o: null};
	
	BTSAdminService.types({}, function(response) {
		$scope.types = response;
	});

	function _startup() {
		if ($routeParams.guid) {
			BTSAdminService.get({guid: $routeParams.guid}, function(response) {
				$scope.data = response;
			});
		} else {
			BTSAdminService.create({}, function(response) {
				$scope.data = response;
				$routeParams.guid = $scope.data.guid;
			});
		}
	}

	_startup();

	$scope.close = function() {
		history.back();
	};

	$scope.save = function() {
		BTSAdminService.save({}, $scope.data, function(response) {
			$scope.data = response;
		});
	};

	$scope.delete = function() {
		BTSAdminService.delete({guid: $routeParams.guid}, function(response) {
			history.back();
		});
	};

	$scope.changepswd = function() {
		if ($scope.pswd.n && $scope.pswd.c && ($scope.pswd.n === $scope.pswd.c)) {
			BTSAdminService.pswd({guid: $routeParams.guid}, {old: $scope.pswd.o, p1: $scope.pswd.n}, function(response) {
				$scope.pswd = {n: null, c: null, o: null};
			});
		}
	};
});

controllers.controller('BTSSearchAdminCtrl', function($scope, BTSAdminService) {

	BTSAdminService.types({}, function(response) {
		$scope.types = response;
	});

	function _startup() {
		BTSAdminService.all({}, function(response) {
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