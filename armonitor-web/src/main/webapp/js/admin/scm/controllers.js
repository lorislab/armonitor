controllers.controller('SCMAdminCtrl', ['$scope','$routeParams','SCMAdminService',
	function($scope, $routeParams, SCMAdminService) {

	$scope.pswd = {n: null, c: null, o: null};
	$scope.test = {result: null};
	
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
	};

	$scope.save = function() {
		SCMAdminService.save({}, $scope.data, function(response) {
			$scope.data = response;
		});
	};

	$scope.remove = function() {
		SCMAdminService.remove({guid: $routeParams.guid}, function(response) {
			history.back();
		});
	};

	$scope.changepswd = function() {
		if ($scope.pswd.n && $scope.pswd.c && ($scope.pswd.n === $scope.pswd.c)) {
			SCMAdminService.pswd({guid: $routeParams.guid}, {old: $scope.pswd.o, p1: $scope.pswd.n}, function(response) {
				$scope.pswd = {n: null, c: null, o: null};
			});
		}
	};
	
	$scope.test = function() {
		$scope.test.result = 'req';
		SCMAdminService.test({guid: $routeParams.guid}, function(response) {
			$scope.test.result = 'ok';
		}, function(response) {
			$scope.test.result = 'error';
		});
	};	
}]);

controllers.controller('SCMSearchAdminCtrl', ['$scope','SCMAdminService',
	function($scope, SCMAdminService) {
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
}]);