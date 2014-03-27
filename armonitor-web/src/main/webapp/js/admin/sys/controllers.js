controllers.controller('SystemAdminCtrl', ['$scope', '$routeParams', 'SystemAdminService',
	function($scope, $routeParams, SystemAdminService) {

		if ($routeParams.guid) {
			SystemAdminService.get({guid: $routeParams.guid}, function(response) {
				$scope.data = response;
			});
		} else {
			SystemAdminService.create({}, function(response) {
				$scope.data = response;
				$routeParams.guid = $scope.data.guid;
			});
		}

		$scope.close = function() {
			history.back();
		};

		$scope.save = function() {
			SystemAdminService.save({}, $scope.data, function(response) {
				$scope.data = response;
			});
		};

		$scope.remove = function() {
			SystemAdminService.remove({guid: $routeParams.guid}, function(response) {
				history.back();
			});
		};

		$scope.deleteKey = function() {
			SystemAdminService.deleteKey({guid: $routeParams.guid}, function(response) {
				$scope.data = response;
			});
		};

		$scope.generateKey = function() {
			SystemAdminService.generateKey({guid: $routeParams.guid}, function(response) {
				$scope.data = response;
			});
		};
	}]);

controllers.controller('SystemRoleAdminCtrl', ['$scope', '$routeParams', 'SystemAdminService', 'RoleAdminService',
	function($scope, $routeParams, SystemAdminService, RoleAdminService) {
		var _load = true;

		RoleAdminService.all({}, function(response) {
			$scope.all = response;
		});

		function _search() {

			SystemAdminService.roles({guid: $routeParams.guid}, function(response) {
				for (var i = 0; i < $scope.all.length; i++) {
					var _item = $scope.all[i];
					_item.remove = response[_item.guid];
				}
			});
		}
		;

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
				return !!(((row.name !== null && row.name.indexOf(tmp)) !== -1));
			}
			return true;
		};

		$scope.addRole = function(role) {
			SystemAdminService.addrole({guid: $routeParams.guid, role: role.guid}, function(response) {
				_search();
			});
		};

		$scope.removeRole = function(role) {
			SystemAdminService.removerole({guid: $routeParams.guid, role: role.guid}, function(response) {
				_search();
			});
		};
	}]);

controllers.controller('SystemAgentAdminCtrl', ['$scope', '$routeParams', 'SystemAdminService', 'AgentAdminService',
	function($scope, $routeParams, SystemAdminService, AgentAdminService) {

		$scope.item = {selected: null};
		var _load = true;

		$scope.load = function() {

			if (_load) {
				AgentAdminService.list({}, function(response) {
					$scope.list = response;
				});
				SystemAdminService.agent({guid: $routeParams.guid}, function(response) {
					$scope.item.selected = response.guid;
				});
			}
			_load = false;
		};

		$scope.add = function() {
			SystemAdminService.addagent({guid: $routeParams.guid, agent: $scope.item.selected}, function(response) {

			});
		};
	}]);


controllers.controller('SystemAppAdminCtrl', ['$scope','$routeParams','SystemAdminService','AppAdminService',
	function($scope, $routeParams, SystemAdminService, AppAdminService) {

	$scope.item = {selected: null};
	var _load = true;

	$scope.load = function() {

		if (_load) {
			AppAdminService.list({}, function(response) {
				$scope.list = response;
			});
			SystemAdminService.app({guid: $routeParams.guid}, function(response) {
				$scope.item.selected = response.guid;
			});
		}
		_load = false;
	};

	$scope.add = function() {
		SystemAdminService.addapp({guid: $routeParams.guid, app: $scope.item.selected}, function(response) {

		});
	};
}]);

controllers.controller('SystemSearchAdminCtrl', ['$scope','SystemAdminService',
	function($scope, SystemAdminService) {

	function _startup() {
		SystemAdminService.all({}, function(response) {
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
			return !!(((row.name !== null && row.name.indexOf(tmp)) !== -1
					|| (row.clazz !== null && row.clazz.indexOf(tmp) !== -1)
					|| (row.domain !== null && row.domain.indexOf(tmp) !== -1)
					|| (row.service !== null && row.service.indexOf(tmp) !== -1)));
		}
		return true;
	};
}]);