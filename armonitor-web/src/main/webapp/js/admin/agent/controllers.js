
controllers.controller('AgentSysAdminCtrl', ['$scope','$routeParams','AgentAdminService',
	function($scope, $routeParams, AgentAdminService) {

	var _load = true;
	
	function _search() {
		AgentAdminService.sys({guid: $routeParams.guid}, function(response) {
			$scope.all = response;
		});
	};
	
	$scope.load = function() {
		if (_load) {
			_search();
		}
		_load = false;
	};
	
	$scope.reload = function() {
		$scope.filter = null;
		_search();
	};

	$scope.clear = function() {
		$scope.filter = null;
	};

	$scope.search = function(row) {
		if ($scope.filter) {
			var tmp = $scope.filter || '';
			return !!(((row.name !== null && row.name.indexOf(tmp)) !== -1 
					|| (row.clazz !== null && row.clazz.indexOf(tmp) !== -1)
					|| (row.domain !== null && row.domain.indexOf(tmp) !== -1)
					|| (row.service !== null && row.service.indexOf(tmp) !== -1)));
		}
		return true;
	};
}]);

controllers.controller('AgentAdminCtrl', ['$scope','$routeParams','AgentAdminService',
	function($scope, $routeParams, AgentAdminService) {

	$scope.pswd = {n: null, c: null, o: null};
	$scope.test = {result: null};
	$scope.build = null;
	$scope.services = null;
	
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

	$scope.remove = function() {
		AgentAdminService.remove({guid: $routeParams.guid}, function(response) {
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

	$scope.test = function() {
		$scope.test.result = 'req';
		AgentAdminService.test({guid: $routeParams.guid}, function(response) {
			$scope.test.result = 'ok';
			$scope.build = response;
			AgentAdminService.services({guid: $routeParams.guid}, function(response) {
				$scope.services = response;
			}, function(response) { $scope.test.result = 'error'; });
		}, function(response) { $scope.test.result = 'error'; });
	};	
}]);

controllers.controller('AgentSearchAdminCtrl', ['$scope','AgentAdminService',
	function($scope, AgentAdminService) {

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
}]);