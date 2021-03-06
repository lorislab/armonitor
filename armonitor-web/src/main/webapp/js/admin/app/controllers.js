
controllers.controller('AppSysAdminCtrl', ['$scope', '$routeParams', 'AppAdminService',
	function($scope, $routeParams, AppAdminService) {
		var _load = true;

		function _search() {
			AppAdminService.sys({guid: $routeParams.guid}, function(response) {
				$scope.all = response;
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
				return !!(((row.name !== null && row.name.indexOf(tmp)) !== -1
						|| (row.clazz !== null && row.clazz.indexOf(tmp) !== -1)
						|| (row.domain !== null && row.domain.indexOf(tmp) !== -1)
						|| (row.service !== null && row.service.indexOf(tmp) !== -1)));
			}
			return true;
		};

	}]);

controllers.controller('AppProjectAdminCtrl', ['$scope', '$routeParams', 'ProjectAdminService', 'AppAdminService',
	function($scope, $routeParams, ProjectAdminService, AppAdminService) {

		$scope.item = {selected: null};
		var _load = true;

		$scope.load = function() {

			if (_load) {
				ProjectAdminService.list({}, function(response) {
					$scope.list = response;
				});
				AppAdminService.project({guid: $routeParams.guid}, function(response) {
					$scope.item.selected = response.guid;
				});
			}
			_load = false;
		};

		$scope.add = function() {
			AppAdminService.addproject({guid: $routeParams.guid, id: $scope.item.selected}, function(response) {

			});
		};
	}]);

controllers.controller('AppScmAdminCtrl', ['$scope', '$routeParams', 'SCMAdminService', 'AppAdminService',
	function($scope, $routeParams, SCMAdminService, AppAdminService) {

		$scope.bts = {selected: null};
		$scope.test = {result: null, url: null};

		$scope.urls = [];

		$scope.$watch(function() {
			return $scope.data;
		}, function(newVal, oldVal) {
			$scope.urls = [];
			$scope.test.url = null;
			if (newVal) {
				if (newVal.scmTrunk !== null) {
					$scope.urls.push(newVal.scmTrunk);
				}
				if (newVal.scmBranches) {
					$scope.urls.push(newVal.scmBranches);
				}
				if (newVal.scmTags) {
					$scope.urls.push(newVal.scmTags);
				}
			}
		});

		var _load = true;

		$scope.load = function() {

			if (_load) {
				SCMAdminService.list({}, function(response) {
					$scope.btslist = response;
				});
				AppAdminService.scm({guid: $routeParams.guid}, function(response) {
					$scope.bts.selected = response.guid;
				});
			}
			_load = false;
		};

		$scope.addscm = function() {
			AppAdminService.addscm({guid: $routeParams.guid, id: $scope.bts.selected}, function(response) {

			});
		};

		$scope.test = function() {
			$scope.test.result = 'req';
			SCMAdminService.repo({guid: $scope.bts.selected}, {repository: $scope.test.url}, function(response) {
				$scope.test.result = 'ok';
			}, function(response) {
				$scope.test.result = 'error';
			});
		};
	}]);

controllers.controller('AppAdminCtrl', ['$scope', '$routeParams', 'AppAdminService',
	function($scope, $routeParams, AppAdminService) {

		AppAdminService.scmTypes({}, function(response) {
			$scope.scmTypes = response;
		});

		if ($routeParams.guid) {
			AppAdminService.get({guid: $routeParams.guid}, function(response) {
				$scope.data = response;
			});
		} else {
			AppAdminService.create({}, function(response) {
				$scope.data = response;
				$routeParams.guid = $scope.data.guid;
			});
		}

		$scope.close = function() {
			history.back();
		};

		$scope.save = function() {
			AppAdminService.save({}, $scope.data, function(response) {
				$scope.data = response;
			});
		};

		$scope.remove = function() {
			AppAdminService.remove({guid: $routeParams.guid}, function(response) {
				history.back();
			});
		};

		$scope.deleteKey = function() {
			AppAdminService.deleteKey({guid: $routeParams.guid}, function(response) {
				$scope.data = response;
			});
		};

		$scope.generateKey = function() {
			AppAdminService.generateKey({guid: $routeParams.guid}, function(response) {
				$scope.data = response;
			});
		};
	}]);

controllers.controller('AppSearchAdminCtrl', ['$scope', 'AppAdminService',
	function($scope, AppAdminService) {

		function _startup() {
			AppAdminService.all({}, function(response) {
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
				return !!(((row.name !== null && row.name.indexOf(tmp)) !== -1 || (row.type !== null && row.type.indexOf(tmp) !== -1)));
			}
			return true;
		};
	}]);